package com.gtngame.game;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

public class enemyShip extends Gameobject2D {
    AssetLoader al;
    public enemyShip(Decal myDecal, AssetLoader al){
        this.al = al;
        this.myDecal = myDecal;
        posX = this.myDecal.getX();
        posZ = this.myDecal.getZ();
    }
    public void updateEnme(Array<Decal> enmes){
        accelForward(-accelRate * 0.7f);
        //enmes.removeValue(myDecal, true);
        findNearest(enmes);
        update();
    };
    public void findNearest(Array<Decal> enmes){
        //System.out.println("size3: " + enmes.size);
        //Array<Decal> newEnmes = new Array<Decal>();
        float shortestDist = 1000;
        float targetX = 0, targetZ = 0;
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
                targetX = enmDec.getX();
                targetZ = enmDec.getZ();
                enmInd = enmes.indexOf(enmDec, true);
                //System.out.println("Setting shortest: " + shortestDist);
            }
        }
        //al.addBoxAt(targetX, -1.5f, targetZ);
        //System.out.println(yaw + " " + getYawTo(myDecal, enmes.get(enmInd)));
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
        //if (angleTo - yaw > 0){
            //yaw += 5;
            accelYaw(-accelRate * 2);
        }
        else if (angleDiff < 0){
        //else if (angleTo - yaw < 0){
            //yaw -= 5;
            accelYaw(accelRate * 2);
        }
        //System.out.println("ind: " + enmInd + " X: " + targetX + ", Z: " + targetZ + " dist: " + shortestDist);
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
}
