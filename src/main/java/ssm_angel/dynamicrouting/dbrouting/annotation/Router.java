package ssm_angel.dynamicrouting.dbrouting.annotation;




import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由注解
 * @author 85073
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Router {

    String routerField() default RouterConstants.ROUTER_FIELD_DEFAULT;

    String tableStyle() default RouterConstants.ROUTER_TABLE_SUFFIX_DEFAULT;
}
