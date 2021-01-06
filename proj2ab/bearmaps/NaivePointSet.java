package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> pointsu;

    public NaivePointSet(List<Point> points) {
        pointsu = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point subject = new Point(x, y);
        double shortest = Double.POSITIVE_INFINITY;
        Point best = null;
        for (Point p : pointsu) {
            if (Point.distance(subject, p) < shortest) {
                best = p;
                shortest = Point.distance(subject, p);
            }
        }
        return best;
    }

    private static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        double x = ret.getX(); // evaluates to 3.3
        double y = ret.getY(); // evaluates to 4.4
    }
}
