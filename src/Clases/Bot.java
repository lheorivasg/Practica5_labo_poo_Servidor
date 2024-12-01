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

    public Bot() {
    }

    
    public Bot(Consulta c) {
        
    }

    
    
    /**
     * Procesa una consulta realizada al bot, realizando las siguientes
     * operaciones:
     * <ul>
     * <li>Elimina signos de puntuación del mensaje.</li>
     * <li>Elimina palabras vacías del mensaje.</li>
     * <li>Cuenta signos de puntuación, palabras vacías eliminadas y palabras
     * clave restantes.</li>
     * <li>Determina la respuesta adecuada basada en las palabras clave.</li>
     * <li>Calcula el tiempo de respuesta del bot en milisegundos.</li>
     * </ul>
     *
     * @param consulta
     * @return Un String con informacion detallada que incluye:
     * <ul>
     * <li>Número de palabras vacías eliminadas.</li>
     * <li>Número de palabras clave encontradas.</li>
     * <li>Número de signos de puntuación detectados.</li>
     * <li>La respuesta generada por el bot.</li>
     * <li>El tiempo que tomó generar la respuesta.</li>
     * </ul>
     */
    public String procesarConsulta(Consulta consulta) {
        if (consulta == null || consulta.getMensaje() == null) {
            return "Error: La consulta o el mensaje es nulo.";
        }
        String mensajeOriginal = consulta.getMensaje().toLowerCase();

        //Registro de tiempo de inicio
        LocalTime inicio = LocalTime.now();

        //Limpieza de texto
        String textoSinSignos = eliminarSignosPuntuacion(mensajeOriginal);
        int signosPuntuacion = contarSignosPuntuacion(mensajeOriginal);  
        String textoLimpio = eliminarPalabrasVacias(textoSinSignos);
        int palabrasVaciasEliminadas = contarPalabrasVaciasEliminadas(textoSinSignos, textoLimpio);
        int palabrasClave = contarPalabrasClave(textoLimpio);
        String respuesta = buscarRespuestaOptimizada(mensajeOriginal);

        
        
        LocalTime fin = LocalTime.now();
        long tiempoRespuesta = ChronoUnit.MILLIS.between(inicio, fin);
        
        
        return prepararRespuesta(signosPuntuacion, palabrasVaciasEliminadas, palabrasClave, respuesta, tiempoRespuesta);
    }
    
    
    /**
     * Metodo para eliminar signos de puntuacion
     * @param texto
     * @return Texto sin los signos de puntuacion
     */
    public String eliminarSignosPuntuacion(String texto) {
        if (texto == null) {
            System.err.println("Error: Mensaje nulo en eliminarSignosPuntuacion.");
            return "";
        }
        return texto.replaceAll("[,\\\\.¡!¿?()]", "");
    }

    
    
    /**
     * Metodo para contar la cantidad de signos de puntuacion del codigo original
     * @param texto
     * @return Numero de Signos de puntuacion en el texto
     */
    public int contarSignosPuntuacion(String texto){
        if(texto == null){
            System.out.println("Error: Texto nulo en contarSignosPuntuacion.");
            return 0;
        }
        return texto.length() - texto.replaceAll("[,\\\\.¡!¿?()]", "").length();
    }
    
    
    
    /**
     * Metodo para eliminar palabtas vacias de un texto
     * @param texto
     * @return Texto sin palabras vacias
     */
    public String eliminarPalabrasVacias(String texto) {
        if(texto == null){
            return "";
        }
        
        String[] palabrasVacias = {"la", "de", "el", "del", "para", "este", "los", "a", "cuando",
            "son", "con", "al", "como", "cual", "que", "y"};

        String regex = "\\b(" + String.join("|", palabrasVacias) + ")\\b";
        return texto.replaceAll(regex, "").replaceAll("\\s+", " ").trim();
    }

    /**
     * Metodo para identificar la cantidad de palabras vacias en el texto
     * 
     * @param textoOriginal
     * @param textoLimpio
     * @return Numero de palabras vacias
     */
    public int contarPalabrasVaciasEliminadas(String textoOriginal, String textoLimpio) {
        if(textoOriginal == null || textoLimpio == null){
            return 0;
        }
        
        int totalOriginal = textoOriginal.split("\\s+").length;
        int totalLimpio = textoLimpio.split("\\s+").length;
        return totalOriginal - totalLimpio;
    }

    
    /**
     * Metodo para identidicar las palabras clave dentro del texto
     *
     * @param textoLimpio
     * @return Numero de palabras clave
     */
    public int contarPalabrasClave(String textoLimpio) {
        if (textoLimpio == null || textoLimpio.isEmpty()) {
            return 0;
        }
        return textoLimpio.split("\\s+").length;
    }

    
    
    
    /**
     * Metodo para darle una respuesta predefinida al usuario de acuerdo a su
     * consulta
     *
     * @param textoLimpio
     * @return Texto de respuesta
     */
    public String buscarRespuestaOptimizada(String textoLimpio) {
        if (textoLimpio == null) {
            System.err.println("Error: Texto nulo en buscarRespuestaOptimizada.");
            return "Error: No se puede procesar la consulta.";
        }
        
        
        
        Map<String[], String> respuestas = new HashMap<>();
        respuestas.put(new String[]{"uam", "azcapotzalco"}, "La Universidad Autónoma Metropolitana Unidad Azcapotzalco actualmente cuenta con 17\n"
                + "licenciaturas, divididas en 3 áreas de estudio.\n"
                + "Visita: https://www.azc.uam.mx/");
        respuestas.put(new String[]{"uam"}, "Fundada en 1974 y con más de 200 mil personas egresadas, la UAM es una institución pública que\n"
                + "ofrece en sus cinco unidades académicas.\n"
                + "Visita: https://www.uam.mx/");
        respuestas.put(new String[]{"servicio", "medico"}, "Proporcionamos atención médica a los miembros de la comunidad universitaria. Contamos con 5\n"
                + "médicos y 4 consultorios, solo debes presentar tu credencial de la UAM.\n"
                + "Visita: https://csu.azc.uam.mx/medicos/");
        respuestas.put(new String[]{"servicios", "escolares"}, "La Coordinación de Sistemas Escolares, administra los procesos, entre los que se encuentran el\n"
                + "registro, seguimiento y control del ingreso, permanencia y egreso de los alumnos de Licenciatura y\n"
                + "Posgrado.\n"
                + "Visita: http://cse.azc.uam.mx/");
        respuestas.put(new String[]{"covid"}, "Es una nueva aplicación para conocer el estado de salud de la comunidad UAM en el regreso\n"
                + "presencial.\n"
                + "Visita: https://coviuam.uam.mx/");

        for (Map.Entry<String[], String> entry : respuestas.entrySet()) {
            String[] palabrasClave = entry.getKey();
            boolean contieneTodas = true;

            for (String palabra : palabrasClave) {
                if (!textoLimpio.contains(palabra)) {
                    contieneTodas = false;
                    break;
                }
            }
            if (contieneTodas) {
                return entry.getValue();
            }

        }

        int palabrasClave = contarPalabrasClave(textoLimpio);
        if (palabrasClave >= 3) {
            return "No tengo la informacion acerca de la consulta";
        } else {
            return "No entiendo la consulta :(";
        }

    }
    
    
    
    
    
    /**
     * Prepara la respuesta completa que sera devuelta al usuario.
     *
     * @param signosPuntuacion
     * @param palabrasVaciasEliminadas
     * @param palabrasClave
     * @param respuesta
     * @param tiempoRespuesta
     * @return Un String que incluye toda la informacion relevante del
     * procesamiento
     */
    public String prepararRespuesta(int signosPuntuacion, int palabrasVaciasEliminadas, int palabrasClave,
            String respuesta, long tiempoRespuesta) {
        return String.format(
                "Palabras vacías eliminadas: %d\n"
                + "Palabras clave: %d\n"
                + "Signos de puntuación: %d\n"
                + "Respuesta: %s\n"
                + "Tiempo de respuesta: %d ms",
                palabrasVaciasEliminadas, palabrasClave, signosPuntuacion, respuesta, tiempoRespuesta);
    }

}
