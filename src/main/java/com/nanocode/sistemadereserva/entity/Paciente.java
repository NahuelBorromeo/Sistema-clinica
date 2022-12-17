package com.nanocode.sistemadereserva.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pacientes")
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String nombre;

  @NotEmpty
  private String apellido;

  @NotEmpty
  @Email
  private String email;

  @NotNull
  private int telefono;

  @NotEmpty
  private String sexo;

  @NotNull
  private int documento;

  @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
  private Set<HistoriaClinica> historiaClinicas = new HashSet<>();

  @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
  private Set<Cita> citas = new HashSet<>();

  public Paciente() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getTelefono() {
    return telefono;
  }

  public void setTelefono(int telefono) {
    this.telefono = telefono;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public int getDocumento() {
    return documento;
  }

  public void setDocumento(int documento) {
    this.documento = documento;
  }

  public Set<HistoriaClinica> getHistoriaClinicas() {
    return historiaClinicas;
  }

  public void setHistoriaClinicas(Set<HistoriaClinica> historiaClinicas) {
    this.historiaClinicas = historiaClinicas;
    for(HistoriaClinica historiaClinica : historiaClinicas) {
      historiaClinica.setPaciente(this);
    }
  }

  public Set<Cita> getCitas() {
    return citas;
  }

  public void setCitas(Set<Cita> citas) {
    this.citas = citas;
    for(Cita cita : citas) {
      cita.setPaciente(this);
    }
  }
}
