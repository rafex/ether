package mx.rafex.utils.rest.servlets;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

public final class UtilServlet {

    private static final Logger LOGGER = Logger.getLogger(BaseServlet.class.getName());

    private UtilServlet() {

    }

    public static String getBasePath(final Class<? extends HttpServlet> httpServlet) {
        final String path = getBasePaths(httpServlet) != null && getBasePaths(httpServlet).length > 0 ? getBasePaths(httpServlet)[0] : null;
        return path;
    }

    public static String[] getBasePaths(final Class<? extends HttpServlet> httpServlet) {
        final WebServlet webServlet = httpServlet.getAnnotation(WebServlet.class);
        String[] paths = null;

        if (webServlet.value().length > 0) {
            paths = webServlet.value();
        }
        if (webServlet.urlPatterns().length > 0) {
            paths = webServlet.urlPatterns();
        }
        LOGGER.fine("Paths: " + paths);
        return paths;
    }
}
