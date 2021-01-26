package com.bytesmyth.lifegame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.bytesmyth.application.Game;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.ecs.components.InventoryComponent;
import com.bytesmyth.lifegame.tilemap.*;
import com.bytesmyth.lifegame.ui.InGameHud;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import com.bytesmyth.resources.Assets;
import org.joml.Vector2f;

import java.util.List;

import static com.bytesmyth.lifegame.tilemap.RectangleAutoTiler.BOTTOM;
import static org.lwjgl.opengl.GL11.*;

public class LifeGame implements Game {

    private final GameContext context;

    public static final String PLAYER_INVENTORY = "player_inventory";
    public static final String TRANSFER_INVENTORY = "transfer_inventory";

    private World world;
    private TileMap map;
    private GuiManager guiManager;

    private TileMapRenderer tileMapRenderer;
    private Graphics worldGraphics;
    private Graphics uiGraphics;

    private int playerEntity;
    private int cameraEntity;
    private SpriteRegistry spriteRegistry;

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
        //initialize textures and sprite mapping system.
//        Texture worldTileset = Assets.loadTexture("/textures/village_tileset.png");
//        TextureAtlas worldAtlas = new TextureAtlas(worldTileset, 16, 16);

//        Texture uiTileset = Assets.loadTexture("/textures/gui-tileset.png");
//        TextureAtlas uiAtlas = new TextureAtlas(uiTileset, 16,16);

//        spriteRegistry = DefaultSpriteRegistry.create(worldAtlas, uiAtlas);

        //initialize world graphics rendering systems
        OrthographicCamera2D worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));
        worldCamera.writePrevTransform();
        worldGraphics = new Graphics(worldCamera, spriteRegistry, 32);

        //initialize ui graphics rendering systems
        OrthographicCamera2D uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));
        uiGraphics = new Graphics(uiCamera, spriteRegistry, 1024);

        //initialize gui
        guiManager = new GuiManager(uiGraphics);
        guiManager.registerGui("hud", new InGameHud());
        guiManager.enableGui("hud");

        guiManager.registerGui(PLAYER_INVENTORY, new PlayerInventoryUI(5, 3));
        guiManager.registerGui(TRANSFER_INVENTORY, new InventoryTransferGui(5, 3));

        Texture groundTiles = Assets.loadTexture("/textures/ground_tiles.png");
        TextureAtlas atlas = new TextureAtlas(groundTiles, 16, 16);

        TileRegistry tileRegistry = DefaultTileRegistry.create(atlas);
        TileMapGen2 mapGen = new TileMapGen2(tileRegistry);
        map = mapGen.blankGrass();

        mapGen.addAutoTileDemo(32, 32, map);
        mapGen.addAutoTileDemo(9, 23, map);
        mapGen.rectangle(0, 1, 63, 63, 2, map);
        mapGen.rectangle(2, 2, 5, 5, 2, map);

        Tiler globalTiler = tileRegistry.getCombinedTiler();
        TileMapLayer l0 = map.getLayer("0");
        TileMapLayer l1 = map.getLayer("1");
        TileMapLayer l2 = map.getLayer("2");

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                globalTiler.tile(x, y, l0);
                globalTiler.tile(x, y, l1);
                globalTiler.tile(x, y, l2);
            }
        }

        WorldConfiguration config = WorldConfig.createDefault();
        //TODO: only pass in 'LifeGame'. Add getters for world, map, graphics, context etc.
        config.register(worldCamera);
        config.register(worldGraphics);
        config.register(map);
        config.register(context);
        config.register(guiManager);
        config.register(this);
        world = new World(config);

        tileMapRenderer = new TileMapRenderer(worldGraphics, groundTiles);

        Texture characterTexture = new Texture("/textures/main_character.png");
        TextureAtlas characterAtlas = new TextureAtlas(characterTexture, 16, 16);
        CharacterFactory characterFactory = new CharacterFactory(world, characterAtlas);
        this.playerEntity = characterFactory.create(16, 16);
    }

    @Override
    public void tick(float delta) {
        worldGraphics.getCamera().writePrevTransform();
        world.delta = delta;
        world.process();
        tileMapRenderer.render(map);
    }

    @Override
    public void render(float alpha) {
        tickGui(alpha);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldGraphics.getCamera().setAlpha(alpha);

        tileMapRenderer.render(map);
        worldGraphics.render(alpha);
        guiManager.render();
    }

    private void tickGui(float alpha) {
        InGameHud hud = (InGameHud) guiManager.getGui("hud");
        hud.setUiMousePosition(uiGraphics.toCameraCoordinates(getInput().getMousePosition()));
        hud.setWorldMousePosition(worldGraphics.toCameraCoordinates(getInput().getMousePosition()));
        hud.setFps(context.getFps());

        List<String> enabledGuis = guiManager.getEnabledGuis();
        boolean otherGuiOpen = !(enabledGuis.size() == 1 && enabledGuis.contains("hud"));

        if (context.getInput().getKey("E").isJustPressed()) {
            PlayerInventoryUI inventoryGui = (PlayerInventoryUI) guiManager.getGui(PLAYER_INVENTORY);

            if (guiManager.isEnabled(PLAYER_INVENTORY)) {
                inventoryGui.setCurrentInventory(null);
                guiManager.disableGui(PLAYER_INVENTORY);
            } else if (!otherGuiOpen) {
                InventoryComponent inventory = world.getEntity(playerEntity).getComponent(InventoryComponent.class);
                inventoryGui.setCurrentInventory(inventory.getInventory());
                guiManager.enableGui(PLAYER_INVENTORY);
            }
        }

        guiManager.handleInput(context.getInput());
    }

    @Override
    public void onWindowResized(int width, int height) {
        System.out.println("Screen size changed " + width + "," + height);
        worldGraphics.setScreenSize(width, height);
        uiGraphics.setScreenSize(width, height);
    }

    @Override
    public void dispose() {
        System.out.println("Shutting down");
    }

    public SpriteRegistry getSpriteRegistry() {
        return spriteRegistry;
    }

    public Vector2f getWorldMousePosition() {
        return worldGraphics.toCameraCoordinates(getInput().getMousePosition());
    }
}
