package com.bytesmyth.lifegame.ecs.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.bytesmyth.lifegame.FarmInteractionHandler;

public class Interaction extends Component {

    private InteractionHandler interactionHandler;

    public Interaction(FarmInteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    public Interaction() {

    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public Interaction setInteractionHandler(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
        return this;
    }

    public void interact(Entity entity) {
        interactionHandler.interact(entity);
    }
}
