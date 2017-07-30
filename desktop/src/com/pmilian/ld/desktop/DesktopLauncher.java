package com.pmilian.ld.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pmilian.ld.LD39;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 900;
        config.height = 600;
//        config.width = 1200;
//        config.height = 900;
        new LwjglApplication(new LD39(), config);
    }
}
