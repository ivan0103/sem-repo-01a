package nl.tudelft.sem.studentservicepost.services;

import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import nl.tudelft.sem.studentservicepost.exceptions.InvalidEditException;
import nl.tudelft.sem.studentservicepost.exceptions.PostNotFoundException;
import nl.tudelft.sem.studentservicepost.repositories.CompetencyRepository;
import nl.tudelft.sem.studentservicepost.repositories.ExpertiseRepository;
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

    @Autowired
    transient ExpertiseRepository expertiseRepository;

    @Autowired
    transient CompetencyRepository competencyRepository;

    /**
     * Create post and save in database.
     *
     * @param post the post
     * @return the saved post
     */
    public Post createPost(Post post) {
        post.setId(null);

        for (Expertise expertise : post.getExpertiseSet()) {
            if (expertiseRepository.existsById(expertise.getExpertiseString())) {
                Expertise tmp = expertiseRepository.getExpertiseByExpertiseString(
                    expertise.getExpertiseString());
                tmp.getPostSet().add(post);
                expertiseRepository.save(tmp);
            } else {
                expertiseRepository.save(expertise);
            }
        }

        for (Competency competency : post.getCompetencySet()) {
            if (competencyRepository.existsById(competency.getCompetencyString())) {
                Competency tmp = competencyRepository.getCompetencyByCompetencyString(
                    competency.getCompetencyString());
                tmp.getPostSet().add(post);
                competencyRepository.save(tmp);
            } else {
                competencyRepository.save(competency);
            }
        }

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
        // TODO check if the user can actually edit this post, related to authentication
        if (postRepository.existsById(post.getId())) {
            Post toEdit = postRepository.getPostById(post.getId());
            if (toEdit.getAuthor().equals(post.getAuthor())) {
                // this only checks that NetID is same
                return postRepository.save(post);
            } else {
                throw new InvalidEditException();
            }
        } else {
            throw new PostNotFoundException();
        }

    }
}
