package com.company;

public final class Sprites {

    private final int size;

    private int x;
    private int y;

    public int[] pixeles;

    private final SpritesSheet hoja;
    //coleccion de sprites

    public static Sprites background = new Sprites(32,0,0, SpritesSheet.background);

    //fin de la coleccion

    public Sprites(final int size, final int columna, final int fila, final SpritesSheet hoja){
        this.size = size;

        pixeles = new int[this.size*this.size];

        this.x = columna*size;
        this.y = size*fila;
        this.hoja = hoja;

        for (int y=0; y<size; y++){
            for (int x= 0; x<size; x++){
                pixeles[x+y*size] = hoja.pixeles[(x+this.x)+(y+this.y)* hoja.obtenAncho()];
            }
        }
    }
}
