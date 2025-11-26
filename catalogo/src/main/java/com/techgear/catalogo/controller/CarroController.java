package com.techgear.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techgear.catalogo.model.Carro;
import com.techgear.catalogo.service.CarroService;


@RestController
@RequestMapping("/carro")
@CrossOrigin(origins = "http://localhost:5173") 
public class CarroController {

    @Autowired
    private CarroService carroService;


    /**
     * 1. GET /carro/{userId} - Carga inicial del carrito
     * Esto reemplaza al GET /carro/{id} genérico.
     * El frontend llama a: http://localhost:8082/carro/1
     */
    @GetMapping("/{userId}")
    // @Operation(summary="Obtener carrito de un usuario", description = "Obtiene todos los ítems del carrito para un ID de usuario")
    public ResponseEntity<List<Carro>> obtenerCarroPorUsuario(@PathVariable("userId") Integer userId) {
        try {
            // Se asume que CarroService tiene este nuevo método
            List<Carro> items = carroService.getCarroByUserId(userId);
            
            if (items.isEmpty()) {
                return ResponseEntity.noContent().build(); // Devuelve 204 (No Content)
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 2. POST /carro/item - Añadir o actualizar un ítem
     * El frontend llama a: http://localhost:8082/carro/item
     * El servicio debe manejar la lógica de "si existe, actualiza; si no, crea".
     */
    @PostMapping("/item")
    // @Operation(summary="Sincronizar ítem", description = "Añade un producto al carrito o actualiza su cantidad")
    public ResponseEntity<Carro> sincronizarItemCarro(@RequestBody Carro carro) {
        try {
            // Se asume que CarroService tiene un método para guardar o actualizar
            Carro savedCarro = carroService.saveOrUpdateItem(carro);
            return ResponseEntity.ok(savedCarro);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 3. DELETE /carro/item/{productId}/{userId} - Eliminar un ítem por producto y usuario
     * El frontend llama a: http://localhost:8082/carro/item/5/1
     */
    @DeleteMapping("/item/{productoId}/{userId}")
    // @Operation(summary="Eliminar ítem del carrito", description = "Elimina un producto específico del carrito del usuario")
    public ResponseEntity<Void> eliminarItemCarro(
        @PathVariable("productoId") Integer productoId, 
        @PathVariable("userId") Integer userId
    ) {
        try {
            // Se asume que CarroService tiene un nuevo método de eliminación por IDs
            carroService.removeItemByProductIdAndUserId(productoId, userId);
            return ResponseEntity.noContent().build(); // Devuelve 204 (No Content)
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/vaciar/{userId}")
    public ResponseEntity<Void> vaciarCarro(@PathVariable("userId") Integer userId) {
        try {
            carroService.clearCartByUserId(userId);
            return ResponseEntity.noContent().build(); // Devuelve 204 (No Content)
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}