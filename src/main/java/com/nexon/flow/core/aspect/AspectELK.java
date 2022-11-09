package com.nexon.flow.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.flow.domain.dto.elk.LogELK;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

@Aspect
@Component
public class AspectELK {

    private ObjectMapper mapper = new ObjectMapper();

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("ELK_LOGGER");

    private String host = "";
    private String ip = "";
    private String clientIp = "";
    private String clientUrl = "";

    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }

    @Around("bean(*Controller)")
    public Object controllerAroundLogging(ProceedingJoinPoint pjp) throws Throwable {

        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        this.clientIp = request.getRemoteAddr();
        this.clientUrl = request.getRequestURL().toString();

        LogELK logelk = new LogELK();
        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setMethod(request.getMethod());
        logelk.setCallFunction(callFunction);
        logelk.setType("CONTROLLER_REQ");
        logelk.setParameter(mapper.writeValueAsString(request.getParameterMap()));

        log.info("{}", mapper.writeValueAsString(logelk));

        Object result = pjp.proceed();
        timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        logelk.setTimestamp(timeStamp);
        logelk.setType("CONTROLLER_RES");
        logelk.setParameter(mapper.writeValueAsString(result));

        log.info("{}", mapper.writeValueAsString(logelk));

        return result;
    }

    @Before("execution(* *..extetnal.*.*(..))")
    public void serviceBeforeLogging(JoinPoint pjp) throws Throwable {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        Object[] argNames = pjp.getArgs();

        LogELK logelk = new LogELK();

        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_REQ");
        logelk.setParameter(mapper.writeValueAsString(argNames));

        log.info("{}", mapper.writeValueAsString(logelk));
    }


    @AfterReturning(pointcut="execution(* *..external.*.*(..))", returning="retVal")
    public void serviceAfterReturningLogging(JoinPoint pjp, Object retVal) throws Throwable {

        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        LogELK logelk = new LogELK();

        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_RES");
        logelk.setParameter(mapper.writeValueAsString(retVal));

        log.info("{}", mapper.writeValueAsString(logelk));

    }
    @AfterThrowing(pointcut="execution(* *..external.*.*(..))", throwing = "exception")
    public void serviceAfterThrowingLogging(JoinPoint pjp,  Exception exception) throws Throwable {

        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        LogELK logelk = new LogELK();

        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_RES");
        logelk.setParameter(mapper.writeValueAsString(exception.getMessage()));

        log.info("{}", mapper.writeValueAsString(logelk));

    }

}
