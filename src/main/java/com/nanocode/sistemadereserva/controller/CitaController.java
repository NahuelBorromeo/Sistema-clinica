package com.nanocode.sistemadereserva.controller;

import com.nanocode.sistemadereserva.entity.Cita;
import com.nanocode.sistemadereserva.entity.Medico;
import com.nanocode.sistemadereserva.entity.Paciente;
import com.nanocode.sistemadereserva.service.CitaService;
import com.nanocode.sistemadereserva.service.MedicoService;
import com.nanocode.sistemadereserva.service.PacienteService;
import com.nanocode.sistemadereserva.util.paginacion.PageRender;
import com.nanocode.sistemadereserva.util.reportes.CitaExporterExcel;
import com.nanocode.sistemadereserva.util.reportes.CitaExporterPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/verCita/{id}")
    public String verHistorialClinicoDelCita(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cita cita = citaService.findOne(id);
        if(cita == null) {
            flash.addFlashAttribute("error", "El cita no existe en la base de datos.");
            return "redirect:/listarCitas";
        }
        model.put("cita", cita);
        model.put("titulo", "Detalle del cita médica: ");
        return "verCita";
    }

    @GetMapping({"/listarCitas"})
    public String listarCitas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cita> citas = citaService.findAll(pageRequest);
        PageRender<Cita> pageRender = new PageRender<>("/listarCitas", citas);

        model.addAttribute("titulo","Listado de citas");
        model.addAttribute("citas", citas);
        model.addAttribute("page", pageRender);


        return "listarCitas";
    }

    @GetMapping("/formCita")
    public String mostrarFormularioDeRegistrarCita(Map<String,Object> model) {
        Cita cita = new Cita();
        model.put("cita", cita);
        model.put("titulo", "Registro de citas");
        return "formCita";
    }

    @PostMapping("/formCita")
    public String guardarCita(@Valid Cita cita, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

        Medico medico = medicoService.findOne(Long.valueOf(1));
        Paciente paciente = pacienteService.findOne(Long.valueOf(1));

        cita.setMedico(medico);
        cita.setPaciente(paciente);

        if(result.hasErrors()) {
            model.addAttribute("titulo", "Registro de citas");
            return "formCita";
        }
        String mensaje = (cita.getId() != null) ? "El cita se ha actualizado correctamente." : "El cita se ha creado correctamente.";
        citaService.save(cita);
        status.setComplete();
        flash.addFlashAttribute("sucess", mensaje);
        return "redirect:/listarCitas";
    }

    @GetMapping("/formCita/{id}")
    public String editarCita(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cita cita = null;
        if(id > 0) {
            cita = citaService.findOne(id);
            if(cita == null) {
                flash.addFlashAttribute("error", "El ID del cita no existe en la base de datos");
                return "redirect:/listarCitas";
            }
        } else {
            flash.addAttribute("error", "El ID del cita tiene que ser mayor a cero");
            return "redirect:/listarCitas";
        }
        model.put("cita", cita);
        model.put("titulo", "Actualización de citas");
        return "formCita";
    }

    @GetMapping("/eliminarCita/{id}")
    public String eliminarCita(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0){
            citaService.delete(id);
            flash.addFlashAttribute("success", "Cita eliminado con éxito");
        }
        return "redirect:/listarCitas";
    }

    @GetMapping("/exportarCitasPDF")
    public void exportarListadoDeCitasEnPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Citas_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Cita> citas = citaService.findAll();

        CitaExporterPDF exporter = new CitaExporterPDF(citas);
        exporter.exportar(response);
    }

    @GetMapping("/exportarCitasExcel")
    public void exportarListadoDeCitasEnExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Citas_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Cita> citas = citaService.findAll();

        CitaExporterExcel exporter = new CitaExporterExcel(citas);
        exporter.exportar(response);
    }

}
