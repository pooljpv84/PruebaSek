package com.app.pruebasek.modelos;

public class Matricula
{
String idMatricula;
String idHorario;
String idMateria;
String idEstudiante;


    public Matricula(String idMatricula, String idHorario, String idMateria, String idEstudiante) {
        this.idMatricula = idMatricula;
        this.idHorario = idHorario;
        this.idMateria = idMateria;
        this.idEstudiante = idEstudiante;
    }

    public Matricula() {
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}
