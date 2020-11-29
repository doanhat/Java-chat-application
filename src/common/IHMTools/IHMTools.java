package common.IHMTools;

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
    public static void fitSizeWidth(Region parent, Region child, float i) {
        child.prefWidthProperty().bind(parent.widthProperty().divide(i));
        child.minWidthProperty().bind(parent.minWidthProperty().divide(i));
    }
}
