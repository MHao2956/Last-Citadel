package managers;

import events.Wave;
import scenes.Playing;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();

    public WaveManager(Playing playing){
        this.playing = playing;
        createWaves();
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0,1))));
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }
}
