/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 12/2/2017
 * 
 * Naive dependency injection container (IoC)
 *
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package app;

import exceptions.ServiceCollisionException;

import java.util.HashMap;

public class ServiceManager {

    private HashMap<String, Service> services;

    public ServiceManager() {
        services = new HashMap<String, Service>();
        // could alternatively init deps here
//        services.put("Calendar", new CalendarController());
    }

    public boolean has(String key) {
        return services.containsKey(key);
    }

    public void add(String key, Service service) throws ServiceCollisionException {
        if (!has(key)) {
            services.put(key, service);
        } else {
            throw new ServiceCollisionException(key);
        }
    }

    public Service get(String key) {
        return services.get(key);
    }
}
