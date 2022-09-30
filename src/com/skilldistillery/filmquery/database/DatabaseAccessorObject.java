package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	

	public DatabaseAccessorObject() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);
		
		ResultSet filmResult = stmt.executeQuery();
		
		if(filmResult.next()) {
			film = new Film(filmResult.getInt("id"), filmResult.getString("title"), 
					filmResult.getString("description"), filmResult.getInt("release_year"),
					filmResult.getInt("language_id"), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"));
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, actorId);

		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name"));

		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		

		return null;
	}

}
