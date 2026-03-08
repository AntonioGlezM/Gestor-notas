package utils;

public class EmailUtils {

    public static String sanitizar(String email) {
        return email.replace("@", "_")
                    .replace(".", "_");
    }
}