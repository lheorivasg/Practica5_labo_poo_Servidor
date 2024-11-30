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

    public Consulta( String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String Consultar(String mensaje){
        hora=LocalTime.now();
        
        return mensaje +" "+ hora;   
    }
    
}
