package Project1;

import java.awt.image.BufferedImage;
/* Vincent Testagrossa
 * Project 1 - Java 2D Graphics
 * 
 * This class defines a RightTriangle object, which allows for group modification. It has individual translation and offset integers, as well as fields for scale and
 * rotation. I would make a base class for an image and extend this one from it with more time to take advantage of polymorphism and define the arrayList in the 
 * Project1 class as ArrayList<Image>, so that different images could be grouped together.
 * 
*/

public class RightTriangle {

    //Basic requirements for an image for the Project1 class
    private ImageTemplate t1 = new ImageTemplate();
    private BufferedImage image;
    private int xOffset, yOffset, translateX, translateY;
    private double rotation, rotationOffset, xScale, yScale;

    //Overloaded constructor with default values, or the ability to choose each
    public RightTriangle(){
        this(ImageTemplate.redTriangle, 0, 0, 0, 0, 0.0, 1.0, 1.0);
    }
    public RightTriangle(int[][] image,int offsetX,int offsetY,int tX,int tY, double rotOffset, double xS, double yS){
        this.image = t1.getImage(image);
        xOffset = offsetX;
        yOffset = offsetY;
        translateX = tX;
        translateY = tY;
        rotationOffset = rotOffset; //RotationOffset allows the images to be constructed already rotated.
        xScale = xS;
        yScale = yS;
    }

    // All of the setters
    public void setTranslateX(int x){
        translateX = x;
    }
    public void setTranslateY(int y){
        translateY = y;
    }
    public void setRotation(double rot){
        rotation = (rot + rotationOffset) * Math.PI / 180; // Absolute rotation with the initial offset.
    }
    public void setXScale(double xS){
        xScale = xS;
    }
    public void setYScale(double yS){
        yScale = yS;
    }

    //getters include offsets (and the image) that are only defined in the constructor
    public int getTranslateX(){
        return translateX;
    }
    public int getTranslateY(){
        return translateY;
    }
    public int getXOffset(){
        return xOffset;
    }
    public int getYOffset(){
        return yOffset;
    }
    public double getRotation(){
        return rotation;
    }
    public double getXScale(){
        return xScale;
    }
    public double getYScale(){
        return yScale;
    }
    public BufferedImage getImage(){
        return image;
    }
}
