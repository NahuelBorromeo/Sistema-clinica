package com.nanocode.sistemadereserva.controller;

import com.nanocode.sistemadereserva.entity.HistoriaClinica;
import com.nanocode.sistemadereserva.entity.Paciente;
import com.nanocode.sistemadereserva.repository.HistoriaClinicaRepository;
import com.nanocode.sistemadereserva.service.HistoriaClinicaService;
import com.nanocode.sistemadereserva.service.PacienteService;
import com.nanocode.sistemadereserva.util.paginacion.PageRender;
import com.nanocode.sistemadereserva.util.reportes.PacienteExporterExcel;
import com.nanocode.sistemadereserva.util.reportes.PacienteExporterPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
import java.util.Set;

@Controller
public class PacienteController {

    public static Long ID_VALUE;
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @GetMapping("/")
    public String index() {

        return "layout/layout";
    }

    @GetMapping("/chatbot")
    public String chatbot() {

      return "/chatbotCovid";
    }


  @GetMapping("/verPaciente/{id}")
    public String verHistorialClinicoDelPaciente(@RequestParam(name = "page", defaultValue = "0") int page, @PathVariable(value = "id") Long id, Model model) {

        Pageable pageRequest = PageRequest.of(page, 100);
        Page<HistoriaClinica> historiaClinicass = historiaClinicaService.findAll(pageRequest);
        PageRender<HistoriaClinica> pageRender = new PageRender<>("/verPaciente", historiaClinicass);


        Paciente paciente = pacienteService.findOne(id);

        ID_VALUE = id;

        Set<HistoriaClinica> historiasClinicas = paciente.getHistoriaClinicas();


        model.addAttribute("titulo","Historia clínica del Paciente");
        model.addAttribute("historiasClinicas", historiasClinicas);
        model.addAttribute("paciente", paciente);
        model.addAttribute("page", pageRender);


        return "verPaciente";
    }

    @GetMapping({"/listarPacientes"})
    public String listarPacientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Paciente> pacientes = pacienteService.findAll(pageRequest);
        PageRender<Paciente> pageRender = new PageRender<>("/listarPacientes", pacientes);

        model.addAttribute("titulo","Listado de pacientes");
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("page", pageRender);


        return "listarPacientes";
    }

    @GetMapping("/formPaciente")
    public String mostrarFormularioDeRegistrarPaciente(Map<String,Object> model) {
        Paciente paciente = new Paciente();
        model.put("paciente", paciente);
        model.put("titulo", "Registro de pacientes");
        return "formPaciente";
    }

    @PostMapping("/formPaciente")
    public String guardarPaciente(@Valid Paciente paciente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Registro de pacientes");
            return "formPaciente";
        }
        String mensaje = (paciente.getId() != null) ? "El paciente se ha actualizado correctamente." : "El paciente se ha creado correctamente.";
        pacienteService.save(paciente);
        status.setComplete();
        flash.addFlashAttribute("sucess", mensaje);
        return "redirect:/listarPacientes";
    }

    @GetMapping("/formPaciente/{id}")
    public String editarPaciente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Paciente paciente = null;
        if(id > 0) {
            paciente = pacienteService.findOne(id);
            if(paciente == null) {
                flash.addFlashAttribute("error", "El ID del paciente no existe en la base de datos");
                return "redirect:/listarPacientes";
            }
        } else {
            flash.addAttribute("error", "El ID del paciente tiene que ser mayor a cero");
            return "redirect:/listarPacientes";
        }
        model.put("paciente", paciente);
        model.put("titulo", "Actualización de pacientes");
        return "formPaciente";
    }

    @GetMapping("/eliminarPaciente/{id}")
    public String eliminarPaciente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0){
            pacienteService.delete(id);
            flash.addFlashAttribute("success", "Paciente eliminado con éxito");
        }
        return "redirect:/listarPacientes";
    }

    @GetMapping("/exportarPacientesPDF")
    public void exportarListadoDePacientesEnPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Pacientes_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Paciente> pacientes = pacienteService.findAll();

        PacienteExporterPDF exporter = new PacienteExporterPDF(pacientes);
        exporter.exportar(response);
    }

    @GetMapping("/exportarPacientesExcel")
    public void exportarListadoDePacientesEnExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Pacientes_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Paciente> pacientes = pacienteService.findAll();

        PacienteExporterExcel exporter = new PacienteExporterExcel(pacientes);
        exporter.exportar(response);
    }

}
