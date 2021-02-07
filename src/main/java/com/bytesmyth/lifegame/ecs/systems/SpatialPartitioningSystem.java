package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.lifegame.LifeGame;
import com.bytesmyth.lifegame.domain.partition.PartitionRef;
import com.bytesmyth.lifegame.domain.partition.SpatialPartition;
import com.bytesmyth.lifegame.ecs.components.TransformComponent;
import org.joml.Vector2f;

@All(TransformComponent.class)
public class SpatialPartitioningSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;

    @Wire
    private LifeGame game;

    private SpatialPartition partitioner;

    @Override
    protected void initialize() {
        super.initialize();
        this.partitioner = game.getSpatialPartitioner();
    }

    @Override
    protected void process(int entityId) {
        TransformComponent transformComponent = mTransform.get(entityId);
        Vector2f position = transformComponent.getPosition();

        PartitionRef partition = partitioner.partition(entityId, position.x, position.y);

        PartitionRef prevPartition = transformComponent.getPartition();
        if (prevPartition != null && !prevPartition.equals(partition)) {
            prevPartition.unPartition(entityId);
        }

        transformComponent.setPartition(partition);
    }
}
