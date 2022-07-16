package com.grh.grh.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeFormView {
    private static Stage stage;
    private static   EmployeFormView employeFormView;
    public EmployeFormView(){
        if (stage == null){
            stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(" Nouveau employe ");
            try {
                FXMLLoader loader = new  FXMLLoader(this.getClass().getResource("/fxml/employe-form.fxml"));
                Scene scene = new Scene((AnchorPane)loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void show(){
        if (employeFormView == null){
            employeFormView = new EmployeFormView();
        }
        stage.showAndWait();
    }
    public static void close(){
        stage.close();
    }
}
