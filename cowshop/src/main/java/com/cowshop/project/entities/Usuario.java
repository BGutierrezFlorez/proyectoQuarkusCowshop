package com.cowshop.project.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false, nullable = false)
    public Long id;

    @Column(nullable = false, length = 100)
    public String nombre;

    @Column(nullable = false, unique = true, length = 20)
    public String cedula;

    @Column(nullable = false, unique = true, length = 150)
    public String correo;

    @Column(length = 20)
    public String celular;

    @Column(name = "tipo_usuario", nullable = false, length = 50)
    public String tipoUsuario;

    @Column(nullable = false)
    public String contrasena;

    public Object getNombre() {
        return null;
    }
}
