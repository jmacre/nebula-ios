package com.mygdx.NEBULA;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class Collision {
    float x, y, width, height;
    Vector2 center;
    float[] vertices;
    Rectangle r = new Rectangle();
    Polygon polygon = new Polygon(new float[]{0,0,0,0,0,0});
    float stateTime = 0f;

    public Collision(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Collision(Enemy enemy, float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.center = new Vector2(x + enemy.getWidth()/2, y + enemy.getHeight()/2);
    }

    public Collision(boolean isMainPlayer, float x, float y, float width, float height){
        if(isMainPlayer) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            this.center = new Vector2(x + Player.getWidth() / 2, y + Player.getHeight() / 2);
        }
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;

        r.set(x,y,width,height);
        this.vertices = new float[] { x, y, x, y + r.height, x+r.width, y+r.height, x+r.width, y};
        polygon.setVertices(vertices);
        polygon.setPosition(this.x, this.y);

    }
    public void move (boolean isMainPlayer, float x, float y){
        if(isMainPlayer) {
            this.x = x;
            this.y = y;

            this.center = new Vector2(x + Player.getWidth() / 2, y + Player.getHeight() / 2);


            this.vertices = new float[]{
                    center.x + Player.getWidth() / 2, center.y - Player.getWidth() / 3,
                    center.x + Player.getWidth() / 3.25f, center.y + Player.getWidth() / 1.75f,
                    center.x + Player.getWidth() / 5, center.y + Player.getWidth() / 8f,
                    center.x, center.y + Player.getHeight() / 2.5f,
                    center.x - Player.getWidth() / 5, center.y + Player.getWidth() / 8f,
                    center.x - Player.getWidth() / 3.25f, center.y + Player.getWidth() / 1.75f,
                    center.x - Player.getWidth() / 2, center.y - Player.getWidth() / 3
            };
            polygon.setVertices(vertices);
            polygon.setPosition(this.x, this.y);
        }
    }
    public void move(Enemy enemy, float x, float y, float delta){
        stateTime += delta;
        this.x = x;
        this.y = y;
        this.center = new Vector2(x + enemy.getWidth() / 2, y + enemy.getHeight() / 2);

        if(enemy.getId() == (Enemy.EYEBAT_ID)) {
            if(enemy.getEnemyAnimation().getKeyFrameIndex(enemy.stateTime) == 0){
                this.vertices = new float[]{
                        center.x + enemy.getWidth() / 2.5f, center.y + enemy.getWidth() / 3.5f,
                        center.x + enemy.getWidth() / 2, center.y,
                        center.x + enemy.getWidth() / 7, center.y - enemy.getWidth() / 7,
                        center.x - enemy.getWidth() / 7, center.y - enemy.getWidth() / 7,
                        center.x - enemy.getWidth() / 2, center.y,
                        center.x - enemy.getWidth() / 2.5f, center.y + enemy.getWidth() / 3.5f};
            }
            else if (enemy.getEnemyAnimation().getKeyFrameIndex(enemy.stateTime) == 1
                    || enemy.getEnemyAnimation().getKeyFrameIndex(enemy.stateTime) == 3) {
                this.vertices = new float[]{
                    center.x + enemy.getWidth() / 2.25f, center.y + enemy.getWidth() / 5.25f,
                    center.x + enemy.getWidth() / 2, center.y,
                    center.x + enemy.getWidth() / 7, center.y - enemy.getWidth() / 7,
                    center.x - enemy.getWidth() / 7, center.y - enemy.getWidth() / 7,
                    center.x - enemy.getWidth() / 2, center.y,
                    center.x - enemy.getWidth() / 2.25f, center.y + enemy.getWidth() / 5.25f};

            }
            else {
                this.vertices = new float[]{
                    center.x + enemy.getWidth() / 2.5f, center.y - enemy.getWidth() / 3, // bottom right
                    center.x + enemy.getWidth() / 2, center.y - enemy.getWidth() / 8f, // right middle
                    center.x + enemy.getWidth() / 7, center.y + enemy.getWidth() / 6, // top right
                    center.x - enemy.getWidth() / 7, center.y + enemy.getWidth() / 6, // top left
                    center.x - enemy.getWidth() / 2, center.y - enemy.getWidth() / 8f, // left middle
                    center.x - enemy.getWidth() / 2.5f, center.y - enemy.getWidth() / 3,// bottom left
                    center.x - enemy.getWidth() / 3.25f, center.y - enemy.getWidth() / 3, //left inner wing
                    center.x - enemy.getWidth() / 7f, center.y - enemy.getWidth() / 7f, //left under eye
                    center.x + enemy.getWidth() / 7f, center.y - enemy.getWidth() / 7f, // right under eye
                    center.x + enemy.getWidth() / 3.25f, center.y - enemy.getWidth() / 3, // right inner wing,
                };
            }
        }

        else if(enemy.getId() == (Enemy.ENEMY_SHIP_ID)){
            this.vertices = new float[]{
                center.x + enemy.getWidth() / 2, center.y + enemy.getWidth() / 6,
                center.x + enemy.getWidth() / 3.25f, center.y - enemy.getWidth() / 2.75f,
                center.x + enemy.getWidth() / 6, center.y - enemy.getWidth() / 8,
                center.x, center.y - enemy.getHeight() / 2f,
                center.x - enemy.getWidth() / 6, center.y - enemy.getWidth() / 8,
                center.x - enemy.getWidth() / 3.25f, center.y - enemy.getWidth() /2.75f,
                center.x - enemy.getWidth() / 2, center.y + enemy.getWidth() / 6
            };
        }
            polygon.setVertices(vertices);
            polygon.setPosition(this.x, this.y);
    }

    public static boolean isNearby(Collision col1, Collision col2){
        return (col1.getY() >= col2.getY() - col2.getHeight()
                && col1.getY() + col1.getHeight() <= col2.getY() + col2.getHeight())
                || (col2.getY() >= col1.getY() - col1.getHeight()
                && col2.getY() + col2.getHeight() <= col1.getY() + col1.getHeight())

                && (col1.getX() >= col2.getX() - col2.getWidth()
                && col1.getX() + col1.getWidth() <= col2.getX() + col2.getWidth())
                || (col2.getX() >= col1.getX() - col1.getWidth()
                && col2.getX() + col2.getWidth() <= col1.getX() + col1.getWidth());
    }

    public static boolean isColliding(Collision col1, Collision col2){
        return Intersector.intersectPolygons(FloatArray.with(col1.polygon.getVertices()), FloatArray.with(col2.polygon.getVertices()));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public Polygon getPolygon(){
        return polygon;
    }
}
