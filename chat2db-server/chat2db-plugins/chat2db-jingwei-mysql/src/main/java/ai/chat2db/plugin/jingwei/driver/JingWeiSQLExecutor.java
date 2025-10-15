package ai.chat2db.plugin.jingwei.driver;

import ai.chat2db.plugin.jingwei.driver.catalog.CatalogInfo;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogsManager;
import ai.chat2db.plugin.jingwei.util.SqlExecUtil;
import ai.chat2db.spi.CommandExecutor;
import ai.chat2db.spi.model.Command;
import ai.chat2db.spi.model.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.List;

/**
 * @author duanfuqiang
 * @date 2025/10/15
 * SQL 执行器（单例）
 */
@Slf4j
public class JingWeiSQLExecutor implements CommandExecutor {

    /** 单例实例 */
    private static final JingWeiSQLExecutor INSTANCE = new JingWeiSQLExecutor();

    /** 私有构造函数，防止外部实例化 */
    private JingWeiSQLExecutor() {}

    /** 获取全局唯一实例 */
    public static JingWeiSQLExecutor getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ExecuteResult> execute(Command command) throws SQLException {
        if (command == null || StringUtils.isEmpty(command.getSchemaName())) {
            throw new RuntimeException("执行参数缺失");
        }

        String schemaName = command.getSchemaName();
        CatalogInfo catalogInfo = CatalogsManager.getCatalogInfo(schemaName);
        if (catalogInfo == null) {
            throw new RuntimeException("数据库信息获取失败");
        }

        ExecuteResult resultSet = SqlExecUtil.sqlExec(catalogInfo, command.getScript());
        return Collections.singletonList(resultSet);
    }

    @Override
    public ExecuteResult executeUpdate(String sql, Connection connection, int n) throws SQLException {
        throw new SQLFeatureNotSupportedException("更新操作不支持");
    }

    @Override
    public List<ExecuteResult> executeSelectTable(Command command) throws SQLException {
        return execute(command);
    }

    @Override
    public ExecuteResult execute(String sql, Connection connection, boolean limitRowSize, Integer offset, Integer count)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("功能未支持");
    }
}
