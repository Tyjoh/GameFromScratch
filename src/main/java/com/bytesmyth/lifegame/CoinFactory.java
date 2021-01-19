package com.bytesmyth.lifegame;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.ecs.components.ItemComponent;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

public class CoinFactory {

    private final World world;
    private final Archetype coinArchetype;
    private TextureAtlas uiAtlas;

    public CoinFactory(World world, TextureAtlas uiAtlas) {
        this.world = world;
        coinArchetype = new ArchetypeBuilder().add(
                TransformComponent.class,
                ItemComponent.class,
                SpriteGraphicsComponent.class
        ).build(world);
        this.uiAtlas = uiAtlas;
    }

    public int create(float x, float y) {
        int coin = world.create(coinArchetype);
        world.getMapper(TransformComponent.class).get(coin).setPosition(new Vector2f(x, y));
        world.getMapper(SpriteGraphicsComponent.class).get(coin)
                .setTextureAtlas(uiAtlas)
                .setTileId(uiAtlas.tileCoordToId(0,16 + 8))
                .setShape(new Rectangle(1f, 1f));
        return coin;
    }

}
