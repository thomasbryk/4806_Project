package application;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@SpringBootApplication
@ComponentScan({"controllers","models","repositories","security", "services"})
@EntityScan({"models"})
@EnableJpaRepositories("repositories")
public class WebLauncher implements WebMvcConfigurer{
    public static void main(String[] args) {
        SpringApplication.run(WebLauncher.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(new SpecificationArgumentResolver());
    }
}
