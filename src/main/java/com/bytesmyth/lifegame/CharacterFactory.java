package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.animation.AnimationMap;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.Frame;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.sprite.SpriteAnimation;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.graphics.texture.TextureRegion;
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

        Animation idleLeft = new Animation("idle_left", toFrames(true, 0));
        Animation idleRight = new Animation("idle_right", toFrames(false, 0));
        Animation runningLeft = new Animation("running_left", toFrames(true, 1,2,3));
        Animation runningRight = new Animation("running_right", toFrames(false,1,2,3));
        AnimationMap animationMap = new AnimationMap(idleLeft, idleRight, runningLeft, runningRight);

        SpriteAnimation spriteAnimation = new SpriteAnimation(animationMap, "idle_right");

        SpriteGraphicsComponent characterGraphics = new SpriteGraphicsComponent()
                .setSpriteProvider(spriteAnimation);

        AnimationComponent animationComponent = new AnimationComponent()
                .setSpriteAnimation(spriteAnimation);

        world.edit(player)
                .add(new TransformComponent().setPosition(new Vector2f(x, y)))
                .add(new VelocityComponent())
                .add(new LookDirectionComponent())
                .add(new UserControl())
                .add(new CameraFollowComponent())
                .add(new ColliderComponent().setHitBox(new Rectangle(0.85f, 0.5f)).setOffset(new Vector2f(0, -0.3f)))
                .add(new InventoryComponent().setInventory(new Inventory(15)))
                .add(new ItemPickupComponent())
                .add(animationComponent)
                .add(characterGraphics);

        return player;
    }

    private Frame[] toFrames(boolean flipX, int... tileIds) {
        Frame[] regions = new Frame[tileIds.length];
        for (int i = 0; i < tileIds.length; i++) {
            TextureRegion region = atlas.getRegionById(tileIds[i]);

            if (flipX) {
                region = region.getFlippedX();
            }

            Sprite sprite = new Sprite(atlas.getTexture(), region)
                    .setSize(1,  1)
                    .setOrigin(0.5f, 0.5f);

            regions[i] = new Frame(sprite, 3);
        }
        return regions;
    }
}
