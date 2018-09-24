package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.UserDTO;
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

    public UserDTO createUser(UserDTO userDTO) {

        User user = userMapper.userDTOToUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

    public UserDTO updateUser(UserDTO userDTO) {

        User user = userMapper.userDTOToUser(userDTO);

        return userMapper.userToUserDTO(userRepository.save(user));
    }
}