import rasterize.FilledLineRasterizer;
import rasterize.LineRasterizer;
import rasterize.RasterBufferedImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		panel.requestFocus();
		panel.requestFocusInWindow();

		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				clear(0xffffff);
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

				lineRasterizer.rasterize(startClickX, startClickY, endClickX, endClickY, Color.YELLOW);

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