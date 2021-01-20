package com.bytesmyth.lifegame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.bytesmyth.application.Game;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.batch.QuadTextureBatcher;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.ui.GuiGraphics;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.domain.item.ItemRegistry;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapRenderer;
import com.bytesmyth.lifegame.ui.InGameHud;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import org.joml.Vector2f;
import org.w3c.dom.Text;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class LifeGame implements Game {

    private final GameContext context;

    public static final String PLAYER_INVENTORY = "player_inventory";
    public static final String TRANSFER_INVENTORY = "transfer_inventory";

    private World world;
    private TileMap map;

    private InterpolatedRenderer renderer;
    private TileMapRenderer tileMapRenderer;

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

    public World getWorld() {
        return world;
    }

    public TileMap getMap() {
        return map;
    }

    public Input getInput() {
        return context.getInput();
    }

    public void init() {
        worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));

        uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));

        QuadTextureBatcher batcher = new QuadTextureBatcher(worldCamera);
        renderer = new InterpolatedRenderer(batcher);

        uiBatcher = new QuadTextureBatcher(uiCamera);
        Texture uiTexture = new Texture("/textures/gui-tileset.png");
        TextureAtlas uiAtlas = new TextureAtlas(uiTexture, 16, 16);

        ItemRegistry itemRegistry = DefaultItemRegistry.create(uiAtlas);

        guiGraphics = new GuiGraphics(this.uiCamera, this.uiBatcher, uiTexture, itemRegistry);
        guiManager = new GuiManager(guiGraphics);
        guiManager.registerGui("hud", new InGameHud());
        guiManager.enableGui("hud");

        guiManager.registerGui(PLAYER_INVENTORY, new PlayerInventoryUI(5, 3));
        guiManager.registerGui(TRANSFER_INVENTORY, new InventoryTransferGui(5, 3));
//
        Texture mapTexture = new Texture("/textures/village_tileset.png");
        mapTextureAtlas = new TextureAtlas(mapTexture, 16, 16);

        TestMapGen testMapGen = new TestMapGen();
        this.map = testMapGen.newMap();

        WorldConfiguration config = WorldConfig.createDefault();
        config.register(worldCamera);
        config.register(renderer);
        config.register(map);
        config.register(context);
        config.register(guiManager);
        config.register(this);
        world = new World(config);

        tileMapRenderer = new TileMapRenderer(worldCamera, batcher, DefaultTileRegistry.create(world, mapTextureAtlas));

        testMapGen.addRandomBushes(map, world, 30, 0.45f);
        testMapGen.addRandomRocks(map, 30);
        testMapGen.addRandomCoins(world, uiAtlas, 15);

        ChestFactory chestFactory = new ChestFactory(map, world, guiManager);
        chestFactory.create(19, 18);
        chestFactory.create(13, 18);

        Texture characterTexture = new Texture("/textures/character1.png");
        TextureAtlas characterAtlas = new TextureAtlas(characterTexture, 16, 32);
        CharacterFactory characterFactory = new CharacterFactory(world, characterAtlas);
        this.player = characterFactory.create(16, 16);
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
        InGameHud hud = (InGameHud) guiManager.getGui("hud");
        hud.setUiMousePosition(uiCamera.toCameraCoordinates(context.getInput().getMousePosition()));
        hud.setWorldMousePosition(worldCamera.toCameraCoordinates(context.getInput().getMousePosition()));
        hud.setFps(context.getFps());

        List<String> enabledGuis = guiManager.getEnabledGuis();
        boolean otherGuiOpen = !(enabledGuis.size() == 1 && enabledGuis.contains("hud"));

        if (context.getInput().getKey("E").isJustPressed()) {
            PlayerInventoryUI inventoryGui = (PlayerInventoryUI) guiManager.getGui(PLAYER_INVENTORY);

            if (guiManager.isEnabled(PLAYER_INVENTORY)) {
                inventoryGui.setCurrentInventory(null);
                guiManager.disableGui(PLAYER_INVENTORY);
            } else if (!otherGuiOpen) {
                InventoryComponent inventory = world.getEntity(player).getComponent(InventoryComponent.class);
                inventoryGui.setCurrentInventory(inventory.getInventory());
                guiManager.enableGui(PLAYER_INVENTORY);
            }
        }

        guiManager.handleInput(context.getInput());
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
