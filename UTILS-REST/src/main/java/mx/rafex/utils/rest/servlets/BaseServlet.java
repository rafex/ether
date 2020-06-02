package mx.rafex.utils.rest.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.MimeTypes;

import mx.rafex.utils.json.JsonUtils;
import mx.rafex.utils.rest.dtos.BaseResponseDto;

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

//    public void responseAsJson(final HttpServletResponse response, final Object obj) {
//
//        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
//
//        final String res = JsonUtils.aJsonExcludeFieldsWithoutExposeAnnotation(obj);
//
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//            out.print(res);
//            out.flush();
//
//        } catch (final IOException e) {
//            LOGGER.warning(e.getMessage());
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//
//    }

    public void responseAsJson(final HttpServletResponse response, final BaseResponseDto baseResponse) {

        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());

        try {
            response.setStatus(Integer.valueOf(baseResponse.getCode()));
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warning(e.getMessage());
        }

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(baseResponse.aJson());
            out.flush();

        } catch (final IOException e) {
            LOGGER.warning(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public void responseAsJson(final HttpServletResponse response, final Object obj) {

        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());

        try {
            response.setStatus(Integer.valueOf(200));
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warning(e.getMessage());
        }

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(JsonUtils.aJsonExcludeFieldsWithoutExposeAnnotation(obj));
            out.flush();

        } catch (final IOException e) {
            LOGGER.warning(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public boolean emptyPath(final HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        LOGGER.fine("Path info: " + pathInfo);
        return pathInfo == null || pathInfo.equals("/");
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doHead(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doHead(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doPut(request, response);
    }

    @Override
    protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doDelete(request, response);
    }

    @Override
    protected void doOptions(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(request, response);
    }

    @Override
    protected void doTrace(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doTrace(request, response);
    }
}
