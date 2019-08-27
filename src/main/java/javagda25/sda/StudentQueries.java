package javagda25.sda;

public interface StudentQueries {

     String INSERT_QUERY = "INSERT INTO `students` (`name`, `age`, `average`, `alive`)\n" +
            "    VALUES\n" +
            "    ( ? , ? , ? , ? )";

     String REMOVE_QUERY = "DELETE FROM `students` WHERE `id` = ? ;";

     String LIST_QUERY = "SELECT * FROM jdbc_student.students;";

     String GET_ONE_STUDENT_BY_ID_QUERY = "SELECT * FROM jdbc_student.students WHERE `id` = ? ;";

     String GET_ONE_STUDENT_BY_NAME_QUERY = "SELECT * FROM jdbc_student.students WHERE `name` = ? ;";

     String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `students` (\n" +
            "    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "    `name` VARCHAR(255) NOT NULL,\n" +
            "    `age` INT NOT NULL,\n" +
            "    `average` DOUBLE NOT NULL,\n" +
            "    `alive` TINYINT NOT NULL);";
}
