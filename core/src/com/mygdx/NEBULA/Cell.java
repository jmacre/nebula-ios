package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;

public class Cell {
    float x, y, width, height;
    Texture texture;
    int cellType;
    Button button;
    float dimensionRatio;

//    public static final int CELL_BUTTON = 0;
//    public static final int CELL_TEXTURE = 1;


    public Cell(Button button, float dimensionRatio){
//        if(type == CELL_BUTTON) {
//            this.cellType = CELL_BUTTON;
        this.button = button;
        this.texture = button.getTexture();
        this.dimensionRatio = dimensionRatio;
//            this.button = null;

    }
    public Cell(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getCellType(){
        return cellType;
    }
    public void setCellButton(Button button){
        this.button = button;
    }
    public Button getCellButton(){
        return this.button;
    }

    public float getDimensionRatio() {
        return dimensionRatio;
    }

    public void setDimensionRatio(float dimensionRatio) {
        this.dimensionRatio = dimensionRatio;
    }
}
