package ssm_angel.dynamicrouting.dbrouting.routing.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;

import ssm_angel.dynamicrouting.bean.RouterSet;
import ssm_angel.dynamicrouting.dbrouting.annotation.RouterConstants;
import ssm_angel.dynamicrouting.dbrouting.contextholder.DbContextHolder;
import ssm_angel.dynamicrouting.dbrouting.routing.DBRouter;
import ssm_angel.dynamicrouting.dbrouting.routing.RouterUtils;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 
 * @author 85073
 *@Description 根据指定变量动态切 库和表
 */
public class DBRouterImpl implements DBRouter {

    private static final Logger log = LoggerFactory.getLogger(DBRouterImpl.class);

    /**
     * 配置列表
     */
    private List<RouterSet> routerSetList;

    @Override
    public String doRoute(String fieldId) {
        if (StringUtils.isEmpty(fieldId)) {
            throw new IllegalArgumentException("dbsCount and tablesCount must be both positive!");
        }
        int routeFieldInt = RouterUtils.getResourceCode(fieldId);
        String dbKey = getDbKey(routerSetList, routeFieldInt);
        return dbKey;
    }

    @Override
    public String doRouteByResource(String resourceCode) {
        if (StringUtils.isEmpty(resourceCode)) {
            throw new IllegalArgumentException("dbsCount and tablesCount must be both positive!");
        }
        int routeFieldInt = Integer.valueOf(resourceCode);
        String dbKey = getDbKey(routerSetList, routeFieldInt);
        return dbKey;
    }


    /**
     * 定位表和库
     * @param routerSets
     * @param routeFieldInt
     * @return
     */
    private String getDbKey(List<RouterSet> routerSets, int routeFieldInt) {
        RouterSet routerSet = null;
        if (routerSets == null || routerSets.size() <= 0) {
            throw new IllegalArgumentException("dbsCount and tablesCount must be both positive!");
        }
        String dbKey = null;
        for (RouterSet item : routerSets) {
            if (item.getRuleType() == routerSet.RULE_TYPE_STR) {
                routerSet = item;
                if (routerSet.getDbKeyArray() != null && routerSet.getDbNumber() != 0) {
                    long dbIndex = 0;
                    long tbIndex = 0;
                    //默认按照分库进行计算
                    long mode = routerSet.getDbNumber();
                    //如果是按照分库分表的话，计算
                    if (item.getRouteType() == RouterSet.ROUTER_TYPE_DBANDTABLE && item.getTableNumber() != 0) {
                        mode = routerSet.getDbNumber() * item.getTableNumber();
                        //库的取模运算索引值
                        dbIndex = routeFieldInt % mode / item.getTableNumber();
                        //表的取模运算
                        tbIndex = routeFieldInt % item.getTableNumber();
                        String tableIndex = getFormateTableIndex(item.getTableIndexStyle(), tbIndex);
                        DbContextHolder.setTableIndex(tableIndex);
                    } else if (item.getRouteType() == RouterSet.ROUTER_TYPE_DB) {
                        mode = routerSet.getDbNumber();
                        dbIndex = routeFieldInt % mode;
                    } else if (item.getRouteType() == RouterSet.ROUTER_TYPE_TABLE) { //若就按照分表的话，直接路由到一库
                        tbIndex = routeFieldInt % item.getTableNumber();
                        String tableIndex = getFormateTableIndex(item.getTableIndexStyle(), tbIndex);
                        DbContextHolder.setTableIndex(tableIndex);
                    }
                    dbKey = routerSet.getDbKeyArray().get(Long.valueOf(dbIndex).intValue());
                    log.debug("getDbKey resource:{}------->dbkey:{},tableIndex:{},", new Object[]{routeFieldInt, dbKey, tbIndex});
                    DbContextHolder.setDbKey(dbKey);
                }
                break;
            }
        }
        return dbKey;
    }


    /**
     * 拼接表后缀
     * @param style
     * @param tbIndex
     * @return
     */
    private static String getFormateTableIndex(String style, long tbIndex) {
        String tableIndex = null;
        DecimalFormat df = new DecimalFormat();
        if (StringUtils.isEmpty(style)) {
            style = RouterConstants.ROUTER_TABLE_SUFFIX_DEFAULT;//在格式后添加诸如单位等字符
        }
        df.applyPattern(style);
        tableIndex = df.format(tbIndex);
        return tableIndex;
    }

    public List<RouterSet> getRouterSetList() {
        return routerSetList;
    }

    public void setRouterSetList(List<RouterSet> routerSetList) {
        this.routerSetList = routerSetList;
    }
}
