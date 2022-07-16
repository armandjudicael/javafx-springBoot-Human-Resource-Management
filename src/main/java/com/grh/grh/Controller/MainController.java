package com.grh.grh.Controller;

import com.grh.grh.entities.Personnel;
import com.grh.grh.view.AlertDialog;
import com.grh.grh.view.EmployeFormView;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Data
public class MainController implements Initializable{
    public static MainController getMainController() {
        return mainController;
    }
    private static MainController mainController;
    @FXML private TableColumn<Personnel,String> dateSortieCol;
    @FXML private TableColumn<Personnel,String> motifSortieCol;
    @FXML private Button nouveauConge;
    @FXML private Button retourBtn1;
    @FXML private TableView congeTableView;
    @FXML private Button retourBtn;
    @FXML private Button nouveauBtn;
    @FXML private TableColumn<Personnel,String> fonctionCol;
    @FXML private TableColumn<Personnel,String> naissanceCol;
    @FXML private TableView<Personnel> tableView;
    @FXML private TableColumn<Personnel,String> nomCol;
    @FXML private TableColumn<Personnel,String> dateEntrerCol;
    @FXML private TableColumn<Personnel,String> echelonCol;
    @FXML private TableColumn<Personnel,String> soldeCol;
    @FXML private TableColumn<Personnel,String> matriculeCol;
    @FXML private TableColumn<Personnel, String> positionCol;
    @FXML private TextField textFieldFilter;
    @FXML private Button searchBtn;
    @FXML private DatePicker dateFilter;
    @FXML private AnchorPane employeListPane;
    @FXML private AnchorPane congePane;
    private static ContextMenu contextMenu;
    private static Boolean isSave = true;

    private void  initFilter(){
        dateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                ObservableList<Personnel> items = tableView.getItems();
                List<Personnel> collect = items.stream().filter(personnel -> personnel.getDateEntre().equals(newValue)).collect(Collectors.toList());
                if (!collect.isEmpty()) tableView.getItems().setAll(collect);
            }
        });
    }

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
        dateEntrerCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                LocalDate dateEntre = param.getValue().getDateEntre();
                return  dateEntre == null ? LocalDate.now().toString() : dateEntre.toString() ;
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
                return String.valueOf(param.getValue().getSolde());
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
        positionCol.setCellValueFactory(param -> new ObservableValue<String>() {
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
        fonctionCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().getFonction();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
    }

    private void initButton(){
        nouveauBtn.setOnAction(event -> EmployeFormView.show());
        retourBtn.setOnAction(event -> employeListPane.toFront());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = this;
        employeListPane.toFront();
        initializeContextMenu();
        initTableView();
        initButton();
        initFilter();
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
        EmployeFormController.setIsSave(false);
        Personnel p = getTableView().getSelectionModel().getSelectedItem();
        EmployeFormController efc = EmployeFormController.getEmployeFormController();
        efc.getNom().setText(p.getNom());
        efc.getPrenom().setText(p.getPrenom());
        efc.getDateNaissance().setValue(p.getDateNaissance());
        efc.getLieuNaissance().setText(p.getLieuDeNaissance());
        efc.getFonction().setText(p.getFonction());
        efc.getEchelonCombo().setValue(p.getEchelon());
        efc.getPositionCombo().setValue(p.getPosition());
        EmployeFormView.show();
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
}