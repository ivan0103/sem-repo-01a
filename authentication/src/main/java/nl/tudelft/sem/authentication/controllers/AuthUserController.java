package nl.tudelft.sem.authentication.controllers;

import javassist.NotFoundException;
import javax.validation.Valid;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.entities.CommunicationEntity;
import nl.tudelft.sem.authentication.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "authentication")
public class AuthUserController {

    private final transient AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    /**
     * Create a new authUser entity.
     *
     * @param communicationEntity entity used to transfer data
     * @return a new authUser entity
     */

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthUser> createAccount(@Valid @RequestBody
                                                  CommunicationEntity communicationEntity) {

        AuthUser authUser = authUserService.createAccount(communicationEntity);
        return new ResponseEntity<>(authUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves an authUser based on its netID.
     *
     * @param netID the netID of the user
     * @return the corresponding authUser entity
     * @throws NotFoundException if the authUser entity netID does not exist
     */

    @GetMapping(path = "/ADMIN")
    public ResponseEntity<AuthUser> retrieveAccount(@Valid @RequestParam String netID)
                                                    throws NotFoundException {

        AuthUser authUser = authUserService.getAccount(netID);
        return new ResponseEntity<>(authUser, HttpStatus.FOUND);
    }

    @GetMapping(path = "/isAuthenticated", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isAuthenticated(@Valid @RequestBody AuthUser authUser) {
        Boolean isAuthenticated = authUserService.isAuthenticated(authUser);
        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }
}
