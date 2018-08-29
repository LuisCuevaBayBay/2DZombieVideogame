package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class SpritesSheet {
    public final int ancho;
    public final int alto;
    public final int[] pixeles;

    //coleccion hojas de sprites

    public static SpritesSheet background = new SpritesSheet("sprites/background.png",320,320);

    //fin de la coleccion

    public SpritesSheet(final String ruta, final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;

        pixeles = new int[ancho * alto];

        BufferedImage imagen;
        try{
            imagen= ImageIO.read(SpritesSheet.class.getResource(ruta));
            imagen.getRGB(0,0,ancho,alto,pixeles,0,ancho);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public int obtenAncho(){
        return ancho;
    }

}
