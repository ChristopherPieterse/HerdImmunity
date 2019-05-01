package com.graph.herdImmunity;

import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable {

	float x, y;
	boolean vaccinated;
	boolean infected;
	int age;
	boolean gender;
	double infectability;

	public double getInfectability() {
		return infectability;
	}

	public void setInfectability(double infectability) {
		this.infectability = infectability;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	int level = 1000;

	boolean recentlyInfected = false;

	public boolean isRecentlyInfected() {
		return recentlyInfected;
	}

	public void setRecentlyInfected(boolean recentlyInfected) {
		this.recentlyInfected = recentlyInfected;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isVaccinated() {
		return vaccinated;
	}

	public void setVaccinated(boolean vaccinated) {
		this.vaccinated = vaccinated;
	}

	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	public Person(float x, float y, boolean vacc, int age, boolean gender, double infectability) {
		this.x = x;
		this.y = y;
		vaccinated = vacc;
		this.gender = gender;
		this.age = age;
		this.infectability = infectability;
	}

	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toString() {
		return x + " " + y + "<" + vaccinated + "," + infected + "," + level + ">";
	}
}
