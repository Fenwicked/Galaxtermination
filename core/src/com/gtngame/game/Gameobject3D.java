package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract public class Gameobject3D extends Gameobject2D{
    public int sizeZ;
    public int posZ;
    public float motionZ;
    public float pitch, roll;
    public float pitchSpeed, rollSpeed;

    public Gameobject3D(){
    }

    final public void turnPitch(){
        this.pitch += this.pitchSpeed;
    }
    final public void turnRoll(){
        this.roll += this.rollSpeed;
    }

    @Override
    final public void turnAll(){
        turnYaw();
        turnPitch();
        turnRoll();
    }

    final public void moveZ(){
        this.posZ += this.motionZ;
    }

    @Override
    final public void moveAll() {
        moveY();
        moveX();
        moveZ();
    }
}
