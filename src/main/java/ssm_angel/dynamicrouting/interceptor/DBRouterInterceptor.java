package ssm_angel.dynamicrouting.interceptor;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ssm_angel.dynamicrouting.dbrouting.annotation.Router;
import ssm_angel.dynamicrouting.dbrouting.annotation.RouterConstants;
import ssm_angel.dynamicrouting.dbrouting.routing.DBRouter;
import ssm_angel.dynamicrouting.dbrouting.routing.RouterUtils;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * @Description 切面切点 在Router注解的方法执行前执行 切点织入
 * 
 */
@Aspect
@Component
public class DBRouterInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DBRouterInterceptor.class);
    
    @Resource
    private DBRouter dBRouter;

    @Pointcut("@annotation( ssm_angel.dynamicrouting.dbrouting.annotation.Router)")
    public void aopPoint() {
    }
    
    /**
     * 切面:执行service上  有router的注解的方法。
     * @param jp
     * @return
     * @throws Throwable
     */
    @Before("aopPoint()")
    public Object doRoute(JoinPoint jp) throws Throwable {
        long t1 = System.currentTimeMillis();
        boolean result = true;
        Method method = getMethod(jp);
        //获取执行目标方法上的注解对象
        Router router = method.getAnnotation(Router.class);
        //获取注解上的路由标识字段   ROUTER_FIELD_DEFAULT userNum 来分库分表
        String routeField = router.routerField();
        //获取目标方法中的入参
        Object[] args = jp.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                long t2 = System.currentTimeMillis();
                //获取  入参中的userNum 字段 对应的value值
                String routeFieldValue = BeanUtils.getProperty(args[i],routeField);
                log.debug("routeFieldValue{}" + (System.currentTimeMillis() - t2));
                
                if (StringUtils.isNotEmpty(routeFieldValue)) {
                	//默认的入参中分表分库标示是否为默认的  【userNum】
                    if (RouterConstants.ROUTER_FIELD_DEFAULT.equals(routeField)) {
                        dBRouter.doRouteByResource("" + RouterUtils.getResourceCode(routeFieldValue));
                        break;
                    } else {//不是根据默认分库分表的默认标示【userNum】
                        this.searchParamCheck(routeFieldValue);
                        String resource = routeFieldValue.substring(routeFieldValue.length() - 4);
                        dBRouter.doRouteByResource(resource);
                        break;
                    }
                }
            }
        }
        log.debug("doRouteTime{}" + (System.currentTimeMillis() - t1));
        return result;
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        return getClass(jp).getMethod(msig.getName(), msig.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp)
            throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }


    /**
     * 查询支付结构参数检查
     *
     * @param payId
     */
    private void searchParamCheck(String payId) {
        if (payId.trim().equals("")) {
            throw new IllegalArgumentException("payId is empty");
        }
    }

    public DBRouter getdBRouter() {
        return dBRouter;
    }

    public void setdBRouter(DBRouter dBRouter) {
        this.dBRouter = dBRouter;
    }

}
