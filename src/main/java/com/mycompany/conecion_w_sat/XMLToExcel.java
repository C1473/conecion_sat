/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conecion_w_sat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.mycompany.conecion_w_sat.ValidacionSAT;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Resto de las importaciones y código...

public class XMLToExcel {
    private static final String EXTENSION_EXCEL = ".xlsx";

    private File fileXML;
    private File fileExcel;

    public XMLToExcel() {
    }

    public void setFileXML(File fileXML) {
        this.fileXML = fileXML;
    }

    public void setFileExcel(File fileExcel) {
        this.fileExcel = fileExcel;
    }

    public void exportToExcel() throws IOException, Exception {
        if (fileXML == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un archivo XML");
            return;
        }

        XSSFWorkbook workbook;

        // Verificar si el archivo Excel ya existe
        if (fileExcel.exists()) {
            // Si existe, carga el libro de trabajo existente
            FileInputStream excelFile = new FileInputStream(fileExcel);
            workbook = new XSSFWorkbook(excelFile);
        } else {
            // Si no existe, crea un nuevo libro de trabajo
            workbook = new XSSFWorkbook();
        }

        XSSFSheet sheet;

        // Verificar si la hoja de trabajo ya existe
        if (workbook.getSheet("Datos") != null) {
            // Si existe, obtén la hoja de trabajo existente
            sheet = workbook.getSheet("Datos");
        } else {
            // Si no existe, crea una nueva hoja de trabajo
            sheet = workbook.createSheet("Datos");

            // Agrega los encabezados al archivo Excel solo si es una hoja de trabajo nueva
            List<String> encabezados = new ArrayList<>(4);
            encabezados.add("RFC Emisor");
            encabezados.add("RFC Receptor");
            encabezados.add("Total");
            encabezados.add("UUID");

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < encabezados.size(); i++) {
                headerRow.createCell(i).setCellValue(encabezados.get(i));
            }
        }

        // Leer datos del archivo XML y agregarlos al archivo Excel
        List<String> datosDesdeXML = leerDatosDesdeXML(fileXML);

        // Buscar la última fila ocupada en el archivo Excel
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i < datosDesdeXML.size(); i++) {
            XSSFRow row = sheet.createRow(lastRowNum + i + 1);
            String[] partes = datosDesdeXML.get(i).split(",");
            for (int j = 0; j < partes.length; j++) {
                row.createCell(j).setCellValue(partes[j]);
            }
        }

        // Guarda el archivo Excel
        try (FileOutputStream outputStream = new FileOutputStream(fileExcel)) {
            workbook.write(outputStream);
        }

        // Guarda los datos en un archivo
        File fileData = new File("datos.txt");
        try (FileWriter fileWriter = new FileWriter(fileData)) {
            for (String dato : datosDesdeXML) {
                fileWriter.write(dato + "\n");
            }
        }

        // Cierra la aplicación y redirige a la clase pantallainicial
        ValidacionSAT.main(new String[0]); // Llama al método main de pantallainicial
        System.exit(0); // Cierra la aplicación actual
    }

    public List<String> leerDatosDesdeXML(File fileXML) throws Exception {
    List<String> datos = new ArrayList<>();

    try {
        // Configura el parser de XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fileXML);

        // Normaliza el documento para manejar espacios en blanco, etc.
        doc.getDocumentElement().normalize();

        // Obtiene la lista de elementos <cfdi:Emisor>
        NodeList emisorList = doc.getElementsByTagName("cfdi:Emisor");
        // Obtiene la lista de elementos <cfdi:Receptor>
        NodeList receptorList = doc.getElementsByTagName("cfdi:Receptor");
        // Obtiene la lista de elementos <cfdi:Comprobante>
        NodeList comprobanteList = doc.getElementsByTagName("cfdi:Comprobante");
        // Obtiene la lista de elementos <cfdi:Complemento>
        NodeList complementoList = doc.getElementsByTagName("tfd:TimbreFiscalDigital");

        for (int i = 0; i < emisorList.getLength(); i++) {
            Element emisorElement = (Element) emisorList.item(i);
            Element receptorElement = (Element) receptorList.item(i);
            Element comprobanteElement = (Element) comprobanteList.item(i);
            Element complementoElement = (Element) complementoList.item(i);

            // Obtiene los valores de los atributos dentro de <cfdi:Emisor>
            String emisor = emisorElement.getAttribute("Rfc");
            // Obtiene los valores de los atributos dentro de <cfdi:Receptor>
            String receptor = receptorElement.getAttribute("Rfc");
            // Obtiene los valores de los atributos dentro de <cfdi:Comprobante>
            String total = comprobanteElement.getAttribute("Total");
            // Obtiene los valores de los atributos dentro de <cfdi:Complemento>
            String uuid = complementoElement.getAttribute("UUID");

            // Crea una cadena con los valores y agrégala a la lista
            String dato = emisor + ", " + receptor + ", " + total + ", " + uuid;
            datos.add(dato);
        }
    } catch (Exception e) {
        // Manejo de excepciones en caso de error de lectura del XML
        e.printStackTrace();
        throw e;
    }

    return datos;
}

    public static void main(String[] args) throws IOException, Exception {
        XMLToExcel xmlToExcel = new XMLToExcel();

        // Elige el archivo XML
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione un archivo XML");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "Archivos XML (*.xml)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            xmlToExcel.setFileXML(fileChooser.getSelectedFile());
        }

        // Elige el archivo Excel
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione un archivo Excel");

        result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            xmlToExcel.setFileExcel(fileChooser.getSelectedFile());
        }

        xmlToExcel.exportToExcel();
    }
}