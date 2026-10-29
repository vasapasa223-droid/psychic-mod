package net.psychic;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.psychic.item.*;

public class ModItems {
    public static final Item PSI_BLAST = register("psi_blast", new PsiBlastItem(new Item.Settings().maxCount(1)));
    public static final Item PYROKINESIS = register("pyrokinesis", new PyrokinesisItem(new Item.Settings().maxCount(1)));
    public static final Item LEVITATION = register("levitation", new LevitationItem(new Item.Settings().maxCount(1)));
    public static final Item TELEKINESIS = register("telekinesis", new TelekinesisItem(new Item.Settings().maxCount(1)));

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(PsychicPowers.MOD_ID, id), item);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(PSI_BLAST);
            entries.add(PYROKINESIS);
            entries.add(LEVITATION);
            entries.add(TELEKINESIS);
        });
    }
}
