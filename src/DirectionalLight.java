/**
 * User: kost
 * Date: 29.04.14
 * Time: 6:25
 */
public class DirectionalLight extends LightNode {
    Vector direction;

    public DirectionalLight() {
        super();
        direction = new Vector();
    }

    public DirectionalLight(Color color, float ambientIntensity, float diffuseIntensity,
                           float a1, float a2, float a3, Vector dir) {
        super(color, ambientIntensity, diffuseIntensity, a1, a2, a3);
        direction = dir;
    }

    @Override
    public float getAttenuationFactor (float r) {
        return 1;
    }
}


