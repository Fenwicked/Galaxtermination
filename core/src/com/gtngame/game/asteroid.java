package com.gtngame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class asteroid extends Gameobject3D {
    ModelInstance myMI;
    Random r;
    public asteroid(float startX, float startZ, AssetLoader al){
        this.posX = startX;
        this.posZ = startZ;
        this.al = al;
        r = new Random();
        yawSpeed = r.nextFloat();
        pitchSpeed = r.nextFloat();
        rollSpeed = r.nextFloat();
        al.addAsteroidAt(this);
    }

    public void updateAst(){
        yaw += yawSpeed;
        pitch += pitchSpeed;
        roll += rollSpeed;
        refreshAst();
        //al.modelInstances.add(myMI);
        /*if (collide) {
            for (enemyShip nme : enmes) {
                if (getDistForCollision(nme) <= 2) {
                    al.enmes.removeValue(nme, true);
                    al.Decals.removeValue(nme.myDecal, true);
                    al.oww.play(1.5f);
                }
            }
        }*/
    }
    public void refreshAst(){
        al.addAsteroidMIAt(posX, -1.5f, posZ, (float)yaw, pitch, roll);
    }
}
