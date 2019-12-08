package mk.ukim.finki.np.vezbanjekol1;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("CategoryNotFoundException");
    }
}

class Category implements Comparable<Category> {
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }


    @Override
    public int compareTo(Category other) {
        return this.categoryName.compareTo(other.categoryName);
    }
}

abstract class NewsItem {
    private String title;
    private Date publishDate;
    private Category category;

    public NewsItem(String title, Date publishDate, Category category) {
        this.title = title;
        this.publishDate = publishDate;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getTitle() {
        return title;
    }

    abstract String getTeaser();
}

class TextNewsItem extends NewsItem {
    private String newsText;

    public TextNewsItem(String title, Date publishDate, Category category, String newsText) {
        super(title, publishDate, category);
        this.newsText = newsText;
    }

    @Override
    String getTeaser() {
        String name = this.getTitle();

        Date currentDate = new Date();

        long diff = currentDate.getTime() - this.getPublishDate().getTime();
        int minutes = Math.abs((int) TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));


        String minutesBefore = "" + minutes;
        String shortText;
        if (newsText.length() < 80) {
            shortText = newsText;
        } else {
            shortText = newsText.substring(0, 80);
        }

        return String.format("%s\n%s\n%s", name, minutesBefore, shortText);
    }
}

class MediaNewsItem extends NewsItem {
    private String url;
    private int views;

    public MediaNewsItem(String title, Date publishDate, Category category, String url, int views) {
        super(title, publishDate, category);
        this.url = url;
        this.views = views;
    }

    @Override
    String getTeaser() {
        String name = this.getTitle();

        Date currentDate = new Date();

        long diff = currentDate.getTime() - this.getPublishDate().getTime();
        int minutes = Math.abs((int) TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));


        String minutesBefore = "" + minutes;

        return String.format("%s\n%s\n%s\n%d", name, minutesBefore, url, views);
    }
}

class FrontPage {
    ArrayList<NewsItem> newsList;
    Category[] categories;

    public FrontPage(Category[] categories) {
        this.newsList = new ArrayList<>();
        this.categories = categories;
    }

    void addNewsItem(NewsItem newsItem) {
        newsList.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category) {
        List<NewsItem> returnList = new ArrayList<>();

        for (int i = 0; i < newsList.size(); i++) {
            if (newsList.get(i).getCategory().equals(category)) {
                returnList.add(newsList.get(i));
            }
        }

        return returnList;
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
//        List<NewsItem> returnList;
//        returnList = newsList.stream()
//                .filter(newsItem ->
//                        newsItem.getCategory().getCategoryName().equals(category)).collect(Collectors.toList());


        List<NewsItem> returnList = new ArrayList<>();

        for(int i = 0 ; i < newsList.size(); i++){
            if(newsList.get(i).getCategory().getCategoryName().equals(category)){
                returnList.add(newsList.get(i));
            }
        }

        if (returnList.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return returnList;
    }

    @Override
    public String toString() {
        return newsList.stream()
                .map(newsItem -> String.format("%s", newsItem.getTeaser()))
                .collect(Collectors.joining("\n"));
    }
}

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
