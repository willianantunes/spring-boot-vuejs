package br.com.willianantunes.controllers;

import br.com.willianantunes.controllers.util.ResponseUtil;
import br.com.willianantunes.services.UserService;
import br.com.willianantunes.services.dtos.UserCreateDTO;
import br.com.willianantunes.services.dtos.UserDTO;
import br.com.willianantunes.services.dtos.UserUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static br.com.willianantunes.controllers.components.UrlBuilder.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(REQUEST_PATH_API)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = REQUEST_PATH_USER_GET_ALL, produces = APPLICATION_JSON_UTF8_VALUE)
    public List<UserDTO> users() {

        log.info("REST request to get all Users");

        return userService.getAllUsers();
    }

    @GetMapping(value = REQUEST_PATH_USER_GET_OR_DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> user(@PathVariable String id) {

        log.info("REST request to get User : {}", id);

        Optional<UserDTO> user = userService.getUserById(id);

        return ResponseUtil.wrapOrNotFound(user);
    }

    @PostMapping(value = REQUEST_PATH_USER_POST_OR_PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO user) {

        log.info("REST request to save User : {}", user);

        UserDTO result = userService.createUser(user);

        return ResponseEntity.created(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_USER_GET_OR_DELETE).expand(result.getId())).body(result);
    }

    @PutMapping(value = REQUEST_PATH_USER_POST_OR_PUT, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO user) {

        log.info("REST request to update User : {}", user);

        UserDTO result = userService.updateUser(user);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = REQUEST_PATH_USER_GET_OR_DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {

        log.info("REST request to delete User : {}", id);

        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}