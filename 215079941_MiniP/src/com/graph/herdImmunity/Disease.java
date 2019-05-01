package com.graph.herdImmunity;

public class Disease {

	String name;
	double R0;
	int HIT;
	double range;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getR0() {
		return R0;
	}

	public void setR0(double r0) {
		R0 = r0;
	}

	public int getHIT() {
		return HIT;
	}

	public void setHIT(int hIT) {
		HIT = hIT;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public Disease(String n, double r, int h, double ra) {
		name = n;
		R0 = r;
		HIT = h;
		range = ra;
	}
}
