package com.bytesmyth.lifegame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.bytesmyth.application.Game;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.graphics.Graphics;
import com.bytesmyth.graphics.camera.OrthographicCamera2D;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.control.*;
import com.bytesmyth.lifegame.domain.building.Building;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.tilemap.*;
import com.bytesmyth.lifegame.ui.Guis;
import com.bytesmyth.lifegame.ui.InGameHud;
import com.bytesmyth.lifegame.ui.InventoryTransferGui;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import com.bytesmyth.resources.Assets;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class LifeGame implements Game {

    private final GameContext context;

    private World world;
    private TileMap map;
    private GuiManager guiManager;
    private Controls controls;

    private TileMapRenderer tileMapRenderer;
    private Graphics worldGraphics;
    private Graphics uiGraphics;

    private int playerEntity;
    private SpriteRegistry spriteRegistry;
    private TileRegistry tileRegistry;

    public LifeGame(GameContext context) {
        this.context = context;
    }

    public World getWorld() {
        return world;
    }

    public TileMap getMap() {
        return map;
    }

    public Controls getControls() {
        return controls;
    }

    public Graphics getWorldGraphics() {
        return worldGraphics;
    }

    public Graphics getUiGraphics() {
        return uiGraphics;
    }

    public TileRegistry getTileRegistry() {
        return tileRegistry;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void init() {
        //initialize textures and sprite mapping system.
        Texture groundTileTexture = Assets.loadTexture("/textures/ground_tiles.png");
        TextureAtlas groundTiles = new TextureAtlas(groundTileTexture, 16, 16);

        spriteRegistry = DefaultSpriteRegistry.create(groundTiles);

        //initialize world graphics rendering systems
        OrthographicCamera2D worldCamera = new OrthographicCamera2D();
        worldCamera.setPosition(new Vector2f(16, 16));
        worldCamera.writePrevTransform();
        worldGraphics = new Graphics(worldCamera, spriteRegistry, 32);

        //initialize ui graphics rendering systems
        OrthographicCamera2D uiCamera = new OrthographicCamera2D();
        uiCamera.setPosition(new Vector2f(0, 0));
        uiGraphics = new Graphics(uiCamera, spriteRegistry, 1024);

        KeyboardVectorControl movement = new KeyboardVectorControl();
        ActivationControl interact = new KeyboardActivationControl("F");
        ActivationControl inventoryControl = new KeyboardActivationControl("E");
        VectorControl lookDirection = new MouseDirectionVectorControl(worldGraphics::toCameraCoordinates, () ->
            world.getEntity(playerEntity).getComponent(TransformComponent.class).getPosition()
        );

        controls = new Controls(movement, lookDirection, interact, inventoryControl);

        //initialize gui
        guiManager = new GuiManager(uiGraphics);
        guiManager.registerGui(Guis.DEBUG_HUD,"debug", new InGameHud(this, context));
        guiManager.enableGui(Guis.DEBUG_HUD);

        guiManager.registerGui(Guis.PLAYER_INVENTORY, "player", new PlayerInventoryUI(5, 3));
        guiManager.registerGui(Guis.PLAYER_TRANSFER_INVENTORY, "player", new InventoryTransferGui(5, 3));

        tileRegistry = DefaultTileRegistry.create(groundTiles);
        ChunkGen mapGen = new ChunkGen(tileRegistry);

        map = new TileMap();
        MapChunk chunk1 = mapGen.blankGrassChunk(0, 0);
        MapChunk chunk2 = mapGen.blankGrassChunk(1, 0);
        MapChunk chunk3 = mapGen.blankGrassChunk(-1, 0);
        MapChunk chunk4 = mapGen.blankGrassChunk(0, 1);
        MapChunk chunk5 = mapGen.blankGrassChunk(0, -1);
        map.addChunk(chunk1);
        map.addChunk(chunk2);
        map.addChunk(chunk3);
        map.addChunk(chunk4);
        map.addChunk(chunk5);

        mapGen.addAutoTileDemo(32, 32, map);
        mapGen.addAutoTileDemo(9, 23, map);
        mapGen.rectangle(0, 1, 63, 63, 2, map);
        mapGen.rectangle(2, 2, 5, 5, 2, map);

        Tiler globalTiler = tileRegistry.getCombinedTiler();
        TileMapLayer l0 = map.getLayer("0");
        TileMapLayer l1 = map.getLayer("1");
        TileMapLayer l2 = map.getLayer("2");

        for (MapChunk chunk : map.getLoadedChunks()) {
            for (int y = 0; y < chunk.getSize(); y++) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    globalTiler.tile(chunk.localToTileX(x), chunk.localToTileY(y), l0);
                    globalTiler.tile(chunk.localToTileX(x), chunk.localToTileY(y), l1);
                    globalTiler.tile(chunk.localToTileX(x), chunk.localToTileY(y), l2);
                }
            }
        }

        TextureRegion[] doorTiles = {
                groundTiles.getRegionByCoord(5, 12),
                groundTiles.getRegionByCoord(6, 12),
                groundTiles.getRegionByCoord(7, 12),

                groundTiles.getRegionByCoord(5, 13),
                groundTiles.getRegionByCoord(6, 13),
                groundTiles.getRegionByCoord(7, 13),
        };

        Building building = new Building(7, 5, 2)
                .wallTheme(new NinePatchTileBuilder(groundTiles).topLeft(0, 11).buildTextureArray())
                .roofTheme(new NinePatchTileBuilder(groundTiles).topLeft(0, 7).buildTextureArray())
                .setPosition(36, 14)
                .setDoor(2, 0, doorTiles);
        building.addToMap(map);

        WorldConfiguration config = WorldConfig.createDefault();
        config.register(worldCamera);
        config.register(worldGraphics);
        config.register(map);
        config.register(context);
        config.register(guiManager);
        config.register(this);
        config.register(controls);
        world = new World(config);

        tileMapRenderer = new TileMapRenderer(worldGraphics, groundTileTexture);

        Texture characterTexture = new Texture("/textures/main_character.png");
        TextureAtlas characterAtlas = new TextureAtlas(characterTexture, 16, 16);
        CharacterFactory characterFactory = new CharacterFactory(world, characterAtlas);
        this.playerEntity = characterFactory.create(16, 16);

        new ChestFactory(this).create(16, 8);

        new VegetableFactory(this).create(20, 5);
        new VegetableFactory(this).create(21, 5);
        new VegetableFactory(this).create(22, 5);
        new VegetableFactory(this).create(23, 5);
        new VegetableFactory(this).create(20, 6);
        new VegetableFactory(this).create(21, 6);
        new VegetableFactory(this).create(22, 6);
        new VegetableFactory(this).create(23, 6);
    }

    @Override
    public void tick(float dt) {
        controls.tick(dt);
        guiManager.tick(dt);

        worldGraphics.getCamera().writePrevTransform();
        world.delta = dt;
        world.process();
        tileMapRenderer.render(map);
    }

    @Override
    public void render(float alpha) {
        controls.poll(context.getInput());
        guiManager.poll(context.getInput());

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        worldGraphics.getCamera().setAlpha(alpha);
        worldGraphics.render(alpha);
        guiManager.render();
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

}
