package com.cowshop.project.resources;

import com.cowshop.data.UsuarioData;
import com.cowshop.helpers.JwtHelper;
import com.cowshop.project.entities.Usuario;
import com.cowshop.project.entities.UsuarioLoginRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioResource {

    /* ===================== OPTIONS (CORS) ===================== */

    @OPTIONS
    public Response optionsUsuario() {
        return Response.ok().build();
    }

    @OPTIONS
    @Path("/{id}")
    public Response optionsUsuarioPorId() {
        return Response.ok().build();
    }

    /* ===================== GET ===================== */

    @GET
    @Operation(summary = "Listar usuarios")
    @APIResponse(responseCode = "200", description = "Lista de usuarios")
    public Response listar() {
        List<Usuario> usuarios = UsuarioResource.listarUsuarios();
        return Response.ok(usuarios != null ? usuarios : List.of()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @APIResponse(responseCode = "200", description = "Usuario encontrado")
    @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    public Response obtenerPorId(@PathParam("id") String id) {
        Usuario usuario = UsuarioData.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(usuario).build();
    }

    /* ===================== POST ===================== */

    @POST
    @Operation(summary = "Registrar usuario")
    @APIResponse(responseCode = "201", description = "Usuario creado")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    public Response registrar(Usuario usuario, @Context UriInfo uriInfo) {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuario inválido")
                    .build();
        }

        boolean success = UsuarioResource.registrarUsuario(usuario);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No se pudo registrar el usuario")
                    .build();
        }

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(usuario.getID_Usuario());

        return Response.created(builder.build()).entity(usuario).build();
    }

    /* ===================== PUT ===================== */

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar usuario")
    @APIResponse(responseCode = "200", description = "Usuario actualizado")
    @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    public Response actualizar(@PathParam("id") int id, Usuario usuario) {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        usuario.setID_Usuario(id);

        boolean success = UsuarioData.actualizarUsuario(id, usuario);
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(usuario).build();
    }

    /* ===================== DELETE ===================== */

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar usuario")
    @APIResponse(responseCode = "204", description = "Usuario eliminado")
    @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    public Response eliminar(@PathParam("id") int id) {
        boolean success = UsuarioData.eliminarUsuario(String.valueOf(id));
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
