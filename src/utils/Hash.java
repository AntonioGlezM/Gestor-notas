package utils;

import java.security.MessageDigest;

public class Hash {

    public static String generarHash(String texto){

        try{

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(texto.getBytes());

            StringBuilder sb = new StringBuilder();

            for(byte b : hash){

                sb.append(String.format("%02x",b));

            }

            return sb.toString();

        }catch(Exception e){

            throw new RuntimeException("Error generando hash");

        }
    }
}