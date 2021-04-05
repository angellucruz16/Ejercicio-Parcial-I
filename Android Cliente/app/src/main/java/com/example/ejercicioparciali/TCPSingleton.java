package com.example.ejercicioparciali;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCPSingleton extends Thread {
    private static  TCPSingleton unicainstancia;

    public static TCPSingleton getInstance(){

        if (unicainstancia == null){
            unicainstancia = new TCPSingleton();
        }
        return unicainstancia;
    }

    private TCPSingleton(){
    }

    private Socket socket;
    private BufferedWriter bWriter;

    @Override
    public void run () {
            try {
                socket = new Socket("192.168.1.12", 6000);
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);

                bWriter = new BufferedWriter(osw);

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void enviarMensaje (String mensaje){
        new Thread(
                ()->{
                    try {
                        Gson gson = new Gson();
                        String mensajeFinal = gson.toJson(mensaje); //Serializando
                        bWriter.write(mensajeFinal + "\n");
                        bWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
    }




