package javagda25.sda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javagda25.sda.StudentQueries.*;


public class StudentDao {

    private MysqlConnection mysqlConnection;

    public StudentDao() {
        mysqlConnection = new MysqlConnection();
    }

    public void insertStudent(Student student1) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                statement.setString(1, student1.getName());
                statement.setInt(2, student1.getAge());
                statement.setDouble(3, student1.getAverage());
                statement.setBoolean(4, student1.getAlive());
                statement.execute();
            }
        }
    }

    public void removeStudent(int id) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {
                statement.setInt(1, id);
                statement.execute();

            }
        }
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
