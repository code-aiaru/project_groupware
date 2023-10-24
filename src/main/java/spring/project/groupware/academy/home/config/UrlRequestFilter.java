package spring.project.groupware.academy.home.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.project.groupware.academy.employee.config.MyUserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UrlRequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UrlRequestFilter.class);
    private static final Set<String> excludedUrl = new HashSet<>();

    static {
        // 제외할 URL들을 추가
        excludedUrl.add("/js/");
        excludedUrl.add("/css/");
        excludedUrl.add("/images/");
        excludedUrl.add("/employeeImages/"); // 추가 - 송원철, 프로필 이미지 업로드 시 사용
        excludedUrl.add("/studentImages/"); // 추가 - 송원철, 수강생 프로필 이미지 업로드 시 사용
        excludedUrl.add("/login");
        excludedUrl.add("/favicon.ico");
        excludedUrl.add("/api/posts");
        excludedUrl.add("/api/");
        excludedUrl.add("/post");
        excludedUrl.add("/notice");
        excludedUrl.add("/tui-editor/");
        excludedUrl.add("/naver/"); // 추가 - 송원철, 조직도 불러오기
//        excludedUrl.add("/login/post");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.info("doFilter Activated");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestedUri = httpRequest.getRequestURI();

        if (isExcludedUrl(requestedUri)) {
            logger.info("excludedUrl activated : {}", requestedUri);
            chain.doFilter(request, response);
            return;
        }

        String asyncUrlRequest = httpRequest.getHeader("AsyncUrlRequest");
        String currentURL = httpRequest.getHeader("CurrentURL");

        if (currentURL != null) {
            httpRequest.getSession().setAttribute("CurrentURL", currentURL);
        }
        String previousURL = (String) httpRequest.getSession().getAttribute("CurrentURL");

        logRequestDetails(requestedUri, asyncUrlRequest, currentURL, previousURL);
        requestTypeDecideHandler(httpRequest, httpResponse, asyncUrlRequest, previousURL);

        chain.doFilter(request, response);
    }

    // 필터 예외 처리된 URL을 확인하기 위한 메소드
    private boolean isExcludedUrl(String requestedUri) {
        return excludedUrl.stream().anyMatch(excluded -> requestedUri.contains(excluded));
    }

    // 요청타입 분별 메소드
    private void requestTypeDecideHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String asyncUrlRequest, String previousURL) throws IOException, ServletException {
        logger.info("requestTypeDecideHandler activated");
        if (asyncUrlRequest == null && previousURL == null) {
            logger.info("초기 로딩 요청.");
            forwardRequest(httpRequest, httpResponse, "/index");
            return;
        } else if (asyncUrlRequest == null && previousURL != null) {
            logger.info("새로고침 요청.");
            httpRequest.setAttribute("previousURL", previousURL);
            forwardRequest(httpRequest, httpResponse, "/index");
            return;
        } else if (asyncUrlRequest != null && previousURL != null) {
            logger.info("SPA URL 요청.");
        }
    }

    // 요청 전달 메소드
    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    // 로그 메시지 출력 메소드
    private void logRequestDetails(String requestedUri, String asyncUrlRequest, String currentURL, String previousURL) {
        logger.info("requestedUri : {}", requestedUri);
        logger.info("asyncUrlRequest: {}", asyncUrlRequest);
        logger.info("currentURL: {}", currentURL);
        logger.info("previousURL: {}", previousURL);
    }

}
