package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import processing.core.PApplet;

public class Main extends PApplet {

	private Socket socket;
	private BufferedReader breader;
	private ArrayList<Recordatorio> listaRecordatorios;
	private Recordatorio vistaPrevia;
	
	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	// 
	public void settings() {
		size(800, 800);

	}

	// 
	public void setup() {

		listaRecordatorios = new ArrayList<Recordatorio>();

		new Thread(() -> {
			try {
				Gson gson = new Gson();
				ServerSocket server = new ServerSocket(6000);
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Cliente esta conectado");

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				InputStreamReader isr = new InputStreamReader(is);
				breader = new BufferedReader(isr);
				

				while (true) {
					
					// Esperando mensaje
					System.out.println("Esperando mensaje...");
					String mensajeRecibido = breader.readLine();
					System.out.println(mensajeRecibido);
					String mensaje = gson.fromJson(mensajeRecibido, String.class); //Des serializando
					String[] caracteristicas = split(mensaje,",");
					
					System.out.println(mensaje);

					if (mensaje.equals("finalizar")) {
						listaRecordatorios.add(vistaPrevia);
						vistaPrevia = null;
					} else {
						vistaPrevia = new Recordatorio(Integer.parseInt(caracteristicas[0]), Integer.parseInt(caracteristicas[1]), caracteristicas[2], caracteristicas[3], this);
					}
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
				
	}

				
	public void draw() {
		background(255,255,255);
		if (vistaPrevia != null) {
		vistaPrevia.pintar();
		
		}
		for (int i = 0; i < listaRecordatorios.size(); i++) {
			listaRecordatorios.get(i).pintar();
			
		}
	}

}