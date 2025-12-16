package com.cowshop.project.entities;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
@Schema(name = UsuarioLoginRequest, description = "Datos necesarios para el login")
public class UsuarioLoginRequest {

    @Schema(
            description = "Correo electronico del usuario",
            examples = "usuario@cowshop.com",
            required = true


    )
    private String correo;

    @Schema(
            description = "Contraseña del usuario",
            examples = "123456789",
            required = true
    )
    private String password;

    // Getter and Setter
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
