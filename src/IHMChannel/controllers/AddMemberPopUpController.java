package IHMChannel.controllers;

import IHMChannel.MemberAddDisplay;
import IHMChannel.MemberDisplay;
import common.IHMTools.IHMTools;
import common.sharedData.User;
import common.sharedData.UserLite;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddMemberPopUpController implements Initializable {

    @FXML
    private TextField userName;

    @FXML
    private Button searchBtn;

    @FXML
    private ListView<HBox> listAddUser;

    @FXML
    private HBox hbox;

    ObservableList<HBox> membersToDisplay = FXCollections.observableArrayList();
    private ChannelController channelController;

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }
    public ChannelController getChannelController(){
        return this.channelController;
    }

    public void initUser() throws IOException {
        List<String> nickName = new ArrayList<>();
        nickName.add("Adriano");
        nickName.add("Beckham");
        nickName.add("Corona");
        nickName.add("Diego");
        nickName.add("Erick");
        nickName.add("Franco");
        nickName.add("Grook");
        nickName.add("Henry");
        nickName.add("Alex");

        for (int i = 0; i < 9; i++) {
            int d = new Random().nextInt(nickName.size());
            UserLite tmpUser = new UserLite();
            tmpUser.setNickName(nickName.get(d));
            nickName.remove(d);
            MemberAddDisplay memberAddDisplay = new MemberAddDisplay(tmpUser, this.getChannelController());
            memberAddDisplay.memberAddController.userToInvite = tmpUser;
            memberAddDisplay.memberAddController.channelController = this.getChannelController();
            HBox tmp = (HBox) memberAddDisplay.root;
            tmp.setId(tmpUser.getNickName());
            tmp.setMaxWidth(listAddUser.getMaxWidth());
            tmp.setMinWidth(listAddUser.getMinWidth());
            tmp.setMaxHeight(listAddUser.getMaxHeight());
            tmp.setMinHeight(listAddUser.getMinHeight());
            membersToDisplay.add(tmp);
        }
        listAddUser.setItems(membersToDisplay);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image editImage = new Image("IHMChannel/icons/search_icon.jpg");
        ImageView editIcon = new ImageView(editImage);
        editIcon.setFitHeight(25);
        editIcon.setFitWidth(25);
        searchBtn.setGraphic(editIcon);
        IHMTools.fitSizeWidth((Region) hbox, (Region) searchBtn, 9);
        userName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != "" && !newValue.trim().isEmpty()) {
                    System.out.println("(" + oldValue + "Changed to  " + newValue + ")\n");
                    ObservableList<HBox> containList = membersToDisplay.filtered(t -> t.getId().toLowerCase().substring(0, newValue.length()).contains(newValue.toLowerCase()));
                    System.out.println(containList);
                    listAddUser.setItems(containList);
                }
                else {
                    listAddUser.setItems(membersToDisplay);
                }
            }
        });
    }

    public void orderAlphabetic(ObservableList<HBox> observer) {
        observer.sort(new Comparator<HBox>() {
            @Override
            public int compare(HBox m1, HBox m2) {
                return m1.getId().compareTo(m2.getId());
            }
        });
    }

    public ListView<HBox> getListAddUser() {
        return listAddUser;
    }
}
