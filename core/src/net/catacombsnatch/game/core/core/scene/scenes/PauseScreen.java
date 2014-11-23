package net.catacombsnatch.game.core.core.scene.scenes;

import net.catacombsnatch.game.core.core.Game;
import net.catacombsnatch.game.core.core.scene.MenuScene;
import net.catacombsnatch.game.core.core.screen.Screen;
import net.catacombsnatch.game.core.core.resource.Language;
import net.catacombsnatch.game.core.core.scene.ReusableAction;
import net.catacombsnatch.game.core.core.scene.SceneManager;

import com.badlogic.gdx.graphics.Color;

public class PauseScreen extends MenuScene {
	
	public PauseScreen() {
		super(Screen.createBlank(0f, 0f, 0f, 0.45f));
		
		addTextButton(Language.get("scene.pause.title"), 0, 0).addAction(new ReusableAction() {
			@Override
			public boolean use(float delta) {
				SceneManager.switchTo(TitleScreen.class, true);
				return true;
			}
		});
		
		addTextButton(Language.get("scene.pause.options"), 0, 0).addAction(new ReusableAction() {
			@Override
			public boolean use(float delta) {
				SceneManager.switchTo(OptionsScene.class).init(Game.options);
				return true;
			}
		});
		
		addTextButton(Language.get("scene.pause.resume"), 0, 0).addAction(new ReusableAction() {
			@Override
			public boolean use(float delta) {
				SceneManager.exit();
				return true;
			}
		});
		
		init();
	}

	@Override
	public void tick(float delta) {
		super.tick(delta);
		
		drawCharacter();
	}
	
}
