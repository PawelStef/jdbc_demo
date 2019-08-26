package javagda25.sda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
    private String name;
    private int age;
    private Double average;
    private Boolean alive;


    public Student(String name, int age, Double average, Boolean alive) {
        this.name = name;
        this.age = age;
        this.average = average;
        this.alive = alive;
    }
}
