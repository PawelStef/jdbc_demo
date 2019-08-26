package javagda25.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //1.Tworzymy schema: jdbc_students
    //2.

    // CREATE TABLE  IF NOT EXISTS `students` (
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

    private static final String REMOVE_QUERY = "DELETE FROM `students` WHERE `id` = ? ;";

    private static final String LIST_QUERY = "SELECT * FROM jdbc_student.students;";

    private static final String GET_ONE_STUDENT_BY_ID_QUERY = "SELECT * FROM jdbc_student.students WHERE `id` = ? ;";

    private static final String GET_ONE_STUDENT_BY_NAME_QUERY = "SELECT * FROM jdbc_student.students WHERE `name` = ? ;";

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

            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }

            Scanner sc = new Scanner(System.in);

            System.out.println("Program do operacjach na danych w bazie");

            boolean quit = false;

            String menuItem;

            do {
                System.out.println("Możliwe opcje:");
                System.out.println("-add-  -remove-  -list-  -getId-  -getName-  -edit  -quit-");
                int id;
                menuItem = sc.nextLine().toLowerCase();

                switch (menuItem) {

                    case "add":

                        System.out.println("Add student:");
                        Student student = new Student();
                        System.out.println("Student name:");
                        student.setName(sc.nextLine());
                        System.out.println("Student age:");
                        student.setAge(Integer.parseInt(sc.nextLine()));
                        System.out.println("Student average:");
                        student.setAverage(Double.parseDouble(sc.nextLine()));
                        System.out.println("Student alive?:");
                        student.setAlive(Boolean.parseBoolean(sc.nextLine()));

                        insertStudent(connection, student);

                        break;

                    case "remove":
                        System.out.println("Remove student");
                        System.out.println("Student id?:");
                        id = Integer.parseInt(sc.nextLine());
                        removeStudent(connection, id);
                        break;

                    case "list":
                        System.out.println("List students");

                        listStudents(connection);

                        break;

                    case "getid":
                        System.out.println("Get one student by id:");

                        System.out.println("Student id?:");
                        id = Integer.parseInt(sc.nextLine());
                        getOneStudent(connection, id);

                        break;

                    case "getname":
                        System.out.println("Get one student by name:");

                        System.out.println("Student name?:");
                        String name = (sc.nextLine());
                        getOneStudentByName(connection, name);

                        break;

                    case "edit":

                        System.out.println("Edit student");

                        break;

                    case "quit":

                        System.out.println("You've chosen qiut ");

                        quit = true;
                        break;

                    default:

                        System.out.println("Invalid choice.");
                }

            } while (!quit);

            System.out.println("Bye-bye!");


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void getOneStudentByName(Connection connection, String name)  throws SQLException{


        try (PreparedStatement statement = connection.prepareStatement(GET_ONE_STUDENT_BY_NAME_QUERY)) {
            statement.setString(1, "%"+name+"%");

            ResultSet resultSet = statement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));

                studentList.add(student);
            }
            studentList.forEach(System.out::println);
        }
    }

    private static void getOneStudent(Connection connection, int id) throws SQLException {


        try (PreparedStatement statement = connection.prepareStatement(GET_ONE_STUDENT_BY_ID_QUERY)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));
                System.out.println( student.toString());
            }


            }
    }

    private static void listStudents(Connection connection) throws SQLException {

        List<Student> studentList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(LIST_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));

                studentList.add(student);

            }

        }

        studentList.forEach(System.out::println);

    }

    private static void removeStudent(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {
            statement.setInt(1, id);
            statement.execute();

        }
    }

    private static void insertStudent(Connection connection, Student student1) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, student1.getName());
            statement.setInt(2, student1.getAge());
            statement.setDouble(3, student1.getAverage());
            statement.setBoolean(4, student1.getAlive());

            statement.execute();

        }
    }
}
