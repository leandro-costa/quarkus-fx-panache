package br.edu.ifba.saj.ads.controller;

import br.edu.ifba.saj.ads.QuarkusFxApp;
import br.edu.ifba.saj.ads.controller.util.ActionTableCell;
import br.edu.ifba.saj.ads.model.MyEntity;
import io.quarkiverse.fx.views.FxView;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@FxView
@Dependent
public class SampleController {
    @Inject
    QuarkusFxApp app;

    @FXML
    TextField txNome;

    @FXML
    private TableView<MyEntity> table;

    @FXML
    private TableColumn<MyEntity, String> tableColumName;

    @FXML
    private TableColumn<PanacheEntity, Void> tableColumAction;

    @FXML
    @Transactional
    public void onButtonClick() {
        if (!StringUtil.isNullOrEmpty(txNome.getText())) {
            MyEntity entity = new MyEntity();
            entity.field = txNome.getText();
            entity.persist();
            txNome.setText("");
            Platform.runLater(() -> txNome.requestFocus());
            onListChange();
        }
    }

    @FXML
    public void initialize() {
        tableColumName.setCellValueFactory(new PropertyValueFactory<>("field"));
        tableColumAction.setCellFactory(param -> new ActionTableCell(this::deleteItem));
        onListChange();
    }

    private void onListChange() {
        table.setItems(FXCollections.observableList(MyEntity.listAll()));
    }

    @Transactional
    public void deleteItem(PanacheEntity entity) {
        entity.delete();
        onListChange();
    }

}
