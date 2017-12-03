/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 12/3/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package exceptions;

import java.io.FileNotFoundException;

public class EventsFileNotFound extends FileNotFoundException {

    public EventsFileNotFound() {
        super("No file found");
    }
}

