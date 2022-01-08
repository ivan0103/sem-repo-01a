package nl.tudelft.sem.gateway.controllers;

import javax.validation.Valid;
import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.entities.CommunicationEntity;
import nl.tudelft.sem.gateway.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class GatewayController {

    private final transient GatewayService gatewayService;


    @Autowired
    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping
    public String greetAuthenticatedUsers() {
        return "HOORAY! You have successfully logged in!";
    }

    /**
     * Create a new authUser entity.
     *
     * @param communicationEntity entity used to transfer data
     * @return a new authUser entity
     * @throws IllegalArgumentException if one of the arguments is invalid
     */

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthUser> createAccount(@Valid @RequestBody
                                                  CommunicationEntity communicationEntity)
                                                  throws IllegalArgumentException {

        AuthUser authUser = gatewayService.createAccount(communicationEntity);
        return new ResponseEntity<>(authUser, HttpStatus.CREATED);
    }
}
