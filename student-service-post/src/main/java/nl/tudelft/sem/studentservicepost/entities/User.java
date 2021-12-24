package nl.tudelft.sem.studentservicepost.entities;

import java.util.List;

public interface User {

    String getNetID();

    String getName();

    Float getRating();

    List<Feedback> getFeedbacks();
}
