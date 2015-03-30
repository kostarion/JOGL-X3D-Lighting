/**
 * User: kost
 * Date: 29.04.14
 * Time: 10:27
 */
public class Fog {
    Color color;
    float fogVisibility;
    boolean isLinear;
    boolean enabled;

    public Fog () {
        color = new Color();
        fogVisibility = 0;
        isLinear = true;
        enabled = false;
    }

    public Fog (Color color, float fogVisibility, boolean fogType) {
        this.color = color;
        this.fogVisibility = fogVisibility;
        this.isLinear = fogType;
        enabled = true;
    }

    public float getFogInterpolant (float dv) {
        if (!enabled)
            return 1;

        if (dv >= fogVisibility)
            return 0;

        if (isLinear)
            return (fogVisibility - dv) / fogVisibility;
        else
            return (float)Math.exp(-1 * dv / (fogVisibility - dv));
    }
}
