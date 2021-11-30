package nl.tudelft.sem.users.services;

import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor of UserService - It instantiates a new UserService object.
     * @param userRepository repository injected with data from the database
     */

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
