package com.gtngame.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class shot extends Gameobject2D {
    AssetLoader al;
    float totalDist, targetX, targetZ, initX, initZ, shotSpeed, targetRemoveRadius;
    boolean isEnemy;
    public shot (float startX, float startZ, float totalDist, float startYaw, AssetLoader al, float initX, float initZ, boolean isEnemy){
        this.al = al;
        this.posX = startX;
        this.posZ = startZ;
        this.totalDist = totalDist;
        this.yaw = startYaw;
        this.initX = initX;
        this.initZ = initZ;
        this.isEnemy = isEnemy;
        shotSpeed = 2f;
        targetRemoveRadius = 0.7f;
        getTarget();
        //System.out.println("shot 2: " + posX + " " + posZ + " " + yaw);
        al.addShotAt(this);
        updateShot(al.enmes, al.asts, false);
        //al.shots.add(this);
    }

    public void updateShot(Array<enemyShip> enmes, Array<asteroid> asts, boolean collide){
        float theta = -(float)Math.atan2(Math.abs(targetX - posX), Math.abs(targetZ - posZ));
        if (posX > targetX){
            posX -= (shotSpeed * -(float)(Math.sin(theta)));
        }
        else if(posX < targetX){
            posX += (shotSpeed * -(float)(Math.sin(theta)));
        }
        if (posZ > targetZ){
            posZ -= (shotSpeed * (float)(Math.cos(theta)));
        }
        else if(posZ < targetZ){
            posZ += (shotSpeed * (float)(Math.cos(theta)));
        }
        if (Math.abs(targetX - posX) < targetRemoveRadius && Math.abs(targetZ - posZ) < targetRemoveRadius){
            this.remove();
        }
        al.addBoxAt(posX, -1.9f, posZ);
        if (collide) {
            for (enemyShip nme : enmes) {
                if (getDistForCollision(nme) <= 2) {
                    al.enmes.removeValue(nme, true);
                    al.Decals.removeValue(nme.myDecal, true);
                    al.shots.removeValue(this,true);
                    if (isEnemy) {
                        al.oww.play(0.1f);
                    }
                    else
                    {
                        al.oww.play(1.0f);
                    }
                }
            }
            for (asteroid ast : asts) {
                if (getDistForCollision(ast) <= 3) {
                    al.asts.removeValue(ast, true);
                    al.modelInstances.removeValue(ast.myMI, true);
                    al.shots.removeValue(this,true);
                    if (isEnemy) {
                        al.oww.play(0.1f);
                    }
                    else
                    {
                        al.oww.play(1.0f);
                    }
                }
            }
        }
    }
    public void refreshShot(Array<enemyShip> enmes, Array<asteroid> asts, boolean collide){
        al.addBoxAt(posX, -1.9f, posZ);
    }
    public void getTarget(){
        targetX = posX - (totalDist * (float)(Math.cos(Math.toRadians(yaw))));
        targetZ = posZ + (totalDist * (float)(Math.sin(Math.toRadians(yaw))));
    }
    public void remove(){
        al.shots.removeValue(this, true);
    }
    public float getDistForCollision(enemyShip nme){
        return (float)Math.sqrt(Math.pow(posX - nme.posX,2) + Math.pow(posZ - nme.posZ,2));
    }
    public float getDistForCollision(asteroid ast){
        return (float)Math.sqrt(Math.pow(posX - ast.posX,2) + Math.pow(posZ - ast.posZ,2));
    }
}
