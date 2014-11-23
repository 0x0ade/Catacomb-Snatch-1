package net.catacombsnatch.game.core.core.statistic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import net.catacombsnatch.game.core.core.player.Player;

public class Statistic {
	
	protected final ObjectIntMap<Player> values;
	protected final String name;
	
	public Statistic(String name) {
		this.name = name;
		
		values = new ObjectIntMap<Player>();
	}	
	
	public int get(Player player) {
		return values.get(player, -1);
	}
	
	public void increment(Player player) {
		values.put(player, get(player) + 1);
	}
	
	public void clear(Player player) {
		values.remove(player, -1);
	}
}
