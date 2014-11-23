package net.catacombsnatch.game.core.core.statistic;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
	protected static Array<Statistic> statistics;
	static {
		statistics = new Array<Statistic>();
	}
	
	public Statistic getStatistic(Statistic statistic) {
		for(Statistic s : statistics) {
			if(s.equals(statistic)) return s;
		}
		
		return null;
	}
	
}
