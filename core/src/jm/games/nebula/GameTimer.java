package jm.games.nebula;

public class GameTimer {
    private float timer;
    private final float resetValue;
    private final Runnable onStart;
    private final Runnable onExpire;
    boolean onStartRan = false;
    boolean onExpireRan = false;

    public GameTimer(float resetValue, Runnable onExpire) {
        this.resetValue = resetValue;
        this.onStart = null;
        this.onExpire = onExpire;
        this.timer = resetValue;
    }
    public GameTimer(float resetValue, Runnable onStart, Runnable onExpire) {
        this.resetValue = resetValue;
        this.onStart = onStart;
        this.onExpire = onExpire;
        this.timer = resetValue;
    }

    public void update(boolean conditionToRun, float delta) {
        if(conditionToRun){
            if (onStart != null && !onStartRan) {
                onStart.run();
                onStartRan = true;
            }

            if (timer < 0) {
                timer += delta;
            } else if (onExpire != null && !onExpireRan){
                onExpire.run();
                reset();
            }
        }
    }
    public void update(float delta)  {
        update(true, delta);
    }

    public void reset() {
        timer = resetValue;
    }

    public void set(float newValue) {
        timer = newValue;
    }

    public float get() {
        return timer;
    }
}
