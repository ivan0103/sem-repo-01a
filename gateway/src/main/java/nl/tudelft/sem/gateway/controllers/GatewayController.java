package nl.tudelft.sem.gateway.controllers;

import nl.tudelft.sem.gateway.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public Boolean isAuthenticated(String netID, String password) {
        return gatewayService.isAuthenticated(netID, password);
    }
}
