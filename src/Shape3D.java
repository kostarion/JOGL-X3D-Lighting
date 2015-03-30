import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Beta
 *
 */
public class Shape3D implements GLEventListener{

    private static GraphicsEnvironment graphicsEnviorment;
    private static boolean isFullScreen = false;
    public static DisplayMode dm, dm_old;
    private static Dimension xgraphic;
    //private static Point point = new Point(0, 0);
    private static float[][][] floor = new float[10][10][3];
    private static float[] floorEmissiveColor = {0.5f, 0.1f, 0.1f};
    private Scene scene;


    private GLU glu = new GLU();

    private float rquad=0.0f, rtri =0.0f;

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        //glu.gluLookAt(scene.viewPoint.x, scene.viewPoint.y, scene.viewPoint.z,
              //  0, 0, 0, 0, 1, 0);

        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        //scene.renderScene(gl);

        gl.glBegin(GL2.GL_TRIANGLES); // draw using triangles
        gl.glVertex3f(1.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        gl.glEnd();

        gl.glFlush();
    }

    /*@Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        float[] diffuse_light = {0.2f, 0.f, 0.2f, 1f};
        float[] ambient_light = {0.8f, 0.7f, 0.8f, 1f};
        float[] specular_light = {0.8f,1f,1f,1f};
        float[] light_pos = {-5f, 5f, 5f, 1f};
        float[] material1 = {1.0f, 0.5f, 0.1f, 1f};
        float[] material2 = {0.7f, 0.2f, 0.1f, 1f};
        float[] mat_shininess = {30f};

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0.1f, 0.1f, 0.5f}, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular_light, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse_light, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient_light, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_pos, 0);


        GLU glu = GLU.createGLU(gl);
        GLUT glut = new GLUT();

        glu.gluLookAt(10.0f * Math.cos(rtri), 2.0f, -10.0f * Math.sin(rtri), 3.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);


        drawFloor(gl);

        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, material1, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mat_shininess, 0);
        //gl.glColor3f(0.8f, 0.0f, 0.2f);
        gl.glColor4f(0.5f, 0.1f, 0.5f, 0.2f);

        gl.glTranslatef(0f, 0.2f, 0f);
        glut.glutSolidTorus(0.5, 0.7, 60, 25);



        gl.glColor3f(0.5f, 1.0f, 0.0f);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, material2, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, new float[]{100f}, 0);

        gl.glTranslatef(-3f, -0.4f, 0.0f);
        glut.glutSolidTeapot(1);

        //gl.glTranslatef(-1.5f, 0.0f,-6.0f);
        // Move Left 1.5 Units And Into The Screen 6.0

        //gl.glRotatef(2*rtri, 0.0f, 1.0f, 0.0f);//(NEW!)

        gl.glFlush();
        rtri +=0.002f;
        rquad -=0.15f;
    }*/

    private void drawTriangle(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);                      // Drawing Using Triangl.gles
		    /*
			 * Front
			 */
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Front)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Left Of Triangle (Front)
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Right Of Triangle (Front)
			/*
			 * Right
			 */
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Right)
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Left Of Triangle (Right)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Right Of Triangle (Right)
			/*
			 * Left
			 */
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Back)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Left Of Triangle (Back)
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
			/*
			 *
			 */
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Left)
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Right Of Triangle (Left)

        gl.glEnd(); // Done Drawing The Pyramid

        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Right Of Triangle (Left)
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Left Of Triangle (Right)
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Right Of Triangle (Right)

        gl.glEnd();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube

        gl.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)

        gl.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Bottom)
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Bottom)

        gl.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)

        gl.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Back)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)

        gl.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)

        gl.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
        gl.glEnd(); // Done Drawing The Quad
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub


    }

    void drawFloor (GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 1, 0);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i+j) % 2 == 0)
                    gl.glColor3f(1, 1, 1);
                else
                    gl.glColor3f(0, 0, 0);

                gl.glVertex3fv(floor[i][j], 0);
                gl.glVertex3fv(floor[i][j + 1], 0);
                gl.glVertex3fv(floor[i + 1][j + 1], 0);
                gl.glVertex3fv(floor[i + 1][j], 0);
            }
        }

        gl.glEnd();
    }

    void setFloor() {
        for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j++) {
                floor [i][j] = new float[]{i - 5, -1f, j - 5};
            }
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        //gl.glEnable(GL2.GL_LIGHTING);
        //gl.glEnable(GL2.GL_LIGHT0);
        //gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        scene = new Scene();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();

        if(height <=0)
            height =1;
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // setUp open GL version 2
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Shape3D r = new Shape3D();
        glcanvas.addGLEventListener(r);
        glcanvas.setSize(400, 400);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true );

        final JFrame frame = new JFrame ("nehe: Lesson 5");

        frame.getContentPane().add(glcanvas);

        //Shutdown
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(animator.isStarted())
                    animator.stop();
                System.exit(0);
            }
        });

        frame.setSize(frame.getContentPane().getPreferredSize());
        /**
         * Centers the screen on start up
         *
         */
        graphicsEnviorment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = graphicsEnviorment.getScreenDevices();

        dm_old = devices[0].getDisplayMode();
        dm = dm_old;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int windowX = Math.max(0, (screenSize.width - frame.getWidth()) / 2);
        int windowY = Math.max(0, (screenSize.height - frame.getHeight()) / 2);

        frame.setLocation(windowX, windowY);
        /**
         *
         */
        frame.setVisible(true);
		/*
		 * Time to add Button Control
		 */
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(0,0));
        frame.add(p, BorderLayout.SOUTH);

        keyBindings(p, frame, r);
        animator.start();
    }

    private static void keyBindings(JPanel p, final JFrame frame, final Shape3D r) {

        ActionMap actionMap = p.getActionMap();
        InputMap inputMap = p.getInputMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
        actionMap.put("F1", new AbstractAction(){

            /**
             *
             */
            private static final long serialVersionUID = -6576101918414437189L;

            @Override
            public void actionPerformed(ActionEvent drawable) {
                // TODO Auto-generated method stub
                fullScreen(frame);
            }});
    }

    protected static void fullScreen(JFrame f) {
        // TODO Auto-generated method stub
        if(!isFullScreen){
            f.dispose();
            f.setUndecorated(true);
            f.setVisible(true);
            f.setResizable(false);
            xgraphic = f.getSize();
            //point = f.getLocation();
            f.setLocation(0, 0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
            isFullScreen=true;
        }else{
            f.dispose();
            f.setUndecorated(false);
            f.setResizable(true);
            //f.setLocation(point);
            f.setSize(xgraphic);
            f.setVisible(true);

            isFullScreen =false;
        }
    }


}
