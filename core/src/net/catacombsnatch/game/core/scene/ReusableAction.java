package net.catacombsnatch.game.core.scene;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/** This class is a wrapper to avoid Actions being removed after using {@link #act(float)}. */
public abstract class ReusableAction extends Action {
	
	protected Actor a;
    protected boolean canAct = true;
	
	@Override
	public boolean act(float delta) {
        if (!canAct) {
            canAct = true;
            return false;
        }
        canAct = false;

		if (a == null) a = getActor();
		
		a.addAction(this);
		return use(delta);
	}
	
	/** @see #act */
	public abstract boolean use(float delta);

}
