package com.shriram.microservices.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Response Utility for sending HTTP response for multiple scenarios
 * <p>
 * This utility sets HTTP status codes for unauthorized and bad request and also sends appropriate messages.
 *
 * @method - badRequest - for sending 400 response
 * @method - unauthorized - for creating token
 */
public class ResponseUtil {

    private static final String BAD_REQUEST = "malformed request";
    private static final String UNAUTHORIZED_REQUEST = "invalid token";

    public static Map<String, Object> badRequest(HttpServletResponse response, String message) {
        Map<String, Object> responseContent = new HashMap<String, Object>();
        if (StringUtils.isEmpty(message)) {
            message = BAD_REQUEST;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseContent.put("message", message);
        return responseContent;
    }

    public static HttpServletResponse unauthorized(HttpServletResponse response, String message) throws IOException {
        if (StringUtils.isEmpty(message)) {
            message = UNAUTHORIZED_REQUEST;
        }
        response.getWriter().write("{\"message\" : \"" + message + "\"}");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return response;
    }

    public static Map<String, Object> unauthorizedMessage(HttpServletResponse response, String message) {
        Map<String, Object> responseContent = new HashMap<String, Object>();
        if (StringUtils.isEmpty(message)) {
            message = UNAUTHORIZED_REQUEST;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseContent.put("message", message);
        return responseContent;
    }
}
