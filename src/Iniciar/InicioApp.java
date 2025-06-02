package Iniciar;

import Controlador.ControladorInterfaz1;
import Vista.Interfaz1;

public class InicioApp {
    public static void main(String[] args) {
        Interfaz1 vista1 = new Interfaz1();
        ControladorInterfaz1 controlador1 = new ControladorInterfaz1(vista1);
        vista1.setVisible(true);
    }
}