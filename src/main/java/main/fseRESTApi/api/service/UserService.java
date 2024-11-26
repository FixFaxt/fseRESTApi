package main.fseRESTApi.api.service;

// import java.util.Optional;
// import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.fseRESTApi.api.model.User;
import main.fseRESTApi.api.model.UserRoles;
import main.fseRESTApi.api.model.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Iterable<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createNewUser(User user) {
    try {
      String encryptedPassword = passwordEncoder.encode(user.getPassword());
      User userWithEncryptedPassword = new User(user.getName(), user.getEmail(), encryptedPassword,
          (UserRoles) user.getRole());
      User newUser = this.userRepository.save(userWithEncryptedPassword);
      return newUser;

    } catch (IllegalArgumentException ex) {
      System.out.println("Invalid argument: " + ex.getMessage());
      throw ex;

    } catch (Exception ex) {
      System.out.println("An unexpected error occurred: " + ex.getMessage());
      throw new RuntimeException("Failed to save user.", ex);
    }
  }
}
