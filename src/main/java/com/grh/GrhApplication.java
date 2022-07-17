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
        // Close this application context,
        // destroys all beans in its bean factory
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

        Durer durer = new Durer();
        durer.setValue(2);
        durer.setDebut(LocalDate.now());
        durer.setUnite(Unite.JOURS);
        durer.setFin(LocalDate.now().plusDays(2));

        AutorisationAbsence ab = new AutorisationAbsence();
        ab.setMotif("Examen de mathématique financiere");
        ab.setNature("Autorisation absence");
        ab.setTypeAutorisation(typeAutorisation.DEFAULT);
        ab.setLieuDeJouissance("Toamasina I");
        ab.setDurer(durer);

        ObservableList<AutorisationAbsence> autorisationAbsences = FXCollections.observableArrayList(ab);
        p1.setAutorisationAbsences(autorisationAbsences);

        personnelRepository.save(p1);
    }
}