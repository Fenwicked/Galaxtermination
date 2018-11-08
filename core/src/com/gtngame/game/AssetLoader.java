package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public SpriteBatch batch;
    public Sprite splashSprite;
    public Texture splashImg;//, debugModesTxr;
    //public TextureRegion dbgSplash, dbgCredits, dbgMM, dbgSettings, dbgGameplay, dbgPaused, dbgGameOver;
    public Float splashTimer;
    public VarLoader vl;
    public BitmapFont font;

    public AssetLoader (VarLoader vl){
        this.vl = vl;
    }

    public void load () {
        System.out.println("1");
        batch = new SpriteBatch();
        splashImg = new Texture("FGwide.jpg");
        splashSprite = new Sprite(splashImg, 0, 0, splashImg.getWidth(),splashImg.getHeight());
        //splashSprite.setPosition(Gdx.graphics.getDisplayMode().width / 2 - splashImg.getWidth() / 2, Gdx.graphics.getDisplayMode().height / 2 - splashImg.getHeight() / 2);
        splashSprite.setPosition(vl.windowWidth / 2 - splashImg.getWidth() / 2, vl.windowHeight / 2 - splashImg.getHeight() / 2);
        splashTimer = 0f;
        /*debugModesTxr = new Texture("debugtitles.png");
        dbgSplash = new TextureRegion(debugModesTxr, 0, 0, 69, 13);
        dbgCredits = new TextureRegion(debugModesTxr, 0, 13, 69, 10);
        dbgMM = new TextureRegion(debugModesTxr, 0, 23, 69, 9);
        dbgSettings = new TextureRegion(debugModesTxr, 0, 33, 69, 12);
        dbgGameplay = new TextureRegion(debugModesTxr, 0, 44, 69, 13);
        dbgPaused = new TextureRegion(debugModesTxr, 0, 58, 69, 10);
        dbgGameOver = new TextureRegion(debugModesTxr, 0, 67, 69, 9);
        */
        font = new BitmapFont();
    }

    public void dispose () {
        batch.dispose();
        splashImg.dispose();
        //debugModesTxr.dispose();
    }
}
