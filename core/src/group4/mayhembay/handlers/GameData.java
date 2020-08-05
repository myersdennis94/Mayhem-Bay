package group4.mayhembay.handlers;

/**
 * This class contains the player's saved data.
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
     * This method returns the object's <b><CODE>highscore</CODE></b>.
     *
     * @return an <b><CODE>int</CODE></b> that is the highest score the player has achieved.
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     * This method sets the object's <b><CODE>highscore</CODE></b>.
     *
     * @param highscore an <b><CODE>int</CODE></b> that is the highest score the player has achieved.
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    /**
     * This method returns the object's <b><CODE>lastScore</CODE></b>.
     *
     * @return an <b><CODE>int</CODE></b> that is the most recent score the player has achieved.
     */
    public int getLastScore() {
        return lastScore;
    }

    /**
     * This method sets the object's <b><CODE>lastScore</CODE></b>.
     *
     * @param lastScore an <b><CODE>int</CODE></b> that is the most recent score the player has achieved.
     */
    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    /**
     * This method returns the object's <b><CODE>lastTime</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the most recent time the player has achieved.
     */
    public float getLastTime() {
        return lastTime;
    }

    /**
     * This method sets the object's <b><CODE>lastTime</CODE></b>.
     *
     * @param lastTime a <b><CODE>float</CODE></b> that is the most recent time the player has achieved.
     */
    public void setLastTime(float lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * This method returns the object's <b><CODE>bestTime</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the longest time the player has achieved.
     */
    public float getBestTime() {
        return bestTime;
    }

    /**
     * This method sets the object's <b><CODE>bestTime</CODE></b>.
     *
     * @param bestTime a <b><CODE>float</CODE></b> that is the longest time the player has achieved.
     */
    public void setBestTime(float bestTime) {
        this.bestTime = bestTime;
    }

    /**
     * This method returns the name of the object's <b><CODE>ship</CODE></b>.
     *
     * @return a <b><CODE>String</CODE></b> that is the name of the player's most recently selected ship.
     */
    public String getShip(){ return ship; }

    /**
     * This method sets the name of the object's <b><CODE>ship</CODE></b>.
     *
     * @param ship a <b><CODE>String</CODE></b> that is the name of the player's most recently selected ship.
     */
    public void setShip(String ship){ this.ship = ship; }

    /**
     * This method returns the value of <b><CODE>musicOn</CODE></b>.
     *
     * @return a <b><CODE>boolean</CODE></b> that is true if the music setting is on; false, if the music setting is off.
     */
    public boolean isMusicOn() {
        return musicOn;
    }

    /**
     * This method sets the value of <b><CODE>musicOn</CODE></b>.
     *
     * @param musicOn a <b><CODE>boolean</CODE></b> that is true if the music setting is on; false, if the music setting is off.
     */
    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    /**
     * This method return's the <b><CODE>totalScore</CODE></b> from the most recent play.
     *
     * @return a <b><CODE>int</CODE></b> that is the totalScore of the most recent play.
     */
    public int getTotalScoreScore(){
        totalScore = (int) (getLastScore() * getLastTime());
        return totalScore;
    }
}
