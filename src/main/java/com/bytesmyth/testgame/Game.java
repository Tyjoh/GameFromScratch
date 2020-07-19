package com.bytesmyth.testgame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.bytesmyth.game.DrawHandler;
import com.bytesmyth.game.TickHandler;
import com.bytesmyth.game.WindowSizeListener;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.testgame.ui.HudGuiDecorator;
import com.bytesmyth.testgame.ui.InventoryUIDecorator;
import com.bytesmyth.ui.*;
import com.bytesmyth.input.Input;
import com.bytesmyth.input.InputWrapper;
import com.bytesmyth.testgame.components.*;
import com.bytesmyth.testgame.systems.*;
import com.bytesmyth.testgame.tilemap.TileMap;
import com.bytesmyth.testgame.tilemap.TileMapFactory;
import com.bytesmyth.testgame.tilemap.TileMapRenderer;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Game implements TickHandler, DrawHandler, WindowSizeListener {

    private final World world;
    private final Renderer renderer;
    private final TileMapRenderer tileMapRenderer;
    private final TileMap map;

    private Input input;
    private final TextureAtlas mapTextureAtlas;

    private final Gui gui;
    private final OrthographicCamera2D uiCamera;

    private final OrthographicCamera2D worldCamera;

    public Game(Input input) {
        this.input = input;

        worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));

        uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));

        QuadTextureBatcher batcher = new QuadTextureBatcher(worldCamera);
        renderer = new Renderer(batcher);

        QuadTextureBatcher uiBatcher = new QuadTextureBatcher(uiCamera);
        Texture uiTexture = new Texture("/textures/gui-tileset.png");

        gui = new Gui(uiTexture, uiBatcher, uiCamera);
        HudGuiDecorator hudDecorator = new HudGuiDecorator();
        hudDecorator.addFpsDisplay(gui);
        hudDecorator.addUIMousePositionDisplay(gui);
        hudDecorator.addWorldMousePositionDisplay(gui);

        InventoryUIDecorator inventoryDecorator = new InventoryUIDecorator();
        inventoryDecorator.addInventory(gui, "test", 5, 3);

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
    public void setFps(int currentFps) {
        if (gui.hasNode("fps_label")) {
            Label label = (Label) gui.getNode("fps_label");
            label.setText("FPS: " + currentFps);
        }
    }

    @Override
    public void tick(float dt) {
        world.delta = dt;
        world.process();

        if (gui.hasNode("ui_mouse_position_label")) {
            Vector2f mouseUI = uiCamera.toCameraCoordinates(input.getMousePosition());
            Label label = (Label) gui.getNode("ui_mouse_position_label");
            label.setText(String.format("UI Mouse: (%.1f, %.1f)", mouseUI.x, mouseUI.y));
        }

        if (gui.hasNode("world_mouse_position_label")) {
            Vector2f mouseWorld = worldCamera.toCameraCoordinates(input.getMousePosition());
            Label label = (Label) gui.getNode("world_mouse_position_label");
            label.setText(String.format("World Mouse: (%.1f, %.1f)", mouseWorld.x, mouseWorld.y));
        }

        gui.handleGuiInput(input);
    }

    @Override
    public void onWindowSizeChanged(int width, int height) {
        System.out.println("Screen size changed " + width + "," + height);

        float widthWorld = 32;
        float ratio = width / widthWorld;
        float heightWorld = height / ratio;

        worldCamera.setCameraView(-16, 16, -heightWorld / 2f, heightWorld / 2f, 0, 100);

        float widthUI = 1024;
        float ratioUI = width / widthUI;
        float heightUI = height / ratioUI;
        uiCamera.setCameraView(-widthUI/2f, widthUI/2f, -heightUI / 2f, heightUI / 2f, 0, 100);
    }
}
