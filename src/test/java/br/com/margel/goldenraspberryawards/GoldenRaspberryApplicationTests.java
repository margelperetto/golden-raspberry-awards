package br.com.margel.goldenraspberryawards;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import br.com.margel.goldenraspberryawards.business.MoviesImporter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GoldenRaspberryApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void shouldCalcMinMax() throws FileNotFoundException, IOException, SQLException {
		new MoviesImporter().importFile(getMovieListResourceFile());
		String response = restTemplate.getForObject("http://localhost:"+port+"/calc-min-max", String.class);
		assertThat(response).isEqualTo("{\"min\":[{\"producer\":\"A\",\"interval\":1,\"previousWin\":2001,\"followingWin\":2002}],\"max\":[{\"producer\":\"D\",\"interval\":4,\"previousWin\":2000,\"followingWin\":2004}]}");
	}
	
	private String getMovieListResourceFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("movielist.csv").getFile()).getAbsolutePath();
	}
}