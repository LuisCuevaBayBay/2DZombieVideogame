package com.company;

public final class pantalla {
    public final int ancho;
    public final int alto;

    public final int[] pixeles;

    //Temporal
    private final static int SPRITE_SIZE = 32;
    private final static int MASCARA_SPRITE = SPRITE_SIZE - 1;
    //Fin Temporal

    public pantalla(final int ancho, final int alto){
        this.alto = alto;
        this.ancho = ancho;

        pixeles = new int[ancho*alto];
    }


    public void limpiar(){
        for (int i = 0; i<pixeles.length;i++){
            pixeles[i] = 0;
        }
    }

    public void mostrar(final int compensacionX, final int compensacionY){
        for(int y = 0; y<alto;y++){
            int posicionY = y+compensacionY;
            if (posicionY<0||posicionY>=alto) {
                continue;
            }
            for (int x=0; x<ancho;x++){
                int posicionX = x+compensacionX;
                if (posicionX<0||posicionX>=ancho){
                    continue;
                }
                //temporal
                pixeles[posicionX + posicionY * ancho] = Sprites.background.pixeles[(x & MASCARA_SPRITE)+(y & MASCARA_SPRITE)* SPRITE_SIZE];

            }
        }
    }
}
