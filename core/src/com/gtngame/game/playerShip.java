package com.gtngame.game;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class playerShip extends Gameobject2D {
    AssetLoader al;
    public playerShip(Decal myDecal, AssetLoader al) {
        this.myDecal = myDecal;
        this.al = al;
    }
}

