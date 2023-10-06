package rasterize;


public class FilledLineRasterizer extends LineRasterizer {

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    protected void drawLine(int x1, int y1, int x2, int y2) {
        calcMidpoint(x1, y1, x2, y2);
    }

    // MIDPOINT
    private void calcMidpoint(int x1, int y1, int x2, int y2) {
        int sx, sy;
        sx = (x1 + x2) / 2;
        sy = (y1 + y2) / 2;
        raster.setPixel(sx, sy, this.color.getRGB());

        if (Math.abs(x1 - sx) > 1 || Math.abs(y1 - sy) > 1) {
            drawLine(x1, y1, sx, sy);
        }

        if (Math.abs(x2 - sx) > 1 || Math.abs(y2 - sy) > 1) {
            drawLine(sx, sy, x2, y2);
        }
    }
}