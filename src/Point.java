/**
 * User: kost
 * Date: 29.04.14
 * Time: 7:14
 */
public class Point {
    float x, y, z;

    public Point () {
        x = 0;
        y = 0;
        z = -1;
    }

    public Point (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float[] asArray () {
        return new float[] {x, y, z};
    }

    public static float getDistanceBetween (Point p1, Point p2) {
        return (float)Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x) +
                (p1.y - p2.y) * (p1.y - p2.y) + (p1.z - p2.z) * (p1.z - p2.z)));
    }
}
