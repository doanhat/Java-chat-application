package common.IHMTools;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

public class IHMTools {

    public static void fitSizeToParent(Region parent, Region child) {
        child.prefHeightProperty().bind(parent.heightProperty());
        child.prefWidthProperty().bind(parent.widthProperty());
        child.minHeightProperty().bind(parent.minHeightProperty());
        child.minWidthProperty().bind(parent.minWidthProperty());
    }
    public static void fitSizeListMessage(Region parent, Region child, int i) {
        child.prefHeightProperty().bind(parent.heightProperty().divide(i));
        child.minHeightProperty().bind(parent.minHeightProperty().divide(i));
    }

    public static boolean confirmationPopup(String text){
        ButtonType confirm = new ButtonType("Confirmer");
        ButtonType annul = new ButtonType("Annuler");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, confirm, annul);

        //récupération du CSS
        DialogPane dialogPane = alert.getDialogPane();
        String url =  IHMTools.class.getResource("../../common/IHMCommon/dialog.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        url =  IHMTools.class.getResource("../../common/IHMCommon/common.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        dialogPane.getStyleClass().add("myDialog");

        //Modification boutons

        Button confirmButton = (Button)dialogPane.lookupButton(alert.getButtonTypes().get(0));
        confirmButton.getStyleClass().add("primary-btn");

        Button annulButton = (Button)dialogPane.lookupButton(alert.getButtonTypes().get(1));
        annulButton.getStyleClass().add("critical-btn");

        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        alert.showAndWait();

        if (alert.getResult() == confirm) {
            return true;
        }
        return false;
    }
}
