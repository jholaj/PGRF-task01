import model.*;
import rasterize.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * trida pro kresleni na platno: zobrazeni pixelu
 * 
 * @author PGRF FIM UHK
 * @version 2023
 */

public class Canvas {

	private JFrame frame;
	private JPanel panel;
	private RasterBufferedImage raster;
	private LineRasterizer lineRasterizer;
	private DottedLineRasterizer dottedLineRasterizer;
	private PolygonRasterizer polygonRasterizer;
	private int startClickX, startClickY, endClickX, endClickY;

	// DRAWN LINES
	private List<Line> lines = new ArrayList<>();
	// DRAWN POLYGONS
	private List<Polygon> polygons = new ArrayList<>();
	// HELP LINES FOR H/V/D LINES
	private List<Line> helpLines = new ArrayList<>();
	//drag line
	private Line currentLine;
	private Polygon polygon;
	private boolean editMode, shiftPressed, polygonMode = false;


	public Canvas(int width, int height) {
		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		raster = new RasterBufferedImage(width, height);

		lineRasterizer = new FilledLineRasterizer(raster);
		dottedLineRasterizer = new DottedLineRasterizer(raster, 10);
		polygonRasterizer = new PolygonRasterizer(lineRasterizer);

		polygon = new Polygon();

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};

		panel.setPreferredSize(new Dimension(width, height));
		panel.requestFocus();
		panel.requestFocusInWindow();

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();

		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {

			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {

				// HORIZONTAL / VERTICAL / DIAGONAL LINES
				if(keyEvent.getKeyCode() == KeyEvent.VK_SHIFT){
					shiftPressed = true;
				}

				if(keyEvent.getKeyCode() == KeyEvent.VK_CAPS_LOCK){
					if (polygonMode) {
						System.out.println("Caps pressed again... Polygon mode turned off");
						polygonMode = false;
						clear(0x000000);
						// draw all lines
						for (Line line : lines) {
							lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
						}
						for (Polygon polygon : polygons) {
							polygonRasterizer.rasterize(polygon);
						}
						panel.repaint();
					} else {
						System.out.println("Caps pressed... Polygon mode");
						//clear polygon object
						polygon = new Polygon();
						polygonMode = true;
					}
				}

				// REMOVE ALL LINES
				if(keyEvent.getKeyCode() == KeyEvent.VK_C){
					System.out.println("C pressed - CLEAR CANVAS");
					clear(0x000000);
					lines.clear();
					polygons.clear();
					polygon = new Polygon();
					panel.repaint();
				}

			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {

			}
		});


		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				//if not editmode => raster all lines
				if(!editMode){
					clear(0x000000);
					for (Line line : lines) {
						lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
					}
					// if shift pressed => draw help line
					if (shiftPressed) {
						int deltaX = e.getX() - startClickX;
						int deltaY = e.getY() - startClickY;

						double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

						// calc nearest multiple of 45
						int nearestAngle = (int) (Math.round(angle / 45) * 45);
						// if negative => add 360°
						if (angle < 0) {
							nearestAngle = (nearestAngle + 360) % 360;
						}

						double radians = Math.toRadians(nearestAngle);

						double lineLength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

						int x2 = (int) (startClickX + Math.cos(radians) * lineLength);
						int y2 = (int) (startClickY + Math.sin(radians) * lineLength);

						helpLines.clear();

						Line line = new Line(startClickX, startClickY, x2, y2, 0xffff00);

						helpLines.add(line);

						for (Line helpLine : helpLines) {
							lineRasterizer.rasterize(helpLine.getX1(), helpLine.getY1(), helpLine.getX2(), helpLine.getY2(), Color.YELLOW);
						}

					} else {
						// if shift not pressed => draw drag line
						lineRasterizer.rasterize(startClickX, startClickY, e.getX(), e.getY(), Color.YELLOW);
					}

				} else if (currentLine != null) {
					currentLine.setX1(startClickX);
					currentLine.setY1(startClickY);
					currentLine.setX2(e.getX());
					currentLine.setY2(e.getY());

					clear(0x000000);

					for (Line line : lines) {
						if (line == currentLine) {
							// if edit mode => draw dotted line
							dottedLineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
						} else {
							lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
						}
					}
				}

				//polygons
				for (Polygon polygon : polygons) {
					polygonRasterizer.rasterize(polygon);
				}

				panel.repaint();

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(polygon.getSize() > 1 && polygonMode) {
					clear(0x000000);

					//draw base of polygon
					lineRasterizer.rasterize(polygon.getPoint(0).x, polygon.getPoint(0).y, startClickX, startClickY, Color.YELLOW);

					//draw drawn polygons and lines
					for (Polygon polygon : polygons) {
						polygonRasterizer.rasterize(polygon);
					}
					for (Line line : lines) {
						lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
					}

					//draw help lines
					dottedLineRasterizer.rasterize(startClickX, startClickY,e.getX(), e.getY(), Color.YELLOW);
					dottedLineRasterizer.rasterize(polygon.getPoints().get(0).x, polygon.getPoints().get(0).y,e.getX(), e.getY(), Color.YELLOW);

				}
				panel.repaint();

			}
		});

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				shiftPressed = false;

				startClickX = e.getX();
				startClickY = e.getY();

				if (e.getButton() == MouseEvent.BUTTON3) {
					editMode = true;
					for (Line line : lines) {
						if (line.FindClosePoint(startClickX, startClickY, line)) {
							currentLine = line;
							System.out.println("Close point found!");
							break;
						}
					}
				}

				if(polygonMode){
					Point p = new Point(e.getX(), e.getY());
					polygon.addPoint(p);
					polygons.add(polygon);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				endClickX = e.getX();
				endClickY = e.getY();
				clear(0x000000);


				Line finalLine = new Line(startClickX, startClickY, endClickX, endClickY, 0xffff00);

				if (!shiftPressed) {
					lines.add(finalLine);
				} else if (shiftPressed) {
					lines.add(helpLines.get(0));
				}

				for (Line line : lines) {
					lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
				}

				editMode = false;
				currentLine = null;

				for (Polygon polygon : polygons) {
					polygonRasterizer.rasterize(polygon);
				}
				panel.repaint();

			}
		});

	}

	public void clear(int color) {
		raster.setClearColor(color);
		raster.clear();
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		drawString(raster.getGraphics(), "H/V/D LINES - SHIFT\nEDIT LINES - RIGHT MOUSE CLICK\nPOLYGON MODE - CAPS", 575, 525);
		panel.repaint();
	}

	public void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}