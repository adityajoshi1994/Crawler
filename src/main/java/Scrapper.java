import edu.uci.ics.crawler4j.crawler.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;

/**
 * Created by adityajoshi on 3/5/17.
 */
public class Scrapper {
    Pattern pattern;
    Pattern split;
    Scrapper(String PatternRegex){
        pattern = Pattern.compile(PatternRegex);
        split = Pattern.compile("\\.");
    }

    Scrapper(String PatternRegex,String splitRegex){
        pattern = Pattern.compile(PatternRegex);
        split = Pattern.compile(splitRegex);

    }

    Set<String> extractText(Set<String> text){
        Set<String> ans = new HashSet<String>();
        for(String data:text){
            if(pattern.matcher(data).matches()){
                ans.add(data);
            }
        }
        return ans;
    }

    public Set<String> preprocess(String url) {
        Pattern pattern = Pattern.compile("(.*?)\\{(.*?)\\}|\\{(.*?)\\}(.*?)");
        org.jsoup.nodes.Document doc = null;
        HashSet<String> set = new HashSet<String>();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Unable to connect to webURL");
            return set;
        }
        Elements e = doc.select("p");
        //String text = doc.body().text();
        for(org.jsoup.nodes.Element ele :e){
            if(!pattern.matcher(ele.text()).matches())
                set.add(ele.text());
        }
        return set;
    }
}
