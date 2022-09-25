package com.mygdx.NEBULA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.NEBULA.GameElements.SCREEN_HEIGHT;

public class Button {
    private float height, width, x, y, extendXTapBy, extendYTapBy;
    private boolean tapped, focused;
    private Texture texture;

    public Button(Texture texture, float x, float y, float width, float height){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.extendXTapBy = 0;
        this.extendYTapBy = 0;
    }
    public Button(Texture texture, float x, float y, float width, float height, float extendXTapBy, float extendYTapBy){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.extendXTapBy = extendXTapBy;
        this.extendYTapBy = extendYTapBy;
    }
    private boolean checkFocused(int tapX, int tapY, float extendXTapBy, float extendYTapBy){
        this.focused = (Gdx.input.isTouched()) && tapX < this.x + this.width + extendXTapBy && tapX > this.x - extendXTapBy
                && SCREEN_HEIGHT - tapY < this.y + this.height + extendYTapBy && SCREEN_HEIGHT - tapY > this.y - extendYTapBy;
        return this.focused;
    }

    private boolean checkTapped(int tapX, int tapY, float extendXTapBy, float extendYTapBy){
        this.focused = (Gdx.input.isTouched()) && tapX < this.x + this.width + extendXTapBy && tapX > this.x - extendXTapBy
                && SCREEN_HEIGHT - tapY < this.y + this.height + extendYTapBy && SCREEN_HEIGHT - tapY > this.y - extendYTapBy;

        this.tapped = true;

        return this.tapped;
    }
    private boolean getReleased(int tapX, int tapY, float extendXTapBy, float extendYTapBy){
        if (!(tapX < this.x + this.width + extendXTapBy || !(tapX > this.x - extendXTapBy) ||
                !(SCREEN_HEIGHT - tapY < this.y + this.height + extendYTapBy) || !(SCREEN_HEIGHT - tapY > this.y - extendYTapBy)))
            this.tapped = false;

        if(!Gdx.input.isTouched()) {
            if ((tapX < this.x + this.width + extendXTapBy) || !(tapX > this.x - extendXTapBy) ||
                    !(SCREEN_HEIGHT - tapY < this.y + this.height + extendYTapBy) || !(SCREEN_HEIGHT - tapY > this.y - extendYTapBy))
                return this.tapped;
        }
        return false;
    }

    public boolean getTapped(){ //Runs after finger is released
        return this.getTappedBefore() && this.getReleased();
    }

    public boolean getTappedBefore(){
        if (Gdx.input.isTouched() && this.checkFocused(Gdx.input.getX(), Gdx.input.getY(), extendXTapBy, extendYTapBy))
            this.focused = true;

        if(Gdx.input.justTouched() && this.focused && this.checkTapped(Gdx.input.getX(), Gdx.input.getY(),extendXTapBy, extendYTapBy))
            this.tapped = true;
        if(!this.focused)
            this.tapped = false;

        return this.tapped;
    }

    public boolean getReleased(){ //Checks if button getTapBefore() finished successfully (situational)
        if(getReleased(Gdx.input.getX(), Gdx.input.getY(), extendXTapBy, extendYTapBy)) {
            this.tapped = false;
            return true;
        }
        return false;
    }

    public Texture getTexture(){
        return this.texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getExtendXTapBy() {
        return extendXTapBy;
    }

    public void setExtendXTapBy(float extendXTapBy) {
        this.extendXTapBy = extendXTapBy;
    }

    public float getExtendYTapBy() {
        return extendYTapBy;
    }

    public void setExtendYTapBy(float extendYTapBy) {
        this.extendYTapBy = extendYTapBy;
    }
}
