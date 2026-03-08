package service;

import model.Usuario;
import utils.Hash;
import utils.EmailUtils;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class UsuarioService {

    private Path usersFile = Paths.get("data", "users.txt");

    public boolean registrarUsuario(String email, String password) {

        try {

            // Generar hash de la contraseña
            String hash = Hash.generarHash(password);

            Usuario usuario = new Usuario(email, hash);

            // Guardar en users.txt
            Files.write(usersFile,
                    (usuario.getEmail() + ";" + usuario.getPasswordHash() + "\n").getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            // Crear carpeta del usuario
            String emailSanitizado = EmailUtils.sanitizar(email);

            Path carpetaUsuario = Paths.get("data", "usuarios", emailSanitizado);

            Files.createDirectories(carpetaUsuario);

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public boolean login(String email, String password) {

        try {

            String hash = Hash.generarHash(password);

            if (!Files.exists(usersFile)) {
                return false;
            }

            List<String> lineas = Files.readAllLines(usersFile);

            for (String linea : lineas) {

                String[] datos = linea.split(";");

                Usuario usuario = new Usuario(datos[0], datos[1]);

                if (usuario.getEmail().equals(email) &&
                        usuario.getPasswordHash().equals(hash)) {

                    return true;
                }
            }

        } catch (IOException e) {
            return false;
        }

        return false;
    }
}