package com.techgear.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techgear.catalogo.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro,Integer>{
    // Para 1: Obtener carrito de usuario
    List<Carro> findByUsuarioId(Integer userId);
    
    // Para 2: Encontrar un ítem específico antes de actualizar (opcional, si no usas saveOrUpdateItem)
    Carro findByUsuarioIdAndProductoId(Integer userId, Integer productoId);
    
    // Para 3: Eliminar un ítem específico (usando @Modifying y @Transactional)
    void deleteByUsuarioIdAndProductoId(Integer userId, Integer productoId);

    // Para 4: Vaciar el carrito completo (usando @Modifying y @Transactional)
    void deleteByUsuarioId(Integer userId);
}
