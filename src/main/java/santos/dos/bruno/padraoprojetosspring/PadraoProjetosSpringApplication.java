package santos.dos.bruno.padraoprojetosspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PadraoProjetosSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadraoProjetosSpringApplication.class, args);
	}

}
