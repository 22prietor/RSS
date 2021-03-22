package com.apptastic.rssreader;
import java.io.*;
import java.io.File;
import java.io.PrintWriter;
import java.lang.String;
import java.util.Scanner;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import com.apptastic.rssreader.Channel;
import com.apptastic.rssreader.DateTime;
import com.apptastic.rssreader.Item;
import com.apptastic.rssreader.RssReader;
/*
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
*/
import javax.net.ssl.SSLContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;

import static javax.xml.stream.XMLStreamConstants.CDATA;
import static javax.xml.stream.XMLStreamConstants.CHARACTERS;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
class Main {
  public static void main(String[] args) throws IOException {
    System.out.println(java.time.LocalDateTime.now().toString());
    RssReader reader = new RssReader();
    Stream<Item> rssFeed = reader.read("https://511nj.org/RSS511Service/RSS511Service.svc/rest/rss/RSSActiveWeather");
    File file = new File("data.txt");
    PrintWriter pw = new PrintWriter(file);
    pw.println(rssFeed);
    pw.close();
    Scanner scanner = new Scanner(file);
    List<String> incidents = new ArrayList<String>();
    scanner.useDelimiter(" -0500 ");
    while (scanner.hasNext()) {
      incidents.add(scanner.next() + " -0500 ");
    }

    int elements = incidents.size() - 1;
    String dates[] = new String[elements];
    String times[] = new String[elements];
    String events[] = new String[elements];
    String localDate = java.time.LocalDate.now().toString();
    String year = localDate.substring(0, localDate.indexOf("-"));
    for (int i = 0; i < elements; i++) {
      String s = String.valueOf(incidents.get(i));
      int origin = 0;
      for (int j = 0; j < s.length(); j++) {
        if ((j == 0 || j == s.length() - 1) && (int) (s.charAt(j)) < 48 || (int) (s.charAt(j)) > 57) {
          origin = j;
          break;
        }
        if (j > 0 && j < s.length() - 1 && ((int) (s.charAt(j)) < 48 || (int) (s.charAt(j)) > 57)
            && ((int) (s.charAt(j - 1)) < 48 || (int) (s.charAt(j - 1)) > 57)
            && ((int) (s.charAt(j + 1)) < 48 || (int) (s.charAt(j + 1)) > 57)) {
          origin = j;
          break;
        }
      }
      int index = 0;
      if (s.indexOf("Sun,") != -1) {
        index = s.indexOf("Sun,");
      }
      if (s.indexOf("Mon,") != -1) {
        index = s.indexOf("Mon,");
      }
      if (s.indexOf("Tues,") != -1) {
        index = s.indexOf("Tues,");
      }
      if (s.indexOf("Wed,") != -1) {
        index = s.indexOf("Wed,");
      }
      if (s.indexOf("Thu,") != -1) {
        index = s.indexOf("Thu,");
      }
      if (s.indexOf("Fri,") != -1) {
        index = s.indexOf("Fri,");
      }
      if (s.indexOf("Sat,") != -1) {
        index = s.indexOf("Sat,");
      }
      dates[i] = s.substring(index, s.lastIndexOf(year) + 4);
      times[i] = s.substring(s.lastIndexOf(year) + 5, s.indexOf(" -0500 "));
      events[i] = s.substring(origin, index - 1);
      if (s.toLowerCase().indexOf("flood") != -1) {
        System.out.println("Date: " + dates[i]);
        System.out.println("Time: " + times[i]);
        System.out.println("Event: " + events[i]);
        System.out.println(" ");
      }
    }
  }
}