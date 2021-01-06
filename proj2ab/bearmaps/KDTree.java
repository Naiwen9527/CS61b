package bearmaps;

import java.util.List;

public class KDTree implements PointSet {

    private List<Point> pointsu;

    private Node headNode;

    private class Node {
        Point point;
        double x;
        double y;
        Node leftDown;
        Node rightUp;
        boolean vertical;

        private Node(Point p, String direction) {
            point = p;
            x = point.getX();
            y = point.getY();
            leftDown = null;
            rightUp = null;
            if (direction.equals("vertical")) {
                vertical = true;
            } else if (direction.equals("horizontal")) {
                vertical = false;
            }
        }
    }

    public KDTree(List<Point> points) {
        pointsu = points;
        headNode = new Node(points.get(0), "vertical");
        for (int i = 1; i < points.size(); i++) {
            Node freshMeat = new Node(points.get(i), "virgin");
            put(freshMeat, headNode, 0);

        }
    }

    private Node put(Node fresh, Node pos, int depth) {

        if (pos == null) {
            return fresh;
        }
        if (depth % 2  == 0) {
            fresh.vertical = false;
            if (fresh.x < pos.x) {
                pos.leftDown = put(fresh, pos.leftDown, depth + 1);
            } 
            if (fresh.x >= pos.x) {
                pos.rightUp = put(fresh, pos.rightUp, depth + 1);
            }

        } else if (depth % 2 != 0) {
            fresh.vertical = true;
            if (fresh.y < pos.y) {
                pos.leftDown = put(fresh, pos.leftDown, depth + 1);
            }
            if (fresh.y >= pos.y) {
                pos.rightUp = put(fresh, pos.rightUp, depth + 1);
            }

        }
        return pos;
    }

    @Override
    public Point nearest(double x, double y) {
        Node best = headNode;
        Point toPoint = new Point(x, y);
        Node subject = new Node(toPoint, "testing");
        return babysitter(headNode, subject, best).point;
    }

    private Node babysitter(Node current, Node subject, Node best) {
        Node I = best;
        Node good = null;
        Node bad = null;
        if (current == null) {
            return best;
        }
        if (Point.distance(subject.point, current.point) < Point.distance(I.point, subject.point)) {
            I = current;
        }

        if (current.vertical) {
            if (subject.x < current.x) {
                good = current.leftDown;
                bad = current.rightUp;
            } else {
                good = current.rightUp;
                bad = current.leftDown;
            }

        }
        if (!current.vertical) {
            if (subject.y < current.y) {
                good = current.leftDown;
                bad = current.rightUp;
            } else {
                good = current.rightUp;
                bad = current.leftDown;
            }
        }

        I = babysitter(good, subject, I);

        if (theBloodyChopper(current, subject, I)) {
            I = babysitter(bad, subject, I);
        }

        return I;
    }

    private boolean theBloodyChopper(Node current, Node subject, Node best) {
        Point virtual = null;
        if (current.vertical) {
            virtual = new Point(current.x, subject.y);
        }
        if (!current.vertical) {
            virtual = new Point(subject.x, current.y);
        }
        if (Point.distance(virtual, subject.point) < Point.distance(best.point, subject.point)) {
            return true;
        }
        return false;
    }

    private static void main(String[] args) {
        Point A = new Point(2, 3);
        Point B = new Point(4, 2);
        Point C = new Point(4, 5);
        Point D = new Point(3, 3);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);
        Point Z = new Point(4, 2);
        KDTree tree = new KDTree(List.of(A, B, C, D, E, F));
        Point nearst = tree.nearest(5, 1);
        Point nearst1 = tree.nearest(0, 7);
        Point nearst2 = tree.nearest(5, 6);
    }
}
