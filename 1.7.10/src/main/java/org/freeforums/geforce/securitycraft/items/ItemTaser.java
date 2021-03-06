package org.freeforums.geforce.securitycraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.freeforums.geforce.securitycraft.entity.EntityTaserBullet;
import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;
import org.freeforums.geforce.securitycraft.misc.SCSounds;
import org.freeforums.geforce.securitycraft.network.packets.PacketCPlaySoundAtPos;

public class ItemTaser extends Item {
	
	public ItemTaser(){
		super();
		this.setMaxDamage(301);
	}
	
	public boolean isFull3D(){
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		if(!par2World.isRemote){
			if(!par1ItemStack.isItemDamaged() && (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(Items.redstone))){
				par2World.spawnEntityInWorld(new EntityTaserBullet(par2World, par3EntityPlayer));
				mod_SecurityCraft.network.sendToAll(new PacketCPlaySoundAtPos(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, SCSounds.TASERFIRED.path, 1.0F));
				par1ItemStack.damageItem(300, par3EntityPlayer);
			}
		}
		
		return par1ItemStack;
	}
	
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		if(!par2World.isRemote){
			if(par1ItemStack.getItemDamage() >= 1){
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
			}
		}
    }

}
