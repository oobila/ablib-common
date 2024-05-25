package com.github.oobila.bukkit.common.scheduling;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class JobScheduler extends Job {

    @Getter @Setter
    private int tickSpacing;
    private boolean running;
    private final Object schedulerLock = new Object();

    public JobScheduler(Plugin plugin, int tickSpacing) {
        this.plugin = plugin;
        this.tickSpacing = tickSpacing;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this::runNextJob, tickSpacing);
    }

    private void runNextJob() {
        synchronized (schedulerLock) {
            Job job = getNextJob();
            if (job == null) {
                running = false;
            } else {
                job.runJob();
                run();
            }
        }
    }

    public void addJob(Job job){
        addChildJob(job);
        synchronized (schedulerLock) {
            if (!running) {
                running = true;
                run();
            }
        }
    }
}
