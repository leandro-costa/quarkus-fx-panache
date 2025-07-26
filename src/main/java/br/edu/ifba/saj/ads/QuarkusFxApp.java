package br.edu.ifba.saj.ads;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import io.quarkiverse.fx.FxPostStartupEvent;
import io.quarkiverse.fx.views.FxViewRepository;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

@ApplicationScoped
public class QuarkusFxApp {

    @Inject
    FxViewRepository fxViewRepository;

    public void start(@Observes final FxPostStartupEvent event) {

        Stage stage = event.getPrimaryStage();

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        setFxView("Sample");
        //setFxView("custom-sample");
    }

    public void setFxView(String fxView) {
        Stage stage = fxViewRepository.getPrimaryStage();        
        if(stage.getScene() != null){
            stage.getScene().setRoot(new Region());
        }
        Scene scene = new Scene(fxViewRepository.getViewData(fxView).getRootNode());
        stage.setScene(scene);
        stage.show();
    }

}