package com.github.oobila.bukkit.common.scheduling.jobs;

import com.github.oobila.bukkit.common.scheduling.Job;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class MaterialPlacer extends Job {

    private final World world;


    private final List<Material> materialList = new ArrayList<>();

    @Getter
    private final List<Location> locations = new ArrayList<>();

    public MaterialPlacer(World world) {
        this.world = world;
    }

    public Material get(Location location) {
        return materialList.get(locations.indexOf(location));
    }

    public void addBlock(Material material, Location location){
        materialList.add(material);
        locations.add(location);
    }

    @Override
    public void run() {
        for(int i = 0; i < locations.size(); i++){
            world.getBlockAt(locations.get(i)).setType(materialList.get(i));
        }
    }

    @Override
    public String toString() {
        return "MaterialPlacer{}";
    }
}
