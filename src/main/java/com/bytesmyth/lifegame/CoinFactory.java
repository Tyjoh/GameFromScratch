package com.bytesmyth.lifegame;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.sprite.StaticSprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.ecs.components.ItemComponent;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

public class CoinFactory {

    private final World world;
    private final Archetype coinArchetype;
    private final Sprite sprite;

    public CoinFactory(World world, TextureAtlas uiAtlas) {
        this.world = world;
        coinArchetype = new ArchetypeBuilder()
                .add(TransformComponent.class)
                .add(ItemComponent.class)
                .add(SpriteGraphicsComponent.class)
                .build(world);

        this.sprite = new StaticSprite(uiAtlas.getTexture(), uiAtlas.getRegionByCoord(0,24))
                .setSize(0.75f, 0.75f)
                .setOrigin(0.75f/2f, 0.75f/2f);
    }

    public int create(float x, float y) {
        int coin = world.create(coinArchetype);
        world.getMapper(TransformComponent.class).get(coin).setPosition(new Vector2f(x, y));
        world.getMapper(SpriteGraphicsComponent.class).get(coin).setSprite(sprite);
        return coin;
    }

}
