/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * 
 * @author Alumno11
 * @version 
 */
public class Bot extends Consulta {

    public Bot(String mensaje) {
        super(mensaje);
    }
    
    
    public String procesarConsulta(Consulta consulta){
        String mensajeOriginal = consulta.getMensaje();
        
        //Registro de tiempo de inicio
        LocalTime inicio = LocalTime.now();
        
        
        //Limpieza de texto
        String textoSinSignos = eliminarSignosPuntuacion(mensaje);
        String textoLimpio = eliminarPalabrasVacias(textoSinSignos);
        int signosPuntuacion = contarSignosPuntuacion(mensajeOriginal);
        int palabrasVaciasEliminadas = contarPalabrasVaciasEliminadas(mensajeOriginal, textoLimpio);
        int palabrasClave = contarPalabrasClave(textoLimpio);
        String respuesta = buscarRespuestaOptimizada(textoLimpio);
        
        
        LocalTime fin = LocalTime.now();
        long tiempoRespuesta = ChronoUnit.MILLIS.between(inicio, fin);
        
        
        return prepararRespuesta(signosPuntuacion, palabrasVaciasEliminadas, palabrasClave, respuesta, tiempoRespuesta);
    }
    
    
    /**
     * Metodo para eliminar signos de puntuacion
     * @param texto
     * @return Texto sin los signos de puntuacion
     */
    public String eliminarSignosPuntuacion(String texto){
        return texto.replaceAll("[,\\\\.¡!¿?()]", "");
    }
    
    
    
    /**
     * Metodo para contar la cantidad de signos de puntuacion del codigo original
     * @param texto
     * @return 
     */
    public int contarSignosPuntuacion(String texto){
        return texto.length() - texto.replaceAll("[,\\\\.¡!¿?()]", "").length();
    }
    
    
    
    /**
     * 
     * @param texto
     * @return 
     */
    public String eliminarPalabrasVacias(String texto){
        String[] palabrasVacias = {"la", "de", "el", "del", "para", "este", "los", "a", "cuando",
                                   "son", "con", "al", "como", "cual", "que", "y"};
        
        String regex = "\\b(" + String.join("|", palabrasVacias) + ")\\b";
        return texto.replaceAll(regex, "").replaceAll("\\s+", " ").trim();
    }
    
    
    /**
     * 
     * @param textoOriginal
     * @param textoLimpio
     * @return 
     */
    public int contarPalabrasVaciasEliminadas(String textoOriginal, String textoLimpio){
        int totalOriginal = textoOriginal.split("\\s+").length;
        int totalLimpio = textoLimpio.split("\\s+").length;
        return totalOriginal - totalLimpio;
    }
    
    
    
    /**
     * 
     * @param textoLimpio
     * @return 
     */
    public int contarPalabrasClave(String textoLimpio){
        if(textoLimpio.isEmpty()){
            return 0;
        }
        return textoLimpio.split("\\s+").length;
    }
    
    
    
    
    
    /**
     * 
     * @param textoLimpio
     * @return 
     */
    public String buscarRespuestaOptimizada(String textoLimpio){
        Map<String[], String> respuestas = new HashMap<>();
        respuestas.put(new String[]{"uam", "azcapotzalco"}, "");
        respuestas.put(new String[]{"uam"}, "");
        respuestas.put(new String[]{"servicio", "medico"}, "");
        respuestas.put(new String[]{"servicios", "escolares"}, "");
        respuestas.put(new String[]{"covid", "escolares"}, "");
        
        for(Map.Entry<String[], String> entry : respuestas.entrySet()){
            String[] palabrasClave = entry.getKey();
            boolean contieneTodas = true;
            
            for(String palabra : palabrasClave){
                if(!textoLimpio.contains(palabra)){
                    contieneTodas = false;
                    break;
                }
            }
            if(contieneTodas){
                return entry.getValue();
            }
            
        }
        
        int palabrasClave = contarPalabrasClave(textoLimpio);
        if(palabrasClave >= 3){
            return "No tengo la informacion acerca de la consulta";
        }else{
            return "No entiendo la consulta :(";
        }

    }
    
    
    
    
    
    /**
     * 
     * @return 
     */
    public String prepararRespuesta(int signosPuntuacion, int palabrasVaciasEliminadas, int palabrasClave,
                                    String respuesta, long tiempoRespuesta){
    
           return String.format(
                   "Palabras vacías eliminadas: %d\\nPalabras clave: %d\\nSignos de puntuación: %d\\nRespuesta: %s\\nTiempo de respuesta: %d ms",
                    palabrasVaciasEliminadas, palabrasClave, signosPuntuacion, respuesta, tiempoRespuesta);
    
    }
}
