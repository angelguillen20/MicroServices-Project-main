package com.techgear.catalogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = true)
    private String imagen;

    @Column(nullable = true, length = 500)
    private String descripcion;

    // Solo para Pokéballs
    @Column(name = "tasa_captura", nullable = true)
    private Double tasaCaptura;

    // Solo para pociones
    @Column(nullable = true)
    private Integer curacion;

    // Solo para MTs
    @Column(nullable = true)
    private Integer potencia;

    @Column(nullable = true)
    private String tipo;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
