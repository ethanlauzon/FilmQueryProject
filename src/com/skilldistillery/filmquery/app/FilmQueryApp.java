package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

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
				+ film.getReleaseYear() + " Runtime: " + film.getLength() + " Rating: " + film.getRating()
				+ " Language: " + film.getLangName());
//		listOfActors(film.getActors());
	}

	private void displayMultiplefilms(List<Film> films) {
		for (Film film : films) {
			System.out.println("Title: " + film.getTitle() + " Description: " + film.getDescription()
					+ " Year Released: " + film.getReleaseYear() + " Runtime: " + film.getLength() + " Rating: "
					+ film.getRating() + " Language " + film.getLangName());
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

		while (run) {
			List<Film> films;
			System.out.println("Welcome, please make a menu selection");
			System.out.println("1) Look up film by ID");
			System.out.println("2) Look up film by keyword");
			System.out.println("3) Exit");
			int userChoice = input.nextInt();
			if (userChoice == 1) {
				System.out.println("Enter the ID of the film you would like to look up");
				int userChoice1 = input.nextInt();
				try {
					if (userChoice1 > 1000 || userChoice1 < 1) {
						System.out.println("invalid entry");
						continue;
					} else {
						film = db.findFilmById(userChoice1);
//					List<Actor> actors;
//					actors = db.findActorsByFilmId(userChoice1);
//					listOfActors(actors);
						displayFilm(film);
						continue;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}
			if (userChoice == 2) {
				System.out.println("Enter the keyword");
				String userChoice2 = input.next();
				try {

					films = db.findFilmByKeyword(userChoice2);
					if (films.size() == 0) {
						System.out.println("No films found");
					} else {
						displayMultiplefilms(films);
					}
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (userChoice == 3) {
				System.out.println("Goodbye");
				run = false;

			} else {
				System.out.println("invalid entry");
				continue;
			}

		}
		input.close();
	}
}
