package co.edu.itli.labs.appjsf.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Filter checks if LoginBean has loginIn property set to true.
 * If it is not set then request is being redirected to the login.xhml page.
 * 
 * @author itcuties
 *
 */
public class LoginFilter implements Filter {


	  private static final Logger LOGGER =Logger.getLogger(LoginFilter.class.getName());

	  public static final String LOGIN_PAGE = "/login.xhtml";

	  @Override
	  public void doFilter(ServletRequest servletRequest,
	      ServletResponse servletResponse, FilterChain filterChain)
	      throws IOException, ServletException {

	    HttpServletRequest httpServletRequest =
	        (HttpServletRequest) servletRequest;
	    HttpServletResponse httpServletResponse =
	        (HttpServletResponse) servletResponse;
	    
	    HttpSession session = httpServletRequest.getSession(false);

	    // managed bean name is exactly the session attribute name
	    String userManager = session!=null ?(String) session.getAttribute("userlogued"):null;
	    String uri = httpServletRequest.getRequestURI();
	    boolean resourceRequest = httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
	    boolean resourceApp = httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + "/resource");
	    

	    if (userManager != null 
	    		|| uri.contains("/public/") 
	    		|| uri.endsWith("index.html")  
	    		|| uri.endsWith("index.xhtml")
	    		|| uri.endsWith("login.xhtml")
	    		|| resourceRequest
	    		|| resourceApp	    		) {
	         // user is logged in, continue request
	        filterChain.doFilter(servletRequest, servletResponse);
	      
	    } else {
	      LOGGER.info("userManager not found");
	      // user is not logged in, redirect to login page
	      httpServletResponse.sendRedirect(
	          httpServletRequest.getContextPath() + LOGIN_PAGE);
	    }
	  }

	  @Override
	  public void init(FilterConfig arg0) throws ServletException {
	    LOGGER.info("loginFilter initialized");
	  }

	  @Override
	  public void destroy() {
	    // close resources
	  }
	
}