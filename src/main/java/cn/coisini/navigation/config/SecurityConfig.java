package cn.coisini.navigation.config;


import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.security.MyUserDetailsServiceImpl;
import cn.coisini.navigation.security.TokenAuthenticationFilter;
import cn.coisini.navigation.security.TokenLoginFilter;
import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: xiaoxiang
 * Description: SecurityConfig全局配置类
 * 流程：
 * -》自定义密码组件-》自定义用户对象-》创建方法根据用户名查询用户信息
 * -》自定义认证过滤器-》返回信息工具类-》认证解析过滤器-》配置用户认证全局信息
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MyUserDetailsServiceImpl myUserDetailService;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final StringRedisTemplate stringRedisTemplate;

    public SecurityConfig(MyUserDetailsServiceImpl myUserDetailService, TokenAuthenticationFilter tokenAuthenticationFilter, StringRedisTemplate stringRedisTemplate) {
        this.myUserDetailService = myUserDetailService;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**")
                .permitAll()
                .antMatchers("/api/v1/index/login", "/api/v1/index/code", "/api/v1/index/register","/api/v1/sort/sortTag")// 对登录、注册、验证码 允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 自定义登录校验
        httpSecurity.addFilter(new TokenLoginFilter(authenticationManager(), stringRedisTemplate));
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(new RestfulAccessDeniedHandler())
                .authenticationEntryPoint(new RestAuthenticationPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * 作用： 用来将自定义AuthenticationManager在工厂中进行暴露，可以在任何位置注入
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 当访问接口没有权限时，自定义的返回结果
     */
    @Component
    public static class RestfulAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSONUtil.parse(Result.error(ResultEnum.NO_OPERATOR_AUTH)));
            response.getWriter().flush();
        }
    }

    /**
     * 当未登录或者token失效访问接口时，自定义的返回结果
     */
    @Component
    public static class RestAuthenticationPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest re, HttpServletResponse response, AuthenticationException e) throws IOException {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSONUtil.parse(Result.error(ResultEnum.NEED_LOGIN)));
            response.getWriter().flush();
        }
    }

}
