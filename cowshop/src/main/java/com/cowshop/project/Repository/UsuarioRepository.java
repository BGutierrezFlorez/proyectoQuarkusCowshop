package com.cowshop.project.Repository;

import com.cowshop.project.entities.Usuario;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

import java.util.List;

@ApplicationScoped
public class UsuarioRepository {

    @PersistenceContext
    EntityManager em;

    // 🔹 Registrar Usuario
    public boolean registrarUsuario(Usuario u) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("RegistrarUsuario");

        sp.registerStoredProcedureParameter(1, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(2, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(3, java.sql.Date.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(4, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(5, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(6, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(7, Integer.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(8, String.class, jakarta.persistence.ParameterMode.IN);
        sp.registerStoredProcedureParameter(9, Integer.class, jakarta.persistence.ParameterMode.IN);

        sp.setParameter(1, u.nombre);
        sp.setParameter(2, u.cedula);
        sp.setParameter(3, java.sql.Date.valueOf(u.fechaNacimiento));
        sp.setParameter(4, u.correo);
        sp.setParameter(5, u.celular);
        sp.setParameter(6, u.tipoUsuario);
        sp.setParameter(7, u.idMembresia);
        sp.setParameter(8, u.contrasena);
        sp.setParameter(9, u.idRol);

        sp.execute();
        return true;
    }

    // 🔹 Listar Usuarios
    @SuppressWarnings("unchecked")
    public List<Usuario> listarUsuarios() {
        return em.createNativeQuery("EXECUTE SP_LISTAR_USUARIO", Usuario.class)
                .getResultList();
    }

    // 🔹 Obtener por ID
    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerPorId(Long id) {
        return em.createNativeQuery("EXECUTE ObtenerUsuario :id", Usuario.class)
                .setParameter("id", id)
                .getResultList();
    }

    // 🔹 Eliminar
    public boolean eliminar(Long id) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("EliminarUsuario");
        sp.registerStoredProcedureParameter(1, Long.class, jakarta.persistence.ParameterMode.IN);
        sp.setParameter(1, id);
        sp.execute();
        return true;
    }

    // 🔹 Login
    public Usuario login(String correo, String contrasena) {
        List<Usuario> result = em.createNativeQuery(
                        "EXEC LoginUsuario :correo, :contrasena", Usuario.class)
                .setParameter("correo", correo)
                .setParameter("contrasena", contrasena)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
