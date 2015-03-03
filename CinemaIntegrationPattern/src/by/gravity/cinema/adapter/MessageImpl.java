package by.gravity.cinema.adapter;

import java.sql.Date;

import by.gravity.cinema.bo.Film;

public class MessageImpl implements Message<Film>{

	@Override
	public Film transofrm(String message) {
		String[] messageWords = message.split(" ");
		Integer filmId = Integer.parseInt(messageWords[1]);
		Long time = Long.parseLong(messageWords[2]);
		
		Film film = new Film(filmId, new Date(time));
		return film;
	}

}
