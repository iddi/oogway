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

import java.util.Hashtable;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PShape;
import static processing.core.PApplet.*;

// TODO: Auto-generated Javadoc
/**
 * Oogway class, making ideas from the LOGO teaching language available in
 * Processing. It was motivated by the requirements emerged from the Gold Ratio
 * module for the master students at Department of Industrial Design, Eindhoven
 * University of Technology.
 * 
 * @author Jun Hu
 * @author Loe Feijs
 * 
 */

public class Oogway implements Cloneable, OConstants {

	/** The PApplet to render to. */
	private PApplet applet;

	/** The dash trace that oogway is following */
	private ODash dash;

	/** The angle (in degrees) that the oogway is heading. */
	private float heading = 0.0f;

	/** If false, the oogway moves but does not leave a trail. */
	private boolean isDown = true;

	/** The memories. */
	private Hashtable<String, Oogway> memories = new Hashtable<String, Oogway>();

	/** The current shape of the oogway itself. By default it is an arrow. */
	private int oogwayShape = OARROW;

	/** The current shape of the oogway that is defined using a SVG file. */
	private PShape oogwayShapeSVG = null;

	/** The path that oogway is currently leaving on the canvas. */
	private OPath path;

	/** Color of line drawn by Oogway (as a Processing color). */
	private int penColor;

	/** The size of the pen of the oogway. */
	private float penSize = 1;

	/** Indicator whether the oogway is reflecting its trace. */
	private int reflect = 1;

	/** The spline trace that oogway is leaving on the canvas. */
	private OSpline spline;

	/** The states of the oogway, working as a stack. */
	private Vector<Oogway> states = new Vector<Oogway>();

	/**
	 * The indicator of the type of the current trace that oogway is leaving on
	 * the canvas.
	 */
	private int trace = OLINE;

	/**
	 * x location on screen. Any change to this variable must be done using
	 * setPosition.
	 */
	private float xcor;
	/**
	 * y location on screen. Any change to this variable must be done using
	 * setPosition.
	 */
	private float ycor;

	/**
	 * Standard constructor, creates a Oogway in the middle of the screen which
	 * draws in white.
	 * 
	 * @param applet
	 *            PApplet to render to.
	 */
	public Oogway(PApplet applet) {
		this.applet = applet;
		setPosition(xcor = applet.width / 2, applet.height / 2);
		penColor = applet.color(255, 255, 255);
		spline = new OSpline(applet);
		path = new OPath(applet);
		dash = new ODash(applet);

	}

	/**
	 * Move Oogway backward.
	 * 
	 * @param distance
	 *            the distance
	 */
	public void backward(float distance) {
		// forward(-distance); //this might cause problems;
		if (distance < 0) {
			System.err.println("Oogway says: distance can not be negative!");
			return;
		}

		left(180);
		forward(distance);
		right(180);
	}

	/**
	 * @param distance
	 * @param svg
	 */
	public void backward(float distance, String svg) {
		beginPath(svg);
		backward(distance);
		endPath();
	}

	/**
	 * 
	 */
	public void beginDash() {
		trace = ODASH;
	}

	/**
	 * @param pattern
	 */
	public void beginDash(float[] pattern) {
		dash = new ODash(applet, pattern);
		trace = ODASH;
	}

	/**
	 * Begin path.
	 */
	public void beginPath() {
		trace = OPATH;
	}

	/**
	 * Begin path.
	 * 
	 * @param path
	 *            the path
	 */
	public void beginPath(String path) {
		this.path.loadPath(path);
		beginPath();
	}

	/**
	 * Begin reflection.
	 */
	public void beginReflection() {
		reflect *= -1;
	}

	/**
	 * Begin spline.
	 */
	public void beginSpline() {
		beginSpline(xcor, ycor);
	}

	/**
	 * Begin spline.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void beginSpline(float x, float y) {
		spline.clear();
		spline.curveVertex(x, y);
		spline.curveVertex(xcor, ycor);
		trace = OSPLINE;
	}

	/**
	 * Bk.
	 * 
	 * @param distance
	 *            the distance
	 */
	public void bk(float distance) {
		backward(distance);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Oogway clone() {
		Oogway o = new Oogway(applet);
		o.copy(this);
		return o;
	}

	/**
	 * Copy.
	 * 
	 * @param o
	 *            the o
	 */
	protected void copy(Oogway o) {
		applet = o.applet;
		setPosition(o.xcor, o.ycor);
		penColor = o.penColor;
		penSize = o.penSize;
		isDown = o.isDown;
		heading = o.heading;
		reflect = o.reflect;
		trace = o.trace;
		oogwayShape = o.oogwayShape;
		oogwayShapeSVG = o.oogwayShapeSVG;
		path.copy(o.path);
		spline.copy(o.spline);
		dash.copy(o.dash);

		/* do not copy memories */
		/* do not copy states */

	}

	/**
	 * @param distance
	 */
	public void dashBackward(float distance) {
		beginDash();
		backward(distance);
		endDash();
	}

	/**
	 * @param distance
	 * @param pattern
	 */
	public void dashBackward(float distance, float[] pattern) {
		beginDash(pattern);
		backward(distance);
		endDash();
	}

	/**
	 * @param distance
	 */
	public void dashForward(float distance) {
		beginDash();
		forward(distance);
		endDash();
	}

	/**
	 * @param distance
	 * @param pattern
	 */
	public void dashForward(float distance, float[] pattern) {
		beginDash(pattern);
		forward(distance);
		endDash();
	}

	/**
	 * Get the distance between this Oogway and point (x,y).
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return distance in pixels.
	 */
	public float distance(float x, float y) {
		return sqrt(pow((x - xcor), 2) + pow((y - ycor), 2));
	}

	/**
	 * Down.
	 */
	public void down() {
		penDown();
	}

	/**
	 * Draw dash.
	 * 
	 * @param distance
	 *            the distance
	 */
	private void drawDash(float x, float y) {
		if (isDown) {
			applet.pushStyle();
			applet.noFill();
			applet.stroke(penColor);
			applet.strokeWeight(penSize);
			dash.draw(xcor, ycor, x, y);
			applet.popStyle();
		}

	}

	/**
	 * Move the Oogway to a specified point. Used internally to allow
	 * HistoryOogway to override this to record lines.
	 * 
	 * @param x
	 *            location in x axis
	 * @param y
	 *            location in y axis
	 */
	private void drawLine(float x, float y) {

		if (isDown) {
			applet.pushStyle();
			applet.stroke(penColor);
			applet.strokeWeight(penSize);

			applet.line(xcor, ycor, x, y);

			applet.popStyle();
		}

	}

	/**
	 * Draw path.
	 * 
	 * @param distance
	 *            the distance
	 */
	private void drawPath(float distance) {

		float rotRad = radians(heading);

		path.transform(xcor, ycor, distance, rotRad, reflect);

		if (isDown) {
			applet.pushStyle();
			applet.noFill();
			applet.stroke(penColor);
			applet.strokeWeight(penSize);
			path.draw();
			applet.popStyle();
		}
	}

	/**
	 * Draw spline.
	 */
	private void drawSpline() {

		if (isDown) {
			applet.pushStyle();
			applet.noFill();
			applet.stroke(penColor);
			applet.strokeWeight(penSize);
			spline.draw();
			applet.popStyle();
		}

	}

	/**
	 * 
	 */
	public void endDash() {
		trace = OLINE;
	}

	/**
	 * End path.
	 */
	public void endPath() {
		trace = OLINE;
	}

	/**
	 * End reflection.
	 */
	public void endReflection() {
		reflect *= -1;
	}

	/**
	 * End spline.
	 */
	public void endSpline() {
		endSpline(xcor, ycor);
	}

	/**
	 * End spline.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void endSpline(float x, float y) {
		spline.curveVertex(x, y);
		drawSpline();
		spline.clear();
		trace = OLINE;
	}

	/**
	 * Fd.
	 * 
	 * @param distance
	 *            the distance
	 */
	public void fd(float distance) {
		forward(distance);
	}

	/**
	 * Move Oogway forward.
	 * 
	 * @param distance
	 *            the distance
	 */
	public void forward(float distance) {

		if (distance < 0) {
			System.err.println("Oogway says: distance can not be negative!");
			return;
		}

		float x, y;
		float rotRad = radians(heading);
		x = xcor + (distance * cos(rotRad));
		y = ycor + (distance * sin(rotRad));

		switch (trace) {
		case OLINE:
			drawLine(x, y);
			break;
		case OSPLINE:
			spline.curveVertex(x, y);
			break;
		case OPATH:
			drawPath(distance);
			break;
		case ODASH:
			drawDash(x, y);
			break;
		}

		setPosition(x, y);
	}

	public void forward(float distance, String svg) {
		beginPath(svg);
		forward(distance);
		endPath();
	}

	/**
	 * Get the angle that the Oogway is facing.
	 * 
	 * @return angle in degrees.
	 */
	public float heading() {
		return heading;
	}

	/**
	 * Home.
	 */
	public void home() {
		setPosition(xcor = applet.width / 2, applet.height / 2);
		setHeading(0f);
	}

	/**
	 * Checks if is down.
	 * 
	 * @return true, if is down
	 */
	public boolean isDown() {
		return isDown;
	}

	/**
	 * @return
	 */
	public boolean isReflecting() {
		return reflect == -1;
	}

	/**
	 * Turn the Oogway left.
	 * 
	 * @param angle
	 *            the angle
	 */
	public void left(float angle) {
		heading -= angle * reflect;
	}

	/**
	 * Lt.
	 * 
	 * @param angle
	 *            the angle
	 */
	public void lt(float angle) {
		left(angle);
	}

	/**
	 * Mirror the position of Oogway over a line. <strong>This does not
	 * draw</strong>.
	 */
	public void mirrorPosition(float x0, float y0, float x1, float y1) {
		float dx, dy, a, b;
		float x2, y2;

		dx = x1 - x0;
		dy = y1 - y0;

		a = (dx * dx - dy * dy) / (dx * dx + dy * dy);
		b = 2 * dx * dy / (dx * dx + dy * dy);

		x2 = a * (xcor - x0) + b * (ycor - y0) + x0;
		y2 = b * (xcor - x0) - a * (ycor - y0) + y0;

		setPosition(x2, y2);
	}

	/**
	 * @param distance
	 */
	public void pathBackward(float distance) {
		beginPath();
		backward(distance);
		endPath();
	}
	
	/**
	 * 
	 * @param distance
	 * @param svg
	 */

	public void pathBackward(float distance, String svg) {
		beginPath(svg);
		backward(distance);
		endPath();
	}

	/**
	 * @param distance
	 */
	public void pathForward(float distance) {
		beginPath();
		forward(distance);
		endPath();
	}

	/**
	 * @param distance
	 * @param svg
	 */
	public void pathForward(float distance, String svg) {
		beginPath(svg);
		forward(distance);
		endPath();
	}

	/**
	 * Pd.
	 */
	public void pd() {
		penDown();
	}

	/**
	 * Pen color.
	 * 
	 * @return the int
	 */
	public int penColor() {
		return penColor;
	}

	/**
	 * put the pen down (draw subsequent movements).
	 */
	public void penDown() {
		isDown = true;
	}

	/**
	 * @return
	 */
	public float penSize() {
		return penSize();
	}

	/**
	 * take the pen up (do not draw subsequent movements).
	 */
	public void penUp() {
		isDown = false;
	}

	/**
	 * popState.
	 */
	public void popState() {
		if (states.size() < 1)
			return;
		Oogway o = states.get(states.size() - 1);
		states.remove(states.size() - 1);
		copy(o);
	}

	/**
	 * Pu.
	 */
	public void pu() {
		penUp();
	}

	/**
	 * 
	 */
	public void pushState() {
		states.add(clone());
	}

	/**
	 * @param c
	 */
	public void recall(char c) {
		recall(String.valueOf(c));
	}

	/**
	 * @param i
	 */
	public void recall(int i) {
		recall(String.valueOf(i));
	}

	/**
	 * @param s
	 */
	public void recall(String s) {
		if (memories.containsKey(s)) {
			copy(memories.get(s));
		}
	}

	/**
	 * @param c
	 */
	public void remember(char c) {
		remember(String.valueOf(c));
	}

	/**
	 * @param i
	 */
	public void remember(int i) {
		remember(String.valueOf(i));
	}

	/**
	 * @param s
	 */
	public void remember(String s) {
		memories.put(s, clone());
	}

	/**
	 * Turn the Oogway right.
	 * 
	 * @param angle
	 *            heading in degrees.
	 */
	public void right(float angle) {
		heading += angle * reflect;
	}

	/**
	 * Rt.
	 * 
	 * @param angle
	 *            the angle
	 */
	/**
	 * @param angle
	 */
	public void rt(float angle) {
		right(angle);
	}

	/**
	 * Set the direction the Oogway is facing in to an absolute angle.
	 * 
	 * @param angle
	 *            the new heading
	 */
	public void setHeading(float angle) {
		heading = angle;
	}

	/**
	 * Set the color the Oogway draws with.
	 * 
	 * @param c
	 *            a color created with
	 *            {@link processing.core.PApplet#color(int, int, int)}.
	 */
	public void setPenColor(int c) {
		penColor = c;
	}

	/**
	 * Set the colour the Oogway draws with.
	 * 
	 * @param r
	 *            red value, 0-255.
	 * @param g
	 *            green value, 0-255.
	 * @param b
	 *            blue value, 0-255.
	 */
	public void setPenColor(int r, int g, int b) {
		penColor = applet.color(r, g, b);
	}

	/**
	 * @param size
	 */
	public void setPenSize(float size) {
		penSize = size;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPos(float x, float y) {
		setPosition(x, y);
	}

	/**
	 * Move the Oogway to an absolute location. <strong>This does not
	 * draw</strong>.
	 * 
	 * @param x
	 *            location in x axis.
	 * @param y
	 *            location in y axis.
	 */
	public void setPosition(float x, float y) {
		xcor = x;
		ycor = y;
	}

	/**
	 * @param svg
	 */
	public void setStamp(String svg) {
		this.oogwayShapeSVG = applet.loadShape(svg);
		this.oogwayShape = OSVG;
	}

	/**
	 * Shift the Oogway along an absolute angle for a certain distance.
	 * <strong>This does not draw</strong>.
	 * 
	 * @param angle
	 *            an absolute angle for shifting.
	 * @param distance
	 *            distance for shifting.
	 */
	public void shift(float angle, float distance) {
		// -distance  might cause problems;
		if (distance < 0) {
			System.err.println("Oogway says: distance can not be negative!");
			return;
		}
		setPosition(xcor + distance * cos(radians(angle)), ycor + distance
				* sin(radians(angle)));
	}

	/**
	 * @param distance
	 */
	public void shiftBackward(float distance) {
		shiftRight(180, distance);
	}

	/**
	 * @param distance
	 */
	public void shiftForward(float distance) {
		shiftRight(0, distance);
	}
	
	/**
	 * Shift the Oogway along a a relative angle towards left for a certain
	 * distance. <strong>This does not draw</strong>.
	 * 
	 * @param angle
	 *            a relative angle for shifting.
	 * @param distance
	 *            distance for shifting.
	 */

	public void shiftLeft(float angle, float distance) {
		shift(heading - reflect * angle, distance);
	}

	/**
	 * Shift the Oogway along a a relative angle towards right for a certain
	 * distance. <strong>This does not draw</strong>.
	 * 
	 * @param angle
	 *            a relative angle for shifting.
	 * @param distance
	 *            distance for shifting.
	 */

	public void shiftRight(float angle, float distance) {
		shift(heading + reflect * angle, distance);
	}

	/**
	 * 
	 */
	public void stamp() {
		stamp(20);
	}

	/**
	 * @param size
	 */
	public void stamp(float size) {
		stamp(size, size);
	}

	/**
	 * Stamp.
	 */
	public void stamp(float width, float height) {
		applet.pushStyle();
		applet.pushMatrix();

		applet.stroke(penColor);
		applet.strokeWeight(penSize);
		applet.translate(xcor, ycor);
		applet.rotate(0.5f * PI + radians(heading));
		switch (this.oogwayShape) {
		case OSVG:
			if (this.oogwayShapeSVG != null) {
				applet.pushStyle();
				applet.noFill();
				applet.shape(this.oogwayShapeSVG, -width / 2, -height / 2,
						width, height);
				applet.popStyle();
				break;
			}
		default:
			applet.beginShape();
			applet.vertex(0, 0);
			applet.vertex(width / 2, height / 2);
			applet.vertex(0, -height / 2);
			applet.vertex(-width / 2, height / 2);
			applet.endShape(CLOSE);
			break;
		}

		applet.popMatrix();
		applet.popStyle();
	}

	/**
	 * Convert the Oogway to a String representation.
	 * 
	 * @return "Oogway at 100,100"
	 */
	@Override
	public String toString() {
		return "Oogway at " + xcor + "," + ycor + " heading " + heading;
	}

	/**
	 * Towards.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the float
	 */
	public float towards(float x, float y) {

		float rotRad = atan2(y - ycor, x - xcor);
		return degrees(rotRad);
	}

	/**
	 * Up.
	 */
	public void up() {
		penUp();
	}

	/**
	 * Xcor.
	 * 
	 * @return the float
	 */
	public float xcor() {
		return xcor;
	}

	/**
	 * Ycor.
	 * 
	 * @return the float
	 */
	public float ycor() {
		return ycor;
	}
	
}
