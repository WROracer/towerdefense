package de.wroracer.towerdefensegame.util.astar;

import de.wroracer.towerdefensegame.objects.PathPoint;
import de.wroracer.towerdefensegame.util.Utilz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Astar {

    /**
     * @param lvl 2 = walkable, other non-walkable
     * @param start
     * @param end
     * @return
     */
    public static List<PathPoint> aStar(int[][] lvl, PathPoint start, PathPoint end) {
        List<PathPoint> openSet = getOpenPoints(lvl);

        HashMap<PathPoint, PathPoint> cameFrom = new HashMap<PathPoint, PathPoint>();

        HashMap<PathPoint, Integer> gScore = new HashMap<PathPoint, Integer>();

        for (PathPoint p : openSet) {
            gScore.put(p, Integer.MAX_VALUE);
        }

        gScore.put(start, 0);

        HashMap<PathPoint, Integer> fScore = new HashMap<PathPoint, Integer>();

        for (PathPoint p : openSet) {
            fScore.put(p, Integer.MAX_VALUE);
        }

        fScore.put(start, heuristic(start, end));

        while (openSet.size() > 0) {
            // find the most optimal path here

            PathPoint current = getLowestScore(openSet, fScore);

            if (current.equals(end)) {
                return constructPath(cameFrom, current);
            }

            openSet.remove(current);

            for (PathPoint neighbor : getNeighbors(lvl, current)) {

                int tentative_gScore = gScore.get(current) + 1;

                if (tentative_gScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentative_gScore);
                    fScore.put(neighbor, gScore.get(neighbor) + heuristic(neighbor, end));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }

        }

        return null;
    }

    private static int heuristic(PathPoint a, PathPoint b) {
        return Math.abs(a.getxCord() - b.getxCord()) + Math.abs(a.getyCord() - b.getyCord());
    }

    private static List<PathPoint> constructPath(HashMap<PathPoint, PathPoint> cameFrom, PathPoint current) {
        List<PathPoint> path = new ArrayList<PathPoint>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        // reverse the path

        List<PathPoint> reversedPath = new ArrayList<PathPoint>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }

    private static List<PathPoint> getNeighbors(int[][] lvl, PathPoint current) {
        List<PathPoint> neighbors = new ArrayList<PathPoint>();

        int x = current.getxCord();
        int y = current.getyCord();

        // top
        if (y > 0 && lvl[y - 1][x] == 2) {
            neighbors.add(new PathPoint(x, y - 1));
        }

        // right
        if (x < lvl[0].length - 1 && lvl[y][x + 1] == 2) {
            neighbors.add(new PathPoint(x + 1, y));
        }

        // bottom
        if (y < lvl.length - 1 && lvl[y + 1][x] == 2) {
            neighbors.add(new PathPoint(x, y + 1));
        }

        // left
        if (x > 0 && lvl[y][x - 1] == 2) {
            neighbors.add(new PathPoint(x - 1, y));
        }

        return neighbors;
    }

    private static PathPoint getLowestScore(List<PathPoint> set, HashMap<PathPoint, Integer> score) {
        PathPoint lowest = set.get(0);
        for (PathPoint p : set) {
            if (score.get(p) < score.get(lowest)) {
                lowest = p;
            }
        }
        return lowest;
    }

    private static List<PathPoint> getOpenPoints(int[][] lvl) {
        // get all 2's in lvl as PathPoints

        List<PathPoint> points = new ArrayList<PathPoint>();

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                if (lvl[y][x] == 2) {
                    // add PathPoint to list
                    PathPoint point = new PathPoint(x, y);
                    points.add(point);
                }
            }
        }
        return points;
    }
}
