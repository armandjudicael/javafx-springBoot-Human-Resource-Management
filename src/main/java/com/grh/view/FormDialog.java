package com.grh.view;

import com.grh.GrhApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FormDialog {

    private static Stage stage;
    private static FormDialog formDialog;
    private Scene employeScene;
    private Scene congeScene;
    private FormType formType;

    public FormDialog(FormType formType){
        formDialog = this;
        this.formType = formType;
        if (stage == null){
            stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(formType.equals(FormType.EMPLOYE) ? " Nouveau employe " : "Nouvelle autorisation d'absence");
            try {
                stage.setScene(createScene(formType));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Scene createScene(FormType formType) throws IOException {
        FXMLLoader loader;
        if (formType.equals(FormType.EMPLOYE)){
            if (employeScene == null){
                loader = new  FXMLLoader(this.getClass().getResource("/fxml/employe-form.fxml"));
                loader.setControllerFactory(GrhApplication.getCtx()::getBean);
                employeScene = new Scene((AnchorPane)loader.load());
            }
            return employeScene;
        } else {
            if (congeScene == null){
                loader = new  FXMLLoader(this.getClass().getResource("/fxml/conge-form.fxml"));
                loader.setControllerFactory(GrhApplication.getCtx()::getBean);
                congeScene = new Scene((AnchorPane)loader.load());
            }
            return congeScene;
        }
    }

    public static void show(FormType type){
        if (formDialog == null){
            formDialog = new FormDialog(type);
        }
        if (!type.equals(formDialog.formType)){
            try {
                stage.setScene(formDialog.createScene(type));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage.show();
    }

    public static void close(){
        stage.close();
    }
}
