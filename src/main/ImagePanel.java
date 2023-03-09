package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	 private BufferedImage bufferedImage;
	 private Image image;
	 public ImagePanel() {
	 super();

	 // Plik umieszczony w podpakiecie "obrazki"
	 URL resource = getClass().getResource("zdjecie.jpg");
	 try {
	 bufferedImage = ImageIO.read(resource);
	 image = bufferedImage.getScaledInstance(300, 500, Image.SCALE_DEFAULT);
	 } catch (IOException e) {
	 System.err.println("Blad odczytu obrazka");
	 e.printStackTrace();
	 }
	 Dimension dimension =
	new Dimension(image.getWidth(null), image.getHeight(null));
	 setPreferredSize(dimension);
	 }
	 public void paintComponent(Graphics g) {
	 Graphics2D g2d = (Graphics2D) g;
	 g2d.drawImage(image, 0, 0, this);
	 }
	}