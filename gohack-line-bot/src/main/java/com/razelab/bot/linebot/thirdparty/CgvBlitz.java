package com.razelab.bot.linebot.thirdparty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.razelab.bot.linebot.model.LineMovie;

public class CgvBlitz {

	public static List<LineMovie> getMovieList(){
		List<LineMovie> movieList = new ArrayList<>();
		Document doc;
        Document movieDoc;
        try {

            doc = Jsoup.connect("https://www.cgv.id/en/movies/now_playing").get();

            // get page title
            String title = doc.title();
            //System.out.println("title : " + title);
            //<div class="movie-list-body">

            Elements contents = doc.select("div.movie-list-body");
            Elements movies = contents.select("ul li");
            for (int i = 0; i<6 ; i++) {
            //for (Element movie : movies) {
            	Elements links = movies.get(i).select("a[href]");
                movieDoc = Jsoup.connect("https://www.cgv.id"+links.get(0).attr("href")).get();
                               
                Elements synopsis = movieDoc.select("div.movie-synopsis.right p");
                Elements thumbnail = movieDoc.select("div.poster-section img");
                System.out.println(movieDoc.select("div.movie-info-title").text());
                System.out.println(synopsis);
                String thumbnailPic = thumbnail.attr("abs:src");
                Elements trailer = movieDoc.select("div.trailer-section iframe");
                String trailerLink = trailer.attr("abs:src");
                movieList.add(  new LineMovie(thumbnailPic,movieDoc.select("div.movie-info-title").text(), synopsis.toString(), trailerLink));
              

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		return movieList;
	}
}
