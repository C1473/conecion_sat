//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mycompany.conecion_w_sat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelLoader {

    public static void main(String[] args) {
        try {
            // Ruta al archivo Excel
            String excelFilePath = "C:\\Users\\Berenice\\Documents\\NetBeansProjects\\ENTRADA\\LAYOUT_ENTRADA.xlsx";

            // Carga el archivo Excel
            FileInputStream inputStream = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(inputStream);

            // Obtener la hoja de trabajo (Worksheet) que deseas leer
            Sheet sheet = workbook.getSheetAt(0); // 0 representa la primera hoja

            // Crear una lista para almacenar los datos del Excel
            List<List<String>> excelData = new ArrayList<>();

            // Iterar a través de las filas y columnas para obtener los datos
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                excelData.add(rowData);
            }

            // Cerrar el flujo de entrada
            inputStream.close();

            // Ahora excelData contiene los datos del Excel en una estructura de lista
            for (List<String> rowData : excelData) {
                for (String cellValue : rowData) {
                    System.out.print(cellValue + "\t");
                }
                System.out.println(); // Nueva línea para cada fila
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
