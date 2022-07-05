package de.wroracer.towerdefensegame.util;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;

import java.util.ArrayList;

public class Utilz {

    public static int[][] arrayListTo2dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];

        if (list.size() <= 0) {
            return createDefaultLevel();
        }

        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index);
            }
        }
        return newArr;
    }

    private static int[][] createDefaultLevel() {
        return new int[][] {
                { 0, 0, 0, 15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 18, 14, 14 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 1, 14, 14, 14, 19, 1, 1, 1, 1, 13, 4, 2 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 13, 0, 0, 0, 15, 1, 1, 1, 1, 13, 3, 0 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 13, 0, 0, 0, 15, 1, 1, 1, 1, 13, 3, 0 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 13, 0, 0, 0, 15, 1, 1, 1, 1, 13, 3, 0 },
                { 0, 0, 0, 15, 1, 1, 1, 1, 1, 12, 12, 12, 16, 1, 1, 1, 1, 13, 3, 0 },
                { 0, 0, 0, 8, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 11, 3, 0 },
                { 0, 0, 4, 2, 2, 2, 2, 5, 0, 0, 0, 4, 2, 2, 2, 2, 2, 2, 6, 0 },
                { 0, 0, 3, 9, 12, 12, 10, 3, 0, 0, 0, 3, 9, 12, 12, 12, 12, 10, 0, 0 },
                { 0, 0, 3, 15, 1, 1, 13, 3, 0, 0, 0, 3, 15, 1, 1, 1, 1, 13, 0, 9 },
                { 0, 0, 3, 15, 1, 1, 13, 7, 2, 2, 2, 6, 15, 18, 14, 14, 14, 11, 0, 8 },
                { 0, 0, 3, 15, 1, 1, 17, 12, 12, 12, 12, 12, 16, 13, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 1, 1, 18, 14, 14, 19, 1, 1, 13, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 1, 1, 13, 0, 0, 15, 1, 1, 17, 10, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 1, 1, 13, 0, 0, 15, 1, 1, 1, 13, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 18, 14, 11, 0, 0, 8, 14, 19, 1, 13, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 13, 0, 0, 0, 0, 0, 0, 15, 1, 13, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 15, 1, 17, 12, 12, 12, 12, 12, 12, 16, 1, 13, 0, 0, 0, 0, 0 },
                { 2, 2, 6, 8, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 11, 0, 0, 0, 0, 0 },
        };
    }

    public static int[] twoDto1Dint(int[][] twoArr) {
        int[] oneArr = new int[twoArr.length * twoArr[0].length];

        for (int j = 0; j < twoArr.length; j++) {
            for (int i = 0; i < twoArr[j].length; i++) {
                int index = j * twoArr.length + i;
                oneArr[index] = twoArr[j][i];
            }
        }
        return oneArr;
    }

    public static final int getHypoDistance(float x1, float y1, float x2, float y2) {
        float xDiff = abs(x1 - x2);
        float yDiff = abs(y1 - y2);

        return (int) hypot(xDiff, yDiff);
    }
}
