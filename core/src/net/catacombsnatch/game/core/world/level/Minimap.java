package net.catacombsnatch.game.core.world.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.catacombsnatch.game.core.scene.Scene;
import net.catacombsnatch.game.core.screen.Screen;
import net.catacombsnatch.game.core.world.tile.Tile;
import net.catacombsnatch.game.core.resource.Art;
import net.catacombsnatch.game.core.screen.Renderable;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Minimap implements Renderable {
	protected Level level;
	protected View view;
	protected Sprite sprite;

    protected static TextureRegion pixel;
	
	public Minimap(Level level, View view) {
		this.level = level;
		this.view = view;
		
		sprite = new Sprite(Art.skin.getAtlas().findRegion("minimap-frame"));

        if (pixel == null) {
            pixel = Art.skin.getAtlas().findRegion("white");
        }
	}
	
	Rectangle vp = new Rectangle();
	
	@Override
	public void render(Scene scene) {
		sprite.draw(scene.getSpriteBatch());
		
		vp.set(view.viewport);
		vp.x += view.offset.x;
		vp.y += view.offset.y;
		
		vp.x -= Screen.getWidth()/2;
		vp.y += Screen.getHeight()/2;

		for (Tile tile : level.getTiles()) {
			if(tile == null) continue;

            float px = sprite.getX() + 6;
            px += ((int)(tile.getPosition().x - (vp.x / Tile.WIDTH))) * 2f;
            float py = sprite.getY() + 5;
            py += ((int)(tile.getPosition().y - (vp.height / Tile.HEIGHT) - (vp.y / Tile.HEIGHT))) * 2f;

            if (px >= sprite.getX() + 6 && py >= sprite.getY() + 5 &&
                    px < sprite.getX() + 86 && py < sprite.getY() + 85) {
                int color = tile.getMinimapColor();
                scene.getSpriteBatch().setColor(
                        ((color & 0xff000000) >>> 24) / 255f,
                        ((color & 0x00ff0000) >>> 16) / 255f,
                        ((color & 0x0000ff00) >>> 8) / 255f,
                        1f);
                scene.getSpriteBatch().draw(pixel, px, py, 2f, 2f);
            }
		}
		
		// TODO add entity icons

        scene.getSpriteBatch().setColor(1f, 1f, 1f, 1f);
	}
	
	public void update(boolean resize) {
		if(resize) {
			sprite.setPosition(Screen.getWidth() - sprite.getWidth() - 2, Screen.getHeight() - sprite.getHeight());
		}
	}
	
}
