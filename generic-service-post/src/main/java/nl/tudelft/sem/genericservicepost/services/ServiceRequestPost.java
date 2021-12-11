package nl.tudelft.sem.genericservicepost.services;

import nl.tudelft.sem.genericservicepost.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Issue #15
 * The system can also allow the companies to post a request for services without targeting specific students
 */
@Service
public class ServiceRequestPost {
    @Autowired
    transient PostRepository postRepository;


}
