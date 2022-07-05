package de.wroracer.towerdefensegame.objects;

import java.util.Objects;

public class PathPoint {

    private int xCord, yCord;

    public PathPoint(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    @Override
    public String toString() {
        return "PathPoint{" +
                "xCord=" + xCord +
                ", yCord=" + yCord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PathPoint pathPoint = (PathPoint) o;
        return xCord == pathPoint.xCord && yCord == pathPoint.yCord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCord, yCord);
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

}
