package com.example.webproject.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.webproject.domain.Log;
import com.example.webproject.service.LogService;
import com.example.webproject.util.SecurityUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;



    ThreadLocal<Long> currentTime = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return 0L;
        }
    };
    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.example.webproject.aop.OpLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }
    /**
     * 设置操作开始时间
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        return joinPoint.proceed();
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "returnVal")
    public void logAfterReturning(JoinPoint joinPoint, Object returnVal) {
        Logger logger = Logger.getLogger(this.getClass());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OpLog logs = method.getAnnotation(OpLog.class);
        try {
            Log log = new Log();
            log.setType(logs.value());
            log.setOt(new Date());
            if (returnVal != null) {
                JSONObject rtnValJSON = (JSONObject) JSONObject.toJSON(returnVal);
                String code = rtnValJSON.getString("code");
                if (code.equals("200")){
                    log.setResult("成功");
                }else {
                    log.setResult("失败");
                }
            }else {
                log.setResult("成功");
            }
            log.setUsername(getUsername());
            log.setSystem(signature.getName() + "()");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.setIp(getIP(request));
            logService.save(log);
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
        }
        finally {
            currentTime.remove();
        }
    }

    public String getUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    public String getIP(HttpServletRequest request) {
        //目前则是网关ip
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                //只获取第一个值
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            //取不到真实ip则返回空，不能返回内网地址。
            return "";
        }
    }

}
