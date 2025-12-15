package com.cowshop.project.resources;

import com.cowshop.data.UsuarioData;
import com.cowshop.helpers.JwtHelper;
import com.cowshop.project.entities.Usuario;
import com.cowshop.project.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/usuarios/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticación")
public class LoginResource {

    @OPTIONS
    public Response optionsLogin() {
        return Response.ok().build();
    }

    @POST
    @Operation(summary = "Login de usuario")
    @APIResponse(responseCode = "200", description = "Login exitoso")
    @APIResponse(responseCode = "401", description = "Credenciales inválidas")
    public Response login(UsuarioLoginRequest request) {

        if (request == null ||
                request.getCorreo() == null ||
                request.getContrasena() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Correo y contraseña son obligatorios")
                    .build();
        }

        var ver = UsuarioData.verificarLogin(
                request.getCorreo(),
                request.getContrasena()
        );

        if (ver == UsuarioData.VerificacionLoginResultado.NoExisteCorreo) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Correo no registrado")
                    .build();
        }

        if (ver == UsuarioData.VerificacionLoginResultado.ContrasenaIncorrecta) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Contraseña incorrecta")
                    .build();
        }

        Usuario usuario = UsuarioData.login(
                request.getCorreo(),
                request.getContrasena()
        );

        if (usuario == null) {
            return Response.serverError()
                    .entity("Error al obtener usuario")
                    .build();
        }

        String token = JwtHelper.generarToken(
                usuario.getID_Usuario(),
                usuario.getTipo_Usuario()
        );

        return Response.ok(
                new LoginResponse(token, usuario)
        ).build();
    }

    /* DTO interno de respuesta */
    public record LoginResponse(String token, UsuarioDTO usuario) {
        public LoginResponse(String token, Usuario u) {
            this(token,
                    new UsuarioDTO(
                            u.getID_Usuario(),
                            u.getNombre(),
                            u.getTipo_Usuario()
                    )
            );
        }
    }

    public record UsuarioDTO(int id, String nombre, String tipo) {}
}
