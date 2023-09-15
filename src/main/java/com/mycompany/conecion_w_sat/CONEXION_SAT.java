/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conecion_w_sat;

import java.awt.Dimension;
import java.awt.Toolkit;
import static javax.swing.Spring.width;
//import mx.sat.wsvalidacion.Acuse;
//import mx.sat.wsvalidacion.ConsultaCFDIService;
//import mx.sat.wsvalidacion.IConsultaCFDIService;

public class CONEXION_SAT {

    public static void main(String[] args) {
        System.out.println("Hola BERE!");
        //ValidacionSAT validador = new ValidacionSAT();
        //String respuesta = validador.obtencionEstatus("CRF090521AP6","");
        //System.out.println("*** RESULTADO VALIDACION = " + respuesta);
        pantallainicial pantallaIni = new pantallainicial();
        pantallaIni.setSize(500, 500);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frame = pantallaIni.getSize();
        pantallaIni.setLocation((pantalla.width / 2 - (frame.width / 2)), pantalla.height / 2 - (frame.height / 2));
        pantallaIni.setVisible(true);
    }
}