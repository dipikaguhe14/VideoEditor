package filter;

import annotation.Authentication;
import constants.HeaderConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationFilter {

    private final HttpServletRequest request;

    public AuthenticationFilter(HttpServletRequest request) {
        this.request = request;
    }

    private boolean isAuthenticated(String token) {
        return token.equals(HeaderConstants.TOKEN);
    }

    @Before("@annotation(Authentication)")
    public void checkAuthentication(org.aspectj.lang.JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Authentication annotation = methodSignature.getMethod().getAnnotation(Authentication.class);

        String headerName = annotation.authHeaderName();
        String headerValue = request.getHeader(headerName);

        if (headerValue == null || headerValue.isEmpty()) {
            throw new IllegalArgumentException("Missing required header: " + headerName);
        }

        if (!isAuthenticated(headerValue)) {
            throw new SecurityException("User is not authenticated!");
        }
    }
}
