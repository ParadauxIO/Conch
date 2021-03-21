package io.paradaux.conch.velocity.managers;

import com.velocitypowered.api.scheduler.Scheduler;

public class TaskManager {

    private final Object plugin;
    private final Scheduler scheduler;

    public TaskManager(Object plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

    public Scheduler.TaskBuilder newTask(Runnable task) {
        return scheduler.buildTask(plugin, task);
    }

}
