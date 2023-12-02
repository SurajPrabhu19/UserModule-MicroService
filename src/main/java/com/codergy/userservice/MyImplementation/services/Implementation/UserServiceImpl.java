package com.codergy.userservice.services.Implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codergy.userservice.dtos.UserRequestDto;
import com.codergy.userservice.dtos.UserResponseDto;
import com.codergy.userservice.models.Session;
import com.codergy.userservice.models.User;
import com.codergy.userservice.repositories.SessionRepository;
import com.codergy.userservice.repositories.UserRepository;
import com.codergy.userservice.services.UserService;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private HashSet<User> userCache = new HashSet<>();

    public UserServiceImpl(UserRepository userRepository, SessionRepository sessionRepository,
            ModelMapper modelMapper) {
        super();
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    // STATIC Methods:
    public static Session createNewSession(User user) {
        // if(get the session count == 5)
        // {
        // // remove an existing session
        // }
        // add a new session
        Session newSession = new Session();
        newSession.setToken(UUID.randomUUID().toString());
        newSession.setUser(user);

        return newSession;
    }

    public void addUserToCache(User user) {
        if (userCache.size() > 5)
            userCache.iterator().remove();

        userCache.add(user);
    }

    // OVERRIDEN Methods:
    @Override
    public UserResponseDto signUpUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        // ArrayList<Session> newSession = new
        // ArrayList<>(Arrays.asList(createNewSession(user)));
        // user.setSession(newSession);

        user = userRepository.save(user);

        addUserToCache(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        // userResponseDto.setSession(newSession);

        return userResponseDto;
    }

    @Override
    public HttpStatus loginUser(UserRequestDto userDetails) throws Exception {

        // if(userCache.contains(user))
        // ArrayList<Session> newSession = new
        // ArrayList<>(Arrays.asList(createNewSession(user)));
        // user.setSession(newSession);

        User user = userRepository.findByEmail(userDetails.getEmail());
        if (user == null)
            throw new Exception("User not Found");

        if (!user.getPassword().equals(userDetails.getPassword()))
            throw new Exception("Incorrect Password");

        List<Session> sessions = sessionRepository.findByUser_Id(user.getId());
        if (sessions.size() >= 5) {
            sessions.forEach((session) -> logger.info(session.getId() + ""));
            throw new Exception("Maximum User Sessions reached, please log out through other sessions");
            // sessionRepository.deleteById(sessions.get(0).getId());
            // logger.info(sessions.get(0).getId()+"");
        }
        logger.info("This is an info message");
        sessionRepository.save(createNewSession(user));

        // logger.info(sessions+"");
        logger.info(sessions.size() + "");

        return HttpStatus.OK;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDto logoutUser(UUID id) throws Exception {
        // Optional<Session> session = sessionRepository.findById(id);
        // if(session.isEmpty()) throw new Exception("User session does not exists!!!");

        // Optional<User> user =
        // userRepository.findById(session.get().getUser().getId());
        // Session session = sessionRepository.findById(id)
        //         .orElseThrow(() -> new Exception("User session does not exist!!!"));

        // User user = userRepository.findById(session.getUser().getId())
        //         .orElseThrow(() -> new Exception("User not found for the given session"));

        sessionRepository.deleteBySessionUUID(id+"");

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new Exception("User sucessfully deleted"));
        logger.info(session.getId()+"");
        return null;
        // if (user != null) {
        //     return modelMapper.map(user, UserResponseDto.class);
        // } else {
        //     throw new Exception("User is null for the given session");
        // }
    }

}
