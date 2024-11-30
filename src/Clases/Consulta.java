/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author Alumno11
 */
public class Consulta implements Serializable{
    protected LocalTime hora;
    protected String mensaje;

    public Consulta() {
    }

    public Consulta( String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Consulta: "+ mensaje + " hora: " + hora;
    }
    
    
    
    public String registrarConsulta(String consulta) {
        this.hora = LocalTime.now(); // Asigna la hora actual
        this.mensaje = consulta; // Asigna el mensaje recibido
        // Devuelve el mensaje con la hora
        return "Consulta registrada a las " + hora + ": " + mensaje;
    }
    
}
