package javagda25.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnection {


    public MysqlConnection() {
        initialize();
    }

    private static final String DB_HOST = "localhost"; //127.0.0.1
    private static final String DB_PORT = "3306"; //
    private static final String DB_USERNAME = "root"; //
    private static final String DB_PASSWORD = "root"; //
    private static final String DB_NAME = "jdbc_student"; //

    private MysqlDataSource dataSource;

    private void initialize(){

        dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(DB_PORT));
        dataSource.setUser(DB_USERNAME);
        dataSource.setServerName(DB_HOST);
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setPassword(DB_PASSWORD);

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