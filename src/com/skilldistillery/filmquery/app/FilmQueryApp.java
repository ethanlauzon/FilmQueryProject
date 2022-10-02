package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.management.loading.PrivateClassLoader;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	private Map<Integer, String> findLang = db.findLang();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void listOfActors(List<Actor> actors) {
		for (Actor actor : actors) {
			System.out.println(actor.getFirstName() + " " + actor.getLastName());
		}
	}

	private void displayFilm(Film film) {
		System.out.println("Title: " + film.getTitle() + " Description: " + film.getDescription() + " Year Released: "
				+ film.getReleaseYear() + " Runtime: " + film.getLength() + " Rating: " + film.getRating());
		listOfActors(film.getActors());
	}

	private void displayMultiplefilms(List<Film> films) {
		for (Film film : films) {
			System.out
					.println("Title: " + film.getTitle() + " Description: " + film.getDescription() + " Year Released: "
							+ film.getReleaseYear() + " Runtime: " + film.getLength() + " Rating: " + film.getRating());
			listOfActors(film.getActors());
		}

	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean run = true;
		Film film;
		System.out.println("Welcome, please make a menu selection");
		System.out.println("1) Look up film by ID");
		System.out.println("2) Look up film by keyword");
		System.out.println("3) Exit");
		int userChoice = input.nextInt();

		while (run) {
			if (userChoice == 1) {
				System.out.println("Enter the ID of the film you would like to look up");
				int userChoice1 = input.nextInt();
				try {
					film = db.findFilmById(userChoice1);
					List<Actor> actors;
					actors = db.findActorsByFilmId(userChoice1);
					listOfActors(actors);
					displayFilm(film);
					break;

				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}
			if (userChoice == 2) {
				System.out.println("Enter the keyword");
				String userChoice2 = input.next();
				try {
					List<Film> films;
					films = db.findFilmByKeyword(userChoice2);
					displayMultiplefilms(films);
					break;
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (userChoice == 3) {
				System.out.println("Goodbye");
				run = false;

			}

		}
		input.close();
	}
}
