package myers.test.handlers;

public class GameData {

    private int highscore;
    private int lastScore;
    private boolean musicOn;
    private float lastTime;
    private float bestTime;

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getLastScore() { return lastScore; }

    public void setLastScore(int lastScore) { this.lastScore = lastScore; }

    public float getLastTime(){ return lastTime; }

    public void setLastTime(float lastTime){ this.lastTime = lastTime; }

    public float getBestTime(){ return bestTime; }

    public void setBestTime(float bestTime){ this.bestTime = bestTime; }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public void printInfo(){
        System.out.printf("%2d. %-10s\n",highscore, musicOn);
        System.out.printf("%06d %5.1f\n",lastScore,lastTime);
    }
}