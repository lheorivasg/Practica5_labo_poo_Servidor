/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete2;

import Clases.Bot;
import Clases.Consulta;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



/**
 *
 * @author josue
 */
public class Servidor{

    public static void main(String[] args) {

        ServerSocket servidor = null;

        try {
            // Crear el servidor en el puerto 12345
            servidor = new ServerSocket(12345);
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                // Esperar conexión de un cliente
                Socket cliente = servidor.accept();
                System.out.println("Conexión aceptada de: " + cliente.getInetAddress().getHostAddress());

                // Streams para comunicación
                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());

                // Leer la consulta del cliente
                Consulta consulta = (Consulta) in.readObject();
                System.out.println("Consulta recibida: " + consulta);

                // Procesar la consulta usando el Bot
                Bot bot = new Bot();
                String respuesta = bot.procesarConsulta(consulta);

                // Enviar respuesta al cliente
                out.writeObject(respuesta);
                out.flush();
                System.out.println("Respuesta enviada al cliente.");

                // Cerrar recursos
                in.close();
                out.close();
                cliente.close();
                System.out.println("Conexión cerrada con el cliente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            if (servidor != null) {
                try {
                    servidor.close();
                    System.out.println("Servidor cerrado.");
                } catch (IOException e) {
                    System.err.println("Error al cerrar el servidor: " + e.getMessage());
                }
            }
        }
    }

}
