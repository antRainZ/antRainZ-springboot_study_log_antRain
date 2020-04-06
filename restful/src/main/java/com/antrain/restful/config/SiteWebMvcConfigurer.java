package com.antrain.restful.config;

        import com.antrain.restful.filter.AdminLoginInterceptor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class SiteWebMvcConfigurer implements WebMvcConfigurer {


    private final AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    public SiteWebMvcConfigurer(AdminLoginInterceptor adminLoginInterceptor) {
        this.adminLoginInterceptor = adminLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
       // registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**")
              //  .excludePathPatterns("/admin/login");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //使外部能访问静态资源
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
