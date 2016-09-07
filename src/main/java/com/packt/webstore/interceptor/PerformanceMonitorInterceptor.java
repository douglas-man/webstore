package com.packt.webstore.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.cache.Cache;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dman on 9/6/16.
 */
public class PerformanceMonitorInterceptor implements HandlerInterceptor {
    private Logger logger =
            LoggerFactory.getLogger(this.getClass());

    ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch(handler.toString());
        stopWatch.start(handler.toString());
        stopWatchLocal.set(stopWatch);

        logger.info("Accessing URL path: " + getURLPath(request));
        logger.info("Request processing started on: " + getCurrentTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Request processing ended on " + getCurrentTime());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        StopWatch stopWatch = stopWatchLocal.get();
        stopWatch.stop();

        logger.info("Total time taken for processing: " + stopWatch.getTotalTimeMillis() + " ms");
        stopWatchLocal.set(null);
        logger.info("=================================================");
    }

    private String getURLPath(HttpServletRequest request) {
        String currentPath = request.getRequestURI();
        String queryString = request.getQueryString();
        queryString = queryString == null ? "" : "?" + queryString;
        return currentPath + queryString;
    }

    private String  getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return formatter.format(calendar.getTime());
    }
}
