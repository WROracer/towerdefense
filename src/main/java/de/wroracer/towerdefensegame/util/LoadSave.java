package de.wroracer.towerdefensegame.util;

import de.wroracer.towerdefensegame.managers.SoundManager;
import de.wroracer.towerdefensegame.objects.PathPoint;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import de.wroracer.towerdefensegame.objects.PathPoint;

public class LoadSave {

    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "TowerDefense";
    public static String levelFile = "level.txt";
    public static String filePath = homePath + File.separator + saveFolder + File.separator + levelFile;
    private static File lvlFile = new File(filePath);

    public static BufferedImage getSpriteAtlas() {
        InputStream is = LoadSave.class.getResourceAsStream("/spriteatlas.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static AudioInputStream getAudioInputStream(String sound){
        String url = "/sounds/"+sound+".wav";
        InputStream stream = LoadSave.class.getResourceAsStream(url);
        if (stream == null){
            return null;
        }
        AudioInputStream audioStream = null;
        try {
            assert stream != null;
            audioStream = AudioSystem.getAudioInputStream(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return audioStream;
    }

    //txt file
    public static void createNewFile() {
        File txtFile = new File("src/main/resources/testTextFile.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(File f, int[] idArr, PathPoint start, PathPoint end) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i : idArr) {
                pw.println(i);
            }
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> readFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PathPoint> getLevelPathPoints() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            if (list.size() <= 0) {
                points.add(new PathPoint(0, 19));
                points.add(new PathPoint(19, 2));
            } else {
                points.add(new PathPoint(list.get(400), list.get(401)));
                points.add(new PathPoint(list.get(402), list.get(403)));
            }
            return points;
        } else {
            System.out.println("File: " + lvlFile + " dose not exists!");
            return null;
        }
    }

    //Save 2d int Array
    public static void saveLevel(int[][] idArr, PathPoint start, PathPoint end) {
        if (lvlFile.exists()) {
            writeToFile(lvlFile, Utilz.twoDto1Dint(idArr), start, end);
        } else {
            System.out.println("File: " + lvlFile + " dose not exists!");
        }
    }

    // Load int Array
    public static int[][] getLevelData() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile(lvlFile);
            return Utilz.arrayListTo2dint(list, 20, 20);
        } else {
            System.out.println("File: " + lvlFile + " dose not exists!");
            return null;
        }
    }

    public static void createLevelFile() {
        if (!lvlFile.exists()) {
            try {
                if (!lvlFile.getParentFile().exists()) {
                    lvlFile.getParentFile().mkdir();
                }
                lvlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File: " + lvlFile + " already exists!");
        }
    }

    //Create a new lvl with default Values
    public static void createLevel(int[] idArr) {
        if (lvlFile.exists()) {
            System.out.println("File: " + lvlFile + " already exists!");
            return;
        } else {
            try {
                lvlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(lvlFile, idArr, new PathPoint(0, 0), new PathPoint(0, 0));

        }
    }

}
