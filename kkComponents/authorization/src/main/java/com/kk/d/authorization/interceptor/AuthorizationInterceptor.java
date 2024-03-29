package com.kk.d.authorization.interceptor;

import com.kk.d.authorization.annotation.Authorization;
import com.kk.d.authorization.constants.TokenClearType;
import com.kk.d.authorization.manager.TokenManager;
import com.kk.d.authorization.manager.impl.AbstractTokenManager;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

/**
 * 身份验证的拦截器
 * 自定义拦截器，对请求进行身份验证
 *
 * @author kk
 * @date 2019/12/26
 * @see Authorization
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String REQUEST_CURRENT_USER_ID = "REQUEST_CURRENT_USER_ID";

    public static final String REQUEST_CURRENT_ACCOUNT = "REQUEST_CURRENT_ACCOUNT";

    private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    /**
     * 管理身份验证操作的对象
     */
    @Autowired
    private TokenManager manager;

    /**
     * 存放鉴权信息的Header名称，默认是Authorization
     */
    private String httpHeaderName = "Authorization";

    /**
     * 鉴权信息的无用前缀，默认为空
     */
    private String httpHeaderPrefix = "";

    /**
     * 鉴权失败后返回的错误信息，默认为401 unauthorized
     */
    private String unauthorizedErrorMessage = "401 unauthorized";

    /**
     * 鉴权失败后返回的HTTP错误码，默认为401
     */
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * debug模式
     */
    private boolean isDebug = false;

    private Long testId;

    private String testNum;

    private String testAccount;

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setTestNum(String testNum) {
        this.testNum = testNum;
    }

    public void setTestAccount(String testAccount) {
        this.testAccount = testAccount;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public void setHttpHeaderName(String httpHeaderName) {
        this.httpHeaderName = httpHeaderName;
    }

    public void setHttpHeaderPrefix(String httpHeaderPrefix) {
        this.httpHeaderPrefix = httpHeaderPrefix;
    }

    public void setUnauthorizedErrorMessage(String unauthorizedErrorMessage) {
        this.unauthorizedErrorMessage = unauthorizedErrorMessage;
    }

    public void setUnauthorizedErrorCode(int unauthorizedErrorCode) {
        this.unauthorizedErrorCode = unauthorizedErrorCode;
    }

    public String getHttpHeaderName() {
        return httpHeaderName;
    }

    public String getHttpHeaderPrefix() {
        return httpHeaderPrefix;
    }

    public String getUnauthorizedErrorMessage() {
        return unauthorizedErrorMessage;
    }

    public int getUnauthorizedErrorCode() {
        return unauthorizedErrorCode;
    }

    public Long getTestId() {
        return testId;
    }

    public String getTestNum() {
        return testNum;
    }

    public String getTestAccount() {
        return testAccount;
    }

    public boolean isDebug() {
        return isDebug;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        String unauthorizedErrorMsg = unauthorizedErrorMessage;

        // 从header中得到token
        String token = request.getHeader(httpHeaderName);
        if (token != null && token.length() > 0) {
            Jws<Claims> claimsJws = null;
            try {

                claimsJws = Jwts.parser().setSigningKey(AbstractTokenManager.TOKEN_KEY).parseClaimsJws(token);
                // OK, we can trust this JWT

            } catch (SignatureException | MalformedJwtException | ExpiredJwtException e) {
                // don't trust the JWT!
                logger.warn("Wrong token: {}", token);
            }
            if (claimsJws != null) {
                String key = manager.getAccount(token);
                if (key != null) {

                    String userNum = claimsJws.getBody().getId();
                    String userId = claimsJws.getBody().getAudience();
                    String userAccount = claimsJws.getBody().getSubject();
                    if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userAccount)) {

                        // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                        request.setAttribute(REQUEST_CURRENT_USER_ID, userId);
                        request.setAttribute(REQUEST_CURRENT_ACCOUNT, userAccount);
                        return true;

                    }
                } else {
                    // token失效原因
                    TokenClearType tokenClearType = manager.getTokenClearType(token);
                    if (tokenClearType != null) {
                        unauthorizedErrorMsg = tokenClearType.getMessage();
                    }
                }
            }
        }
        // 如果验证token失败，并且方法注明了Authorization，返回401错误
        if (!isDebug && (handlerMethod.getBeanType().getAnnotation(Authorization.class) != null   // 查看方法所在的Controller是否有注解
                || method.getAnnotation(Authorization.class) != null)) { // 查看方法上是否有注解

            response.setStatus(unauthorizedErrorCode);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write("{\n" +
                    "  \"msg\": \"" + unauthorizedErrorMsg + "\",\n" +
                    "  \"ret\": 401\n" +
                    "}");
            writer.close();
            return false;
        }

        if (isDebug) {
            request.setAttribute(REQUEST_CURRENT_USER_ID, testId);
            request.setAttribute(REQUEST_CURRENT_ACCOUNT, testAccount);
        } else {

            // 为了防止以恶意操作直接在REQUEST_CURRENT_USER_ID、REQUEST_CURRENT_ACCOUNT中写入数据，将其设为null
            request.setAttribute(REQUEST_CURRENT_USER_ID, null);
            request.setAttribute(REQUEST_CURRENT_ACCOUNT, null);
        }
        return true;
    }
}
