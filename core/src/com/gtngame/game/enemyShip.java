package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class enemyShip extends Gameobject2D {
    AssetLoader al;
    int numShots = 1;
    float shotTimer;
    boolean shotTimerStarted;
    Random r;
    public enemyShip(Decal myDecal, AssetLoader al){
        this.al = al;
        this.myDecal = myDecal;
        posX = this.myDecal.getX();
        posZ = this.myDecal.getZ();
        r = new Random();
        shotTimer = (r.nextFloat()/2) - 3;
        shotTimerStarted = true;
    }
    public void updateEnme(Array<Decal> enmes){
        //System.out.println("updating");
        accelForward(-accelRate * 0.6f);
        findNearest(enmes);
        if (shotTimerStarted) {
            shotTimer += Gdx.graphics.getDeltaTime();
        }
        shoot();
        update();
        //al.addCircleAt(posX,-2, posZ);
    }
    public void refreshEnme(Array<Decal> enmes){
        refresh();
    }
    public void findNearest(Array<Decal> enmes){
        float shortestDist = 2000;
        int enmInd = 0;
        for (Decal enmDec : enmes) {
        //    if (myDecal.getX() != enmDec.getX() && myDecal.getZ() != enmDec.getZ()) {
        //        newEnmes.add(enmDec);
        //    }
        //}
        //for (Decal enmDec : newEnmes){
            //System.out.println(enmDec.getX() + " " + enmDec.getZ());
            //System.out.println(getDist(myDecal, enmDec));

            if (getDist(myDecal, enmDec) <= shortestDist && getDist(myDecal, enmDec) > 0.0f){
                shortestDist = getDist(myDecal, enmDec);
                enmInd = enmes.indexOf(enmDec, true);
            }
        }
        //al.addBoxAt(targetX, -1.5f, targetZ);
        double angleTo = getYawTo(myDecal, enmes.get(enmInd));
        //Math.toDegrees(Math.atan2(myDecal.getZ() - enmes.get(enmInd).getZ(), myDecal.getX() - enmes.get(enmInd).getX()) + Math.PI / 2);
//        if (angleTo > 360){
//            angleTo -= 360;
//        }
//        else if (angleTo < 0){
//            angleTo += 360;
//        }
//        if (yawForCompare > 360){
//            yawForCompare -= 360;
//        }
//        else if (yawForCompare < 0){
//            yawForCompare += 360;
//        }
        double angleDiff = (angleTo + 9000000) - (-yaw + 9000000);
//        if (angleDiff > 360){
//            angleDiff -= 180;
//        }
//        else if (angleDiff < 0){
//            angleDiff += 180;
//        }
        //System.out.println(angleDiff);
        //System.out.println(angleTo);
        //if (Math.abs(getYawTo(myDecal, enmes.get(enmInd))) - yaw > 0){
        if (angleDiff > 0){
            accelYaw(-accelRate * 1.2f);
        }
        else if (angleDiff < 0){
            accelYaw(accelRate * 1.2f);
        }
    }
    public float getDist(Decal me, Decal you){
        float xDist = Math.abs(me.getX() - you.getX());
        float zDist = Math.abs(me.getZ() - you.getZ());
        return (float)Math.sqrt((xDist * xDist) + (zDist * zDist));
    }
    public float getYawTo(Decal me, Decal you){
        float xPosDiff = me.getX() - you.getX();
        float zPosDiff = me.getZ() - you.getZ();
        return (float)Math.toDegrees(Math.atan2((double)zPosDiff, (double)xPosDiff));
    }
    public void shoot (){
        if (numShots < 1) {
            shot sht = new shot(posX, posZ, 50, (float)yaw, al, motionX, motionZ, true);
            al.pew.play(0.02f);
            numShots += 1;
            shotTimerStarted = true;
        }
        if (shotTimer > 1f){
            numShots = 0;
            shotTimer = 0;
            shotTimerStarted = false;
        }
        //System.out.println(shotTimer + " " + numShots);
        //System.out.println("shot 1: " + posX + " " + posZ + " " + yaw);
    }
    public void die(){

    }
}
