package com.mygdx.NEBULA;

import com.badlogic.gdx.utils.Pool;

public class EnemyPool extends Pool<Enemy>{

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public EnemyPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public EnemyPool(){
        super();
    }

    // method to create a single object
    @Override
    protected Enemy newObject() {
        return new Enemy();
    }

}