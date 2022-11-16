import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    private static final String URL = "https://lenta.ru/";

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("img");
            elements.forEach(element -> {
                String urlImg = element.attr("abs:src");
                String[] fragments = urlImg.split("/");
                if (fragments.length == 10) {
                    String fileName = fragments[fragments.length - 1];
                    String pathName = "images\\" + fileName;
                    try {
                        URL url = new URL(urlImg);
                        InputStream inputStream = url.openStream();
                        Files.copy(inputStream, Paths.get(pathName), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(fileName);
                } else {
                    System.out.println("Wrong url");
                }
            });

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
