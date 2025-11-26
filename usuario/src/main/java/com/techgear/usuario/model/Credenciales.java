package com.techgear.usuario.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, toString, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Credenciales {


    private String username; 
    
    private String password;
}