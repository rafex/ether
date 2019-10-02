package mx.rafex.utils.rest.servlets;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

public abstract class ServletUtil {

    public static String getBasePath(final Class<? extends Servlet> servlet) {
        final String path = getBasePaths(servlet) != null && getBasePaths(servlet).length > 0 ? getBasePaths(servlet)[0] : null;
        return path;
    }

    public static String[] getBasePaths(final Class<? extends Servlet> servlet) {
        final WebServlet webServlet = servlet.getAnnotation(WebServlet.class);
        String[] paths = null;

        if (webServlet.value().length > 0) {
            paths = webServlet.value();
        }
        if (webServlet.urlPatterns().length > 0) {
            paths = webServlet.urlPatterns();
        }
        return paths;
    }

}
