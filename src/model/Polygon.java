package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private static final List<Point> points = new ArrayList<>();

    public Polygon() {
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public static Point getPoint(int index){
        return points.get(index);
    }

    public List<Point> getPoints(){
        return points;
    }

}
