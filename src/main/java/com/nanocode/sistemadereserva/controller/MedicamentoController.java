package com.nanocode.sistemadereserva.controller;

import com.nanocode.sistemadereserva.entity.Medicamento;
import com.nanocode.sistemadereserva.service.MedicamentoService;
import com.nanocode.sistemadereserva.util.paginacion.PageRender;
import com.nanocode.sistemadereserva.util.reportes.MedicamentoExporterExcel;
import com.nanocode.sistemadereserva.util.reportes.MedicamentoExporterPDF;
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
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/verMedicamento/{id}")
    public String verHistorialClinicoDelMedicamento(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Medicamento medicamento = medicamentoService.findOne(id);
        if(medicamento == null) {
            flash.addFlashAttribute("error", "El medicamento no existe en la base de datos.");
            return "redirect:/listarMedicamentos";
        }
        model.put("medicamento", medicamento);
        model.put("titulo", "Detalle del medicamento: " + medicamento.getNombreComercial());
        return "verMedicamento";
    }

    @GetMapping({"/listarMedicamentos"})
    public String listarMedicamentos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Medicamento> medicamentos = medicamentoService.findAll(pageRequest);
        PageRender<Medicamento> pageRender = new PageRender<>("/listarMedicamentos", medicamentos);

        model.addAttribute("titulo","Listado de medicamentos");
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("page", pageRender);


        return "listarMedicamentos";
    }

    @GetMapping("/formMedicamento")
    public String mostrarFormularioDeRegistrarMedicamento(Map<String,Object> model) {
        Medicamento medicamento = new Medicamento();
        model.put("medicamento", medicamento);
        model.put("titulo", "Registro de medicamentos");
        return "formMedicamento";
    }

    @PostMapping("/formMedicamento")
    public String guardarMedicamento(@Valid Medicamento medicamento, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if(result.hasErrors()) {
            model.addAttribute("titulo", "Registro de medicamentos");
            return "formMedicamento";
        }
        String mensaje = (medicamento.getId() != null) ? "El medicamento se ha actualizado correctamente." : "El medicamento se ha creado correctamente.";
        medicamentoService.save(medicamento);
        status.setComplete();
        flash.addFlashAttribute("sucess", mensaje);
        return "redirect:/listarMedicamentos";
    }

    @GetMapping("/formMedicamento/{id}")
    public String editarMedicamento(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Medicamento medicamento = null;
        if(id > 0) {
            medicamento = medicamentoService.findOne(id);
            if(medicamento == null) {
                flash.addFlashAttribute("error", "El ID del medicamento no existe en la base de datos");
                return "redirect:/listarMedicamentos";
            }
        } else {
            flash.addAttribute("error", "El ID del medicamento tiene que ser mayor a cero");
            return "redirect:/listarMedicamentos";
        }
        model.put("medicamento", medicamento);
        model.put("titulo", "Actualización de medicamentos");
        return "formMedicamento";
    }

    @GetMapping("/eliminarMedicamento/{id}")
    public String eliminarMedicamento(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0){
            medicamentoService.delete(id);
            flash.addFlashAttribute("success", "Medicamento eliminado con éxito");
        }
        return "redirect:/listarMedicamentos";
    }

    @GetMapping("/exportarMedicamentosPDF")
    public void exportarListadoDeMedicamentosEnPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Medicamentos_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Medicamento> medicamentos = medicamentoService.findAll();

        MedicamentoExporterPDF exporter = new MedicamentoExporterPDF(medicamentos);
        exporter.exportar(response);
    }

    @GetMapping("/exportarMedicamentosExcel")
    public void exportarListadoDeMedicamentosEnExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Medicamentos_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Medicamento> medicamentos = medicamentoService.findAll();

        MedicamentoExporterExcel exporter = new MedicamentoExporterExcel(medicamentos);
        exporter.exportar(response);
    }

}
