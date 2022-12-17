package com.nanocode.sistemadereserva.controller;

import com.nanocode.sistemadereserva.entity.HistoriaClinica;
import com.nanocode.sistemadereserva.entity.Paciente;
import com.nanocode.sistemadereserva.repository.HistoriaClinicaRepository;
import com.nanocode.sistemadereserva.repository.PacienteRepository;
import com.nanocode.sistemadereserva.service.HistoriaClinicaService;
import com.nanocode.sistemadereserva.service.PacienteService;
import com.nanocode.sistemadereserva.util.paginacion.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteRepository pacienteRepository;


    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @GetMapping("/formHistoriaClinica")
    public String mostrarFormularioDeRegistrarHistoriaClinica(Map<String,Object> model) {
        HistoriaClinica historiaClinica = new HistoriaClinica();

        Paciente paciente = pacienteService.findOne(PacienteController.ID_VALUE);
        historiaClinica.setPaciente(paciente);
        System.out.println(PacienteController.ID_VALUE);
        //model.put("pacienteID",PacienteController.ID_VALUE);
        model.put("historiaClinica",historiaClinica);
        model.put("titulo", "Registro de historiaClinicas");
        return "formHistoriaClinica";
    }

    @PostMapping("/formHistoriaClinica")
    public String guardarHistoriaClinica(@Valid HistoriaClinica historiaClinica, BindingResult result, Model model,RedirectAttributes flash, SessionStatus status) {
        Long idPaciente = PacienteController.ID_VALUE;
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Registro de historiaClinicas");
            return "formHistoriaClinica";
        }
        String mensaje = (historiaClinica.getId() != null) ? "La historiaClinica se ha actualizado correctamente." : "La historiaClinica se ha creado correctamente.";

        if(historiaClinica.getId() == null){
            Paciente paciente = pacienteService.findOne(PacienteController.ID_VALUE);
            historiaClinica.setPaciente(paciente);
            System.out.println(PacienteController.ID_VALUE);
        }

        historiaClinicaService.save(historiaClinica);
        status.setComplete();
        flash.addFlashAttribute("sucess", mensaje);
        return "redirect:/verPaciente/" + idPaciente;
    }

    @GetMapping(value = {"/eliminarHistoriaClinica/{id}"})
    public String eliminarHistoriaClinica(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        HistoriaClinica historiaClinica = historiaClinicaService.findOne(id);

        Long idPaciente = historiaClinica.getPaciente().getId();
        if(id > 0){
            historiaClinicaService.delete(id);
            flash.addFlashAttribute("success", "HistoriaClinica eliminado con éxito");
        }
        return "redirect:/verPaciente/" + idPaciente;
    }

    @GetMapping("/formHistoriaClinica/{id}")
    public String editarHistorialClinicaDelPaciente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        HistoriaClinica historiaClinica  = historiaClinicaService.findOne(id);
        Paciente paciente = historiaClinica.getPaciente();
        model.put("historiaClinica",historiaClinica);
        model.put("paciente",paciente);
        model.put("titulo", "Actualización de historial clinica");
        return "formHistoriaClinica";
    }

}
