package ucsal.oferta.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import net.datafaker.Faker;

@TestConfiguration
public class TestBeanConfig {

	@Bean
	public Faker faker() {
		return new Faker();
	}

}
