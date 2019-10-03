package mx.rafex.utils.rest.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;

import mx.rafex.utils.json.JsonUtils;

public abstract class ServletUtil {

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
        return paths;
    }

    public static <T> T requestAsObject(final HttpServletRequest request, final Class<T> clazz) {
        if (request.getContentType() != null && MimeTypes.Type.APPLICATION_JSON.asString().contains(request.getContentType())
                || MimeTypes.Type.APPLICATION_JSON.asString().equals(request.getContentType().trim())) {
            try {
                final StringBuilder buffer = new StringBuilder();
                final BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                final String payload = buffer.toString();
                return JsonUtils.aJson(payload, clazz);
            } catch (final IOException ex) {

            }
        }
        return null;
    }

    public static void responseAsJson(final HttpServletResponse response, final Object obj) {

        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());

        final String res = JsonUtils.aJsonExcludeFieldsWithoutExposeAnnotation(obj);

        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(res);
            out.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean validPath(final HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        return pathInfo == null || pathInfo.equals("/");
    }
}
