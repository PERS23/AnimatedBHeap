package github.com.PERS23.AnimatedBHeap;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class BezierHeapSwapTransition extends Transition {
    private static final double QUAD_BEZ_DIST = DisplayHeapPriorityQueue.MIN_Y_DIST;

    private CircleLabel mNodeA;
    private CircleLabel mNodeB;

    private double startX, startY;
    private double endX, endY;
    /* (bezierX,bezierY) is the control point which governs the curvature of the curve, think in this manner,
     * if this point is farther away, then the curve is more steep. if this point is closer then the curve
     * is less steep.
     */
    private double bezierX, bezierY;
    private double oppositeBezierX, oppositeBezierY;

    public BezierHeapSwapTransition(Duration duration) {
        this.setCycleDuration(duration);
    }

    public void setNodes(CircleLabel nodeA, CircleLabel nodeB) {
        mNodeA = nodeA;
        mNodeB = nodeB;

        startX = nodeA.getLayoutX();
        startY = nodeA.getLayoutY();

        endX = nodeB.getLayoutX();
        endY = nodeB.getLayoutY();

        // First 2 cases where A is left child of B, or vice versa
        if (startX < endX && startY > endY) {
            bezierX = startX;
            bezierY = startY - QUAD_BEZ_DIST;

            oppositeBezierX = endX;
            oppositeBezierY = endY + QUAD_BEZ_DIST;
        }  else if (startX > endX && startY < endY) {
            bezierX = startX;
            bezierY = startY + QUAD_BEZ_DIST;

            oppositeBezierX = endX;
            oppositeBezierY = endY - QUAD_BEZ_DIST;
        }
        // Last 2 cases where B is right child of A, or vice versa
        else if (startX < endX && startY < endY) {
            bezierX = startX;
            bezierY = startY + QUAD_BEZ_DIST;

            oppositeBezierX = endX;
            oppositeBezierY = endY - QUAD_BEZ_DIST;
        } else if (startX > endX && startY > endY) {
            bezierX = startX;
            bezierY = startY - QUAD_BEZ_DIST;

            oppositeBezierX = endX;
            oppositeBezierY = endY + QUAD_BEZ_DIST;
        }
    }

    /* The argument passed to the frac parameter defines the current position within the animation.
     * The fraction is 0.0 at the start and 1.0 at the end.
     */
    @Override
    protected void interpolate(double frac) {
        mNodeA.setLayoutX(
                ((1 - frac) * (1 - frac) * startX + 2 * (1 - frac) * frac * bezierX + frac * frac * endX));
        mNodeA.setLayoutY(
                ((1 - frac) * (1 - frac) * startY + 2 * (1 - frac) * frac * bezierY + frac * frac * endY));

        mNodeB.setLayoutX(
                ((1 - frac) * (1 - frac) * endX + 2 * (1 - frac) * frac * oppositeBezierX + frac * frac * startX));
        mNodeB.setLayoutY(
                ((1 - frac) * (1 - frac) * endY + 2 * (1 - frac) * frac * oppositeBezierY + frac * frac * startY));
    }
}