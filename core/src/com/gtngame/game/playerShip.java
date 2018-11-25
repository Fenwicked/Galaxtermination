package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public class playerShip extends Gameobject2D {
    int numShots, score, shields;
    float shotTimer, immuneTimer, animTimer;
    boolean shotTimerStarted, isImmune;

    public playerShip(Decal myDecal, AssetLoader al ) {
        this.myDecal = myDecal;
        this.al = al;
        shotTimerStarted = false;
        score = 0;
        shields = 2;
        animTimer = 0;
    }
    public void update(){
        //System.out.println(posX + " " + posZ);
        animTimer += Gdx.graphics.getDeltaTime();
        if (animTimer > 1) animTimer = 0;
        if (shotTimerStarted) {
            shotTimer += Gdx.graphics.getDeltaTime();
        }
        if (isImmune) {
            if (immuneTimer > 0) {
                immuneTimer -= Gdx.graphics.getDeltaTime();
            }
            else
            {
                immuneTimer = 0;
                isImmune = false;
            }
        }
        for (asteroid ast : al.asts){
            if (getDist(myDecal, ast) < 3){
                al.asts.removeValue(ast, true);
                injure();
            }
        }
        super.update();
    }
    public void refresh(){
        super.refresh();
    }
    public void confirmKill(int score){
        this.score += score;
    }
    public void shoot (){
        if (numShots < 1) {
            shot sht = new shot(posX, posZ, 100, (float)yaw, al, motionX, motionZ, false, this);
            numShots += 1;
            shotTimerStarted = true;
            al.shot.play(0.6f);
        }
        if (shotTimer > 0.5f){
            numShots = 0;
            shotTimer = 0;
            shotTimerStarted = false;
        }
        //System.out.println("shot 1: " + posX + " " + posZ + " " + yaw);
    }
    public void injure(){
        if (!isImmune){
            shields --;
            isImmune = true;
            immuneTimer = 1f;
            if (shields >= 0) {
                al.hurt.play(0.6f);
            }
            else {
                al.dead.play(0.6f);
            }
        }
    }
    public float findNearest(Array<Decal> enmes){
        float shortestDist = 3000;
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
    public float getDist(Decal me, asteroid you){
        float xDist = Math.abs(me.getX() - you.posX);
        float zDist = Math.abs(me.getZ() - you.posZ);
        return (float)Math.sqrt((xDist * xDist) + (zDist * zDist));
    }
}

