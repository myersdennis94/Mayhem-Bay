package myers.test.handlers;

/**
 *
 */
public class GameData {

    private int totalScore;
    private int highscore;
    private int lastScore;
    private boolean musicOn;
    private float lastTime;
    private float bestTime;
    private String ship;

    /**
     * @return
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     * @param highscore
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    /**
     * @return
     */
    public int getLastScore() {
        return lastScore;
    }

    /**
     * @param lastScore
     */
    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    /**
     * @return
     */
    public float getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime
     */
    public void setLastTime(float lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * @return
     */
    public float getBestTime() {
        return bestTime;
    }

    /**
     * @param bestTime
     */
    public void setBestTime(float bestTime) {
        this.bestTime = bestTime;
    }

    public String getShip(){ return ship; }

    public void setShip(String ship){ this.ship = ship; }

    /**
     * @return
     */
    public boolean isMusicOn() {
        return musicOn;
    }

    /**
     * @param musicOn
     */
    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    /**
     *
     */
    public void printInfo() {
        System.out.printf("%2d. %-10s\n", highscore, musicOn);
        System.out.printf("%06d %5.1f\n", lastScore, lastTime);
    }

    public int getTotalScoreScore(){
        totalScore = (int) (getLastScore() * getLastTime());
        return totalScore;
    }
}
