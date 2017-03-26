package com.razelab.bot.linebot.model;

public class LineMovie {
	
	String movieTitle;
	String movieSynopsis;
	String movieTrailer;
	String movieThumbnail;
	
	
	public String getMovieThumbnail() {
		return movieThumbnail;
	}
	public void setMovieThumbnail(String movieThumbnail) {
		this.movieThumbnail = movieThumbnail;
	}
	public LineMovie(String movieThumbnail,String movieTitle, String movieSynopsis, String movieTrailer) {
		super();
		this.movieThumbnail = movieThumbnail;
		this.movieTitle = movieTitle;
		this.movieSynopsis = movieSynopsis;
		this.movieTrailer = movieTrailer;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getMovieSynopsis() {
		return movieSynopsis;
	}
	public void setMovieSynopsis(String movieSynopsis) {
		this.movieSynopsis = movieSynopsis;
	}
	public String getMovieTrailer() {
		return movieTrailer;
	}
	public void setMovieTrailer(String movieTrailer) {
		this.movieTrailer = movieTrailer;
	}
	
	

}
