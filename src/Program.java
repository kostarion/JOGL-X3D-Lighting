import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: kost
 * Date: 03.05.14
 * Time: 3:06
 */
public class Program {
    private static GraphicsEnvironment graphicsEnviorment;
    private static boolean isFullScreen = false;
    public static DisplayMode dm, dm_old;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene.setScene();

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setPreferredSize(new Dimension(840, 590));
                frame.setSize(840, 590);
                frame.setTitle("X3D Lighting");


                JPanel panel = new JPanel();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                panel.setLayout(new BorderLayout());


                final GLProfile profile = GLProfile.get(GLProfile.GL2);
                GLCapabilities capabilities = new GLCapabilities(profile);

                // The canvas
                final GLJPanel glPanel = new GLJPanel(capabilities);
                glPanel.setPreferredSize(new Dimension(500, 400));
                X3D_GLListener r = new X3D_GLListener(new Scene());
                glPanel.addGLEventListener(r);
                //glPanel.setSize(600, 400);

                final FPSAnimator animator = new FPSAnimator(glPanel, 300,true );


                panel.add(glPanel, BorderLayout.CENTER);
                panel.add(new SettingPanel(), BorderLayout.EAST);

                //Shutdown
                frame.addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        if(animator.isStarted())
                            animator.stop();
                        System.exit(0);
                    }
                });


                /**
                 * Centers the screen on start up
                 *
                 */
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                int windowX = Math.max(0, (screenSize.width - frame.getWidth()) / 2);
                int windowY = Math.max(0, (screenSize.height - frame.getHeight()) / 2);

                frame.setLocation(windowX, windowY);
                frame.add(panel);

                frame.setVisible(true);
                frame.pack();
                animator.start();
            }
        });
    }
}

class MainFrame extends JFrame {


    public MainFrame (Scene scene) {

    }
}
