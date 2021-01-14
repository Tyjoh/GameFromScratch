package com.bytesmyth.lifegame;

import com.artemis.World;
import com.bytesmyth.graphics.animation.Animation;
import com.bytesmyth.graphics.animation.AnimationTimeline;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureAtlas;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;

public class CharacterFactory {

    private World world;

    public CharacterFactory(World world) {
        this.world = world;
    }

    public int create(float x, float y) {
        Texture characterTexture = new Texture("/textures/character1.png");
        TextureAtlas textureAtlas = new TextureAtlas(characterTexture, 16, 32);

        int player = world.create();

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
                .add(new Transform().setPosition(new Vector2f(x, y)))
                .add(new Velocity())
                .add(new Direction())
                .add(new UserControl())
                .add(new CameraFollow())
                .add(new Collider().setHitBox(new Rectangle(0.85f, 0.5f)).setOffset(new Vector2f(0, -0.3f)))
                .add(new AnimatedTextureGraphics().setAnimation(animation).setCurrentAnimation("down"))
                .add(new InventoryComponent().setInventory(new Inventory(15)))
                .add(new Pickup())
                .add(characterGraphics);

        return player;
    }
}
