package net.yiliufeng.windows_control.dao;

public class Location {
    private Float dx;
    private Float dy;

    public Location(Float dx, Float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Float getDx() {
        return dx;
    }

    public void setDx(Float dx) {
        this.dx = dx;
    }

    public Float getDy() {
        return dy;
    }

    public void setDy(Float dy) {
        this.dy = dy;
    }
}