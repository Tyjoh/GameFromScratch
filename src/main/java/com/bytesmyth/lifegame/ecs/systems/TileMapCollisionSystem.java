package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.lifegame.tilemap.TileMap;
import com.bytesmyth.lifegame.ecs.components.ColliderComponent;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import com.bytesmyth.lifegame.ecs.components.VelocityComponent;
import org.joml.Vector2f;

public class TileMapCollisionSystem extends IteratingSystem {
    private static final Rectangle TILE = new Rectangle(1, 1);

    private ComponentMapper<ColliderComponent> mCollider;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    @Wire
    private TileMap map;

    private Vector2f delta = new Vector2f();
    private Vector2f position = new Vector2f();


    public TileMapCollisionSystem() {
        super(Aspect.all(ColliderComponent.class, TransformComponent.class));
    }

    @Override
    protected void process(int entityId) {
        ColliderComponent collider = mCollider.get(entityId);
        TransformComponent transformComponent = mTransform.get(entityId);
        position.set(transformComponent.getPosition()).add(collider.getOffset());

        int minX = (int) (position.x - collider.getHitBox().getHalfWidth());
        int maxX = (int) (position.x + collider.getHitBox().getHalfWidth()) + 1;

        int minY = (int) (position.y - collider.getHitBox().getHalfHeight());
        int maxY = (int) (position.y + collider.getHitBox().getHalfHeight()) + 1;


        for (int i = 0; i < 2; i++) {
            Overlap largestOverlap = null;

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    if (map.getTile("collision", x, y) == null) {
                        continue;
                    }

                    Vector2f tilePos = new Vector2f(x, y);

                    if (!overlaps(collider.getHitBox(), position, tilePos)) {
                        continue;
                    }

                    Overlap overlap = getOverlap(collider.getHitBox(), position, tilePos);
                    if (largestOverlap == null || overlap.overlapArea > largestOverlap.overlapArea) {
                        largestOverlap = overlap;
                    }
                }
            }

            if (largestOverlap != null) {
                if (Math.abs(largestOverlap.xOverlap) < Math.abs(largestOverlap.yOverlap)) {
                    position.add(new Vector2f(-largestOverlap.xOverlap, 0));
                    if (mVelocity.has(entityId)) {
                        Vector2f vel = mVelocity.get(entityId).getVelocity();
                        vel.set(0, vel.y);
                    }
                } else {
                    position.add(new Vector2f(0, -largestOverlap.yOverlap));
                    if (mVelocity.has(entityId)) {
                        Vector2f vel = mVelocity.get(entityId).getVelocity();
                        vel.set(vel.x, 0);
                    }
                }
            }
        }

        transformComponent.setPosition(position.sub(collider.getOffset()));
    }

    private Overlap getOverlap(Rectangle hitBox, Vector2f position, Vector2f tilePos) {
        delta.set(tilePos).sub(position);
        float xOverlap = (TILE.getHalfWidth() + hitBox.getHalfWidth()) - Math.abs(delta.x);
        float yOverlap = (TILE.getHalfHeight() + hitBox.getHalfHeight()) - Math.abs(delta.y);
        Overlap overlap = new Overlap(tilePos, xOverlap * sign(delta.x), yOverlap * sign(delta.y));
        if (overlap.overlapArea == 0) {
            System.out.println(overlap);
        }
        return overlap;
    }

    private float sign(float f) {
        return f < 0.0 ? -1 : 1;
    }

    private boolean overlaps(Rectangle colliderRect, Vector2f colliderPos, Vector2f tilePos) {
        delta.set(tilePos).sub(colliderPos);

        if (Math.abs(delta.x) >= TILE.getHalfWidth() + colliderRect.getHalfWidth()) {
            return false;
        }

        if (Math.abs(delta.y) >= TILE.getHalfHeight() + colliderRect.getHalfHeight()) {
            return false;
        }

        return true;
    }

    private static class Overlap {
        private Vector2f tilePos;
        private float xOverlap;
        private float yOverlap;
        private float overlapArea;

        public Overlap(Vector2f tilePos, float xOverlap, float yOverlap) {
            this.tilePos = tilePos;
            this.xOverlap = xOverlap;
            this.yOverlap = yOverlap;
            this.overlapArea = Math.abs(xOverlap * yOverlap);
        }

        @Override
        public String toString() {
            return "Overlap{" +
                    "tilePos=" + tilePos +
                    ", xOverlap=" + xOverlap +
                    ", yOverlap=" + yOverlap +
                    ", overlapArea=" + overlapArea +
                    '}';
        }
    }

}
