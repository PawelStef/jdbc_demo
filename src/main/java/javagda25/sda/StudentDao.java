package javagda25.sda;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javagda25.sda.StudentQueries.*;


public class StudentDao {

    private MysqlConnection mysqlConnection;

    public StudentDao() throws IOException, SQLException {
        mysqlConnection = new MysqlConnection();
        createTableIfNotExist();
    }

    private void createTableIfNotExist() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {

                statement.execute();
            }
        }
    }


    public void insertStudent(Student student1) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, student1.getName());
                statement.setInt(2, student1.getAge());
                statement.setDouble(3, student1.getAverage());
                statement.setBoolean(4, student1.getAlive());

              //  boolean succes = statement.execute();
                int affectedRecord = statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    Long generatedId = resultSet.getLong(1);
                    System.out.println("Został utworzony rekort o id "+generatedId);
                }
            }
        }
    }

    public boolean removeStudent(int id) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {
                statement.setInt(1, id);
                int affectedRecord = statement.executeUpdate();

                if(affectedRecord >0){
                    System.out.println("Usunięto rekord o id "+id);
                    return true;
                }
            }
        }
        System.out.println("nic nie usunięto");
        return false;
    }


    public List<Student> listStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(LIST_QUERY)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Student student = insertStudentFromDB(resultSet);
                    studentList.add(student);
                }
            }
        }
        return studentList; //.forEach(System.out::println);
    }


    public Optional<Student> getOneStudent(int id) throws SQLException {
        Student student = new Student();

        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ONE_STUDENT_BY_ID_QUERY)) {
                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    student = insertStudentFromDB(resultSet);
                    System.out.println(student.toString());
                }
            }
        }

        Optional<Student> stoptional = Optional.ofNullable(student);
        return stoptional;
    }

    private Student insertStudentFromDB(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong(1));
        student.setName(resultSet.getString(2));
        student.setAge(resultSet.getInt(3));
        student.setAverage(resultSet.getDouble(4));
        student.setAlive(resultSet.getBoolean(5));
        return student;
    }


    public void getOneStudentByName(String name) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ONE_STUDENT_BY_NAME_QUERY)) {
                statement.setString(1, "%" + name + "%");

                ResultSet resultSet = statement.executeQuery();
                List<Student> studentList = new ArrayList<>();
                while (resultSet.next()) {
                    Student student = insertStudentFromDB(resultSet);

                    studentList.add(student);
                }
                studentList.forEach(System.out::println);
            }
        }
    }

}
