/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alison
 */
public class Servidor {
    
    ServerSocket serverSocket;
    Socket socket;
    
    public Servidor(int porta) throws IOException{
    
    serverSocket = new ServerSocket(porta);
    
    }
    
    public Socket retornar() throws IOException{
        
        socket = serverSocket.accept();
    
        return socket;
    }
    
}
