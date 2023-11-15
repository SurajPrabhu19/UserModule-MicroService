import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codergy.userservice.dtos.UserRequestDto;
import com.codergy.userservice.dtos.UserResponseDto;
import com.codergy.userservice.services.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }
    
    // get mapping:
    // /

    // post mapping:

    // /users - sign up the user by storing data into the database:
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> signUpUser(@RequestBody UserRequestDto userDetails)
    {
        
        return userService.signUpUser(userDetails);;
    }

    // /login - login to the application by validating the token in session table

    // /logout - logout - removes or invalidates the session for user in the session tables

}
