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
	
	@Override
	public List<Film> findFilmByKeyword (String keyword) throws SQLException {
		List<Film> films = new ArrayList<Film>();
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration,"
				+ "rental_rate, length, replacement_cost, rating, special_features, language.name FROM film "
				+ "JOIN language ON film.language_id = language.id WHERE film.description LIKE ?"
				+ "OR film.title LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");

		ResultSet filmResult = stmt.executeQuery();
		while(filmResult.next()) {
			film = new Film(findActorsByFilmId(filmResult.getInt("film.id")), filmResult.getInt("film.id"), filmResult.getString("title"),
					filmResult.getString("description"), filmResult.getInt("release_year"),
					filmResult.getInt("language_id"), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"), filmResult.getString("language.name"));
			films.add(film);
		}
		filmResult.close();
		stmt.close();
		conn.close();
		return films;
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, "
			+ "rental_rate, length, replacement_cost, rating, special_features, language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);

		ResultSet filmResult = stmt.executeQuery();

		while (filmResult.next()) {
			film = new Film(findActorsByFilmId(filmResult.getInt("film.id")), filmResult.getInt("film.id"), filmResult.getString("title"),
					filmResult.getString("description"), filmResult.getInt("release_year"),
					filmResult.getInt("language_id"), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"), filmResult.getString("language.name"));
		
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
