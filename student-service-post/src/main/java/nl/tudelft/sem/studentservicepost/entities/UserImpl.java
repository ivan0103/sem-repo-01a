package nl.tudelft.sem.studentservicepost.entities;

import java.util.List;
import java.util.Objects;

public class UserImpl implements User {

    private transient String netID;

    private transient String name;

    private transient Float rating;

    private transient List<Feedback> feedbacks;

    /**
     * Default constructor.
     */

    public UserImpl() {
    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
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

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserImpl user = (UserImpl) o;
        return Objects.equals(netID, user.netID) && Objects.equals(name, user.name)
                && Objects.equals(rating, user.rating) && Objects.equals(feedbacks, user.feedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, name, rating, feedbacks);
    }
}
