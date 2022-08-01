package five.agency.droolsdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kie.api.definition.rule.All;

@Data
@AllArgsConstructor
public class Person {

    private String firstName;
    private String lastName;
    private Address address;
    private int age;

    public Person changeAge(int age) {
        return new Person(firstName, lastName, address, age);
    }

}
