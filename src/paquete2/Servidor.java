/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete2;

import Clases.Consulta;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author josue
 */
public class Servidor {

    public static void main(String[] args) {

        Consulta c;
        ServerSocket servidor;
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket s1;

        try {
            servidor = new ServerSocket(12345);
            while (true) {
                System.out.println("Servidor en espera...");
                s1= servidor.accept();
                System.out.println("Conexion Aceptada");
                in = new ObjectInputStream(s1.getInputStream());
                c = (Consulta) in.readObject();
                System.out.println("Objeto Deserializado...");
                System.out.println();
                
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Excepcion " + ex.getMessage());
        } catch (IOException ex2) {
            System.out.println("Excepcion " + ex2.getMessage());
        } catch (ClassNotFoundException ex3) {
            System.out.println("Excepcion " + ex3.getMessage());
        }

    }

}
