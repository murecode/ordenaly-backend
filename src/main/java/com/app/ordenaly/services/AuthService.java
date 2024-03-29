package com.app.ordenaly.services;

import com.app.ordenaly.dto.AuthRequest;
import com.app.ordenaly.dto.AuthResponse;
import com.app.ordenaly.dto.SignUpRequest;
import com.app.ordenaly.models.User;
import com.app.ordenaly.repositories.UserRepository;
import com.app.ordenaly.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {
  @Autowired
  AuthenticationManager authManager;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserRepository userRepository;
  @Autowired
  JwtService jwtService;

  @Transactional
  public void signup(SignUpRequest registerRequest) {
    User user = new User();
    user.setFullname(registerRequest.getFullname());
    user.setUsername(registerRequest.getUsername());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setRole(Roles.STAFF);
    userRepository.save( user );
  }

  public AuthResponse login(AuthRequest authRequest) {
    //1
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (
            authRequest.getUsername(), authRequest.getPassword()
    );
    //2
    authManager.authenticate( authToken );

    //3
    User user = userRepository.findByUsername(authRequest.getUsername()).get();

    //4
    String jwt = jwtService.generateToken(user, generateExtraClaims(user));
    return new AuthResponse(jwt);

  }

  private Map<String, Object> generateExtraClaims(User user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("name", user.getFullname());
    extraClaims.put("role", user.getRole());
    extraClaims.put("permissions", user.getAuthorities());
    return extraClaims;
  }

}

//1. Crea un objeto llamado "authToken" que representea la info del usuario (username, password)
//2. Realiza la autenticación utilizando la información proporcionada en "authToken".
//3. Busca el username en la base de datos
//4. LLama al servicio encargado de generar el JWT crear una respuesta de autenticación "AuthResponse"

