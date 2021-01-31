package com.bytesmyth.lifegame.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.bytesmyth.graphics.ui.GuiManager;
import com.bytesmyth.lifegame.control.Controls;
import com.bytesmyth.lifegame.domain.item.Inventory;
import com.bytesmyth.lifegame.ecs.components.*;
import com.bytesmyth.lifegame.ui.Guis;
import com.bytesmyth.lifegame.ui.PlayerInventoryUI;
import org.joml.Vector2f;

@All({UserControl.class})
public class UserControlSystem extends IteratingSystem {

    private ComponentMapper<UserControl> mUserControl;
    private ComponentMapper<VelocityComponent> mVelocity;
    private ComponentMapper<LookDirectionComponent> mDirection;
    private ComponentMapper<InventoryComponent> mInventory;

    @Wire
    private Controls controls;

    @Wire
    private GuiManager guiManager;

    @Override
    protected void begin() {
        super.begin();
    }

    @Override
    protected void process(int entityId) {
        if (!guiManager.groupActive(Guis.PLAYER_GROUP)) {
            handleMovement(entityId);
            handleDirection(entityId);
        }

        handleUi(entityId);
    }

    private void handleMovement(int entityId) {
        if (mVelocity.has(entityId)) {
            UserControl userControl = mUserControl.get(entityId);
            VelocityComponent velocityComponent = mVelocity.get(entityId);

            Vector2f dir = controls.getMovement().getValue();

            velocityComponent.getVelocity().set(dir).mul(userControl.getControlSpeed());
        }
    }

    private void handleDirection(int entityId) {
        if (mDirection.has(entityId)) {
            Vector2f lookDir = controls.getLookDirection().getValue();
            mDirection.get(entityId).setLookDir(lookDir);
        }
    }

    private void handleUi(int entityId) {
        boolean activated = controls.getInventory().isJustActivated();
        if (!activated) return;

        if (guiManager.groupActive(Guis.PLAYER_GROUP)) {
            guiManager.disableGroup(Guis.PLAYER_GROUP);
        } else {
            if (mInventory.has(entityId)) {
                PlayerInventoryUI gui = (PlayerInventoryUI) guiManager.getGui(Guis.PLAYER_INVENTORY);
                Inventory inventory = mInventory.get(entityId).getInventory();
                gui.setCurrentInventory(inventory);
                gui.enable();
                stopMovement(entityId);
            }
        }
    }

    private void stopMovement(int entityId) {
        VelocityComponent velocityComponent = mVelocity.get(entityId);
        velocityComponent.getVelocity().set(0, 0);
    }
}
