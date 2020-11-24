package IHMChannel;

import IHMChannel.controllers.AdminMembersListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminMembersListDisplay {
    public Parent root = null;
    public AdminMembersListController AdminMembersController;

    public AdminMembersListController getController(){return this.AdminMembersController;}

    public AdminMembersListDisplay() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/AdminMembersList.fxml"));
        root = fxmlLoader.load();
        AdminMembersController = fxmlLoader.getController();
    }
}
