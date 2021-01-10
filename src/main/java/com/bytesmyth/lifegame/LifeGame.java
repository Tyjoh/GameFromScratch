package com.bytesmyth.lifegame;

import com.artemis.*;
import com.bytesmyth.application.*;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.ui.GuiGraphics;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.*;
import com.bytesmyth.lifegame.ecs.systems.*;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapFactory;
import com.bytesmyth.lifegame.tilemap.TileMapRenderer;
import com.bytesmyth.lifegame.ui.InGameHud;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import com.bytesmyth.lifegame.ui.TestUI;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class LifeGame implements Game {

    private final GameContext context;

    public static final String PLAYER_INVENTORY = "player_inventory";

    private World world;
    private Renderer renderer;
    private TileMapRenderer tileMapRenderer;
    private TileMap map;

    private TextureAtlas mapTextureAtlas;

    private OrthographicCamera2D uiCamera;

    private OrthographicCamera2D worldCamera;
    private GuiManager guiManager;

    private QuadTextureBatcher uiBatcher;
    private int player;
    private GuiGraphics guiGraphics;

    public LifeGame(GameContext context) {
        this.context = context;
    }

    public void init() {
        worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));

        uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));

        QuadTextureBatcher batcher = new QuadTextureBatcher(worldCamera);
        renderer = new Renderer(batcher);

        uiBatcher = new QuadTextureBatcher(uiCamera);
        Texture uiTexture = new Texture("/textures/gui-tileset.png");
        TextureAtlas uiAtlas = new TextureAtlas(uiTexture, 16, 16);

        guiGraphics = new GuiGraphics(this.uiCamera, this.uiBatcher, uiTexture);
        guiManager = new GuiManager(guiGraphics);
//        guiManager.registerGui("test", new TestUI());
//        guiManager.enableGui("test");

        guiManager.registerGui("hud", new InGameHud());
        guiManager.enableGui("hud");

        guiManager.registerGui(PLAYER_INVENTORY, new PlayerInventoryUI(5, 3));
//

        tileMapRenderer = new TileMapRenderer(worldCamera, batcher);

        Texture mapTexture = new Texture("/textures/village_tileset.png");
        mapTextureAtlas = new TextureAtlas(mapTexture, 16, 16);

        map = new TileMapFactory().create(mapTextureAtlas);

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new PrevTransformSystem())
                .with(new EntityControlSystem())
                .with(new PositionIntegrationSystem())
                .with(new TileMapCollisionSystem())
                .with(new ItemPickupSystem())
//                .with(new InventoryControlSystem())
                .with(new CameraFollowSystem())
                .with(new DirectionalAnimationSystem())
                .with(new TextureAnimationSystem())
                .with(new TextureGraphicsRenderingSystem())
                .build();

        config.register(worldCamera);
        config.register(renderer);
        config.register(map);
        config.register(context);
        config.register(guiManager);

        world = new World(config);

        Texture characterTexture = new Texture("/textures/character1.png");
        TextureAtlas textureAtlas = new TextureAtlas(characterTexture, 16, 32);

        player = world.create();

        TexturedGraphics characterGraphics = new TexturedGraphics()
                .setTextureAtlas(textureAtlas)
                .setTileId(0)
                .setShape(new Rectangle(1, 2));

        AnimationTimeline downAnim = new AnimationTimeline("down", new int[]{0,1,2,3});
        AnimationTimeline rightAnim = new AnimationTimeline("right", new int[]{4,5,6,7});
        AnimationTimeline upAnim = new AnimationTimeline("up", new int[]{8,9,10,11});
        AnimationTimeline leftAnim = new AnimationTimeline("left", new int[]{12,13,14,15});
        Animation animation = new Animation(upAnim, downAnim, leftAnim, rightAnim);

        world.edit(player)
                .add(new Transform().setPosition(new Vector2f(16, 16)))
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

        for (int i = 0; i < 400; i++) {
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
    public void render(float alpha) {
        tickGui(alpha);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldCamera.setAlpha(alpha);

        tileMapRenderer.render(map, mapTextureAtlas);
        renderer.render(alpha);

        guiManager.render();
    }

    private void tickGui(float alpha) {
        guiManager.handleInput(context.getInput());

        InGameHud hud = (InGameHud) guiManager.getGui("hud");
        hud.setUiMousePosition(uiCamera.toCameraCoordinates(context.getInput().getMousePosition()));
        hud.setWorldMousePosition(worldCamera.toCameraCoordinates(context.getInput().getMousePosition()));
        hud.setFps(context.getFps());

        if (context.getInput().getKey("E").isJustPressed()) {
            PlayerInventoryUI inventoryGui = (PlayerInventoryUI) guiManager.getGui(PLAYER_INVENTORY);

            if (guiManager.isEnabled(PLAYER_INVENTORY)) {
                System.out.println("Disabling player inventory");
                inventoryGui.setCurrentInventory(null);
                guiManager.disableGui(PLAYER_INVENTORY);
            } else {
                System.out.println("Enabling player inventory");
                InventoryComponent inventory = world.getEntity(player).getComponent(InventoryComponent.class);
                inventoryGui.setCurrentInventory(inventory.getInventory());
                guiManager.enableGui(PLAYER_INVENTORY);
            }
        }
    }

    @Override
    public void tick(float delta) {
        world.delta = delta;
        world.process();
    }

    @Override
    public void onWindowResized(int width, int height) {
        System.out.println("Screen size changed " + width + "," + height);

        float widthWorld = 32;
        float ratio = width / widthWorld;
        float heightWorld = height / ratio;
        worldCamera.setWindowSize(width, height);
        worldCamera.setCameraView(-widthWorld / 2f, widthWorld / 2f, -heightWorld / 2f, heightWorld / 2f, 0, 100);

        float widthUI = 1024;
        float ratioUI = width / widthUI;
        float heightUI = height / ratioUI;
        uiCamera.setWindowSize(width, height);
        uiCamera.setCameraView(-widthUI / 2f, widthUI / 2f, -heightUI / 2f, heightUI / 2f, 0, 100);
    }

    @Override
    public void dispose() {
        System.out.println("Shutting down");
    }
}
