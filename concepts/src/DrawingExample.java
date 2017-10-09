/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/9/2017
 *
 * concepts:
 */
import java.awt.*;

public class DrawingExample {

    public static void main(String[] args) throws Exception {

        int height = 500;
        int width = 400;
        drawScreen(width, height);
    }

    public static void drawScreen(int width, int height) {
        DrawingPanel panel = new DrawingPanel(width, height);
        Graphics gfx = panel.getGraphics();

        Color bgColor = Color.CYAN;
        panel.setBackground(bgColor);

        for (int i=0; i < 10; i++) {
            for (int j=0; j < 5; j++) {
                int red = (int) (Math.random()*255);
                int green = (int) (Math.random()*255);
                int blue = (int) (Math.random()*255);
                Color randomColor = new Color(red, green, blue);
                gfx.setColor(randomColor);

                gfx.fillRect(10+(45*i), 20+(45*j), 45, 45);

                gfx.setColor(Color.BLACK);
                gfx.drawString("BrianT", 13+(45*i), 35+(45*j));

                panel.sleep(500);
            }
//            panel.sleep(500);
            panel.clear();
        }
    }

    public static void parabola(int x, int y) {

    }
}
