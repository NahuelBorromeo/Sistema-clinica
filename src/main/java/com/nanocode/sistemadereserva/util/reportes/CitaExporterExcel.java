package com.nanocode.sistemadereserva.util.reportes;

import com.nanocode.sistemadereserva.entity.Cita;
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

public class CitaExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Cita> listaCitas;

    public CitaExporterExcel(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Citas");
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
        celda.setCellValue("Documento");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Número de doctor");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Hora");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Especialidad");
        celda.setCellStyle(estilo);

    }

    private void escribirDatosDeLaTabla() {
        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();

        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(Cita cita : listaCitas) {
            Row fila = hoja.createRow(numeroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(cita.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(cita.getDocumento());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(cita.getNrodoctor());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(cita.getFecha());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(cita.getHora());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);


            celda = fila.createCell(5);
            celda.setCellValue(cita.getEspecialidad());
            hoja.autoSizeColumn(5);
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
