package com.gtngame.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.UBJsonReader;

public class AssetLoader {
    public SpriteBatch batch;
    public Sprite splashSprite;
    public Array<Sprite> sprites;
    public Texture splashImg, starFieldImg, starFieldTrImg, playerShipImg, logoImg, pausedImg, victoryImg, gameoverImg, shieldImg, titleBG;
    public TextureRegion playerShipReg, playerShipEngineReg, playerShipInvisReg, starFieldReg, starFieldTrReg, shieldReg;
    public Float splashTimer;
    public VarLoader vl;
    public BitmapFont font;

    //3d stuff
    public Environment env;
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public Model cubeModel, planeModel, shotModel, astModel;
    public ModelInstance cubeInstance, planeInstance, shotInstance, astInstance;
    public Material planeMaterial;
    //public Array<Model> models;
    public Array<ModelInstance> modelInstances;
    public Decal floorDecal, shipDecal, enemyDecal;
    public Array<Decal> Decals;
    public DecalBatch decalBatch;
    public GroupStrategy strategy;
    public Array<shot> shots;
    public Array<enemyShip> enmes;
    public Array<asteroid> asts;
    public Array<AnimationController> astAnim;
    public ShapeRenderer shaRend;
    public Sound shot, nmeshot, hurt, dead, nmeoww, astoww;
    private DirectionalLight light;
    AnimationController animControl;

    public AssetLoader (VarLoader vl){
        this.vl = vl;
    }

    public void load () {
        //System.out.println("1");
        enmes = new Array<enemyShip>();
        asts = new Array<asteroid>();
        astAnim = new Array<AnimationController>();
        batch = new SpriteBatch();
        modelBatch = new ModelBatch();
        shaRend = new ShapeRenderer();
        shaRend.setColor(Color.BLACK);
        //shaRend.setProjectionMatrix(camera.combined);
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
        playerShipEngineReg = new TextureRegion(playerShipImg, 0, 32, 32, 32);
        playerShipInvisReg = new TextureRegion(playerShipImg, 32, 32, 32, 32);

        shieldImg = new Texture("shield.png");
        shieldReg = new TextureRegion(shieldImg, 0, 0, 32, 32);

        logoImg = new Texture("GTNLOGO.png");
        pausedImg = new Texture("PAUSED.png");
        victoryImg = new Texture("VICTORY.png");
        gameoverImg = new Texture("GAMEOVER.png");
        titleBG = new Texture("titlebg.png");
        /*debugModesTxr = new Texture("debugtitles.png");
        dbgSplash = new TextureRegion(debugModesTxr, 0, 0, 69, 13);
        dbgCredits = new TextureRegion(debugModesTxr, 0, 13, 69, 10);
        dbgMM = new TextureRegion(debugModesTxr, 0, 23, 69, 9);
        dbgSettings = new TextureRegion(debugModesTxr, 0, 33, 69, 12);
        dbgGameplay = new TextureRegion(debugModesTxr, 0, 44, 69, 13);
        dbgPaused = new TextureRegion(debugModesTxr, 0, 58, 69, 10);
        dbgGameOver = new TextureRegion(debugModesTxr, 0, 67, 69, 9);
        */
        font = new BitmapFont(Gdx.files.internal("wr.fnt"));

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
        shot = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
        nmeshot = Gdx.audio.newSound(Gdx.files.internal("nmeshot.wav"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        nmeoww = Gdx.audio.newSound(Gdx.files.internal("nmeoww.wav"));
        astoww = Gdx.audio.newSound(Gdx.files.internal("astoww.wav"));

    }

    public void init3D(){
        light = new DirectionalLight().set(2.2f, 2.2f, 2.2f, -1f, -0.8f, -0.2f);
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 2.4f, 2.4f, 2.4f, 1f));
        env.add(light);
        //modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, vl.windowWidth, vl.windowHeight);
        cam.position.set(5f, 25, 0f);
        cam.lookAt(-9,0,0);
//        cam.position.set(0f, 150, 0f);
//        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        //camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        strategy = new CameraGroupStrategy(cam);
        decalBatch = new DecalBatch(strategy);
        modelInstances = new Array<ModelInstance>();
        shots = new Array<shot>();
    }

    public void buildModel(){
        //planeMaterial = new Material();
        //modelInstances = new Array<ModelInstance>();
        //ModelBuilder modelBuilder = new ModelBuilder();
        //cubeModel = modelBuilder.createBox(4f, 4f, 4f,
        //        new Material(ColorAttribute.createDiffuse(Color.GREEN)),
        //        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        //cubeInstance = new ModelInstance(cubeModel);
        //modelInstances.add(cubeInstance);
        Decals = new Array<Decal>();

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

        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        astModel = modelLoader.loadModel(Gdx.files.getFileHandle("asteroid.g3db", Files.FileType.Internal));
        //System.out.println("ANIM: "+astModel.animations.get(0).id);

//        for (int i = 0; i < astModel.animations.size; i++) {
//            //System.out.println(astModel.animations.get(i).id);
//            astAnim.add(new AnimationController())
//        }

    }

    public void addAsteroidAt(asteroid ast){
        asts.add(ast);
    }

    public ModelInstance addAsteroidMIAt(float x, float y, float z, float yaw, float pitch, float roll){
        /*UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        astModel = modelLoader.loadModel(Gdx.files.getFileHandle("asteroid.g3db", Files.FileType.Internal));*/
        astInstance = new ModelInstance(astModel);
        astInstance.transform.scale(1.8f,1.8f,1.8f);
        astInstance.transform.rotate(1, 0, 0, -90);
        astInstance.transform.rotate(Vector3.X, yaw);
        astInstance.transform.rotate(Vector3.Y, pitch);
        astInstance.transform.rotate(Vector3.Z, roll);
        astInstance.transform.setTranslation(x, y, z);
        modelInstances.add(astInstance);
        //asts.add(ast);

//        for (int i = 0; i < astModel.animations.size; i++) {
//            animControl = new AnimationController(astInstance);
//            animControl.setAnimation(astModel.animations.get(i).id);
//            //System.out.println(astModel.animations.get(i).id);
//            astAnim.add(animControl);
//        }
        //animControl.queue(ship.animations.get(0).id, 3, 1, null, 0);
        //animControl.queue(astModel.animations.get(1).id, 380, 0.3f, null, 0);
        //animControl.

        return astInstance;
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

    public void addBoxAt(float x, float y, float z){
        ModelBuilder modelBuilder = new ModelBuilder();
        cubeModel = modelBuilder.createBox(0.4f, 0.4f, 0.4f,
                new Material(ColorAttribute.createDiffuse(Color.ORANGE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        cubeInstance = new ModelInstance(cubeModel, new Vector3(x,y,z));
        modelInstances.add(cubeInstance);
    }

    public void addCircleAt(float x, float y, float z){
        ModelBuilder modelBuilder = new ModelBuilder();
        cubeModel = modelBuilder.createCylinder(4,0.1f,4,16,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        cubeInstance = new ModelInstance(cubeModel, new Vector3(x,y,z));
        modelInstances.add(cubeInstance);
    }

    public void addShotAt(shot sht){
        /*ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("line", 1, 3, new Material());
        builder.setColor(Color.CYAN);
        //System.out.println("shot 3: " + sht.posX + " " + sht.posZ + " " + sht.targetX + " " + sht.targetZ);
        builder.line(sht.posX, -1.8f, sht.posZ, sht.targetX, -1.8f, sht.targetZ);
        shotModel = modelBuilder.end();
        shotInstance = new ModelInstance(shotModel);*/
        //modelInstances.add(shotInstance);
        shots.add(sht);
    }
    public void dispose () {
        batch.dispose();
        splashImg.dispose();
        modelBatch.dispose();
//        cubeModel.dispose();
        //debugModesTxr.dispose();
    }
}
