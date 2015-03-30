import javax.media.opengl.GL2;
import java.util.ArrayList;

/**
 * User: kost
 * Date: 29.04.14
 * Time: 15:23
 */
public class Figure {
    Material material;
    Polygon[] polygons;
    String name;

    public Figure (Material material, Polygon[] polygons) {
        this.material = material;
        this.polygons = polygons;
    }

    public void draw (ArrayList<LightNode> lights, Point viewer, Fog fog, GL2 gl) {
        for (Polygon polygon : polygons) {
            gl.glBegin(GL2.GL_POLYGON);
            gl.glNormal3fv(polygon.normal.asArray(), 0);
            for (Point p : polygon.vertices) {
                Color c = Scene.getLitColor(lights, p, polygon.normal,
                        polygon.color, fog, material, viewer);
                gl.glColor3f(c.red, c.green, c.blue);
                gl.glVertex3f(p.x, p.y, p.z);
            }
            gl.glEnd();
        }
    }
}
