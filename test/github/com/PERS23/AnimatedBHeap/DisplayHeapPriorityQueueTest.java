package github.com.PERS23.AnimatedBHeap;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import net.datastructures.Entry;
import org.junit.Before;

import static org.junit.Assert.*;

public class DisplayHeapPriorityQueueTest {

    private DisplayHeapPriorityQueue<Integer, Shape> instance;

    @Before
    public void setUp() throws Exception {
        instance = new DisplayHeapPriorityQueue<>();
    }

    @org.junit.Test
    public void getNextSwapShouldReturnDistinctPairsIfSwapsOccurred() {
        instance.insert(5, new Circle(20));
        instance.insert(4, new Circle(50));
        instance.insert(3, new Circle(30));

        Pair<Shape, Shape> firstSwap = instance.getNextSwap();
        Pair<Shape, Shape> secondSwap = instance.getNextSwap();
        Pair<Shape, Shape> thirdSwap = instance.getNextSwap();

        assertNotNull(firstSwap);
        assertNotEquals(firstSwap.getKey(), firstSwap.getValue());

        assertNotNull(secondSwap);
        assertNotEquals(secondSwap.getKey(), secondSwap.getValue());

        assertNull(thirdSwap);
    }
}