package com.graph.herdImmunity;

import java.awt.EventQueue;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import com.graph.herdImmunity.Graph.Edge;
import com.graph.herdImmunity.Graph.Vertex;

import org.graphstream.graph.Graph;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Font;

public class Main {

	private JFrame frame;
	JPanel scrollPane;
	ViewPanel scrollPan;
	boolean hasRun = false;
	Deque<com.graph.herdImmunity.Graph<Person>> days = new Deque<>();
	// ArrayList<com.graph.herdImmunity.Graph<Person>> days = new ArrayList<>();
	GraphMod grap = null;
	JTextPane results = null;
	Graph graph = null;
	Viewer viewer = null;
	JLabel lblDay = null;
	JTextPane txtNode = null;
	StringBuilder result = new StringBuilder();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 400);

		frame.setTitle("Herd Immunity");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 307, 160, 244, 216, 0 };
		gridBagLayout.rowHeights = new int[] { 21, 21, 21, 21, 130, 126, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		lblDay = new JLabel("");
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 36));
		GridBagConstraints gbc_lblDay = new GridBagConstraints();
		gbc_lblDay.gridwidth = 2;
		gbc_lblDay.gridheight = 2;
		gbc_lblDay.anchor = GridBagConstraints.WEST;
		gbc_lblDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblDay.gridx = 0;
		gbc_lblDay.gridy = 0;
		frame.getContentPane().add(lblDay, gbc_lblDay);

		JLabel lblPopulation = new JLabel("Population:");
		GridBagConstraints gbc_lblPopulation = new GridBagConstraints();
		gbc_lblPopulation.fill = GridBagConstraints.BOTH;
		gbc_lblPopulation.insets = new Insets(0, 0, 5, 5);
		gbc_lblPopulation.gridx = 2;
		gbc_lblPopulation.gridy = 0;
		frame.getContentPane().add(lblPopulation, gbc_lblPopulation);

		JComboBox boxPopulation = new JComboBox();
		boxPopulation.addItem("15");
		boxPopulation.addItem("30");
		boxPopulation.addItem("100");
		GridBagConstraints gbc_boxPopulation = new GridBagConstraints();
		gbc_boxPopulation.fill = GridBagConstraints.BOTH;
		gbc_boxPopulation.insets = new Insets(0, 0, 5, 0);
		gbc_boxPopulation.gridx = 3;
		gbc_boxPopulation.gridy = 0;
		frame.getContentPane().add(boxPopulation, gbc_boxPopulation);

		JLabel lblPercentOfPopulation = new JLabel("Percent of population vaccinated:");
		GridBagConstraints gbc_lblPercentOfPopulation = new GridBagConstraints();
		gbc_lblPercentOfPopulation.fill = GridBagConstraints.BOTH;
		gbc_lblPercentOfPopulation.insets = new Insets(0, 0, 5, 5);
		gbc_lblPercentOfPopulation.gridx = 2;
		gbc_lblPercentOfPopulation.gridy = 1;
		frame.getContentPane().add(lblPercentOfPopulation, gbc_lblPercentOfPopulation);

		JComboBox boxVacc = new JComboBox();
		boxVacc.addItem("0");
		boxVacc.addItem("30");
		boxVacc.addItem("50");
		boxVacc.addItem("90");
		boxVacc.addItem("99");
		GridBagConstraints gbc_boxVacc = new GridBagConstraints();
		gbc_boxVacc.fill = GridBagConstraints.BOTH;
		gbc_boxVacc.insets = new Insets(0, 0, 5, 0);
		gbc_boxVacc.gridx = 3;
		gbc_boxVacc.gridy = 1;
		frame.getContentPane().add(boxVacc, gbc_boxVacc);
		
		JButton btnResults = new JButton("Results");
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,results.toString());
			}
		});
		GridBagConstraints gbc_btnResults = new GridBagConstraints();
		gbc_btnResults.anchor = GridBagConstraints.WEST;
		gbc_btnResults.insets = new Insets(0, 0, 5, 5);
		gbc_btnResults.gridx = 1;
		gbc_btnResults.gridy = 2;
		frame.getContentPane().add(btnResults, gbc_btnResults);

		JLabel lblDisease = new JLabel("Disease:");
		GridBagConstraints gbc_lblDisease = new GridBagConstraints();
		gbc_lblDisease.fill = GridBagConstraints.BOTH;
		gbc_lblDisease.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisease.gridx = 2;
		gbc_lblDisease.gridy = 2;
		frame.getContentPane().add(lblDisease, gbc_lblDisease);

		JComboBox boxDisease = new JComboBox();
		boxDisease.addItem("Measles");
		boxDisease.addItem("Influenza");
		boxDisease.addItem("Bubonic Plague");
		GridBagConstraints gbc_boxDisease = new GridBagConstraints();
		gbc_boxDisease.fill = GridBagConstraints.BOTH;
		gbc_boxDisease.insets = new Insets(0, 0, 5, 0);
		gbc_boxDisease.gridx = 3;
		gbc_boxDisease.gridy = 2;
		frame.getContentPane().add(boxDisease, gbc_boxDisease);

		JButton btnGenerateAndTest = new JButton("Generate");
		btnGenerateAndTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// first generates day 1 and displays it
				int population = Integer.parseInt((String) boxPopulation.getSelectedItem());
				int perVacc = Integer.parseInt((String) boxVacc.getSelectedItem());
				String disease = (String) boxDisease.getSelectedItem();
				Disease d = null;

				if (disease.equalsIgnoreCase("Measles")) {
					d = new Disease("Measles", 12, 95, 3);
					grap = new GraphMod(perVacc, d);
				} else if (disease.equalsIgnoreCase("Influenza")) {
					d = new Disease("Influenza", 1.7, 30, 2.5);
					grap = new GraphMod(perVacc, d);
				} else if (disease.equalsIgnoreCase("Bubonic Plague")) {
					d = new Disease("Bubonic Plague", 40, 99, 6);
					grap = new GraphMod(perVacc, d);
				}
				if (hasRun) {
					days = new Deque<com.graph.herdImmunity.Graph<Person>>();
				}

				days.queueHead(grap.initialise(population));
				displayDay(days.peakHead());
				days = createDays(days);

				hasRun = true;
				int totalInfected = 0;
				int currDayNew = 0;
				totalInfected = days.peakTail().getNumInf();
				result.append("Results:\n" + "Total Number of People Infected: " + totalInfected);
				result.append("\nNew Infections in Week 1: 1");
				cycleDay(days,1);
				for(int i = 1; i < days.size();i++) {
					currDayNew = days.peakHead().getNumInf()-days.peakTail().getNumInf();
					result.append("\nNew Infections in Week " + (i+1) + ": " + currDayNew);
					cycleDay(days,1);
				}
			}
		});

		JButton btnPrevDay = new JButton("Previous Day");
		btnPrevDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				days = cycleDay(days, -1);
				displayDay(days.peakHead());
			}
		});
		GridBagConstraints gbc_btnPrevDay = new GridBagConstraints();
		gbc_btnPrevDay.anchor = GridBagConstraints.EAST;
		gbc_btnPrevDay.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevDay.gridx = 0;
		gbc_btnPrevDay.gridy = 3;
		frame.getContentPane().add(btnPrevDay, gbc_btnPrevDay);

		JButton btnNextDay = new JButton("Next Day");
		btnNextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				days = cycleDay(days, 1);
				displayDay(days.peakHead());
			}
		});
		GridBagConstraints gbc_btnNextDay = new GridBagConstraints();
		gbc_btnNextDay.anchor = GridBagConstraints.WEST;
		gbc_btnNextDay.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextDay.gridx = 1;
		gbc_btnNextDay.gridy = 3;
		frame.getContentPane().add(btnNextDay, gbc_btnNextDay);
		GridBagConstraints gbc_btnGenerateAndTest = new GridBagConstraints();
		gbc_btnGenerateAndTest.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerateAndTest.fill = GridBagConstraints.BOTH;
		gbc_btnGenerateAndTest.gridx = 3;
		gbc_btnGenerateAndTest.gridy = 3;
		frame.getContentPane().add(btnGenerateAndTest, gbc_btnGenerateAndTest);

		scrollPane = new JPanel();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		txtNode = new JTextPane();
		txtNode.setEditable(false);
		JScrollPane txtNodeP = new JScrollPane(txtNode);

		GridBagConstraints gbc_txtNodeP = new GridBagConstraints();
		gbc_txtNodeP.fill = GridBagConstraints.BOTH;
		gbc_txtNodeP.gridx = 3;
		gbc_txtNodeP.gridy = 5;
		frame.getContentPane().add(txtNodeP, gbc_txtNodeP);

		results = new JTextPane();
		results.setEditable(false);
		GridBagConstraints gbc_results = new GridBagConstraints();
		gbc_results.insets = new Insets(0, 0, 5, 0);
		gbc_results.fill = GridBagConstraints.BOTH;
		gbc_results.gridx = 3;
		gbc_results.gridy = 4;
		frame.getContentPane().add(results, gbc_results);
	}

	private Deque<com.graph.herdImmunity.Graph<Person>> createDays(Deque<com.graph.herdImmunity.Graph<Person>> day) {// populates
																														// the
																														// deque
																														// with
																														// all
																														// days

		GraphMod gmod = grap;
		gmod.runNum = 2;
		com.graph.herdImmunity.Graph<Person> tempG = null;
		while (gmod.isOver(day.peakTail()) == false) {

			try {
				File file = new File("temp.dat");
				FileOutputStream fo = new FileOutputStream(file);
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(day.peakTail());
				oo.flush();
				FileInputStream fi = new FileInputStream(file);
				ObjectInputStream oi = new ObjectInputStream(fi);
				tempG = (com.graph.herdImmunity.Graph<Person>) oi.readObject();
				oo.close();
				fo.close();
				oi.close();
				fi.close();
				file.delete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			tempG = gmod.spread(tempG);
			day.queueTail(tempG);
		}
		// day.remove(day.size()-1);
		return day;
	}

	private Deque<com.graph.herdImmunity.Graph<Person>> cycleDay(Deque<com.graph.herdImmunity.Graph<Person>> day,
			int direction) {// moves the graph at the head of the queue to the back or the graph at the back
							// to the front depending on direction
		if (direction == 1) {
			com.graph.herdImmunity.Graph<Person> temp = day.popHead();
			day.queueTail(temp);
		} else if (direction == -1) {
			com.graph.herdImmunity.Graph<Person> temp = day.popTail();
			day.queueHead(temp);
		}
		return day;
	}

	private void displayDay(com.graph.herdImmunity.Graph<Person> day) { // displays the graph at the head of the queue
																		// or the current day
		lblDay.setText("WEEK " + day.getDayNum());
		txtNode.setText("");
		if (hasRun) {
			frame.getContentPane().remove(scrollPan);
		} else {
			frame.getContentPane().remove(scrollPane);
		}
		graph = new SingleGraph("Herd", false, false);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");

		results.setText("Number of people vaccinated: " + day.getNumVacc() + "\nNumber of people unvaccinated: "
				+ day.getNumUn() + "\nNumber of people infected: " + day.getNumInf()
				+ "\nPercentage of people who are unvaccinated who are infected: " + day.getPerUnInf() * 100);
		int num = 1;
		for (Vertex<Person> v : day.getVertices()) {
			graph.addNode(v.toString());
			Node n = graph.getNode(v.toString());
			n.setAttribute("x", v.getValue().getX());
			n.setAttribute("y", v.getValue().getY());

			if (v.getValue().isInfected()) {
				if (v.getValue().isRecentlyInfected()) {
					n.setAttribute("ui.style", "fill-color: orange; size: 20px;");
				} else {
					n.setAttribute("ui.style", "fill-color: red; size: 20px;");
				}
			} else if (v.getValue().isVaccinated()) {
				n.setAttribute("ui.style", "fill-color: green; size: 20px;");
			} else {
				n.setAttribute("ui.style", "fill-color: grey; size: 20px;");
			}
			n.setAttribute("ui.label", num);

			txtNode.setText(txtNode.getText() + "Node " + num + ": " + "\nAge: " + v.getValue().getAge() + "\nGender: "
					+ v.getValue().isGender() + "\n");
			num++;
		}
		for (Edge<Person> e : day.getEdges()) {
			Node n1 = graph.getNode(e.getFromVertex().toString());
			Node n2 = graph.getNode(e.getToVertex().toString());
			graph.addEdge(e.toString(), n1, n2, true);

		}
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);

		scrollPan = viewer.addDefaultView(false);

		GridBagConstraints gbc_scrollPan = new GridBagConstraints();
		gbc_scrollPan.gridheight = 2;
		gbc_scrollPan.gridwidth = 3;
		gbc_scrollPan.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPan.fill = GridBagConstraints.BOTH;
		gbc_scrollPan.gridx = 0;
		gbc_scrollPan.gridy = 4;
		frame.getContentPane().add(scrollPan, gbc_scrollPan);

		scrollPan.revalidate();
		frame.revalidate();
		frame.getContentPane().revalidate();
		frame.repaint();
		frame.setVisible(true);
	}

}
