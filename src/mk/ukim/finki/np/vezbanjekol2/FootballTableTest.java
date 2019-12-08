package mk.ukim.finki.np.vezbanjekol2;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class FootballTeam {
    String teamName;
    Integer totalMatches;
    Integer totalWins;
    Integer totalDraws;
    Integer totalLosses;
    Integer totalPoints;
    Integer scoredGoals;
    Integer receivedGoals;

    public FootballTeam(String teamName) {
        this.teamName = teamName;
        this.totalMatches = 0;
        this.totalWins = 0;
        this.totalDraws = 0;
        this.totalLosses = 0;
        this.totalPoints = 0;
        this.scoredGoals = 0;
        this.receivedGoals =0;
    }

    public String getTeamName() {
        return teamName;
    }


    public Integer getScoredGoals() {
        return scoredGoals;
    }

    public Integer getReceivedGoals() {
        return receivedGoals;
    }

    public Integer getTotalMatches() {
        return totalMatches;
    }

    public Integer getTotalWins() {
        return totalWins;
    }

    public Integer getTotalDraws() {
        return totalDraws;
    }

    public Integer getTotalLosses() {
        return totalLosses;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Integer getGoalDifference(){
        return this.scoredGoals - this.receivedGoals;
    }

    public void increaseTotalWins(){
        this.totalWins++;
    }

    public void increaseTotalMatches(){
        this.totalMatches++;
    }

    public void increaseTotalDraws(){
        this.totalDraws++;
    }

    public void increaseTotalLosses(){
        this.totalLosses++;
    }

    public void increaseScoredGoals(){
        this.scoredGoals++;
    }

    public void increaseReceivedGoals(){
        this.receivedGoals++;
    }

    public void setScoredGoals(Integer scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public void setReceivedGoals(Integer receivedGoals) {
        this.receivedGoals = receivedGoals;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.getClass()!=obj.getClass()){
            return false;
        }
        else{
            FootballTeam otherTeam = (FootballTeam) obj;
            return this.teamName.equalsIgnoreCase(otherTeam.teamName);
        }
    }


}

class FootballMatch{
    String homeTeam;
    String awayTeam;
    Integer homeGoals;
    Integer awayGoals;

    public FootballMatch(String homeTeam, String awayTeam, Integer homeGoals, Integer awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }
}

class FootballTable {
    Set<FootballTeam> footballTeams;

    public FootballTable() {
        footballTeams = new TreeSet<>(
                Comparator.comparing(FootballTeam::getTotalPoints).reversed()
                        .thenComparing(FootballTeam::getGoalDifference).reversed()
                        .thenComparing(FootballTeam::getTeamName)
        );
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        FootballMatch matchToProcess = new FootballMatch(homeTeam,awayTeam,homeGoals,awayGoals);
    }

    public void processMatch(FootballMatch matchToProcess){
        /*
            Ishodot na eden fudbalski mech e:
                1. Pobeduva domakin homeTeam;
                2. Izgubuva domakin homeTeam, znaci pobeduva awayTeam;
                3. Nereseno;
            Moze fudbalskata ekipa da postoi vo setot, a moze i da ne postoi;
            Ako postoi, samo sodovetni inkerementi;
            Ako ne postoi, se dodava;
         */
        FootballTeam homeTeam = new FootballTeam(matchToProcess.getHomeTeam());
        FootballTeam awayTeam = new FootballTeam(matchToProcess.getAwayTeam());

        if(footballTeams.contains(homeTeam)){
            if(matchToProcess.getHomeGoals()>matchToProcess.getAwayGoals()){
                //Home timot e pobednik
                homeTeam.increaseTotalMatches();
                homeTeam.increaseTotalWins();
                homeTeam.setScoredGoals(homeTeam.getScoredGoals() + matchToProcess.getHomeGoals());
                homeTeam.setReceivedGoals(homeTeam.getReceivedGoals() + matchToProcess.getAwayGoals());
            }
        }
        else{

        }

        if(footballTeams.contains(awayTeam)){

        }
        else{

        }

    }


}

public class FootballTableTest {

}
