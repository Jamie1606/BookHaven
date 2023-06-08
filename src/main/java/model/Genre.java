//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 7.6.2023
//Description : Genre Class

package model;

public class Genre {
	private int genreID;
	private String genre;
	
	public Genre(int genreID, String genre) {
		this.genreID = genreID;
		this.genre = genre;
	}

	public int getGenreID() {
		return genreID;
	}

	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
