package nl.tudelft.sem.authentication.controllers;

import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "authentication")
public class AuthUserController {

    private final transient AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping(path = "{netID}/{name}/{password}/{role}")
    public AuthUser createAccount(@PathVariable(value = "netID") String netID,
                                  @PathVariable(value = "name") String name,
                                  @PathVariable(value = "password") String password,
                                  @PathVariable(value = "role") String role) {

        return authUserService.createAccount(netID, name, password, role);
    }

    @GetMapping(path = "{netID}/{password}")
    public AuthUser getAuthUser(@PathVariable(value = "netID") String netID,
                                @PathVariable(value = "password") String password) {

        return authUserService.getAuthUser(netID, password);
    }
}
