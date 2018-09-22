package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.UserDTO;
import br.com.willianantunes.services.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserDTO userDTO) {

        User user = userMapper.userDTOToUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
