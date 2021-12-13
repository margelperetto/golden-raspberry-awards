package br.com.margel.goldenraspberryawards.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.margel.goldenraspberryawards.database.Db;
import br.com.margel.goldenraspberryawards.exceptions.AnaliserException;
import br.com.margel.goldenraspberryawards.model.Interval;
import br.com.margel.goldenraspberryawards.model.MinMax;

public class AwardsAnalizer {
	
	private int minInterval, maxInterval;
	private List<Interval> intervals = new ArrayList<>();

	public synchronized MinMax calcMinMax() {
		minInterval = 0; 
		maxInterval = 0;
		intervals.clear();
		try(
				Connection conn = Db.getInstance().newConnection();
				PreparedStatement ppst = conn.prepareStatement("SELECT PRODUCERS, YEAR FROM MOVIES WHERE WINNER = TRUE ORDER BY YEAR");
				ResultSet rs = ppst.executeQuery()
				){
			Map<String, Interval> producerMap = new HashMap<>();
			while(rs.next()) {
				analiseRow(rs, producerMap);
			}
			return findMinMax();
		} catch (SQLException e) {
			throw new AnaliserException("Database connection error", e);
		}
	}

	private MinMax findMinMax() {
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
			int year = rs.getInt("YEAR");
			if(map.containsKey(producer)) {
				Interval interval = map.get(producer);
				interval.setFollowingWin(year);
				updateMinMaxInterval(interval);
				intervals.add(interval);
			}
			map.put(producer, new Interval(producer, year));
		}
	}

	private void updateMinMaxInterval(Interval interval) {
		if(interval.getInterval() > maxInterval) {
			maxInterval = interval.getInterval();
			if(minInterval==0) {
				minInterval = maxInterval;
			}
		}
		if(interval.getInterval() < minInterval) {
			minInterval = interval.getInterval();
		}
	}
	
	private String[] getProducers(String producers) {
		return producers.replace(" and ", ", ").split(", ");
	}
}