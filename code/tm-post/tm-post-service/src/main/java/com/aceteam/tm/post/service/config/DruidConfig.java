package com.aceteam.tm.post.service.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: When the database connection pool uses druid, we can make some simple configurations to view sql monitoring, web monitoring, url monitoring, etc.
 * @author: haoran
 */

@Configuration
public class DruidConfig {

    /**
     * Register ServletRegistrationBean
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean registrationBean(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        /**Init Configuration, initParams**/
        // Whitelist
        bean.addInitParameter("allow", "127.0.0.1,47.119.192.69,124.221.124.200");
        // Blacklist
        bean.addInitParameter("deny", "192.168.1.73");

        // Log in to view the account password.
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "admin");

        // Whether to reset the data.
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    /**
     * Register FilterRegistrationBean
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        // Add filter rules.
        bean.addUrlPatterns("/*");
        // Add format information that does not need to be ignored.
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }





}
