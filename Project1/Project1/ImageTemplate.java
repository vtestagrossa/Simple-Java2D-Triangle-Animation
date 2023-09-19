package Project1;

import java.awt.Color;
import java.awt.image.BufferedImage;

/* Vincent Testagrossa
 * Project 1 - Java 2D Graphics
 * 
 * This class provides the methods for creating buffered images from a 2D int array. The triangleWithBorder image is a (very) simple
 * right triangle and has options for a borderColor, a fillColor, and size. The getImage method is modified from the original 
 * ImageTemplate class and provides the bufferedImage. It scans the 2D array and converts each element to an RGB color within a
 * switch statement, with 8 options (default is black);
 * 
*/
public class ImageTemplate {

    
    

    // I left this intact for the reference when I was building images by hand. I decided I wanted to generate right triangles
    // in a loop instead.
    public ImageTemplate(){
    }
    
    // Define a Simple Images based on the Alphabet
    // Use static so can send them into the getImage() method remotely
    // Note these are 10x10 and can be modified to any desired
    // image size by adding or removing rows and columns
   public static int[][] letterT = {
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};

    
    /* Generates a (jagged-edged) triangle matrix with a 1px wide border. borderColor and fillColor are able to be
     * selected via an integer value. The method that generates the image applies the color values
     */
    private static int[][] generateTriangleWithBorder(int size, int borderColor, int fillColor){
        int[][] triangleWithBorder = new int[size][size];
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                if ((y == 0 || y == x || x == size - 1)){
                    // The right triangle is aligned with the right angle at the bottom left. The first
                    // column will be all border (y == 0), and with the slope being -1/1 means that when x and y are equal,
                    // the loop is at a border pixel for the hypotenuse. The last row will be entirely border pixels.
                    triangleWithBorder[x][y] = borderColor;
                }
                else if (y > 0 && y < x){
                    // The column is one that is between the borders.
                    triangleWithBorder[x][y] = fillColor;
                }
                else{
                    // The current loop element is outside of the triangle.
                    triangleWithBorder[x][y] = 0;
                }
            }
        }
        return triangleWithBorder;
    }

    // Three different colored triangles with black borders.
    public static int[][] blueTriangle = generateTriangleWithBorder(25, 7, 5);
    public static int[][] redTriangle = generateTriangleWithBorder(30, 7, 1);
    public static int[][] greenTriangle = generateTriangleWithBorder(40, 7, 4);

    
    //Modified to set the loop bounds based on the size of the array, so that the image size can be defined in the image.
    public BufferedImage getImage(int[][] data) {
        int IMGSIZEX = data.length;
        int IMGSIZEY = data[0].length;
        BufferedImage image = new BufferedImage(IMGSIZEX, IMGSIZEY,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < IMGSIZEX; x++) {
            for (int y = 0; y < IMGSIZEY; y++) {
                int pixelColor = data[x][y];
                // Set Colors based on Binary Image value
                switch(pixelColor){
                    //Expanded color pallette.
                    case 0:
                        pixelColor = Color.WHITE.getRGB();
                        break;
                    case 1:
                        pixelColor = Color.RED.getRGB();
                        break;
                    case 2:
                        pixelColor = Color.ORANGE.getRGB();
                        break;
                    case 3:
                        pixelColor = Color.YELLOW.getRGB();
                        break;
                    case 4:
                        pixelColor = Color.GREEN.getRGB();
                        break;
                    case 5:
                        pixelColor = Color.BLUE.getRGB();
                        break;
                    case 6:
                        pixelColor = Color.blue.getRGB();
                        break;
                    default:
                        pixelColor = Color.BLACK.getRGB();
                        break;
                }
                image.setRGB(x, y, pixelColor);
            } // End for y.
        } // End for x.
        return image;
    } // End getData method.

}
