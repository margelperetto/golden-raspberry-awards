package br.com.margel.goldenraspberryawards.model;

import java.util.ArrayList;
import java.util.List;

public class MinMax {

	private List<Interval> min = new ArrayList<>();
	private List<Interval> max = new ArrayList<>();
	
	public List<Interval> getMin() {
		return min;
	}
	public List<Interval> getMax() {
		return max;
	}
	public void addMin(Interval prodInterval) {
		min.add(prodInterval);
	}
	public void addMax(Interval prodInterval) {
		max.add(prodInterval);
	}
}
