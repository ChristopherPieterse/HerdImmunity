package com.graph.herdImmunity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.graph.herdImmunity.Graph.Edge;
import com.graph.herdImmunity.Graph.TYPE;
import com.graph.herdImmunity.Graph.Vertex;

public class GraphMod {
	// variables
	Graph<Person> people;
	int day;
	float vaccPer;
	Disease dis;
	private Random random = new Random();
	float width;
	int runNum = 1;
	List<Integer> levels = null;

	// constructor
	public GraphMod(int vp, Disease d) {
		vaccPer = vp;
		dis = d;
	}

	// init process. Unforunately this runs in Omega(N^2) based on how unlucky you
	// get with the random number generation. It could even run infinitely but that
	// is almost zero chance
	public Graph<Person> initialise(int population) {
		// random seeding
		random.setSeed(System.currentTimeMillis());
		// Collections of the people and edges between them
		List<Vertex<Person>> graphVer = new ArrayList<Vertex<Person>>();
		List<Edge<Person>> graphEdge = new ArrayList<Edge<Person>>();
		// position and vaccinated
		float x, y;
		boolean vacc;
		// getting dimensions of the space based on population size
		switch (population) {
		case 15:
			width = population / 5;
			break;
		case 30:
			width = population / 10;
			break;
		case 100:
			width = population / 15;
			break;
		}
		// new person to be added to the graph
		Vertex<Person> newPerson;

		for (int i = 0; i < population; i++) {
			// randomly create their coordinates and their vaccinated chance
			x = getRandomNumber();
			y = getRandomNumber();
			vacc = getRandomBoolean(vaccPer / 100);
			int age = (int) (random.nextDouble() * 100);
			boolean gender = random.nextBoolean();
			double infectability = 1;
			if (vacc == false) {
				if (age > 8 || age < 65) {
					infectability = infectability - 0.1;
				}
				if (gender == true) {
					infectability = infectability - 0.1;
				} else {
					infectability = infectability - 0.05;
				}
			} else {
				infectability = 0;
			}
			infectability = infectability * (dis.getR0());
			for (Vertex<Person> p : graphVer) {// check to make sure no overlap
				while (distance(p.getValue().getX(), p.getValue().getY(), x, y) < 1.5) {
					x = getRandomNumber();
					y = getRandomNumber();
				}
			}
			newPerson = new Vertex<Person>(new Person(x, y, vacc, age, gender, infectability));

			graphVer.add(newPerson);
		}
		// create patient zero
		graphVer.get(0).getValue().setInfected(true);
		graphVer.get(0).getValue().setRecentlyInfected(true);
		graphVer.get(0).getValue().setLevel(0);
		double direction = 0;
		double directionToOriginal = 0;
		for (int i = 0; i < graphVer.size(); i++) {
			for (int j = 0; j < graphVer.size(); j++) {
				// checks each vertex against every other vertex, and if their distance is
				// within limits and they aren't equal to each other, then create an edge
				// between them
				if (distance(graphVer.get(i).getValue().getX(), graphVer.get(i).getValue().getY(),
						graphVer.get(j).getValue().getX(), graphVer.get(j).getValue().getY()) < dis.getRange()) {
					if (i != j) {
						graphEdge.add(new Edge<>(0, graphVer.get(i), graphVer.get(j)));
					}
				}
			}
		}
		double xOrg = 0;
		double yOrg = 0;
		for (int i = 0; i < graphVer.size(); i++) {
			for (int j = 0; j < graphVer.size(); j++) {
				if (i != j) {

					x = graphVer.get(j).getValue().getX() - graphVer.get(i).getValue().getX();
					y = graphVer.get(j).getValue().getY() - graphVer.get(i).getValue().getY();

					xOrg = graphVer.get(0).getValue().getX() - graphVer.get(i).getValue().getX();
					yOrg = graphVer.get(0).getValue().getY() - graphVer.get(i).getValue().getY();

					if (x > 0 && y > 0) {
						direction = Math.toDegrees(Math.atan(y / x));
					} else if (x < 0 && y > 0) {
						direction = 180 + Math.toDegrees(Math.atan(y / x));
					} else if (x < 0 && y < 0) {
						direction = 180 + Math.toDegrees(Math.atan(y / x));
					} else if (x > 0 && y < 0) {
						direction = 360 + Math.toDegrees(Math.atan(y / x));
					} else if (x == 0) {
						if (y < 0) {
							direction = 270;
						} else if (y > 0) {
							direction = 90;
						}
					}

					if (xOrg > 0 && yOrg > 0) {
						directionToOriginal = Math.toDegrees(Math.atan(yOrg / xOrg));
					} else if (xOrg < 0 && yOrg > 0) {
						directionToOriginal = 180 + Math.toDegrees(Math.atan(yOrg / xOrg));
					} else if (xOrg < 0 && yOrg < 0) {
						directionToOriginal = 180 + Math.toDegrees(Math.atan(yOrg / xOrg));
					} else if (xOrg > 0 && yOrg < 0) {
						directionToOriginal = 360 + Math.toDegrees(Math.atan(yOrg / xOrg));
					} else if (xOrg == 0) {
						if (yOrg < 0) {
							directionToOriginal = 270;
						} else if (yOrg > 0) {
							directionToOriginal = 90;
						}
					}

					if ((direction + 360) > (directionToOriginal + 270)
							&& (direction + 360) < (directionToOriginal + 450)) {
						graphEdge.remove(new Edge<>(0, graphVer.get(i), graphVer.get(j)));
					}
				}
			}
		}
		// create the graph based on the above people and edges
		people = new Graph<>(TYPE.DIRECTED, graphVer, graphEdge);
		setStats(people);
		people.setDayNum(runNum);
		runNum++;

		// creates an arraylist of the different "levels" away from the origin to be
		// used by other methods.
		levels = new ArrayList<>();

		for (int i = 0; i < graphVer.size(); i++) {
			if (levels.contains(graphVer.get(i).getValue().getLevel()) == false) {
				levels.add(graphVer.get(i).getValue().getLevel());
			}
		}
		// sort this arraylist
		Collections.sort(levels);
		return people;
	}

	private void setStats(Graph<Person> people2) {// sets the statstics of a graph based on the people in the graph
		int numVacc = 0, numUn = 0, numInf = 0;// number of vaccinated, unvaccinated and infected people
		double perUnInf = 0; // percentage of unvaccinated people infected
		for (Vertex<Person> v : people2.getVertices()) {
			if (v.getValue().isInfected()) {
				numInf++;
			} else if (v.getValue().isVaccinated()) {
				numVacc++;
			}
			if (v.getValue().isVaccinated() == false) {
				numUn++;
			}
		}
		perUnInf = (numInf * 1.0) / (numUn * 1.0);
		people2.setNumInf(numInf);
		people2.setNumUn(numUn);
		people2.setNumVacc(numVacc);
		people2.setPerUnInf(perUnInf);
	}

	// spread disease to neighbours
	public Graph<Person> spread(Graph<Person> g) {
		List<Vertex<Person>> graphVer = g.getVertices();
		// reseting the recent infections for a new week to start
		for (int i = 0; i < graphVer.size(); i++) {
			graphVer.get(i).getValue().setRecentlyInfected(false);
		}

		List<Edge<Person>> edges;
		boolean infect = false;
		for (int i = 0; i < graphVer.size(); i++) {// for every vertex in the graph
			if ((graphVer.get(i).getValue().isInfected() == true)
					&& (graphVer.get(i).getValue().isRecentlyInfected() == false)) {// if the vertex is infected and not
																					// recently infected
				edges = graphVer.get(i).getEdges();// get the edges of this vertex
				for (int j = 0; j < edges.size(); j++) {// for each edge of the particular vertex
					// if the vertex at the other side of the edge is not vaccinated and is not
					// infected, infect them
					if ((edges.get(j).getToVertex().getValue().isVaccinated() == false)
							&& (edges.get(j).getToVertex().getValue().isInfected() == false)) {
						// infecting
						infect = getRandomBoolean((float) graphVer.get(i).getValue().getInfectability());
						if (infect == true) {
							edges.get(j).getToVertex().getValue().setInfected(true);
							edges.get(j).getToVertex().getValue().setRecentlyInfected(true);
						}
					}
				}

			}
		}

		setStats(g);
		g.setDayNum(runNum);
		runNum++;
		return g;
	}

	// checks to see if the simulation is over
	public boolean isOver(Graph<Person> g) {
		int count = 0;
		boolean newInfected = false;
		for (Vertex<Person> v : g.getVertices()) {// if there are any recent infections the simulation is not over

			if (v.getValue().isRecentlyInfected() == true) {
				newInfected = true;
			}
		}
		if (newInfected == false) {
			return true;
		}
		return false;

	}

	private boolean getRandomBoolean(float p) {// weighted random boolean
		return random.nextFloat() < p;
	}

	private float getRandomNumber() {// random number within bounds
		return -width + (random.nextFloat() * (width - (-width)));
	}

	private double distance(float x1, float y1, float x, float y) {// distance formula
		return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
	}
}
