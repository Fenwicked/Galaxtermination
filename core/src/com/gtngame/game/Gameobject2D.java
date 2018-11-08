package com.gtngame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract public class Gameobject2D {
    public int sizeY, sizeX;
    public int posY, posX;
    public float motionX, motionY;
    public float yaw;
    public float yawSpeed;
    public String textureFile;
    public Texture texture;
    public TextureRegion textureRegion;

    public Gameobject2D(){
    }

    final public void turnYaw(){
        this.yaw += this.yawSpeed;
    }
    public void turnAll(){
        turnYaw();
    }

    final public void moveY(){
        this.posY += (int)this.motionY;
    }
    final public void moveX(){
        this.posX += (int)this.motionX;
    }
    public void moveAll(){
        moveX();
        moveY();
    }
}
