package com.Cajero.Cajero.Controller;


import com.Cajero.Cajero.DTO.RetiroDTO;
import com.Cajero.Cajero.JPA.Cajero;
import com.Cajero.Cajero.JPA.Result;
import com.Cajero.Cajero.JPA.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UserController {
    
    public String url = "http://localhost:8080/";
    
     private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    @GetMapping("/{idUsuario}")
    public String perfilUsuario(@PathVariable("idUsuario") int idUsuario,
                                Model model, RedirectAttributes redirectAttributes,
                                HttpServletRequest request){
        
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirectAttributes.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Usuario>> response =
        restTemplate.exchange(url + "/usuario/detail/" + idUsuario,
                              HttpMethod.GET,
                              entity,
                              new ParameterizedTypeReference<Result<Usuario>>() {});
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Usuario usuario = new Usuario();
            usuario = (Usuario) response.getBody().object;
            model.addAttribute("usuario", usuario);
        } else {
        
            model.addAttribute("error", "no se encontro este usuario");
        }

        
        return "UsuarioPerfil";
    }
    
    @GetMapping("/retirar/{idUsuario}")
    public String retirar(@PathVariable("idUsuario") int idUsuario,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request){
        
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirectAttributes.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        
        
        ResponseEntity<Result<Usuario>> response =
        restTemplate.exchange(url + "/usuario/detail/" + idUsuario,
                              HttpMethod.GET,
                              entity,
                              new ParameterizedTypeReference<Result<Usuario>>() {});
        
        
        ResponseEntity<Result<Cajero>> responseCajeros =
        restTemplate.exchange(url + "/cajeros",
                              HttpMethod.GET,
                              entity,
                              new ParameterizedTypeReference<Result<Cajero>>() {});
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
               && responseCajeros.getStatusCode().is2xxSuccessful() && responseCajeros.getBody() != null) {
                                    
            Usuario usuario = new Usuario();
            usuario = (Usuario) response.getBody().object;
            model.addAttribute("usuario", usuario);
            
            List<Cajero> cajeros = new ArrayList<>();
            cajeros = (List<Cajero>) responseCajeros.getBody().objects;
            model.addAttribute("cajeros", cajeros);
            
            RetiroDTO retiro = new RetiroDTO();
            long idU = idUsuario;
            retiro.setIdUser(idU);
            model.addAttribute("retiro", retiro);
        } else {
        
            model.addAttribute("error", "no se encontro este usuario");
        }
        return "Retiro";
    }
    
    @PostMapping("/retirar/{idUser}")
    public String retirarCantidad(@PathVariable("idUser") int idUser,
                                  @ModelAttribute("retiro") RetiroDTO retiro,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {
        long iduser = idUser;
        retiro.setIdUser(iduser);
//        -----RETIRO-----
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirectAttributes.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(retiro, headers);
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> response =
            restTemplate.exchange(
                url + "cajeros/retirar",
                HttpMethod.POST,
                 entity,
                new ParameterizedTypeReference<Result>() {}
            );
        Result result = response.getBody();
//        -----USUARIO------
        
        ResponseEntity<Result<Usuario>> responseUsuario =
        restTemplate.exchange(url + "/usuario/detail/" + idUser,
                              HttpMethod.GET,
                              entity,
                              new ParameterizedTypeReference<Result<Usuario>>() {});
        
        Usuario usuario = new Usuario();
            usuario = (Usuario) responseUsuario.getBody().object;
            
        if(response.getStatusCode().value() == 200) {
            model.addAttribute("status", "Correcto");
            model.addAttribute("usuario", usuario);
            model.addAttribute("cantidad", retiro.getCantidadRetiro());
            model.addAttribute("denominaciones", result.objects);
        } else {
        
            model.addAttribute("status", "Incorrecto");
            model.addAttribute("usuario", usuario);
            model.addAttribute("cantidad", retiro.getCantidadRetiro());
            model.addAttribute("error", result.errorMessage);
        }
        
        return "Result";
    }

}
