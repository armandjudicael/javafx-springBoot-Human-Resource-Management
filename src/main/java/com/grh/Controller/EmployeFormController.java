package com.grh.Controller;

import com.grh.entities.Regime;
import com.grh.entities.CategorieEmploye;
import com.grh.entities.Personnel;
import com.grh.entities.Sexe;
import com.grh.other.MainService;
import com.grh.repository.PersonnelRepository;
import com.grh.view.FormDialog;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Data
@Controller
public class EmployeFormController implements Initializable{
    private static EmployeFormController employeFormController;
    private static Boolean isSave = true;
    @FXML private RadioButton regimeCumuleRadio;
    @FXML private RadioButton regimeAnnuelleRadio;
    @FXML private ToggleGroup regimeRadio;
    @FXML private TextField fonction;
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
    @FXML private RadioButton fonctionnaireRadio;
    @FXML private ToggleGroup CategorieRadio;
    @FXML private RadioButton agentContactuelleRadio;

    public EmployeFormController() {
        employeFormController = this;
    }

    @Autowired
    private PersonnelRepository personnelRepository;
    @Autowired
    private MainService mainService;

    private final String[] ECHELON_A ={"A1","A2","A3"};
    private final String[] ECHELON_B ={"B1","B2"};
    private final String[] ECHELON_C ={"C1","C2"};
    private final String[] ECHELON_D ={"D1","D2","D3"};
    private final String[] FONCTIONNAIRE_POSITION = {
            "en disponibilité",
            "hors cadre",
            "en activité",
            "sous le drapeau",
            "en détachement"
    };

    private void initRadio(){
         getCategorieRadio().selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue.equals(fonctionnaireRadio)){
               positionCombo.getItems().setAll(FONCTIONNAIRE_POSITION);
             }else {
               positionCombo.getItems().removeAll("en disponibilité","hors cadre","en détachement");
             }
         });
    }

    private void initCombobox(){
        // CLASSE ET ECHELON
        List<String> list = List.of("A", "B", "C", "D");
        classeCombo.getItems().addAll(FXCollections.observableArrayList(list));
        classeCombo.getSelectionModel().selectedItemProperty().addListener(this::updateEchelonCombo);
        // POSITION
        positionCombo.getItems().addAll(FONCTIONNAIRE_POSITION);
    }

    private void saveEmploye(ActionEvent event){
        MainController controller = MainController.getMainController();
        Personnel p = createPersonnel();
        if (!isSave){
            TableView<Personnel> tableView = controller.getTableView();
            Personnel p1 = tableView.getSelectionModel().getSelectedItem();
            Long id = p1.getId();
            p.setId(id);
            tableView.getItems().remove(p1);
        }
       mainService.launch(new Task<Personnel>() {
            @Override
            protected Personnel call() throws Exception {
                return personnelRepository.save(p);
            }
            @Override
            protected void succeeded() {
                controller.getTableView().getItems().add(this.getValue());
                FormDialog.close();
                isSave = true;
                resetForm();
            }
        });
    }

    private Personnel createPersonnel() {
        Personnel p = new Personnel();
        p.setNom(nom.getText());
        p.setLieuDeNaissance(lieuNaissance.getText());
        p.setDateNaissance(dateNaissance.getValue());
        p.setPrenom(prenom.getText());
        p.setClasse(classeCombo.getValue());
        p.setEchelon(echelonCombo.getValue());
        p.setMatricule(matricule.getText());
        p.setPosition(positionCombo.getValue());
        p.setSexe(femininRadio.isSelected() ? Sexe.FEMININ : Sexe.MASCULIN);
        p.setFonction(fonction.getText());
        p.setDateEntre(dateEntre.getValue());
        p.setPosition(positionCombo.getValue());
        p.setCategorieEmploye(fonctionnaireRadio.isSelected() ? CategorieEmploye.FONCTIONNAIRE : CategorieEmploye.AGENT_CONTRACTUEL);
        p.setRegime(regimeAnnuelleRadio.isSelected() ? Regime.ANNUEL : Regime.ANNUELLE_CUMULE);
        return p;
    }

    private void resetForm(){
        nom.setText("");
        prenom.setText("");
        lieuNaissance.setText("");
        dateNaissance.setValue(null);
        matricule.setText("");
        positionCombo.setValue(null);
        dateEntre.setValue(null);
        fonction.setText("");
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

    @Override public void initialize(URL location, ResourceBundle resources) {
        employeFormController = this;
        enregisterBtn.setOnAction(this::saveEmploye);
        initCombobox();
        initRadio();
    }
    public static EmployeFormController getEmployeFormController() {
        return employeFormController;
    }
    public static Boolean getIsSave() {
        return isSave;
    }
    public static void setIsSave(Boolean isSave) {
        EmployeFormController.isSave = isSave;
    }
}
