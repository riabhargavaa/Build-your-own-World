package byow.Core;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.In;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import static java.lang.Long.parseLong;

public class Engine {
//    TERenderer ter = new TERenderer();
//    /* Feel free to change the width and height. */
//    private final int WIDTH = 51;
//    String command = "";
//    private final int HEIGHT = 51;
//    private final int x = 35;
//    private int oldX = 3;
//    private int oldY = 0;
//    private static final File CWD = new File(System.getProperty("user.dir"));
//    //holds the history
//    private static final File FINALTILE = new File(CWD, "board.txt");
//    private static final File PLAYERS = new File(CWD, "avatar.txt");
//    boolean playing;
//    TETile[][] board = new TETile[WIDTH][HEIGHT];
//    private String oldDisplay = "";
//    private TETile avatars;
//    TETile avatarType;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 51;
    public static final int HEIGHT = 51;
    public static int roomsnumb = 0;
    static List<Room> rooms = new ArrayList<>();
    public static boolean aBoolean = false;
    public int currPosMouseX = 2;
    public int currPosMouseY = 2;
    public TETile[][] worlds = new TETile[WIDTH][HEIGHT];
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File TILES = new File(CWD, "tiles.txt");
    public static final File PLAYER = new File(CWD, "avatar.txt");
    public boolean lightsOn = true;
    public TETile avatarType = Tileset.AVATAR;
    public int ind = 1;
    public boolean grassy = false;
    public int loseInt = 0;
    public boolean winner = false;
    public String oldDisplay = "";
    public int olders = 3;
    public int oldY = 0;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        displayMenu();
        boolean sq_or_no = true;
        while (sq_or_no) {
            char nextKey = getKey();
            if (nextKey == 'n') {
                Long get = getterss();
                Random random = new Random(get);
                initializeEmpty(worlds);
                oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
                initializeWorld(sq_or_no, random);
                String[] avatar = addObstaclesAndAvatar(worlds, Math.toIntExact((get)));
                hud();
                String ava = avatar[0] + " " + avatar[1] +  " " + avatar[2] + " " + avatar[3];
                oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
                which(ava, avatar);
            }  else if (nextKey == 'l') {
                In out = new In("avatar.txt");
                In bo = new In("board.txt");
                String arr = bo.readLine();

                String[] arrs = arr.split(" ");
                //System.out.println("BOARD" + arrs[1]);
                System.out.println("BOARD" + "n" + arrs[0] + "s" + arrs[1]);
                worlds = interactWithInputString("n" + arrs[0] + "s" + arrs[1]);
                String seed = "";
                for (int i = 0; i < arr.length(); i++) {
                    if (arr.charAt(i) == 'n') {
                        continue;
                    } else if (arr.charAt(i) == 's') {
                        i = arr.length() - 1;
                    } else {
                        seed += arr.charAt(i);
                    }
                }
                String newAv = out.readLine();
                winner = false;
                hud();
                System.out.print(newAv);

                which(newAv, arrs);
            } else if (nextKey == 'i') {
                mainmenu();
            } else if (getKey() == 'm') {
                sq_or_no = false;
                mainmenu();
            } else if (nextKey == 'a') {
                whichone();
                char nextestKey = getKey();
                if (nextestKey == 's') {
                    avatarType = Tileset.SAND;
                    displayMenu();
                } else if (nextestKey == 'm') {
                    avatarType = Tileset.MOUNTAIN;
                    displayMenu();
                } else if (nextestKey == 'w') {
                    avatarType = Tileset.WATER;
                    displayMenu();
                } else {
                    avatarType = Tileset.AVATAR;
                    displayMenu();
                }
            }
            else if (nextKey == 'q') {
                sq_or_no = false;
                System.exit(0);
            } else {
                System.out.println("Invalid command, please enter either 'n', 'l', 'a', or 'q'!!!!");
            }

        }
    }

    public void hud() {

        //print any text
        //write text and ur world might be overriding
        //add extra length to the top and then worry about rest

        int newX = (int) StdDraw.mouseX();
        int newY = (int) StdDraw.mouseY();

        // Update oldDisplay if mouse position has changed
        if (newX != olders || newY != oldY) {
            olders = newX;
            oldY = newY;
            oldDisplay = worlds[newX][newY].description();
        }

        // Display the current tile description
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(36, 47, "Current tile is " + oldDisplay);
        StdDraw.show();
    }


    private void whichone() {
        double x_coor = 0.5;
        double y_coor = 0.8;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(x_coor, y_coor, "So, which type of avatar do you want to be?");
        StdDraw.text(x_coor, y_coor - 0.05, "Here are your following options:");
        StdDraw.text(x_coor, y_coor - 0.15, "1) Mountain (Press 'M')");
        StdDraw.text(x_coor, y_coor - 0.25, "2) Sand (Press 'S')");
        StdDraw.text(x_coor, y_coor - 0.35, "3) Water (Press 'W')");
        StdDraw.text(x_coor, y_coor - 0.45, "4) Type anything else to default to the OG Avatar Symbol!");
        StdDraw.show();
    }

    private long getterss() {
        double x_coor = 0.5;
        double y_coor = 0.8;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(x_coor, y_coor, "You, my friend, have embarked on a new journey");
        StdDraw.text(x_coor, y_coor - 0.2, "Please enter a seed value, ending with the character 's'");
        StdDraw.text(x_coor, y_coor - 0.4, "Current seed value:");
        StdDraw.show();
        String inputSeed = "";

        while(true) {
            char nextKey = getKey();
            if (nextKey == 's') {
                break;
            }
            inputSeed += nextKey;
            StdDraw.clear(StdDraw.RED);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(x_coor, y_coor, "You, my friend, have embarked on a new journey");
            StdDraw.text(x_coor, y_coor - 0.2, "Please enter a seed value, ending with the character 's'");
            StdDraw.text(x_coor, y_coor - 0.4, "Current seed value:" + inputSeed);
            StdDraw.show();
        }
        return Long.parseLong(inputSeed);
    }

    private void mainmenu() {
        double x_coor = 0.5;
        double y_coor = 0.8;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(x_coor, y_coor, "You, my friend, are a monster now!");
        StdDraw.text(x_coor, y_coor - 0.05, "So, you need to eat as much as you can!");
        StdDraw.text(x_coor, y_coor - 0.15, "This is how to win the game:");
        StdDraw.text(x_coor, y_coor - 0.25, "1) Flowers provide nourishment and nutrition, so eat them all!");
        StdDraw.text(x_coor, y_coor - 0.35, "2) You are a sleep monster, so eat all the lights!");
        StdDraw.text(x_coor, y_coor - 0.4, "3) Press 'l' to turn the lights on and off!");
        StdDraw.text(x_coor, y_coor - 0.45, "4) Press 'g' to do the grass-dance!");
        StdDraw.text(x_coor, y_coor - 0.5, "If you quit the world and reload, you automatically win! (Cheatcode!!)");
        StdDraw.text(x_coor, y_coor - 0.55, "P.S., if you turn on the lights after eating them,");
        StdDraw.text(x_coor, y_coor - 0.6, "You have to start eating the lights from scratch!");
        StdDraw.text(x_coor, y_coor - 0.65, "Type 'm' to go back to the menu....GOOD LUCK!!!!");
        StdDraw.show();

    }

    private char getKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                return Character.toLowerCase(StdDraw.nextKeyTyped());
            }
        }
    }

    private void displaymose() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < WIDTH && y < HEIGHT){
            StdDraw.text(3, HEIGHT + 3, worlds[x][y].description());
            StdDraw.show();
            StdDraw.pause(350);
            StdDraw.clear();
        }

        //StdDraw.textLeft(0, HEIGHT - 1, today);

        StdDraw.show();

    }
    public void showflower() {
        //ter.renderFrame(worlds);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(36, 47, "Current tile is " + "Flower");
        StdDraw.show();
    }
    public void showfloor() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(36, 47, "Current tile is " + "Floor");
        StdDraw.show();
    }
    public void showlight() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(36, 47, "Current tile is Light");
        StdDraw.show();
    }

    private void which(String avatar, String[] rand) {
        System.out.print(rand[0] + rand[1]);
        String avatarmovements = "";
        String[] arr = avatar.split("\\s+");
        boolean gameIsOver = false;
        String coun = "";
//        coun += String.valueOf(arr[3]);
        //System.out.print(arr[3]);
        TERenderer newWorld = new TERenderer();
        newWorld.initialize(WIDTH, HEIGHT);
        TETile[][] testers = new TETile[WIDTH][HEIGHT];
        int x_pos = Integer.parseInt(arr[0]);
        int y_pos = Integer.parseInt(arr[1]);;
        if (arr[2].equals("you")) {
            avatarType = Tileset.AVATAR;
        } else if (arr[2].equals("water")) {
            avatarType = Tileset.WATER;
        } else if (arr[2].equals("sand")) {
            avatarType = Tileset.SAND;
        } else {
            avatarType = Tileset.MOUNTAIN;
        }
        coun += " ";

        while (true) {
            //displaymose();
            newWorld.renderFrame(worlds);
            char nextKey = getKey();
            if (nextKey == 'm') {
                System.exit(0);
                //displayMenu();
                System.out.print("t");
            } else if (nextKey == 'w') {
                hud();
                //displaymose();
                if (no(x_pos, y_pos + 1) && notLight(x_pos, y_pos + 1)) {
                    worlds[x_pos][y_pos + 1] = avatarType;
                    worlds[x_pos][y_pos] = Tileset.FLOOR;
                    displaymose();
                    y_pos += 1;
                    //checkWin();
                    //checkLoose();
                    //limitVision(x_pos, y_pos, finalWorldFrame);
                    newWorld.renderFrame(worlds);
                    hud();
                    coun += 'w';
                    avatarmovements += 'w';
                }
            } else if (nextKey == 'a') {
                hud();
                if (no(x_pos - 1, y_pos) && notLight(x_pos - 1, y_pos)) {
                    worlds[x_pos - 1][y_pos] = avatarType;
                    worlds[x_pos][y_pos] = Tileset.FLOOR;
                    x_pos -= 1;
                    //limitVision(x_pos, y_pos, finalWorldFrame);
                    //checkWin();
                    //checkLoose();
                    newWorld.renderFrame(worlds);
                    hud();
                    coun += 'a';
                    avatarmovements += 'a';
                }
            } else if (nextKey == '1') {
                showfloor();
            } else if (nextKey == '2') {
                showlight();
            } else if (nextKey == '3') {
                showflower();
            }
            else if (nextKey == 's') {
                hud();
                if (no(x_pos, y_pos - 1) && notLight(x_pos, y_pos - 1)) {
                    worlds[x_pos][y_pos - 1] = avatarType;
                    worlds[x_pos][y_pos] = Tileset.FLOOR;
                    y_pos -= 1;
                    //limitVision(x_pos, y_pos, finalWorldFrame);
                    //checkWin();
                    //checkLoose();
                    newWorld.renderFrame(worlds);
                    hud();
                    coun += 's';
                    avatarmovements += 's';
                }
            } else if (nextKey == 'd') {
                if (no(x_pos + 1, y_pos) && notLight(x_pos + 1, y_pos)) {
                    worlds[x_pos + 1][y_pos] = avatarType;
                    worlds[x_pos][y_pos] = Tileset.FLOOR;
                    x_pos += 1;
                    //limitVision(x_pos, y_pos, finalWorldFrame);
                    //checkWin();
                    //checkLoose();
                    newWorld.renderFrame(worlds);
                    hud();
                    coun += 'd';
                    avatarmovements += 'd';
                }
            }
            else if (nextKey == 'z') {
                checkWins();
            } else if (nextKey == 'c') {
                checkLoose();
            }
            else if (nextKey == ':') {
                while (true) {
                    if (getKey() == 'q') {
                        String s = avatarType.description();
                        System.out.println(s);
                        String avatars = String.valueOf(x_pos) + " " + String.valueOf(y_pos) + " " + s;
                        System.out.println(avatars);
                        Out out = new Out("avatar.txt");
                        out.print(avatars);
//                        Utils2.writeObject(players, avatar);
                        //TETile[][]
                        //boardss = board;
//                        System.out.println();
//                        System.out.println(input);
                        System.out.print("HELLO" + coun);
                        Out outer = new Out("board.txt");

                        //seeds + move
                        outer.print(coun);
//                        In bo = new In("board.txt");
//                        String arrs = bo.readLine();
//                        finalWorldFrame = help(arrs);
//                        String hel = "";
//                        for(int i = 0; i < avatar(arrs).length(); i++) {
//                            hel += avatar(arrs).charAt(i);
//                        }
//                        for(int i = 1; i < coun.length(); i++) {
//                            hel += coun.charAt(i);
//                        }
//                        String s = avatarType.description();
//                        avatar = String.valueOf(x_pos) + " " + String.valueOf(y_pos) + " " + s;
//                        Utils.writeObject(AVATAR, avatar);
//                        TETile[][] lala = finalWorldFrame;
//                        Utils.writeObject(TILES, lala);
                        System.exit(0);
                    }
                    break;
                }
            } else if (nextKey == 'l') {
                if (lightsOn) {
                    turnOffLights();
                    lightsOn = false;
                } else {
                    turnOnLights();
                    lightsOn = true;
                }
            } else if (nextKey == 'g') {
                if (grassy) {
                    repairWalls();
                    grassy = false;
                } else {
                    wallDance();
                    grassy = true;
                }
            }

        }
    }
    public TETile[][] help(String input) {
        System.out.println("DEG" + input);
        System.out.println(input);
        String seedss = "";
        String seeds = "";
        StringBuilder avatarMovements = new StringBuilder();
        boolean made = false;
        for (int i = 0; i < input.length(); i++) {
            switch (Character.toLowerCase(input.charAt(i))) {
                case 'n':
                    seedss += 'n';
                    break;
                case 'w':
                    seedss += 'w';
                    avatarMovements.append("w");
                    break;
                //System.out.print("HI");
                case 'a':
                    seedss += 'a';
                    //System.out.print("WHY");
                    avatarMovements.append("a");
                    break;
                case 's':
                    if (made) {
                        seedss += 's';
                        //System.out.print("HI");
                        avatarMovements.append("s");
                    } else {
                        seedss += 's';
                        made = true;
                    }
                    break;
                case 'd':
                    seedss += 'd';
                    avatarMovements.append("d");
                    break;
                case ':':
                    seedss += ':';
                    break;
                case 'q':
                    seedss += 'q';
                    break;
                case 'l':
                    seedss += 'l';
                    break;
                default:
                    seedss += input.charAt(i);
                    seeds += input.charAt(i);
                    break;
            }
        }
        seedss = input;
        int x_pos = 0;
        int y_pos = 0;
        boolean sq_or_no = true;
//        Sys   tem.out.println("n");
//        System.out.print(seeds);
        System.out.print(seeds);
        long randoms = parseLong(seeds);
        Random random = new Random(randoms);
        initializeWorld(true, random);
        String[] avatar = addObstaclesAndAvatar(worlds, Integer.parseInt(seeds));
        x_pos = Integer.parseInt(avatar[0]);
        y_pos = Integer.parseInt(avatar[1]);
        return worlds;
    }
    public StringBuilder avatar(String input) {
        String seedss = "";
        String seeds = "";
        StringBuilder avatarMovements = new StringBuilder();
        boolean made = false;
        for (int i = 0; i < input.length(); i++) {
            switch (Character.toLowerCase(input.charAt(i))) {
                case 'n':
                    seedss += 'n';
                    break;
                case 'w':
                    seedss += 'w';
                    avatarMovements.append("w");
                    break;
                //System.out.print("HI");
                case 'a':
                    seedss += 'a';
                    //System.out.print("WHY");
                    avatarMovements.append("a");
                    break;
                case 's':
                    if (made) {
                        seedss += 's';
                        //System.out.print("HI");
                        avatarMovements.append("s");
                    } else {
                        seedss += 's';
                        made = true;
                    }
                    break;
                case 'd':
                    seedss += 'd';
                    avatarMovements.append("d");
                    break;
                case ':':
                    seedss += ':';
                    break;
                case 'q':
                    seedss += 'q';
                    break;
                case 'l':
                    seedss += 'l';
                    break;
                default:
                    seedss += input.charAt(i);
                    seeds += input.charAt(i);
                    break;
            }
        }
        return avatarMovements;
    }
    private boolean won() {

        for (int i = 0; i < WIDTH; i++) {
            for (int k = 0; k < HEIGHT; k++) {
                if (worlds[i][k] == Tileset.FLOWER || worlds[i][k] == Tileset.LOCKED_DOOR) {
                    return false;
                }
            }
        }
        winner = true;
        return true;

    }
    private void checkLoose() {
        double x_coor = 25;
        double y_coor = 40;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(x_coor, y_coor, "You, my friend, have LOST THE GAME!!!!");
        StdDraw.show();
        if (getKey() == 'q') {
            System.exit(0);
        }
    }
    private void checkWins() {
        double x_coor = 25;
        double y_coor = 40;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.text(x_coor, y_coor, "You, my friend, have WON THE GAME!!!!");
        StdDraw.text(x_coor, y_coor - 2, "Take a second to pat yourself on the back!");
        StdDraw.text(x_coor, y_coor - 4, "You deserve this win! Good job my friend.");
        StdDraw.text(x_coor, y_coor - 6, "Everyone in 61BL is proud of you for winning");
        StdDraw.text(x_coor, y_coor - 8, "Congrats on finishing this summer semester!");
        StdDraw.text(x_coor, y_coor - 10, "I hope you had a great time, I know I had fun!");
        StdDraw.text(x_coor, y_coor - 12, "Thank you to Ashley for a great semester");
        StdDraw.text(x_coor, y_coor - 14, "Type 'q' to quit the game");
        StdDraw.show();
        if (getKey() == 'q') {
            System.exit(0);
        }
    }

    private void checkWin() {
        if (won() && winner) {
            double x_coor = 25;
            double y_coor = 40;
            StdDraw.clear(StdDraw.RED);
            StdDraw.setPenColor(StdDraw.BLACK);

            StdDraw.text(x_coor, y_coor, "You, my friend, have WON THE GAME!!!!");
            StdDraw.text(x_coor, y_coor - 2, "Take a second to pat yourself on the back!");
            StdDraw.text(x_coor, y_coor - 4, "You deserve this win! Good job my friend.");
            StdDraw.text(x_coor, y_coor - 6, "Everyone in 61BL is proud of you for winning");
            StdDraw.text(x_coor, y_coor - 8, "Congrats on finishing this summer semester!");
            StdDraw.text(x_coor, y_coor - 10, "I hope you had a great time, I know I had fun!");
            StdDraw.text(x_coor, y_coor - 12, "Thank you to Ashley for a great semester");
            StdDraw.text(x_coor, y_coor - 14, "Type 'q' to quit the game");
            StdDraw.show();
            if (getKey() == 'q') {
                System.exit(0);
            }


        }
    }



    private void wallDance() {
        for (int i = 0; i < WIDTH; i++) {
            for (int k = 0; k < HEIGHT; k++) {
                if (worlds[i][k] == Tileset.WALL) {
                    worlds[i][k] = Tileset.GRASS;
                    ind++;
                }
            }
        }
    }

    private void repairWalls() {
        for (int i = 0; i < WIDTH; i++) {
            for (int k = 0; k < HEIGHT; k++) {
                if (worlds[i][k] == Tileset.GRASS) {
                    worlds[i][k] = Tileset.WALL;
                    ind++;
                }
            }
        }
    }



    private boolean no(int x_pos, int y_pos) {

        if (worlds[x_pos][y_pos].description().equals(Tileset.WALL.description())) {

//            InputStream is = StdAudio.class.getResourceAsStream("byow/Core/droplets-88879.txt");
//            System.out.print(is);
//            StdAudio.play("byow/Core/droplets-88879.txt");
            loseInt++;
        }
        return !worlds[x_pos][y_pos].description().equals(Tileset.WALL.description());
    }

    private boolean notLight(int x_pos, int y_pos) {
        return true;
        //!finalWorldFrame[x_pos][y_pos].description().equals(Tileset.LOCKED_DOOR.description());
    }

    private void displayMenu() {
        double x_coor = 0.5;
        double y_coor = 0.8;
        StdDraw.clear(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x_coor, y_coor, "CS61BL: EAT EVERYTHING AND SLEEP!");
        StdDraw.text(x_coor, y_coor - 0.4, "New World (N)");
        StdDraw.text(x_coor, y_coor - 0.45, "Load Previous World (L)");
        StdDraw.text(x_coor, y_coor - 0.5, "Read the instructions (I)");
        StdDraw.text(x_coor, y_coor - 0.55, "Choose Avatar Appearence(A)");
        StdDraw.text(x_coor, y_coor - 0.6, "Quit World (Q)");

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] helper(String input, int startx, int starty) {
        String seedss = "";
        String seeds = "";
        StringBuilder avatarMovements = new StringBuilder();
        boolean made = false;
        for (int i = 0; i < input.length(); i++) {
            switch (Character.toLowerCase(input.charAt(i))) {
                case 'n':
                    seedss += 'n';
                    break;
                case 'w':
                    seedss += 'w';
                    avatarMovements.append("w");
                    break;
                //System.out.print("HI");
                case 'a':
                    seedss += 'a';
                    //System.out.print("WHY");
                    avatarMovements.append("a");
                    break;
                case 's':
                    if (made) {
                        seedss += 's';
                        //System.out.print("HI");
                        avatarMovements.append("s");
                    } else {
                        seedss += 's';
                        made = true;
                    }
                    break;
                case 'd':
                    seedss += 'd';
                    avatarMovements.append("d");
                    break;
                case ':':
                    seedss += ':';
                    break;
                case 'q':
                    seedss += 'q';
                    break;
                case 'l':
                    seedss += 'l';
                    break;
                default:
                    seedss += input.charAt(i);
                    seeds += input.charAt(i);
                    break;
            }
        }
        seedss = input;
        int x_pos = startx;
        int y_pos = starty;
        boolean sq_or_no = true;
            System.out.print("HII");
            Random random = new Random(Long.parseLong(seeds));
            initializeEmpty(worlds);
            oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
            initializeWorld(sq_or_no, random);
            String[] avatars = addObstaclesAndAvatar(worlds, Long.parseLong((seeds)));
            hud();
            String ava = avatars[0] + " " + avatars[1] +  " " + avatars[2];
            oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
            System.out.print("HISSSI");
            //which(ava, avatar);
//            System.out.println(avatarMovements);
        for(int i = 0; i < avatarMovements.length(); i++) {
            System.out.println("entered");
            if (i == -1) {
//                System.out.print("l");
                break;
            }
            if (avatarMovements.charAt(i) == 'q') {
                sq_or_no = false;
                return worlds;
            }
            if (avatarMovements.charAt(i) == 'w') {
                System.out.print("hi");
                if (inBounds(x_pos, y_pos) && worlds[x_pos][y_pos + 1] != Tileset.WALL) {
                    System.out.print("HII");
                    worlds[x_pos][y_pos + 1] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    y_pos += 1;
                    // newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 'a') {
                if (inBounds(x_pos - 1, y_pos) && worlds[x_pos - 1][y_pos] != Tileset.WALL) {
                    worlds[x_pos - 1][y_pos] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    x_pos -= 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 's') {
                //System.out.print("hi");
                if (inBounds(x_pos, y_pos - 1) && worlds[x_pos][y_pos - 1] != Tileset.WALL) {
                    worlds[x_pos][y_pos - 1] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    y_pos -= 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 'd') {
                System.out.print("hi");
                if (inBounds(x_pos + 1, y_pos) && worlds[x_pos + 1][y_pos] != Tileset.WALL) {
                    worlds[x_pos + 1][y_pos] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    x_pos += 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == ':') {
//                System.out.print("rec");
                while (true) {
                    if (seedss.charAt(i + 1) == 'q') {
                        //System.out.print("rec");
                        String s = Tileset.AVATAR.description();
                        String avatar = String.valueOf(x_pos) + " " + String.valueOf(y_pos) + " " + s + " " + seeds;
                        Out out = new Out("avatar.txt");
                        out.print(avatar);
//                        Utils2.writeObject(players, avatar);
                        //TETile[][]
                        //boardss = board;
//                        System.out.println();
//                        System.out.println(input);
                        Out outer = new Out("board.txt");
                        outer.print(seeds + " " + avatarMovements);
//                        Utils2.writeObject(finaLTile, board);
                        hud();
                        return worlds;

                    }
                    hud();
                    break;
                }
            }
        }

        return worlds;
    }
    public TETile[][] interactWithInputString(String input) {

        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String seedss = "";
        String seeds = "";
        StringBuilder avatarMovements = new StringBuilder();
        boolean made = false;
        for (int i = 0; i < input.length(); i++) {
            switch (Character.toLowerCase(input.charAt(i))) {
                case 'n':
                    seedss += 'n';
                    break;
                case 'w':
                    seedss += 'w';
                    avatarMovements.append("w");
                    break;
                //System.out.print("HI");
                case 'a':
                    seedss += 'a';
                    //System.out.print("WHY");
                    avatarMovements.append("a");
                    break;
                case 's':
                    if (made) {
                        seedss += 's';
                        //System.out.print("HI");
                        avatarMovements.append("s");
                    } else {
                        seedss += 's';
                        made = true;
                    }
                    break;
                case 'd':
                    seedss += 'd';
                    avatarMovements.append("d");
                    break;
                case ':':
                    seedss += ':';
                    break;
                case 'q':
                    seedss += 'q';
                    break;
                case 'l':
                    seedss += 'l';
                    break;
                default:
                    seedss += input.charAt(i);
                    seeds += input.charAt(i);
                    break;
            }
        }
        seedss = input;
        int x_pos = 0;
        int y_pos = 0;
        boolean sq_or_no = true;
        if(seedss.charAt(0) == 'n') {
            System.out.print("HII");
            Random random = new Random(Long.parseLong(seeds));
            initializeEmpty(worlds);
            oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
            initializeWorld(sq_or_no, random);
            String[] avatar = addObstaclesAndAvatar(worlds, Long.parseLong((seeds)));
            hud();
            String ava = avatar[0] + " " + avatar[1] +  " " + avatar[2];
            oldDisplay = worlds[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
            System.out.print("HISSSI");
            //which(ava, avatar);
//            System.out.println(avatarMovements);
        } else {
            //hud();
//            System.out.println("nothing");
            In bo = new In("board.txt");
            String arr = bo.readLine();
//            System.out.println(arr);
//            System.out.println();
            worlds = help(arr);
            String hel = "";
            In ava = new In("ava.txt");
            String avatar = bo.readLine();
            x_pos = avatar.charAt(0);
            y_pos = avatar.charAt(2);
            for(int u = 0; u < avatar(arr).length(); u++) {
                hel += avatar(arr).charAt(u);
            }
            for(int u = 1; u < input.length(); u++) {
                hel += input.charAt(u);
            }

            seedss = hel;
//            System.out.println();
//            System.out.println(hel + " UGDUYGDKJHKJD");
            seeds = "";
        }
        System.out.print("HISIDJFJSDI");
        System.out.println(avatarMovements);

        for(int i = 0; i < avatarMovements.length(); i++) {
            System.out.println("entered");
            if (i == -1) {
//                System.out.print("l");
                break;
            }
            if (avatarMovements.charAt(i) == 'q') {
                sq_or_no = false;
                return worlds;
            }
            if (avatarMovements.charAt(i) == 'w') {
                System.out.print("hi");
                if (inBounds(x_pos, y_pos) && worlds[x_pos][y_pos + 1] != Tileset.WALL) {
                    System.out.print("HII");
                    worlds[x_pos][y_pos + 1] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    y_pos += 1;
                    // newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 'a') {
                if (inBounds(x_pos - 1, y_pos) && worlds[x_pos - 1][y_pos] != Tileset.WALL) {
                    worlds[x_pos - 1][y_pos] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    x_pos -= 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 's') {
                //System.out.print("hi");
                if (inBounds(x_pos, y_pos - 1) && worlds[x_pos][y_pos - 1] != Tileset.WALL) {
                    worlds[x_pos][y_pos - 1] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    y_pos -= 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == 'd') {
                 System.out.print("hi");
                if (inBounds(x_pos + 1, y_pos) && worlds[x_pos + 1][y_pos] != Tileset.WALL) {
                    worlds[x_pos + 1][y_pos] = Tileset.AVATAR;
                    worlds[x_pos][y_pos] = Tileset.FLOWER;
                    x_pos += 1;
                    //newWorld.renderFrame(board);
                    hud();
                }
                hud();
            } else if (avatarMovements.charAt(i) == ':') {
//                System.out.print("rec");
                while (true) {
                    if (seedss.charAt(i + 1) == 'q') {
                        //System.out.print("rec");
                        String s = Tileset.AVATAR.description();
                        String avatar = String.valueOf(x_pos) + " " + String.valueOf(y_pos) + " " + s + " " + seeds;
                        Out out = new Out("avatar.txt");
                        out.print(avatar);
//                        Utils2.writeObject(players, avatar);
                        //TETile[][]
                        //boardss = board;
//                        System.out.println();
//                        System.out.println(input);
                        Out outer = new Out("board.txt");
                        outer.print(seeds + " " + avatarMovements);
//                        Utils2.writeObject(finaLTile, board);
                        hud();
                        return worlds;

                    }
                    hud();
                    break;
                }
            }
        }

        return worlds;
    }
    public boolean inBounds(int x, int y) {
        if(x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return false;
        } if(worlds[x][y] == Tileset.WALL) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Engine engine = new Engine();
        TERenderer en = new TERenderer();
        //engine.interactWithKeyboard();
        //TETile[][] eng = engine.interactWithInputString("n80869031643s");

        TETile[][] eng = engine.interactWithInputString("n1392967723524655428sddsaawws");
//        eng.toString();
//        en.initialize(51, 51);
//        //engine.interactWithKeyboard();
//        en.renderFrame(eng);
//        engine.toString();
//        eng = engine.interactWithInputString("laddw");

        //ETile[][] eng = engine.interactWithInputString("n1392967723524655428sddsaawwsaddw");
        //engine.interactWithKeyboard();
//        eng2.toString();
//        en.initialize(51, 51);
//        //engine.interactWithKeyboard();
//        en.renderFrame(eng2);
        //TETile[][] eng = engine.interactWithInputString("sdwdd");
        for (int i = 0; i < eng.length; i++) {
            for (int j = 0; j < eng[0].length; j++) {
                System.out.print(eng[i][j].description() + " ");
            }
        }
        eng.toString();
        en.initialize(51, 51);
        //engine.interactWithKeyboard();
        en.renderFrame(eng);
        engine.toString();
    }

    private void initializeWorld(boolean sq_or_no, Random seed) {
        Random rara = new Random(234);
        while (rooms.size() < 9) {
            if (sq_or_no) {
                makeSquareRoom(worlds, seed.nextInt(15), seed.nextInt(17), seed.nextInt(35), seed.nextInt(33));
                sq_or_no = false;
            } else {
                makeRectangleRoom(worlds, seed.nextInt(15), seed.nextInt(9), seed.nextInt(35), seed.nextInt(40));
                sq_or_no = true;
            }

        }
        Collections.sort(rooms);
        for (int i = 0; i < rooms.size(); i++) {
            //System.out.println(collectionOfRooms.get(i).center.x + "," + collectionOfRooms.get(i).center.y);
        }
        for (int i = 0; i < 4; i++) {
            for (int k = 3; k < 9; k++) {
                Room prevRoom = rooms.get(i);
                Room currRoom = rooms.get(k);
                connectRooms(worlds, prevRoom, currRoom);
            }
        }
        for (Room r: rooms) {
            Point lowerLeft = r.bottomLeft;
            Point upperRight = r.upperRight;
            for (int i = lowerLeft.x; i <= upperRight.x; i++) {
                for (int k = lowerLeft.y; k <= upperRight.y; k++) {
                    if (RandomUtils.bernoulli(seed, 0.01))
                        worlds[i][k] = Tileset.FLOWER;
                }
            }
        }
        turnOnLights();
    }

    private void turnOnLights() {
        for (Room r: rooms) {
            if (r.center.x < 49 && r.center.y < 49 && worlds[r.center.x][r.center.y] == Tileset.FLOOR) {
                worlds[r.center.x][r.center.y] =
                        TETile.colorVariant(Tileset.LOCKED_DOOR, 117, 219, 219, new Random(45));
                if (worlds[r.center.x + 1][r.center.y] == Tileset.FLOOR) {
                    worlds[r.center.x + 1][r.center.y] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x + 1][r.center.y + 1] == Tileset.FLOOR) {
                    worlds[r.center.x + 1][r.center.y + 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x + 1][r.center.y - 1] == Tileset.FLOOR) {
                    worlds[r.center.x + 1][r.center.y] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x][r.center.y - 1] == Tileset.FLOOR) {
                    worlds[r.center.x][r.center.y - 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x][r.center.y + 1] == Tileset.FLOOR) {
                    worlds[r.center.x][r.center.y + 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x - 1][r.center.y + 1] == Tileset.FLOOR) {
                    worlds[r.center.x - 1][r.center.y + 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x - 1][r.center.y - 1] == Tileset.FLOOR) {
                    worlds[r.center.x - 1][r.center.y - 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                }
                if (worlds[r.center.x - 1][r.center.y] == Tileset.FLOOR) {
                    worlds[r.center.x - 1][r.center.y] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));

                    if (worlds[r.center.x + 1][r.center.y - 1] == Tileset.FLOOR) {
                        worlds[r.center.x + 1][r.center.y - 1] = TETile.colorVariant(Tileset.LOCKED_DOOR, 255, 255, 255, new Random(45));
                    }
                }
            }
        }
    }

    private void turnOffLights() {
        for (Room r: rooms) {
            if (r.center.x < 49 && r.center.y < 49) {
                if (worlds[r.center.x][r.center.y] != Tileset.WALL && worlds[r.center.x][r.center.y] != Tileset.NOTHING) {
                    worlds[r.center.x][r.center.y] = Tileset.FLOOR;
                }
                if (worlds[r.center.x + 1][r.center.y] != Tileset.WALL && worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x + 1][r.center.y] != Tileset.FLOWER) {
                        worlds[r.center.x + 1][r.center.y] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x + 1][r.center.y + 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x + 1][r.center.y + 1] != Tileset.FLOWER) {
                        worlds[r.center.x + 1][r.center.y + 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x + 1][r.center.y - 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x + 1][r.center.y - 1] != Tileset.FLOWER) {
                        worlds[r.center.x + 1][r.center.y - 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x][r.center.y - 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x][r.center.y - 1] != Tileset.FLOWER) {
                        worlds[r.center.x][r.center.y - 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x][r.center.y + 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x][r.center.y + 1] != Tileset.FLOWER) {
                        worlds[r.center.x][r.center.y + 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x - 1][r.center.y + 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x - 1][r.center.y + 1] != Tileset.FLOWER) {
                        worlds[r.center.x - 1][r.center.y + 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x - 1][r.center.y - 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x - 1][r.center.y - 1] != Tileset.FLOWER) {
                        worlds[r.center.x - 1][r.center.y - 1] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x - 1][r.center.y] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x - 1][r.center.y] != Tileset.FLOWER) {
                        worlds[r.center.x - 1][r.center.y] = Tileset.FLOOR;
                    }
                }
                if (worlds[r.center.x + 1][r.center.y - 1] != Tileset.WALL&& worlds[r.center.x + 1][r.center.y] != Tileset.NOTHING) {
                    if (worlds[r.center.x + 1][r.center.y - 1] != Tileset.FLOWER) {
                        worlds[r.center.x + 1][r.center.y - 1] = Tileset.FLOOR;
                    }
                }

            }

        }
    }
    private TETile randomTile(TETile[][] tiles, int index) {
        Random tempers = new Random(172543);
        if (tempers.nextInt() % index == 0) {
            return Tileset.FLOWER;
        } else { //tempers.nextInt() % (index) == 1
            return Tileset.FLOWER;
        }
    }

    //add obstacles to the gameboard
    private String[] addObstaclesAndAvatar(TETile[][] tiles, long seeds) {
        Random tempers = new Random(seeds);
        int index = 2;
        for (Room room: rooms) {
            if (room.upperRight.x < 51 && room.upperRight.y < 51) {
                if (tiles[room.upperRight.x][room.upperRight.y] == Tileset.FLOOR) {
                    tiles[room.upperRight.x][room.upperRight.y] = randomTile(tiles, index);
                }

            }
        }
        while (true) {
            int x = RandomUtils.uniform(tempers, 0, 50);
            int y = RandomUtils.uniform(tempers, 0, 50);
            if (worlds[x][y] == Tileset.FLOOR) {
                worlds[x][y] = avatarType;
                String s = avatarType.description();
                return new String[] {String.valueOf(x), String.valueOf(y), s, String.valueOf(seeds)};
            }
        }


    }

    //attempts to connect rooms
    private void connectRooms(TETile[][] tiles, Room prevRoom, Room currRoom) {
        if (rooms.size() > 1) {
            //curr_index++;
            aBoolean = false;
            Point prev_center = prevRoom.upperLeft;
            Point curr_center = currRoom.bottomLeft;
            int highest_x = Math.max(prev_center.x, curr_center.x);
            int highest_y = Math.max(prev_center.y, curr_center.y);
            int lowest_x = Math.min(prev_center.x, curr_center.x);
            int lowest_y = Math.min(prev_center.y, curr_center.y);
            int middleX = (lowest_x + highest_x) / 2;
            int middleY = (lowest_y + highest_y) / 2;


            for (int i = lowest_x; i < highest_x; i++) {
                for (int k = lowest_y; k < lowest_y + 1; k++) {
                    if (i < 51 && k < 51) {
                        if (tiles[i][k] == Tileset.NOTHING) {
                            tiles[i][k] = Tileset.FLOOR;
                        }
                        if (tiles[i][k] == Tileset.WALL) {
                            tiles[i][k] = Tileset.FLOOR;
                        }
                    }
                }
            }

            for (int i = highest_x; i < highest_x + 1; i++) {
                for (int k = lowest_y; k < highest_y; k++) {
                    if (i < 51 && k < 51) {
                        if (tiles[i][k] == Tileset.NOTHING) {
                            tiles[i][k] = Tileset.FLOOR;
                        }
                        if (tiles[i][k] == Tileset.WALL) {
                            tiles[i][k] = Tileset.FLOOR;
                        }
                    }
                }
            }
            checkWalls(tiles);
        }
    }


    private void checkWalls(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {

                if (x > 0 && y > 0 && x < 50 && y < 50) {
                    if (tiles[x][y] == Tileset.FLOOR) {
                        if (tiles[x + 1][y] == Tileset.NOTHING) {
                            tiles[x + 1][y] = Tileset.WALL;
                        }
                        if (tiles[x - 1][y] == Tileset.NOTHING) {
                            tiles[x - 1][y] = Tileset.WALL;
                        }
                        if (tiles[x + 1][y + 1] == Tileset.NOTHING) {
                            tiles[x + 1][y + 1] = Tileset.WALL;
                        }
                        if (tiles[x - 1][y + 1] == Tileset.NOTHING) {
                            tiles[x - 1][y + 1] = Tileset.WALL;
                        }
                        if (tiles[x][y + 1] == Tileset.NOTHING) {
                            tiles[x][y + 1] = Tileset.WALL;
                        }
                        if (tiles[x - 1][y - 1] == Tileset.NOTHING) {
                            tiles[x - 1][y - 1] = Tileset.WALL;
                        }
                        if (tiles[x + 1][y - 1] == Tileset.NOTHING) {
                            tiles[x + 1][y - 1] = Tileset.WALL;
                        }
                        if (tiles[x][y - 1] == Tileset.NOTHING) {
                            tiles[x][y - 1] = Tileset.WALL;
                        }
                    }

                }
            }
        }
    }

    private void initializeEmpty(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }


    private void makeSquareRoom(TETile[][] tiles, int height, int width, int y_pos, int x_pos) {
        for (int y = 1; y < height; y++) {
            for (int x = 1; x < width; x++) {
                if (tiles[x_pos + x][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x + 1][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x - 1][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x][y_pos + y + 1] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x][y_pos + y - 1] != Tileset.NOTHING) {
                    return;
                }
            }
        }
        if (height - 1 >= 3 && width - 1 >= 3) {
            for (int y = 1; y < height; y++) {
                for (int x = 1; x < width; x++) {
                    tiles[x_pos + x][y_pos + y] = Tileset.WALL;
                }
            }
            Point bottomLeft = new Point(x_pos + 2, y_pos + 2);
            Point bottomRight = new Point(x_pos + width - 2, y_pos + 2);
            Point upperLeft = new Point(x_pos + 2, y_pos + height - 2);
            Point upperRight = new Point(x_pos + width - 2, y_pos + height - 2);
            Point center = new Point((2 * x_pos + width) / 2, (2 * y_pos + height) / 2);
            Room newRoom = new Room(roomsnumb, bottomLeft, bottomRight, upperLeft, upperRight, center);
            rooms.add(newRoom);
            roomsnumb++;
            aBoolean = true;


            for (int y = 2; y < height - 1; y++) {
                for (int x = 2; x < width - 1; x++) {
                    tiles[x_pos + x][y_pos + y] = Tileset.FLOOR;
                }
            }

        }

    }


    private void makeRectangleRoom(TETile[][] tiles, int height, int width, int y_pos, int x_pos) {
        for (int y = 1; y < height; y++) {
            for (int x = 3; x < width; x++) {
                if (tiles[x_pos + x][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x + 1][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x - 1][y_pos + y] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x][y_pos + y + 1] != Tileset.NOTHING) {
                    return;
                }
                if (tiles[x_pos + x][y_pos + y - 1] != Tileset.NOTHING) {
                    return;
                }
            }
        }
        if (height - 2 >= 5 && width - 2 >= 5) {
            for (int y = 1; y < height; y++) {
                for (int x = 3; x < width; x++) {
                    tiles[x_pos + x][y_pos + y] = Tileset.WALL;
                }
            }
            Point bottomLeft = new Point(x_pos + 4, y_pos + 2);
            Point bottomRight = new Point(x_pos + width - 2, y_pos + 2);
            Point upperLeft = new Point(x_pos + 4, y_pos + height - 2);
            Point upperRight = new Point(x_pos + width - 2, y_pos + height - 2);
            Point center = new Point((2 * x_pos + 2 + width / 2), (2 * y_pos + height) / 2);
            Room newRoom = new Room(roomsnumb, bottomLeft, bottomRight, upperLeft, upperRight, center);
            rooms.add(newRoom);
            roomsnumb++;
            aBoolean = true;

            for (int y = 2; y < height - 1; y++) {
                for (int x = 4; x < width - 1; x++) {
                    tiles[x_pos + x][y_pos + y] = Tileset.FLOOR;
                }
            }

        }

    }

    private static class Room implements Comparable<Room> {
        public Integer roomName;
        public Point bottomLeft;
        public Point bottomRight;
        public Point upperLeft;
        public Point upperRight;
        public Point center;


        public Room(Integer roomName, Point bottomLeft, Point bottomRight, Point upperLeft, Point upperRight, Point center) {
            this.roomName = roomName;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            this.upperLeft = upperLeft;
            this.upperRight = upperRight;
            this.center = center;
        }


        @Override
        public int compareTo(Room room) {
            return (int) Math.sqrt(Math.pow((this.center.y - room.center.y), 2) + Math.pow((this.center.x - room.center.x), 2));
        }
    }

    private static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


















//    public void interactWithKeyboard() {
//        displaymenu();
//        playing = true;
//        while (playing) {
//            char nextKey = getKey();
//            if(nextKey == 'n') {
//                newWorld();
//                oldDisplay = board[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
//                String[] ok = addObstaclesAndAvatar(board);
//                board = initialier(seedStarter());
//                whatCommand(ok);
//            } else if(nextKey == 'l') {
//                if (finaLTile.exists() && players.exists()) {
//                    board = Utils2.readObject(finaLTile, TETile[][].class);
//                    String[] newplayer = Utils2.readObject(players, String[].class);
//                }
//            }
//        }
//
//
//    }
//    public void interactWithKeyboard() {
//        displaymenu();
//        boolean sq_or_no = true;
//        while (sq_or_no) {
//            char nextKey = getKey();
//            if (nextKey == 'n') {
//                int random = seedStarter();
//
//                //initializeEmpty(finalWorldFrame);
//                //oldDisplay = board[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
//                board = initialier(new Random(random));
//                String[] avatar = addObstaclesAndAvatar(board);
//                String ava = avatar[0] + " " + avatar[1] +  " " + avatar[2];
//                oldDisplay = board[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
//                menuuus();
//                whatCommand(ava, random);
//                menuuus();
//            } else if (nextKey == 'l') {
//                if (FINALTILE.exists() && PLAYERS.exists()) {
//                    In out = new In("avatar.txt");
//                    In bo = new In("board.txt");
//                    String arr = bo.readLine();
//                    board = interactWithInputString(arr);
//                    String seed = "";
//                    for (int i = 0; i < arr.length(); i++) {
//                        if (arr.charAt(i) == 'n') {
//                            continue;
//                        } else if (arr.charAt(i) == 's') {
//                            i = arr.length() - 1;
//                        } else {
//                            seed += arr.charAt(i);
//                        }
//                    }
//                    String newAv = out.readLine();
//                    //seed and the path inputted
////                    board = in.();
//                    menuuus();
//                    playing = false;
//                    menuuus();
//                    whatCommand(newAv, Long.parseLong(seed));
//                    menuuus();
//                }  else if (nextKey == 'r') {
//                    System.out.print("entered");
//                    chooseAvatar();
//                    char nextestKey = getKey();
//
//                    if (nextestKey == 's') {
//                        avatarType = Tileset.SAND;
//                        displaymenu();
//                    } else if (nextestKey == 'm') {
//                        avatarType = Tileset.MOUNTAIN;
//                        displaymenu();
//                    } else if (nextestKey == 'w') {
//                        avatarType = Tileset.WATER;
//                        displaymenu();
//                    } else {
//                        avatarType = Tileset.AVATAR;
//                        displaymenu();
//                    }
//                }
//                else {
//                    sq_or_no = false;
//                }
////            } else if (nextKey == 'i') {
////                instructions();
////                if (getKey() == 'm') {
////                    displayMenu();
////                }
////            } else if (nextKey == 'a') {
////                chooseAvatar();
////                char nextestKey = getKey();
////                if (nextestKey == 's') {
////                    avatarType = Tileset.SAND;
////                    displayMenu();
////                } else if (nextestKey == 'm') {
////                    avatarType = Tileset.MOUNTAIN;
////                    displayMenu();
////                } else if (nextestKey == 'w') {
////                    avatarType = Tileset.WATER;
////                    displayMenu();
////                } else {
////                    avatarType = Tileset.AVATAR;
////                    displayMenu();
////                }
//                if (nextKey == 'q') {
//                    sq_or_no = false;
//                    return;
//                } else {
//                    System.out.println("Invalid command, please enter either 'n', 'l', 'a', or 'q'!!!!");
//                }
//            }
//        }
//    }
//    private String[] addObstaclesAndAvatar (TETile[][] tiles) {
//        Random tempers = new Random(9087122);
////        for (Room room: Room.rooms) {
////            if (room.upperRight.x < 51 && room.upperRight.y < 51) {
////                if (tiles[room.upperRight.x][room.upperRight.y] == Tileset.FLOOR) {
////                    tiles[room.upperRight.x][room.upperRight.y] = Tileset.GRASS;
////                }
////
////            }
////        }
//        while (true) {
//            int x = RandomUtils.uniform(tempers, 0, 50);
//            int y = RandomUtils.uniform(tempers, 0, 50);
//            if (board[x][y] == Tileset.FLOWER) {
//                board[x][y] = Tileset.AVATAR;
//                String s = Tileset.AVATAR.description();
//                return new String[]{String.valueOf(x), String.valueOf(y), s};
//            }
//        }
//
//    }
//
//    private char getKey() {
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                return Character.toLowerCase(StdDraw.nextKeyTyped());
//            }
//        }
//    }
//
//    public TETile[][] initialier (Random seed) {
//        int x = RandomUtils.uniform(seed, 0, 50);
//        int y = RandomUtils.uniform(seed, 0, 50);
//
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                board[i][j] = Tileset.FLOOR;
//            }
//        }
//        Board boardInstance = new Board(WIDTH, HEIGHT, board);
//        for (int i = 0; i < x; i++) {
//            board = boardInstance.changeBoard(seed, board);
////            board = boardInstance.hallerways(board, seed);
//        }
////        boolean sq_or_no = true;
////        Random rara = new Random(234);
////        while (Room.rooms.size() < 9) {
////            if (sq_or_no) {
////                Board.makeSquareRoom(finalWorldFrame, seed.nextInt(15), seed.nextInt(17), seed.nextInt(35), seed.nextInt(33));
////                sq_or_no = false;
////            } else {
////                makeRectangleRoom(finalWorldFrame, seed.nextInt(15), seed.nextInt(9), seed.nextInt(35), seed.nextInt(40));
////                sq_or_no = true;
////            }
////
////        }
//        board = Room.makePath(board);
//        board[x][y] = Tileset.GRASS;
//        return board;
//    }
//    public TETile[][] help(String input) {
//        String seedss = "";
//        String seeds = "";
//        StringBuilder avatarMovements = new StringBuilder();
//        boolean made = false;
//        for (int i = 0; i < input.length(); i++) {
//            switch (Character.toLowerCase(input.charAt(i))) {
//                case 'n':
//                    seedss += 'n';
//                    break;
//                case 'w':
//                    seedss += 'w';
//                    avatarMovements.append("w");
//                    break;
//                //System.out.print("HI");
//                case 'a':
//                    seedss += 'a';
//                    //System.out.print("WHY");
//                    avatarMovements.append("a");
//                    break;
//                case 's':
//                    if (made) {
//                        seedss += 's';
//                        //System.out.print("HI");
//                        avatarMovements.append("s");
//                    } else {
//                        seedss += 's';
//                        made = true;
//                    }
//                    break;
//                case 'd':
//                    seedss += 'd';
//                    avatarMovements.append("d");
//                    break;
//                case ':':
//                    seedss += ':';
//                    break;
//                case 'q':
//                    seedss += 'q';
//                    break;
//                case 'l':
//                    seedss += 'l';
//                    break;
//                default:
//                    seedss += input.charAt(i);
//                    seeds += input.charAt(i);
//                    break;
//            }
//        }
//        seedss = input;
//        int x_pos = 0;
//        int y_pos = 0;
//        boolean sq_or_no = true;
////        Sys   tem.out.println("n");
////        System.out.print(seeds);
//        long randoms = parseLong(seeds);
//        Random random = new Random(randoms);
//        board = initialier(random);
//        String[] avatar = addObstaclesAndAvatar(board);
//        x_pos = Integer.parseInt(avatar[0]);
//        y_pos = Integer.parseInt(avatar[1]);
//        return board;
//    }
//    public StringBuilder avatar(String input) {
//        String seedss = "";
//        String seeds = "";
//        StringBuilder avatarMovements = new StringBuilder();
//        boolean made = false;
//        for (int i = 0; i < input.length(); i++) {
//            switch (Character.toLowerCase(input.charAt(i))) {
//                case 'n':
//                    seedss += 'n';
//                    break;
//                case 'w':
//                    seedss += 'w';
//                    avatarMovements.append("w");
//                    break;
//                //System.out.print("HI");
//                case 'a':
//                    seedss += 'a';
//                    //System.out.print("WHY");
//                    avatarMovements.append("a");
//                    break;
//                case 's':
//                    if (made) {
//                        seedss += 's';
//                        //System.out.print("HI");
//                        avatarMovements.append("s");
//                    } else {
//                        seedss += 's';
//                        made = true;
//                    }
//                    break;
//                case 'd':
//                    seedss += 'd';
//                    avatarMovements.append("d");
//                    break;
//                case ':':
//                    seedss += ':';
//                    break;
//                case 'q':
//                    seedss += 'q';
//                    break;
//                case 'l':
//                    seedss += 'l';
//                    break;
//                default:
//                    seedss += input.charAt(i);
//                    seeds += input.charAt(i);
//                    break;
//            }
//        }
//        return avatarMovements;
//    }
//    public TETile[][] interactWithInputString(String input) {
//        String seedss = "";
//        String seeds = "";
//        StringBuilder avatarMovements = new StringBuilder();
//        boolean made = false;
//        for (int i = 0; i < input.length(); i++) {
//            switch (Character.toLowerCase(input.charAt(i))) {
//                case 'n':
//                    seedss += 'n';
//                    break;
//                case 'w':
//                    seedss += 'w';
//                    avatarMovements.append("w");
//                    break;
//                //System.out.print("HI");
//                case 'a':
//                    seedss += 'a';
//                    //System.out.print("WHY");
//                    avatarMovements.append("a");
//                    break;
//                case 's':
//                    if (made) {
//                        seedss += 's';
//                        //System.out.print("HI");
//                        avatarMovements.append("s");
//                    } else {
//                        seedss += 's';
//                        made = true;
//                    }
//                    break;
//                case 'd':
//                    seedss += 'd';
//                    avatarMovements.append("d");
//                    break;
//                case ':':
//                    seedss += ':';
//                    break;
//                case 'q':
//                    seedss += 'q';
//                    break;
//                case 'l':
//                    seedss += 'l';
//                    break;
//                default:
//                    seedss += input.charAt(i);
//                    seeds += input.charAt(i);
//                    break;
//            }
//        }
//        seedss = input;
//        int x_pos = 0;
//        int y_pos = 0;
//        boolean sq_or_no = true;
//        if(seedss.charAt(0) == 'n') {
////            System.out.println("n");
////            System.out.println(seeds);
//            long randoms = parseLong(seeds);
//            Random random = new Random(randoms);
//            board = initialier(random);
//            String[] avatar = addObstaclesAndAvatar(board);
//            x_pos = Integer.parseInt(avatar[0]);
//            y_pos = Integer.parseInt(avatar[1]);
////            System.out.println(avatarMovements);
//        } else {
////            System.out.println("nothing");
//            In bo = new In("board.txt");
//            String arr = bo.readLine();
////            System.out.println(arr);
////            System.out.println();
//            board = help(arr);
//            String hel = "";
//            for(int i = 0; i < avatar(arr).length(); i++) {
//                hel += avatar(arr).charAt(i);
//            }
//            for(int i = 1; i < input.length(); i++) {
//                hel += input.charAt(i);
//            }
//
//            seedss = hel;
////            System.out.println();
////            System.out.println(hel + " UGDUYGDKJHKJD");
//            seeds = "";
//        }
//
////        System.out.println(seedss);
//
//        for(int i = 0; i < avatarMovements.length(); i++) {
//            if(i == -1) {
////                System.out.print("l");
//                break;
//            }
//            if (seedss.charAt(i) == 'q') {
//                sq_or_no = false;
//                return board;
//            } if (seedss.charAt(i) == 'w') {
////                System.out.print("hi");
//                if (inBounds(x_pos, y_pos) && board[x_pos][y_pos + 1] != Tileset.WALL) {
//                    board[x_pos][y_pos + 1] = Tileset.AVATAR;
//                    board[x_pos][y_pos] = Tileset.FLOWER;
//                    y_pos += 1;
//                    // newWorld.renderFrame(board);
//                    menuuus();
//                }
//                menuuus();
//            } else if (seedss.charAt(i) == 'a') {
//                if (inBounds(x_pos - 1, y_pos) && board[x_pos - 1][y_pos] != Tileset.WALL) {
//                    board[x_pos - 1][y_pos] = Tileset.AVATAR;
//                    board[x_pos][y_pos] = Tileset.FLOWER;
//                    x_pos -= 1;
//                    //newWorld.renderFrame(board);
//                    menuuus();
//                }
//                menuuus();
//            } else if (seedss.charAt(i) == 's') {
//                //System.out.print("hi");
//                if ( inBounds(x_pos, y_pos - 1) && board[x_pos][y_pos - 1] != Tileset.WALL) {
//                    board[x_pos][y_pos - 1] = Tileset.AVATAR;
//                    board[x_pos][y_pos] = Tileset.FLOWER;
//                    y_pos -= 1;
//                    //newWorld.renderFrame(board);
//                    menuuus();
//                }
//                menuuus();
//            } else if (seedss.charAt(i) == 'd') {
//                // System.out.print("hi");
//                if (inBounds(x_pos + 1, y_pos) && board[x_pos + 1][y_pos] != Tileset.WALL) {
//                    board[x_pos + 1][y_pos] = Tileset.AVATAR;
//                    board[x_pos][y_pos] = Tileset.FLOWER;
//                    x_pos += 1;
//                    //newWorld.renderFrame(board);
//                    menuuus();
//                }
//                menuuus();
//            } else if (seedss.charAt(i) == ':') {
////                System.out.print("rec");
//                while (true) {
//                    if (seedss.charAt(i + 1) == 'q') {
//                        //System.out.print("rec");
//                        String s = Tileset.AVATAR.description();
//                        String avatar = String.valueOf(x_pos) + " "  + String.valueOf(y_pos) + " " + s + " " + seeds;
//                        Out out = new Out("avatar.txt");
//                        out.print(avatar);
////                        Utils2.writeObject(players, avatar);
//                        //TETile[][]
//                        //boardss = board;
////                        System.out.println();
////                        System.out.println(input);
//                        Out outer = new Out("board.txt");
//                        outer.print(input);
////                        Utils2.writeObject(finaLTile, board);
//                        menuuus();
//                        return board;
//
//                    }
//                    menuuus();
//                    break;
//                }
//            }
//
//        }
//        return board;
//    }
//
//    public void menuuus() {
//        //System.out.println((int) StdDraw.mouseX());
//        //System.out.println((int) StdDraw.mouseY());
//
//        int newX = (int) StdDraw.mouseX();
//        int newY = (int) StdDraw.mouseY();
//        if (newX != oldX || newY != oldY) {
//            oldX = newX;
//            oldY = newY;
//        }
//        String display = board[oldX][oldY].description();
//        if (!oldDisplay.equals(display)) {
//            oldDisplay = display;
//        }
//        StdDraw.setPenColor(StdDraw.WHITE);
//        StdDraw.text(36, 47, "Current tile is " + display);
//        StdDraw.show();
//    }
//
//    private void chooseAvatar() {
//        double x_coor = 0.5;
//        double y_coor = 0.8;
//        StdDraw.clear(StdDraw.RED);
//        StdDraw.setPenColor(StdDraw.BLACK);
//
//        StdDraw.text(x_coor, y_coor, "So, which type of avatar do you want to be?");
//        StdDraw.text(x_coor, y_coor - 0.05, "Here are your following options:");
//        StdDraw.text(x_coor, y_coor - 0.15, "1) Mountain (Press 'M')");
//        StdDraw.text(x_coor, y_coor - 0.25, "2) Sand (Press 'S')");
//        StdDraw.text(x_coor, y_coor - 0.35, "3) Water (Press 'W')");
//        StdDraw.text(x_coor, y_coor - 0.45, "4) Type anything else to default to the OG Avatar Symbol!");
//        StdDraw.show();
//    }
//
//    // Assuming the method is part of the Core.Engine class
////    public TETile[][] interactWithInputString(String input) {
////        TETile[][] b = board;
////        //System.out.print(input);
////        // Extract the seed and avatar movements
////
////        int x = 25;
////        int y = 25;
////        String[] newAv;
//////        if(finaLTile.exists()) {
//////            board = Utils2.readObject(finaLTile, TETile[][].class);
//////            newAv = Utils2.readObject(players, String[].class);
//////        }
//////        else {
////        //System.out.print(seeds);
////        if(seeds.equals("")) {
////            b = Utils2.readObject(finaLTile, TETile[][].class);
////            newAv = Utils2.readObject(players, String[].class);
////        } else {
////            //System.out.print(avatarMovements);
////            long seed = Long.parseLong(seeds);
////            Random random = new Random(seed);
////            // Initialize the board
////            TETile[][] board = new TETile[WIDTH][HEIGHT];
////            for (int a = 0; a < WIDTH; a++) {
////                for (int c = 0; c < HEIGHT; c++) {
////                    board[a][c] = Tileset.FLOOR;
////                }
////            }
////
////            // Generate the board
////            Board boardInstance = new Board(WIDTH, HEIGHT, board);
////            for (int i = 0; i < x; i++) {
////                board = boardInstance.changeBoard(random, board);
////            }
////            x = RandomUtils.uniform(random, 0, WIDTH);
////            y = RandomUtils.uniform(random, 0, HEIGHT);
////            board = Room.makePath(board);
////            board[x][y] = Tileset.GRASS;
////        }
////
////        if(players.exists()) {
////            System.out.print("hi");
////            String[] x2 = Utils2.readObject(players, String[].class);
////             x = Integer.parseInt(x2[0]);
////             y = Integer.parseInt(x2[1]);
////        }
////        int count = 0;
////        // Handle avatar movements
////        for (char move : avatarMovements.toString().toCharArray()) {
////            System.out.println(avatarMovements);
////                if(move == 'd') {
////                    if (inBounds(x + 1, y) && board[x + 1][y] != Tileset.WALL) {
////                        System.out.print("D");
////                        board[x + 1][y] = Tileset.AVATAR;
////                        board[x][y] = Tileset.FLOOR;
////                        x += 1;
////                        count++;
////                    }
////                }
////                if(move == 'w') {
////                    System.out.print("W");
////                    if (inBounds(x, y + 1) && board[x][y + 1] != Tileset.WALL) {
////                        board[x][y + 1] = Tileset.AVATAR;
////                        board[x][y] = Tileset.FLOOR;
////                        y += 1;
////                        count++;
////                    }
////                }
////                if(move == 'a') {
////                    System.out.print("A");
////                    if (inBounds(x - 1, y) && board[x - 1][y] != Tileset.WALL) {
////                        board[x - 1][y] = Tileset.AVATAR;
////                        board[x][y] = Tileset.FLOOR;
////                        x -= 1;
////                        count++;
////                    }
////                }
////                if(move == 's') {
////                    System.out.print("S");
////                    if (inBounds(x, y - 1) && board[x][y - 1] != Tileset.WALL) {
////                        board[x][y - 1] = Tileset.AVATAR;
////                        board[x][y] = Tileset.FLOOR;
////                        y -= 1;
////                        count++;
////                    }
////                }
////                if(move == ':') {
////                    if (input.charAt(count + 1) == 'q') {
////                        String s = Tileset.AVATAR.description();
////                        newAv = new String[]{String.valueOf(x), String.valueOf(y), s};
////                        Utils2.writeObject(players, newAv);
////                        TETile[][] lol = board;
////                        Utils2.writeObject(finaLTile, lol);
////                        System.exit(0);
////                    }
////                }
////                if(move == 'l') {
////                    if (finaLTile.exists() && players.exists()) {
////                        board = Utils2.readObject(finaLTile, TETile[][].class);
////                        newAv = Utils2.readObject(players, String[].class);
////                    }
////                }
////        }
////
////        return board;
////    }
//
//    // Helper method to check if the position (x, y) is within the bounds of the board
//    public boolean inBounds(int x, int y) {
//        if(x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
//            return false;
//        } if(board[x][y] == Tileset.WALL) {
//            return false;
//        }
//        return true;
//    }

//
//    private int seedStarter() {
//        double x_coor = 0.5;
//        double y_coor = 0.8;
//        StdDraw.clear(StdDraw.RED);
//        StdDraw.setPenColor(StdDraw.BLACK);
//
//        StdDraw.text(x_coor, y_coor, "You, my friend, have embarked on a new journey");
//        StdDraw.text(x_coor, y_coor - 0.2, "Please enter a seed value, ending with the character 's'");
//        StdDraw.text(x_coor, y_coor - 0.4, "Current seed value:");
//        StdDraw.show();
//        String inputSeed = "";
//
//        while(true) {
//            char nextKey = getKey();
//            if (nextKey == 's') {
//                break;
//            }
//            inputSeed += nextKey;
//            StdDraw.clear(StdDraw.RED);
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.text(x_coor, y_coor, "You, my friend, have embarked on a new journey");
//            StdDraw.text(x_coor, y_coor - 0.2, "Please enter a seed value, ending with the character 's'");
//            StdDraw.text(x_coor, y_coor - 0.4, "Current seed value:" + inputSeed);
//            StdDraw.show();
//        }
//        return (int) parseLong(inputSeed);
//    }
//
//
//    public void displaymenu() {
//        double x_coor = 0.5;
//        double y_coor = 0.8;
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//        Font fontBig = new Font("Monaco", Font.BOLD, 30);
//        StdDraw.setFont(fontBig);
//        StdDraw.text(x_coor, y_coor, "CS61BL: THE GAME");
//        Font fontRed = new Font("Monaco", Font.TRUETYPE_FONT, 15);
//        StdDraw.setFont(fontRed);
//        StdDraw.text(x_coor, y_coor - 0.2, "NEW GAME: N");
//        StdDraw.text(x_coor, y_coor - 0.25, "LOAD GAME: L");
//        StdDraw.text(x_coor, y_coor - 0.35, "CHOOSE AVATAR: R");
//        StdDraw.text(x_coor, y_coor - 0.3, "QUIT: Q");
////        Font fontBig = new Font("Monaco", Font.BOLD, 30);
////        StdDraw.setFont(fontBig);
////        StdDraw.textLeft(this.WIDTH / 2, this.HEIGHT / 2, "CS61B: THE GAME");
////        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
////        StdDraw.setFont(fontSmall);
////        //StdDraw.line(0, HEIGHT - 2, this.WIDTH, this.HEIGHT - 2);
////        StdDraw.textLeft(0, this.HEIGHT / 2, "CS61B: THE GAME");
//    }
//    public static void main(String[] args) {
//        Engine engine = new Engine();
//        TERenderer en = new TERenderer();
//        //engine.interactWithKeyboard();
//        //TETile[][] eng = engine.interactWithInputString("n80869031643s");
//
//        TETile[][] eng = engine.interactWithInputString("n1392967723524655428sddsaawws:q");
//        eng.toString();
//        en.initialize(51, 51);
////        //engine.interactWithKeyboard();
//////        en.renderFrame(eng);
//////        engine.toString();
//         eng = engine.interactWithInputString("laddw");
//
//        //ETile[][] eng = engine.interactWithInputString("n1392967723524655428sddsaawwsaddw");
//        //engine.interactWithKeyboard();
////        eng2.toString();
////        en.initialize(51, 51);
////        //engine.interactWithKeyboard();
////        en.renderFrame(eng2);
//        //TETile[][] eng = engine.interactWithInputString("sdwdd");
//        for (int i = 0; i < eng.length; i++) {
//            for (int j = 0; j < eng[0].length; j++) {
//                System.out.print(eng[i][j].description() + " ");
//            }
//        }
//        eng.toString();
//        en.initialize(51, 51);
//        //engine.interactWithKeyboard();
//        en.renderFrame(eng);
//        engine.toString();
//    }


}

