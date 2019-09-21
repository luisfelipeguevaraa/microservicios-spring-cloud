package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import filters.MiPreFiltro;
 
@SpringBootApplication
@ComponentScan
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulServer {
	
 
	public static void main(String[] args) {
		SpringApplication.run(ZuulServer.class, args);
	}
	
	
	@Bean
	public MiPreFiltro miPreFiltro() {
		return new MiPreFiltro();
	}
	
}

				
