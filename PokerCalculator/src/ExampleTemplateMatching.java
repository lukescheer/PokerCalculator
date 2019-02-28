/**
 * Created by lcs_5 on 2/7/2018.
 */

//package boofcv.examples.features;

import boofcv.alg.feature.detect.template.TemplateMatching;
import boofcv.alg.feature.detect.template.TemplateMatchingIntensity;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.factory.feature.detect.template.FactoryTemplateMatching;
import boofcv.factory.feature.detect.template.TemplateScoreType;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.feature.Match;
import boofcv.struct.image.GrayF32;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Robot;

import static java.awt.MouseInfo.getPointerInfo;

/**
 * Example of how to find objects inside an image using template matching.  Template matching works
 * well when there is little noise in the image and the object's appearance is known and static.  It can
 * also be very slow to compute, depending on the image and template size.
 * @author Peter Abeles
 *
 * Some code was borrowed from Peter Abeles' example template matching  -Luke Scheer
 */
public class ExampleTemplateMatching {


    public static final String DIRECTORY = "C:\\Users\\Luke\\Documents\\PokerCalculator\\images";
    public static final String DIRECTORY2 =  "C:\\Users\\Luke\\Documents\\PokerCalculator";




    /**
     * Demonstrates how to search for matches of a template inside an image
     *
     * @param image           Image being searched
     * @param template        Template being looked for
     * @param mask            Mask which determines the weight of each template pixel in the match score
     * @param expectedMatches Number of expected matches it hopes to find
     * @return List of match location and scores
     */
    private static List<Match> findMatches(GrayF32 image, GrayF32 template, GrayF32 mask,
                                           int expectedMatches) {
        // create template matcher.
        TemplateMatching<GrayF32> matcher =
                FactoryTemplateMatching.createMatcher(TemplateScoreType.SUM_DIFF_SQ, GrayF32.class);

        // Find the points which match the template the best
        matcher.setImage(image);
        matcher.setTemplate(template, mask, expectedMatches);
        matcher.process();

        return matcher.getResults().toList();

    }

    /**
     * Computes the template match intensity image and displays the results. Brighter intensity indicates
     * a better match to the template.
     */
    public static void showMatchIntensity(GrayF32 image, GrayF32 template, GrayF32 mask) {

        // create algorithm for computing intensity image
        TemplateMatchingIntensity<GrayF32> matchIntensity =
                FactoryTemplateMatching.createIntensity(TemplateScoreType.SUM_DIFF_SQ, GrayF32.class);

        // apply the template to the image
        matchIntensity.setInputImage(image);
        matchIntensity.process(template, mask);

        // get the results
        GrayF32 intensity = matchIntensity.getIntensity();

        // adjust the intensity image so that white indicates a good match and black a poor match
        // the scale is kept linear to highlight how ambiguous the solution is
        float min = ImageStatistics.min(intensity);
        float max = ImageStatistics.max(intensity);
        float range = max - min;
        PixelMath.plus(intensity, -min, intensity);
        PixelMath.divide(intensity, range, intensity);
        PixelMath.multiply(intensity, 255.0f, intensity);

        BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);
        VisualizeImageData.grayMagnitude(intensity, output, -1);
        ShowImages.showWindow(output, "Match Intensity", true);
    }

    public static void takeScreenshot(String fileName, int x, int y, int x2, int y2) {
        try {
            Robot robot = new Robot();
            String format = "png";
            //fileName = "PartialScreenshot." + format;

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureRect = new Rectangle(x, y, x2 - x, y2 - y);
            BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
            ImageIO.write(screenFullImage, format, new File(fileName));

            //System.out.println("A partial screenshot saved!");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    public static void click() {
        int x1 = 400;
        int y1 = 20;
        int x2 = 1501;
        int y2 = 385;

        try {
            Robot bot = new Robot();
            bot.delay(2000);
            Point p = MouseInfo.getPointerInfo().getLocation();
            bot.mouseMove(p.x+1016, p.y+397);
            bot.delay(50);


        } catch (AWTException e) {
            e.printStackTrace();
        }


   /**     Thread.sleep(50);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(50);
        bot.mouseMove(x2, y2);
        Thread.sleep(50);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
    **/
    }

    public static void main(String args[]) {

        // Load image and templates
        //String DIRECTORY = UtilIO.pathExample("template");
        //String DIRECTORY = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator\\images";
        //String DIRECTORY2 = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator";

        String[] boardcards = {"boardcard1.png", "boardcard2.png", "boardcard3.png"};
        for (int i = 0; i < 3; i++) {
            takeScreenshot(boardcards[i], 1396 + 50 * i, 651, 1410 + 50 * i, 681);
        }

        takeScreenshot("holecard1.png", 1475, 813, 1475 + 15, 813 + 30);
        takeScreenshot("holecard2.png", 1519, 813, 1519 + 15, 813 + 30);


        GrayF32 image = UtilImageIO.loadImage(DIRECTORY, "allcards.png", GrayF32.class);
        GrayF32 templateChrome = UtilImageIO.loadImage(DIRECTORY, "3d.png", GrayF32.class);

        GrayF32 templateBoard1 = UtilImageIO.loadImage(DIRECTORY2, "boardcard1.png", GrayF32.class);
        GrayF32 templateBoard2 = UtilImageIO.loadImage(DIRECTORY2, "boardcard2.png", GrayF32.class);
        GrayF32 templateBoard3 = UtilImageIO.loadImage(DIRECTORY2, "boardcard3.png", GrayF32.class);

        GrayF32 templateHole1 = UtilImageIO.loadImage(DIRECTORY2, "holecard1.png", GrayF32.class);
        GrayF32 templateHole2 = UtilImageIO.loadImage(DIRECTORY2, "holecard2.png", GrayF32.class);


        // create output image to show results
        BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);
        ConvertBufferedImage.convertTo(image, output);
        Graphics2D g2 = output.createGraphics();

        // Search for the cursor in the image.  For demonstration purposes it has been pasted 3 times
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));
        int[] board2Location = drawRectangles(g2, image, templateBoard2, null, 1);
        // show match intensity image for this template
        showMatchIntensity(image, templateChrome, null);

        // Now it's try finding the cursor without a mask.  it will get confused when the background is black
        int[] board1Location = drawRectangles(g2, image, templateBoard1, null, 1);

        // Now it searches for a specific icon for which there is only one match
        int[] board3Location = drawRectangles(g2, image, templateBoard3, null, 1);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        int[] hole1Location = drawRectangles(g2, image, templateHole1, null, 1);
        int[] hole2Location = drawRectangles(g2, image, templateHole2, null, 1);


        String board = getCell(board1Location);
        board = board + getCell(board2Location);
        board = board + getCell(board3Location);
        String hole = getCell(hole1Location);
        hole = hole + getCell(hole2Location);


        System.out.println(board);

        String[] pott = getPot();
        System.out.println(pott[0]);
        System.out.println(pott[1]);

        //ShowImages.showWindow(output, "Found Matches",true);
    }

    private static String getCell(int[] xy) {
        String result;
        if (xy[1] < 20) {
            result = "a";
        } else if (xy[1] < 56) {
            result = "k";
        } else if (xy[1] < 92) {
            result = "q";
        } else if (xy[1] < 130) {
            result = "j";
        } else if (xy[1] < 163) {
            result = "t";
        } else if (xy[1] < 200) {
            result = "9";
        } else if (xy[1] < 235) {
            result = "8";
        } else if (xy[1] < 270) {
            result = "7";
        } else if (xy[1] < 305) {
            result = "6";
        } else if (xy[1] < 348) {
            result = "5";
        } else if (xy[1] < 383) {
            result = "4";
        } else if (xy[1] < 420) {
            result = "3";
        } else {
            result = "2";
        }

        if (xy[0] < 11) {
            return (result + "s");
        } else if (xy[0] < 30) {
            return (result + "d");
        } else if (xy[0] < 49) {
            return (result + "h");
        } else {
            return (result + "c");
        }

    }

    /**
     * Helper function will is finds matches and displays the results as colored rectangles
     */
    private static int[] drawRectangles(Graphics2D g2,
                                        GrayF32 image, GrayF32 template, GrayF32 mask,
                                        int expectedMatches) {
        List<Match> found = findMatches(image, template, mask, expectedMatches);

        int r = 2;
        int w = template.width + 2 * r;
        int h = template.height + 2 * r;

        int[] result = {0, 0};


        for (Match m : found) {
            System.out.println("Match " + m.x + " " + m.y + "    score " + m.score);
            // this demonstrates how to filter out false positives
            // the meaning of score will depend on the template technique
//			if( m.score < -1000 )  // This line is commented out for demonstration purposes
//				continue;

            // the return point is the template's top left corner
            result[0] = m.x;
            result[1] = m.y;

            int x0 = m.x - r;
            int y0 = m.y - r;
            int x1 = x0 + w;
            int y1 = y0 + h;

            g2.drawLine(x0, y0, x1, y0);
            g2.drawLine(x1, y0, x1, y1);
            g2.drawLine(x1, y1, x0, y1);
            g2.drawLine(x0, y1, x0, y0);


        }
        return result;
    }


    public static String[] getFlop() {

        // Load image and templates
        //String DIRECTORY = UtilIO.pathExample("template");
        //String DIRECTORY = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator\\images";
        //String DIRECTORY2 = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator";

        int x1 = 1396;
        int y1 = 671;
        int x2 = 50;   //x2 here is the x distance between board cards
        int cardWidth = 14;
        int cardHeight = 30;

        String[] boardcards = {"boardcard1.png", "boardcard2.png", "boardcard3.png"};
        for (int i = 0; i < 3; i++) {
            takeScreenshot(boardcards[i], x1 + x2 * i, y1, x1 + cardWidth + x2 * i, y1 + cardHeight);
        }
        x1 = 1474;
        y1 = 830;
        x2 = 1519;
        cardWidth = 15;
        cardHeight = 30;
        takeScreenshot("holecard1.png", x1, y1, x1 + cardWidth, y1 + cardHeight);
        takeScreenshot("holecard2.png", x2, y1, x2 + cardWidth, y1 + cardHeight);

        GrayF32 image = UtilImageIO.loadImage(DIRECTORY, "allcards.png", GrayF32.class);
        GrayF32 templateChrome = UtilImageIO.loadImage(DIRECTORY, "3d.png", GrayF32.class);

        GrayF32 templateBoard1 = UtilImageIO.loadImage(DIRECTORY2, "boardcard1.png", GrayF32.class);
        GrayF32 templateBoard2 = UtilImageIO.loadImage(DIRECTORY2, "boardcard2.png", GrayF32.class);
        GrayF32 templateBoard3 = UtilImageIO.loadImage(DIRECTORY2, "boardcard3.png", GrayF32.class);

        GrayF32 templateHole1 = UtilImageIO.loadImage(DIRECTORY2, "holecard1.png", GrayF32.class);
        GrayF32 templateHole2 = UtilImageIO.loadImage(DIRECTORY2, "holecard2.png", GrayF32.class);


        // create output image to show results
        BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);
        ConvertBufferedImage.convertTo(image, output);
        Graphics2D g2 = output.createGraphics();

        // Search for the cursor in the image.  For demonstration purposes it has been pasted 3 times
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));
        int[] board2Location = drawRectangles(g2, image, templateBoard2, null, 1);
        // show match intensity image for this template
        //showMatchIntensity(image, templateChrome, null);

        // Now it's try finding the cursor without a mask.  it will get confused when the background is black
        int[] board1Location = drawRectangles(g2, image, templateBoard1, null, 1);

        // Now it searches for a specific icon for which there is only one match
        int[] board3Location = drawRectangles(g2, image, templateBoard3, null, 1);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        int[] hole1Location = drawRectangles(g2, image, templateHole1, null, 1);
        int[] hole2Location = drawRectangles(g2, image, templateHole2, null, 1);


        String board = getCell(board1Location);
        board = board + getCell(board2Location);
        board = board + getCell(board3Location);
        String hole = getCell(hole1Location);
        hole = hole + getCell(hole2Location);


        System.out.println(board);
        //ShowImages.showWindow(output, "Found Matches",true);
        String[] result = {board, hole};

        return result;
    }

    public static String[] getTurn(Boolean turn) {

        // Load image and templates
        //String DIRECTORY = UtilIO.pathExample("template");
        //String DIRECTORY = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator\\images";
        //String DIRECTORY2 = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator";

        int x1 = 1396;
        int y1 = 671;
        int x2 = 50;   //x2 here is the x distance between board cards
        int cardWidth = 14;
        int cardHeight = 30;

        String[] boardcards = {"boardcard1.png", "boardcard2.png", "boardcard3.png", "boardcard4.png"};
        for (int i = 0; i < 3; i++) {
            takeScreenshot(boardcards[i], x1 + x2 * i, y1, x1 + cardWidth + x2 * i, y1 + cardHeight);
        }
        x1 = 1474;
        y1 = 830;
        x2 = 1519;
        cardWidth = 15;
        cardHeight = 30;
        takeScreenshot("holecard1.png", x1, y1, x1 + cardWidth, y1 + cardHeight);
        takeScreenshot("holecard2.png", x2, y1, x2 + cardWidth, y1 + cardHeight);


        GrayF32 image = UtilImageIO.loadImage(DIRECTORY, "allcards.png", GrayF32.class);
        GrayF32 templateChrome = UtilImageIO.loadImage(DIRECTORY, "3d.png", GrayF32.class);


        GrayF32 templateBoard1 = UtilImageIO.loadImage(DIRECTORY2, "boardcard1.png", GrayF32.class);
        GrayF32 templateBoard2 = UtilImageIO.loadImage(DIRECTORY2, "boardcard2.png", GrayF32.class);
        GrayF32 templateBoard3 = UtilImageIO.loadImage(DIRECTORY2, "boardcard3.png", GrayF32.class);


        GrayF32 templateHole1 = UtilImageIO.loadImage(DIRECTORY2, "holecard1.png", GrayF32.class);
        GrayF32 templateHole2 = UtilImageIO.loadImage(DIRECTORY2, "holecard2.png", GrayF32.class);


        // create output image to show results
        BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_BGR);
        ConvertBufferedImage.convertTo(image, output);
        Graphics2D g2 = output.createGraphics();

        // Search for the cursor in the image.  For demonstration purposes it has been pasted 3 times
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));
        int[] board2Location = drawRectangles(g2, image, templateBoard2, null, 1);
        // show match intensity image for this template
        //showMatchIntensity(image, templateChrome, null);

        // Now it's try finding the cursor without a mask.  it will get confused when the background is black
        int[] board1Location = drawRectangles(g2, image, templateBoard1, null, 1);

        // Now it searches for a specific icon for which there is only one match
        int[] board3Location = drawRectangles(g2, image, templateBoard3, null, 1);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        int[] hole1Location = drawRectangles(g2, image, templateHole1, null, 1);
        int[] hole2Location = drawRectangles(g2, image, templateHole2, null, 1);


        String board = getCell(board1Location);
        board = board + getCell(board2Location);
        board = board + getCell(board3Location);
        String hole = getCell(hole1Location);
        hole = hole + getCell(hole2Location);

        if (turn) {
            int i = 3;
            takeScreenshot(boardcards[i], x1 + x2 * i, y1, x1 + cardWidth + x2 * i, y1 + cardHeight);
            GrayF32 templateBoard4 = UtilImageIO.loadImage(DIRECTORY2, "boardcard4.png", GrayF32.class);
            int[] board4Location = drawRectangles(g2, image, templateBoard4, null, 1);
            board = board + getCell(board4Location);
        }


        System.out.println(board);
        //ShowImages.showWindow(output, "Found Matches",true);
        String[] result = {board, hole};
        return result;
    }


    public static String[] getPot() {
        //String DIRECTORY = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator\\images";
        //String DIRECTORY2 = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator";
        GrayF32 potimage = UtilImageIO.loadImage(DIRECTORY, "allpot.png", GrayF32.class);

        BufferedImage output = new BufferedImage(potimage.width, potimage.height, BufferedImage.TYPE_INT_BGR);
        ConvertBufferedImage.convertTo(potimage, output);
        Graphics2D g2 = output.createGraphics();
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(3));



        int potx1 = 1538;
        int poty1 = 730;
        takeScreenshot("potsize.png", potx1, poty1, potx1 + 40, poty1 + 14);

        GrayF32 potLocation = UtilImageIO.loadImage(DIRECTORY2, "potsize.png", GrayF32.class);

        BufferedImage output2 = new BufferedImage(potLocation.width, potLocation.height, BufferedImage.TYPE_INT_BGR);
        ConvertBufferedImage.convertTo(potLocation, output2);
        Graphics2D g3 = output2.createGraphics();
        g3.setColor(Color.BLUE);
        g3.setStroke(new BasicStroke(5));

        GrayF32 dollar = UtilImageIO.loadImage(DIRECTORY, "dollar.png", GrayF32.class);
        int[] dL = drawRectangles(g3, potLocation, dollar, null, 1);

        //ShowImages.showWindow(output2, "Found Matches",true);


        System.out.println(dL[0]);
        System.out.println(dL[1]);
        if(dL[0] < 3) {
            takeScreenshot("potone.png", potx1 + dL[0] + 9, poty1 + dL[1] + 1, potx1 + dL[0] + 17, poty1 + dL[1] + 11);
            takeScreenshot("pottwo.png", potx1 + dL[0] + 21, poty1 + dL[1] + 1, potx1 + dL[0] + 30, poty1 + dL[1] + 11);
            takeScreenshot("potthree.png", potx1 + dL[0] + 30, poty1 + dL[1] + 1, potx1 + dL[0] + 38, poty1 + dL[1] + 11);
        }
        else{
            takeScreenshot("potone.png", potx1 + 14, poty1 + 2, potx1 + 23, poty1 + 12);
            takeScreenshot("pottwo.png", potx1 + 29, poty1 + 2, potx1 + 38, poty1 + 12);
            takeScreenshot("potthree.png", potx1 + 39, poty1 + 2, potx1 + 47, poty1 + 12);
        }

        int betx1 = 1700;
        int bety1 = 935;
        takeScreenshot("betsize.png", betx1, bety1, 1732, 949);
        takeScreenshot("bet1.png", betx1 + 8, bety1 + 3, betx1 + 14, bety1 + 11);
        takeScreenshot("bet2.png", betx1 + 17, bety1 + 3, betx1 + 24, bety1 + 11);
        takeScreenshot("bet3.png", betx1 + 24, bety1 + 3, betx1 + 30, bety1 + 11);

        // Search for the cursor in the image.  For demonstration purposes it has been pasted 3 times

        GrayF32 templatePotOne = UtilImageIO.loadImage(DIRECTORY2, "potone.png", GrayF32.class);
        int[] potOneLocation = drawRectangles(g2, potimage, templatePotOne, null, 1);
        GrayF32 templatePotTwo = UtilImageIO.loadImage(DIRECTORY2, "pottwo.png", GrayF32.class);
        int[] potTwoLocation = drawRectangles(g2, potimage, templatePotTwo, null, 1);
        GrayF32 templatePotThree = UtilImageIO.loadImage(DIRECTORY2, "potthree.png", GrayF32.class);
        int[] potThreeLocation = drawRectangles(g2, potimage, templatePotThree, null, 1);

        GrayF32 templateBet1 = createResizedCopy("bet1.png" ,10, 11, Boolean.TRUE, 0);
        GrayF32 templateBet2 = createResizedCopy("bet2.png" ,10, 11, Boolean.TRUE, 1);
        GrayF32 templateBet3 = createResizedCopy("bet3.png" ,10, 11, Boolean.TRUE, 2);

        int[] bet1Location = drawRectangles(g2, potimage, templateBet1, null, 1);
        int[] bet2Location = drawRectangles(g2, potimage, templateBet2, null, 1);
        int[] bet3Location = drawRectangles(g2, potimage, templateBet3, null, 1);

        String bet = getPotCell(bet1Location) + getPotCell(bet2Location) + getPotCell(bet3Location);



        String pot = getPotCell(potOneLocation) + getPotCell(potTwoLocation) + getPotCell(potThreeLocation);

        String result[] = {pot, bet};
        return result;

    }

    private static String getPotCell(int[] xy) {
        if (xy[0] < 6) {
            return ("1");
        } else if (xy[0] < 19) {
            return ("2");
        } else if (xy[0] < 31) {
            return ("3");
        } else if (xy[0] < 44) {
            return ("4");
        } else if (xy[0] < 57) {
            return ("5");
        } else if (xy[0] < 69) {
            return ("6");
        } else if (xy[0] < 81) {
            return ("7");
        } else if (xy[0] < 93) {
            return ("8");
        } else if (xy[0] < 106) {
            return ("9");
        } else {
            return ("0");
        }
    }


    public static GrayF32 createResizedCopy(String imageName,
                                                  int scaledWidth, int scaledHeight,
                                                  boolean preserveAlpha, int i) {

        //String DIRECTORY2 = "C:\\Users\\lcs_5\\OneDrive\\PokerCalculator";

        File imageFile = new File(imageName);
        BufferedImage img = null;

        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        //return scaledBI;

        String[] resized = {"resizedBet1.png","resizedBet2.png","resizedBet3.png"};
        try {
            ImageIO.write(scaledBI, "png", new File(resized[i]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GrayF32 templateBet = UtilImageIO.loadImage(DIRECTORY2, resized[i], GrayF32.class);
        return templateBet;

    }


}