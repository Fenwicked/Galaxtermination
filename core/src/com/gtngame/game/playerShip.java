package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

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
    public void refresh(){
        super.refresh();
    }
    public void shoot (){
        if (numShots < 1) {
            shot sht = new shot(posX, posZ, 100, (float)yaw, al, motionX, motionZ, false);
            numShots += 1;
            shotTimerStarted = true;
            al.pew.play(1f);
        }
        if (shotTimer > 0.5f){
            numShots = 0;
            shotTimer = 0;
            shotTimerStarted = false;
        }
        //System.out.println("shot 1: " + posX + " " + posZ + " " + yaw);
    }
    public float findNearest(Array<Decal> enmes){
        float shortestDist = 2000;
        for (Decal enmDec : enmes) {

            if (getDist(myDecal, enmDec) <= shortestDist && getDist(myDecal, enmDec) > 0.0f){
                shortestDist = getDist(myDecal, enmDec);
            }
        }
        return shortestDist;
    }
    public float getDist(Decal me, Decal you){
        float xDist = Math.abs(me.getX() - you.getX());
        float zDist = Math.abs(me.getZ() - you.getZ());
        return (float)Math.sqrt((xDist * xDist) + (zDist * zDist));
    }
}

