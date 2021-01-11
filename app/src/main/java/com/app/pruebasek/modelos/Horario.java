package com.app.pruebasek.modelos;

public class Horario
{
String idHorario;
String dia;
String hora;
String idMateria;

    public Horario(String idHorario, String dia, String hora, String idMateria) {
        this.idHorario = idHorario;
        this.dia = dia;
        this.hora = hora;
        this.idMateria = idMateria;
    }

    public Horario() {
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }
}
