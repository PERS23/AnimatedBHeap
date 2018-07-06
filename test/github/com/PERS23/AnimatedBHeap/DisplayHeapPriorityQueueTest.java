package github.com.PERS23.AnimatedBHeap;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import net.datastructures.Entry;
import org.junit.Before;

import static org.junit.Assert.*;

public class DisplayHeapPriorityQueueTest {

    private DisplayHeapPriorityQueue<Integer, CircleLabel> instance;

    @Before
    public void setUp() throws Exception {
        instance = new DisplayHeapPriorityQueue<>();
    }

    @org.junit.Test
    public void getNextSwapShouldReturnDistinctPairsIfSwapsOccurred() {
        instance.insert(5, new CircleLabel("_"));
        instance.insert(4, new CircleLabel("_"));
        instance.insert(3, new CircleLabel("_"));

        Pair<CircleLabel, CircleLabel> firstSwap = instance.getNextSwap();
        Pair<CircleLabel, CircleLabel> secondSwap = instance.getNextSwap();
        Pair<CircleLabel, CircleLabel> thirdSwap = instance.getNextSwap();

        assertNotNull(firstSwap);
        assertNotEquals(firstSwap.getKey(), firstSwap.getValue());

        assertNotNull(secondSwap);
        assertNotEquals(secondSwap.getKey(), secondSwap.getValue());

        assertNull(thirdSwap);
    }
}