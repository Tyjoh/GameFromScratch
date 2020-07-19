package com.bytesmyth.testgame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.bytesmyth.game.DrawHandler;
import com.bytesmyth.game.TickHandler;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.ui.*;
import com.bytesmyth.input.Input;
import com.bytesmyth.input.InputWrapper;
import com.bytesmyth.testgame.components.*;
import com.bytesmyth.testgame.systems.*;
import com.bytesmyth.testgame.tilemap.TileMap;
import com.bytesmyth.testgame.tilemap.TileMapFactory;
import com.bytesmyth.testgame.tilemap.TileMapRenderer;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Game implements TickHandler, DrawHandler {


    private final World world;
    private final Renderer renderer;
    private final TileMapRenderer tileMapRenderer;
    private final TileMap map;

    private Input input;
    private final TextureAtlas mapTextureAtlas;

    private final QuadTextureBatcher uiBatcher;
    private final Texture uiTexture;

    private final Gui gui;
    private final OrthographicCamera2D uiCamera;

    public Game(Input input) {
        this.input = input;

        float ratio = 1280 / 32;
        float height = 800 / ratio;

        OrthographicCamera2D worldCamera = new OrthographicCamera2D(-16, 16, -height / 2f, height / 2f, 0, 100);
        worldCamera.setPosition(new Vector2f(16, 16));

        float widthUI = 1024;
        float ratioUI = 1280 / widthUI;
        float heightUI = 800 / ratioUI;
        uiCamera = new OrthographicCamera2D(-widthUI/2f, widthUI/2f, -heightUI / 2f, heightUI / 2f, 0, 100);
        uiCamera.setPosition(new Vector2f(0, 0));

        QuadTextureBatcher batcher = new QuadTextureBatcher(worldCamera);
        renderer = new Renderer(batcher);

        uiBatcher = new QuadTextureBatcher(uiCamera);

        uiTexture = new Texture("/textures/gui-tileset.png");

        Pane root = new Pane(512, 256 + 128);
        gui = new Gui(root, uiTexture, uiBatcher);

        Button leftButton = new Button("Left", 128, 32).setColor(new Vector3f(1f, 0.3f, 0.3f));
        BasicPositioning left = BasicPositioning.bottomLeft(8);
        root.addChild(leftButton, left);

        Button centerButton = new Button("CENTER", 128, 32).setColor(new Vector3f(0.3f, 0.3f, 1f));
        BasicPositioning center = BasicPositioning.bottomCenter(8);
        root.addChild(centerButton, center);

        Button rightButton = new Button("Right", 128, 32).setColor(new Vector3f(0.3f, 1f, 0.3f));
        BasicPositioning right = BasicPositioning.bottomRight(8);
        root.addChild(rightButton, right);

        Button sideButton = new Button("",24, 24);
        BasicPositioning rightSide = new BasicPositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, 36f);
        root.addChild(sideButton, rightSide);

        Button sideButtonTop = new Button("",24, 24);
        BasicPositioning rightSideTop = new BasicPositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, 0f);
        root.addChild(sideButtonTop, rightSideTop);

        Button sideButtonBottom = new Button("",24, 24);
        BasicPositioning rightSideBottom = new BasicPositioning(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, -4, -36f);
        root.addChild(sideButtonBottom, rightSideBottom);

        tileMapRenderer = new TileMapRenderer(worldCamera, batcher);

        Texture mapTexture = new Texture("/textures/village_tileset.png");
        mapTextureAtlas = new TextureAtlas(mapTexture, 16, 16);

        map = new TileMapFactory().create(mapTextureAtlas);

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new EntityControlSystem())
                .with(new PositionIntegrationSystem())
                .with(new TileMapCollisionSystem())
                .with(new CameraFollowSystem())
                .with(new DirectionalAnimationSystem())
                .with(new TextureAnimationSystem())
                .with(new TextureGraphicsRenderingSystem())
                .build();

        config.register(worldCamera);
        config.register(renderer);
        config.register(map);
        config.register(new InputWrapper(input));

        world = new World(config);

        Texture characterTexture = new Texture("/textures/character1.png");
        TextureAtlas textureAtlas = new TextureAtlas(characterTexture, 16, 32);

        int i = world.create();

        TexturedGraphics graphics = new TexturedGraphics()
                .setTextureAtlas(textureAtlas)
                .setTileId(0)
                .setShape(new Rectangle(1, 2));

        AnimationTimeline downAnim = new AnimationTimeline("down", new int[]{0,1,2,3});
        AnimationTimeline rightAnim = new AnimationTimeline("right", new int[]{4,5,6,7});
        AnimationTimeline upAnim = new AnimationTimeline("up", new int[]{8,9,10,11});
        AnimationTimeline leftAnim = new AnimationTimeline("left", new int[]{12,13,14,15});
        Animation animation = new Animation(upAnim, downAnim, leftAnim, rightAnim);

        world.edit(i)
                .add(new Transform().setPosition(new Vector2f(32, 32)))
                .add(new Velocity())
                .add(new InputControl())
                .add(new CameraFollow())
                .add(new Collider().setHitBox(new Rectangle(0.85f, 0.5f)).setOffset(new Vector2f(0, -0.3f)))
                .add(new AnimatedTextureGraphics().setAnimation(animation).setCurrentAnimation("down"))
                .add(graphics);
    }

    @Override
    public void draw(float dt) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        tileMapRenderer.render(map, mapTextureAtlas);
        renderer.render(dt);

        gui.render();
    }

    @Override
    public void tick(float dt) {
        world.delta = dt;
        world.process();

        gui.handleGuiInput(input);
    }
}
