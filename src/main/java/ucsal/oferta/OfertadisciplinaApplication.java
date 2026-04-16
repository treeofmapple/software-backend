package ucsal.oferta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ucsal.oferta.config.CustomBanner;

@SpringBootApplication
public class OfertadisciplinaApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OfertadisciplinaApplication.class);
		app.setBanner(new CustomBanner());
		app.run(args);
	}

}
