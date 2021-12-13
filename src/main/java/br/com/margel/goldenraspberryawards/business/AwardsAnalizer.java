package br.com.margel.goldenraspberryawards.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.margel.goldenraspberryawards.database.Db;
import br.com.margel.goldenraspberryawards.exceptions.AnaliserException;
import br.com.margel.goldenraspberryawards.model.Interval;
import br.com.margel.goldenraspberryawards.model.MinMax;

public class AwardsAnalizer {
	
	private int minInterval, maxInterval;

	public synchronized MinMax calcMinMax() {
		minInterval = 0; 
		maxInterval = 0;
		try(
				Connection conn = Db.getInstance().newConnection();
				PreparedStatement ppst = conn.prepareStatement("SELECT PRODUCERS, YEAR FROM MOVIES WHERE WINNER = TRUE");
				ResultSet rs = ppst.executeQuery()
				){
			Map<String, Interval> producerMap = new HashMap<>();
			while(rs.next()) {
				analiseRow(rs, producerMap);
			}
			return findMinMax(producerMap.values());
		} catch (SQLException e) {
			throw new AnaliserException("Database connection error", e);
		}
	}

	private MinMax findMinMax(Collection<Interval> intervals) {
		MinMax prodAwards = new MinMax();
		for (Interval interval : intervals) {
			if(interval.getInterval() == minInterval) {
				prodAwards.addMin(interval);
			}
			if(interval.getInterval() == maxInterval) {
				prodAwards.addMax(interval);
			}
		}
		return prodAwards;
	}

	private void analiseRow(ResultSet rs, Map<String, Interval> map) throws SQLException {
		for(String producer : getProducers(rs.getString("PRODUCERS"))) {
			Interval prodInterval = map.computeIfAbsent(producer, key->new Interval(key));
			int year = rs.getInt("YEAR");
			if(prodInterval.getPreviousWin()>year) {
				prodInterval.updatePreviousWin(year);
			}
			if(prodInterval.getFollowingWin()<year) {
				prodInterval.updateFollowingWin(year);
			}
			updateMinMaxInterval(prodInterval);
		}
	}

	private void updateMinMaxInterval(Interval prodInterval) {
		if(prodInterval.getInterval()==0) {
			return;
		}
		if(prodInterval.getInterval() > maxInterval) {
			maxInterval = prodInterval.getInterval();
			if(minInterval==0) {
				minInterval = maxInterval;
			}
		}
		if(prodInterval.getInterval() < minInterval) {
			minInterval = prodInterval.getInterval();
		}
	}
	
	private String[] getProducers(String producers) {
		return producers.replace(" and ", ", ").split(", ");
	}
}