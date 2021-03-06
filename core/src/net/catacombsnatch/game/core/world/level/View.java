package net.catacombsnatch.game.core.world.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.catacombsnatch.game.core.entity.systems.RenderSystem;
import net.catacombsnatch.game.core.scene.Scene;
import net.catacombsnatch.game.core.screen.Updateable;
import net.catacombsnatch.game.core.world.tile.Tile;
import net.catacombsnatch.game.core.resource.Art;
import net.catacombsnatch.game.core.screen.Renderable;

public class View implements Renderable, Updateable {
	protected Level level;
	protected Rectangle viewport;
	protected int rendered;
	protected Vector2 offset, target;
	
	protected RenderSystem renderer;
	
	protected Sprite panel;
	
	protected Minimap minimap;
	
	public View(Level lvl) {
		level = lvl;
		
		offset = new Vector2();
		panel = new Sprite(Art.skin.getAtlas().findRegion("player-panel"));
		
		minimap = new Minimap(level, this);
		
		renderer = level.setSystem(new RenderSystem(), true);
	}
	
	@Override
	public void render( Scene scene ) {
		if(viewport == null) return;
		
		if(target != null) {
			// "Camera" movement
			offset.lerp(target, Gdx.graphics.getDeltaTime());
			
			// Draw tiles
			rendered = 0; // Reset counter
			
			for(float y = (viewport.y - viewport.height - offset.y) / Tile.HEIGHT - 2; y <= -(viewport.y + offset.y) / Tile.HEIGHT + 4; y++) {
				for(float x = (viewport.x + offset.x) / Tile.WIDTH - 2; x < (viewport.x + viewport.width + offset.x) / Tile.WIDTH + 2; x++) {
					Tile tile = level.getTile((int) x, (int) y);
					
					if(tile != null && tile.shouldRender(this)) {
						tile.render(scene.getSpriteBatch(), this);
						rendered++;
					}
				}
			}
			
			// Render entities
			renderer.setGraphics(scene.getSpriteBatch());
			renderer.process();
		}
		
		// Draw overlays
		panel.draw(scene.getSpriteBatch());
		
		minimap.render(scene);
	}
	
	@Override
	public void update(boolean resize) {
		if(viewport == null || !resize) return;

        viewport.x -= viewport.width / 2f;
        viewport.y -= viewport.height / 2f;
		
		panel.setPosition((viewport.getWidth() - panel.getWidth()) / 2, viewport.getHeight() - panel.getHeight());
		
		minimap.update(resize);
	}

	/**
	 * Moves the view offset.
	 * 
	 * @param target The new target vector
	 */
	public void setTarget(Vector2 target) {
		this.target = target;
	}
	
	public void setViewport(Rectangle view) {
		viewport = view;
	}

    public void setViewport(float x, float y, float w, float h) {
        if (viewport == null) {
            viewport = new Rectangle(x, y, w, h);
        } else {
            viewport.set(x, y, w, h);
        }
    }
	
	/** @return The number of tiles rendered during the last {@link #render(Scene)} call. */
	public int getLastRenderedTileCount() {
		return rendered;
	}
	
	/** @return The current view offset. */
	public Vector2 getOffset() {
		return offset;
	}
	
	/** @return The current view port. */
	public Rectangle getViewport() {
		return viewport;
	}

    protected Vector2 viewportOffset = new Vector2();
	/** @return The current view port + offset. */
	public Vector2 getViewportOffset() {
		return viewportOffset.set(offset).add(viewport.x, viewport.y);
	}

}
