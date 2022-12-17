package com.nanocode.sistemadereserva.util.reportes;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nanocode.sistemadereserva.entity.Cita;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CitaExporterPDF {

    private List<Cita> listaCitas;

    public CitaExporterPDF(List<Cita> listaCitas) {
        super();
        this.listaCitas = listaCitas;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Id", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Documento", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nro Doctor", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Hora", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Especialidad", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for(Cita cita : listaCitas) {
            tabla.addCell(String.valueOf(cita.getId()));
            tabla.addCell(String.valueOf(cita.getDocumento()));
            tabla.addCell(String.valueOf(cita.getNrodoctor()));
            tabla.addCell(cita.getFecha().toString());
            tabla.addCell(cita.getHora().toString());
            tabla.addCell(cita.getEspecialidad());
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de citas médicas", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] {1f, 2.3f,2.3f,2f,2f,3f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }

}
