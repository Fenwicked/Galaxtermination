package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;

public class Gameobject2D {
    public int sizeZ, sizeX;
    public float posZ, posX;
    public float motionX, motionZ;
    public float motionForward, motionLateral;
    public double yaw;
    public float yawSpeed;
    public String textureFile;
    public Texture texture;
    public TextureRegion textureRegion;
    public Decal myDecal;
    private final float motionDecay = 0.04f;
    private final float motionFalloff = 0.05f;
    private final float yawDecay = 0.05f;
    private final float yawFalloff = 0.055f;
    public Vector2 motionVec;
    public final float accelRate = 0.09f;
    private final float maxX = 0.9f;
    private final float maxZ = 0.9f;
    private final float maxForward = 0.9f;
    private final float maxLateral = 0.9f;
    private final float maxYawSpeed = 2.5f;
    public AssetLoader al;

    public Gameobject2D(){}
    public Gameobject2D(Decal myDecal, AssetLoader al){
        this.myDecal = myDecal;
        this.al = al;
    }

    final public void turnYaw(){
        this.yaw += this.yawSpeed;
    }
    public void turnAll(){
        turnYaw();
    }

    final public void accelYaw(Float yawAccel) { this.yawSpeed += yawAccel * 1.5;}
    final public void translateZ(Float zTr){
        this.posZ += zTr;
    }
    final public void translateX(Float xTr){
        this.posX += xTr;
    }
    final public void accelZ(Float zMO) { this.motionZ += zMO; } //old
    final public void accelX(Float xMO){
        this.motionX += xMO;
    } //old
    //final public void accelLateral(Float zMO) { this.motionLateral += zMO; }
    final public void accelForward(Float xMO){
        //this.motionX += Math.sin(this.yaw) * xMO;
        //this.motionZ += Math.cos(this.yaw) * xMO;
        motionVec = new Vector2(xMO, 0).rotate((float)yaw);
        this.motionX += motionVec.x;
        this.motionZ -= motionVec.y;
    }
    final public void moveZ(){
        this.posZ += (int)this.motionZ;
    } //old
    final public void moveX(){
        this.posX += (int)this.motionX;
    } //old

    public void moveAll(){
        moveX();
        moveZ();
    }
    public void update(){
        posX += motionX;
        posZ += motionZ;
        if (motionX > 0) {
            motionX -= motionDecay * (Math.abs(motionX) / (Math.abs(motionX) + Math.abs(motionZ)));
        }
        else if (motionX < 0){
            motionX += motionDecay * Math.abs((motionX) / (Math.abs(motionX) + Math.abs(motionZ)));
        }
        if (motionZ > 0) {
            motionZ -= motionDecay * (Math.abs(motionZ) / (Math.abs(motionX) + Math.abs(motionZ)));
        }
        else if (motionZ < 0){
            motionZ += motionDecay * (Math.abs(motionZ) / (Math.abs(motionX) + Math.abs(motionZ)));
        }
        if ((Math.abs(motionX) + Math.abs(motionZ)) > maxForward)
        {
            if (motionX >=0){
                motionX = maxForward * (Math.abs(motionX) / (Math.abs(motionX) + Math.abs(motionZ)));
            }
            else
            {
                motionX = -maxForward * (Math.abs(motionX) / (Math.abs(motionX) + Math.abs(motionZ)));
            }
            if (motionZ >= 0) {
                motionZ = maxForward * (Math.abs(motionZ) / (Math.abs(motionX) + Math.abs(motionZ)));
            }
            else
            {
                motionZ = -maxForward * (Math.abs(motionZ) / (Math.abs(motionX) + Math.abs(motionZ)));
            }
        }
        if ((Math.abs(motionX) + Math.abs(motionZ)) < motionFalloff) {
            motionX = 0;
            motionZ = 0;
        }
        //System.out.println(yaw);
        //System.out.println("motionX: " + motionX + " motionZ: " + motionZ);
        yaw += yawSpeed;
        if (yawSpeed > 0) {
            yawSpeed -= yawDecay;
        }
        else{
            yawSpeed += yawDecay;
        }
        if (Math.abs(yawSpeed) >= maxYawSpeed){
            if (yawSpeed >=0){
                yawSpeed = maxYawSpeed;
            }
            else
            {
                yawSpeed = -maxYawSpeed;
            }
        }
        else if (Math.abs(yawSpeed) <= yawFalloff){
            yawSpeed = 0;
        }
//        if (yaw > 360){
//            yaw -= 360;
//        }
//        else if (yaw < 0){
//            yaw += 360;
//        }
        myDecal.setPosition(posX,myDecal.getY(), posZ);
        myDecal.setRotation((float)yaw,90,90);
        //posX = myDecal.getX();
        //posZ = myDecal.getZ();
        //yaw = myDecal.getRotation().getYaw();
    }
}
