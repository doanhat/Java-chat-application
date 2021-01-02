package common.IHMTools;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class IHMTools {

    private IHMTools(){

    }

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

    public static boolean confirmationPopup(String text) {
        ButtonType confirm = new ButtonType("Confirmer");
        ButtonType annul = new ButtonType("Annuler");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, confirm, annul);

        //récupération du CSS
        DialogPane dialogPane = alert.getDialogPane();
        String url = IHMTools.class.getResource("../../common/IHMCommon/dialog.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        url = IHMTools.class.getResource("../../common/IHMCommon/common.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        dialogPane.getStyleClass().add("myDialog");

        //Modification boutons

        Button confirmButton = (Button) dialogPane.lookupButton(alert.getButtonTypes().get(0));
        confirmButton.getStyleClass().add("primary-btn");

        Button annulButton = (Button) dialogPane.lookupButton(alert.getButtonTypes().get(1));
        annulButton.getStyleClass().add("critical-btn");

        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        alert.showAndWait();

        return (alert.getResult() == confirm);
    }

    public static boolean informationPopup(String text) {
        ButtonType ok = new ButtonType("ok");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ok);

        //récupération du CSS
        DialogPane dialogPane = alert.getDialogPane();
        String url = IHMTools.class.getResource("../../common/IHMCommon/dialog.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        url = IHMTools.class.getResource("../../common/IHMCommon/common.css").toExternalForm();
        dialogPane.getStylesheets().add(url);

        dialogPane.getStyleClass().add("myDialog");

        //Modification boutons
        Button okButton = (Button) dialogPane.lookupButton(alert.getButtonTypes().get(0));
        okButton.getStyleClass().add("critical-btn");

        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        alert.showAndWait();

        return (alert.getResult() == ok);
    }

    public static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(e -> popup.hide());
        String url = IHMTools.class.getResource("../../common/IHMCommon/common.css").toExternalForm();
        label.getStylesheets().add(url);
        label.getStyleClass().add("notification-popup");
        popup.getContent().add(label);
        return popup;
    }

    public static void showPopupMessage(final String message, final Window window) {
        final Popup popup = createPopup(message);
        popup.setOnShown(e -> {
            popup.setX(window.getX() + window.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(window.getY() + window.getHeight() / 6 - popup.getHeight() / 2);
        });
        popup.show(window);
    }

}
