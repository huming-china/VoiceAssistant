package com.cloudring.magicsound.model;

import com.fjtk.musiclib.beans.Data;


/**
 * Created by zengpeijin on 2016/12/8.
 */

public class TaskQueue {
    public enum TaskQueueType{
        START_ACTIVITY,START_APP,START_MUSIC;
    }
    private TaskQueueType taskQueueType;
    public Data musicData;
    private String start_name;

    public TaskQueueType getTaskQueueType() {
        return taskQueueType;
    }

    public TaskQueue(TaskQueue.TaskQueueType taskQueueType, String start_name) {
        this.taskQueueType = taskQueueType;
        this.start_name = start_name;
    }
    public TaskQueue(Data musicData) {
        this.taskQueueType = TaskQueueType.START_MUSIC;
        this.musicData = musicData;
    }
}
