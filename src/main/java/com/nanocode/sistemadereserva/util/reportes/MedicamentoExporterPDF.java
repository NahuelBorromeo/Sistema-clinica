package com.nanocode.sistemadereserva.util.reportes;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nanocode.sistemadereserva.entity.Medicamento;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MedicamentoExporterPDF {

    private List<Medicamento> listaMedicamentos;

    public MedicamentoExporterPDF(List<Medicamento> listaMedicamentos) {
        super();
        this.listaMedicamentos = listaMedicamentos;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Id", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre científico", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre comercial", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Dosis", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Laboratorio", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for(Medicamento medicamento : listaMedicamentos) {
            tabla.addCell(String.valueOf(medicamento.getId()));
            tabla.addCell(medicamento.getNombreCientifico());
            tabla.addCell(medicamento.getNombreComercial());
            tabla.addCell(String.valueOf(medicamento.getDosis()));
            tabla.addCell(medicamento.getLaboratorio());
            tabla.addCell(String.valueOf(medicamento.getPrecio()));
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de medicamentos", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] {1f, 3f,3f,2f,3f,2.5f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }

}
