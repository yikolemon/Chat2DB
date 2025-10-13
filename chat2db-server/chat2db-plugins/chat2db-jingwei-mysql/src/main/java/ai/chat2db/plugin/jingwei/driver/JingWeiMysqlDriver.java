package ai.chat2db.plugin.jingwei.driver;


import java.sql.SQLException;

public class JingWeiMysqlDriver extends RpcDriver {
    
    private static final String PROTOCOL = "jdbc:jingwei:";
    static {
        try {
            java.sql.DriverManager.registerDriver(new JingWeiMysqlDriver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    /**
     * Construct a new driver and register it with DriverManager
     *
     * @throws SQLException
     *             if a database error occurs.
     */
    public JingWeiMysqlDriver() throws SQLException {
        // Required for Class.forName().newInstance()
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url != null && url.startsWith(PROTOCOL);
    }
}
