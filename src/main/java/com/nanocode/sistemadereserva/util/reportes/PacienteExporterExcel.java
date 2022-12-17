package com.nanocode.sistemadereserva.util.reportes;

import com.nanocode.sistemadereserva.entity.Paciente;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PacienteExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Paciente> listaPacientes;

    public PacienteExporterExcel(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Pacientes");
    }

    private void escribirCabeceraDeLaTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Email");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Telefono");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Sexo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("Documento");
        celda.setCellStyle(estilo);

    }

    private void escribirDatosDeLaTabla() {
        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();

        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(Paciente paciente : listaPacientes) {
            Row fila = hoja.createRow(numeroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(paciente.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(paciente.getNombre());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(paciente.getApellido());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(paciente.getEmail());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(paciente.getTelefono());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(paciente.getSexo());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(paciente.getDocumento());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeLaTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);

        libro.close();
        outputStream.close();
    }

}
