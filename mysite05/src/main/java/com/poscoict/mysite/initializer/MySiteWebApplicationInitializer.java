package com.poscoict.mysite.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.poscoict.mysite.config.AppConfig;
import com.poscoict.mysite.config.WebConfig;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	} // new int[5] {1,2,3,4,5}와 비슷한 방식

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
		
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("utf-8", false)};
	}

	/*
	 * 핸들러 매핑에 해당되는 URL이 없다면 Exception을 발생(true)시키겠다는 뜻. 따라서 exception.jsp로 가게된다.
	 * 근데 문제가 하나 있음. 절대 발생하지않는다는 문제가 있다는 뜻이다.
	 * Exception 발생하면 Global... -> exception.jsp임
	 * 근데 우리가 한 모든 작업들은 Handler가 있는 작업들임
	 * 
	 * MvcConfig의 configureDefaultServletHandling로 가도록 했다. 
	 * 
	 * 그러면 assets를 이용못함
	 * 
	 * resourceMapping을 해서 assets를 이용해야한다.
	 * 
	 * 404없애보기 (web.xml까지)
	 * 
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
	}
	
}
