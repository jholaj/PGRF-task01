import model.Line;
import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

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

	private int startClickX, startClickY, endClickX, endClickY;
	private List<Line> lines = new ArrayList<>();


	public Canvas(int width, int height) {
		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);

		lineRasterizer = new FilledLineRasterizer(raster);


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
					System.out.println("Shift pressed - LINE SHIFT");

				}

				// REMOVE ALL LINES
				if(keyEvent.getKeyCode() == KeyEvent.VK_C){
					System.out.println("C pressed - CLEAR CANVAS");
					clear(0x000000);
					lines.clear();
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
				clear(0x000000);
				for (Line line : lines) {
					lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
				}

				lineRasterizer.rasterize(startClickX, startClickY, e.getX(), e.getY(), Color.YELLOW);

				panel.repaint();
			}

		});

		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				startClickX = e.getX();
				startClickY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				endClickX = e.getX();
				endClickY = e.getY();
				Line finalLine = new Line(startClickX, startClickY, endClickX, endClickY, 0xffff00);
				lines.add(finalLine);

				for (Line line : lines) {
					lineRasterizer.rasterize(line.getX1(), line.getY1(), line.getX2(), line.getY2(), Color.YELLOW);
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
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}