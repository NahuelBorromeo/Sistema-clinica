package com.nanocode.sistemadereserva.util.reportes;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nanocode.sistemadereserva.entity.Paciente;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PacienteExporterPDF {

    private List<Paciente> listaPacientes;

    public PacienteExporterPDF(List<Paciente> listaPacientes) {
        super();
        this.listaPacientes = listaPacientes;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Id", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Apellido", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Email", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Telefono", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Sexo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Documento", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for(Paciente paciente : listaPacientes) {
            tabla.addCell(String.valueOf(paciente.getId()));
            tabla.addCell(paciente.getNombre());
            tabla.addCell(paciente.getApellido());
            tabla.addCell(paciente.getEmail());
            tabla.addCell(String.valueOf(paciente.getTelefono()));
            tabla.addCell(paciente.getSexo());
            tabla.addCell(String.valueOf(paciente.getDocumento()));
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de pacientes", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] {1f, 2.3f,2.3f,6f,3.3f,3.3f,3.3f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }

}
