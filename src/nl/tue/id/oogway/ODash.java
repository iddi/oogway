/*
 This file is part of Oogway.

 Oogway is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Oogway is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with PTurtle.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tue.id.oogway;
import processing.core.PApplet;
import static processing.core.PApplet.*;

/**
 * ODash class. 
 * 
 * @author Jun Hu
 * @author Loe Feijs
 * 
 */
public class ODash {
	
	private float[] pattern = null;
	private PApplet applet;
	
	public ODash(PApplet applet){
		this.applet = applet;
		this.pattern = new float[] {10,5};
	}
	
	public ODash(PApplet applet, float[] pattern){
		this.applet = applet;
		this.pattern = pattern;
	}
	
	public void copy(ODash dash){
		this.applet = dash.applet;
		this.pattern = dash.pattern;
	}
	
	public ODash clone(){
		return new ODash(applet, pattern);
	}
	
	
	/*
	 * Draw a dashed line with given set of dashes and gap lengths. x0 starting
	 * x-coordinate of line. y0 starting y-coordinate of line. x1 ending
	 * x-coordinate of line. y1 ending y-coordinate of line. spacing array
	 * giving lengths of dashes and gaps in pixels; an array with values {5, 3,
	 * 9, 4} will draw a line with a 5-pixel dash, 3-pixel gap, 9-pixel dash,
	 * and 4-pixel gap. if the array has an odd number of entries, the values
	 * are recycled, so an array of {5, 3, 2} will draw a line with a 5-pixel
	 * dash, 3-pixel gap, 2-pixel dash, 5-pixel gap, 3-pixel dash, and 2-pixel
	 * gap, then repeat.
	 */
	public void draw(float x0, float y0, float x1, float y1) {
		if (pattern == null) {
			applet.line(x0, y0, x1, y1);
			return;
		}
		
		if (pattern.length<2){
			applet.line(x0, y0, x1, y1);
			return;
		}
		
		
		float distance = dist(x0, y0, x1, y1);
		float[] xSpacing = new float[pattern.length];
		float[] ySpacing = new float[pattern.length];
		float drawn = 0.0f; // amount of distance drawn

		if (distance > 0) {
			int i;
			boolean drawLine = true; // alternate between dashes and gaps

			/*
			 * Figure out x and y distances for each of the spacing values I
			 * decided to trade memory for time; I'd rather allocate a few dozen
			 * bytes than have to do a calculation every time I draw.
			 */
			for (i = 0; i < pattern.length; i++) {
				xSpacing[i] = lerp(0, (x1 - x0), pattern[i] / distance);
				ySpacing[i] = lerp(0, (y1 - y0), pattern[i] / distance);
			}

			i = 0;
			while (drawn < distance) {
				/* Add distance "drawn" by this line or gap */
				drawn = drawn + mag(xSpacing[i], ySpacing[i]);

				if (drawLine) {
					if (drawn < distance)
						applet.line(x0, y0, x0 + xSpacing[i], y0 + ySpacing[i]);
					else
						applet.line(x0, y0, x1, y1);
				}
				x0 += xSpacing[i];
				y0 += ySpacing[i];
				i = (i + 1) % pattern.length; // cycle through array
				drawLine = !drawLine; // switch between dash and gap
			}
		}
	}	
	

}
