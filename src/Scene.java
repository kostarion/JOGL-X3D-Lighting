import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * User: kost
 * Date: 29.04.14
 * Time: 11:17
 */
public class Scene {
    static List<Figure> figures;
    static ArrayList<LightNode> light;
    static Fog fog;
    static Point viewPoint;
    static ArrayList<Point> LIGHT_POSITION;
    static Vector DIR_LIGHT_VECTOR = new Vector(0, 1, 0);

    public static void setScene () {
        final float SIZE = 1;
        final Point SPHERE_CENTER = new Point(-3, (float)Math.sqrt(2 * SIZE), 3);
        final Point OCTO_CENTER = new Point(3, 1.4f * SIZE, -3);
        final Point FLOOR_CENTER = new Point(0, 0, 0);
        figures = new ArrayList<>();

        figures.add(Scene.setFloor(new Material(), FLOOR_CENTER));

        figures.add(Scene.setOctahedron(SPHERE_CENTER, SIZE, new Color(0.5f, 0.2f, 1),
                new Material()));
        for (int i = 0; i < 4; i ++)
            modelEnhancement(figures.get(1), SPHERE_CENTER, SIZE);

        figures.add(Scene.setOctahedron(OCTO_CENTER, 1.4f * SIZE, new Color(0.9f, 0.2f, 0.2f),
                new Material()));

        figures.get(0).name = "Floor";
        figures.get(1).name = "Sphere";
        figures.get(2).name = "Octahedron";

        LIGHT_POSITION = new ArrayList<>();
        LIGHT_POSITION.add(new Point(5, 5, 5));
        LIGHT_POSITION.add(new Point(-1, 5, -5));
        light = new ArrayList<>();
        light.add(new PointLight());
        ((PointLight)light.get(0)).position = LIGHT_POSITION.get(0);
        light.add(new SpotLight());
        ((SpotLight)light.get(1)).position = LIGHT_POSITION.get(1);
        //((SpotLight)light.get(1)).isOn = false;

        fog = new Fog();
        fog.fogVisibility = 10;
        viewPoint = new Point(0, 1, 5);
    }

    public static void renderScene (GL2 gl) {
        for (Figure f: figures) {
            f.draw(light, viewPoint, fog, gl);
        }
    }

    public static Figure setOctahedron (Point center, float size, Color color, Material m) {
        Point[] vertices = new Point[] {new Point(center.x + size, center.y + size, center.z),
                new Point(center.x + size, center.y - size, center.z),
                new Point(center.x - size, center.y - size, center.z),
                new Point(center.x - size, center.y + size, center.z),
                new Point(center.x, center.y, center.z + (float)(2 * size * Math.sqrt(2) / 2)),
                new Point(center.x, center.y, center.z - (float)(2 * size * Math.sqrt(2) / 2))};

        Point[][] polygons = new Point[][] {{vertices[0], vertices[1], vertices[4]},
        {vertices[0], vertices[3], vertices[4]}, {vertices[1], vertices[2], vertices[4]},
        {vertices[2], vertices[3], vertices[4]}, {vertices[0], vertices[1], vertices[5]},
        {vertices[0], vertices[3], vertices[5]}, {vertices[1], vertices[2], vertices[5]},
        {vertices[2], vertices[3], vertices[5]}};

        Polygon[] faces = new Polygon[8];
        for (int i = 0; i < 8; i++) {
            faces[i] = new Polygon(Vector.getNormal(polygons[i], center), polygons[i], color);
        }

        return new Figure(m, faces);
    }

    private static void modelEnhancement (Figure f, Point center, float size) {
        Polygon[] newPolygons = new Polygon[f.polygons.length * 4];
        int i = 0;
        Polygon[] temp;
        for (Polygon p: f.polygons) {
            temp = trianglePolygonEnhancement(p, center, p.color, size);
            for (Polygon poly: temp) {
                newPolygons[i] = poly;
                ++i;
            }
        }

        f.polygons = newPolygons;
    }

    private static Polygon[] trianglePolygonEnhancement (Polygon p, Point center, Color color, float size) {
        Polygon[] newPolys = new Polygon[4];
        Point[] newVerts = new Point[3];
        Point[] face;
        for (int i = 0; i < 3; i++) {
            newVerts[i] = getNormalPointOnSide(p.vertices[i], p.vertices[(i+1)%3], center, size);
        }
        for (int i = 0; i < 3; i++) {
            face = new Point[]{newVerts[i], newVerts[(i+1)%3], p.vertices[(i+1)%3]};
            newPolys[i] = new Polygon(Vector.getNormal(face, center), face, color);
        }
        newPolys[3] = new Polygon(Vector.getNormal(newVerts, center), newVerts, color);

        return newPolys;
    }

    private static Point getNormalPointOnSide (Point p1, Point p2, Point center, float size) {
        Point p = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2, (p1.z + p2.z) / 2);
        Vector v = new Vector(center, p);
        v.setLength((float)Math.sqrt(2 * size));
        p.x = center.x + v.x;
        p.y = center.y + v.y;
        p.z = center.z + v.z;

        return p;
    }

    public static Figure setFloor (Material m, Point center) {
        Color black = new Color(0.1f, 0.1f, 0.1f);
        Color white = new Color();
        final int NUM_ROWS = 10;
        final int NUM_COLUMNS = 10;
        Vector normal = new Vector(0, 1, 0);

        Polygon[] polygons = new Polygon[NUM_ROWS * NUM_COLUMNS];
        for (int i = 0; i < NUM_COLUMNS; i ++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                polygons[i * NUM_ROWS + j] = new Polygon(normal,
                        new Point[]{new Point(i + center.x - (float)NUM_ROWS / 2, 0,
                                j + center.z - (float)NUM_ROWS / 2),
                                new Point(i + 1 + center.x - (float)NUM_ROWS / 2, 0,
                                        j + center.z - (float)NUM_ROWS / 2),
                                new Point(i + 1 + center.x - (float)NUM_ROWS / 2, 0,
                                        j + 1 + center.z - (float)NUM_ROWS / 2),
                                new Point(i + center.x - (float)NUM_ROWS / 2, 0, j + 1 + center.z - (float)NUM_ROWS /
                                        2)},
                        ((i + j) % 2 == 0 ? black : white));
            }
        }

        return new Figure(m, polygons);
    }


    public static Color getLitColor (ArrayList<LightNode> lights, Point pos, Vector N, Color vertColor,
                                     Fog fog, Material material, Point viewer) {
        float dv = Point.getDistanceBetween(pos, viewer);
        float f0 = fog.getFogInterpolant(dv);
        Color Od = vertColor; //??

        Color fogComponent =  Color.mult(fog.color, (1 - f0));
        Color notFogComponent = new Color(0, 0, 0);
        for (LightNode light: lights) {
            boolean flag = false;
            if (light instanceof PointLight) {
                float dl = Point.getDistanceBetween(((PointLight)light).position,
                        pos);
                if (dl > ((PointLight)light).radius)
                    flag = true;
            }
            if (light instanceof SpotLight) {
                float dl = Point.getDistanceBetween(((SpotLight)light).position,
                        pos);
                if (dl > ((SpotLight)light).radius)
                    flag = true;
            }

            if (!light.isOn || flag) {
                continue;
            }
            Vector V = new Vector(pos, viewer);
            Vector L = defineL(light, pos);

            Color ambient = Color.mult(Color.mult(Od, material.ambientIntensity), light.ambientIntensity);

            Color diffuse = Color.mult(Color.mult(Od, Vector.modifiedDotProduct(N, L)), light.diffuseIntensity);

            Color specular = Color.mult(material.specularColor, light.diffuseIntensity);
            Vector L_V = Vector.add(L, V);
            L_V.normalize();

            float h = Vector.modifiedDotProduct(N, L_V);
            double s = Math.pow(h, material.shininess * 128);
            specular = Color.mult(specular, (float)s);

            Color current;
            current = Color.add(ambient, diffuse, specular);
            current = Color.mult(current, light.color);
            current = Color.mult(current, light.getSpotLightFactor(L));
            current = Color.mult(current, light.getAttenuationFactor(L.getLength()));

            notFogComponent = Color.add(notFogComponent, current);
        }

        notFogComponent = Color.add(notFogComponent, material.emissiveColor);
        notFogComponent = Color.mult(notFogComponent, f0);

        Color result = Color.add(fogComponent, notFogComponent);
        result.standartize();

        return result;
    }

    private static Vector defineL (LightNode light, Point pos) {
        Vector L;
        if (light instanceof DirectionalLight) {
            L = ((DirectionalLight) light).direction;
        }
        else {
            Point p;
            p = (light instanceof SpotLight ?
                    ((SpotLight) light).position :
                    ((PointLight) light).position);
            L = new Vector(pos, p);
        }

        return L;
    }
}
