package by.gravity.cinema.bo;

import java.util.Date;

public class Film {

	private int id;
	
	private Date time;

	public Film(int id, Date time) {
		this.id = id;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public Date getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", time=" + time + "]";
	}
	
	
	
	
}
