package github.com.PERS23.AnimatedBHeap;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import net.datastructures.*;

import java.util.ArrayDeque;

public class DisplayHeapPriorityQueue<K, V extends CircleLabel> extends HeapPriorityQueue<K,V> {

    public static final double MIN_X_DIST = 100;
    public static final double MIN_Y_DIST = 100;

    private Queue<Pair<V, V>> swapQueue = new LinkedQueue<>();

    private static <K, V extends CircleLabel> int layout(DisplayHeapPriorityQueue<K, V> tree, int node, int depth, int x) {
        if (tree.left(node) < tree.size()) {
            x = layout(tree, tree.left(node), depth + 1, x);                     // resulting x will be increased
        }

        tree.heap.get(node).getValue().setLayoutX(MIN_X_DIST * (x++));                               // post-increment x
        tree.heap.get(node).getValue().setLayoutY(MIN_Y_DIST * depth);

        if (tree.right(node) < tree.size()) {
            x = layout(tree, tree.right(node), depth + 1, x);                    // resulting x will be increased
        }
        return x;
    }

    public static <K, V extends CircleLabel> java.util.List<Line> getEdges(DisplayHeapPriorityQueue<K, V> tree) {
        java.util.Deque<Integer> stack = new ArrayDeque<Integer>();
        java.util.List<Line> edges = new java.util.ArrayList<>();

        Integer current = 0;
        stack.push(current);

        while (!stack.isEmpty()) {
            current = stack.pop();
            CircleLabel parent = tree.heap.get(current).getValue();

            int left = tree.left(current), right = tree.right(current);

            if (left < tree.size()) {
                CircleLabel leftChild = tree.heap.get(left).getValue();
                if (leftChild != null) {
                    stack.push(left);
                    Line leftEdge = new Line(parent.getCenterX(), parent.getCenterY(), leftChild.getCenterX(), leftChild.getCenterY());
                    edges.add(leftEdge);
                }
            }

            if (right < tree.size()) {
                CircleLabel rightChild = tree.heap.get(right).getValue();
                if (rightChild != null) {
                    stack.push(right);
                    Line rightEdge = new Line(parent.getCenterX(), parent.getCenterY(), rightChild.getCenterX(), rightChild.getCenterY());
                    edges.add(rightEdge);
                }
            }
        }

        return edges;
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);                                          // auxiliary key-checking method (could throw exception)
        Entry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);                                                                  // add to the end of the list

        layout(this, 0, 0, 0); // Redraw the tree so element is removed and is ready for swap anim

        upheap(heap.size() - 1);                                                          // upheap newly added entry
        return newest;
    }

    @Override
    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) return null;
        Entry<K,V> answer = heap.get(0);
        swap(0, heap.size() - 1);                                                   // put minimum item at the end
        heap.remove(heap.size() - 1);                                              // and remove it from the list;

        layout(this, 0, 0, 0);

        downheap(0);                                                                             // then fix new root
        return answer;
    }

    @Override
    protected void swap(int i, int j) {
                                  // Add the swap to the queue so the sys can correctly draw the bubbling steps in order
        swapQueue.enqueue(new Pair<>(heap.get(i).getValue(), heap.get(j).getValue()));

        Entry<K,V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Returns the next pair of nodes to swap the positions of.
     * @return Pair of entries that should have their display positions swapped. Null if empty.
     */
    public Pair<V, V> getNextSwap() {
        return swapQueue.dequeue();
    }
}
