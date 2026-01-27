package com.Cajero.Cajero.Controller;

import com.Cajero.Cajero.JPA.LoginRequest;
import com.Cajero.Cajero.JPA.Usuario;
import com.Cajero.Cajero.Security.JwtService;
import com.Cajero.Cajero.Service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    JwtService jwtUtil;
    
     public String url = "http://localhost:8080/";
    
    @GetMapping("/login")
    public String login(){
    
        return "Login";
    }
    
    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String email,
            @RequestParam String nip,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response) {

        RestTemplate restTemplate = new RestTemplate();
        LoginRequest loginReq = new LoginRequest(email, nip);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginReq, headers);

        try {
            ResponseEntity<Map> resp = restTemplate.exchange(
                    url + "auth/login",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map body = resp.getBody();
            if (body != null && body.containsKey("token")) {

                redirectAttributes.getFlashAttributes().clear();

                String token = (String) body.get("token");

                // Guardar cookie
                Cookie cookie = new Cookie("JWT_TOKEN", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);

                // Buscar usuario en BD
                Optional<Usuario> usuario = usuarioService.getByCuenta(email);
                
                Usuario user = usuario.orElse(null);

                if (user.roll.getIdRoll() == 1) { // ADMIN
                    return "redirect:/admin/" + user.getIdUsuario();
                } else {
                    return "redirect:/usuario/" + user.getIdUsuario();
                }
                
            } else {
                redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
                return "redirect:/login";
            }

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // expira inmediatamente
        response.addCookie(cookie);

        return "redirect:/login?logout=true";
    }
    
}
