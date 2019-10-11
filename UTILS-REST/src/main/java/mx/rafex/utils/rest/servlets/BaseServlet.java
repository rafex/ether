package mx.rafex.utils.rest.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;

import mx.rafex.utils.json.JsonUtils;

public abstract class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 2964713457449758057L;

    private final Logger LOGGER = Logger.getLogger(BaseServlet.class.getName());

    public <T> T requestAsObject(final HttpServletRequest request, final Class<T> clazz) {
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
                LOGGER.warning(ex.getMessage());
            }
        }
        return null;
    }

    public void responseAsJson(final HttpServletResponse response, final Object obj) {

        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());

        final String res = JsonUtils.aJsonExcludeFieldsWithoutExposeAnnotation(obj);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(res);
            out.flush();

        } catch (final IOException e) {
            LOGGER.warning(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public boolean validPath(final HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        LOGGER.fine("Path info: " + pathInfo);
        return pathInfo == null || pathInfo.equals("/");
    }
}
