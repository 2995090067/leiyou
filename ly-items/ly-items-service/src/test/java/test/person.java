package test;

import java.util.Arrays;

public class person {
    private int Age;
    private String Name;
    private String [] StringName;

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String[] getStringName() {
        return StringName;
    }

    public void setStringName(String[] stringName) {
        StringName = stringName;
    }

    @Override
    public String toString() {
        return "person{" +
                "Age=" + Age +
                ", Name='" + Name + '\'' +
                ", StringName=" + Arrays.toString(StringName) +
                '}';
    }
}
