/**
 * User: kost
 * Date: 29.04.14
 * Time: 6:50
 */
public class SpotLight extends LightNode{
    public float beamWidth;
    public float cutOffAngle;
    public Vector direction;
    public Point position;
    public float radius;

    public SpotLight () {
        super();
        beamWidth = (float)Math.PI / 2;
        cutOffAngle = (float)Math.PI / 4;
        direction = new Vector();
        position = new Point();
        radius = 20;
    }

    public SpotLight (Color color, float ambientIntensity, float diffuseIntensity,
                      Point pos, float rad, float a1, float a2, float a3,
                      Vector dir, float cutOffAngle, float beamWidth) {
        super(color, ambientIntensity, diffuseIntensity, a1, a2, a3);
        this.beamWidth = beamWidth;
        this.cutOffAngle = cutOffAngle;
        direction = dir;
        position = pos;
        radius = rad;
    }

    @Override
    public float getSpotLightFactor (Vector L) {
        float spotAngle = (float)Math.acos((float)Vector.modifiedDotProduct(direction, L));

        if (spotAngle >= cutOffAngle)
            return 0;

        if (spotAngle <= beamWidth)
            return 1;

        return (spotAngle - cutOffAngle) / (beamWidth - cutOffAngle);
    }
}
