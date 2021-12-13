package br.com.margel.goldenraspberryawards.business;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.margel.goldenraspberryawards.database.Db;

public class MoviesImporter {

	public void importFile(String fileName) throws FileNotFoundException, IOException, SQLException {
		try (
				Connection conn = Db.getInstance().newConnection();
				PreparedStatement ppst = conn.prepareStatement("INSERT INTO MOVIES (YEAR, TITLE, STUDIOS, PRODUCERS, WINNER) VALUES (?,?,?,?,?)");
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
				) {
			String[] split;
			String line = in.readLine();
			while ((line = in.readLine()) != null) {
				try {
					split = line.split(";");
					ppst.setInt(1, Integer.parseInt(split[0]));
					ppst.setString(2, split[1]);
					ppst.setString(3, split[2]);
					ppst.setString(4, split[3]);
					ppst.setBoolean(5, split.length==5 && "yes".equals(split[4]));
					ppst.executeUpdate();
				} catch (NumberFormatException e) {
					System.err.println("Invalid line! "+line);
				}
			}
		}
	}
	
}