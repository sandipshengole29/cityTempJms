package day6.cityTempJms.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);
	
    public void commence(final HttpServletRequest request,  final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
    	LOGGER.info("*** inside commence @CustomBasicAuthenticationEntryPoint ***");
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
         
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
    }
     
    public void afterPropertiesSet() throws Exception {
        setRealmName("SecurityRealm");
        super.afterPropertiesSet();
    }
}
