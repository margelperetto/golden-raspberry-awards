package br.com.margel.goldenraspberryawards.model;

public class Interval {
	private final String producer;
	private int interval;
	private int previousWin;
	private int followingWin;

	public Interval(String producer) {
		this.producer = producer;
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
	
	public void updatePreviousWin(int previousWin) {
		this.previousWin = previousWin;
		if(followingWin==0) {
			followingWin = previousWin;
		}
		updateInterval();
	}
	public void updateFollowingWin(int followingWin) {
		this.followingWin = followingWin;
		if(previousWin==0) {
			previousWin = followingWin;
		}
		updateInterval();
	}
	
	private void updateInterval() {
		if(followingWin==0 && previousWin>0) {
			followingWin = previousWin;
		}
		interval = followingWin - previousWin;
	}
}
