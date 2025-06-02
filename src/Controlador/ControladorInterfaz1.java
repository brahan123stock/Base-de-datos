package Controlador;

import Modelo.CursoDAO;
import Modelo.UsuarioCursoDAO;
import Modelo.UsuarioDAO;
import Vista.Interfaz1;
import Vista.Interfaz2;
import java.awt.event.*;
import javax.swing.*;

public class ControladorInterfaz1 implements ActionListener {
    private Interfaz1 vista;
    private UsuarioDAO usuarioDAO;
    private CursoDAO cursoDAO;
    private UsuarioCursoDAO usuarioCursoDAO;
    private int idUsuarioRegistrado = -1; // Guardar el usuario registrado

    public ControladorInterfaz1(Interfaz1 vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        this.cursoDAO = new CursoDAO();
        this.usuarioCursoDAO = new UsuarioCursoDAO();

        this.vista.getBtnRegistrar().addActionListener(this);
        this.vista.getBtnAvanzar().addActionListener(this);

        cargarCursos();
    }

    private void cargarCursos() {
        vista.getCmbCursos().setModel(cursoDAO.listarCursos());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegistrar()) {
            String nombre = vista.getTxtNombre().getText();
            String dni = vista.getTxtDNI().getText();
            String email = vista.getTxtEmail().getText();
            String cursoSeleccionado = (String) vista.getCmbCursos().getSelectedItem();

            if (nombre.isEmpty() || dni.isEmpty() || email.isEmpty() 
                || cursoSeleccionado == null || cursoSeleccionado.equals("---Seleccionar---")) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos y seleccione un curso válido.");
                return;
            }

            int idUsuario = usuarioDAO.registrarUsuario(nombre, dni, email);
            if (idUsuario > 0) {
                int idCurso = cursoDAO.obtenerIdCurso(cursoSeleccionado);
                if (idCurso > 0) {
                    if (usuarioCursoDAO.registrarUsuarioCurso(idUsuario, idCurso)) {
                        JOptionPane.showMessageDialog(null, "Registro exitoso");
                        idUsuarioRegistrado = idUsuario;
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar curso para el usuario");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Curso seleccionado no válido en la base de datos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar usuario");
            }
        }

        if (e.getSource() == vista.getBtnAvanzar()) {
            if (idUsuarioRegistrado == -1) {
                JOptionPane.showMessageDialog(null, "Debe registrar un usuario primero.");
                return;
            }
            Interfaz2 vista2 = new Interfaz2();
            ControladorInterfaz2 controlador2 = new ControladorInterfaz2(vista2, idUsuarioRegistrado);
            vista2.setVisible(true);
            vista.dispose();
        }
        
    }
    
}