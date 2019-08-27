package javagda25.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


    public static void main(String[] args) {

        StudentDao studentDao ;


        try{
            studentDao= new StudentDao();

        }catch (Exception e){
            System.err.println("Error"+e.getMessage());
            return;
        }


        try {

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

                        studentDao.insertStudent( student);

                        break;

                    case "remove":
                        System.out.println("Remove student");
                        System.out.println("Student id?:");
                        id = Integer.parseInt(sc.nextLine());
                        studentDao.removeStudent(id);
                        break;

                    case "list":
                        System.out.println("List students");

                        studentDao.listStudents().forEach(System.out::println);

                        break;

                    case "getid":
                        System.out.println("Get one student by id:");

                        System.out.println("Student id?:");
                        id = Integer.parseInt(sc.nextLine());
                        studentDao.getOneStudent(id);

                        break;

                    case "getname":
                        System.out.println("Get one student by name:");

                        System.out.println("Student name?:");
                        String name = (sc.nextLine());
                        studentDao.getOneStudentByName(name);

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





}
