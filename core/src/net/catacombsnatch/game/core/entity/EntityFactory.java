package net.catacombsnatch.game.core.entity;

import net.catacombsnatch.game.core.entity.components.Health;
import net.catacombsnatch.game.core.entity.components.Position;
import net.catacombsnatch.game.core.entity.components.Render;
import net.catacombsnatch.game.core.entity.components.Velocity;
import net.catacombsnatch.game.core.world.level.Level;
import net.catacombsnatch.game.core.entity.renderers.PlayerRenderer;
import net.catacombsnatch.game.core.entity.renderers.TileRenderer;

import com.artemis.Entity;

public final class EntityFactory {

	public static Entity createPlayerEntity(Level level) {
		Entity player = level.createEntity();
		EntityHelper.addToGroup(player, "players");
		
		player.edit().add(new Health(20));
		player.edit().add(new Position(level.getNextSpawnLocation())); // TODO: This can return null!
		player.edit().add(new Velocity());
		player.edit().add(new Render(new PlayerRenderer(player)));
		
		return player;
	}
	
	public static Entity createTileEntity(Level level, int x, int y) {
		Entity tile = level.createEntity();
		EntityHelper.addToGroup(tile, "tiles");
		
		tile.edit().add(new Position(x, y));
		tile.edit().add(new Render(new TileRenderer(tile)));
		
		return tile;
	}
	
}
