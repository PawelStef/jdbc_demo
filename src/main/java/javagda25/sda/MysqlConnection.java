package javagda25.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/*
    private static final String DB_HOST = "localhost"; //127.0.0.1
    private static final String DB_PORT = "3306"; //
    private static final String DB_USERNAME = "root"; //
    private static final String DB_PASSWORD = "root"; //
    private static final String DB_NAME = "jdbc_student"; //
*/


public class MysqlConnection {

    public MysqlConnection() throws IOException {
        parameters = new MysqlConnectionParameters();
        initialize();
    }

private MysqlConnectionParameters parameters;
    private MysqlDataSource dataSource;

    private void initialize(){

        dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(parameters.getDbPort()));
        dataSource.setUser(parameters.getDbUserName());
        dataSource.setServerName(parameters.getDbHost());
        dataSource.setDatabaseName(parameters.getDbName());
        dataSource.setPassword(parameters.getDbPassword());

        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


}