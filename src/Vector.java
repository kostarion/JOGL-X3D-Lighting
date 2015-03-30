/**
 * User: kost
 * Date: 29.04.14
 * Time: 7:09
 */
public class Vector {
    float x, y, z;

    public Vector () {
        x = 0;
        y = 0;
        z = -1;
    }

    public Vector (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector (Point a, Point b) {
        x = b.x - a.x;
        y = b.y - a.y;
        z = b.z - a.z;
        normalize();
    }

    public float getLength () {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    void normalize () {
        float length = getLength();
        x /= length;
        y /= length;
        z /= length;
    }

    void setLength (float length) {
        float curLength = getLength();
        x = x / curLength * length;
        y = y / curLength * length;
        z = z / curLength * length;
    }

    void  reverse () {
        x *= -1;
        y *= -1;
        z *= -1;
    }

    public static Vector getNormal (Point[] v, Point center) {
        Point normal = new Point(0, 0, 0);
        for (Point p: v) {
            normal.x += p.x;
            normal.y += p.y;
            normal.z += p.z;
        }
        normal.x /= v.length;
        normal.y /= v.length;
        normal.z /= v.length;
        Vector n = new Vector(center, normal);
        n.normalize();

        return n;
    }

    public float[] asArray () {
        return new float[] {x, y, z};
    }

    public static Vector add (Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static float dotProduct (Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static float modifiedDotProduct (Vector v1, Vector v2) {
        float dotProduct = dotProduct(v1, v2);

        return (dotProduct < 0 ? 0 : dotProduct);
    }
}
