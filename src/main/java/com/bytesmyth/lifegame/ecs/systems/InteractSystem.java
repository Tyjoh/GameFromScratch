package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.Input;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.ecs.components.LookDirectionComponent;
import com.bytesmyth.lifegame.ecs.components.InteractiveComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.ecs.components.UserControl;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;
import org.joml.Vector2f;

import java.util.Optional;

public class InteractSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mUserControl;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<InteractiveComponent> mInteraction;
    private ComponentMapper<LookDirectionComponent> mDirection;

    @Wire
    private LifeGame game;

    public InteractSystem() {
        super(Aspect.all(UserControl.class, TransformComponent.class));
    }

    @Override
    protected void process(int entityId) {
        Input input = game.getInput();
        if (!input.getKey("F").isPressed()) {
            return;
        }

        TileMap map = game.getMap();

        TransformComponent transformComponent = mTransform.get(entityId);

        Vector2f pos = transformComponent.getPosition();
        Vector2f dir = mDirection.get(entityId).getLookDir();

        int px = (int) (pos.x + 0.5f);
        int py = (int) (pos.y);

        int fx = (int) (pos.x + 0.5f + Math.signum(dir.x));
        int fy = (int) (pos.y + Math.signum(dir.y));

        TileMapLayer objectLayer1 = map.getLayer("1");

        Tile currentTile = objectLayer1.getTile(px, py);
        Tile facingTile = objectLayer1.getTile(fx, fy);

        Optional<InteractiveComponent> currentTileInteraction = getInteraction(currentTile);
        Optional<InteractiveComponent> facingTileInteraction = getInteraction(facingTile);

        if (currentTileInteraction.isPresent()) {
            currentTileInteraction.get().interact(world.getEntity(entityId));
        } else if (facingTileInteraction.isPresent()) {
            facingTileInteraction.get().interact(world.getEntity(entityId));
        } else {
            System.out.printf("No tile to interact with. Current: (%d, %d), Facing: (%d, %d)\n", px, py, fx, fy);
        }

    }

    private Optional<InteractiveComponent> getInteraction(Tile tile) {
        if (tile == null) return Optional.empty();
        if (!tile.isDynamic()) return Optional.empty();

        if (mInteraction.has(tile.getEntityId())) {
            return Optional.ofNullable(mInteraction.get(tile.getEntityId()));
        } else {
            return Optional.empty();
        }
    }

    private static class InteractCandidate {
        private float distance;
        private float dot;
        private InteractiveComponent interactiveComponent;

        private InteractCandidate(InteractiveComponent interactiveComponent, float distance, float dot) {
            this.interactiveComponent = interactiveComponent;
            this.distance = distance;
            this.dot = dot;
        }
    }
}
