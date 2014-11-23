package net.catacombsnatch.game.android;

import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.*;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.utils.GdxRuntimeException;
import net.catacombsnatch.game.core.core.Game;
import net.catacombsnatch.game.core.core.PlatformDependent;

import android.os.Bundle;

import java.lang.reflect.Method;

public class CatacombSnatchGameActivity extends AndroidApplication {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;

        initialize(new Game(new PlatformDependent() {
            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }

            @Override
            public Object[] createPlatformObjects() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void create() {
                // TODO Auto-generated method stub

            }
	    }), config);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        if (this.getVersion() < MINIMUM_SDK) {
            throw new GdxRuntimeException("LibGDX requires Android API Level " + MINIMUM_SDK + " or later.");
        }

        //Reordered to make using Gdx.app, Gdx.graphics etc possible (due to AndroidBackend.getGdxInput)

        graphics = new AndroidGraphics(this, config,
                config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
        audio = new AndroidAudio(this, config);
        this.getFilesDir(); // workaround for Android bug #10515463
        files = new AndroidFiles(this.getAssets(), this.getFilesDir().getAbsolutePath());
        net = new AndroidNet(this);
        this.listener = listener;
        this.handler = new Handler();
        this.useImmersiveMode = config.useImmersiveMode;
        this.hideStatusBar = config.hideStatusBar;

        // Add a specialized audio lifecycle listener
        addLifecycleListener(new LifecycleListener() {

            @Override
            public void resume () {
                // No need to resume audio here
            }

            @Override
            public void pause () {
                //audio.pause(); //screw it, world shall explode as I can't change it...
            }

            @Override
            public void dispose () {
                audio.dispose();
            }
        });

        Gdx.app = this;
        Gdx.audio = this.getAudio();
        Gdx.files = this.getFiles();
        Gdx.graphics = this.getGraphics();
        Gdx.net = this.getNet();

        input = (Ouya.isRunningOnOuya()?
                new OuyaInput(this, this, graphics.getView(), config):
                AndroidInputFactory.newAndroidInput(this, this, graphics.getView(), config)
        );

        Gdx.input = this.getInput();

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception ex) {
            log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(graphics.getView(), createLayoutParams());

        createWakeLock(config.useWakelock);
        hideStatusBar(this.hideStatusBar);
        useImmersiveMode(this.useImmersiveMode);
        if (this.useImmersiveMode && getVersion() >= 19) {
            try {
                Class<?> vlistener = Class.forName("com.badlogic.gdx.backends.android.AndroidVisibilityListener");
                Object o = vlistener.newInstance();
                Method method = vlistener.getDeclaredMethod("createListener", AndroidApplicationBase.class);
                method.invoke(o, this);
            } catch (Exception e) {
                log("AndroidApplication", "Failed to create AndroidVisibilityListener", e);
            }
        }
    }


}
