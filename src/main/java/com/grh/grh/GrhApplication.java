package com.grh.grh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GrhApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GrhApplication.class.getResource("/fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Grh app");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}