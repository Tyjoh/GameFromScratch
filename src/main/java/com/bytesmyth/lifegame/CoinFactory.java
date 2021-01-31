package com.bytesmyth.lifegame;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.bytesmyth.lifegame.ecs.components.ItemComponent;
import com.bytesmyth.lifegame.ecs.components.SpriteGraphicsComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

public class CoinFactory {

    private final World world;
    private final Archetype coinArchetype;
    private final LifeGame game;

    public CoinFactory(LifeGame game) {
        this.game = game;
        this.world = game.getWorld();
        coinArchetype = new ArchetypeBuilder()
                .add(TransformComponent.class)
                .add(ItemComponent.class)
                .add(SpriteGraphicsComponent.class)
                .build(world);
    }

    public int create(float x, float y) {
        int coin = world.create(coinArchetype);
        world.getMapper(TransformComponent.class).get(coin).setPosition(new Vector2f(x, y));
        world.getMapper(SpriteGraphicsComponent.class).get(coin).setSprite(game.getSpriteRegistry().createSprite("coin"));
        return coin;
    }

}
