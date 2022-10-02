package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Integer, String> findLang(){
		Map<Integer, String> returnMap = new HashMap<>();
		String user = "student";
		String pass = "student";
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM language";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet filmResultSet = stmt.executeQuery();
			
			while(filmResultSet.next()) {
				Integer key = filmResultSet.getInt("id");
				String value  = filmResultSet.getString("name");
				returnMap.put(key, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnMap;
	
		
	}
	
	@Override
	public List<Film> findFilmByKeyword (String keyword) throws SQLException {
		List<Actor> actors = new ArrayList<Actor>();
		List<Film> films = new ArrayList<Film>();
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film WHERE description LIKE ?"
				+ "OR title LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");

		ResultSet filmResult = stmt.executeQuery();
		while(filmResult.next()) {
			film = new Film(actors, filmResult.getInt("id"), filmResult.getString("title"),
					filmResult.getString("description"), filmResult.getInt("release_year"),
					filmResult.getInt("language_id"), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"));
			films.add(film);
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return films;
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<Actor>();
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);

		ResultSet filmResult = stmt.executeQuery();

		while (filmResult.next()) {
			film = new Film(actors, filmResult.getInt("id"), filmResult.getString("title"),
					filmResult.getString("description"), filmResult.getInt("release_year"),
					filmResult.getInt("language_id"), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"));
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name"));

		}
		actorResult.close();
		stmt.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<Actor>();
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
//		String sql = "SELECT * FROM actor JOIN film_actor ON actor_id = actor.id JOIN film ON film_id = ?";
		String sql = "SELECT act.* FROM film JOIN film_actor fa ON film.id = fa.film_id JOIN actor act ON fa.actor_id = act.id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);

		ResultSet filmResult = stmt.executeQuery();

		while (filmResult.next()) {
			Actor actor = new Actor(filmResult.getInt("id"), filmResult.getString("first_name"),
					filmResult.getString("last_name"));
			actors.add(actor);
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return actors;
	}

}
