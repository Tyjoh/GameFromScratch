package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.sprite.AnimatedSprite;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;
import org.joml.Vector2i;

@All({UserControl.class, TransformComponent.class, VelocityComponent.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mEntityControl;
    private ComponentMapper<VelocityComponent> mVelocity;
    private ComponentMapper<DirectionComponent> mDirection;
    private ComponentMapper<SpriteGraphicsComponent> mSpriteGraphics;
    private ComponentMapper<TransformComponent> mTransform;

    private Vector2f controlDir = new Vector2f();
    private Vector2i dir = new Vector2i();

    @Wire
    private LifeGame game;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int entityId) {
        UserControl userControl = mEntityControl.get(entityId);
        VelocityComponent velocityComponent = mVelocity.get(entityId);

        Input input = game.getInput();

        dir.zero();

        String animationKey = "idle";

        if (input.getKey("W").isPressed()) {
            dir.y = 1;
            animationKey = "running";
        } else if(input.getKey("S").isPressed()) {
            dir.y = -1;
            animationKey = "running";
        }

        if (input.getKey("A").isPressed()) {
            dir.x = -1;
            animationKey = "running";
        } else if (input.getKey("D").isPressed()) {
            dir.x = 1;
            animationKey = "running";
        }

        controlDir.set(dir);
        if (controlDir.lengthSquared() > 0) {
            controlDir.normalize();
        }

        velocityComponent.getVelocity().set(controlDir).mul(userControl.getControlSpeed());

        Vector2f position = mTransform.get(entityId).getPosition();
        Vector2f mouse = game.getWorldMousePosition();

        boolean facingLeft = false;
        if (mouse.x < position.x) {
            facingLeft = true;
        }

        if (mSpriteGraphics.has(entityId)) {
            Sprite sprite = mSpriteGraphics.get(entityId).getSprite();
            if (sprite instanceof AnimatedSprite) {
                AnimatedSprite animatedSprite = (AnimatedSprite) sprite;
                animatedSprite.setCurrentAnimation(animationKey);
                animatedSprite.setFlipHorizontal(facingLeft);
            }
        }

        if (dir.lengthSquared() > 0 && mDirection.has(entityId)) {
            mDirection.get(entityId).setDir(dir);
        }
    }
}
