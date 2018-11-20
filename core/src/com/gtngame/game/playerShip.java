package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class playerShip extends Gameobject2D {
    int numShots;
    float shotTimer;
    boolean shotTimerStarted;
    public playerShip(Decal myDecal, AssetLoader al ) {
        this.myDecal = myDecal;
        this.al = al;
        shotTimerStarted = false;
    }
    public void update(){
        if (shotTimerStarted) {
            shotTimer += Gdx.graphics.getDeltaTime();
        }
        super.update();
    }
    public void shoot (){
        if (numShots < 3) {
            shot sht = new shot(posX, posZ, 100, (float)yaw, al, motionX, motionZ);
            numShots += 1;
            shotTimerStarted = true;
        }
        if (shotTimer > 0.5f){
            numShots = 0;
            shotTimer = 0;
            shotTimerStarted = false;
        }
        if(numShots < 1) {
            al.pew.play(0.9f);
        }
        //System.out.println("shot 1: " + posX + " " + posZ + " " + yaw);
    }
}

