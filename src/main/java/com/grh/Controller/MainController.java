package com.grh.Controller;

import com.grh.entities.*;
import com.grh.other.MainService;
import com.grh.repository.AutorisationAbsRepository;
import com.grh.repository.PersonnelRepository;
import com.grh.view.AlertDialog;
import com.grh.view.FormDialog;
import com.grh.view.FormType;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Controller
public class MainController implements Initializable{

    public static MainController getMainController() {
        return mainController;
    }

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
        //  EMPLOYE
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
                Personnel p = param.getValue();
                List<AutorisationAbsence> absences = p.getAutorisationAbsences();
                if (absences !=null){
                    IntStream intStream = absences.stream().map(AutorisationAbsence::getDurer).mapToInt(Durer::getValue);
                    int count = NOMBRE_TOTAL_CONGE - intStream.sum();
                    return String.valueOf(count);
                }else return String.valueOf(NOMBRE_TOTAL_CONGE);
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
                                then(employeContextMenu).
                                otherwise((ContextMenu) null));
                return tableRow;
            }
        });
        absenceTableView.setRowFactory(new Callback<TableView<AutorisationAbsence>, TableRow<AutorisationAbsence>>() {
            @Override public TableRow<AutorisationAbsence> call(TableView<AutorisationAbsence> param) {
                final TableRow<AutorisationAbsence> tableRow = new TableRow<>();
                tableRow.contextMenuProperty().bind(
                        Bindings.when(
                                Bindings.isNotNull(param.itemsProperty())).
                                then(congeContextMenu).
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
        // AUTORISATION ABSENCE
        typeCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }
            @Override
            public String getValue() {
                switch (param.getValue().getTypeAutorisation()){
                    case CONGE:{
                     return "congé"   ;
                    }
                    case DEFAULT:{
                       return  "Autorisation d'absence";
                    }
                    case PERMISSION:{
                       return "permission d'absence";
                    }
                }
                return "";
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        natureCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                String nature = param.getValue().getNature();
                return nature!=null ? nature : "";
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        dateDebutCol.setCellValueFactory(param -> new ObservableValue<LocalDate>() {
            @Override
            public void addListener(ChangeListener<? super LocalDate> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super LocalDate> listener) {

            }

            @Override
            public LocalDate getValue() {
                return param.getValue().getDurer().getDebut();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        dateFinCol.setCellValueFactory(param -> new ObservableValue<LocalDate>() {
            @Override
            public void addListener(ChangeListener<? super LocalDate> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super LocalDate> listener) {

            }

            @Override
            public LocalDate getValue() {
                return param.getValue().getDurer().getFin();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        motifCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }
            @Override public String getValue() {
                return param.getValue().getMotif();
            }
            @Override public void addListener(InvalidationListener listener){

            }
            @Override public void removeListener(InvalidationListener listener) {

            }
        });
        durerCol.setCellValueFactory(param -> new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                Durer durer = param.getValue().getDurer();
                return String.valueOf(durer.getValue()) + " " + durer.getUnite().toString();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
    }

    private void deleteConge(ActionEvent event){
        Personnel p = tableView.getSelectionModel().getSelectedItem();
        AutorisationAbsence ab = absenceTableView.getSelectionModel().getSelectedItem();
        AlertDialog alertDialog = AlertDialog.getInstance(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cette autorisation d'absence ?");
        alertDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES) || buttonType.equals(ButtonType.OK)) {
                mainService.launch(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        p.getAutorisationAbsences().remove(ab);
                        return null;
                    }
                    @Override
                    protected void succeeded() {
                        absenceTableView.getItems().remove(ab);
                    }
                });
            }
        });
    }

    private void initData(){
        mainService.launch(new Task<List<Personnel>>() {
            @Override
            protected List<Personnel> call() throws Exception {
                return personnelRepository.findAll();
            }
            @Override
            protected void succeeded() {
                tableView.getItems().setAll(this.getValue());
            }
        });
    }

    private void initButton(){
        nouveauBtn.setOnAction(event -> FormDialog.show(FormType.EMPLOYE));
        retourBtn.setOnAction(event -> employeListPane.toFront());
        nouveauCongeBtn.setOnAction(event -> {
            Personnel p = tableView.getSelectionModel().getSelectedItem();
            List<AutorisationAbsence> absences = p.getAutorisationAbsences();
            if (absences!=null)
                absenceTableView.getItems().setAll(absences);
            FormDialog.show(FormType.CONGE);
        });
        actualiserBtn.setOnAction(event -> initData());
        actualiserBtnConge.setOnAction(this::refreshCongeTableView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = this;
        employeListPane.toFront();
        initializeContextMenu();
        initTableView();
        initButton();
        initFilter();
        initData();
    }
    // CONTEXTUAL MENU
    private void initializeContextMenu() {
        initEmployeContextMenu();
        initCongeContextMenu();
    }
    private void initEmployeContextMenu() {
        employeContextMenu = new ContextMenu();
        // SOLDE
        MenuItem soldeItem = new MenuItem("Solde de conge");
        soldeItem.setOnAction(this::showCongeSolde);
        // details
        MenuItem editMenuItem = new MenuItem("Editer");
        editMenuItem.setOnAction(this::editAction);
        //supprimer
        MenuItem deleteMenuItem = new MenuItem("Supprimer");
        deleteMenuItem.setOnAction(this::deleteAction);
        employeContextMenu.getItems().addAll(soldeItem,editMenuItem,deleteMenuItem);
    }
    private void initCongeContextMenu(){
        congeContextMenu = new ContextMenu();
        // details
        MenuItem editMenuItem = new MenuItem("Editer");
        editMenuItem.setOnAction(event -> {
            FormDialog.show(FormType.CONGE);
            initCongeEdit();
        });
        //supprimer
        MenuItem deleteMenuItem = new MenuItem("Supprimer");
        deleteMenuItem.setOnAction(this::deleteConge);
        congeContextMenu.getItems().addAll(editMenuItem,deleteMenuItem);
    }

    private void initCongeEdit() {
        AutorisationAbsence ab = absenceTableView.getSelectionModel().getSelectedItem();

        CongeFormController controller = CongeFormController.getCongeFormController();
        controller.getMotif().setText(ab.getMotif());
        controller.getLieuDeJouissance().setText(ab.getLieuDeJouissance());
        Durer durer = ab.getDurer();

        controller.getDebutConge().setValue(durer.getDebut());
        controller.getDureSpinner().getEditor().setText(String.valueOf(durer.getValue()));
        controller.getUniteCombo().setValue(durer.getUnite().toString());
        controller.getNatureCombo().setValue(ab.getNature());
        typeAutorisation typeAutorisation = ab.getTypeAutorisation();

        controller.getCongeRadio().setSelected(typeAutorisation == com.grh.entities.typeAutorisation.CONGE);
        controller.getPermissionRadio().setSelected(typeAutorisation == com.grh.entities.typeAutorisation.PERMISSION);
        controller.getAutorisationRadio().setSelected(typeAutorisation == com.grh.entities.typeAutorisation.DEFAULT);
    }

    private void editAction(ActionEvent event){
        Personnel p = getTableView().getSelectionModel().getSelectedItem();
        FormDialog.show(FormType.EMPLOYE);
        EmployeFormController efc = EmployeFormController.getEmployeFormController();
        efc.getNom().setText(p.getNom());
        efc.getPrenom().setText(p.getPrenom());
        efc.getDateNaissance().setValue(p.getDateNaissance());
        efc.getLieuNaissance().setText(p.getLieuDeNaissance());
        efc.getFonction().setText(p.getFonction());
        efc.getMatricule().setText(p.getMatricule());
        efc.getEchelonCombo().setValue(p.getEchelon());
        efc.getPositionCombo().setValue(p.getPosition());
        EmployeFormController.setIsSave(false);
    }
    private void showCongeSolde(ActionEvent event) {
        Personnel p = tableView.getSelectionModel().getSelectedItem();
        List<AutorisationAbsence> absences = p.getAutorisationAbsences();
        if (absences != null)
            absenceTableView.getItems().setAll(absences);
        congePane.toFront();
    }

    private void deleteAction(ActionEvent event) {
        Personnel selectedItem = tableView.getSelectionModel().getSelectedItem();
        AlertDialog alertDialog = AlertDialog.getInstance(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cette employé ?");
        alertDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES) || buttonType.equals(ButtonType.OK)) {
                mainService.launch(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        personnelRepository.delete(selectedItem);
                        return null;
                    }
                    @Override
                    protected void succeeded() {
                        tableView.getItems().remove(selectedItem);
                    }
                });
            }
        });
    }

    @FXML private TableColumn<Personnel,String> dateSortieCol;
    @FXML private TableColumn<Personnel,String> motifSortieCol;
    @FXML private Button retourBtn1;
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
    @FXML private TableColumn<Personnel,String> corpsCol;

    @FXML private TableView<AutorisationAbsence> absenceTableView;
    @FXML private TableColumn<AutorisationAbsence,String> natureCol;
    @FXML private TableColumn<AutorisationAbsence,String> typeCol;
    @FXML private TableColumn<AutorisationAbsence,LocalDate> dateDebutCol;
    @FXML private TableColumn<AutorisationAbsence,LocalDate> dateFinCol;
    @FXML private TableColumn<AutorisationAbsence,String> motifCol;
    @FXML private TableColumn<AutorisationAbsence,String> durerCol;
    @FXML private TextField textFieldFilter;
    @FXML private Button searchBtn;
    @FXML private DatePicker dateFilter;
    @FXML private AnchorPane employeListPane;
    @FXML private AnchorPane congePane;
    @FXML private Button nouveauCongeBtn;
    @FXML private Button actualiserBtn;
    @FXML private Button actualiserBtnConge;

    @Autowired private PersonnelRepository personnelRepository;
    @Autowired private AutorisationAbsRepository absRepository;
    @Autowired private MainService mainService;

    private final int NOMBRE_TOTAL_CONGE = 45;
    private static MainController mainController;
    private static ContextMenu employeContextMenu;
    private static ContextMenu congeContextMenu;
    private static Boolean isSave = true;

    private void refreshCongeTableView(ActionEvent event) {
        Personnel p = tableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            List<AutorisationAbsence> autorisationAbsences = p.getAutorisationAbsences();
            if (autorisationAbsences!=null || !autorisationAbsences.isEmpty())
                absenceTableView.getItems().setAll(autorisationAbsences);
        }
    }
}