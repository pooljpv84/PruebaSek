package com.app.pruebasek.modelos;

public class Estudiante
{
    String idEstudiante;
    String nombresEstudiante;
    String apellidosEstudiante;
    String telefonoEstudiante;

    public Estudiante() {
    }

    public Estudiante(String idEstudiante, String nombresEstudiante, String apellidosEstudiante, String telefonoEstudiante) {
        this.idEstudiante = idEstudiante;
        this.nombresEstudiante = nombresEstudiante;
        this.apellidosEstudiante = apellidosEstudiante;
        this.telefonoEstudiante = telefonoEstudiante;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombresEstudiante() {
        return nombresEstudiante;
    }

    public void setNombresEstudiante(String nombresEstudiante) {
        this.nombresEstudiante = nombresEstudiante;
    }

    public String getApellidosEstudiante() {
        return apellidosEstudiante;
    }

    public void setApellidosEstudiante(String apellidosEstudiante) {
        this.apellidosEstudiante = apellidosEstudiante;
    }

    public String getTelefonoEstudiante() {
        return telefonoEstudiante;
    }

    public void setTelefonoEstudiante(String telefonoEstudiante) {
        this.telefonoEstudiante = telefonoEstudiante;
    }
}
