package github.com.PERS23.AnimatedBHeap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DisplayHeapPriorityQueue<Integer, Label> mHeapPQ = new DisplayHeapPriorityQueue<>();
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
        Label node = new Label(Integer.toString(key) + "_");
        node.setFont(new Font(28));
        // Add to tree_display pane
        tree_display.getChildren().add(node);
        // add shape and key to PQ
        mHeapPQ.insert(key, node);
        // Loop over all swaps and do animation for each

        // Redo all the edges
        redrawEdges();
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

    private void doSwapAnimation(Label nodeA, Label nodeB) {

    }

    private void redrawEdges() {
        tree_display.getChildren().removeAll(mTreeEdges);
        mTreeEdges = DisplayHeapPriorityQueue.getEdges(mHeapPQ);
        tree_display.getChildren().addAll(mTreeEdges);
    }
}
