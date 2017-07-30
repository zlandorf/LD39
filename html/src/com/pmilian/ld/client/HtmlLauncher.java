package com.pmilian.ld.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.pmilian.ld.LD39;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(900, 800);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LD39();
        }
}