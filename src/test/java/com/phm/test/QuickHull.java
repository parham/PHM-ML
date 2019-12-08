
package com.phm.test;

/**
 *
 * @author phm
 */
// file: QuickHull.java
// date: 06/09/09

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

public class QuickHull extends Applet implements ActionListener
{
    public static final long serialVersionUID = 1L;

    // variables
    Random rnd;
    int pNum = 100;
    int xPoints[];
    int yPoints[];
    int xPoints2[];
    int yPoints2[];
    int num;
    int w, h;
    //
    Button bt;

    public void init()
    {
	Dimension size = getSize();
	w = size.width;
	h = size.height;

	rnd = new Random();

	xPoints = new int[pNum];
	yPoints = new int[pNum];
	num = 0;
	xPoints2 = new int[pNum];
	yPoints2 = new int[pNum];

	quickconvexhull();

	bt = new Button("New");
	bt.addActionListener(this);
	add(bt);
    }

    public void actionPerformed(ActionEvent ev)
    {
	if ( ev.getSource() == bt ) {
	    num = 0;
	    quickconvexhull();
	}
	repaint();
    }

    // check whether point p is right of line ab
    public int right(int a, int b, int p)
    {
	return (xPoints[a] - xPoints[b])*(yPoints[p] - yPoints[b]) - (xPoints[p] - xPoints[b])*(yPoints[a] - yPoints[b]);
    }

    // square distance point p to line ab
    public float distance(int a, int b, int p)
    {
	float x, y, u;
	u = (((float)xPoints[p] - (float)xPoints[a])*((float)xPoints[b] - (float)xPoints[a]) + ((float)yPoints[p] - (float)yPoints[a])*((float)yPoints[b] - (float)yPoints[a])) 
	    / (((float)xPoints[b] - (float)xPoints[a])*((float)xPoints[b] - (float)xPoints[a]) + ((float)yPoints[b] - (float)yPoints[a])*((float)yPoints[b] - (float)yPoints[a]));
	x = (float)xPoints[a] + u * ((float)xPoints[b] - (float)xPoints[a]);
	y = (float)yPoints[a] + u * ((float)yPoints[b] - (float)yPoints[a]);
	return ((x - (float)xPoints[p])*(x - (float)xPoints[p]) + (y - (float)yPoints[p])*(y - (float)yPoints[p]));
    }

    public int farthestpoint(int a, int b, ArrayList<Integer>al)
    {
	float maxD, dis;
	int maxP, p;
	maxD = -1;
	maxP = -1;
	for ( int i = 0; i < al.size(); i++ ) {
	    p = al.get(i);
	    if ( (p == a) || (p == b) )
		continue;
	    dis = distance(a, b, p);
	    if ( dis > maxD ) {
		maxD = dis;
		maxP = p;
	    }
	}
	return maxP;
    }

    public void quickhull(int a, int b, ArrayList<Integer>al)
    {
	//System.out.println("a:"+a+",b:"+b+" size: "+al.size());
	if ( al.size() == 0 )
	    return;

	int c, p;

	c = farthestpoint(a, b, al);

	ArrayList<Integer> al1 = new ArrayList<Integer>();
	ArrayList<Integer> al2 = new ArrayList<Integer>();

	for ( int i=0; i<al.size(); i++ ) {
	    p = al.get(i);
	    if ( (p == a) || (p == b) )
		continue;
	    if ( right(a,c,p) > 0 )
		al1.add(p);
	    else if ( right(c,b,p) > 0 )
		al2.add(p);
	}

	quickhull(a, c, al1);
	xPoints2[num] = xPoints[c];
	yPoints2[num] = yPoints[c];
	num++;
	quickhull(c, b, al2);
    }

    public void quickconvexhull()
    {
	// random
	int x, y;
	for ( int i = 0; i < pNum; i++ ) {
	    xPoints[i] = 50 + rnd.nextInt(w-100);
	    yPoints[i] = 50 + rnd.nextInt(h-100);
	}

	// find two points: right (bottom) and left (top)
	int r, l;
	r = l = 0;
	for ( int i = 1; i < pNum; i++ ) {
	    if ( ( xPoints[r] > xPoints[i] ) || ( xPoints[r] == xPoints[i] && yPoints[r] > yPoints[i] ))
		r = i;
	    if ( ( xPoints[l] < xPoints[i] ) || ( xPoints[l] == xPoints[i] && yPoints[l] < yPoints[i] ))
		l = i;
	}

	//System.out.println("l: "+l+", r: "+r);

	ArrayList<Integer> al1 = new ArrayList<Integer>();
	ArrayList<Integer> al2 = new ArrayList<Integer>();

	int upper;
	for ( int i = 0; i < pNum; i++ ) {
	    if ( (i == l) || (i == r) )
		continue;
	    upper = right(r,l,i);
	    if ( upper > 0 )
		al1.add(i);
	    else if ( upper < 0 )
		al2.add(i);
	}

	xPoints2[num] = xPoints[r];
	yPoints2[num] = yPoints[r];
	num++;
	quickhull(r, l, al1);
	xPoints2[num] = xPoints[l];
	yPoints2[num] = yPoints[l];
	num++;
	quickhull(l, r, al2);
    }

    public void paint(Graphics g)
    {
	// background
	g.setColor(Color.lightGray);
	g.fillRect(0,0,w-1,h-1);
	
	// set of points
	g.setColor(Color.blue);
	for ( int i = 0; i < pNum; i++ ) {
	    g.fillOval(xPoints[i]-2,yPoints[i]-2, 4,4);
	}
	
	// lines
	g.setColor(Color.black);
	g.drawPolygon(xPoints2, yPoints2, num);

	// border points
	g.setColor(Color.red);
	for ( int i = 0; i < num; i++ ) {
	    g.drawOval(xPoints2[i]-5,yPoints2[i]-5, 10,10);
	}
    }
}