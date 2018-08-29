package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.*;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;

    private static  final int ANCHO = 800;
    private static  final int ALTO = 800;

    private static volatile boolean EnFuncionamiento = false;

    private static final String NOMBRE = "Game";

    private static int APS = 0;
    private static int FPS = 0;

    private static int x = 0;
    private static int y = 0;

    private static JFrame ventana;
    private static Thread thread;
    private static teclado teclado1;
    private static pantalla pantalla;

    private static BufferedImage image = new BufferedImage(ANCHO,ALTO,BufferedImage.TYPE_INT_RGB);
    private static int[] pixeles = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(){
        setPreferredSize(new Dimension(ANCHO,ALTO));

        pantalla = new pantalla(ANCHO,ALTO);

        teclado1 = new teclado();
        addKeyListener(teclado1);
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
    public synchronized void iniciar(){
        EnFuncionamiento = true;

        thread = new Thread(this, "Graficos");
        thread.start();
    }

    public synchronized void detener(){
        EnFuncionamiento = false;

        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    private void actualizar(){
        teclado1.actualizar();
        if (teclado1.arriba){
            System.out.println("Funcion arriba: ACTIVADA!!!!");
        }
        if (teclado1.abajo){
            System.out.println("Funcion abajo: ACTIVADA!!!!");
        }
        if (teclado1.derecha){
            System.out.println("Funcion derecha: ACTIVADA!!!!");
        }
        if (teclado1.izquierda){
            System.out.println("Funcion Izquierda: ACTIVADA!!!!");
        }

        APS ++;

    }
    private void mostrar(){
        BufferStrategy estrategia = getBufferStrategy();

        if(estrategia == null){
            createBufferStrategy(3);
            return;
        }

        pantalla.limpiar();
        pantalla.mostrar(x,y);

       // for(int i = 0; i<pixeles.length;i++){
         //   pixeles[i] = pantalla.pixeles[i];
        //}

        System.arraycopy(pantalla.pixeles,0,pixeles,0,pixeles.length);

        Graphics g = estrategia.getDrawGraphics();
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.dispose();

        estrategia.show();

        FPS++;
    }

    public void run() {
        final int NS_X_SEGUNDOS = 1000000000;
        final byte APS_OBJETIVO = 60;
        final double NS_X_ACTUALIZACION = NS_X_SEGUNDOS/APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido;
        double delta = 0;

        requestFocus();

        while (EnFuncionamiento){
            final long inicioBucle = System.nanoTime();

            tiempoTranscurrido= inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;

            delta += tiempoTranscurrido / NS_X_ACTUALIZACION;

            while (delta>=1){
                actualizar();
                delta--;
            }
            mostrar();

            if(System.nanoTime() - referenciaContador > NS_X_SEGUNDOS){
                ventana.setTitle("NOMBRE: "+ "| APS: "+APS+" || FPS: "+FPS);
                APS = 0;
                FPS = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }
}
