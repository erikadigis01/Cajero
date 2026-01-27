package com.Cajero.Cajero.RestController;

import com.Cajero.Cajero.JPA.LoginRequest;
import com.Cajero.Cajero.Security.JwtService;
import com.Cajero.Cajero.Security.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired 
    private JwtService jwtUtil;

    @Autowired
    private UserService usuarioService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCuenta(), loginRequest.getNip())
            );

            final UserDetails userDetails = usuarioService.loadUserByUsername(loginRequest.getCuenta());
            final String token = jwtUtil.creatToken(userDetails);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("status", "success");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Email o contraseña incorrectos");
            return ResponseEntity.ok(response);
        }
    }

}
