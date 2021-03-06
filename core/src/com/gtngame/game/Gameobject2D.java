package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Gameobject2D {
    public int sizeZ, sizeX;
    public float posZ, posX;
    public float motionX, motionZ;
    public float motionForward, motionLateral;
    public float yaw;
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
    private final float maxForward = 0.7f;
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

    final public void accelYaw(Float yawAccel) { this.yawSpeed += yawAccel * 1.5f;}
    //final public void accelLateral(Float zMO) { this.motionLateral += zMO; }
    final public void accelForward(Float xMO){
        //motionVec = new Vector2(xMO, 0).rotate(yaw);
        //this.motionX += motionVec.x;
        //this.motionZ -= motionVec.y;
        this.motionX += xMO * MathUtils.cosDeg(yaw);
        this.motionZ -= xMO * MathUtils.sinDeg(yaw);
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
        if (posX > 1500){
            //motionX = -motionX;
            //posX -= 2;
            posX -= 3000;
        }
        else if (posX < -1500){
            //motionX = -motionX;
            //posX += 2;
            posX += 3000;
        }
        posZ += motionZ;
        if (posZ > 1500){
            //motionZ = -motionZ;
            //posZ -= 2;
            posZ -= 3000;
        }
        else if (posZ < -1500){
            //motionZ = -motionZ;
            //posZ += 2;
            posZ += 3000;
        }
        //if (posZ > 1500 || posZ < -1500) motionZ = -motionZ;
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
        motionForward = (float)Math.sqrt(Math.pow(motionX,2) * Math.pow(motionZ, 2));
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
        refresh();
        //posX = myDecal.getX();
        //posZ = myDecal.getZ();
        //yaw = myDecal.getRotation().getYaw();
    }
    public void refresh(){
        myDecal.setPosition(posX,myDecal.getY(), posZ);
        myDecal.setRotation(yaw,90,90);
    }
}
