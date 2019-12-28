package mk.ukim.finki.np.vezbanjekol2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class FootballTeam {
    String name;
    Integer wins;
    Integer draws;
    Integer losses;
    Integer scoredGoals;
    Integer takenGoals;

    public FootballTeam(String name) {
        this.name = name;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.scoredGoals = 0;
        this.takenGoals = 0;
    }

    private void addGoalsFromMatch(Integer scored, Integer taken) {
        this.scoredGoals += scored;
        this.takenGoals += taken;
    }

    public void addWin(Integer scored, Integer taken) {
        this.wins++;
        this.addGoalsFromMatch(scored, taken);
    }

    public void addLoss(Integer scored, Integer taken) {
        this.losses++;
        this.addGoalsFromMatch(scored, taken);
    }

    public void addDraw(Integer scored, Integer taken) {
        this.draws++;
        this.addGoalsFromMatch(scored, taken);
    }

    public int teamPoint() {
        return wins * 3 + draws;
    }

    public int goalDifference() {
        return scoredGoals - takenGoals;
    }


    @Override
    public String toString() {
        return String.format("%-15s%5s%5s%5s%5s%5s",
                this.name,
                this.wins + this.draws + this.losses, //P
                this.wins, //W
                this.draws, //D
                this.losses, //L
                this.teamPoint() //PTS
        );
    }

    public String getName() {
        return name;
    }
}

class FootballTable {
    Map<String, FootballTeam> footballTeamMap;

    public FootballTable() {
        footballTeamMap = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        //Proverka dali timovite gi ima vo mapata, ako gi nema gi dodavam blanko;
        FootballTeam home = new FootballTeam(homeTeam);
        FootballTeam away = new FootballTeam(awayTeam);

        //Ako gi nema, gi dodavam
        if (!footballTeamMap.containsKey(homeTeam)) {
            footballTeamMap.put(homeTeam, home);
        }
        if (!footballTeamMap.containsKey(awayTeam)) {
            footballTeamMap.put(awayTeam, away);
        }

        //Veke sekako gi ima, pa gi zemam;
        home = footballTeamMap.get(homeTeam);
        away = footballTeamMap.get(awayTeam);

        //Procesiram go matchot vo zavisnost od rezultatot homeGoals : awayGoals;
        if (homeGoals > awayGoals) {
            //Pobednik e domakinot;
            home.addWin(homeGoals, awayGoals);
            away.addLoss(awayGoals, homeGoals);
        } else if (homeGoals < awayGoals) {
            //Pobednik e gostinot;
            home.addLoss(homeGoals, awayGoals);
            away.addWin(awayGoals, homeGoals);
        } else {
            //Inaku nereseno e;
            home.addDraw(homeGoals, awayGoals);
            away.addDraw(awayGoals, homeGoals);
        }

        //Sega novoto procesiranje go dodavame vo mapata, pa:
        footballTeamMap.put(homeTeam, home);
        footballTeamMap.put(awayTeam, away);
    }

    public void printTable() {
        AtomicInteger counter = new AtomicInteger(0);
        footballTeamMap.values().stream()
                .sorted(Comparator
                        .comparing(FootballTeam::teamPoint)
                        .thenComparing(FootballTeam::goalDifference).reversed()
                        .thenComparing(FootballTeam::getName)
                )
                .forEach(footballTeam -> System.out.println(String.format("%2d. ", counter.incrementAndGet()) + footballTeam.toString()));
    }
}


public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}
