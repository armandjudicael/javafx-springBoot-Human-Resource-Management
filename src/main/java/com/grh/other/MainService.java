package com.grh.other;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MainService<Void> extends Service<Void>{
    @Override protected Task<Void> createTask() {
        return null;
    }
    public void launch(Task<Void> task) {
        if (!this.isFxApplicationThread()){
            Platform.runLater(() -> {
                this.executeTask(task);
            });
        } else this.executeTask(task);
    }
    @Override protected void failed() {
        Throwable exception = this.getException();
        exception.printStackTrace();
    }
    boolean isFxApplicationThread() {
        return Platform.isFxApplicationThread();
    }
}
