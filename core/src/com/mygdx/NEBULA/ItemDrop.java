package com.mygdx.NEBULA;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.NEBULA.MainGame.MAIN_UI_HEIGHT;

public class ItemDrop {
    Assets assets;
    Animation<TextureRegion> itemAnimation;
    public static final int BOMB_ID = 0;
    public static final int MISSILE_ID = 1;
    public static final int HEART_ID = 2;
    public static final int RAPID_FIRE_ID = 3;
    public static final int HOURGLASS_ID = 4;

    public static final float MIN_ITEM_SPAWN_TIME = 15f;
    public static final float MAX_ITEM_SPAWN_TIME = 25f;

    public static float MISSILE_TIMER = -9f;
    public static float RAPID_FIRE_TIMER = -9f;
    public static float HOURGLASS_TIMER = -4.5f;
    public static float HOURGLASS_SPEED_MULTIPLIER = 0.25f;

    public static final float HEART_HEIGHT = MAIN_UI_HEIGHT/1.75f;
    public static final float HEART_WIDTH = HEART_HEIGHT * (13.0f/11.0f);

    public static final float BOMB_HEIGHT = MAIN_UI_HEIGHT/1.75f;
    public static final float BOMB_WIDTH = BOMB_HEIGHT;

    public static final float RAPID_FIRE_HEIGHT = MAIN_UI_HEIGHT/1.75f;
    public static final float RAPID_FIRE_WIDTH = RAPID_FIRE_HEIGHT * (13/16f);

    public static final float HOURGLASS_HEIGHT = MAIN_UI_HEIGHT/1.75f;
    public static final float HOURGLASS_WIDTH = RAPID_FIRE_HEIGHT * (15/16f);

    public static final float MISSILE_HEIGHT = MAIN_UI_HEIGHT/1.5f;
    public static final float MISSILE_WIDTH = MISSILE_HEIGHT * (16/22f);

    public Sprite itemSheet;
    Anim itemAnim;

    public static final int SPEED = 2000;

    public int lastChangedDirectionTime;

    float ITEM_X;
    float ITEM_Y;

    public static float ITEM_HEIGHT;
    public static float ITEM_WIDTH;

    float stateTime = 0f;
    int itemId;

    Collision rect;
    public boolean remove = false;

    public ItemDrop(float ITEM_X, float ITEM_HEIGHT, float ITEM_WIDTH, int itemId, Assets assets) {
        this.ITEM_X = ITEM_X;
        this.ITEM_Y = 1.2f * MainGame.SCREEN_HEIGHT;
        ItemDrop.ITEM_HEIGHT = ITEM_HEIGHT;
        ItemDrop.ITEM_WIDTH = ITEM_WIDTH;

        this.assets = assets;

        this.itemId = itemId;
        this.rect = new Collision(ITEM_X, ITEM_Y, ITEM_WIDTH, ITEM_HEIGHT);
        this.lastChangedDirectionTime = 0;

        switch (itemId) {
            case HEART_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.heart_item_ss, Texture.class));
                break;
            case BOMB_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.bomb_ss, Texture.class));
                break;
            case MISSILE_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.missile_item_ss, Texture.class));
                break;
            case RAPID_FIRE_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.rapid_fire_ss, Texture.class));
                break;
            case HOURGLASS_ID:
                itemSheet = new Sprite(assets.assetManager.get(Assets.hourglass_ss, Texture.class));
                break;
        }

        itemAnimation = Anim.createAnimation(itemSheet, 2, 0.025f);
    }
    public void update(float deltaTime){

        ITEM_Y -= SPEED * deltaTime;
        if (ITEM_Y < -ITEM_HEIGHT){
            remove = true;
        }
        rect.move(ITEM_X, ITEM_Y);
    }

    public void render (float width, float height, float delta, SpriteBatch batch) {
        stateTime += delta / 10;
        itemAnim = new Anim();
        itemAnim.drawAnim(itemAnimation, stateTime, ITEM_X, ITEM_Y, width, height,   true, batch, false);


    }
    public float getItemY(){
        return ITEM_Y;
    }

    public float getItemX(){
        return ITEM_X;
    }

    public Collision getCollision() {
        return rect;
    }

    public int getItemId(){
        return itemId;
    }

}
