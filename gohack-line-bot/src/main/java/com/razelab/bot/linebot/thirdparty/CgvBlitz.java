package com.razelab.bot.linebot.thirdparty;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CgvBlitz {

	static List<String> getMovieList(){
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
            for (Element movie : movies) {
            	Elements links = movie.select("a[href]");
                movieDoc = Jsoup.connect("https://www.cgv.id"+links.get(0).attr("href")).get();
                               
                Elements synopsis = movieDoc.select("div.movie-synopsis.right p");
                System.out.println(movieDoc.select("div.movie-info-title").text());
                System.out.println(synopsis);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
}
