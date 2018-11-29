package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class Gameobject3D extends Gameobject2D{
    public int sizeY;
    public float posY;
    public float motionY;
    public float pitch, roll;
    public float pitchSpeed, rollSpeed;

    public Gameobject3D(){}
    public Gameobject3D(Decal myDecal){
        this.myDecal = myDecal;
    }

    final public void turnPitch(){
        this.pitch += this.pitchSpeed;
    }
    final public void turnRoll(){
        this.roll += this.rollSpeed;
    }

    final public void turnAll(){
        turnYaw();
        turnPitch();
        turnRoll();
    }

    final public void moveY(){
        this.posY += this.motionY;
    }

    @Override
    final public void moveAll() {
        moveY();
        moveX();
        moveZ();
    }
}
