package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.artemis.Entity;

public class InteractiveComponent extends Component {

    private InteractionHandler interactionHandler;

    public InteractiveComponent(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    public InteractiveComponent() {

    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public InteractiveComponent setInteractionHandler(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
        return this;
    }

    public void interact(Entity entity) {
        interactionHandler.interact(entity);
    }
}
