package com.techgear.usuario.controller;
import com.techgear.usuario.model.Credenciales; // Importar la clase de credenciales
import com.techgear.usuario.model.Usuario;
import com.techgear.usuario.service.UsuarioService; // Tu servicio para buscar usuarios
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth") // Base URL: http://localhost:8081/auth
@CrossOrigin(origins = "http://localhost:5173") 
public class AuthController {

    @Autowired
    private UsuarioService usuarioService; // Usaremos tu servicio para la lógica

    /**
     * Endpoint para manejar el inicio de sesión.
     * Mapea a: POST http://localhost:8081/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Credenciales credenciales) {
        
        String correoDelCliente = credenciales.getUsername();
        String contrasenaDelCliente = credenciales.getPassword();

        try {
            // 1. Buscar al usuario por el campo 'correo'
            Usuario usuarioEncontrado = usuarioService.findByCorreo(correoDelCliente); 

            // 2. Verificar si el usuario existe y si la contraseña es correcta
            // NOTA: 'checkPassword' DEBE usar BCryptPasswordEncoder para comparar.
            if (usuarioEncontrado == null || !usuarioService.checkPassword(contrasenaDelCliente, usuarioEncontrado.getContrasena())) {
                // Devolver 401 Unauthorized si las credenciales son inválidas
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Usuario o contraseña incorrectos")
                );
            }
            
            // 3. Autenticación exitosa (Aquí se generaría y devolvería un JWT)
            // Se devuelve el cuerpo JSON esperado por el frontend
            Map<String, Object> responseBody = Map.of(
                "username", usuarioEncontrado.getNombre(), 
                "email", usuarioEncontrado.getCorreo(),
                "token", "JWT_TOKEN_GENERADO_AQUI_12345", // Simulando la respuesta del token
                "id", usuarioEncontrado.getId()
            );
            
            return ResponseEntity.ok(responseBody);
            
        } catch (Exception e) {
            // Manejar errores de servidor (ej. error de base de datos)
            return ResponseEntity.internalServerError().body(
                Map.of("message", "Error interno del servidor durante la autenticación")
            );
        }
    }
}