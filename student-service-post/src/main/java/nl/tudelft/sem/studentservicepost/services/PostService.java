package nl.tudelft.sem.studentservicepost.services;

import java.util.Collection;
import java.util.HashSet;
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

    /**
     * The Post repository.
     */
    @Autowired
    transient PostRepository postRepository;

    /**
     * The Expertise repository.
     */
    @Autowired
    transient ExpertiseRepository expertiseRepository;

    /**
     * The Competency repository.
     */
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
     * @param post   the post
     * @param postId the post id
     * @return the post
     * @throws PostNotFoundException id the post is not found in the database.
     */
    public Post editPost(Post post, String postId) {
        // TODO check if the user can actually edit this post, related to authentication
        long id;
        try {
            id = Long.parseLong(postId);

            if (postRepository.existsById(id)) {
                Post toEdit = postRepository.getPostById(id);

                // this only checks that NetID is same
                if (toEdit.getAuthor().equals(post.getAuthor())) {
                    post.setId(toEdit.getId());
                    return postRepository.save(post);
                } else {
                    throw new InvalidEditException();
                }
            } else {
                throw new PostNotFoundException();
            }

        } catch (NumberFormatException e) {
            throw new PostNotFoundException();
        }
    }

    /**
     * Gets by id.
     *
     * @param postId the post id
     * @return the by id
     */
    public Post getById(String postId) {
        long id;

        try {
            id = Long.parseLong(postId);
            if (postRepository.existsById(id)) {
                return postRepository.getPostById(id);
            } else {
                throw new PostNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new PostNotFoundException();
        }
    }

    /**
     * Search by keyword.
     *
     * @param keyword the keyword
     * @return the collection
     */
    public Collection<Post> searchByKeyword(String keyword) {
        keyword = Competency.makeSearchable(keyword);
        Collection<Competency> competencies =
            competencyRepository.getAllBySearchableStringContaining(keyword);

        Collection<Post> result = new HashSet<>();

        for (Competency competency : competencies) {
            result.addAll(postRepository.getAllByCompetencySetContaining(competency));
        }

        Collection<Expertise> expertises =
            expertiseRepository.getAllBySearchableStringContaining(keyword);
        for (Expertise expertise : expertises) {
            result.addAll(postRepository.getAllByExpertiseSetContaining(expertise));
        }

        for (Post post : result) {
            post.setCompanyOfferSet(null);
        }
        return result;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public Collection<Post> getAll() {
        return postRepository.findAll();
    }
}
