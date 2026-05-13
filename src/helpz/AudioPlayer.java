package helpz;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip musicClip;
    private String currentSong;

    public void playMusic(String filePath) {

        if (filePath.equals(currentSong) && musicClip != null && musicClip.isRunning()) {
            return;
        }

        stopMusic();

        try {
            File musicFile = new File(filePath);

            if (!musicFile.exists()) {
                System.out.println("Music file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);

            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();

            currentSong = filePath;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (musicClip != null) {
            if (musicClip.isRunning()) {
                musicClip.stop();
            }

            musicClip.close();
            musicClip = null;
        }

        currentSong = null;
    }
}