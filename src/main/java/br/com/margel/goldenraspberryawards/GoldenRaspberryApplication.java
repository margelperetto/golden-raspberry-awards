package br.com.margel.goldenraspberryawards;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.margel.goldenraspberryawards.business.MoviesImporter;

@SpringBootApplication
public class GoldenRaspberryApplication {

	public static void main(String[] args) throws IOException {
		try {
			new MoviesImporter().importFile(args[args.length-1]);
			SpringApplication.run(GoldenRaspberryApplication.class, args);
		} catch (FileNotFoundException fnf) {
			System.err.println("File not found! "+Arrays.asList(args)+" "+fnf.getMessage());
		} catch (SQLException sqlEx) {
			System.err.println("Database connection error! "+sqlEx.getMessage());
		}
	}

}