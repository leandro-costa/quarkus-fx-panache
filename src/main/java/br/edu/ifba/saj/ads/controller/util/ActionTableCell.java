package br.edu.ifba.saj.ads.controller.util;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ActionTableCell extends javafx.scene.control.TableCell<PanacheEntity, Void> {
    private java.util.function.Consumer<PanacheEntity> deleteCallback;

    public ActionTableCell(java.util.function.Consumer<PanacheEntity> deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            Button btn = new Button("X");
            btn.setOnAction(event -> {
                PanacheEntity entity = getTableView().getItems().get(getIndex());
                new Alert(Alert.AlertType.CONFIRMATION, "Delete " + entity.id + "?")
                        .showAndWait()
                        .filter(response -> response == javafx.scene.control.ButtonType.OK)
                        .ifPresent(response -> {
                            deleteCallback.accept(entity);                            
                        });
            });
            setGraphic(btn);
        }
    }
}