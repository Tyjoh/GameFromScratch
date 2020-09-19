package com.bytesmyth.testgame;

import com.artemis.*;
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
import com.bytesmyth.input.Input;
import com.bytesmyth.input.InputWrapper;
import com.bytesmyth.testgame.components.*;
import com.bytesmyth.testgame.item.Inventory;
import com.bytesmyth.testgame.systems.*;
import com.bytesmyth.testgame.tilemap.TileMap;
import com.bytesmyth.testgame.tilemap.TileMapFactory;
import com.bytesmyth.testgame.tilemap.TileMapRenderer;
import com.bytesmyth.testgame.ui.GuiFactory;
import com.bytesmyth.testgame.ui.GuiManager;
import com.bytesmyth.ui.Gui;
import com.bytesmyth.ui.Label;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Game implements TickHandler, DrawHandler, WindowSizeListener {

    private final World world;
    private final Renderer renderer;
    private final TileMapRenderer tileMapRenderer;
    private final TileMap map;

    private Input input;
    private final TextureAtlas mapTextureAtlas;

    private final OrthographicCamera2D uiCamera;

    private final OrthographicCamera2D worldCamera;
    private final GuiManager guiManager;

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
        TextureAtlas uiAtlas = new TextureAtlas(uiTexture, 16, 16);

        GuiFactory guiFactory = new GuiFactory(uiTexture, uiBatcher, uiCamera);

        guiManager = new GuiManager();
        guiManager.registerGui("hud", guiFactory.createHUDGui());
        guiManager.enableGui("hud");
        guiManager.registerGui("player_inventory", guiFactory.createPlayerInventoryGui());
        guiManager.registerGui("inventory_transfer", guiFactory.createInventoryTransferGui());
        guiManager.enableGui("inventory_transfer");

//        InventoryUIDecorator inventoryDecorator = new InventoryUIDecorator();
//        inventoryDecorator.addInventory(gui, "test", 5, 3);

        tileMapRenderer = new TileMapRenderer(worldCamera, batcher);

        Texture mapTexture = new Texture("/textures/village_tileset.png");
        mapTextureAtlas = new TextureAtlas(mapTexture, 16, 16);

        map = new TileMapFactory().create(mapTextureAtlas);

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new EntityControlSystem())
                .with(new PositionIntegrationSystem())
                .with(new TileMapCollisionSystem())
                .with(new ItemPickupSystem())
                .with(new InventoryControlSystem())
                .with(new CameraFollowSystem())
                .with(new DirectionalAnimationSystem())
                .with(new TextureAnimationSystem())
                .with(new TextureGraphicsRenderingSystem())
                .build();

        config.register(worldCamera);
        config.register(renderer);
        config.register(map);
        config.register(new InputWrapper(input));
        config.register(guiManager);

        world = new World(config);

        Texture characterTexture = new Texture("/textures/character1.png");
        TextureAtlas textureAtlas = new TextureAtlas(characterTexture, 16, 32);

        int character = world.create();

        TexturedGraphics characterGraphics = new TexturedGraphics()
                .setTextureAtlas(textureAtlas)
                .setTileId(0)
                .setShape(new Rectangle(1, 2));

        AnimationTimeline downAnim = new AnimationTimeline("down", new int[]{0,1,2,3});
        AnimationTimeline rightAnim = new AnimationTimeline("right", new int[]{4,5,6,7});
        AnimationTimeline upAnim = new AnimationTimeline("up", new int[]{8,9,10,11});
        AnimationTimeline leftAnim = new AnimationTimeline("left", new int[]{12,13,14,15});
        Animation animation = new Animation(upAnim, downAnim, leftAnim, rightAnim);

        world.edit(character)
                .add(new Transform().setPosition(new Vector2f(32, 32)))
                .add(new Velocity())
                .add(new Direction())
                .add(new UserControl())
                .add(new CameraFollow())
                .add(new Collider().setHitBox(new Rectangle(0.85f, 0.5f)).setOffset(new Vector2f(0, -0.3f)))
                .add(new AnimatedTextureGraphics().setAnimation(animation).setCurrentAnimation("down"))
                .add(new InventoryComponent().setInventory(new Inventory(15)))
                .add(new Pickup())
                .add(characterGraphics);

        Archetype coinArchetype = new ArchetypeBuilder().add(
                Transform.class,
                ItemComponent.class,
                TexturedGraphics.class
        ).build(world);

        for (int i = 0; i < 25; i++) {
            int coin = world.create(coinArchetype);
            float x = (float) Math.random() * 64;
            float y = (float) Math.random() * 64;
            world.getMapper(Transform.class).get(coin).setPosition(new Vector2f(x, y));
            world.getMapper(TexturedGraphics.class).get(coin)
                    .setTextureAtlas(uiAtlas)
                    .setTileId(uiAtlas.tileCoordToId(0,16 + 8))
                    .setShape(new Rectangle(1f, 1f));
        }
    }

    @Override
    public void draw(float dt) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        tileMapRenderer.render(map, mapTextureAtlas);
        renderer.render(dt);

        guiManager.render();
    }

    @Override
    public void setFps(int currentFps) {
        Gui gui = guiManager.getGui("hud");

        if (gui.hasNode("fps_label")) {
            Label label = (Label) gui.getNode("fps_label");
            label.setText("FPS: " + currentFps);
        }
    }

    @Override
    public void tick(float dt) {
        world.delta = dt;
        world.process();

        Gui gui = guiManager.getGui("hud");

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
