package net.psychic;

import net.fabricmc.api.ModInitializer;

public class PsychicPowers implements ModInitializer {
    public static final String MOD_ID = "psychic_powers";

    @Override
    public void onInitialize() {
        ModItems.init();
    }
}
