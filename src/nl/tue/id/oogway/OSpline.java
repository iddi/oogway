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
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * OSpline class. 
 * 
 * @author Jun Hu
 * @author Loe Feijs
 * 
 */
public class OSpline implements Cloneable{
	
	/** The applet. */
	private PApplet applet;
	
	/** The vertices. */
	private Vector<float[]> vertices = new Vector<float[]>();
	
	/**
	 * Instantiates a new spline.
	 *
	 * @param applet the applet
	 */
	public OSpline(PApplet applet){
		this.applet = applet;
	}
	
	/**
	 * Clear.
	 */
	public void clear(){
		vertices.clear();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Spline [applet=" + applet + ", vertices=" + vertices + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public OSpline clone(){
		OSpline s = new OSpline(applet);
        s.copy(this);
		return s;
	}
	
	/**
	 * Copy.
	 *
	 * @param s the s
	 */
	protected void copy(OSpline s){
		this.clear();
		for (int i = 0; i < s.vertices.size(); i++)
			curveVertex(s.vertices.get(i)[0], s.vertices.get(i)[1]);
	}
	
	/**
	 * Curve vertex.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void curveVertex(float x, float y){
		float v[] = {x, y};
		vertices.add(v);
	}
	
	/**
	 * Draw.
	 */
	public void draw(){
		if(vertices.size()<4) return;
		applet.beginShape();
		for (int i = 0; i < vertices.size(); i++)
			applet.curveVertex(vertices.get(i)[0], vertices.get(i)[1]);
		applet.endShape();
	}
	
}
