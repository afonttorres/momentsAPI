package com.factoria.moments.auth.controller;


import com.factoria.moments.auth.configuration.JwtUtils;
import com.factoria.moments.auth.configuration.UserDetailsImpl;
import com.factoria.moments.models.Role;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;
import com.factoria.moments.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private final IAuthRepository authRepository;
    private final IRoleRepository roleRepository;
    private  final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            IAuthRepository authRepository,
            IRoleRepository roleRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?>  authenticateUser(@RequestBody LoginRequest loginReq){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest singUpReq){

//        if (this.someUserAlreadyExists()) {
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }


        if(authRepository.existsByUsername(singUpReq.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username already taken!"));
        }

        if(authRepository.existsByEmail(singUpReq.getEmail())){
            return  ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email already in use!"));
        }

        User user = new User(
                singUpReq.getUsername(),
                singUpReq.getEmail(),
                encoder.encode(singUpReq.getPassword()),
                singUpReq.getName()
        );

        Set<String> strRoles = singUpReq.getRole();
        Set<Role> roles = new HashSet<>();


        if(strRoles == null){
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Role is not found."));

            roles.add(userRole);
        }else{
            strRoles.forEach(role->{
                switch (role){
                    case "admin" :{
                        Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Role is not found."));
                        roles.add(adminRole);
                    }
                    default:{
                        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                                .orElseThrow(()-> new RuntimeException("Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        authRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    private boolean someUserAlreadyExists(){
//        return !authRepository.findAll().isEmpty();
//    }


}
