package com.example.inflearnrestfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 -> String -> Convert -> int
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        final User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // 응답코드값 제어
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 가변 변수
                .buildAndExpand(savedUser.getId()) // 대입
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users")
    public ResponseEntity<User> editUser(@Valid @RequestBody User newUser) {
        User editedUser = service.editById(newUser);
        if (editedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", newUser.getId()));
        }

        // 응답코드값 제어
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 가변 변수
                .buildAndExpand(editedUser.getId()) // 대입
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
