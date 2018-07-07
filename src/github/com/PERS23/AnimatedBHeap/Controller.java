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
    private static final int SWAP_TIME_MS = 850;

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
        try {
            Integer key = Integer.parseInt(key_entry.getText());

            CircleLabel node = new CircleLabel(Integer.toString(key));
            tree_display.getChildren().add(node);

            mHeapPQ.insert(key, node);
            playUpheapAnimation();
        } catch (NumberFormatException nfe) {                 // Parse int failed to recognise num, so it can't be added
            key_entry.setText("* Error - Invalid Number");
        }
    }

    @FXML
    private void removeMin() {
        playDownheapAnimation(mHeapPQ.removeMin().getValue());
    }

    @FXML
    private void resetTreeAndDisplay() {
        mHeapPQ = new DisplayHeapPriorityQueue<>();
        tree_display.getChildren().clear();
    }

    private void playUpheapAnimation() {
        Pair<CircleLabel, CircleLabel> firstPair = mHeapPQ.getNextSwap();

        if (firstPair != null) {                              // No swaps were executed, so no animation needs to happen
            final BezierHeapSwapTransition animation = new BezierHeapSwapTransition(new Duration(SWAP_TIME_MS));

            clearEdges();                                       // Clear the edges so that the animation appears cleaner
            animation.setNodes(firstPair.getKey(), firstPair.getValue());
            animation.play();

            animation.setOnFinished(e -> {
                Pair<CircleLabel, CircleLabel> nextPair = mHeapPQ.getNextSwap();
                if (nextPair != null) {                           // There's another pair to swap, so update and restart
                    animation.setNodes(nextPair.getKey(), nextPair.getValue());
                    animation.playFromStart();
                } else { // Insertion was already immediately redrawn, and swaps recorrect it so drawing edges at the end will always be correct
                    drawEdges();
                }
            });
        } else {                                                                  // Edges do need to be redrawn however
            clearEdges();
            drawEdges();
        }
    }

    private void playDownheapAnimation(CircleLabel removedNode) {
        if (mHeapPQ.isEmpty()) {               // The heap is empty, so we can just immediately remove it from the scene
            tree_display.getChildren().remove(removedNode);
        } else {
            Pair<CircleLabel, CircleLabel> firstPair = mHeapPQ.getNextSwap();
            final BezierHeapSwapTransition animation = new BezierHeapSwapTransition(new Duration(SWAP_TIME_MS));

            clearEdges();
            animation.setNodes(firstPair.getKey(), firstPair.getValue());
            animation.play();

            animation.setOnFinished(e -> {
                Pair<CircleLabel, CircleLabel> nextPair = mHeapPQ.getNextSwap();
                                        // Inefficient, but no easy/nice work around as can't set it to null in a lambda
                tree_display.getChildren().remove(removedNode);

                if (nextPair != null) {
                    animation.setNodes(nextPair.getKey(), nextPair.getValue());
                    animation.playFromStart();
                } else {
                    mHeapPQ.redraw(); // Have to redraw after all swaps, otherwise the tree might be wider than is expected
                    drawEdges();
                }
            });
        }
    }

    private void clearEdges() {
        tree_display.getChildren().removeAll(mTreeEdges);
        mTreeEdges = null;
    }

    private void drawEdges() {
        mTreeEdges = DisplayHeapPriorityQueue.getEdges(mHeapPQ);
        if (mTreeEdges != null) {
            for (Line edge : mTreeEdges) {
                tree_display.getChildren().add(edge);
                edge.toBack();
            }
        }
    }
}
