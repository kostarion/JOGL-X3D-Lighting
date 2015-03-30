/**
 * User: kost
 * Date: 29.04.14
 * Time: 6:39
 */
public class PointLight extends LightNode {
    Point position;
    float radius;

    public PointLight () {
        super();
        position = new Point();
        radius = 20;
    }

    public PointLight (Color color, float ambientIntensity, float diffuseIntensity,
                       Point pos, float rad, float a1, float a2, float a3) {
        super(color, ambientIntensity, diffuseIntensity, a1, a2, a3);
        position = pos;
        radius = rad;
    }

    public void setRadius(float v) {
        radius = v;
    }
}
