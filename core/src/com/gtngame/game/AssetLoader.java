package com.gtngame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class AssetLoader {
    public SpriteBatch batch;
    public Sprite splashSprite;
    public Texture splashImg;//, debugModesTxr;
    //public TextureRegion dbgSplash, dbgCredits, dbgMM, dbgSettings, dbgGameplay, dbgPaused, dbgGameOver;
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
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    public void buildModel(){
        //planeMaterial = new Material();
        modelInstances = new Array<ModelInstance>();
        ModelBuilder modelBuilder = new ModelBuilder();
        cubeModel = modelBuilder.createBox(4f, 4f, 4f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        cubeInstance = new ModelInstance(cubeModel);
        modelInstances.add(cubeInstance);
        planeModel = modelBuilder.createLineGrid(100, 100, 10, 10, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position);
        planeInstance = new ModelInstance(planeModel);
        planeInstance.transform.setToTranslation(0,-2,0);
        modelInstances.add(planeInstance);
    }

    public void dispose () {
        batch.dispose();
        splashImg.dispose();
        modelBatch.dispose();
        cubeModel.dispose();
        //debugModesTxr.dispose();
    }
}
