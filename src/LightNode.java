/**
 * User: kost
 * Date: 29.04.14
 * Time: 5:56
 */
public abstract class LightNode {
    Color color;
    float ambientIntensity;
    float diffuseIntensity;
    float[] attenuation;
    boolean isOn;

    public LightNode () {
        color = new Color();
        ambientIntensity = 0;
        diffuseIntensity = 1;
        attenuation = new float[] {1, 0, 0};
        isOn = true;
    }

    public LightNode (Color color, float ambientIntensity, float diffuseIntensity,
                      float a1, float a2, float a3) {
        this.color = color;
        this.ambientIntensity = ambientIntensity;
        this.diffuseIntensity = diffuseIntensity;
        attenuation = new float[] {a1, a2, a3};
        isOn = true;
    }

    public float getAttenuationFactor (float r) {
        return 1 / Math.max(attenuation[0] + attenuation[1] * r +
        attenuation[2] * r * r, 1);
    }

    public float getSpotLightFactor (Vector L) {
        return 1;
    }
}
