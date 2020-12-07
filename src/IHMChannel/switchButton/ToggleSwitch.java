package IHMChannel.switchButton;

import IHMChannel.controllers.MemberController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class ToggleSwitch extends StackPane {
    private final Rectangle back = new Rectangle(30, 17, Color.RED);
    private final Button button = new Button();
//    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
//    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private String buttonStyleOff = "-fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-background-color: #00893d;";
    private String currentRole;
    private boolean state;
    private MemberController memberController;

    public void setMemberController(MemberController memberController) {
        this.memberController = memberController;
        this.state = memberController.isAdmin();
        if (state) {
            button.setStyle(buttonStyleOn);
            back.setFill(Color.valueOf("#80C49E"));
            setAlignment(button, Pos.CENTER_RIGHT);
            state = true;
            currentRole = "admin";
        } else {
            button.setStyle(buttonStyleOff);
            back.setFill(Color.valueOf("#ced5da"));
            setAlignment(button, Pos.CENTER_LEFT);
            state = false;
            currentRole = "member";
        }
    }

    private void init() {
        getChildren().addAll(back, button);
        setMinSize(30, 15);
        back.maxWidth(30);
        back.minWidth(30);
        back.maxHeight(10);
        back.minHeight(10);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#ced5da"));
        Double r = 2.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(15, 15);
        button.setMinSize(15, 15);
        button.setStyle(buttonStyleOff);
        this.state = false;
        this.currentRole = "member";
    }

    public ToggleSwitch() {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#ced5da"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                    currentRole = "member";
                    memberController.toggleAdmin();
                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                    currentRole = "admin";
                    memberController.toggleAdmin();
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }
    public String getCurrentRole() {
        return currentRole;
    }

}