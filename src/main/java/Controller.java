/**
 * Created by adityajoshi on 3/2/17.
 */

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    CrawlController crawlController;
    PageFetcher pageFetcher;
    RobotstxtConfig robotstxtConfig;
    RobotstxtServer robotstxtServer;
    CrawlConfig crawlConfig;
    static HashMap<String,Set<String>> offers;
    Controller(){
        String rootFolder = "root/data";
        crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder(rootFolder);
        crawlConfig.setMaxPagesToFetch(10);
        crawlConfig.setPolitenessDelay(1000);
        crawlConfig.setMaxDepthOfCrawling(4);
        pageFetcher = new PageFetcher(crawlConfig);
        robotstxtConfig = new RobotstxtConfig();
        System.out.println(robotstxtConfig.getUserAgentName());
        robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
        try {
            crawlController = new CrawlController(crawlConfig,pageFetcher,robotstxtServer);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void crawlURL(String URL){
        offers = new HashMap<String, Set<String>>();
        crawlController.addSeed(URL);
        crawlController.start(Crawler.class,1);
    }

    public static void main(String[] args){
        Controller controller = new Controller();
        controller.crawlURL("https://www.mcdonalds.com/us/en-us.html");
        printOffers();
    }

    private static void printOffers() {

        System.out.println("Inside");
        for (String url : offers.keySet()){
            for (String data: offers.get(url)){
                System.out.println(url + ": " + data);
            }
        }
    }


}
