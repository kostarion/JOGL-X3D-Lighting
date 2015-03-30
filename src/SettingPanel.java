import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * User: kost
 * Date: 03.05.14
 * Time: 0:49
 */
public class SettingPanel extends JPanel {

    public SettingPanel () {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(270, 590));

        JTabbedPane setLights = new JTabbedPane();

        LightSettings light1Settings = new LightSettings(Scene.light, 0);
        LightSettings light2Settings = new LightSettings(Scene.light, 1);
        light2Settings.lightType.setSelectedIndex(2);

        setLights.addTab("Light 1", light1Settings);
        setLights.addTab("Light 2", light2Settings);


        JTabbedPane setMaterials = new JTabbedPane();
        for (Figure f : Scene.figures) {
            setMaterials.addTab(f.name, new MaterialSettings(f));
        }

        JPanel setFog = new JPanel();
        setFog.add(new FogSettings(Scene.fog));
        setFog.setBorder(new TitledBorder("Fog"));

        JPanel playPanel = new JPanel();
        final JButton play = new JButton("Play scene");
        play.setEnabled(false);
        final JButton stop = new JButton("Stop scene");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play.setEnabled(false);
                stop.setEnabled(true);
                X3D_GLListener.startMoving();
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop.setEnabled(false);
                play.setEnabled(true);
                X3D_GLListener.stopMoving();
            }
        });
        playPanel.add(play);
        playPanel.add(stop);

        add(playPanel);
        add(setLights);
        add(setMaterials);
        add(setFog);
    }

    static enum MaterialColor {DIFFUSE_COLOR, EMISSIVE_COLOR, SPECULAR_COLOR}

    static JSlider setAmbientIntensity(final Object o) throws ClassCastException{
        JSlider ambientIntensity = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        ambientIntensity.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(100), new JLabel("1"));
        //ambientIntensity.setLabelTable(labelTable);
        //ambientIntensity.setPaintTicks(true);
        //ambientIntensity.setMinorTickSpacing(10);
        //ambientIntensity.setMajorTickSpacing(50);

        ambientIntensity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                float intensity = (float)source.getValue() / 100;
                if (o instanceof LightNode) {
                    ((LightNode)o).ambientIntensity = intensity;
                }
                else if (o instanceof Material) {
                    ((Material)o).ambientIntensity = intensity;
                }
                else throw new ClassCastException("Not a light or material");
            }
        });

        return ambientIntensity;
    }

    static float getRGBValue (JSlider slider) {
        return slider.getValue() / 100.f;
    }

    static JSlider[] setRGB(final Object o, final MaterialColor color) throws ClassCastException{
        final JSlider[] rgbComponents = new JSlider[3];
        for (int i = 0; i < 3; i++) {
            rgbComponents[i] = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
            rgbComponents[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (o instanceof LightNode) {
                        ((LightNode)o).color.red =  getRGBValue(rgbComponents[0]);
                        ((LightNode)o).color.green = getRGBValue(rgbComponents[1]);
                        ((LightNode)o).color.blue = getRGBValue(rgbComponents[2]);
                    }
                    else if (o instanceof Material) {
                        switch (color) {
                            case DIFFUSE_COLOR :
                                ((Material)o).diffuseColor.red =  getRGBValue(rgbComponents[0]);
                                ((Material)o).diffuseColor.green = getRGBValue(rgbComponents[1]);
                                ((Material)o).diffuseColor.blue = getRGBValue(rgbComponents[2]);
                                break;
                            case EMISSIVE_COLOR:
                                ((Material)o).emissiveColor.red =  getRGBValue(rgbComponents[0]);
                                ((Material)o).emissiveColor.green = getRGBValue(rgbComponents[1]);
                                ((Material)o).emissiveColor.blue = getRGBValue(rgbComponents[2]);
                                break;
                            case SPECULAR_COLOR:
                                ((Material)o).specularColor.red =  getRGBValue(rgbComponents[0]);
                                ((Material)o).specularColor.green = getRGBValue(rgbComponents[1]);
                                ((Material)o).specularColor.blue = getRGBValue(rgbComponents[2]);
                                break;
                        }
                    }
                    else if (o instanceof Fog) {
                        ((Fog)o).color.red = getRGBValue(rgbComponents[0]);
                        ((Fog)o).color.green = getRGBValue(rgbComponents[1]);
                        ((Fog)o).color.blue = getRGBValue(rgbComponents[2]);
                    }
                    else throw new ClassCastException("setRGB. Not a light or material");
                }
            });
        }

        return rgbComponents;
    }

    static float getSpinnerValue(JSpinner spinner) {
        SpinnerNumberModel m = (SpinnerNumberModel)spinner.getModel();
        float f = m.getNumber().floatValue();

        return f;
    }
}

class LightSettings extends JPanel {
    ArrayList<LightNode> light;
    int index;

    JComboBox lightType;
    JSlider ambientIntensity;
    JSlider diffuseIntensity;
    JSlider[] rgbComponents;
    JSlider cutOffAngle;
    JSlider beamWidth;
    JSpinner[] attenuation;
    JSpinner radius;

    public LightSettings (ArrayList<LightNode> l, int index) {
        light = l;
        this.index = index;

        setDiffuseIntensity();
        setSpotAngles();
        setRadius();
        setAttenuation();
        setAmbientIntensity();
        setRGB();
        setLightType();
        //setMaximumSize(new Dimension(269, 0));
        //setPreferredSize(new Dimension(269, 300));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setAlignmentX(LEFT_ALIGNMENT);
        //c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        //c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridy = 0;
        add(lightType, c);

        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        add(new JLabel("Ambient Intensity"), c);

        c.gridx = 3;
        c.gridwidth = 3;
        c.gridy = 1;
        add(new JLabel("Diffuse Intensity"), c);

        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 2;
        ambientIntensity.setMinimumSize(new Dimension(130, 25));
        add(ambientIntensity, c);

        c.gridx = 3;
        c.gridwidth = 3;
        c.gridy = 2;
        diffuseIntensity.setMinimumSize(new Dimension(130, 25));
        add(diffuseIntensity, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        add (new JLabel("Red"), c);
        c.gridx = 2;
        add (new JLabel("Green"), c);
        c.gridx = 4;
        add (new JLabel("Blue"), c);

        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        for (int i = 0; i < 3; i++) {
            rgbComponents[i].setMinimumSize(new Dimension(83, 18));
            add(rgbComponents[i], c);
            c.gridx = i * 2 + 2;
        }

        JPanel attenPanel = new JPanel();
        attenPanel.setLayout(new BoxLayout(attenPanel, BoxLayout.X_AXIS));
        attenPanel.add(new JLabel("Attenuation  "));
        for (JSpinner s : attenuation) {
            s.setMinimumSize(new Dimension(40, 20));
        }
        attenPanel.add(attenuation[0]);
        attenPanel.add(new JLabel(", "));
        attenPanel.add(attenuation[1]);
        attenPanel.add(new JLabel(", "));
        attenPanel.add(attenuation[2]);
        //attenPanel.setPreferredSize(new Dimension(210, 25));
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 6;
        add(attenPanel, c);

        c.gridy = 6;
        c.gridx = 1;
        c.gridwidth = 1;
        add(new JLabel("Radius"), c);
        c.gridx = 2;
        add(radius, c);

        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 7;
        add(new JLabel("Cut Off Angle"), c);

        c.gridx = 3;
        c.gridwidth = 3;
        c.gridy = 7;
        add(new JLabel("Beam Width"), c);

        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 8;
        cutOffAngle.setMinimumSize(new Dimension(130, 35));
        add(cutOffAngle, c);

        c.gridx = 3;
        c.gridwidth = 3;
        c.gridy = 8;
        beamWidth.setMinimumSize(new Dimension(130, 35));
        add(beamWidth, c);

    }

    private void setLightType () {
        String[] types = {"Point light", "Directional light", "Spot light", "None"};
        lightType = new JComboBox(types);
        lightType.setSelectedIndex(0);
        switchAll(true);
        beamWidth.setEnabled(false);
        cutOffAngle.setEnabled(false);
        lightType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox)e.getSource();
                String type = (String)source.getSelectedItem();
                switch (type) {
                    //TODO эффект на сцену
                    case "Point light":

                        light.set(index, new PointLight(new Color(SettingPanel.getRGBValue(rgbComponents[0]),
                                SettingPanel.getRGBValue(rgbComponents[1]), SettingPanel.getRGBValue(rgbComponents[2])),
                                ambientIntensity.getValue() / 100f, diffuseIntensity.getValue() / 100f,
                                Scene.LIGHT_POSITION.get(index), SettingPanel.getSpinnerValue(radius),
                                SettingPanel.getSpinnerValue(attenuation[0]), SettingPanel.getSpinnerValue(attenuation[1]),
                                SettingPanel.getSpinnerValue(attenuation[2])));
                        switchAll(true);
                        beamWidth.setEnabled(false);
                        cutOffAngle.setEnabled(false);

                        break;

                    case "Directional light":
                        light.set(index, new DirectionalLight(new Color(SettingPanel.getRGBValue(rgbComponents[0]),
                                SettingPanel.getRGBValue(rgbComponents[1]), SettingPanel.getRGBValue(rgbComponents[2])),
                                ambientIntensity.getValue() / 100f, diffuseIntensity.getValue() / 100f,
                                SettingPanel.getSpinnerValue(attenuation[0]), SettingPanel.getSpinnerValue(attenuation[1]),
                                SettingPanel.getSpinnerValue(attenuation[2]), Scene.DIR_LIGHT_VECTOR));
                        switchAll(true);
                        beamWidth.setEnabled(false);
                        cutOffAngle.setEnabled(false);
                        radius.setEnabled(false);
                        for (JSpinner spinner : attenuation)
                            spinner.setEnabled(false);

                        break;

                    case "Spot light":
                        Vector dir = new Vector(Scene.LIGHT_POSITION.get(index),
                                new Point(5, 0, 5));
                        dir.normalize();
                        light.set(index, new SpotLight(new Color(SettingPanel.getRGBValue(rgbComponents[0]),
                                SettingPanel.getRGBValue(rgbComponents[1]), SettingPanel.getRGBValue(rgbComponents[2])),
                                ambientIntensity.getValue() / 100f, diffuseIntensity.getValue() / 100f,
                                Scene.LIGHT_POSITION.get(index), SettingPanel.getSpinnerValue(radius),
                                SettingPanel.getSpinnerValue(attenuation[0]), SettingPanel.getSpinnerValue(attenuation[1]),
                                SettingPanel.getSpinnerValue(attenuation[2]), new Vector(),
                                (float)(cutOffAngle.getValue() * Math.PI / 50),
                                (float)(cutOffAngle.getValue() * Math.PI / 50) * beamWidth.getValue() / 100));
                        switchAll(true);

                        break;

                    case "None":
                        light.get(index).isOn = false;
                        switchAll(false);

                        break;
                }
            }
        });
    }

    private void setAmbientIntensity(){
        ambientIntensity = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        ambientIntensity.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(100), new JLabel("1"));
        //ambientIntensity.setLabelTable(labelTable);
        //ambientIntensity.setPaintTicks(true);
        //ambientIntensity.setMinorTickSpacing(10);
        //ambientIntensity.setMajorTickSpacing(50);

        ambientIntensity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                float intensity = (float)source.getValue() / 100;
                light.get(index).ambientIntensity = intensity;
            }
        });
    }

    public void setRGB() {
        rgbComponents = new JSlider[3];
        for (int i = 0; i < 3; i++) {
            rgbComponents[i] = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
            rgbComponents[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    light.get(index).color.red =  SettingPanel.getRGBValue(rgbComponents[0]);
                    light.get(index).color.green = SettingPanel.getRGBValue(rgbComponents[1]);
                    light.get(index).color.blue = SettingPanel.getRGBValue(rgbComponents[2]);
                }
            });
        }
    }

    private void setDiffuseIntensity() {
        diffuseIntensity = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        diffuseIntensity.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(100), new JLabel("1"));
        //diffuseIntensity.setLabelTable(labelTable);
        //diffuseIntensity.setPaintTicks(true);
        //diffuseIntensity.setMinorTickSpacing(10);
        //diffuseIntensity.setMajorTickSpacing(50);

        diffuseIntensity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                float intensity = (float)source.getValue() / 100;
                light.get(index).diffuseIntensity = intensity;
            }
        });
    }

    private void setAttenuation () {
        attenuation = new JSpinner[3];
        for (int i = 0; i < 3; i++) {
            attenuation[i] = new JSpinner();
            attenuation[i].setModel(new SpinnerNumberModel(0, 0, 10, 1));
            final int finalI = i;
            attenuation[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    light.get(index).attenuation[finalI] = SettingPanel.getSpinnerValue(attenuation[finalI]);
                }
            });
        }
    }

    private void setRadius () {
        radius = new JSpinner();
        radius.setModel(new SpinnerNumberModel(20, 0, 30, 0.5));
        radius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (light.get(index) instanceof SpotLight)
                    ((SpotLight)light.get(index)).radius = SettingPanel.getSpinnerValue(radius);
                if (light.get(index) instanceof PointLight)
                    ((PointLight)light.get(index)).radius = SettingPanel.getSpinnerValue(radius);
            }
        });
    }

    private void setSpotAngles () {
        beamWidth = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        cutOffAngle = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        Hashtable labelTabel1 = new Hashtable();
        Hashtable labelTabel2 = new Hashtable();
        labelTabel1.put(new Integer(0), new JLabel("0"));
        labelTabel1.put(new Integer(100), new JLabel("π/2"));
        labelTabel2.put(new Integer(0), new JLabel("0"));
        labelTabel2.put(new Integer(100), new JLabel("cutOff"));
        beamWidth.setPaintLabels(true);
        beamWidth.setLabelTable(labelTabel2);
        cutOffAngle.setPaintLabels(true);
        cutOffAngle.setLabelTable(labelTabel1);

        cutOffAngle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ((SpotLight)light.get(index)).cutOffAngle = (float)(cutOffAngle.getValue() * Math.PI / 50);
                ((SpotLight)light.get(index)).beamWidth = ((SpotLight)light.get(index)).cutOffAngle *
                        beamWidth.getValue() / 100;
            }
        });

        beamWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ((SpotLight)light.get(index)).beamWidth = ((SpotLight)light.get(index)).cutOffAngle *
                        beamWidth.getValue() / 100;
            }
        });
    }

    private void switchAll (boolean flag) {
        for (int i = 0; i < 3; i++) {
            attenuation[i].setEnabled(flag);
            rgbComponents[i].setEnabled(flag);
        }
        cutOffAngle.setEnabled(flag);
        beamWidth.setEnabled(flag);
        ambientIntensity.setEnabled(flag);
        diffuseIntensity.setEnabled(flag);
        radius.setEnabled(flag);
    }
}

class MaterialSettings extends JPanel {
    private Material material;

    private JSlider ambientIntensity;
    private JSlider shininess;
    private JSlider transparency;
    private JSlider[][] rgbComponents;

    public MaterialSettings (Figure figure) {
        //setMaximumSize(new Dimension(269, 250));
        material = figure.material;
        ambientIntensity = SettingPanel.setAmbientIntensity(material);
        rgbComponents = new JSlider[3][3];
        for (int i = 0; i < 3; i++) {
            rgbComponents[i] = SettingPanel.setRGB(material, SettingPanel.MaterialColor.values()[i]);
        }
        for (int i = 0; i < 3; i++) {
            rgbComponents[1][i].setValue(0);
            rgbComponents[2][i].setValue(0);
        }
        setShininess(material);
        setTransparency(material);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTabbedPane colors = new JTabbedPane();
        JPanel[] colorPanes = new JPanel[3];
        for (int i = 1; i < 3; i++) {
            colorPanes[i] = new JPanel();
            //colorPanes[i].setPreferredSize(new Dimension(260, 30));
            colorPanes[i].setLayout(new GridLayout(2, 3));
            colorPanes[i].setAlignmentX(CENTER_ALIGNMENT);
            colorPanes[i].add(new JLabel("Red"));
            colorPanes[i].add(new JLabel("Green"));
            colorPanes[i].add(new JLabel("Blue"));
            colorPanes[i].add(rgbComponents[i][0]);
            colorPanes[i].add(rgbComponents[i][1]);
            colorPanes[i].add(rgbComponents[i][2]);
        }
        //colors.addTab("Diffuse", colorPanes[0]);
        colors.addTab("Emissive", colorPanes[1]);
        colors.addTab("Specular", colorPanes[2]);
        colors.setBorder(BorderFactory.createTitledBorder("Color"));
        //colors.setMaximumSize(new Dimension(269, 50));

        JPanel paramPanel = new JPanel(new GridLayout(3, 2));
        paramPanel.add(new JLabel("Ambient intensity  "));
        //ambientIntensity.setPreferredSize(new Dimension(100, 30));
        paramPanel.add(ambientIntensity);

        paramPanel.add(new JLabel("Shininess  "));
        //shininess.setPreferredSize(new Dimension(100, 30));
        paramPanel.add(shininess);

        /*paramPanel.add(new JLabel("Transparency  "));
        //transparency.setPreferredSize(new Dimension(100, 30));
        paramPanel.add(transparency);
        //paramPanel.setPreferredSize(new Dimension(260, 160));
        paramPanel.setAlignmentX(CENTER_ALIGNMENT);*/

        add(colors);
        add(paramPanel);
    }

    private void setShininess (final Material m) {
        shininess = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        shininess.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(100), new JLabel("1"));
        /*shininess.setLabelTable(labelTable);
        shininess.setPaintTicks(true);
        shininess.setMinorTickSpacing(10);
        shininess.setMajorTickSpacing(50);*/

        shininess.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                float s = (float) source.getValue() / 100;
                m.shininess = s;
            }
        });
    }

    private void setTransparency(final Material m) {
        transparency = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        transparency.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(100), new JLabel("1"));
        /*transparency.setLabelTable(labelTable);
        transparency.setPaintTicks(true);
        transparency.setMinorTickSpacing(10);
        transparency.setMajorTickSpacing(50);*/

        transparency.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                float s = (float) source.getValue() / 100;
                m.transparency = s;
            }
        });
    }
}

class FogSettings extends JPanel {
    private Fog fog;

    private JCheckBox isOn;
    private JComboBox fogType;
    private JSlider[] color;
    private JSpinner visibilityRange;

    public FogSettings(Fog f) {
        fog = f;

        fogType = setFogType();
        visibilityRange = setVisibilityRange();
        color = SettingPanel.setRGB(fog, SettingPanel.MaterialColor.DIFFUSE_COLOR);
        isOn = new JCheckBox("On/Off");
        isOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOn.isSelected()) {
                    fog.enabled = true;
                }
                else fog.enabled = false;
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel fogTypePanel = new JPanel();
        //fogType.setMaximumSize(new Dimension(267, 10));
        fogTypePanel.add(isOn);
        fogTypePanel.add(new JLabel("    Fog type"));
        fogTypePanel.add(fogType);
        //fogTypePanel.setMaximumSize(new Dimension(267, 10));
        add(fogTypePanel);

        JPanel colorPanel = new JPanel(new GridLayout(2, 3));
        colorPanel.setBorder(new TitledBorder("Fog color"));
        JLabel red = new JLabel("Red");
        red.setAlignmentX(CENTER_ALIGNMENT);
        colorPanel.add(new JLabel("Red"));
        colorPanel.add(new JLabel("Green"));
        colorPanel.add(new JLabel("Blue"));
        for (JSlider slider: color) {
            //slider.setMaximumSize(new Dimension(80, 25));
            colorPanel.add(slider);
        }
        colorPanel.setMaximumSize(new Dimension(260, 40));
        add(colorPanel);

        JPanel rangePanel = new JPanel();
        rangePanel.setLayout(new BoxLayout(rangePanel, BoxLayout.X_AXIS));
        visibilityRange.setMaximumSize(new Dimension(40, 20));
        rangePanel.add(new JLabel("Visibility range"));
        rangePanel.add(visibilityRange);
        rangePanel.setMaximumSize(new Dimension(260, 20));
        add(rangePanel);

        //setPreferredSize(new Dimension(269, 160));
    }

    private JComboBox setFogType () {
        final JComboBox b = new JComboBox(new String[]{"Exponential", "Linear"});
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b.getSelectedIndex() == 0) {
                    fog.isLinear = false;
                }
                else {
                    fog.isLinear = true;
                }
            }
        });

        return b;
    }

    private JSpinner setVisibilityRange () {
        final JSpinner radius = new JSpinner();
        radius.setModel(new SpinnerNumberModel(10, 0, 30, 0.5));
        radius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                 fog.fogVisibility = SettingPanel.getSpinnerValue(radius);
            }
        });

        return radius;
    }
}


