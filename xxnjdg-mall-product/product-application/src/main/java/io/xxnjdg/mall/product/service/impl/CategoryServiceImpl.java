package io.xxnjdg.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.xxnjdg.mall.product.service.CategoryBrandRelationService;
import io.xxnjdg.mall.product.vo.Catelog2VO;
import io.xxnjdg.mall.product.vo.Catelog3VO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.Query;

import io.xxnjdg.mall.product.dao.CategoryDao;
import io.xxnjdg.mall.product.entity.CategoryEntity;
import io.xxnjdg.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1）、找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;

    }

    @Override
    public void removeMenuByIds(List<Long> list) {

        // TODO: 2020/6/7 分类被引用不能删
        removeByIds(list);
    }

    //[2,25,225]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);


        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional
    @Override
    @CacheEvict(value = "category",allEntries = true)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    @Cacheable(value = "category",key = "#root.methodName")
    public List<CategoryEntity> getLevel1Categories() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        return selectList.stream().filter(o -> o.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "category",key = "#root.method.name")
    public Map<String, List<Catelog2VO>> getCatalogJson() {
        List<CategoryEntity> selectList = baseMapper.selectList(null);
        List<CategoryEntity> level1Categories = getParent_cid(selectList, 0L);

        Map<String, List<Catelog2VO>> parentCid = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            List<Catelog2VO> catelog2VOS = null;
            if (!CollectionUtils.isEmpty(categoryEntities)) {
                catelog2VOS = categoryEntities.stream().map(l2 -> {
                    Catelog2VO catelog2VO = new Catelog2VO(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    List<CategoryEntity> level3Catalog = getParent_cid(selectList, l2.getCatId());
                    if (!CollectionUtils.isEmpty(level3Catalog)) {
                        List<Catelog3VO> collect = level3Catalog.stream().map(l3 -> {
                            Catelog3VO catelog3VO = new Catelog3VO(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3VO;
                        }).collect(Collectors.toList());
                        catelog2VO.setCatalog3List(collect);
                    }
                    return catelog2VO;
                }).collect(Collectors.toList());
            }
            return catelog2VOS;
        }));
        return parentCid;

    }

    /**
     * 利用Redis进行缓存商品分类数据
     *
     * @return
     */
    public Map<String, List<Catelog2VO>> getCatalogJson2() throws InterruptedException {
        // TODO 产生堆外内存溢出 OutOfDirectMemoryError
        /**
         * 1. SpringBoot2.0之后默认使用 lettuce 作为操作 redis 的客户端，lettuce 使用 Netty 进行网络通信
         * 2. lettuce 的 bug 导致 Netty 堆外内存溢出 -Xmx300m   Netty 如果没有指定对外内存 默认使用 JVM 设置的参数
         *      可以通过 -Dio.netty.maxDirectMemory 设置堆外内存
         * 解决方案：不能仅仅使用 -Dio.netty.maxDirectMemory 去调大堆外内存
         *      1. 升级 lettuce 客户端   2. 切换使用 jedis
         *
         *      RedisTemplate 对 lettuce 与 jedis 均进行了封装 所以直接使用 详情见：RedisAutoConfiguration 类
         */

        /**
         * - 空结果缓存：解决缓存穿透
         *
         * - 设置过期时间（加随机值）：解决缓存雪崩
         *
         * - 加锁：解决缓存击穿
         */

        // 给缓存中放入JSON字符串，取出JSON字符串还需要逆转为能用的对象类型

        // 1. 加入缓存逻辑， 缓存中存的数据是 JSON 字符串
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            // 2 如果缓存未命中 则查询数据库
            Map<String, List<Catelog2VO>> catalogJsonFromDB = getCatalogJsonFromDBWithRedisLock();

            // 4 返回从数据库中查询的数据
            return catalogJsonFromDB;
        }

        Map<String, List<Catelog2VO>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2VO>>>() {
        });
        return result;
    }

    public Map<String, List<Catelog2VO>> getCatalogJsonFromDBWithRedissonLock() {

        RLock lock = redisson.getLock("catalogJson-lock");

        lock.lock();
        Map<String, List<Catelog2VO>> dataFromDB;
        try {
            dataFromDB = getDataFromDB();
        } finally {
            lock.unlock();
        }
        return dataFromDB;
    }

    /**
     * Redis 实现分布式锁
     *
     * @return
     */
    public Map<String, List<Catelog2VO>> getCatalogJsonFromDBWithRedisLock() throws InterruptedException {
        // 1 Redis 占位
        String uuid = UUID.randomUUID().toString();
        Boolean lockResult = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lockResult) {
            // 2 加锁成功 执行业务
            Map<String, List<Catelog2VO>> dataFromDB;
            try {
                dataFromDB = getDataFromDB();
            } finally {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Collections.singletonList("lock"), uuid);
            }
//            String lockFromRedis = stringRedisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockFromRedis))
//                stringRedisTemplate.delete("lock"); // 删除锁
            return dataFromDB;
        } else {
            // 3 加锁失败 睡眠 100ms 后重试
            Thread.sleep(100);
            return getCatalogJsonFromDBWithRedisLock();
        }
    }

    private Map<String, List<Catelog2VO>> getDataFromDB() {
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if (!StringUtils.isEmpty(catalogJSON)) {
            Map<String, List<Catelog2VO>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2VO>>>() {
            });
            return result;
        }

        List<CategoryEntity> selectList = baseMapper.selectList(null);
        List<CategoryEntity> level1Categories = getParent_cid(selectList, 0L);

        Map<String, List<Catelog2VO>> parentCid = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            List<Catelog2VO> catelog2VOS = null;
            if (!CollectionUtils.isEmpty(categoryEntities)) {
                catelog2VOS = categoryEntities.stream().map(l2 -> {
                    Catelog2VO catelog2VO = new Catelog2VO(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    List<CategoryEntity> level3Catalog = getParent_cid(selectList, l2.getCatId());
                    if (!CollectionUtils.isEmpty(level3Catalog)) {
                        List<Catelog3VO> collect = level3Catalog.stream().map(l3 -> {
                            Catelog3VO catelog3VO = new Catelog3VO(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3VO;
                        }).collect(Collectors.toList());
                        catelog2VO.setCatalog3List(collect);
                    }
                    return catelog2VO;
                }).collect(Collectors.toList());
            }
            return catelog2VOS;
        }));
        // 3 查到的数据再放入缓存 将对象转为JSON放入缓存
        String cache = JSON.toJSONString(parentCid);
        stringRedisTemplate.opsForValue().set("catalogJSON", cache, 1, TimeUnit.DAYS);
        return parentCid;
    }

    /**
     * 从数据库查询并封装商品分类数据
     *
     * @return
     */
    public Map<String, List<Catelog2VO>> getCatalogJsonFromDBWithLocalLock() {
        /**
         * 优化
         * 1. 将数据库的多次查询变为一次查询
         *
         * SpringBoot 所有的组件在容器中默认都是单例的，使用 synchronized (this) 可以实现加锁
         */
        synchronized (this) {
            /**
             * 得到锁之后 应该再去缓存中确定一次，如果没有的话才需要继续查询
             *
             * 假如有100W个并发请求，首先得到锁的请求开始查询，此时其他的请求将会排队等待锁
             * 等到获得锁的时候再去执行查询，但是此时有可能前一个加锁的请求已经查询成功并且将结果添加到了缓存中
             */

            return getDataFromDB();
        }
    }

    //225,25,2
    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        //1、收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;

    }

    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //2、菜单的排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

}