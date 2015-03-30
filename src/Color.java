/**
 * User: kost
 * Date: 29.04.14
 * Time: 7:20
 */

/**
 * Представление цвета в формате RGB
 */
public class Color {
    float red; //красный
    float green;
    float blue;

    public Color () {
        red = 1;
        green = 1;
        blue = 1;
    }

    public Color (float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    /**
     * Приводит компоненты цвета к стандартным значениям
     */
    public void standartize () {
        if (red > 1) red = 1;
        if (red < 0) red = 0;
        if (green > 1) green = 1;
        if (green < 0) green = 0;
        if (blue > 1) blue = 1;
        if (blue < 0) blue = 0;
    }

    /**
     * Представляет цвет как массив
     * @return Массив из значений красного, зеленого и синего компонентов
     */
    public float[] asArray () {
        return new float[] {red, green, blue, 1};
    }

    /**
     * Перемножает цвет и число
     * @param c цвет
     * @param f число
     * @return произведение
     */
    public static Color mult (Color c, float f) {
        return new Color (c.red * f, c.green * f, c.blue * f);
    }

    /**
     * Перемножает два цвета
     * @param c1 цвет1
     * @param c2 цвет2
     * @return произведение
     */
    public static Color mult (Color c1, Color c2) {
        return new Color(c1.red * c2.red, c1.green * c2.green, c1.blue * c2.blue);
    }

    /**
     * Складывает два цвета
     * @param c1 цвет1
     * @param c2 цвет2
     * @return сумма
     */
    public static Color add (Color c1, Color c2) {
        return new Color(c1.red + c2.red, c1.green + c2.green, c1.blue + c2.blue);
    }

    /**
     * Складывает три цвета
     * @param c1 цвет1
     * @param c2 цвет2
     * @param c3 цвет3
     * @return сумма
     */
    public static Color add (Color c1, Color c2, Color c3) {
        return new Color(c1.red + c2.red + c3.red,
                c1.green + c2.green + c3.green, c1.blue + c2.blue + c3.blue);
    }
}
