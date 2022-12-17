package com.nanocode.sistemadereserva.controller;

import com.nanocode.sistemadereserva.entity.Medico;
import com.nanocode.sistemadereserva.service.MedicoService;
import com.nanocode.sistemadereserva.util.paginacion.PageRender;
import com.nanocode.sistemadereserva.util.reportes.MedicoExporterExcel;
import com.nanocode.sistemadereserva.util.reportes.MedicoExporterPDF;
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
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/verMedico/{id}")
    public String verHistorialClinicoDelMedico(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Medico medico = medicoService.findOne(id);
        if(medico == null) {
            flash.addFlashAttribute("error", "El medico no existe en la base de datos.");
            return "redirect:/listarMedicos";
        }
        model.put("medico", medico);
        model.put("titulo", "Detalle del medico: " + medico.getNombre() + " " + medico.getApellido());
        return "verMedico";
    }

    @GetMapping({"/listarMedicos"})
    public String listarMedicos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Medico> medicos = medicoService.findAll(pageRequest);
        PageRender<Medico> pageRender = new PageRender<>("/listarMedicos", medicos);

        model.addAttribute("titulo","Listado de medicos");
        model.addAttribute("medicos", medicos);
        model.addAttribute("page", pageRender);


        return "listarMedicos";
    }

    @GetMapping("/formMedico")
    public String mostrarFormularioDeRegistrarMedico(Map<String,Object> model) {
        Medico medico = new Medico();
        model.put("medico", medico);
        model.put("titulo", "Registro de medicos");
        return "formMedico";
    }

    @PostMapping("/formMedico")
    public String guardarMedico(@Valid Medico medico, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Registro de medicos");
            return "formMedico";
        }
        String mensaje = (medico.getId() != null) ? "El medico se ha actualizado correctamente." : "El medico se ha creado correctamente.";
        medicoService.save(medico);
        status.setComplete();
        flash.addFlashAttribute("sucess", mensaje);
        return "redirect:/listarMedicos";
    }

    @GetMapping("/formMedico/{id}")
    public String editarMedico(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Medico medico = null;
        if(id > 0) {
            medico = medicoService.findOne(id);
            if(medico == null) {
                flash.addFlashAttribute("error", "El ID del medico no existe en la base de datos");
                return "redirect:/listarMedicos";
            }
        } else {
            flash.addAttribute("error", "El ID del medico tiene que ser mayor a cero");
            return "redirect:/listarMedicos";
        }
        model.put("medico", medico);
        model.put("titulo", "Actualización de medicos");
        return "formMedico";
    }

    @GetMapping("/eliminarMedico/{id}")
    public String eliminarMedico(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0){
            medicoService.delete(id);
            flash.addFlashAttribute("success", "Medico eliminado con éxito");
        }
        return "redirect:/listarMedicos";
    }

    @GetMapping("/exportarMedicosPDF")
    public void exportarListadoDeMedicosEnPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Medicos_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Medico> medicos = medicoService.findAll();

        MedicoExporterPDF exporter = new MedicoExporterPDF(medicos);
        exporter.exportar(response);
    }

    @GetMapping("/exportarMedicosExcel")
    public void exportarListadoDeMedicosEnExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Medicos_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Medico> medicos = medicoService.findAll();

        MedicoExporterExcel exporter = new MedicoExporterExcel(medicos);
        exporter.exportar(response);
    }

}
