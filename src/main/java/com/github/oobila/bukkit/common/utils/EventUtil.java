package com.github.oobila.bukkit.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventUtil {

    public static boolean isActionableRightClick(PlayerInteractEvent event, EquipmentSlot equipmentSlot) {
        //check equipment slot
        if (!equipmentSlot.equals(event.getHand())) {
            return false;
        }

        //check action
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            //instant allow
            return true;
        } else if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            //instant deny
            return false;
        }

        //we are right-clicking a block
        if (event.getClickedBlock() == null) {
            //apparently not
            return true;
        }

        if (event.getClickedBlock().getType().isInteractable() && !event.getPlayer().isSneaking()) {
            //player is interacting with a block
            return false;
        }

        //we got this far, let's allow
        return true;
    }

    public static boolean isActionableRightClick(PlayerInteractEvent event) {
        return isActionableRightClick(event, EquipmentSlot.HAND);
    }

}
