package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class Gameobject2D {
    public int sizeZ, sizeX;
    public float posZ, posX;
    public float motionX, motionZ;
    public float yaw;
    public float yawSpeed;
    public String textureFile;
    public Texture texture;
    public TextureRegion textureRegion;
    public Decal myDecal;
    private final float motionDecay = 0.04f;
    private final float motionFalloff = 0.05f;
    public final float accelRate = 0.09f;
    private final float maxX = 1f;
    private final float maxZ = 1f;

    public Gameobject2D(){}
    public Gameobject2D(Decal myDecal){
        this.myDecal = myDecal;
    }

    final public void turnYaw(){
        this.yaw += this.yawSpeed;
    }
    public void turnAll(){
        turnYaw();
    }

    final public void translateZ(Float zTr){
        this.posZ += zTr;
    }
    final public void translateX(Float xTr){
        this.posX += xTr;
    }
    final public void accelZ(Float zMO) { this.motionZ += zMO; }
    final public void accelX(Float xMO){
        this.motionX += xMO;
    }
    final public void moveZ(){
        this.posZ += (int)this.motionZ;
    }
    final public void moveX(){
        this.posX += (int)this.motionX;
    }
    public void moveAll(){
        moveX();
        moveZ();
    }
    public void update(){
        posX += motionX;
        posZ += motionZ;
        if (motionX > 0) {
            motionX -= motionDecay;
        }
        else{
            motionX += motionDecay;
        }
        if (Math.abs(motionX) >= maxX){
            if (motionX >=0){
                motionX = maxX;
            }
            else
            {
                motionX = -maxX;
            }
        }
        else if (Math.abs(motionX) <= motionFalloff){
            motionX = 0;
        }
        if (motionZ > 0) {
            motionZ -= motionDecay;
        }
        else{
            motionZ += motionDecay;
        }
        if (Math.abs(motionZ) >= maxZ){
            if (motionZ >=0){
                motionZ = maxZ;
            }
            else
            {
                motionZ = -maxZ;
            }
        }
        else if (Math.abs(motionZ) <= motionFalloff){
            motionZ = 0;
        }
        myDecal.setPosition(posX,myDecal.getY(), posZ);
        myDecal.setRotation(yaw,90,90);
        //posX = myDecal.getX();
        //posZ = myDecal.getZ();
        //yaw = myDecal.getRotation().getYaw();
    }
}
