package br.com.willianantunes.support;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.UserDTO;
import br.com.willianantunes.services.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ScenarioBuilder {

    private static final String JSON_FILE_MOCKED_USERS = "mocked-users-docs.json";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public void createMockedUsersOnDatabase() throws IOException {

        userRepository.saveAll(getMockedUsers());
    }

    public List<User> getMockedUsers() throws IOException {

        return List.of(objectMapper.readValue(getMockedUsersAsJson(), User[].class));
    }

    public List<UserDTO> getMockedUserDTOs() throws IOException {

        return List.of(objectMapper.readValue(getMockedUsersAsJson(), User[].class)).stream()
            .map(userMapper::userToUserDTO)
            .collect(toList());
    }

    public String getMockedUsersAsJson() throws IOException {

        InputStream jsonFile = new ClassPathResource(JSON_FILE_MOCKED_USERS).getInputStream();
        return StreamUtils.copyToString(jsonFile, Charset.forName("UTF-8"));
    }

    public void clearAllRepositories() {

        userRepository.findAll().stream().forEach(userRepository::delete);
    }
}