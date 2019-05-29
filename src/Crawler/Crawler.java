package Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Console;
import java.io.IOException;
import java.util.HashSet;

public class Crawler {

    private HashSet<String> links;

    public Crawler() {
        links = new HashSet<String>();
    }

    public void getHtmlLinks(String URL) {
   
        if (!links.contains(URL)) {
            try {
                //4. Adds links to queue if not already visited
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //2. Fetch the pizzas html code
                Document document = Jsoup.connect(URL).get();
                //3. Parse the HTML to extract links to other URLs
                Elements PizzaContainers = document.select("a[href^=\"#cat-\"]");
                Elements PizzName = document.select("a[href][item-div]");
               
                for (Element link: PizzaContainers) {
                    String url = link.attr("");
                    String text = link.text();
                    System.out.println(text + ", " + url);
                }
                
                
             
                for (Element page : PizzaContainers) {
                	getHtmlLinks(page.attr("abs:href"));
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
    

    public static void main(String[] args) {
        //1. Picks URL from frontier, get menu of pizza restaurant
        new Crawler().getHtmlLinks("http://www.mamarosa-esbjerg.dk/menukort");
      


    }

}

