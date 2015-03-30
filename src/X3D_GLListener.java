import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * User: kost
 * Date: 06.05.14
 * Time: 4:38
 */
public class X3D_GLListener implements GLEventListener{
    Scene scene;
    private GLU glu;
    private float rtri = 0.0f;
    private static boolean isMoving = true;

    public X3D_GLListener(Scene scene) {
        this.scene = scene;
        glu = new GLU();
    }

    static void stopMoving () {
        isMoving = false;
    }

    static void startMoving () {
        isMoving = true;
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
    public void dispose(GLAutoDrawable drawable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();


        Scene.viewPoint.x = 13.0f * (float)Math.cos(rtri);
        Scene.viewPoint.z = -13.0f * (float)Math.sin(rtri);
        glu.gluLookAt(Scene.viewPoint.x, 3.0f, Scene.viewPoint.z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
                0.0f);

//        ((PointLight)Scene.light.get(0)).position = new Point(15.0f * (float)Math.cos(rtri), 5.0f,
       //         -15.0f * (float)Math.sin(rtri));
        //gl.glTranslatef(0.0f, 0.0f, -20.0f);
        scene.renderScene(gl);

        gl.glFlush();

        if (isMoving)
            rtri += 0.005f;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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
}
