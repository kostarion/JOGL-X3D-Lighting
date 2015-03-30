/**
 * User: kost
 * Date: 29.04.14
 * Time: 9:30
 */
public class Material {
    float ambientIntensity;
    Color diffuseColor;
    Color emissiveColor;
    Color specularColor;
    float shininess;
    float transparency;

    public Material () {
        ambientIntensity = 0.2f;
        diffuseColor = new Color (0.8f, 0.8f, 0.8f);
        emissiveColor = new Color (0, 0, 0);
        specularColor = new Color(0, 0, 0);
        shininess = 0.2f;
        transparency = 0;
    }

    public Material (float ambientIntensity, Color diffuseColor,
                     Color emissiveColor, Color specularColor,
                     float shininess, float transparency) {

        this.ambientIntensity = ambientIntensity;
        this.diffuseColor = diffuseColor;
        this.emissiveColor = emissiveColor;
        this.specularColor = specularColor;
        this.shininess = shininess;
        this.transparency = transparency;
    }
}
