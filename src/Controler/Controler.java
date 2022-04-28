/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Model.Servidor;
import View.Avisos;
import View.TelaServidor;
import View.TelaServiço;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alison
 */
public class Controler implements ActionListener{
    
    Servidor servidor;
    TelaServidor telaServidor;
    TelaServiço telaServiço;
    Avisos avisos;
    String pasta = "root";
    public Controler(TelaServidor telaServidor){
    
        this.telaServidor = telaServidor;
        telaServidor.getIniciarButton().addActionListener(this);
        avisos = new Avisos();
    }

    
    public void buscarArquivo(String caminho){
        
        String fileName = "."+caminho;
        FileInputStream file = null;
        boolean fileExits = true;
        
        try {
            file = new FileInputStream(pasta+fileName);         
           } catch (FileNotFoundException ex) {
            fileExits = false;
        }
        
        System.out.println(fileExits);
    
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()== telaServidor.getIniciarButton()){
            int porta = Integer.parseInt(telaServidor.getPortaField().getText());
            try {
                servidor = new Servidor(porta);
                telaServidor.setVisible(false);
                telaServiço = new TelaServiço();
                Ouvir ouvir = new Ouvir();
                new Thread(ouvir).start();
            } catch (IOException ex) {
                avisos.portaEmUso();
            }
            
        }
                
        
    }

    public class Ouvir implements Runnable{
        
        Socket socket;
        
        public Ouvir() {
        
            
        }

        
        
        
        @Override
        public void run() {
        
            while(true){
            
                try {
                    socket = servidor.retornar();
                    Request request = new Request(socket);
                    new Thread(request).start();
                } catch (IOException ex) {
                    Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            
            
        }
    
        
    public class Request implements Runnable{
        
            BufferedReader in;
            String[] vetorString;
            public boolean conexao=false;
            String requisicao ="";
            String caminho ="";
            String Protocol ="";
            public Request(Socket socket) {
                
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException ex) {
                    Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
     
            @Override
            public void run() {
                
                int cont = 0;
                try {
                    
                    while((requisicao=in.readLine()) != null){ 
                        
                        if(cont == 0){
                        
                            vetorString = requisicao.split(" ");
                           
                            requisicao = vetorString[0];
                            caminho = vetorString[1];
                            Protocol = vetorString[2];
                            cont =1;
                            
                        }
                        
                        buscarArquivo(caminho);
                    }
                  
                  
                } catch (IOException ex) {
                    Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
            }
    
   
    
    
    }
    
    
    
    }

    
}

