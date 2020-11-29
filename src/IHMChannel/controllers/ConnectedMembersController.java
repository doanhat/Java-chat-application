package IHMChannel.controllers;

import IHMChannel.ConnectedMemberDisplay;
import IHMChannel.IHMChannelController;
import IHMChannel.MemberDisplay;
import common.sharedData.UserLite;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectedMembersController {
    private IHMChannelController ihmChannelController;

    ObservableList<HBox> connectedMembersToDisplay = FXCollections.observableArrayList();
    ListChangeListener<UserLite> membersListListener;

    @FXML
    ListView connectedMembersList;



    /**
     * Pour les tests seulement. A l'avenir, nécessite appel à interface de comm
     */
    private void initConnectedMembersList(){
        //Membres
        List<String> nickName = new ArrayList<>();
        nickName.add("Léa");
        nickName.add("Aida");
        nickName.add("Lucas");
        nickName.add("Vladimir");
        nickName.add("Jérôme");
        nickName.add("Van-Triet");
        nickName.add("Benjamin");
        nickName.add("Stéphane");

        for (int i = 0; i < 6; i++) {
            int d = new Random().nextInt(nickName.size());
            UserLite tmpUser = new UserLite();
            tmpUser.setNickName(nickName.get(d));
            nickName.remove(d);
            try{
                HBox tmp = (HBox) new ConnectedMemberDisplay(tmpUser).root;
                tmp.setMaxWidth(connectedMembersList.getMaxWidth());
                tmp.setMinWidth(connectedMembersList.getMinWidth());
                tmp.setMaxHeight(connectedMembersList.getMaxHeight());
                tmp.setMinHeight(connectedMembersList.getMinHeight());
                connectedMembersToDisplay.add(tmp);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        connectedMembersList.setItems(connectedMembersToDisplay);
//        tmpUser.setNickName("Aida");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Lucas");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Vladimir");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Jérôme");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);
//        tmpUser.setNickName("Van-Triet");
//        tmp = (HBox) new MemberDisplay(tmpUser).root;
//        membersToDisplay.add(tmp);

    }

    public void initialize(){
        initConnectedMembersList();
    }

    public IHMChannelController getIhmChannelController() {
        return ihmChannelController;
    }

    public void setIhmChannelController(IHMChannelController ihmChannelController) {
        this.ihmChannelController = ihmChannelController;
    }
}
