package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;


public class Board extends Engine {

    TETile[][] board;
    int width;
    int height;


    public Board(int w, int h, TETile[][] toe) {
        width = w;
        height = h;
        board = toe;
    }
    public TETile[][] changeBoard(Random s, TETile[][] b) {
        Random seed = s;
        board = b;
        int tileNum =  RandomUtils.uniform(seed,2);
        Board boardInstance =  new Board(width, height, board);
        switch(tileNum) {
            case 1:
                board = boardInstance.makeSquareRoom(RandomUtils.uniform(seed,10), RandomUtils.uniform(seed,40), RandomUtils.uniform(seed,40), board);
            case 0:
                board = boardInstance.makeRectRoom(RandomUtils.uniform(seed,10), RandomUtils.uniform(seed,15), RandomUtils.uniform(seed,40), RandomUtils.uniform(seed,40), board);
            default:
                board = boardInstance.makeSquareRoom(RandomUtils.uniform(seed,10), RandomUtils.uniform(seed,40), RandomUtils.uniform(seed,40), board);
        }
        return board;
    }
    public TETile[][] fill(int w, int h, int posx, int posy, TETile[][] b) {
        board = b;
        for (int i = w - 2; i >= 0; i--) {
            for (int j = h - 2; j >= 0; j--) {
                board[posx + i][posy + j] = Tileset.FLOWER;
//                    board[posx + i][posy + h - 1] = Tileset.WALL;
            }
        }

//        for (int j = 0; j < 2; j++) {
//            for (int i = h - 1; i >= 0; i--) {
//                if (j == 0) {
//                    board[posx][posy + i] = Tileset.WALL;
//                }
//                if (j == 1) {
//                    board[posx + w - 1][posy + i] = Tileset.WALL;
//                }
//            }
//        }
        return board;
    }
    public boolean isValid(int w, int h, int posx, int posy, TETile[][] b) {
        for (int j = 0; j < 2; j++) {
            for (int i = w - 1; i >= 0; i--) {
                if (j == 0) {
                    if(board[posx + i][posy] != Tileset.FLOOR) {
                        return false;
                    }
                }
                if (j == 1) {
                    if(board[posx + i][posy + h - 1] != Tileset.FLOOR) {
                        return false;
                    }
                }
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int i = h - 1; i >= 0; i--) {
                if (j == 0) {
                    if(board[posx][posy + i] != Tileset.FLOOR) {
                        return false;
                    }
                }
                if (j == 1) {
                    if(board[posx + w - 1][posy + i] != Tileset.FLOOR) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public TETile[][] makeSquareRoom(int w, int posx, int posy, TETile[][] b) {
        if(!isValid(w, w, posx, posy, b) || w <= 2) {
            return b;
        }
        fill(w,w,posx,posy, b);
        board = b;
        for (int j = 0; j < 2; j++) {
            for (int i = w - 1; i >= 0; i--) {
                if (j == 0) {
                    board[posx + i][posy] = Tileset.WALL;
                    board[posx][posy + i] = Tileset.WALL;
                }
                if (j == 1) {
                    board[posx + i][posy + w - 1] = Tileset.WALL;
                    board[posx + w - 1][posy + i] = Tileset.WALL;
                }
            }
        }
        Room x = new Room(new Point(posx + w, posy), new Point (posx + w, posy + w), new Point (posx, posy + w), new Point(posx, posy), new Point(posx + w / 2, posy + w / 2));
        //taticSystem.out.print(Room.rooms.size());
        return board;
    }

    public TETile[][] makeRectRoom(int w, int h, int posx, int posy, TETile[][] b) {
        if( w <= 2 || h <= 2 || posx + w >= b.length || posy + h >= b[0].length || !isValid(w, h, posx, posy, b) ) {
            return b;
        }
        fill(w,h,posx,posy, b);
        board = b;
        for (int j = 0; j < 2; j++) {
            for (int i = w - 1; i >= 0; i--) {
                if (j == 0) {
                    board[posx + i][posy] = Tileset.WALL;
                }
                if (j == 1) {
                    board[posx + i][posy + h - 1] = Tileset.WALL;
                }
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int i = h - 1; i >= 0; i--) {
                if (j == 0) {
                    board[posx][posy + i] = Tileset.WALL;
                }
                if (j == 1) {
                    board[posx + w - 1][posy + i] = Tileset.WALL;
                }
            }
        }
        Room x = new Room(new Point(posx + w, posy), new Point (posx + w, posy + h), new Point (posx, posy + h), new Point(posx, posy), new Point(posx + w / 2, posy + h / 2));
        return board;
    }


    public TETile[][] createHallways(Random s, TETile[][] worldGrid) {
        for (int i = 0; i < Room.rooms.size(); i++) {
            Room currRoom = Room.rooms.get(i);
            for (int j = i + 1; j < Room.rooms.size(); j++) {
                Room nextRoom = Room.rooms.get(j);
                if (areRoomsAdjacents(currRoom, nextRoom) == true) {
                    createHallwayBetweenRooms(currRoom, nextRoom, worldGrid);
                }
            }
        }
        return worldGrid;
    }
    public void moveAvatar() {

    }


    public boolean areRoomsAdjacents(Room room1, Room room2) {
        int x0 = room1.center.x;
        int y0 = room1.center.y;
        int x1 = room2.center.x;
        int y1 = room2.center.y;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = Integer.compare(x1, x0);
        int sy = Integer.compare(y1, y0);
        int err = dx - dy;

        while (x0 != x1 || y0 != y1) {
            if (board[x0][y0] != Tileset.WALL) {
                // There is an obstacle or another room in between the two points
                return false;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        // The line-of-sight is clear, so the rooms are adjacent
        return true;
    }











    public void generateHallway(Room one, Room two) {
        Point start = one.center;
        Point goal = two.center;

        // Implement A* algorithm to find the shortest path between start and goal

        List<Point> path = aStarSearch(start, goal);
        System.out.print("Done");

        // Generate hallways based on the path found by A*
        // You can use Bresenham's line algorithm to connect the cells in the path
        generateHallwayFromPath(path);
    }
    private List<Point> aStarSearch(Point start, Point goal) {
        Map<Point, Point> cameFrom = new HashMap<>();
        Map<Point, Integer> costSoFar = new HashMap<>();
        PriorityQueue<Point> frontier = new PriorityQueue<>(Comparator.comparingInt(point -> costSoFar.getOrDefault(point, Integer.MAX_VALUE)));
        System.out.print("Done");
        frontier.add(start);
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Point current = frontier.poll();

            if (current.equals(goal)) {
                break;
            }

            for (Point next : getNeighbors(current)) {
                int newCost = costSoFar.get(current) + 1; // In this example, the cost is the same for all moves

                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    int priority = newCost + heuristic(goal, next); // Heuristic function estimates the cost to reach the goal

                    // Remove the next point from the frontier if it's already present with a higher priority
                    // This step ensures that we explore the most promising paths first
                    frontier.remove(next);

                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        //System.out.print("Done");
        // Reconstruct the path from start to goal
        List<Point> path = new ArrayList<>();
        Point current = goal;
        while (current != null) {
            path.add(0, current);
            current = cameFrom.get(current);
        }

        // If the goal is unreachable, return an empty list
        if (!path.contains(start)) {
            return Collections.emptyList();
        }
        //System.out.print("Done");
        return path;
    }


    private int heuristic(Point goal, Point next) {
        // Implement a heuristic function to estimate the cost to reach the goal from the next cell
        // In this example, you can use the Euclidean distance as the heuristic for straight hallways
        return Math.abs(goal.x - next.x) + Math.abs(goal.y - next.y);
    }

    private List<Point> getNeighbors(Point current) {
        TETile[][] grid = board;
        // Implement logic to get neighboring points that can be moved to from the current point
        // This should consider valid moves within the grid and avoid obstacles, if any
        List<Point> neighbors = new ArrayList<>();

        int numRows = grid.length;
        int numCols = grid[0].length;

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Possible movement directions (right, left, down, up)

        for (int[] dir : directions) {
            int nextX = current.x + dir[0];
            int nextY = current.y + dir[1];

            // Check if the next position is within the grid boundaries
            if (nextX >= 0 && nextX < numRows && nextY >= 0 && nextY < numCols) {
                // Check if the next position is not an obstacle (assuming grid[i][j] == 0 means it's not an obstacle)
                if (grid[nextX][nextY] == Tileset.FLOOR) {
                    neighbors.add(new Point(nextX, nextY));
                }
            }
        }

        return neighbors;
    }

    private void generateHallwayFromPath(List<Point> path) {
        // Implement logic to generate the hallways based on the path
        // You can use Bresenham's line algorithm to draw straight hallways between consecutive points
        for (int i = 0; i < path.size() - 1; i++) {
            Point current = path.get(i);
            Point next = path.get(i + 1);
            generateStraightHallway(current, next);
        }
    }

    private void generateStraightHallway(Point start, Point end) {
        int x1 = start.x;
        int y1 = start.y;
        int x2 = end.x;
        int y2 = end.y;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // Draw the hallway on the grid (board)
            board[x1][y1] = Tileset.FLOWER;

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

//    public void generatePath(Room one, Room two) {
//        Point first = one.center;
//        Point second = two.center;
//        int x1 = first.x;
//        int y1 = first.y;
//        int x2 = second.x;
//        int y2 = second.y;
//
//        int dx = Math.abs(x2 - x1);
//        int dy = Math.abs(y2 - y1);
//        int sx = (x1 < x2) ? 1 : -1;
//        int sy = (y1 < y2) ? 1 : -1;
//        int err = dx - dy;
//
//        while (true) {
//            board[x1][y1] = Tileset.FLOWER;
//
//            if (x1 == x2 && y1 == y2) {
//                break;
//            }
//
//            int e2 = 2 * err;
//            if (e2 > -dy) {
//                err -= dy;
//                x1 += sx;
//            }
//            if (e2 < dx) {
//                err += dx;
//                y1 += sy;
//            }
//        }
//    }















    public void get() {
        generateHallway(Room.rooms.get(0), Room.rooms.get(1));
    }






    public String areRoomsAdjacent(Room room1, Room room2) {
        if(room1.lowerRight.x == room2 .lowerRight.x) {
            return "room1.lowerRight.x";
        } if(room1.lowerRight.y == room2 .lowerRight.y) {
            return "room1.lowerRight.y";
        }
        if(room1.lowerLeft.x == room2 .lowerLeft.x) {
            return "room1.lowerLeft.x";
        } if(room1.lowerLeft.y == room2 .lowerLeft.y) {
            return "room1.lowerLeft.y";
        }
        if(room1.upperLeft.x == room2 .upperLeft.x) {
            return "room1.upperLeft.x";
        } if(room1.upperLeft.y == room2 .upperLeft.y) {
            return "room1.upperLeft.y";
        }
        if(room1.upperRight.x == room2 .upperRight.x) {
            return "room1.upperRight.x";
        } if(room1.upperRight.y == room2 .upperRight.y) {
            return "room1.upperRight.y";
        }
        // Check if rooms share a wall or have a gap of one tile between them.
        // Implement your logic here based on the corner coordinates of the rooms.
        // Return true if adjacent, otherwise return false.
        return null;
        //pick two rooms
        //pick two poitns
        //find path
    }
//    public static TETile[][] checkall(TETile[][] boards) {
//        board = boards;
//        int count = 0;
//        while(count != rooms.size()) {
//            for (int i = 0; i < rooms.size(); i++) {
//                board = findhall(rooms.get(count), rooms.get(i), board);
//            }
//            count++;
//        }
//        return board;
//    }
//    public static TETile[][] findhall(Room one, Room two, TETile[][] boards) {
//        board = boards;
//        if (rooms.size() > 1) {
//            Point prev_center = one.upperLeft;
//            Point curr_center = two.lowerLeft;
//            int highest_x = Math.max(prev_center.x, curr_center.x);
//            int highest_y = Math.max(prev_center.y, curr_center.y);
//            int lowest_x = Math.min(prev_center.x, curr_center.x);
//            int lowest_y = Math.min(prev_center.y, curr_center.y);
//            int middleX = (lowest_x + highest_x) / 2;
//            int middleY = (lowest_y + highest_y) / 2;
//
//            // Update tiles for the horizontal hallway
//            for (int i = lowest_x; i <= highest_x; i++) {
//                if (i < 51 && middleY < 51) {
//                    if (board[i][middleY] == Tileset.FLOOR) {
//                        board[i][middleY] = Tileset.FLOWER;
//                    }
//                }
//            }
//
//            // Update tiles for the vertical hallway
//            for (int k = lowest_y; k <= highest_y; k++) {
//                if (middleX < 51 && k < 51) {
//                    if (board[middleX][k] == Tileset.FLOOR) {
//                        board[middleX][k] = Tileset.FLOWER;
//                    }
//                }
//            }
//        }
//        return board;
//    }

    public TETile[][] haller(Room room1, Room room2, TETile[][] b) {
        board = b;
        Point prev_center = room1.upperLeft;
        Point curr_center = room2.lowerLeft;
        int highest_x = Math.max(prev_center.x, curr_center.x);
        int highest_y = Math.max(prev_center.y, curr_center.y);
        int lowest_x = Math.min(prev_center.x, curr_center.x);
        int lowest_y = Math.min(prev_center.y, curr_center.y);
        int middleX = (lowest_x + highest_x) / 2;
        int middleY = (lowest_y + highest_y) / 2;


        for (int i = lowest_x; i < highest_x; i++) {
            for (int k = lowest_y; k < lowest_y + 1; k++) {
                if (i < 51 && k < 51) {
                    if (board[i][k] == Tileset.NOTHING) {
                        board[i][k] = Tileset.FLOWER;
                    }
                    if (board[i][k] == Tileset.WALL) {
                        board[i][k] = Tileset.FLOWER;
                    }
                }
            }
        }

        for (int i = highest_x; i < highest_x + 1; i++) {
            for (int k = lowest_y; k < highest_y; k++) {
                if (i < 51 && k < 51) {
                    if (board[i][k] == Tileset.NOTHING) {
                        board[i][k] = Tileset.FLOWER;
                    }
                    if (board[i][k] == Tileset.WALL) {
                        board[i][k] = Tileset.FLOWER;
                    }
                }
            }
        }
        return board;
    }
    public TETile[][] hallerways(TETile[][] boa, Random rs) {
        Collections.sort(Room.rooms);
        board = boa;
        for (int i = 0; i < Room.rooms.size(); i++) {
            for (int k = 0; k < Room.rooms.size(); k++) {
                Room prevRoom = Room.rooms.get(i);
                Room currRoom = Room.rooms.get(k);
                haller(prevRoom, currRoom, board);
            }
        }
        for (Room r: Room.rooms) {
            Point lowerLeft = r.lowerRight;
            Point upperRight = r.upperRight;
            for (int i = lowerLeft.x; i < upperRight.x; i++) {
                for (int k = lowerLeft.y; k < upperRight.y; k++) {
                    if (RandomUtils.bernoulli(rs, 0.01))
                        board[i][k] = Tileset.FLOWER;
                }
            }
        }
        return board;
    }


    public Point find(Room room1, Room room2, int side, int where) {
        if (where == 1) { // Check for intersection along x-axis
            int minY = Math.max(room1.lowerLeft.y, room2.lowerLeft.y);
            int maxY = Math.min(room1.upperLeft.y, room2.upperLeft.y);
            return new Point(minY + 1, maxY);
        } else if (where == 2) { // Check for intersection along y-axis
            int minX = Math.max(room1.lowerLeft.x, room2.lowerLeft.x);
            int maxX = Math.min(room1.lowerRight.x, room2.lowerRight.x);
            return new Point(minX + 1, maxX);
        }
        return null;
    }

    public Point findIntersec(Room room1, Room room2, String side) {
        Point found = null;
        switch (side) {
            case "room1.lowerRight.x":
                found = find(room1, room2, room1.lowerRight.x, 1);
            case "room1.lowerRight.y":
                found = find(room1, room2, room1.lowerRight.y, 2);
            case "room1.lowerLeft.x":
                found = find(room1, room2, room1.lowerLeft.x, 1);
            case "room1.lowerLeft.y":
                found = find(room1, room2, room1.lowerLeft.y, 2);
            case "room1.upperLeft.x":
                found = find(room1, room2, room1.upperLeft.x, 1);
            case "room1.upperLeft.y":
                found = find(room1, room2, room1.upperLeft.y, 2);
            case "room1.upperRight.x":
                found = find(room1, room2, room1.upperRight.x, 1);
            case "room1.upperRight.y":
                found = find(room1, room2, room1.upperRight.y, 2);
        }
        return found;
    }

    public TETile[][] createHallwayBetweenRooms(Room room1, Room room2, TETile[][] worldGrid) {
        // Calculate the coordinates for the hallway that connects room1 and room2.
        // Draw the hallway on the world grid using visually distinct tiles.
        // Update the world grid with the hallway's coordinates.
        String side = areRoomsAdjacent(room1, room2);
        //Point inter = findIntersec(room1, room2, side);
        // Calculate the coordinates for the hallway that connects room1 and room2.
        Point inter = findIntersec(room1, room2, areRoomsAdjacent(room1, room2));
        int x1 = inter.x;
        int x2 = inter.y;

//        if(room1.upperLeft.y = )
//        int y1 = room1.upperLeft.y;
//        int y2 = room2.lowerLeft.y;



        // Ensure that x1 is always smaller than x2 for simplicity
        if (inter.x > room2.lowerLeft.x) {
            x1 = room2.lowerLeft.x;
            x2 = inter.x;
        }


        return worldGrid;

    }


}