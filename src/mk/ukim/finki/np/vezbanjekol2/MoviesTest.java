package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;
import java.util.stream.Collectors;

class Movie {
    String title;
    ArrayList<Integer> ratingsList;

    public Movie(String title, int[] ratings) {
        this.title = title;
        ratingsList = new ArrayList<>();
        for (int rating : ratings) {
            ratingsList.add(rating);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRatingsList() {
        return ratingsList;
    }

    public double getAverageRaiting() {
        OptionalDouble average = ratingsList.stream().mapToInt(a -> a).average();
        if (average.isPresent()) {
            return average.getAsDouble();
        } else {
            return 0;
        }
    }

    public double getRatingCoefficient() {
        return getAverageRaiting() * ratingsList.size() * 1.0;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, getAverageRaiting(), ratingsList.size());
    }
}

class MoviesList {
    SortedSet<Movie> sortedByAvgRating;
    SortedSet<Movie> sortedByRatingCoefficient;
//    List<Movie> moviesList;
    /*
        Poraka do Aco pred 2kol (da ne se cudas so prava kodo):

        Komentiranite funkcii, pretstavuvaat varijanta na resenie vo koe, site filmovi pri dodavanjeto se cuvaat
        vo edna lista, i potoa pri povik na sekoj metod, listata so filmovi se strima, se sortira prema soodvetniot
        komparator i se zemaat (limitiraat) 10 elementi bidejki top10 barame;

        Istata funkcionalnost e dobiena so pomos na dva setovi soodvetni za sekoj metod, i elementite vo tie setovi
        se sortirani spored soodvetnite baranje so sto komparatorot e daden vo konstruktorite na setovite.

     */

    public MoviesList() {
        //Inicijaliziranje na dva prazni setovi so soodvetnite komparatori;
        sortedByAvgRating = new TreeSet<>(
                Comparator.comparing(Movie::getAverageRaiting).reversed().thenComparing(Movie::getTitle)
        );

        sortedByRatingCoefficient = new TreeSet<>(
                Comparator.comparing(Movie::getRatingCoefficient).reversed().thenComparing(Movie::getTitle)
        );
    }

    public void addMovie(String title, int[] ratings) {

        /*
            Varijanta site da se stavat vo set;
            sortedByAvgRating.add(movieToBeAdded);
            sortedByRatingCoefficient.add(movieToBeAdded);

            Nema zaso da gi cuvam site u seto, ka mi trebat samo 10, mozam samo 10-te soodvetno najgolemi
            da gi cuvam;
         */
        Movie movieToBeAdded = new Movie(title, ratings);
        if (sortedByAvgRating.size() > 10) {
            sortedByAvgRating.remove(sortedByAvgRating.last());
        }
        sortedByAvgRating.add(movieToBeAdded);

        if (sortedByRatingCoefficient.size() > 10) {
            sortedByRatingCoefficient.remove(sortedByRatingCoefficient.last());
        }
        sortedByRatingCoefficient.add(movieToBeAdded);


    }

    public List<Movie> top10ByAvgRating() {
        return sortedByAvgRating.stream()
                .limit(10)
                .collect(Collectors.toList());
    }

//    public List<Movie> top10ByAvgRating() {
//        return moviesList.stream()
//                .sorted(
//                        Comparator.comparing(Movie::getAverageRaiting).reversed().thenComparing(Movie::getTitle)
//                )
//                .limit(10)
//                .collect(Collectors.toList());
//    }

    public List<Movie> top10ByRatingCoef() {
        return sortedByRatingCoefficient.stream()
                .limit(10)
                .collect(Collectors.toList());
    }

//    public List<Movie> top10ByRatingCoef() {
//        return moviesList.stream()
//                .sorted(
//                        Comparator.comparing(Movie::getRatingCoefficient).reversed().thenComparing(Movie::getTitle)
//                )
//                .limit(10)
//                .collect(Collectors.toList());
//    }

}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
