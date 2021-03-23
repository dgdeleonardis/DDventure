package view;

import ddventuremain.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {
    //resources file name
    private static final String SONG_FILE_NAME = "music/bella_ciao.mp3";

    private static MusicManager instance = null;

    private MediaPlayer mediaPlayer;
    private boolean playing;
    private static final double VOLUME = 0.3;

    private MusicManager() {
        this.mediaPlayer = new MediaPlayer(
                new Media(this.getClass().getResource(SONG_FILE_NAME).toExternalForm())
        );
        this.mediaPlayer.setVolume(VOLUME);
        this.mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        this.playing = false;
    }

    public static MusicManager getInstance() {
        if(instance == null)
            instance = new MusicManager();
        return instance;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void playMusic() {
        this.mediaPlayer.play();
        this.playing = true;
    }

    public void stopMusic() {
        this.mediaPlayer.stop();
        this.playing = false;
    }
}
