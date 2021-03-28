package main;

import processing.core.PApplet;

public class Recordatorio{

	private int posX, posY;
	private String importancia;
	private String texto;
	private PApplet app;
	
	public Recordatorio(int posX, int posY, String texto, String importancia, PApplet app)  {
		
		super();
		this.posX = posX;
		this.posY = posY;
		this.importancia = importancia;
		this.texto = texto;
		this.app = app;
	}
	
	public void pintar () {
		
		this.app.stroke(104,120,140);
		this.app.strokeWeight(2);
		this.app.noFill();
		this.app.rect(this.posX, this.posY, 250, 50);
		
		switch (importancia) {
		case "importanciaBaja":
			
			this.app.fill(101,170,11);
			
			
			break;
		case "importanciaMedia":
			
			this.app.fill(255,177,39);
			
			break;
		case "importanciaAlta":
			
			this.app.fill(255,54,39);
			
			break;
		}
		
		this.app.noStroke();
		this.app.ellipse(this.posX, this.posY, 30, 30);
		this.app.text(this.texto, this.posX + 15, this.posY + 30);
	
	}
	
}
