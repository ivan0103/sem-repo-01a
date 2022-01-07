package nl.tudelft.sem.genericservicepost.entities;

import java.util.Objects;

public class Student {

    private String role;

    private String netId;

    private String name;

    private Float rating;

    protected Student(String netId, String name, Float rating) {
        this.role = "student";
        this.netId = netId;
        this.name = name;
        this.rating = rating;
    }

    protected Student(){
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(role, student.role) && Objects.equals(netId, student.netId) && Objects.equals(name, student.name) && Objects.equals(rating, student.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, netId, name, rating);
    }

    @Override
    public String toString() {
        return "Student{" +
                ", netId='" + netId + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
