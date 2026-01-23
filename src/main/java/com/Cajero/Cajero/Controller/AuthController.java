package com.Cajero.Cajero.Controller;

import com.Cajero.Cajero.JPA.LoginRequest;
import com.Cajero.Cajero.Security.JwtService;
import com.Cajero.Cajero.Service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
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
    JwtService jwtService;
    
    @Autowired
    UsuarioService usuarioService;
    
     public String url = "http://localhost:8080/";
    
    @GetMapping("/login")
    public String login(){
    
        return "Login";
    }
    
    @PostMapping("/login")
    public String postLogin(@RequestParam("cuenta") String cuenta,
                            @RequestParam("nip") int nip,
                            RedirectAttributes redirectAttributes,
                            HttpServletResponse response){
        
        RestTemplate restTemplate = new RestTemplate();
        LoginRequest loginReq = new LoginRequest(cuenta, nip);
        
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
                String userEmail = jwtService.extraerUsername(token);

                // Guardar cookie
                Cookie cookie = new Cookie("JWT_TOKEN", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);

//                // Buscar usuario en BD
//                Long iduser = usuarioService.getUserIdByEmail(userEmail);
//                Usuario usuario = usuarioService.getById(iduser);

//                // Validar si la cuenta esta verificada
//                if (usuario.getIsVerified() == null || usuario.getIsVerified() == 0) {
//                    redirectAttributes.addFlashAttribute("error", "Cuenta no Verificada");
//                    redirectAttributes.addFlashAttribute("accountStatus", "UNVERIFIED");
//                    redirectAttributes.addFlashAttribute("email", email);
//                    return "redirect:login";
//
//                } else {
//                    // Redirigir según rol
//                    if (usuario.getRoll().getIdRoll() == 1) { // ADMIN
//                        return "redirect:/administrador/pokemons";
//                    } else {
//                        return "redirect:/pokedex";
//                    }
//                }

            } else {
                redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
                return "redirect:/login";
            }

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
            return "redirect:/login";
        }
        return "redirect:/login";
    }
}
