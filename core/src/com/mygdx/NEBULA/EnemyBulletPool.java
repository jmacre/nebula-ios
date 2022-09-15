package com.mygdx.NEBULA;

import com.badlogic.gdx.utils.Pool;

public class EnemyBulletPool extends Pool<EnemyBullet>{

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public EnemyBulletPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public EnemyBulletPool(){
        super();
    }

    // method to create a single object
    @Override
    protected EnemyBullet newObject() {
        return new EnemyBullet();
    }

}