package github.taskmanagementapp.controller;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> loginWithCredentials(CredentialsDTO credentials)
    {

    }
}
