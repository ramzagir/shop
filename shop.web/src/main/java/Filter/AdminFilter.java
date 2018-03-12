package Filter;

import practic.LoginBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter{

    @Inject
    LoginBean loginBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        if(loginBean.isAdmin()){
            chain.doFilter(request, response);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/home.xhtml");

    }

    @Override
    public void destroy() {

    }
}
