/**
 * User: kost
 * Date: 29.04.14
 * Time: 15:23
 */
public class Polygon {
    Vector normal;
    Point[] vertices;
    Color color;

    public Polygon (Vector normal, Point[] vertices, Color colors) {
        this.normal = normal;
        this.vertices = vertices;
        this.color = colors;
    }
}
