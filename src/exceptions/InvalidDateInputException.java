/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package exceptions;

public class InvalidDateInputException extends IllegalArgumentException {
    private static String messageFormat = "%s range from 1 to %d, %s given.";

    public InvalidDateInputException(String segmentName, int upperBound, String dateSegment) {
        super(String.format(messageFormat, segmentName, upperBound, dateSegment));
    }
}
