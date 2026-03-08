package ui;

import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);

    private UsuarioService usuarioService = new UsuarioService();

    public LoginFrame() {

        setTitle("Sistema de Notas - Login");
        setSize(350,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2));

        JButton loginBtn = new JButton("Iniciar sesión");
        JButton regBtn = new JButton("Registrarse");

        panel.add(new JLabel("Email"));
        panel.add(emailField);

        panel.add(new JLabel("Contraseña"));
        panel.add(passField);

        panel.add(loginBtn);
        panel.add(regBtn);

        add(panel);

        loginBtn.addActionListener(e -> login());
        regBtn.addActionListener(e -> registrar());
    }

    private void login(){

        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        if(usuarioService.login(email,pass)){

            JOptionPane.showMessageDialog(this,"Login correcto");

            new NotasFrame(email).setVisible(true);

            dispose();

        }else{

            JOptionPane.showMessageDialog(this,
                    "Email o contraseña incorrectos");

        }
    }

    private void registrar(){

        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        boolean ok = usuarioService.registrarUsuario(email,pass);

        if(ok){

            JOptionPane.showMessageDialog(this,
                    "Usuario registrado correctamente");

        }else{

            JOptionPane.showMessageDialog(this,
                    "Error al registrar usuario");

        }
    }
}