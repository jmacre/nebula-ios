package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.Anim.DEFAULT_FRAME_DURATION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GemElement extends GameElements{
    public static final int AD_ID = 0; //this determines the order in the gem screen
    public static final int ONE_DOLLAR_ID = 1;
    public static final int FIVE_DOLLAR_ID = 2;
    public static final int TEN_DOLLAR_ID = 3;
    public static final int TWENTY_DOLLAR_ID = 4;

    public static final float AD_PRICE = 0;
    public static final float ONE_DOLLAR_PRICE = 0.99f;
    public static final float FIVE_DOLLAR_PRICE = 4.99f;
    public static final float TEN_DOLLAR_PRICE = 9.99f;
    public static final float TWENTY_DOLLAR_PRICE = 19.99f;


    float x, y, width, height;


    public static int gemOptionsCount = 4; // one less than actual count for cycling between ships

    private Sprite elementSheet;
    private String title;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public GemElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(id == AD_ID) {

//            setElementAnimation(colorId);
//
//            if(elementSheet != null) {
//                elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
//                elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
//            }
        }
    }
    public void render(float stateTime, SpriteBatch batch){
        elementAnim.drawAnim(elementAnimation, stateTime, x, y, width, height, true, batch);
    }

    public static void createElements(Assets assets){
//        mainShip = new Sprite(assets.assetManager.get(Assets.ship_ss, Texture.class));
//        redShip = new Sprite(assets.assetManager.get(Assets.ship_red_ss, Texture.class));
//        blackShip = new Sprite(assets.assetManager.get(Assets.ship_black_ss, Texture.class));
//        purpleShip = new Sprite(assets.assetManager.get(Assets.ship_purple_ss, Texture.class));
//        yellowShip = new Sprite(assets.assetManager.get(Assets.ship_yellow_ss, Texture.class));
//        cyanShip = new Sprite((assets.assetManager.get(Assets.ship_cyan_ss, Texture.class)));
//        blueShip = new Sprite((assets.assetManager.get(Assets.ship_blue_ss, Texture.class)));
//        bredShip = new Sprite((assets.assetManager.get(Assets.ship_bred_ss, Texture.class)));
//        orangeShip = new Sprite((assets.assetManager.get(Assets.ship_orange_ss, Texture.class)));
//        greenShip = new Sprite((assets.assetManager.get(Assets.ship_green_ss, Texture.class)));
    }

    public String getTitle(){
        return title;
    }

    public void setElementAnimation(int colorId){
        switch (colorId) {

            case AD_ID:
//                elementSheet = mainShip;
                title = "25 GEMS";
                break;
            case ONE_DOLLAR_ID:
//                elementSheet = redShip;
                title = "1,000 GEMS";
                break;
            case FIVE_DOLLAR_ID:
//                elementSheet = blackShip;
                title = "10,000 GEMS";
                break;
            case TEN_DOLLAR_ID:
//                elementSheet = purpleShip;
                title = "30,000 GEMS";
                break;
            case TWENTY_DOLLAR_ID:
//                elementSheet = yellowShip;
                title = "100,000 GEMS";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 4, DEFAULT_FRAME_DURATION * 1.5f);
            elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
    public static float getPriceByElementId(int elementId){
        if(elementId == AD_ID){
            return AD_PRICE;
        }
        else if(elementId == ONE_DOLLAR_ID){
            return ONE_DOLLAR_PRICE;
        }
        else if(elementId == FIVE_DOLLAR_ID){
            return FIVE_DOLLAR_PRICE;
        }
        else if(elementId == TEN_DOLLAR_ID){
            return TEN_DOLLAR_PRICE;
        }
        else if(elementId == TWENTY_DOLLAR_ID){
            return TWENTY_DOLLAR_PRICE;
        }

        return 0;
    }

//    public void setColorId(int colorId){
//        this.colorId = colorId;
//    }
//    public int getColorId(){
//        return colorId;
//    }
}
