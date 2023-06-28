/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.proxy;

import fr.sandji.sphone.mod.common.register.ItemsRegister;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ItemsRegister.INSTANCE);
    }

    @Override
    public void init() {
        super.init();
    }

}
