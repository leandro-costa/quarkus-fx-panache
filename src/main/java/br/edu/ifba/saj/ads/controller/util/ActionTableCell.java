package br.edu.ifba.saj.ads.controller.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import br.edu.ifba.saj.ads.model.AbstractModel;
import javafx.geometry.Pos;

public class ActionTableCell extends javafx.scene.control.TableCell<AbstractModel, Void> {
    private java.util.function.Consumer<AbstractModel> deleteCallback;
    private String cssClass;

    public ActionTableCell(java.util.function.Consumer<AbstractModel> deleteCallback, String cssClass) {
        this.deleteCallback = deleteCallback;
        this.cssClass = cssClass;
        setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            Button btn = new Button();
            btn.getStyleClass().add(cssClass);
            btn.setOnAction(event -> {
                AbstractModel entity = getTableView().getItems().get(getIndex());
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