package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class AssetLoader {
    public SpriteBatch batch;
    public Sprite splashSprite;
    public Array<Sprite> sprites;
    public Texture splashImg, starFieldImg, starFieldTrImg, playerShipImg;
    public TextureRegion playerShipReg, starFieldReg, starFieldTrReg;//dbgSplash, dbgCredits, dbgMM, dbgSettings, dbgGameplay, dbgPaused, dbgGameOver;
    public Float splashTimer;
    public VarLoader vl;
    public BitmapFont font;

    //3d stuff
    public Environment env;
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public Model cubeModel, planeModel;
    public ModelInstance cubeInstance, planeInstance;
    public Material planeMaterial;
    //public Array<Model> models;
    public Array<ModelInstance> modelInstances;
    public Decal floorDecal, shipDecal, enemyDecal;
    public Array<Decal> Decals;
    public DecalBatch decalBatch;
    public GroupStrategy strategy;

    public AssetLoader (VarLoader vl){
        this.vl = vl;
    }

    public void load () {
        //System.out.println("1");
        batch = new SpriteBatch();
        splashImg = new Texture("FGwide.jpg");
        splashSprite = new Sprite(splashImg, 0, 0, splashImg.getWidth(),splashImg.getHeight());
        //splashSprite.setPosition(Gdx.graphics.getDisplayMode().width / 2 - splashImg.getWidth() / 2, Gdx.graphics.getDisplayMode().height / 2 - splashImg.getHeight() / 2);
        splashSprite.setPosition(vl.windowWidth / 2 - splashImg.getWidth() / 2, vl.windowHeight / 2 - splashImg.getHeight() / 2);
        splashTimer = 0f;
        starFieldImg = new Texture("starfield.png");
        starFieldImg.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        starFieldReg = new TextureRegion(starFieldImg);
        starFieldReg.setRegion(0,0,starFieldImg.getWidth()*40,starFieldImg.getHeight()*40);

        starFieldTrImg = new Texture("starfieldTr.png");
        starFieldTrImg.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        starFieldTrReg = new TextureRegion(starFieldTrImg);
        starFieldTrReg.setRegion(0,0,starFieldTrImg.getWidth()*40,starFieldTrImg.getHeight()*40);
        starFieldTrReg.flip(true,true);

        playerShipImg = new Texture("playership.png");
        playerShipReg = new TextureRegion(playerShipImg, 0, 0, 32, 32);
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

/*        //3d stuff
        /*env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, vl.windowWidth, vl.windowHeight);
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        ModelBuilder modelBuilder = new ModelBuilder();
        cubeModel = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
*/
    }

    public void init3D(){
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, vl.windowWidth, vl.windowHeight);
        cam.position.set(5f, 15, 0f);
        cam.lookAt(-8,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        //camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        strategy = new CameraGroupStrategy(cam);
        decalBatch = new DecalBatch(strategy);
    }

    public void buildModel(){
        //planeMaterial = new Material();
        modelInstances = new Array<ModelInstance>();
        Decals = new Array<Decal>();
        //ModelBuilder modelBuilder = new ModelBuilder();
        //cubeModel = modelBuilder.createBox(4f, 4f, 4f,
        //        new Material(ColorAttribute.createDiffuse(Color.GREEN)),
        //        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        //cubeInstance = new ModelInstance(cubeModel);
        //modelInstances.add(cubeInstance);

        floorDecal = Decal.newDecal(starFieldTrReg.getRegionWidth()/4, starFieldTrReg.getRegionHeight()/4, starFieldTrReg, true);
        floorDecal.setDimensions(4000, 4000);
        floorDecal.setPosition(0, -2, 0);
        floorDecal.setRotationX(-90);
        Decals.add(floorDecal);

        floorDecal = Decal.newDecal(starFieldReg.getRegionWidth()/4, starFieldReg.getRegionHeight()/4, starFieldReg, true);
        floorDecal.setDimensions(4000, 4000);
        floorDecal.setPosition(0, -6, 0);
        floorDecal.setRotationX(-90);
        Decals.add(floorDecal);

        shipDecal = Decal.newDecal(playerShipReg.getRegionWidth()/8, playerShipReg.getRegionHeight()/8, playerShipReg, true);
        //shipDecal.setDimensions(1000, 1000);
        shipDecal.setPosition(0, -1.5f, 0);
        shipDecal.rotateX(-90);
        shipDecal.rotateZ(90);
        Decals.add(shipDecal);

    }

    public Decal addEnemyAt(float x, float y, float z){
        enemyDecal = Decal.newDecal(playerShipReg.getRegionWidth()/8, playerShipReg.getRegionHeight()/8, playerShipReg, true);
        //shipDecal.setDimensions(1000, 1000);
        enemyDecal.setPosition(x, y, z);
        enemyDecal.rotateX(-90);
        enemyDecal.rotateZ(90);
        Decals.add(enemyDecal);
        return enemyDecal;
    }

    public void dispose () {
        batch.dispose();
        splashImg.dispose();
        modelBatch.dispose();
//        cubeModel.dispose();
        //debugModesTxr.dispose();
    }
}
