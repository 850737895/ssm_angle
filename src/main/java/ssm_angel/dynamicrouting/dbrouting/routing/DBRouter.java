package ssm_angel.dynamicrouting.dbrouting.routing;


/**
 * 路由接口类
 * @author 85073
 *
 */
public interface DBRouter {
	/**
	 * 进行路由
	 * @param fieldId
	 * @return
	 * @throws
	 */
    public String doRoute(String fieldId);


    public String doRouteByResource(String resourceCode);
}
