package net.catacombsnatch.game.core.core.screen;

import net.catacombsnatch.game.core.core.scene.Scene;

public interface Renderable {
	
	/**
	 * Renders content to the scene.
	 * 
	 * @param screen The {@link net.catacombsnatch.game.core.core.scene.Scene} to draw on.
	 */
	public abstract void render( Scene scene );
	
}
