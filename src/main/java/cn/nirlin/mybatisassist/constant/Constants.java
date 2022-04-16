package cn.nirlin.mybatisassist.constant;

import java.util.List;

/**
 * @author nielin
 * @date 2022/1/26
 */
public class Constants {

    /**
     * sql分隔符
     */
    public static final String SQL_DELIMITER = "Preparing: ";

    /**
     * sql参数
     */
    public static final String SQL_PARAMETERS= "Parameters:";

    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    public static final String UPDATE = "UPDATE";
    public static final String SELECT = "SELECT";

    /**
     * SQL关键字段
     */
    public static final List<String> SQL_KEY_FIELD = List.of(INSERT, DELETE, UPDATE, SELECT);

    /**
     * sql日志最小行数
     */
    public static final Integer SQL_MIN_ROW = 2;

    /**
     * sql无效选择
     */
    public static final String SQL_INVALID_SELECTION = "SQL无效选择";

    /**
     * sql已复制
     */
    public static final String SQL_COPIED = "SQL已复制";


}
