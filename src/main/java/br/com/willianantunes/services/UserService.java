package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.UserCreateDTO;
import br.com.willianantunes.services.dtos.UserDTO;
import br.com.willianantunes.services.dtos.UserUpdateDTO;
import br.com.willianantunes.services.mappers.UserMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserCreateDTO userCreateDTO) {

        User user = userMapper.userCreateDTOToUser(userCreateDTO);

        user.setPassword(configurePassword(user.getPassword()));

        return userMapper.userToUserDTO(userRepository.save(user));
    }

    public void deleteUserByEmail(String email) {

        userRepository.findOneByEmail(email).ifPresent(u -> {

            userRepository.delete(u);
        });
    }

    public void deleteUserById(String id) {

        userRepository.findById(new ObjectId(id)).ifPresent(u -> {

            userRepository.delete(u);
        });
    }

    public Optional<UserDTO> getUserByEmail(String email) {

        return userRepository.findOneByEmail(email).map(userMapper::userToUserDTO);
    }

    public List<UserDTO> getAllUsers() {

        return userMapper.usersToUserDTOs(userRepository.findAll());
    }

    public Optional<UserDTO> getUserById(String id) {

        return userRepository.findById(new ObjectId(id)).map(userMapper::userToUserDTO);
    }

    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {

        User user = userMapper.userUpdateDTOToUser(userUpdateDTO);

        Optional.ofNullable(user.getPassword())
            .ifPresentOrElse(p -> user.setPassword(configurePassword(p)),
                () -> userRepository.findById(user.getId()).ifPresent(u -> user.setPassword(u.getPassword())));

        return userMapper.userToUserDTO(userRepository.save(user));
    }

    private String configurePassword(String password) {

        return passwordEncoder.encode(password);
    }
}