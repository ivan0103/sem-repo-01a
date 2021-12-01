package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.Post;
import org.springframework.stereotype.Service;

/**
 * The type Post service.
 */
@Service
public class PostService {

    /**
     * Create post post.
     *
     * @param post the post
     * @return the post
     */
    public Post createPost(Post post) {
        // TODO save in database, do whatever else needs to be done
        System.out.println(post);
        return post;
    }
}
