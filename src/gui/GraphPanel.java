package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import date.InvalidDateException;
import time.InvalidTimeException;

public class GraphPanel extends JPanel {
	private int padding = 25;
	private int labelPadding = 25;
	private JLabel lblTitle;
	private JLabel lblAverage;
	private Color color;
	private Color gridColor = new Color(200, 200, 200, 200);
	private Color pointColor = new Color(102, 102, 102, 255);
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private List<Double> scores;
	private double average;

	public GraphPanel(List<Double> scores, Color color, String title)
			throws IOException, InvalidTimeException, InvalidDateException {
		this.color = color;

		if (title != "Distance") {
			for (int i = 0; i < scores.size(); i++) {
				average += scores.get(i);
			}
			average /= scores.size();
			average = Math.floor(average * 100) / 100;
			lblAverage = new JLabel();
			lblAverage.setText("Average " + title + ": " + Double.toString(average));
			this.add(lblAverage);
		} else {
			this.lblTitle = new JLabel(title);
			this.add(lblTitle);
		}
		this.scores = scores;

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < scores.size(); i++) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (scores.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((getMinScore()
						+ (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i < scores.size(); i++) {
			if (scores.size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
				int x1 = x0;
				if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding, x1, padding);
				}
			}
		}

		// black line on x and y axis
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
				getHeight() - padding - labelPadding);
		// Lines are drawn here
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(GRAPH_STROKE);
		if (scores.size() > 40) {
			for (int i = 0; i < graphPoints.size() - 1; i++) {
				g2.setColor(color);
				int x1 = graphPoints.get(i).x;
				int y1 = graphPoints.get(i).y;
				int x2 = graphPoints.get(i + 1).x;
				int y2 = graphPoints.get(i + 1).y;
				g2.drawLine(x1, y1, x2, y2);
			}
		} else {
			for (int i = 0; i < graphPoints.size(); i++) {
				g2.setColor(color);
				int x1 = graphPoints.get(i).x;
				int y1 = graphPoints.get(i).y;
				g2.drawLine(x1, y1, x1, getHeight()-padding-labelPadding);
			}
			g2.setStroke(oldStroke);
			g2.setColor(pointColor);
			for (int i = 0; i < graphPoints.size(); i++) {
				int x = graphPoints.get(i).x - pointWidth / 2;
				int y = graphPoints.get(i).y - pointWidth / 2;
				int ovalW = pointWidth;
				int ovalH = pointWidth;
				g2.fillOval(x, y, ovalW, ovalH);
			}
		}
	}

	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		for (Double score : scores) {
			minScore = Math.min(minScore, score);
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		for (Double score : scores) {
			maxScore = Math.max(maxScore, score);
		}
		return maxScore;
	}

	public void setScores(List<Double> scores) {
		this.scores = scores;
		invalidate();
		this.repaint();
	}

	public List<Double> getScores() {
		return scores;
	}
}
