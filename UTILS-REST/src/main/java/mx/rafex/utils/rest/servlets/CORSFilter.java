package mx.rafex.utils.rest.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, DELETE, PUT, POST");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        final HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS
        // handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
