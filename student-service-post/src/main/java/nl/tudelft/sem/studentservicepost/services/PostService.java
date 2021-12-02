package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
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
        post.setId(null);
        Post returned = postRepository.save(post);
        System.out.println(returned);
        return returned;
    }

    /**
     * Edit post.
     *
     * @param post the post
     * @return the post
     * @throws PostNotFoundException id the post is not found in the database.
     */
    public Post editPost(Post post) {
        // TODO change the value in the database
        // TODO check if the user can actually edit this post, related to authentication
        if (postRepository.existsById(post.getId())) {
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException();
        }

    }
}
