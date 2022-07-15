package com.grh.grh.Controller;

import com.grh.grh.entities.Personnel;
import com.grh.grh.entities.Sexe;
import com.grh.grh.view.AlertDialog;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private TableColumn<Personnel,String> naissanceCol;
    @FXML private TableView<Personnel> tableView;
    @FXML private TableColumn<Personnel,String> nomCol;
    @FXML private TableColumn<Personnel,String> classeCol;
    @FXML private TableColumn<Personnel,String> echelonCol;
    @FXML private TableColumn<Personnel,String> soldeCol;
    @FXML private TableColumn<Personnel,String> matriculeCol;
    @FXML private TableColumn<Personnel, String> position;
    @FXML private TextField nom;
    @FXML private TextField prenom;
    @FXML private TextField matricule;
    @FXML private DatePicker dateNaissance;
    @FXML private TextField lieuNaissance;
    @FXML private DatePicker dateEntre;
    @FXML private Button enregisterBtn;
    @FXML private RadioButton femininRadio;
    @FXML private RadioButton masculinRadio;
    @FXML private ToggleGroup sexeRadio;
    @FXML private ComboBox<String> classeCombo;
    @FXML private ComboBox<String> echelonCombo;
    @FXML private ComboBox<String> positionCombo;
    @FXML private AnchorPane employeListPane;
    @FXML private AnchorPane congePane;

    private final String[] ECHELON_A ={"A1","A2","A3"};
    private final String[] ECHELON_B ={"B1","B2"};
    private final String[] ECHELON_C ={"C1","C2"};
    private final String[] ECHELON_D ={"D1","D2","D3"};
    private static ContextMenu contextMenu;
    private static Boolean isSave = true;

    private void initTableView(){
        nomCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personnel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Personnel, String> param) {
                return new ObservableValue<String>() {
                    @Override
                    public void addListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {

                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getNom() + " " + param.getValue().getPrenom();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {

                    }
                };
            }
        });
        classeCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return  param.getValue().getClasse();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        soldeCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().getSolde();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        matriculeCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().getMatricule();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        echelonCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().getEchelon();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        tableView.setRowFactory(new Callback<TableView<Personnel>, TableRow<Personnel>>() {
            @Override public TableRow<Personnel> call(TableView<Personnel> param) {
                final TableRow<Personnel> tableRow = new TableRow<>();
                tableRow.contextMenuProperty().bind(
                        Bindings.when(
                                Bindings.isNotNull(param.itemsProperty())).
                                then(contextMenu).
                                otherwise((ContextMenu) null));
                return tableRow;
            }
        });
        naissanceCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                Personnel p = param.getValue();
                return p.getLieuDeNaissance()+ " à " + p.getDateNaissance();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        position.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().getPosition();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
    }

    private void initCombobox(){
        // CLASSE ET ECHELON
        List<String> list = List.of("A", "B", "C", "D");
        classeCombo.getItems().addAll(FXCollections.observableArrayList(list));
        classeCombo.getSelectionModel().selectedItemProperty().addListener(this::updateEchelonCombo);
        // POSITION
        List<String> positionList = List.of("en activité", "en détachement", "hors cadre", "sous le drapeau","en disponibilité");
        positionCombo.getItems().addAll(FXCollections.observableArrayList(positionList));
    }

    private void initButton(){
        enregisterBtn.setOnAction(this::saveEmploye);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeContextMenu();
        initTableView();
        initCombobox();
        initButton();
    }

    // CONTEXTUAL MENU
    private void initializeContextMenu() {
        contextMenu = new ContextMenu();
        // SOLDE
        MenuItem soldeItem = new MenuItem("Solde");
        soldeItem.setOnAction(event -> {
             congePane.toFront();
        });
        // details
        MenuItem detailsMenuItem = new MenuItem("Editer");
        detailsMenuItem.setOnAction(this::editEmployeAction);
        //supprimer
        MenuItem deleteMenuItem = new MenuItem("Supprimer");
        deleteMenuItem.setOnAction(this::deleteEmploye);
        contextMenu.getItems().addAll(soldeItem,detailsMenuItem,deleteMenuItem);
    }

    private void editEmployeAction(ActionEvent event) {
        isSave = false;
        Personnel selectedItem = tableView.getSelectionModel().getSelectedItem();
        nom.setText(selectedItem.getNom());
        prenom.setText(selectedItem.getPrenom());
        classeCombo.getSelectionModel().select(selectedItem.getClasse());
        dateEntre.setValue(selectedItem.getDateEntre());
        dateNaissance.setValue(selectedItem.getDateNaissance());
        lieuNaissance.setText(selectedItem.getLieuDeNaissance());
        echelonCombo.getSelectionModel().select(selectedItem.getEchelon());
    }

    private void saveEmploye(ActionEvent event){
        Personnel p = new Personnel();
        p.setNom(nom.getText());
        p.setPrenom(prenom.getText());
        p.setClasse(classeCombo.getValue());
        p.setEchelon(echelonCombo.getValue());
        p.setMatricule(matricule.getText());
        p.setPosition(positionCombo.getValue());
        p.setSexe(femininRadio.isSelected() ? Sexe.FEMININ : Sexe.MASCULIN);
        if (!isSave){
            Personnel selectedItem = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedItem);
        }
        tableView.getItems().add(p);
        isSave = true;
    }

    private void deleteEmploye(ActionEvent event) {
        AlertDialog alertDialog = AlertDialog.getInstance(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cette employé ?");
        alertDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES) || buttonType.equals(ButtonType.OK)) {
                Personnel selectedItem = tableView.getSelectionModel().getSelectedItem();
                tableView.getItems().remove(selectedItem);
            }
        });
    }

    private void updateEchelonCombo(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        switch (newValue) {
            case "A": {
                echelonCombo.getItems().setAll(List.of(ECHELON_A));
            }
            break;
            case "B": {
                echelonCombo.getItems().setAll(List.of(ECHELON_B));
            }
            break;
            case "C": {
                echelonCombo.getItems().setAll(List.of(ECHELON_C));
            }
            break;
            case "D": {
                echelonCombo.getItems().setAll(List.of(ECHELON_D));
            }
            break;
        }
    }
}