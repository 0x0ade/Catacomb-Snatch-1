package net.catacombsnatch.game.java;

import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jglfw.JglfwApplication;
import com.badlogic.gdx.backends.jglfw.JglfwApplicationConfiguration;
import com.badlogic.gdx.backends.jglfw.ShadowJglfwApplication;
import com.badlogic.gdx.graphics.Pixmap;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import net.catacombsnatch.game.core.core.Game;
import net.catacombsnatch.game.core.core.PlatformDependent;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.BufferUtils;

public class CatacombSnatchGameDesktop {
	public static final int GAME_WIDTH = 512;
	public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
	
	public static void main( String[] args ) {
		System.out.println("Starting game in DESKTOP mode!");

        Game game = new Game(
                new PlatformDependent() {
                    @Override
                    public void create() {
                        // Set game cursor
                        int size = 16, center = (size / 2);

                        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
                        pixmap.setColor(1f, 1f, 1f, 1f);
                        pixmap.drawLine(center, 0, center, center-2);
                        pixmap.drawLine(center, center+2, center, size);
                        pixmap.drawLine(0, center, center-2, center);
                        pixmap.drawLine(center+2, center, size, center);

                        Gdx.input.setCursorImage(pixmap, center, center);
                    }

                    @Override
                    public Object[] createPlatformObjects() {
                        throw new UnsupportedOperationException("Unimplemented");
                    }

                    @Override
                    public void dispose() {
                    }
                });

        if (args.length == 1 && args[0].equals("jglfw")) {
            JglfwApplicationConfiguration config = new JglfwApplicationConfiguration();
            //config.useGL30 = false;
            config.title = "Catacomb-Snatch";
            config.width = GAME_WIDTH;
            config.height = GAME_HEIGHT;

            new ShadowJglfwApplication(game, config);
        } else {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.useGL30 = false;
            config.title = "Catacomb-Snatch";
            config.width = GAME_WIDTH;
            config.height = GAME_HEIGHT;

            new LwjglApplication(game, config);
        }
	}
}
