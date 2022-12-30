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

    static Sprite adGem, oneDollarGem, fiveDollarGem, tenDollarGem, twentyDollarGem;

    float x, y, width, height;

    public static int gemOptionsCount = 4;

    private Sprite elementSheet;
    private String title;
    private String sku;

    Animation<TextureRegion> elementAnimation;
    Anim elementAnim = new Anim();

    public GemElement(int id, int colorId, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(id == AD_ID) {

            setElementAnimation(colorId);

            if(elementSheet != null) {
                elementAnimation = Anim.createAnimation(elementSheet, 1, DEFAULT_FRAME_DURATION * 1.5f);
                elementAnimation.setPlayMode(Animation.PlayMode.LOOP);
            }
        }
    }
    public void render(float stateTime, SpriteBatch batch){
        elementAnim.drawAnim(elementAnimation, stateTime, x, y, width, height, true, batch);
    }

    public static void createElements(Assets assets){
        adGem = new Sprite(assets.assetManager.get(Assets.gem_25, Texture.class));
        oneDollarGem = new Sprite(assets.assetManager.get(Assets.gem_1k, Texture.class));
        fiveDollarGem = new Sprite(assets.assetManager.get(Assets.gem_10k, Texture.class));
        tenDollarGem = new Sprite(assets.assetManager.get(Assets.gem_30k, Texture.class));
        twentyDollarGem = new Sprite(assets.assetManager.get(Assets.gem_100k, Texture.class));
    }

    public String getTitle(){
        return title;
    }

    public void setElementAnimation(int colorId){
        switch (colorId) {

            case AD_ID:
                elementSheet = adGem;
                title = "25 GEMS";
                break;
            case ONE_DOLLAR_ID:
                elementSheet = oneDollarGem;
                title = "1,000 GEMS";
                sku = "1k_gems";
                break;
            case FIVE_DOLLAR_ID:
                elementSheet = fiveDollarGem;
                title = "10,000 GEMS";
                sku = "10k_gems";
                break;
            case TEN_DOLLAR_ID:
                elementSheet = tenDollarGem;
                title = "30,000 GEMS";
                sku = "30k_gems";
                break;
            case TWENTY_DOLLAR_ID:
                elementSheet = twentyDollarGem;
                title = "100,000 GEMS";
                sku = "100k_gems";
                break;
        }

        if(elementSheet != null) {
            elementAnimation = Anim.createAnimation(elementSheet, 1, DEFAULT_FRAME_DURATION * 1.5f);
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
    public String getSKU(){
        return sku;
    }
}
