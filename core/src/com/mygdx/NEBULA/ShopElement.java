package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.Anim.DEFAULT_FRAME_DURATION;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class ShopElement extends GameElements{
    public static final int SHIP_ID = 0;

    public static final int MAIN_SHIP_ID = 0;
    public static final int RED_SHIP_ID = 1;
    public static final int BLACK_SHIP_ID = 2;
    public static final int PURPLE_SHIP_ID = 3;

    float x, y, width, height;

    static Sprite mainShip, blackShip, redShip, purpleShip;

    public static int shipCount = 3; // one less than actual count for cycling between ships
    private int id, colorId;

    private Sprite elementSheet;
    private String title;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public ShopElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorId = colorId;

        if(id == SHIP_ID) {

            setElementAnimation(colorId);

            if(elementSheet != null) {
                elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
                elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
            }
        }
    }
    public void render(float stateTime, SpriteBatch batch){
        elementAnim.drawAnim(elementAnimation, stateTime, x, y, width, height, true, batch);
    }

    public static void createElements(Assets assets){
        mainShip = new Sprite(assets.assetManager.get(Assets.ship_ss, Texture.class));
        redShip = new Sprite(assets.assetManager.get(Assets.ship_red_ss, Texture.class));
        blackShip = new Sprite(assets.assetManager.get(Assets.ship_black_ss, Texture.class));
        purpleShip = new Sprite(assets.assetManager.get(Assets.ship_purple_ss, Texture.class));
    }
    public String getTitle(){
        return title;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setElementAnimation(int colorId){
        switch (colorId) {

            case MAIN_SHIP_ID:
                elementSheet = mainShip;
                title = "CLASSIC";
                break;
            case RED_SHIP_ID:
                elementSheet = redShip;
                title = "CRIMSON";
                break;
            case BLACK_SHIP_ID:
                elementSheet = blackShip;
                title = "GHOST";
                break;
            case PURPLE_SHIP_ID:
                elementSheet = purpleShip;
                title = "ULTRAVIOLET";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
            elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public void setColorId(int colorId){
        this.colorId = colorId;
    }
    public int getColorId(){
        return colorId;
    }
}
