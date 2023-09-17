package github.taskmanagementapp.controller;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.model.UserEntity;
import github.taskmanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        boolean userExists = userService.userExistsByUsername(credentialsDTO.getUsername());
        if(userExists) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDTO loggedUser = userService.getUserByCredentials(credentialsDTO);
            return new ResponseEntity<>(loggedUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO){
        boolean userExists = userService.userExistsByUsername(userDTO.getUsername());
        if(userExists)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        UserDTO registeredUser = userService.registerUser(userDTO);
        if(registeredUser != null)
            return new ResponseEntity<>(registeredUser,HttpStatus.OK);
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
