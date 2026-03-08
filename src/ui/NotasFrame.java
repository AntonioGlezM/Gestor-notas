package ui;

import service.NotaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotasFrame extends JFrame {

    private String email;

    private NotaService notaService = new NotaService();

    private DefaultListModel<String> modelo = new DefaultListModel<>();
    private JList<String> lista = new JList<>(modelo);

    private JTextField titulo = new JTextField();
    private JTextArea contenido = new JTextArea();

    private JTextField buscador = new JTextField();

    private JLabel contador = new JLabel("Notas: 0");
    private JLabel estado = new JLabel("Estado: listo");

    public NotasFrame(String email){

        this.email = email;

        setTitle("Gestor de notas - " + email);
        setSize(700,450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // PANEL IZQUIERDO (BUSCADOR + LISTA)
        JPanel izquierda = new JPanel(new BorderLayout());

        izquierda.add(new JLabel("Buscar:"),BorderLayout.NORTH);
        izquierda.add(buscador,BorderLayout.CENTER);
        izquierda.add(new JScrollPane(lista),BorderLayout.SOUTH);

        panel.add(izquierda,BorderLayout.WEST);

        // PANEL EDITOR
        JPanel editor = new JPanel(new BorderLayout());

        editor.add(titulo,BorderLayout.NORTH);
        editor.add(new JScrollPane(contenido),BorderLayout.CENTER);

        panel.add(editor,BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel();

        JButton crear = new JButton("Crear nota");
        JButton editar = new JButton("Editar nota");
        JButton eliminar = new JButton("Eliminar nota");
        JButton limpiar = new JButton("Limpiar campos");
        JButton borrarTodo = new JButton("Borrar todas");
        JButton cerrarSesion = new JButton("Cerrar sesión");

        botones.add(crear);
        botones.add(editar);
        botones.add(eliminar);
        botones.add(limpiar);
        botones.add(borrarTodo);
        botones.add(cerrarSesion);
        botones.add(contador);
        botones.add(estado);

        panel.add(botones,BorderLayout.SOUTH);

        add(panel);

        cargarNotas();

        // 1️⃣ CREAR NOTA
        crear.addActionListener(e -> {

            if(titulo.getText().isEmpty()){

                JOptionPane.showMessageDialog(this,"Introduce un título");
                return;

            }

            notaService.guardarNota(email,
                    titulo.getText(),
                    contenido.getText());

            estado.setText("Estado: nota creada");

            cargarNotas();
        });

        // 2️⃣ SELECCIONAR / VER NOTA
        lista.addListSelectionListener(e -> {

            String nombre = lista.getSelectedValue();

            if(nombre!=null){

                String texto = notaService.leerNota(email,nombre);

                titulo.setText(nombre.replace(".txt",""));
                contenido.setText(texto);

                estado.setText("Estado: nota cargada");

            }

        });

        // 3️⃣ EDITAR NOTA
        editar.addActionListener(e -> {

            String seleccion = lista.getSelectedValue();

            if(seleccion==null){

                JOptionPane.showMessageDialog(this,
                        "Selecciona una nota primero");

                return;
            }

            notaService.guardarNota(email,
                    titulo.getText(),
                    contenido.getText());

            estado.setText("Estado: nota editada");

            cargarNotas();
        });

        // 4️⃣ ELIMINAR NOTA
        eliminar.addActionListener(e -> {

            String seleccion = lista.getSelectedValue();

            if(seleccion==null){

                JOptionPane.showMessageDialog(this,
                        "Selecciona una nota primero");

                return;
            }

            notaService.eliminarNota(email,seleccion);

            estado.setText("Estado: nota eliminada");

            cargarNotas();
        });

        // 5️⃣ LIMPIAR CAMPOS
        limpiar.addActionListener(e -> limpiarCampos());

        // 6️⃣ BORRAR TODAS
        borrarTodo.addActionListener(e -> {

            int r = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres borrar TODAS las notas?");

            if(r==0){

                notaService.borrarTodas(email);

                estado.setText("Estado: todas las notas eliminadas");

                cargarNotas();

            }
        });

        // CERRAR SESIÓN
        cerrarSesion.addActionListener(e -> {

            new LoginFrame().setVisible(true);

            dispose();

        });

        // 5️⃣ BUSCAR / FILTRAR
        buscador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e){filtrar();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){filtrar();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){filtrar();}

        });

    }

    private void cargarNotas(){

        modelo.clear();

        List<String> notas = notaService.listarNotas(email);

        for(String n : notas){

            modelo.addElement(n);

        }

        contador.setText("Notas: " + notas.size());
    }

    private void limpiarCampos(){

        titulo.setText("");
        contenido.setText("");
        lista.clearSelection();

        estado.setText("Estado: campos limpiados");

    }

    private void filtrar(){

        String texto = buscador.getText().toLowerCase();

        modelo.clear();

        List<String> notas = notaService.listarNotas(email);

        for(String n : notas){

            if(n.toLowerCase().contains(texto)){

                modelo.addElement(n);

            }
        }
    }
}