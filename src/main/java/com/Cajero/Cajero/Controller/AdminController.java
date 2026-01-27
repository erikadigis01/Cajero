package com.Cajero.Cajero.Controller;

import com.Cajero.Cajero.JPA.Banco;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
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
    public String perfilAdmin(@PathVariable("idUsuario") int idUsuario,
                                Model model, HttpServletRequest request,
                                RedirectAttributes redirectAttributes){
        
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
        
        ResponseEntity<Result<Banco>> responsebancos =
        restTemplate.exchange(url + "/bancos",
                              HttpMethod.GET,
                              entity,
                              new ParameterizedTypeReference<Result<Banco>>() {});
        
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                && responseCajeros.getStatusCode().is2xxSuccessful() && responseCajeros.getBody() != null
                && responsebancos.getStatusCode().is2xxSuccessful() && responsebancos.getBody() != null) {
            Usuario usuario = new Usuario();
            usuario = (Usuario) response.getBody().object;
            model.addAttribute("usuario", usuario);
            
            List<Cajero> cajeros = new ArrayList<>();
            cajeros = (List<Cajero>) responseCajeros.getBody().objects;
            model.addAttribute("cajeros", cajeros);
            
            
            List<Banco> bancos = new ArrayList();
            bancos = (List<Banco>) responsebancos.getBody().objects;
            
            model.addAttribute("bancos", bancos);
            model.addAttribute("cajero", new Cajero());
        } else {
        
            model.addAttribute("error", "no se encontro este usuario");
        }
        return "Admin";
    }
    
    @PostMapping("/rellenarCajero")
    public String rellenarCajero(@RequestParam("inputIdCajero") int IdCajero,
                                 @RequestParam("inputIdAdmin") int IdUser,
                                 Model model, RedirectAttributes redirect,
                                  HttpServletRequest request){
        
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirect.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> response =
            restTemplate.exchange(
                url + "cajeros/" + IdCajero,
                HttpMethod.POST,
                 entity,
                new ParameterizedTypeReference<Result>() {}
            );
        Result result = response.getBody();
        
        if(response.getStatusCode().value() == 201) {
            
            redirect.addFlashAttribute("success", "Se relleno correctamente el cajero");
        
        } else {
             redirect.addFlashAttribute("error", "No se relleno el cajero");
        }
        return "redirect:/admin/" + IdUser;
    }
    
    @PostMapping("/agregarCajero")
    public String addCajero(@ModelAttribute("cajero") Cajero cajero,
                            @RequestParam("idUsuario") int IdUser,
                            RedirectAttributes redirect, 
                            HttpServletRequest request){
        
        
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirect.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        HttpEntity<?> entity = new HttpEntity<>(cajero,headers);
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> response =
            restTemplate.exchange(
                url + "cajeros",
                HttpMethod.POST,
                 entity,
                new ParameterizedTypeReference<Result>() {}
            );
        Result result = response.getBody();
        
        if(response.getStatusCode().value() == 201) {
            
            redirect.addFlashAttribute("success", "Se relleno correctamente el cajero");
        
        } else {
             redirect.addFlashAttribute("error", "No se relleno el cajero");
        }
        return "redirect:/admin/" + IdUser;
    }
    
    @PostMapping("/deleteCajero")
    public String deleteCajero(@RequestParam("inputIdCajeroEliminar") int idCajero,
                                @RequestParam("idUsuario") int IdUser,
                                RedirectAttributes redirect,
                                HttpServletRequest request) {
        
        String token = getTokenFromCookie(request);
        if (token == null) {
            redirect.addAttribute("status", "Su sesión ha caducado");
            return "redirect:/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> response =
            restTemplate.exchange(
                url + "/cajeros/" + idCajero,
                HttpMethod.DELETE,
                 entity,
                new ParameterizedTypeReference<Result>() {}
            );
        Result result = response.getBody();
        
        if(response.getStatusCode().value() == 201) {
            
            redirect.addFlashAttribute("success", "Se elimino correctamente el cajero");
        
        } else {
             redirect.addFlashAttribute("error", "No se elimino el cajero");
        }
        return "redirect:/admin/" + IdUser;
    }
}
