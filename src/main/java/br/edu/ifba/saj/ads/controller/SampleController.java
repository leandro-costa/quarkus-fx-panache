package br.edu.ifba.saj.ads.controller;

import br.edu.ifba.saj.ads.QuarkusFxApp;
import br.edu.ifba.saj.ads.controller.util.ActionTableCell;
import br.edu.ifba.saj.ads.model.AbstractModel;
import br.edu.ifba.saj.ads.model.MyEntity;
import io.quarkiverse.fx.views.FxView;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

@FxView
@Dependent
public class SampleController {
    @Inject
    QuarkusFxApp app;

    @FXML
    private Label lblResult;

    @FXML
    TextField txNome;

    @FXML
    TextField txFilter;

    @FXML
    private TableView<MyEntity> table;

    @FXML
    private TableColumn<MyEntity, String> tableColumName;

    @FXML
    private TableColumn<AbstractModel, Void> tableColumAction;

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
        tableColumAction.setCellFactory(param -> new ActionTableCell(this::deleteItem, "delete-button"));
        onListChange();
    }

    private void onListChange() {
        lblResult.setText("Total: " + MyEntity.count());
        showFilteredList();
    }

    @Transactional
    public void deleteItem(AbstractModel entity) {
        entity.delete();
        onListChange();
    }

    @FXML
    void btActionFxml(ActionEvent event) {
        app.setFxView("custom-sample");
    }

    @Transactional
    void showFilteredList() {
        String filterText = txFilter.getText();
        if (StringUtil.isNullOrEmpty(filterText)) {
            table.setItems(FXCollections.observableList(MyEntity.listAll()));
            return;
        }        
        table.setItems(FXCollections.observableList(MyEntity.findByField(filterText)));
    }

    @FXML
    void filterTable(KeyEvent event) {
        showFilteredList();
    }

}
