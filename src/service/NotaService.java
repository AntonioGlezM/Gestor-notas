package service;

import utils.EmailUtils;

import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotaService {

    // Obtener carpeta del usuario
    private Path obtenerCarpeta(String email) {

        String emailSanitizado = EmailUtils.sanitizar(email);

        return Paths.get("data", "usuarios", emailSanitizado);
    }

    // Listar notas
    public List<String> listarNotas(String email) {

        List<String> notas = new ArrayList<>();

        try {

            // Obtenemos la carpeta del usuario
            Path carpeta = obtenerCarpeta(email);

            // Si la carpeta no existe, devolvemos lista vacía
            if (!Files.exists(carpeta)) {
                return notas;
            }

            // Creamos un DirectoryStream para recorrer los archivos de la carpeta
            // Uso try with resources para cerrar stream
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpeta)) {

                // Recorremos cada archivo que hay dentro de la carpeta
                for (Path archivo : stream) {

                    // Obtenemos solo el nombre del archivo (ej: nota1.txt)
                    notas.add(archivo.getFileName().toString());

                }
            }

        } catch (IOException e) {
            // Si ocurre algún error de lectura, no rompemos el programa
        }

        // Devolvemos la lista de nombres de notas
        return notas;
    }

    public String leerNota(String email, String nombreArchivo) {

        try {

            Path ruta = obtenerCarpeta(email).resolve(nombreArchivo);

            return Files.readString(ruta);

        } catch (IOException e) {
            return "";
        }
    }

    public void guardarNota(String email, String titulo, String contenido) {

        try {

            Path carpeta = obtenerCarpeta(email);

            Files.createDirectories(carpeta);

            Path archivo = carpeta.resolve(titulo + ".txt");

            Files.writeString(archivo, contenido,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {

        }
    }

    public void eliminarNota(String email, String nombreArchivo) {

        try {

            Path ruta = obtenerCarpeta(email).resolve(nombreArchivo);

            Files.deleteIfExists(ruta);

        } catch (IOException e) {
        }
    }

    public void borrarTodas(String email) {

        try {

            Path carpeta = obtenerCarpeta(email);

            if (!Files.exists(carpeta)) {
                return;
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpeta)) {

                for (Path archivo : stream) {

                    Files.deleteIfExists(archivo);

                }
            }

        } catch (IOException e) {
        }
    }
}