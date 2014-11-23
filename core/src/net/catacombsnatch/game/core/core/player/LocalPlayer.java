package net.catacombsnatch.game.core.core.player;

import net.catacombsnatch.game.core.core.world.level.Level;

public class LocalPlayer implements Player {
	protected LevelPlayer lvlPlayer;
	
	@Override
	public LevelPlayer getLevelPlayer() {
		return lvlPlayer;
	}

	@Override
	public void prepareLevelPlayer(Level level) {
		lvlPlayer = new LevelPlayer(level);
	}

}
