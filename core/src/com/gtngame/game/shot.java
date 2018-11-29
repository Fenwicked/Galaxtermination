package com.gtngame.game;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class shot extends Gameobject2D {
    AssetLoader al;
    float totalDist, targetX, targetZ, initX, initZ, shotSpeed, targetRemoveRadius;
    boolean isEnemy;
    playerShip playerShip;
    Random r;
    hundred hun;

    public shot (float startX, float startZ, float totalDist, float startYaw, AssetLoader al, float initX, float initZ, boolean isEnemy, playerShip playerShip){
        this.al = al;
        this.posX = startX;
        this.posZ = startZ;
        this.totalDist = totalDist;
        this.yaw = startYaw;
        this.initX = initX;
        this.initZ = initZ;
        this.isEnemy = isEnemy;
        this.playerShip = playerShip;
        r = new Random();
        shotSpeed = 2f;
        targetRemoveRadius = 0.7f;
        getTarget();
        //System.out.println("shot 2: " + posX + " " + posZ + " " + yaw);
        al.addShotAt(this);
        updateShot(al.enmes, al.asts, false, playerShip);
        //al.shots.add(this);
    }

    public void updateShot(Array<enemyShip> enmes, Array<asteroid> asts, boolean collide, playerShip playerShip){
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
                        al.nmeoww.play(0.05f);
                    }
                    else
                    {
                        playerShip.confirmKill(5);
                        al.addFiveFXAt(nme.posX,-1.5f, nme.posZ);
                        al.nmeoww.play(0.6f);
                    }
                }
            }
            for (asteroid ast : asts) {
                if (getDistForCollision(ast) <= 3) {
                    al.asts.removeValue(ast, true);
                    al.modelInstances.removeValue(ast.myMI, true);
                    al.shots.removeValue(this,true);
                    if (isEnemy) {
                        al.astoww.play(0.02f);
                    }
                    else
                    {
                        int randomR = r.nextInt(100);
                        //System.out.println(randomR);
                        if (randomR <= 2){
                            hun = new hundred(ast.posX,ast.posZ, al);
                            al.astowwhund.play(0.3f);
                        }
                        else {
                            playerShip.confirmKill(1);
                            al.addOneFXAt(ast.posX, ast.posY, ast.posZ);
                            al.astoww.play(0.3f);
                        }
                    }
                }
            }
            if (getDistForCollision(playerShip) <= 2) {

                al.shots.removeValue(this,true);
                if (isEnemy) {
                    playerShip.injure();
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
    public float getDistForCollision(playerShip plr){
        return (float)Math.sqrt(Math.pow(posX - plr.posX,2) + Math.pow(posZ - plr.posZ,2));
    }
}
