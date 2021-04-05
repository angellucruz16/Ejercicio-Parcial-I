package com.example.ejercicioparciali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private BufferedWriter bWriter;
    private ImageButton botonVerde,botonAmarillo,botonRojo;
    private EditText posX,posY,recordatorio;
    private Button botonPreview,botonConfirmar;
    private Boolean verdeIsSelected, amarilloIsSelected, rojoIsSelected;
    private String posicionX, posicionY, textoRecordatorio;
    private TCPSingleton tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonVerde = findViewById(R.id.botonVerde);
        botonAmarillo = findViewById(R.id.botonAmarillo);
        botonRojo = findViewById(R.id.botonRojo);
        posX = findViewById(R.id.posX);
        posY = findViewById(R.id.posY);
        recordatorio = findViewById(R.id.recordatorio);
        botonPreview = findViewById(R.id.botonPreview);
        botonConfirmar = findViewById(R.id.botonConfirmar);
        verdeIsSelected = false;
        amarilloIsSelected = false;
        rojoIsSelected = false;

        tcp = TCPSingleton.getInstance();
        tcp.start();

        //botones Importancia
        botonVerde.setOnClickListener(
                v-> {
                    verdeIsSelected = true;
                    amarilloIsSelected = false;
                    rojoIsSelected = false;
                    colorSeleccionado();

                }
        );
        botonAmarillo.setOnClickListener(
                v-> {
                    verdeIsSelected = false;
                    amarilloIsSelected = true;
                    rojoIsSelected = false;
                    colorSeleccionado();

                }
        );
        botonRojo.setOnClickListener(
                v-> {
                    verdeIsSelected = false;
                    amarilloIsSelected = false;
                    rojoIsSelected = true;
                    colorSeleccionado();

                }
        );
        //botones posicion
        botonPreview.setOnClickListener(
                v -> {
                    posicionX = posX.getText().toString();
                    posicionY = posY.getText().toString();
                    textoRecordatorio = recordatorio.getText().toString();

                    if (posicionX == null || posicionY == null || textoRecordatorio == null || (verdeIsSelected == false && amarilloIsSelected == false && rojoIsSelected == false)){
                       Toast.makeText(getApplicationContext(),"Tiene campos sin rellenar", Toast.LENGTH_SHORT).show();
                    } else {
                        botonConfirmar.setEnabled(true);
                    //    tcp.enviarMensaje(posicionX + " : " + posicionY);
                    //    tcp.enviarMensaje("recordatorio : " + textoRecordatorio);
                        String importancia = "";
                        if (verdeIsSelected == true){
                            //tcp.enviarMensaje("importanciaBaja");
                            importancia = "importanciaBaja";
                        } else if (amarilloIsSelected == true) {
                            //tcp.enviarMensaje("importanciaMedia");
                            importancia = "importanciaMedia";
                        } else {
                            //tcp.enviarMensaje("importanciaAlta");
                            importancia = "importanciaAlta";
                        }
                        tcp.enviarMensaje(posicionX + "," + posicionY +","+ textoRecordatorio + ","+ importancia);
                    }
                }
        );
        botonConfirmar.setOnClickListener(
                v -> {
                tcp.enviarMensaje("finalizar");
                }
        );

        //Conexión con eclipse
      /*  new Thread(
                ()->{
                    try {
                        Socket socket = new Socket ("192.168.1.12" , 6000);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bWriter = new BufferedWriter(osw);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start(); */
    }

    private void colorSeleccionado (){
        if (verdeIsSelected){
            botonVerde.setBackgroundResource(R.drawable.verde_selected);
        } else {
            botonVerde.setBackgroundResource(R.drawable.boton_verde);
        }

        if (amarilloIsSelected) {
            botonAmarillo.setBackgroundResource(R.drawable.amarillo_selected);
        } else {
            botonAmarillo.setBackgroundResource(R.drawable.boton_amarillo);
        }

        if (rojoIsSelected){
            botonRojo.setBackgroundResource(R.drawable.rojo_selected);

        } else {
            botonRojo.setBackgroundResource(R.drawable.boton_rojo);
        }
    }
    }