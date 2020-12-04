package IHMChannel.tools.AddMemberCellFactory;

import common.sharedData.UserLite;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class AddMemberCellFactory implements Callback<ListView<UserLite>, ListCell<UserLite>> {
    @Override
    public ListCell<UserLite> call(ListView<UserLite> param) {
        return new AddMemberCell();
    }
}
