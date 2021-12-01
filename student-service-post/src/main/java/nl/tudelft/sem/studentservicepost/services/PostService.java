package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Post service.
 */
@Service
public class PostService {

    @Autowired
    transient PostRepository postRepository;

    /**
     * Create post and save in database.
     *
     * @param post the post
     * @return the saved post
     */
    public Post createPost(Post post) {
        // TODO save in database, do whatever else needs to be done
        post.setId(null);
        Post returned = postRepository.save(post);
        System.out.println(returned);
        return returned;
    }
}
