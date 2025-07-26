package br.edu.ifba.saj.ads.controller;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import br.edu.ifba.saj.ads.QuarkusFxApp;
import io.quarkiverse.fx.views.FxView;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@FxView("custom-sample")
@Dependent
public class YetAnotherSampleController {

    private static final Random RANDOM = new SecureRandom();
    private AtomicInteger atomicInteger = new AtomicInteger();

    @Inject
    QuarkusFxApp app;

    @FXML
    Label rollResultLabel;

    @FXML
    private void handleClickMeAction() {
        // Roll a d20
        int value = RANDOM.nextInt(0, 21);
        this.rollResultLabel.setText(String.valueOf(value));
        if(atomicInteger.incrementAndGet() % 5 == 0){
            app.setFxView("Sample");
        }
    }
}
