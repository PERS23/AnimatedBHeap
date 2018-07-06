package github.com.PERS23.AnimatedBHeap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import jdk.internal.util.xml.impl.Pair;

import java.io.IOException;

public class CircleLabel extends StackPane {

    private static final double PADDING = 10.0;

    private Text mLabel;
    private Circle mCircle;

    public CircleLabel(String text) {
        super();

        mLabel = createText(text);
        mCircle = encircle(mLabel);

        this.getChildren().addAll(mCircle, mLabel);
    }

    private Text createText(String string) {
        Text text = new Text(string);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-family: \"Segoe UI Light\";" +
                      "-fx-font-size: 48px;"
        );
        text.setFill(Paint.valueOf("#FFFFFD"));

        return text;
    }

    private Circle encircle(Text text) {
        Circle circle = new Circle();
        circle.setFill(Color.valueOf("#D01818"));
        circle.setRadius(getTextWidth(text) / 2 + PADDING);

        return circle;
    }

    private double getTextWidth(Text text) {
        new Scene(new Group(text));
        text.applyCss();

        return text.getLayoutBounds().getWidth();
    }

    public double getCenterX() {
        return this.getLayoutX() + mCircle.getRadius() + this.getPadding().getLeft();
    }

    public double getCenterY() {
        return this.getLayoutY() + mCircle.getRadius() + this.getPadding().getTop();
    }
}
