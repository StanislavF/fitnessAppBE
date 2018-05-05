package da.proj.fitnessApp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import da.proj.fitnessApp.config.DataSourceConfig;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App {
	
	@Autowired
	private DataSourceConfig dataSourceProperties;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
		        .create()
		        .username(dataSourceProperties.getUsername())
		        .password(dataSourceProperties.getPassword())
		        .url(dataSourceProperties.getUrl())
		        .driverClassName(dataSourceProperties.getDriverClassName())
		        .build();
	}
	
	 @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("DELETE");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }	

}
