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
    public static final String SQL_DELIMITER = "Preparing:";

    /**
     * sql参数
     */
    public static final String SQL_PARAMETERS = "Parameters:";

    public static final String INSERT = "INSERT";
    public static final String DELETE = "DELETE";
    public static final String UPDATE = "UPDATE";
    public static final String SELECT = "SELECT";

    /**
     * SQL关键字段
     * 由于sql字段中可能存在关键字，例如：is_delete，导致切割出错，因此给关键字添加空格
     */
    public static final List<String> SQL_KEY_FIELD = List.of(INSERT + " ", DELETE + " ", UPDATE + " ", SELECT + " ");

    /**
     * sql日志最小行数
     */
    public static final Integer SQL_MIN_ROW = 2;

    /**
     * sql无效选择
     */
    public static final String SQL_INVALID_SELECTION = "SQL INVALID SELECTION";

    /**
     * sql已复制
     */
    public static final String SQL_COPIED = "SQL COPIED";


}
