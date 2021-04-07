package com.example.orderservice.aop;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class WebAspect {
    @Pointcut("execution(* com.example.orderservice.controller.*.*(..))")
    public void poinCut() {

    }
    @Around("poinCut()")
    public Object myaround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("---------------------执行Controller开始: "+ pjp.getSignature() + " 参数：" + Lists.newArrayList(pjp.getArgs()).toString());
        Object proceed = pjp.proceed();
        log.info("---------------------执行Controller结束: "+ pjp.getSignature() + " 返回值：" + proceed.toString());
        return proceed;
        //return new CommonResult<String>(200,"成功","sssdsd");
    }
}
