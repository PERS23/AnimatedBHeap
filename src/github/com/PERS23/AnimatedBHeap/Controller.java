package github.com.PERS23.AnimatedBHeap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DisplayHeapPriorityQueue<Integer, CircleLabel> mHeapPQ = new DisplayHeapPriorityQueue<>();
    private List<Line> mTreeEdges = new ArrayList<>();

    @FXML private Pane tree_display;
    @FXML private TextField key_entry;
    @FXML private Button add_key_button;
    @FXML private Button remove_min_button;
    @FXML private Button reset_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addKey() {
        // fetch key from field
        Integer key = retrieveKey();
        // validate entry
        // Create shape
        CircleLabel node = new CircleLabel(Integer.toString(key));
        // Add to tree_display pane
        tree_display.getChildren().add(node);
        // add shape and key to PQ
        mHeapPQ.insert(key, node);
        // Loop over all swaps and do animation for each
        doSwapAnimations();
    }

    @FXML
    private void removeMin() {
        // fetch key from field
        // validate entry
    }

    private int retrieveKey() throws IllegalArgumentException {
        return Integer.parseInt(key_entry.getText());
    }

    @FXML
    private void resetTreeAndDisplay() {

    }

    private void doSwapAnimations() {
        Pair<CircleLabel, CircleLabel> firstPair = mHeapPQ.getNextSwap();
        if (firstPair != null) {
            final BezierHeapSwapTransition animation = new BezierHeapSwapTransition(new Duration(400));

            clearEdges();
            animation.setNodes(firstPair.getKey(), firstPair.getValue());
            animation.play();

            animation.setOnFinished(e -> {
                Pair<CircleLabel, CircleLabel> nextPair = mHeapPQ.getNextSwap();
                if (nextPair != null) {
                    animation.setNodes(nextPair.getKey(), nextPair.getValue());
                    animation.playFromStart();
                } else {
                    drawEdges();
                }
            });
        } else {
            clearEdges();
            drawEdges();
        }
    }

    private void clearEdges() {
        tree_display.getChildren().removeAll(mTreeEdges);
        mTreeEdges = null;
    }

    private void drawEdges() {
        mTreeEdges = DisplayHeapPriorityQueue.getEdges(mHeapPQ);
        for (Line edge : mTreeEdges) {
            tree_display.getChildren().add(0, edge);
            edge.toBack();
        }
    }
}
