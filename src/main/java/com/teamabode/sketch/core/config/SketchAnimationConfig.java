package com.teamabode.sketch.core.config;

import com.teamabode.sketch.core.api.config.BooleanProperty;
import com.teamabode.sketch.core.api.config.Config;

public class SketchAnimationConfig extends Config {
    public static final SketchAnimationConfig INSTANCE = new SketchAnimationConfig();

    public final BooleanProperty armadillo;
    public final BooleanProperty bat;
    public final BooleanProperty breeze;
    public final BooleanProperty camel;
    public final BooleanProperty frog;
    public final BooleanProperty sniffer;
    public final BooleanProperty warden;

    public SketchAnimationConfig() {
        super("sketch.animation");

        this.armadillo = new BooleanProperty("armadillo", true);
        this.bat = new BooleanProperty("bat", true);
        this.breeze = new BooleanProperty("breeze", true);
        this.camel = new BooleanProperty("camel", true);
        this.frog = new BooleanProperty("frog", true);
        this.sniffer = new BooleanProperty("sniffer", true);
        this.warden = new BooleanProperty("warden", true);

        this.defineCategory(
                "override_vanilla_animations",
                this.armadillo,
                this.bat,
                this.camel,
                this.frog,
                this.sniffer,
                this.warden
        );
    }
}
