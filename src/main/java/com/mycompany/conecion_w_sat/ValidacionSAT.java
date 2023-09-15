//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.mycompany.conecion_w_sat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import com.mycompany.conecion_w_sat.Acuse;
import com.mycompany.conecion_w_sat.ConsultaCFDIService;
import com.mycompany.conecion_w_sat.IConsultaCFDIService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ARMANDO
 */
public class ValidacionSAT {

    static void main(String[] string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String rutaArchivo = "";
    private pantallainicial pantalla;
    //javax.swing.JFrame pantalla = new javax.swing.JFrame("Mi Ventana");

    public ValidacionSAT() {
    }

    public ValidacionSAT(String rutaArchivo, pantallainicial pantalla) {
        this.rutaArchivo = rutaArchivo;
        this.pantalla = pantalla;
    }

    /*public ValidacionSAT(String rutaArchivo, javax.swing.JFrame pantalla){
        this.rutaArchivo = rutaArchivo;
        this.pantalla = pantalla;
    }*/
    public String obtencionEstatus(String rfcEmisor, String rfcReceptor, String total, String uuid) {

        return null;

    }

    public Acuse consulta(java.lang.String expresionImpresa) {
        try {
            ConsultaCFDIService Service = new ConsultaCFDIService();
            IConsultaCFDIService port = Service.getBasicHttpBindingIConsultaCFDIService();
            return port.consulta(expresionImpresa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public File cargarArchivoListadoCFDI(String ubicacionArchivo) {
        try {
            // Crear un objeto File con la ubicación del archivo
            File archivo = new File(ubicacionArchivo);

            // Verificar si el archivo existe
            if (!archivo.exists()) {
                // El archivo no existe, puedes manejar esto según tus necesidades
                System.err.println("El archivo no existe en la ubicación especificada: " + ubicacionArchivo);
                return null; // O puedes lanzar una excepción si lo prefieres
            }

            // Realizar alguna lógica adicional si es necesario para procesar el archivo
            // Por ejemplo, puedes abrir el archivo y leer su contenido aquí
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;

            // Leer el archivo línea por línea e imprimir su contenido como ejemplo
            while ((linea = bufferedReader.readLine()) != null) {
                System.out.println(linea);
                // Puedes realizar operaciones específicas con cada línea aquí
            }

            // Cerrar el flujo de lectura del archivo
            bufferedReader.close();
            fileReader.close(); // Se agrega esta línea para cerrar el FileReader

            // Una vez procesado, puedes retornar el archivo si es necesario
            return archivo;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Manejar errores según tus necesidades
        }
    }

    public String datosCFDI(String rfcEmisor, String rfcReceptor, String total, String uuid) {
        try {
            // Construir la cadena de consulta para obtener los datos del CFDI
            String cadenaConsulta = "?re=" + rfcEmisor + "&rr=" + rfcReceptor + "&tt=" + total + "&id=" + uuid;

            // Realizar una consulta al servicio web o base de datos para obtener los datos del CFDI
            // Por ejemplo, puedes hacer una llamada al método consulta() que ya tienes implementado
            Acuse acuseSAT = consulta(cadenaConsulta);

            // Verificar si la consulta fue exitosa y obtener los datos
            if (acuseSAT != null) {
                // Supongamos que quieres obtener el código de estatus del CFDI
                String codigoEstatus = acuseSAT.getCodigoEstatus().getValue();
                // Puedes agregar más lógica aquí para obtener otros datos que necesites

                // Retornar los datos obtenidos en el formato que desees
                return "Código de Estatus del CFDI: " + codigoEstatus;
            } else {
                return "No se pudo obtener información del CFDI"; // Manejar errores según tus necesidades
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener datos del CFDI"; // Manejar errores según tus necesidades
        }
    }

    public String validarExcel(String rutaExcel) {
        String resultadoProcesamiento = "CORRECTO";
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;

        try {
            // Ruta al archivo Excel
            String excelFilePath = rutaExcel;

            // Cargar el archivo Excel
            inputStream = new FileInputStream(excelFilePath);
            workbook = new XSSFWorkbook(inputStream);

            // Obtener la hoja de Trabajo (worksheet) que se desea leer
            Sheet sheet = workbook.getSheetAt(0); // 0 representa la primera hoja

            // Iterar a través de las filas y columnas para obtener los datos
            int contadorRenglon = 0; // Contador para indicar el número de renglón

            for (Row row : sheet) {
                contadorRenglon++;

                if (contadorRenglon == 1) {
                    continue;
                }

                // Se VA A RECORRER LAS CELDAS DE LA FILA PARA ARMAR CADENA DE PETICIÓN
                String rfcEmisor = "";
                String rfcReceptor = "";
                String total = "";
                String uuid = "";

                HashMap<String, Object> mapDatosSAT = new HashMap<>();
                mapDatosSAT.put("RESULTADO", "SIN RESPUESTA DEL SAT");
                int contadorCelda = 0;

                for (Cell cell : row) {
                    contadorCelda++;

                    // Obtener el valor de la celda como una cadena
                    String cellValue = cell.toString();

                    switch (contadorCelda) {
                        case 1:
                            rfcEmisor = cellValue;
                            break;
                        case 2:
                            rfcReceptor = cellValue;
                            break;
                        case 3:
                            total = cellValue;
                            break;
                        case 4:
                            uuid = cellValue;
                            break;
                        case 5:
                            mapDatosSAT = obtencionEstatusSAT1(rfcEmisor, rfcReceptor, total, uuid);
                            this.pantalla.escribirConsola((contadorRenglon - 1)
                                    + " - RFC EMISOR = " + rfcEmisor
                                    + ", RFC RECEPTOR = " + rfcReceptor
                                    + ", TOTAL = " + total
                                    + ", UUID = " + uuid
                                    + ", ESTATUS PROCESO = " + mapDatosSAT.get("RESULTADO").toString()
                            );
                            cell.setCellValue(mapDatosSAT.get("ESTATUSPETICION").toString());
                            break;
                    }

                    if (mapDatosSAT.get("RESULTADO").toString().toUpperCase().equals("S - COMPROBANTE OBTENIDO SATISFACTORIAMENTE.")) {
                        switch (contadorCelda) {
                            case 6:
                                cell.setCellValue(mapDatosSAT.get("ESTATUSCFDI").toString());
                                break;
                            case 7:
                                cell.setCellValue(mapDatosSAT.get("ESCANCELABLE").toString());
                                break;
                            case 8:
                                cell.setCellValue(mapDatosSAT.get("ESTATUSCANCELACION").toString());
                                break;
                            case 9:
                                cell.setCellValue(mapDatosSAT.get("VALIDACIONEFOS").toString());
                                break;
                        }
                    }
                }
            }

            // Cerrar el flujo de entrada
            inputStream.close();

            // Guardar los cambios en el archivo Excel
            outputStream = new FileOutputStream(rutaExcel);
            workbook.write(outputStream);
            outputStream.close();

            // Cerrar el libro de Excel
            workbook.close();

            this.pantalla.escribirConsola("* NUMERO DE REGISTROS PROCESADOS = " + (contadorRenglon - 1));
            System.out.println("Archivo Excel creado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            resultadoProcesamiento = "ERROR: " + e.getMessage();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultadoProcesamiento;
        }
    }

    public HashMap obtencionEstatusSAT1(String rfcEmisor, String rfcReceptor, String total, String uuid) {
        HashMap mapDatosSAT = new HashMap();
        rfcEmisor = rfcEmisor.replaceAll("&", "&amp;");
        rfcReceptor = rfcReceptor.replaceAll("&", "&amp;");
        String respuestaPeticion = "SIN RESPUESTA DEL SAT";
        String cadenaPeticion = "?re=" + rfcEmisor + "&rr=" + rfcReceptor + "&tt=" + total + "&id=" + uuid;
        ValidacionSAT operacionesWSValidacion = new ValidacionSAT();
        Acuse acuseSAT = operacionesWSValidacion.consulta(cadenaPeticion);
        if (acuseSAT != null) {
            if (acuseSAT.getCodigoEstatus() != null) {
                String codigoEstatus = acuseSAT.getCodigoEstatus().getValue();
                if (codigoEstatus != null && !codigoEstatus.equals("")) {
                    mapDatosSAT.put("ESTATUSPETICION", codigoEstatus);

                    if (codigoEstatus.toUpperCase().equals("N - 601: La expresión impresa proporcionada no es válida.".toUpperCase())) {
                        respuestaPeticion = "N - 601: La expresión impresa proporcionada no es válida.";
                        
                    } else if (codigoEstatus.toUpperCase().equals("N - 602: La expresión impresa proporcionada se encontro.".toUpperCase())) {
                        respuestaPeticion = "N - 601: La expresión impresa proporcionada no es válida.";
                    } else if (codigoEstatus.toUpperCase().equals("S - Comprobante obtenido satisfactoriamente.".toUpperCase())) {
                        if (acuseSAT.getEstado() != null) {
                            mapDatosSAT.put("ESTATUSCFDI", acuseSAT.getEstado().getValue());
                            mapDatosSAT.put("ESCANCELABLE", acuseSAT.getEsCancelable().getValue());
                            mapDatosSAT.put("ESTATUSCANCELACION", acuseSAT.getEstatusCancelacion().getValue());
                            mapDatosSAT.put("VALIDACIONEFOS", acuseSAT.getValidacionEFOS().getValue());
                            respuestaPeticion = "S - Comprobante obtenido satisfactoriamente.";
                        } else {
                            respuestaPeticion = "NO EXISTE EN SAT";
                        }
                    }
                } else {
                    respuestaPeticion = "Sin respuesta del SAT";
                }
            } else {
                respuestaPeticion = "Sin respuesta del SAT";
            }
        } else {
            respuestaPeticion = "Sin respuesta del SAT";
        }
        mapDatosSAT.put("RESULTADO", respuestaPeticion);
        return mapDatosSAT;
    }

    private HashMap obtencionEstatusSAT(String rfcEmisor, String rfcReceptor, String total, String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String obtencionEstatus(String crF090521AP6, String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
