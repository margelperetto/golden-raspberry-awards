package br.com.margel.goldenraspberryawards.model;

public class Interval {
	private final String producer;
	private int interval;
	private int previousWin;
	private int followingWin;

	public Interval(String producer, int previousWin) {
		this.producer = producer;
		this.previousWin = previousWin;
	}
	public String getProducer() {
		return producer;
	}
	public int getInterval() {
		return interval;
	}
	public int getPreviousWin() {
		return previousWin;
	}
	public int getFollowingWin() {
		return followingWin;
	}
	
	public void setPreviousWin(int previousWin) {
		this.previousWin = previousWin;
	}
	public void setFollowingWin(int followingWin) {
		this.followingWin = followingWin;
		interval = followingWin - previousWin;
	}
	
}
