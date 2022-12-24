package com.mygdx.NEBULA;

import static com.mygdx.NEBULA.GameElements.SHIP_X;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;

public class MyInputProcessor implements InputProcessor {
    private int tapReleaseCount = 0;
    private int tapCount = 0;
    MainGame m;
    public MyInputProcessor(MainGame m){
        this.m = m;
    };
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        movePlayer(screenX, screenY, pointer);
        tapCount++;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        movePlayer(screenX, screenY, pointer);
        tapReleaseCount++;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        movePlayer(screenX, screenY, pointer);
        return false;
    }

    private void movePlayer(int screenX, int screenY, int pointer){
        if(pointer == 0) {

            if (!m.isPaused && m.isTransitionedIn && !m.isShipLeaving && !m.isFadingOut && !m.isTransitioningOut) {
                if (!(screenY < (int) (GameElements.SCREEN_HEIGHT / 5f)) && m.SHIP_START_Y > GameElements.SHIP_Y) {
                    m.shipMovementVal = (m.deltaP * m.moveSpeed * (screenX - SHIP_X - GameElements.SHIP_WIDTH / 2f));
                    if (SHIP_X + m.shipMovementVal > GameElements.SCREEN_WIDTH - GameElements.SHIP_WIDTH) {
                        SHIP_X = GameElements.SCREEN_WIDTH - GameElements.SHIP_WIDTH;
                    } else if (SHIP_X + m.shipMovementVal < 0) {
                        SHIP_X = 0;
                    } else {
                        SHIP_X += (int) m.shipMovementVal;
                    }

                    GameElements.CURRENT_SHIP_X = SHIP_X;
                }
            }
        }
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public int getTapReleaseCount() {
        return tapReleaseCount;
    }
    public int getTapCount() {
        return tapCount;
    }

}
