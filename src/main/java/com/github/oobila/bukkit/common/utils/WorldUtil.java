package com.github.oobila.bukkit.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorldUtil {

    public static Block getBlockAtDistance(LivingEntity entity, int range, boolean stopAtSolid) {
        BlockIterator iterator = new BlockIterator(entity, range);
        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (iterator.hasNext()) {
                if (stopAtSolid && !MaterialUtil.getTRANSPARENT_MATERIALS().contains(b.getType())) {
                    return b;
                }
                //continue
            } else {
                return b;
            }
        }
        return null;
    }

    public static List<Location> getLine(Location start, double range, int locationsBetween) {
        Vector dir = start.getDirection();
        double step = range/locationsBetween;
        List<Location> temp = new ArrayList<>();

        for (int i=0; i < locationsBetween; i++) {
            temp.add(dir.add(dir.clone().normalize().multiply(step)).toLocation(start.getWorld()));
        }

        return temp;
    }
}
