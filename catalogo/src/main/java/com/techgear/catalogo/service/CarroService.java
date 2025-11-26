package com.techgear.catalogo.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.techgear.catalogo.model.Carro;
import com.techgear.catalogo.repository.CarroRepository;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    // MÉTODOS EXISTENTES
    public List<Carro> getCarros(){
        return carroRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    public List<Carro> getCarrosDetalles(){
        List<Carro> carros = carroRepository.findAll();
        for (Carro carro : carros) {
            try {
                String usuarioUrl = usuarioServiceUrl + "/" + carro.getUsuarioId();
                Map<String, Object> usuarioDetalles = restTemplate.getForObject(usuarioUrl, Map.class);
                carro.setUsuarioDetalles(usuarioDetalles);
            } catch (Exception e) {
                carro.setUsuarioDetalles(null);
            }
        }
        return carros;
    }

    public Carro getCarro(int id){
        return carroRepository.findById(id).orElse(null);
    }

    public Carro saveCarro(Carro carro){
        return carroRepository.save(carro);
    }

    public Carro updateCarro(Carro carro){
        Carro updcarro = getCarro(carro.getId());
        if (updcarro==null) {
            return null;
        }
        updcarro.setProducto(carro.getProducto());
        updcarro.setCantidad(carro.getCantidad());
        
        return carroRepository.save(updcarro);
    }

    public void deleteCarro(Integer id){
        carroRepository.deleteById(id);
    }

    // ----------------------------------------------------------------------
    // NUEVOS MÉTODOS DEL MICROSERVICIO DE CARRITO
    // ----------------------------------------------------------------------

    // 1. Carga inicial del carrito
    public List<Carro> getCarroByUserId(Integer userId) {
        return carroRepository.findByUsuarioId(userId);
    }

    // 2. Lógica para añadir/actualizar ítem (Sincronización POST)
    @Transactional
    public Carro saveOrUpdateItem(Carro nuevoCarroItem) {
        
        Integer userId = nuevoCarroItem.getUsuarioId();
        Integer productId = nuevoCarroItem.getProducto().getId(); 

        Carro itemExistente = carroRepository.findByUsuarioIdAndProductoId(userId, productId);
        
        if (itemExistente != null) {
            // Actualiza la cantidad y detalles
            itemExistente.setCantidad(nuevoCarroItem.getCantidad());
            itemExistente.setProducto(nuevoCarroItem.getProducto());
            return carroRepository.save(itemExistente);
        } else {
            // Crea un nuevo ítem
            return carroRepository.save(nuevoCarroItem);
        }
    }

    // 3. Eliminar un producto específico del carrito (DELETE /carro/item)
    @Transactional
    public void removeItemByProductIdAndUserId(Integer productoId, Integer userId) {
        carroRepository.deleteByUsuarioIdAndProductoId(userId, productoId);
    }

    // 4. Vaciar carrito completo (DELETE /carro/vaciar)
    @Transactional
    public void clearCartByUserId(Integer userId) {
        carroRepository.deleteByUsuarioId(userId);
    }
}