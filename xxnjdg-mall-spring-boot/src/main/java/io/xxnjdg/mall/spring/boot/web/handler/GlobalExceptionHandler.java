package io.xxnjdg.mall.spring.boot.web.handler;


import io.xxnjdg.common.constant.SysErrorCodeEnum;
import io.xxnjdg.common.exception.RRException;
import io.xxnjdg.common.exception.ServiceException;
import io.xxnjdg.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e){
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    /**
     * 逻辑异常
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public R serviceExceptionHandler( ServiceException ex) {
        logger.debug("[serviceExceptionHandler]", ex);
        return R.error(ex.getCode(), ex.getMessage());
    }

    /**
     *  Spring MVC 参数不正确
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R missingServletRequestParameterExceptionHandler( MissingServletRequestParameterException ex) {
        logger.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return R.error(SysErrorCodeEnum.MISSING_REQUEST_PARAM_ERROR.getCode(), SysErrorCodeEnum.MISSING_REQUEST_PARAM_ERROR.getMessage() + ":" + ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R constraintViolationExceptionHandler(ConstraintViolationException ex) {
        logger.info("[constraintViolationExceptionHandler]", ex);
        // 拼接详细报错
        StringBuilder detailMessage = new StringBuilder("\n\n详细错误如下：");
        ex.getConstraintViolations().forEach(constraintViolation -> detailMessage.append("\n").append(constraintViolation.getMessage()));
        return R.error(SysErrorCodeEnum.VALIDATION_REQUEST_PARAM_ERROR.getCode(), SysErrorCodeEnum.VALIDATION_REQUEST_PARAM_ERROR.getMessage()
            + detailMessage.toString());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public R handleValidationException(ValidationException e) {
        logger.warn("[ValidationException]", e);
        return R.error(SysErrorCodeEnum.VALIDATION_REQUEST_PARAM_ERROR.getCode(),
                SysErrorCodeEnum.VALIDATION_REQUEST_PARAM_ERROR.getMessage()+":"+e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("[exceptionHandler]", e);
        // 返回 ERROR R
        return R.error(SysErrorCodeEnum.SYS_ERROR.getCode(), SysErrorCodeEnum.SYS_ERROR.getMessage());
    }


}
