package tuenti.contest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Alfonso{

	private class Shape {
		private Rectangle rect;
		private int color;
		public Rectangle getRect() {
			return rect;
		}
		public void setRect(Rectangle rect) {
			this.rect = rect;
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public Shape(Rectangle rect, int color) {
			super();
			this.rect = rect;
			this.color = color;
		}
		public double getArea() {
			return this.rect.getWidth() * this.rect.getHeight(); 
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Alfonso main = new Alfonso();
		main.process();
	}
	public void process() throws IOException {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String test = in.nextLine();
			String[] values = test.split(" ");
			int width = Integer.parseInt(values[0]);
			int height = Integer.parseInt(values[1]);
			int numPolygons = Integer.parseInt(values[2]);
			ArrayList<Shape> shapes = new ArrayList<Shape>();
			Rectangle rect = new Rectangle(0, 0, width, height);
			shapes.add(new Shape(rect,1));
			
			for (int i = 0; i < numPolygons; i++) {				
				int xStart = Integer.parseInt(values[2+i*5+1]);
				int xEnd = Integer.parseInt(values[2+i*5+3]);
				int yStart = Integer.parseInt(values[2+i*5+2]);
				int yEnd = Integer.parseInt(values[2+i*5+4]);
				int color = Integer.parseInt(values[2+i*5+5]);
				rect = new Rectangle(xStart, yStart, xEnd-xStart, yEnd-yStart);
				shapes.add(new Shape(rect,color));
			}
			System.out.println(magic(shapes, width, height));
		}
	}

	public String magic (ArrayList<Shape> shapes, int width, int height) {
        BufferedImage img = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);  
        img.createGraphics();  
        Graphics2D g = (Graphics2D)img.getGraphics();

        for (Shape shape : shapes) {
            g.setColor(new Color(0, 0, shape.getColor()));  
            g.fillRect((int)shape.rect.getX(), (int)shape.rect.getY(), (int)shape.rect.getWidth(), (int)shape.rect.getHeight());  
        }
        int color;
        Map<Integer,Integer> map = new TreeMap<Integer,Integer>();
       	for (int j = 0; j < height; j++) {
       		for (int i = 0; i < width; i++) {
        		color = img.getRGB(i, j);
        		color = color & 0x0000FF;
        		//System.out.print(color);
        		if (map.containsKey(color)) {
        			map.put(color, map.get(color)+1);
        		} else {
        			map.put(color, 1);
        		}
        	}
        	//System.out.println("");
        }
        String ret = "";
        boolean first = true;
        for (Integer key : map.keySet()) {
        	if (!first) {
        		ret += " ";
        	}
        	first = false;
        	ret += key + " " + map.get(key);
        }
        
		return ret;
	}


}
