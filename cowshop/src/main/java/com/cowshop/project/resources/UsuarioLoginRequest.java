package com.cowshop.project.resources;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
@Schema(name = UsuarioLoginRequest, description = "Datos necesarios para el login")
public class UsuarioLoginRequest {

    @Schema(
            description = "Correo electronico del usuario",
            examples = "usuario@cowshop.com",
            required = true


    )
    private String correo;

    @Schema()

}
