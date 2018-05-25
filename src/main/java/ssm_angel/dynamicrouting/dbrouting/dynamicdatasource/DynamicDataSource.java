package ssm_angel.dynamicrouting.dbrouting.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import ssm_angel.dynamicrouting.dbrouting.contextholder.DbContextHolder;

import java.util.logging.Logger;

/**
 * 动态数据源
 * @author 85073
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static final Logger logger = Logger.getLogger(DynamicDataSource.class.toString());
    @Override
    protected Object determineCurrentLookupKey() {
    	return DbContextHolder.getDbKey();//获取当前数据源
    }

}