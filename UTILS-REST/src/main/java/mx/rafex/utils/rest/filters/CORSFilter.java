package mx.rafex.utils.rest.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CORSFilter", urlPatterns = { "/*" })
public class CORSFilter implements Filter {

    private final Logger LOGGER = Logger.getLogger(CORSFilter.class.getName());

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        this.LOGGER.info("CORSFilter HTTP Request: " + request.getMethod());

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, DELETE, PUT, POST");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        chain.doFilter(request, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.LOGGER.info("CORS Activado");
    }

    @Override
    public void destroy() {

    }

}
