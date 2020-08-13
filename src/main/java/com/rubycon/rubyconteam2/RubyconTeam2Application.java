package com.rubycon.rubyconteam2;

import com.rubycon.rubyconteam2.config.application.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class RubyconTeam2Application {

	public static void main(String[] args) {
		SpringApplication.run(RubyconTeam2Application.class, args);
	}

}

@RestController
class HomeContoller{
	@GetMapping("/")
	public String index() {
		return "Hello Spring Boot with AWS EB :)";
	}
}