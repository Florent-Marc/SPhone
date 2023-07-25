
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.items;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenPhone;
import fr.sandji.sphone.mod.common.register.ItemsRegister;
import fr.sandji.sphone.mod.server.bdd.MethodesBDDImpl;
import fr.sandji.sphone.mod.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemPhone extends Item {

    public static final String SIM_KEY_TAG = "simcard";

    public ItemPhone(String name, CreativeTabs creativeTabs, int stackcount) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        ItemsRegister.INSTANCE.getItems().add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);

            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("simcard")) {
                SPhone.network.sendTo(new PacketOpenPhone("home", ""), (EntityPlayerMP) player);
            } else {
                int sim = Utils.getRandomNumber(1000, 9999);
                int num = Utils.getRandomNumber(10000, 99999);
                boolean isExist = false;
                if (!MethodesBDDImpl.addSim(sim, num)) {
                    for (int j = 0; j < 50; j++) {
                        sim = Utils.getRandomNumber(1000, 9999);
                        num = Utils.getRandomNumber(10000, 99999);
                        if (MethodesBDDImpl.addSim(sim, num)) {
                            isExist = true;
                            setSimCard(player, stack, sim);
                            break;
                        }
                    }
                    if (!isExist) {
                        Utils.sendErrorChat(player, "Il semblerai que la limite de sim soit atteinte, veuillez contacter un administrateur.", false);
                    }
                } else {
                    setSimCard(player, stack, sim);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (getSimCard(stack) == 0) {
                tooltip.add("-> Aucune Carte Sim Injecté");
            } else {
                tooltip.add("-> Carte Sim : " + getSimCard(stack));
            }
            tooltip.add("-> Batterie : 100%");
            tooltip.add("");
            tooltip.add("Appuyer sur SHIFT pour plus d'informations.");
        }
        else {
            tooltip.add("");
            tooltip.add("-> Version du Téléphone : " + SPhone.VERSION);
            tooltip.add("-> Mode Développeur : " + SPhone.DEV_MOD);
            tooltip.add("");
        }
        super.addInformation(stack, world, tooltip, flagIn);
    }

    public static boolean isPhone(ItemStack stack){
        return stack.getItem() == ItemsRegister.ITEM_PHONE;
    }

    public static NBTTagCompound getTagCompound(ItemStack stack){
        NBTTagCompound nbt;
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        nbt = stack.getTagCompound();
        return nbt;
    }

    public static void setSimCard(EntityPlayer player, ItemStack stack, int sim){
        if(!isPhone(stack)) return;
        NBTTagCompound nbt = getTagCompound(stack);
        nbt.setInteger(SIM_KEY_TAG, sim);
        Utils.sendActionChat(player, "Vous avez injecté la carte sim : " + sim, false);
    }

    public static int getSimCard(ItemStack stack){
        if(isPhone(stack)) {
            NBTTagCompound nbt = getTagCompound(stack);
            if(nbt.hasKey(SIM_KEY_TAG)) return nbt.getInteger(SIM_KEY_TAG);
        }
        return 0;
    }

}
