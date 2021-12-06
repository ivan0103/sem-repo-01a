package nl.tudelft.sem.studentservicepost.repositories;

import java.util.Collection;
import nl.tudelft.sem.studentservicepost.entities.Competency;
import nl.tudelft.sem.studentservicepost.entities.Expertise;
import nl.tudelft.sem.studentservicepost.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostById(Long id);

    Collection<Post> getAllByCompetencySetContaining(Competency competency);

    Collection<Post> getAllByExpertiseSetContaining(Expertise expertise);
}
