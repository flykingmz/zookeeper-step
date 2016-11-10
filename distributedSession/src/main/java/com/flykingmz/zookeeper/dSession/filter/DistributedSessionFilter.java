package com.flykingmz.zookeeper.dSession.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class DistributedSessionFilter implements Filter {
	private final static Logger logger = LoggerFactory
			.getLogger(DistributedSessionFilter.class);
	/**
	 *
	 */
	private static final long serialVersionUID = -1L;

	private String sessionIdName = "D_SESSION_ID";

	private String cookieDomain = "";

	private String cookiePath = "/";

	private List<String> excludeUrl = new ArrayList<String>();

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = ((HttpServletRequest) request).getRequestURI();
		
		if (this.excludeUrl != null && this.isMatchExcludeUrl(uri)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//设置cookieDomain
		initCookieDomain(request);
		
		
		String sessionId = getSessionId(request, response);
		HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(sessionId, request);
		logger.debug("sessionId:"+sessionId);
        
        
		filterChain.doFilter(httpServletRequestWrapper, response);
	}



	private void initCookieDomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		cookieDomain = serverName;
	}


	private String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		String sessionId = "";
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie.getName().equals(sessionIdName)) {
					sessionId = sCookie.getValue();
				}
			}
		}
		
		if (sessionId == null || sessionId.length() == 0) {
			sessionId = java.util.UUID.randomUUID().toString();
			response.addHeader("Set-Cookie", sessionIdName + "=" + sessionId
					+ ";domain=" + this.cookieDomain + ";Path="
					+ this.cookiePath + ";HTTPOnly");
		}
		return sessionId;
	}
	
	

	public void init(FilterConfig filterConfig) throws ServletException {
		this.cookieDomain = filterConfig.getInitParameter("cookieDomain");
		if (this.cookieDomain == null) {
			this.cookieDomain = "";
		}
		this.cookiePath = filterConfig.getInitParameter("cookiePath");
		if (this.cookiePath == null || this.cookiePath.length() == 0) {
			this.cookiePath = "/";
		}
		String excludeUrlsString = filterConfig.getInitParameter("excludeUrls");
		if (!StringUtils.isEmpty(excludeUrlsString)) {
			String[] urls = excludeUrlsString.split(",");
			this.excludeUrl = Arrays.asList(urls);
		}
	}

	private boolean isMatchExcludeUrl(String uri) {
		if (StringUtils.isEmpty(uri)) {
			return false;
		}
		// 修复类型匹配规则
		for (String regexUrl : this.excludeUrl) {
			if (uri.endsWith(regexUrl)) {
				return true;
			}
		}
		return false;
	}

	public void destroy() {
		this.excludeUrl = null;
		this.cookieDomain = null;
		this.cookiePath = null;
	}

}
