package Project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Vincent Testagrossa
 * Project 1 - Java 2D Graphics
 * 
 * This class uses the CMSC405P1Template and animates the transforms for 3 images which are provided by the ImageTemplate class.
 * The images are placed at (15, -25), (-40, -25), and (75, -25), then translated to the left by 5px, up 7px, rotated counter clockwise
 * by 45 degrees, rpotated clockwise by 90 degrees, then scaled by 2.0 in the x plane and then 0.5 in the y plane. 
 * 
 * The transforms are done on a frame-by-frame basis, with the delay being determined by the int frameRate and the current transform
 * is handled by a switch statement and the frameNumber int. Translation, rotation and scaling are modified within the switch and 
 * the draw operations for each triangle image are handled after.
 */


public class Project1 extends JPanel{

    private int frameNumber;
        
    static int frameRate = 3;

    static ArrayList<RightTriangle> imageGroup = new ArrayList<RightTriangle>();
    //stores a list of images to apply transforms

    public static void main(String[] args){
        //add the three triangle images to the list with their respective offsets, translations, rotations and scales.
        imageGroup.add(new RightTriangle(ImageTemplate.redTriangle, 15, -25, 0, 0, 135, 1.0, 1.0));
        imageGroup.add(new RightTriangle(ImageTemplate.blueTriangle, -40, -25, 0, 0, 135, 1.0, 1.0));
        imageGroup.add(new RightTriangle(ImageTemplate.greenTriangle, 75, -25, 0, 0, 135, 1.0, 1.0));

        JFrame window;
        window = new JFrame("Java 2D Graphics");
        final Project1 panel = new Project1();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
            (screen.width - window.getWidth()) / 2,
            (screen.height - window.getHeight()) /2);
        
        
        Timer animationTimer;

        //Redraws the screen at the desired framerate.
        animationTimer = new Timer((int)(1000/frameRate), new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                /* With 7 frames of animation (0 - 6), this section causes the scene to repeat.
                 * The (rough) framerate is determined by modifying the frameRate value.
                 */
                if (panel.frameNumber >= 7) {
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                //repaints the panel after the appropriate delay
                panel.repaint();
            }
        });
        window.setVisible(true);
        animationTimer.start();
    }

    public Project1() {
        setPreferredSize(new Dimension(800, 600));
    }

    /* Sets up the canvas, sets the x/y translation, scaling, and rotation per frame, and draws the three images with the appropriate
     * offsets. Gets called with panel.repaint();
     */
    protected void paintComponent(Graphics g){
        //Draws the background the images will be drawn on
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

        // Used to reset reference coords after transforms are applied. If it's not called after each draw operation, 
        // The next images will become distorted.
        AffineTransform savedTransform = g2.getTransform();

        /* Modified from the template: This section provides the translations, scaling, and rotation for each frame of animation.
         * Each operation happens in it's own "frame" so that they can be distinctly observed.
         */
        switch (frameNumber){
            case 0:
                setTransform(0, 0, 0, 1.0, 1.0); // All shapes drawn in their regular positions, scalings and rotations
                break;
            case 1:
                setTransform(-5, 0, 0, 1.0, 1.0); // Translate all shapes by -5 in the X direction
                break;
            case 2:
                setTransform(-5, 7, 0, 1.0, 1.0); // Translate all shapes by 7 in the Y direction
                break;
            case 3:
                setTransform(-5, 7, 45, 1.0, 1.0); // Rotate all shapes counter clockwise 45 degrees
                break;
            case 4:
                setTransform(-5, 7, -45, 1.0, 1.0); // Rotate all shapes to the clockwise 90 degrees (45 - 90 = -45)
                break;
            case 5:
                setTransform(-5, 7, -45, 2.0, 1.0); // Scale by 2X in the X direction
                break;
            case 6:
                //scale down by 0.5 in the y direction.
                setTransform(-5, 7, -45, 2.0, 0.5);
                break;
            case 7:
                //added to show there are 8 frames. I just wanted an even number.
                break;
            default:
                break;
        }
        /* Each image must be translated(with their offsets added), rotated, and then scaled
         * to prevent the image from distorting in unexpected ways when drawImage is finally called. The savedTransform
         * is then applied to g2 to reset the reference position and the next image is transformed and drawn in the same way.
         */

        //Draw all images in the list
        for (RightTriangle image : imageGroup){
            // adds the translate X and Y coords to the corresponding offsets
            g2.translate(
                        image.getTranslateX() + image.getXOffset(), 
                        image.getTranslateY() + image.getYOffset()
                        );
            g2.rotate(image.getRotation());
            g2.scale(image.getXScale(), image.getYScale());
            g2.drawImage(image.getImage(), 0, 0, this);
            g2.setTransform(savedTransform);
        }

    }

    // transforms all the images added to the list. Effectively groups images together.
    private void setTransform(int x, int y, double rot, double sX, double sY){
        for (RightTriangle image : imageGroup){
            image.setTranslateX(x);
            image.setTranslateY(y);
            image.setRotation(rot);
            image.setXScale(sX);
            image.setYScale(sY);
        }
    }

    // Method taken directly from AnimationStarter.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        //double pixelWidth = Math.abs((right - left) / width);
        //double pixelHeight = Math.abs((bottom - top) / height);
        //pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
}
