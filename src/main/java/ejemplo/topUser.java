package ejemplo;

public class topUser {
    private final String username;
    private final int bestScore;

    public topUser(String username, int bestScore) {
        this.username = username;
        this.bestScore = bestScore;
    }

    public String getUsername() {
        return username;
    }

    public int getBestScore() {
        return bestScore;
    }
}