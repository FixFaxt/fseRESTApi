package main.fseRESTApi.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.fseRESTApi.api.model.User;
import main.fseRESTApi.api.model.ValidatePasswordDto;
import main.fseRESTApi.api.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> createNewUser(@RequestBody User user) {
    if (user.getName() == null || user.getName().trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Name cannot be empty");
    }
    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Email cannot be empty");
    }
    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Password cannot be empty");
    }
    if (user.getPassword().length() < 8) {
      return ResponseEntity.badRequest().body("Password must be at least 8 characters long");
    }

    try {
      User savedUser = userService.createNewUser(user);
      return ResponseEntity.ok(savedUser);

    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/admin/allUsers")
  public Iterable<User> getUsers() {
    return this.userService.getUsers();
  }

  // Purely for Testing User password auth
  @PostMapping("/auth/validate")
  public boolean validatePassword(@RequestBody ValidatePasswordDto validatePasswordDto) {
    return this.userService.validatePassword(validatePasswordDto);
  }
}
