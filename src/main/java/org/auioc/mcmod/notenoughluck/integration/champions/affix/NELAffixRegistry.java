package org.auioc.mcmod.notenoughluck.integration.champions.affix;

import static org.auioc.mcmod.notenoughluck.NotEnoughLuck.LOGGER;
import org.auioc.mcmod.notenoughluck.integration.NELIntegration;
import top.theillusivec4.champions.Champions;

public class NELAffixRegistry {

    public static void register() {
        LOGGER.info(NELIntegration.MARKER, "Mod Champions is loaded, register NEL affixes");

        Champions.API.registerAffixes(

        );
    }

}
