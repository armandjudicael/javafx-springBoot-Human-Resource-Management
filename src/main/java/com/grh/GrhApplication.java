package com.grh;

import com.gluonhq.ignite.spring.SpringContext;
import com.grh.entities.*;
import com.grh.other.MainService;
import com.grh.repository.PersonnelRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class GrhApplication extends Application implements CommandLineRunner {

    private final SpringContext context = new SpringContext(this);
    private static ConfigurableApplicationContext ctx;
    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public void init() throws Exception {
        context.init(() -> {
            return ctx = SpringApplication.run(GrhApplication.class);
        });
    }

    @Override
    public void stop() throws Exception {
        ctx.close();
    }

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(GrhApplication.class.getResource("/fxml/main-view.fxml"));
        fxmlLoader.setControllerFactory(ctx::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Grh app");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void run(String... args) throws Exception {
        Personnel p1 = new Personnel();
        p1.setNom("RANDRIANARISON Voahanginirina");
        p1.setPrenom("Noeline");
        p1.setRegime(Regime.ANNUELLE_CUMULE);
        p1.setDateNaissance(LocalDate.of(1990,10,10));
        p1.setLieuDeNaissance("MANARAN-TSANDRY");
        p1.setSexe(Sexe.FEMININ);
        p1.setFonction("SECRETAIRE");
        p1.setEchelon("C1");
        p1.setCorps("REALISATEUR");
        p1.setClasse("C");
        p1.setMatricule("15412315613");
        p1.setPosition("en activité");
        p1.setCategorieEmploye(CategorieEmploye.FONCTIONNAIRE);
        p1.setDateEntre(LocalDate.of(2016,2,2));

        Personnel p2 = new Personnel();
        p2.setNom("RAKOTOSON");
        p2.setPrenom("Jean francois");
        p2.setRegime(Regime.ANNUELLE_CUMULE);
        p2.setDateNaissance(LocalDate.of(1990,10,10));
        p2.setLieuDeNaissance("Fenerive-Est");
        p2.setSexe(Sexe.MASCULIN);
        p2.setFonction("delegue communale");
        p2.setEchelon("D2");
        p2.setCorps("Operateur");
        p2.setClasse("D");
        p2.setMatricule("156465");
        p2.setPosition("en activité");
        p2.setCategorieEmploye(CategorieEmploye.FONCTIONNAIRE);
        p2.setDateEntre(LocalDate.of(2014,2,2));

        Durer durer1 = new Durer();
        durer1.setValue(2);
        durer1.setDebut(LocalDate.now());
        durer1.setUnite(Unite.JOURS);
        durer1.setFin(LocalDate.now().plusDays(2));

        Durer durer2= new Durer();
        durer2.setValue(1);
        durer2.setDebut(LocalDate.now());
        durer2.setUnite(Unite.MOIS);
        durer2.setFin(LocalDate.now().plusDays(1));

        AutorisationAbsence ab1 = new AutorisationAbsence();
        ab1.setMotif("Examen de mathématique financiere");
        ab1.setNature("Autorisation absence");
        ab1.setTypeAutorisation(typeAutorisation.DEFAULT);
        ab1.setLieuDeJouissance("Toamasina I");
        ab1.setDurer(durer1);

        AutorisationAbsence ab2 = new AutorisationAbsence();
        ab2.setMotif("Accouchement de sa femme");
        ab2.setNature("Congé de paternité");
        ab2.setTypeAutorisation(typeAutorisation.CONGE);
        ab2.setLieuDeJouissance("Toamasina I");
        ab2.setDurer(durer2);

        ObservableList<AutorisationAbsence> abs1 = FXCollections.observableArrayList(ab1);
        p1.setAutorisationAbsences(abs1);

        ObservableList<AutorisationAbsence> abs2 = FXCollections.observableArrayList(ab2);
        p2.setAutorisationAbsences(abs2);

        personnelRepository.saveAll(List.of(p1,p2));
    }
}