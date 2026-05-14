package helpz;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip musicClip;
    private String currentSong;

    // Music theme
    private boolean isMuted = false;

    // sound effect
    private boolean isBtnSfxOn = true;
    private boolean isShootSfxOn = true;

    private float currentVolumeDb = 0f;
    private int currentVolumeLevel = 5;

    public void playMusic(String filePath) {
        if (isMuted) return; // if turn off, then cannot load file

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

            applyCurrentVolume();

            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();

            currentSong = filePath;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // --- CÁC HÀM XỬ LÝ SFX ---

    public void playButtonSfx(String filePath) {
        if (!isBtnSfxOn) return; // Nếu tắt SFX thì không phát
        playSoundEffect(filePath);
    }

    public void playShootSfx(String filePath) {
        if (!isShootSfxOn) return; // Nếu tắt SFX thì không phát
        playSoundEffect(filePath);
    }

    // Hàm phụ trợ dùng chung để phát tiếng động ngắn (click, bắn)
    private void playSoundEffect(String filePath) {
        try {
            File sfxFile = new File(filePath);
            if (!sfxFile.exists()) return;

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sfxFile);
            Clip sfxClip = AudioSystem.getClip();
            sfxClip.open(audioStream);

            // Đồng bộ âm lượng SFX theo thanh Volume chung
            FloatControl gainControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(currentVolumeDb);

            sfxClip.start(); // Phát 1 lần, không lặp lại
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- CÁC HÀM GETTER / SETTER ---

    public void setVolume(int volumeLevel) {
        this.currentVolumeLevel = volumeLevel;

        if (volumeLevel == 0) {
            currentVolumeDb = -80.0f; // Tắt hẳn tiếng
        } else {
            // Chuyển đổi mức 1-10 thành decibel (dB)
            float minDb = -40.0f;
            float maxDb = 6.0f;
            currentVolumeDb = minDb + ((maxDb - minDb) * (volumeLevel - 1) / 9.0f);
        }
        if (!isMuted) {
            applyCurrentVolume();
        }
    }

    public void mute() {
        isMuted = true;
        if (musicClip != null && musicClip.isOpen()) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-80.0f); // Set về mức thấp nhất
        }
    }

    public void unmute() {
        isMuted = false;

        // Nếu trước đó đang có nhạc mà bị tắt ngang, ta bật lại luôn
        if (currentSong != null && (musicClip == null || !musicClip.isRunning())) {
            playMusic(currentSong);
        } else {
            applyCurrentVolume(); // Khôi phục lại âm lượng trước đó
        }
    }

    private void applyCurrentVolume() {
        if (musicClip != null && musicClip.isOpen()) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            if (isMuted) {
                gainControl.setValue(-80.0f);
            } else {
                gainControl.setValue(currentVolumeDb);
            }
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
        // Lưu ý: KHÔNG set currentSong = null ở đây, để lúc Unmute còn biết bài gì mà bật lại
    }

    public boolean isMuted() { return isMuted; }
    public int getVolumeLevel() { return currentVolumeLevel; }

    public boolean isButtonSfxOn() { return isBtnSfxOn; }
    public void setButtonSfxOn(boolean btnSfxOn) { this.isBtnSfxOn = btnSfxOn; }

    public boolean isShootSfxOn() { return isShootSfxOn; }
    public void setShootSfxOn(boolean shootSfxOn) { this.isShootSfxOn = shootSfxOn; }
}