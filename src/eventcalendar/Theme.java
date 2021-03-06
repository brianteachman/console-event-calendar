/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Doctor Who Calendar Theme
*----------------------------------------------------------------------------*/
package eventcalendar;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Theme {
    private static final String PATH_FROM_ROOT = "data/";

    /*----------------------------------------------------------------------------
     * Creative requirement
     *--------------------------------------------------------------------------*/

    public static void drawHeaderArt(StringBuilder s) {
        Scanner in = null;
        try {
            in = new Scanner(new File(PATH_FROM_ROOT + "header_graphic.txt"));
            while(in.hasNextLine()) {
                s.append(in.nextLine()).append(View.EOL);
            }
        }
        catch (Exception e) { /* keep quiet */ }
    }

    public static void drawBanner(StringBuilder s) {
        String content = getDrQuote();
        s.append(View.EOL);
        View.drawHeader(s, "-", content.length());
        s.append(content).append(View.EOL);
        View.drawHeader(s, "-", content.length());
    }

    private static String getDrQuote() {
        String[] quotes = {
            "\"Great men are forged in fire. It is the privilege of lesser men to light the flame.\" - The War Doctor",
            "\"As we learn about each other, so we learn about ourselves.\" - The First Doctor",
            "\"Logic merely enables one to be wrong with authority.\" - The Second Doctor",
            "\"I reversed the polarity of the neutron flow.\" - The Third Doctor",
            "\"Answers are easy. It's asking the right questions which is hard.\" - The Fourth Doctor",
            "\"Yes. Still, the future lies this way.\" - The Fifth Doctor",
            "\"What's the use of a good quotation if you can't change it?\" - The Sixth Doctor",
            "\"All is change, all is movement\" - The Seventh Doctor",
            "\"I love humans. Always seeing patterns in things that aren’t there.\" - The Eighth Doctor",
            "\"I love a happy medium!\" - The Ninth Doctor",
            "\"Allons-y!\" - The Tenth Doctor",
            "\"I have a thing. It's like a plan, but with more greatness.\" - The Eleventh Doctor",
            "\"Sometimes the only choices you have are bad ones, but you still have to choose.\" - The Twelfth Doctor"
        };
        return quotes[new Random().nextInt(quotes.length)];
    }

}
