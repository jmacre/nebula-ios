package jm.games.nebula;

import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
    private int tapReleaseCount = 0;
    private int tapCount = 0;

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
        tapCount++;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tapReleaseCount++;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    // Added in libGDX 1.12+ — fires when a touch is cancelled (e.g. the
    // OS steals focus). Mirror touchUp so our tap bookkeeping stays sane.
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
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
