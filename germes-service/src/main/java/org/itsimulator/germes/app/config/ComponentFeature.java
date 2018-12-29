package org.itsimulator.germes.app.config;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class ComponentFeature implements Feature {
    @Override
    public boolean configure(final FeatureContext context){
        context.register(new ComponentFeature());
        return true;
    }
}
