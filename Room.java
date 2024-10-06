package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.apache.commons.collections.list.CursorableLinkedList;

import java.util.*;

public class Room implements Comparable<Room> {
    Point upperLeft;
    Point upperRight;
    Point lowerLeft;
    Point lowerRight;
    Point center;
    static List<Room> rooms = new ArrayList<>();
    public Room(Point ul, Point ur, Point ll, Point lr, Point c) {
        upperLeft = ul;
        upperRight = ur;
        lowerLeft = ll;
        lowerRight = lr;
        center = c;
        rooms.add(this);
    }


    @Override
    public int compareTo(Room o) {
        return (int) Math.sqrt(Math.pow((this.center.y - o.center.y), 2) + Math.pow((this.center.x - o.center.x), 2));
    }

    //make path
    public static TETile[][] makePath(TETile[][] bo) {

        TETile[][] board = new TETile[bo.length][bo[0].length];
        for (int i = 0; i < bo.length; i++) {
            System.arraycopy(bo[i], 0, board[i], 0, bo[i].length);
        }
        HashMap<Room, Integer> visit = new HashMap<>();
        int count = 0;
        for(int i = 0; i < rooms.size(); i++) {
            visit.put(rooms.get(i), 0);
        }
//        while(count < rooms.size()) {
//            for (Room y : visit.keySet()) {
//                y = rooms.get(count);
//                count++;
//            }
//        }
//        for(int x : visit.values()) {
//            x = 0;
//        }
        //start with one room then go to adj
        Set<Room> visited = new HashSet<>();
        for(Room r : rooms) {
            if(visited.contains(r)) {
                break;
            }
            visited.add(r);
            List<Room> rest = new ArrayList<>();
            if(visited.size() == rooms.size()) {
                break;
            }
            for(Room x : rooms) {
                if(!visited.contains(x)) {
                    rest.add(x);
                }
            }
            Room to = getAdj(r, rest);
            board = makePaths(to, r, board);
//                if (visit.get(x) != 2) {
//                    withr.add(x);
//                }
//            }

        }
//
//        for (Room r : rooms) {
//            if (visit.get(r) == 2) {
//                break;
//            }
//            List<Room> withr = new ArrayList<>();
//            for(Room x : rooms) {
//                if (visit.get(x) != 2) {
//                    withr.add(x);
//                }
//            }
//            withr.remove(r);
//            Room to = getAdj(r, withr);
//            visit.put(to, visit.get(to) + 1);
//            //visit.put(r, visit.get(r) + 1);
//            board = makePaths(to, r, board);
//            //board[rooms.get(rooms.size() - 1).center.x][rooms.get(rooms.size() - 1).center.y] = Tileset.GRASS;
//        }
        return board;
    }

    public static Room isValid(Room r, Room t, TETile[][] bo) {
        int diffx = Math.abs(r.center.x - t.center.x);
        int diffy = Math.abs(r.center.y - t.center.y);
        if (r.center.x + diffx >= bo.length || r.center.y + diffy >= bo[0].length) {
            return t; // Return r, not t
        }
        return r;
    }

    public static TETile[][] makePaths(Room r, Room t, TETile[][] bo) {
        TETile[][] board = new TETile[bo.length][bo[0].length];
        for (int i = 0; i < bo.length; i++) {
            System.arraycopy(bo[i], 0, board[i], 0, bo[i].length);
        }

        int diffx = 10001000;
        int diffy = 10000000;

        // Check if the paths need to be drawn horizontally
        while (diffx != Math.max(r.center.x, t.center.x) || diffy != Math.max(r.center.y, t.center.y)) {
            for (int i = Math.min(r.center.x, t.center.x); i <= Math.max(r.center.x, t.center.x); i++) {
                int y = 0;
                if (Math.min(r.center.x, t.center.x) == r.center.x) {
                    y = r.center.y;
                } else {
                    y = t.center.y;
                }
                // Check if the tile is a WALL before setting it as a path tile
                if (board[i][y] != Tileset.WALL) {
                    board[i][y] = Tileset.FLOWER;
                } else {
                    board[i][y] = Tileset.LOCKED_DOOR;
                }
                diffx = i;
            }

            // Check if the paths need to be drawn vertically
            for (int i = Math.min(r.center.y, t.center.y); i <= Math.max(r.center.y, t.center.y); i++) {
                // Check if the tile is a WALL before setting it as a path tile
                if (board[diffx][i] != Tileset.WALL) {
                    board[diffx][i] = Tileset.FLOWER;
                }
//                    board[diffx][i] = Tileset.LOCKED_DOOR;
//                }
                diffy = i;
            }
        }

        // Connect the endpoints of the path with a straight line
        connectPoints(r.center, t.center, board);
        addPathBorders(board);
        return board;
    }

    // Helper function to connect two points with a straight line
    private static void connectPoints(Point p1, Point p2, TETile[][] board) {
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;

        int dx = x2 - x1;
        int dy = y2 - y1;

        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = absDx - absDy;

        while (x1 != x2 || y1 != y2) {
            if (board[x1][y1] != Tileset.WALL) {
                board[x1][y1] = Tileset.FLOWER;
            } else {
                board[x1][y1] = Tileset.UNLOCKED_DOOR;
            }

            int e2 = err * 2;

            if (e2 > -absDy) {
                err -= absDy;
                x1 += sx;
            }
            if (e2 < absDx) {
                err += absDx;
                y1 += sy;
            }
        }
    }
    public static TETile[][] addPathBorders(TETile[][] board) {
        TETile[][] updatedBoard = new TETile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, updatedBoard[i], 0, board[i].length);
        }

        for (int x = 1; x < board.length - 1; x++) {
            for (int y = 1; y < board[0].length - 1; y++) {
                if (board[x][y] == Tileset.FLOWER) {
                    if (board[x + 1][y] == Tileset.NOTHING) {
                        updatedBoard[x + 1][y] = Tileset.WALL;
                    }
                    if (board[x - 1][y] == Tileset.NOTHING) {
                        updatedBoard[x - 1][y] = Tileset.WALL;
                    }
                    if (board[x][y + 1] == Tileset.NOTHING) {
                        updatedBoard[x][y + 1] = Tileset.WALL;
                    }
                    if (board[x][y - 1] == Tileset.NOTHING) {
                        updatedBoard[x][y - 1] = Tileset.WALL;
                    }
                }
            }
        }

        return updatedBoard;
    }

    public static void onePath(TETile[][] bo) {
        Set<Room> tilemarked = new HashSet<>();
        //ystem.out.print(Path.isPathCoveringAllRooms(bo));

    }




    public static Room getAdj(Room one, List<Room> ro) {
        Room closest = ro.get(0);
        for (Room r : ro) {
            int distanceX = Math.abs(r.center.x - one.center.x);
            int distanceY = Math.abs(r.center.y - one.center.y);

            int closestDistanceX = Math.abs(closest.center.x - one.center.x);
            int closestDistanceY = Math.abs(closest.center.y - one.center.y);

            // Check if the current room is closer in both x and y dimensions
            if (distanceX <= closestDistanceX && distanceY <= closestDistanceY) {
                closest = r;
            }
        }
        return closest;
    }


//    //get adjacent node
//
//
////
////    public static List<Point> findPath(Room from, Room to, TETile[][] grid) {
////        Queue<Point> queue = new LinkedList<>();
////        Map<Point, Point> parentMap = new HashMap<>();
////        Set<Point> visited = new HashSet<>();
////
////        queue.add(from.center);
////        visited.add(from.center);
////
////        while (!queue.isEmpty()) {
////            Point current = queue.poll();
////            if (current.equals(to.center)) {
////                System.out.println(to.center.x + " " + to.center.y);
////                System.out.println(from.center.x + " " + from.center.y);
////                // Reconstruct the path
////                return reconstructPath(parentMap, current);
////            }
////            boolean contain = false;
////            // Explore neighbors
////            for (Point neighbor : getNeighbors(current, grid)) {
////                for(Point p : visited) {
////                    if(p.x == neighbor.x && p.y == neighbor.y) {
////
////                        contain = true;
////                    }
////                }
////
////                if (!contain){
////                    System.out.println(neighbor.x + " " + neighbor.y);
////                    queue.add(neighbor);
////                    visited.add(neighbor);
////                    parentMap.put(neighbor, current);
////                }
////            }
////        }
////        return null;
////    }
//
//    // Method to get adjacent neighbors of a point
////    private static List<Point> getNeighbors(Point point, TETile[][] grid) {
////        int x = point.x;
////        int y = point.y;
////        int numRows = grid.length;
////        int numColumns = grid[0].length;
////        List<Point> neighbors = new ArrayList<>();
////
////        // Add all valid adjacent points
////        //check x and y values
////        if (x > 0)
////            neighbors.add(new Point(x - 1, y));
////        if (x < numColumns - 1)
////            neighbors.add(new Point(x + 1, y));
////        if (y > 0)
////            neighbors.add(new Point(x, y - 1));
////        if (y < numRows - 1)
////            neighbors.add(new Point(x, y + 1));
////
////        return neighbors;
////    }
//
//    // Method to reconstruct the path from start to end using parentMap
////    private static List<Point> reconstructPath(Map<Point, Point> parentMap, Point end) {
////        List<Point> path = new LinkedList<>();
////        Point current = end;
////        while (current != null) {
////            //System.out.println(current);
////            path.add(0, current);
////            current = parentMap.get(current);
////        }
////        return path;
////    }
}