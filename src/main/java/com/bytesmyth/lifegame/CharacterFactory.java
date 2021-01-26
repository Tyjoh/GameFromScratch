package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.animation.AnimationMap;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.Frame;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.sprite.AnimatedSprite;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;

public class CharacterFactory {

    private World world;
    private TextureAtlas atlas;

    public CharacterFactory(World world, TextureAtlas atlas) {
        this.world = world;
        this.atlas = atlas;
    }

    public int create(float x, float y) {
        int player = world.create();

        Animation idle = new Animation("idle", toFrames(0));
        Animation running = new Animation("running", toFrames(1,2,3));
        AnimationMap animationMap = new AnimationMap(idle, running);

        Sprite sprite = new AnimatedSprite(atlas.getTexture(), animationMap, "idle")
                .setSize(1, 1)
                .setOrigin(0.5f, 0.5f);

        SpriteGraphicsComponent characterGraphics = new SpriteGraphicsComponent(sprite);

        world.edit(player)
                .add(new TransformComponent().setPosition(new Vector2f(x, y)))
                .add(new VelocityComponent())
                .add(new DirectionComponent())
                .add(new UserControl())
                .add(new CameraFollowComponent())
                .add(new ColliderComponent().setHitBox(new Rectangle(0.85f, 0.5f)).setOffset(new Vector2f(0, -0.3f)))
                .add(new InventoryComponent().setInventory(new Inventory(15)))
                .add(new ItemPickupComponent())
                .add(characterGraphics);

        return player;
    }

    private Frame[] toFrames(int... tileIds) {
        Frame[] regions = new Frame[tileIds.length];
        for (int i = 0; i < tileIds.length; i++) {
            regions[i] = new Frame(atlas.getRegionById(tileIds[i]), 3);
        }
        return regions;
    }
}
