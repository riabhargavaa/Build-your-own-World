package byow.Core;

import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path {
    Point centerone;
    Point centertwo;
    static List<Path> paths = new ArrayList<>();

    public Path(Point ul, Point ur) {
        centerone = ul;
        centertwo = ur;
    }

    public void addPath(Path c) {
        paths.add(c);
    }
}
