package com.nanocode.sistemadereserva.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "historiaclinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @JoinColumn(name = "id_paciente")
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    private Paciente paciente;

    @NotEmpty
    private String motivoConsulta;

    @NotEmpty
    private String antecedentes;

    @NotEmpty
    private String enfermedadActual;

    public HistoriaClinica() {
    }

    public HistoriaClinica(Date fecha, Paciente paciente, String motivoConsulta, String antecedentes, String enfermedadActual) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.motivoConsulta = motivoConsulta;
        this.antecedentes = antecedentes;
        this.enfermedadActual = enfermedadActual;
    }

    public HistoriaClinica(Long id, Date fecha, Paciente paciente, String motivoConsulta, String antecedentes, String enfermedadActual) {
        this.id = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.motivoConsulta = motivoConsulta;
        this.antecedentes = antecedentes;
        this.enfermedadActual = enfermedadActual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getEnfermedadActual() {
        return enfermedadActual;
    }

    public void setEnfermedadActual(String enfermedadActual) {
        this.enfermedadActual = enfermedadActual;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
