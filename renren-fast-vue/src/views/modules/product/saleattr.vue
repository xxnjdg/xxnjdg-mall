<template>
  <div>
      <base-attr :attrtype="0"></base-attr>
  </div>
</template>

<script>
import BaseAttr from './baseattr'
export default {
  // import引入的组件需要注入到对象中才能使用
  components: {BaseAttr},
  props: {},
  data () {
    // 这里存放数据
    return {}
  },
  // 计算属性类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 方法集合
  methods: {}
}
</script>
<style scoped>
</style>
