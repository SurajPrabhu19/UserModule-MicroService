package com.codergy.userservice.ScalerImplementation.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.models.Role;
import com.codergy.userservice.ScalerImplementation.models.Session;
import com.codergy.userservice.ScalerImplementation.models.SessionStatus;
import com.codergy.userservice.ScalerImplementation.models.User;
import com.codergy.userservice.ScalerImplementation.repositories.SessionRepository;
import com.codergy.userservice.ScalerImplementation.repositories.UserRepository;
// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

// jwt
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.lang.Object;

@Service
public class AuthService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository,
            SessionRepository sessionRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDto> login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found, please sign up");
        }

        // Build token:
        // String token = RandomStringUtils.randomAlphanumeric(30);

        // "hello".getBytes()

        // String message = "{\n" +
        // " \"email\": \"naman@scaler.com\",\n" +
        // " \"roles\": [\n" +
        // " \"mentor\",\n" +
        // " \"ta\"\n" +
        // " ],\n" +
        // " \"expirationDate\": \"23rdOctober2023\"\n" +
        // "}";
        // // user_id
        // // user_email
        // // roles
        // byte[] content = message.getBytes(StandardCharsets.UTF_8);

        // Create the compact JWS:
        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createdAt", new Date());
        jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts.builder()
                .addClaims(jsonForJwt)
                .signWith(SECRET_KEY)
                .compact();

        // compact// Parse the compact JWS:
        // String content =
        // Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();
        // var content = Jwts.parserBuilder().ver(SECRET_KEY).

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE);
        // session.setExpireAt(token);
        sessionRepository.save(session);

        // Map<String, String> headers = new HashMap<>();
        // headers.put(HttpHeaders.SET_COOKIE, token);

        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+token);

        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<UserDto>(userDto, headers, HttpStatus.OK);

        return responseEntity;
    }

    public ResponseEntity<UserDto> logout(String token, Long userId) {
        return null;
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return UserDto.from(userRepository.save(user));
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> optionalSession = sessionRepository.findByToken(token);
    
        if(optionalSession.isEmpty())
        {
            return SessionStatus.EXPIRED;
        }

        Session session = optionalSession.get();
        if(!session.getStatus().equals(SessionStatus.ACTIVE))
        {
            return SessionStatus.EXPIRED;
        }

        Jws<Claims> claimsJws = Jwts.parserBuilder().build().parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        
        String email = (String)claims.get("email");
        List<Role> roles = (List<Role>) claims.get("roles");
        Date expiryDate = (Date) claims.get("expiryAt");
        Date createdDate = (Date) claims.get("createdAt");

        if(expiryDate.after(new Date()))
            return SessionStatus.EXPIRED;

        return SessionStatus.ACTIVE;
    }

}
