import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//import RSSLivestreaming.model.Feed;
//import RSSLivestreaming.model.FeedMessage;
//import RSSLivestreaming.write.RSSFeedWriter;

class Main {
  public static void main(String[] args) {
            // create the rss feed
        String copyright = "511NJ";
        String title = "RSS NJ Weather Data";
        String description = "Current NJ Weather Information";
        String language = "en";
        String link = "https://511nj.org/RSS511Service/RSS511Service.svc/rest/rss/RSSActiveWeather";
        Calendar cal = new GregorianCalendar();
        Date creationDate = cal.getTime();
        SimpleDateFormat date_format = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
        String pubdate = date_format.format(creationDate);
        Feed rssFeeder = new Feed(title, link, description, language, copyright, pubdate);

        // now add one example entry
        /*
        FeedMessage feed = new FeedMessage();
        feed.setTitle("RSSFeed");
        feed.setDescription("This is a description");
        feed.setAuthor("nonsense@somewhere.de (Lars Vogel)");
        feed.setGuid("https://www.vogella.com/tutorials/RSSFeed/article.html");
        feed.setLink("https://511nj.org/RSS511Service/RSS511Service.svc/rest/rss/RSSActiveWeather");
        rssFeeder.getMessages().add(feed);
        */

        // now write the file
        RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, "articles.rss");
        try {
            writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RSSFeedParser parser = new RSSFeedParser("https://511nj.org/RSS511Service/RSS511Service.svc/rest/rss/RSSActiveWeather");
        Feed feed2 = parser.readFeed();
        System.out.println(feed2.getCopyright());
        System.out.println("All Active Flooding" + " " + feed2.getPubDate().substring(0, feed2.getPubDate().length() - 6));
        for (FeedMessage message : feed2.getMessages()) {
          if(message.getTitle().contains("Flood")) {
          System.out.println("");
          System.out.println("Event: " + message.getTitle());
          System.out.println("Description: " + message.getDescription());
            }
        }

        
  }
}