package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.GameContext;
import com.bytesmyth.application.Input;
import com.bytesmyth.graphics.sprite.AnimatedSprite;
import com.bytesmyth.graphics.sprite.Sprite;
import com.bytesmyth.lifegame.ecs.components.*;
import org.joml.Vector2f;
import org.joml.Vector2i;

@All({UserControl.class, TransformComponent.class, VelocityComponent.class})
public class EntityControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mEntityControl;
    private ComponentMapper<VelocityComponent> mVelocity;
    private ComponentMapper<DirectionComponent> mDirection;
    private ComponentMapper<SpriteGraphicsComponent> mSpriteGraphics;

    private Vector2f controlDir = new Vector2f();
    private Vector2i dir = new Vector2i();

    @Wire
    private GameContext gameContext;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int i) {
        UserControl userControl = mEntityControl.get(i);
        VelocityComponent velocityComponent = mVelocity.get(i);

        Input input = gameContext.getInput();

        dir.zero();

        String animationKey = "down";

        if (input.getKey("W").isPressed()) {
            dir.y = 1;
            animationKey = "up";
        } else if(input.getKey("S").isPressed()) {
            dir.y = -1;
            animationKey = "down";
        }

        if (input.getKey("A").isPressed()) {
            dir.x = -1;
            animationKey = "left";
        } else if (input.getKey("D").isPressed()) {
            dir.x = 1;
            animationKey = "right";
        }

        controlDir.set(dir);
        if (controlDir.lengthSquared() > 0) {
            controlDir.normalize();
        }

        velocityComponent.getVelocity().set(controlDir).mul(userControl.getControlSpeed());

        if (dir.lengthSquared() > 0 && mDirection.has(i)) {
            mDirection.get(i).setDir(dir);
            if (mSpriteGraphics.has(i)) {
                Sprite sprite = mSpriteGraphics.get(i).getSprite();
                if (sprite instanceof AnimatedSprite) {
                    ((AnimatedSprite)sprite).setCurrentAnimation(animationKey);
                }
            }
        }
    }
}
