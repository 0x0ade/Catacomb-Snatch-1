package net.catacombsnatch.game.core.core.player;

import net.catacombsnatch.game.core.core.world.level.Level;

public interface Player {
	
	public LevelPlayer getLevelPlayer();
	
	public void prepareLevelPlayer(Level level);
	
}
