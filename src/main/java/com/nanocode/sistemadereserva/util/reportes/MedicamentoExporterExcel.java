package com.nanocode.sistemadereserva.util.reportes;

import com.nanocode.sistemadereserva.entity.Medicamento;
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

public class MedicamentoExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Medicamento> listaMedicamentos;

    public MedicamentoExporterExcel(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Medicamentos");
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
        celda.setCellValue("Nombre Científico");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Nombre Comercial");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Dosis");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Laboratorio");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Precio");
        celda.setCellStyle(estilo);

    }

    private void escribirDatosDeLaTabla() {
        int numeroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();

        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(Medicamento medicamento : listaMedicamentos) {
            Row fila = hoja.createRow(numeroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(medicamento.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(medicamento.getNombreCientifico());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(medicamento.getNombreComercial());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(medicamento.getDosis());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(medicamento.getLaboratorio());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(medicamento.getPrecio());
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
