package Controlador;

import Modelo.CursoDAO;
import Modelo.ProgresoCursoDAO;
import Vista.Interfaz2;
import Vista.Interfaz3;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorInterfaz2 implements ActionListener {
    private Interfaz2 vista;
    private ProgresoCursoDAO progresoDAO;
    private int idUsuario;

    public ControladorInterfaz2(Interfaz2 vista, int idUsuario) {
        this.vista = vista;
        this.progresoDAO = new ProgresoCursoDAO();
        this.idUsuario = idUsuario;

        this.vista.getBtnVerContenido().addActionListener(this);
        this.vista.getBtnGuardarProgreso().addActionListener(this);
        this.vista.getBtnIrForo().addActionListener(this);

        cargarTablaProgreso();
    }

    private void cargarTablaProgreso() {
        DefaultTableModel modelo = progresoDAO.listarProgresos(idUsuario);
        vista.getTablaProgreso().setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnVerContenido()) {
            int fila = vista.getTablaProgreso().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un curso de la tabla.");
                return;
            }
            String progreso = (String) vista.getTablaProgreso().getValueAt(fila, 1);
            String contenido = (String) vista.getTablaProgreso().getValueAt(fila, 2);

            JOptionPane.showMessageDialog(null, "Progreso: " + progreso + "\nContenido: " + contenido);
        }

        if (e.getSource() == vista.getBtnGuardarProgreso()) {
            int fila = vista.getTablaProgreso().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un curso de la tabla.");
                return;
            }

            String curso = (String) vista.getTablaProgreso().getValueAt(fila, 0);
            String progreso = (String) vista.getTablaProgreso().getValueAt(fila, 1);
            String contenido = (String) vista.getTablaProgreso().getValueAt(fila, 2);

            CursoDAO cursoDAO = new CursoDAO();
            int idCurso = cursoDAO.obtenerIdCurso(curso);

            if (progresoDAO.guardarProgreso(idUsuario, idCurso, progreso, contenido)) {
                JOptionPane.showMessageDialog(null, "Progreso guardado");
                cargarTablaProgreso();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar progreso");
            }
        }

        if (e.getSource() == vista.getBtnIrForo()) {
            Interfaz3 vista3 = new Interfaz3();
            ControladorInterfaz3 controlador3 = new ControladorInterfaz3(vista3);
            vista3.setVisible(true);
            vista.dispose();
        }
    }
}

