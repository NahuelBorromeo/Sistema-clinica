package com.nanocode.sistemadereserva.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "citas")
public class Cita {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private int documento;

  @NotNull
  private int nrodoctor;

  @NotEmpty
  private String especialidad;

  @NotNull
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fecha;

  @NotNull
  @Temporal(TemporalType.TIME)
  @DateTimeFormat(pattern = "hh:mm")
  private Date hora;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "idPaciente")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Paciente paciente;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "idMedico")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Medico medico;


  public Cita() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getDocumento() {
    return documento;
  }

  public void setDocumento(int documento) {
    this.documento = documento;
  }

  public int getNrodoctor() {
    return nrodoctor;
  }

  public void setNrodoctor(int nrodoctor) {
    this.nrodoctor = nrodoctor;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Date getHora() {
    return hora;
  }

  public void setHora(Date hora) {
    this.hora = hora;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Medico getMedico() {
    return medico;
  }

  public void setMedico(Medico medico) {
    this.medico = medico;
  }

  public String getEspecialidad() {
    return especialidad;
  }

  public void setEspecialidad(String especialidad) {
    this.especialidad = especialidad;
  }
}
