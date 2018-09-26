package br.com.willianantunes.controllers;

import br.com.willianantunes.controllers.excep.BadRequestException;
import br.com.willianantunes.controllers.util.ResponseUtil;
import br.com.willianantunes.services.UserService;
import br.com.willianantunes.services.dtos.UserDTO;
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

@RestController
@RequestMapping(REQUEST_PATH_API)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(REQUEST_PATH_USER_GET_ALL)
    public List<UserDTO> users() {

        log.info("REST request to get all Users");

        return userService.getAllUsers();
    }

    @GetMapping(REQUEST_PATH_USER_GET_OR_DELETE)
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {

        log.info("REST request to get User : {}", id);

        Optional<UserDTO> user = userService.getUserById(id);

        return ResponseUtil.wrapOrNotFound(user);
    }

    @PostMapping(REQUEST_PATH_USER_POST_OR_PUT)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {

        log.info("REST request to save User : {}", user);

        UserDTO result = userService.createUser(user);

        return ResponseEntity.created(new UriTemplate(REQUEST_PATH_API + REQUEST_PATH_USER_GET_OR_DELETE).expand(result.getId())).body(result);
    }

    @PutMapping(REQUEST_PATH_USER_POST_OR_PUT)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO user) {

        log.info("REST request to update User : {}", user);

        Optional.ofNullable(user.getId()).orElseThrow(BadRequestException::new);
        UserDTO result = userService.updateUser(user);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(REQUEST_PATH_USER_GET_OR_DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {

        log.info("REST request to delete User : {}", id);

        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}