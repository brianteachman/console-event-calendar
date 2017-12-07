/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 12/3/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package exceptions;

public class ServiceCollisionException extends IllegalArgumentException {

    public ServiceCollisionException(String key) {
        super(key + " service already loaded");
    }
}
