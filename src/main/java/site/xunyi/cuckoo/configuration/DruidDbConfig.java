package site.xunyi.cuckoo.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidDbConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}

	/**
	 * 配置Druid监控
	 */
	@Bean
	public ServletRegistrationBean<StatViewServlet> statServlet() {
		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		// druid管理界面的账号密码
		bean.addInitParameter("loginUsername", "admin");
		bean.addInitParameter("loginPassword", "admin");
		// 默认允许所有
		bean.addInitParameter("allow", "");
		return bean;
	}

	/**
	 * 配置web监控filter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean statFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
		// 配置通过的资源
		bean.addInitParameter("exclusions", "*.js, *.css, *.jpg, *.png, *.ico, /druid/*");
		bean.addUrlPatterns("/*");
		return bean;
	}
}