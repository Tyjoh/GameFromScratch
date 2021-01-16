package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.application.Input;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.ecs.components.Direction;
import com.bytesmyth.lifegame.ecs.components.Interaction;
import com.bytesmyth.lifegame.ecs.components.Transform;
import com.bytesmyth.lifegame.ecs.components.UserControl;
import com.bytesmyth.lifegame.tilemap.Tile;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.tilemap.TileMapLayer;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InteractSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mUserControl;
    private ComponentMapper<Transform> mTransform;
    private ComponentMapper<Interaction> mInteraction;
    private ComponentMapper<Direction> mDirection;

    @Wire
    private LifeGame game;

    public InteractSystem() {
        super(Aspect.all(UserControl.class, Transform.class));
    }

    @Override
    protected void process(int entityId) {
        Input input = game.getInput();
        if (!input.getKey("F").isPressed()) {
            return;
        }

        TileMap map = game.getMap();

        Transform transform = mTransform.get(entityId);

        Vector2f pos = transform.getPosition();
        Vector2f dir = mDirection.get(entityId).getDir();

        int px = (int) (pos.x);
        int py = (int) (pos.y);

        int fx = (int) (pos.x + Math.signum(dir.x));
        int fy = (int) (pos.y + Math.signum(dir.y));

        TileMapLayer objectLayer1 = map.getLayer("1");

        Tile currentTile = objectLayer1.getTile(px, py);
        Tile facingTile = objectLayer1.getTile(fx, fy);

        Optional<Interaction> currentTileInteraction = getInteraction(currentTile);
        Optional<Interaction> facingTileInteraction = getInteraction(facingTile);

        if (currentTileInteraction.isPresent()) {
            currentTileInteraction.get().interact(world.getEntity(entityId));
        } else if (facingTileInteraction.isPresent()) {
            facingTileInteraction.get().interact(world.getEntity(entityId));
        } else {
            System.out.printf("No tile to interact with. Current: (%d, %d), Facing: (%d, %d)\n", px, py, fx, fy);
        }

    }

    private Optional<Interaction> getInteraction(Tile tile) {
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
        private Interaction interaction;

        private InteractCandidate(Interaction interaction, float distance, float dot) {
            this.interaction = interaction;
            this.distance = distance;
            this.dot = dot;
        }
    }
}
