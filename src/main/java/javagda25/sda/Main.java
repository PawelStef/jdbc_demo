package javagda25.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    //1.Tworzymy schema: jdbc_students
    //2.

    // CREATE TABLE `students` (
    //`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    //`name` VARCHAR(255) NOT NULL,
    //`age` INT NOT NULL,
    //`average` DOUBLE NOT NULL,
    //`alive` TINYINT NOT NULL
    //)

    /*

    INSERT INTO `students` (`name`, `age`, `average`, `alive`)
    VALUES
    ( ? , ? , ? , ? )

    */

    private static final String INSERT_QUERY = "INSERT INTO `students` (`name`, `age`, `average`, `alive`)\n" +
            "    VALUES\n" +
            "    ( ? , ? , ? , ? )";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `students` (\n" +
            "    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "    `name` VARCHAR(255) NOT NULL,\n" +
            "    `age` INT NOT NULL,\n" +
            "    `average` DOUBLE NOT NULL,\n" +
            "    `alive` TINYINT NOT NULL);";

    private static final String DB_HOST = "localhost"; //127.0.0.1
    private static final String DB_PORT = "3306"; //
    private static final String DB_USERNAME = "root"; //
    private static final String DB_PASSWORD = "root"; //
    private static final String DB_NAME = "jdbc_student"; //



    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
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


        try {
            Connection connection = dataSource.getConnection();
            System.out.println("Hurra");

            Student student1 = new Student( "Marian Koszałek", 23, 4.5, true);

            try(PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)){
                statement.execute();
            }


            try(PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)){
                statement.setString(1,student1.getName());
                statement.setInt(2,student1.getAge());
                statement.setDouble(3,student1.getAverage());
                statement.setBoolean(4,student1.getAlive());
                statement.execute();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
