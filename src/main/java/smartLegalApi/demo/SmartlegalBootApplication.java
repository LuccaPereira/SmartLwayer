package smartLegalApi.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "smartLegalApi")
@EnableJpaRepositories(basePackages = "smartLegalApi.infrastructure.persistence.jpa.repository")
@EntityScan(basePackages = "smartLegalApi.infrastructure.persistence.jpa.entity")
public class SmartlegalBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartlegalBootApplication.class, args);
	}

}
