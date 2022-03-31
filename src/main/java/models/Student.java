package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Student {
    public Long id;
    public String firstName;
    public String lastName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Student(@JsonProperty("id") Long id, @JsonProperty("firstName") String fName, @JsonProperty("lastName") String lName){
        this.id = id;
        firstName = fName;
        lastName = lName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return id + " " + firstName + " " +lastName;
    }
}
