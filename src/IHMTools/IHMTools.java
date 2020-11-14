package IHMTools;

import javafx.scene.layout.Region;

public class IHMTools {

    public static void fitSizeToParent(Region parent, Region child) {
        child.prefHeightProperty().bind(parent.heightProperty());
        child.prefWidthProperty().bind(parent.widthProperty());
        child.minHeightProperty().bind(parent.minHeightProperty());
        child.minWidthProperty().bind(parent.minWidthProperty());
    }

}
