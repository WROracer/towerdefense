package de.wroracer.towerdefensegame.managers;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.util.LoadSave;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoundManager {

    private List<SoundThread> soundThreads = new ArrayList<>();

    private Game game;

    public SoundManager(Game game){
        this.game = game;
    }

    public void playSound(String name){
        SoundThread t = new SoundThread(soundThreads.size(),name);
        t.start();
        soundThreads.add(t);
    }

        public static class SoundThread extends Thread {

            private Clip clip;
            private int id;
            private String sound;

            public SoundThread(int id,String sound){
               this.id = id;
                try {
                    clip = AudioSystem.getClip();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                initAudio(sound);
            }

            public void initAudio(String sound){
                this.sound = sound;
                if (clip.isOpen()){
                    clip.close();
                }
                try {
                    clip.open(LoadSave.getAudioInputStream(sound));
                } catch (LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void run() {
                if (clip!=null){
                    clip.start();
                }
                super.run();
            }

            public boolean isActive(){
                return clip.isActive();
            }

            public boolean isRunning(){
                return clip.isRunning();
            }
        }
}
