package org.freeforums.geforce.securitycraft.handlers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.freeforums.geforce.securitycraft.blocks.BlockLaserBlock;
import org.freeforums.geforce.securitycraft.blocks.BlockOwnable;
import org.freeforums.geforce.securitycraft.entity.EntitySecurityCamera;
import org.freeforums.geforce.securitycraft.interfaces.IOwnable;
import org.freeforums.geforce.securitycraft.items.ItemModule;
import org.freeforums.geforce.securitycraft.main.Utils.PlayerUtils;
import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;
import org.freeforums.geforce.securitycraft.network.packets.PacketCheckRetinalScanner;
import org.freeforums.geforce.securitycraft.tileentity.CustomizableSCTE;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityOwnable;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityPortableRadar;

@SuppressWarnings({"unused"})
public class ForgeEventHandler {
	
	private int counter = 0;
	private int cooldownCounter = 0;
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event){
		mod_SecurityCraft.instance.setIrcBot(event.player.getName());
		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("Thanks for using SecurityCraft " + mod_SecurityCraft.getVersion() + "! Tip: " + getRandomTip(), new Object[0]);
    	
		if(mod_SecurityCraft.configHandler.sayThanksMessage){
			event.player.addChatComponentMessage(chatcomponenttranslation);	
		}
	}
	
	private String getRandomTip(){
    	Random random = new Random();
    	int randomInt = random.nextInt(3);
    	
    	if(randomInt == 0){
    		return "Join breakinbad.net, the official SecurityCraft server!";
    	}else if(randomInt == 1){
    		return "/sc recipe will display the recipe for SecurityCraft blocks/items.";
    	}else if(randomInt == 2){
    		return "/sc help will display help info for SecurityCraft blocks/items.";
    	}else{
    		return "";
    	}
    }
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(PlayerTickEvent event){
		counter++;
		if(cooldownCounter > 0){
			cooldownCounter--;
		}
		
		if(counter >= 20){
			mod_SecurityCraft.network.sendToServer(new PacketCheckRetinalScanner(event.player.getName()));
			counter = 0;
		}
		
	}
	
	@SubscribeEvent
	public void onBucketUsed(FillBucketEvent event){
		ItemStack result = fillBucket(event.world, event.target.getBlockPos());
		if(result == null){ return; }
		event.result = result;
		event.setResult(Result.ALLOW);
		
	}
	
	@SubscribeEvent 
	public void onPlayerLoggedOut(PlayerLoggedOutEvent event){
		if(mod_SecurityCraft.configHandler.disconnectOnWorldClose && mod_SecurityCraft.instance.getIrcBot(event.player.getName()) != null){
			mod_SecurityCraft.instance.getIrcBot(event.player.getName()).disconnect();
			mod_SecurityCraft.instance.removeIrcBot(event.player.getName());
		}
			
	}
	
	@SubscribeEvent 
	public void onPlayerInteracted(PlayerInteractEvent event){
		if(event.entityPlayer.worldObj.isRemote){
			return;
		}else{
			if(event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer.worldObj.getTileEntity(event.pos) != null && isCustomizableBlock(event.entityPlayer.worldObj.getBlockState(event.pos).getBlock(), event.entityPlayer.worldObj.getTileEntity(event.pos)) && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == mod_SecurityCraft.universalBlockModifier){
				event.setCanceled(true);
				
				if(event.entityPlayer.getCurrentEquippedItem().getItem() == mod_SecurityCraft.universalBlockModifier && ((CustomizableSCTE) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerUUID() != null && !((CustomizableSCTE) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerUUID().matches(event.entityPlayer.getGameProfile().getId().toString())){
					PlayerUtils.sendMessageToPlayer(event.entityPlayer, "I'm sorry, you can not customize this block. This block is owned by " + ((TileEntityOwnable) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerUUID() + ".", EnumChatFormatting.RED);
					return;
				}
				
				event.entityPlayer.openGui(mod_SecurityCraft.instance, 100, event.entityPlayer.worldObj, event.pos.getX(), event.pos.getY(), event.pos.getZ());	
				return;
			}
			
			if(event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer.worldObj.getBlockState(event.pos).getBlock() == mod_SecurityCraft.portableRadar && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == Items.name_tag && event.entityPlayer.getCurrentEquippedItem().hasDisplayName()){
				event.setCanceled(true);
				
				event.entityPlayer.getCurrentEquippedItem().stackSize--;
				
				((TileEntityPortableRadar) event.entityPlayer.worldObj.getTileEntity(event.pos)).setCustomName(event.entityPlayer.getCurrentEquippedItem().getDisplayName());
				return;
			}
			
			if(event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer.worldObj.getTileEntity(event.pos) != null && isOwnableBlock(event.entityPlayer.worldObj.getBlockState(event.pos).getBlock(), event.entityPlayer.worldObj.getTileEntity(event.pos)) && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == mod_SecurityCraft.universalBlockRemover){
				event.setCanceled(true);

				if(((IOwnable) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerUUID() != null && !((IOwnable) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerUUID().matches(event.entityPlayer.getGameProfile().getId().toString())){
					PlayerUtils.sendMessageToPlayer(event.entityPlayer, "I'm sorry, you can not remove this block. This block is owned by " + ((IOwnable) event.entityPlayer.worldObj.getTileEntity(event.pos)).getOwnerName() + ".", EnumChatFormatting.RED);
					return;
				}

				if(event.entityPlayer.worldObj.getBlockState(event.pos).getBlock() == mod_SecurityCraft.LaserBlock){
					event.entityPlayer.worldObj.destroyBlock(event.pos, true);
					BlockLaserBlock.destroyAdjecentLasers(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ());
					event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
				}else{
					event.entityPlayer.worldObj.destroyBlock(event.pos, true);
					event.entityPlayer.worldObj.removeTileEntity(event.pos);
					event.entityPlayer.getCurrentEquippedItem().damageItem(1, event.entityPlayer);
				}
			}
		}
	}
	
	@SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if(event.modID.equals("securitycraft")){
        	mod_SecurityCraft.configFile.save();
        }
    }
	
	@SubscribeEvent
	public void onBlockBroken(BreakEvent event){
		if(!event.world.isRemote){
			if(event.world.getTileEntity(event.pos) != null && event.world.getTileEntity(event.pos) instanceof CustomizableSCTE){
				for(int i = 0; i < ((CustomizableSCTE) event.world.getTileEntity(event.pos)).getNumberOfCustomizableOptions(); i++){
					if(((CustomizableSCTE) event.world.getTileEntity(event.pos)).itemStacks[i] != null){
						ItemStack stack = ((CustomizableSCTE) event.world.getTileEntity(event.pos)).itemStacks[i];
						EntityItem item = new EntityItem(event.world, (double) event.pos.getX(), (double) event.pos.getY(), (double) event.pos.getZ(), stack);
						event.world.spawnEntityInWorld(item);
						
						((CustomizableSCTE) event.world.getTileEntity(event.pos)).onModuleRemoved(stack, ((ItemModule) stack.getItem()).getModule());
					}
				}
			}
		}
	}
	
	@SubscribeEvent //TODO
	public void onEntityMounted(EntityMountEvent event){
		if(!mod_SecurityCraft.debuggingMode){ return; }
		
		if(!event.worldObj.isRemote && event.isMounting() && event.entityBeingMounted != null && event.entityBeingMounted instanceof EntitySecurityCamera){
			((EntityPlayer) event.entityMounting).setInvisible(true);
		}else if(!event.worldObj.isRemote && event.isDismounting() && event.entityBeingMounted != null && event.entityBeingMounted instanceof EntitySecurityCamera){
			((EntityPlayer) event.entityMounting).setInvisible(false);
		}
	}
	
	private ItemStack fillBucket(World world, BlockPos pos){
		Block block = world.getBlockState(pos).getBlock();
		
		if(block == mod_SecurityCraft.bogusWater){
			world.setBlockToAir(pos);
			return new ItemStack(mod_SecurityCraft.fWaterBucket, 1);
		}else if(block == mod_SecurityCraft.bogusLava){
			world.setBlockToAir(pos);
			return new ItemStack(mod_SecurityCraft.fLavaBucket, 1);
		}else{
			return null;
		}
	}
  
	private boolean isOwnableBlock(Block block, TileEntity tileEntity){
    	if(tileEntity instanceof TileEntityOwnable || tileEntity instanceof IOwnable || block instanceof BlockOwnable){
    		return true;
    	}else{
    		return false;
    	}
    }
	
	private boolean isCustomizableBlock(Block block, TileEntity tileEntity){
    	if(tileEntity instanceof CustomizableSCTE){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public void setCooldown(int par1){
    	this.cooldownCounter = par1;
    }
    
    public int getCooldown(){
    	return this.cooldownCounter;
    }

}
