import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;


import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Created by adityajoshi on 3/2/17.
 */
public class Crawler extends WebCrawler {
    HashSet<String> set = new HashSet<String>();
    Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jquery|jpe?g" + "|php|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    Pattern finder = Pattern.compile("(.*)(deal|discount|trend|hurry|offer|free)(.*)");
    Integer count = 0;
    Scrapper scrapper = new Scrapper("(.*)(deal|trend|discount|%|hurry|offer|free)(.*)");
    String domain;
    boolean first = false;
    @Override
    public boolean shouldVisit(Page page, WebURL url) {
        String href = url.getURL().toLowerCase();
        String pageUrl = page.getWebURL().getURL();
        if(!first){
            first = true;
            domain = new String(href);
            return !filters.matcher(href).matches();

        }
        count++;
        boolean is =  !filters.matcher(href).matches() && finder.matcher(href).matches();
        return is;

    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        //set.add(url);
        System.out.println("Visited: " + url);

        /*if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            text = text.trim();
            //String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            Iterator<WebURL> it = links.iterator();
            int count  = 0;
            for(int i = 0;i < links.size();i++){
                System.out.println(count++ + " " + it.next().getURL());
            }
            //System.out.println("Text length: " + text);
            //System.out.println("Html length: " + html.length());
            //System.out.println("Number of outgoing links: " + links.size());
        }*/

        /*try {
            String content = new String(page.getContentData(),page.getContentCharset());
            System.out.println("Content:" + content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        Set<String> set = scrapper.preprocess(url);
        /*for (String s:set){
            System.out.println(s);
        }*/

        Controller.offers.put(url,scrapper.extractText(set));


    }


}
