package com.grh.Controller;

import com.grh.entities.*;
import com.grh.other.MainService;
import com.grh.repository.AutorisationAbsRepository;
import com.grh.view.FormDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Data;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Data
@Controller
public class CongeFormController implements Initializable {
    @FXML private ComboBox<String> natureCombo;
    @FXML private Spinner<Integer> dureSpinner;
    @FXML private TextField lieuDeJouissance;
    @FXML private Button enregistrerBtn;
    @FXML private Button annulerBtn;
    @FXML private RadioButton congeRadio;
    @FXML private RadioButton permissionRadio;
    @FXML private RadioButton autorisationRadio;
    @FXML private TextArea motif;
    @FXML private ToggleGroup type;
    @FXML private ComboBox<String> uniteCombo;
    @FXML private DatePicker debutConge;
    @FXML private static CongeFormController congeFormController;

    @Autowired
    private MainService mainService;

    @Autowired
    private AutorisationAbsRepository autorisationAbsRepository;

    public static CongeFormController getCongeFormController() {
        return congeFormController;
    }

    private final String[] AUTORISATION_ABSENCE ={
           "absence ordinaires",
           "absence spéciale"
    };

    private void initRadio(){
        type.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> items = natureCombo.getItems();
            if (newValue.equals(autorisationRadio))
                items.setAll(AUTORISATION_ABSENCE);
            else if (newValue.equals(congeRadio)) items.setAll(TYPE_CONGE);
            else items.clear();
        });
    }

    private final String[] TYPE_CONGE = {
            "congé de maternité"
            ,"congé de paternité"
            ,"conge pour education"
            ,"congé de longue dureé"
            ,"congé pour maladie"
            ,"congé de cure thermale"
            ,"congé pour formation"
    };

    private void initCombo(){
        natureCombo.getItems().addAll(TYPE_CONGE);
        uniteCombo.getItems().addAll("jours","mois","anneé");
    }

    private void initButton(){
        enregistrerBtn.setOnAction(this::enregistrerAutorisation);
       // enregistrerBtn.disableProperty().bind(debutConge.valueProperty().isEqualTo(LocalDate.now()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        congeFormController = this;
        initRadio();
        initCombo();
        initSprinner();
        initButton();
    }

    private void initSprinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 0);
        dureSpinner.setValueFactory(integerSpinnerValueFactory);
        dureSpinner.setEditable(true);
    }

    private void enregistrerAutorisation(ActionEvent event) {
        MainController controller = MainController.getMainController();
        AutorisationAbsence ab = createAutorisationAbsence();
        Durer durer = createDurer();
        ab.setDurer(durer);
        // ajouter a la liste des autorisations d'absence de l'employé selectionné
        TableView<Personnel> employeTableView = controller.getTableView();
        Personnel p = employeTableView.getSelectionModel().getSelectedItem();
        List<AutorisationAbsence> absences = p.getAutorisationAbsences();
        if (absences !=null)
            absences.add(ab);
        else p.setAutorisationAbsences(FXCollections.observableArrayList(ab));
        mainService.launch(new Task<AutorisationAbsence>() {
            @Override
            protected AutorisationAbsence call() throws Exception {
                return autorisationAbsRepository.save(ab);
            }
            @Override
            protected void succeeded() {
                TableView<AutorisationAbsence> tableView = controller.getAbsenceTableView();
                tableView.getItems().add(this.getValue());
                resetForm();
                FormDialog.close();
            }
        });
    }

    private void resetForm() {
        natureCombo.setValue(null);
        motif.setText("");
    }

    private AutorisationAbsence createAutorisationAbsence() {
        AutorisationAbsence ab = new AutorisationAbsence();
        ab.setNature(natureCombo.getValue());
        ab.setTypeAutorisation(permissionRadio.isSelected() ? typeAutorisation.PERMISSION : (autorisationRadio.isSelected() ? typeAutorisation.CONGE : typeAutorisation.DEFAULT));
        ab.setMotif(motif.getText());
        ab.setLieuDeJouissance(lieuDeJouissance.getText());
        return ab;
    }

    private Durer createDurer() {
        Durer durer = new Durer();
        Integer durerValue = dureSpinner.getValue();
        String uniteAutorisation = uniteCombo.getValue();
        durer.setValue(durerValue);
        Unite unite = uniteAutorisation.equals("jours") ? Unite.JOURS : ( uniteAutorisation.equals("mois") ? Unite.MOIS : Unite.ANNEE);
        durer.setUnite(unite);
        LocalDate debut = debutConge.getValue();
        durer.setDebut(debut);
        durer.setFin( unite==Unite.MOIS ?
                debut.plusMonths(durerValue) :
                ( unite == Unite.ANNEE ? debut.plusYears(durerValue) : debut.plusDays(durerValue)));
        return durer;
    }
}
