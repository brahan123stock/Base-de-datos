package Controlador;
import Modelo.ForoDAO;
import Vista.Interfaz3;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorInterfaz3 implements ActionListener {
    private Interfaz3 vista;
    private ForoDAO foroDAO;

    public ControladorInterfaz3(Interfaz3 vista) {
        this.vista = vista;
        this.foroDAO = new ForoDAO();

        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnVer().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);

        cargarTablaForos();
    }

    private void cargarTablaForos() {
        DefaultTableModel modelo = foroDAO.listarForos();
        vista.getTablaForos().setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            String titulo = vista.getTxtTitulo().getText();
            String nombre = vista.getTxtNombre().getText();
            String fechaStr = vista.getTxtFecha().getText();
            String publicacion = vista.getTxtPublicacion().getText();

            if (titulo.isEmpty() || nombre.isEmpty() || fechaStr.isEmpty() || publicacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }

            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                if (foroDAO.guardarPublicacion(titulo, nombre, sqlDate, publicacion)) {
                    JOptionPane.showMessageDialog(null, "Publicación guardada");
                    cargarTablaForos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar publicación");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Fecha inválida. Use formato yyyy-MM-dd.");
            }
        }

        if (e.getSource() == vista.getBtnVer()) {
            int fila = vista.getTablaForos().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una publicación de la tabla.");
                return;
            }
            String publicacion = (String) vista.getTablaForos().getValueAt(fila, 4);
            JOptionPane.showMessageDialog(null, "Publicación:\n" + publicacion);
        }

        if (e.getSource() == vista.getBtnBuscar()) {
            String textoBuscar = JOptionPane.showInputDialog("Ingrese texto a buscar en foros:");
            if (textoBuscar != null && !textoBuscar.trim().isEmpty()) {
                DefaultTableModel modelo = foroDAO.buscarForos(textoBuscar);
                vista.getTablaForos().setModel(modelo);
            }
        }
    }
}
