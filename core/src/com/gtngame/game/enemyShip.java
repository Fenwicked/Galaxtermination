package com.gtngame.game;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public class enemyShip extends Gameobject2D {

    public enemyShip(Decal myDecal){
        this.myDecal = myDecal;
        posX = this.myDecal.getX();
        posZ = this.myDecal.getZ();
    }
    public void updateEnme(){
        accelForward(-accelRate);
        update();
    };
    public void findNearest(Array<enemyShip> enmes, playerShip plyr){

    }
}
