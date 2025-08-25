package com.sermaluc.prueba.apiuser.prueba.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Telefono {

    public Telefono() {
    }

    public Telefono(String numeroFono, String codCiudad, String codPais) {
        this.numeroFono = numeroFono;
        this.codCiudad = codCiudad;
        this.codPais = codPais;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 10, nullable = false)
    private String numeroFono;

    @Column(length = 5, nullable = false)
    private String codCiudad;

    @Column(length = 5, nullable = false)
    private String codPais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Relación con la tabla USUARIOS
    private User usuario;   

}
