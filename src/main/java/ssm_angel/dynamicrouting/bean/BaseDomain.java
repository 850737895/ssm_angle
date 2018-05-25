package ssm_angel.dynamicrouting.bean;

import java.io.Serializable;

import ssm_angel.dynamicrouting.dbrouting.contextholder.DbContextHolder;


/**
 * 分库分表类
 * @author 85073
 *
 */
public class BaseDomain  implements Serializable {

    private String userNum;

    private String tableIndex;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getTableIndex() {
        this.tableIndex = DbContextHolder.getTableIndex();
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}
