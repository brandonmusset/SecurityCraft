package org.freeforums.geforce.securitycraft.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.freeforums.geforce.securitycraft.items.ItemModule;
import org.freeforums.geforce.securitycraft.misc.EnumCustomModules;
import org.freeforums.geforce.securitycraft.tileentity.CustomizableSCTE;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityInventoryScanner;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityKeycardReader;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityKeypad;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityRetinalScanner;
import org.freeforums.geforce.securitycraft.timers.ScheduleKeycardUpdate;
import org.freeforums.geforce.securitycraft.timers.ScheduleUpdate;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * SecurityCraft's utility methods are found here. Frequently used or simplified vanilla code can be found here.
 * 
 * @author Geforce
 */
public class Utils {
	
public static class PlayerUtils{
	
	/**
	 * Sets the given player's position and rotation. <p>
	 * 
	 * Args: player, x, y, z, yaw, pitch.
	 */
	public static void setPlayerPosition(EntityPlayer player, double x, double y, double z, float yaw, float pitch){
		player.setPositionAndRotation(x, y, z, yaw, pitch);
		player.setPositionAndUpdate(x, y, z);
	}
	
	/**
	 * Gets the EntityPlayer instance of a player (if they're online) using their name. <p>
	 * 
	 * Args: playerName.
	 */
	@SuppressWarnings("rawtypes")
	public static EntityPlayer getPlayerFromName(String par1){
    	List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
    	Iterator iterator = players.iterator();
    	
    	while(iterator.hasNext()){
    		EntityPlayer tempPlayer = (EntityPlayer) iterator.next();
    		if(tempPlayer.getCommandSenderName().matches(par1)){
    			return tempPlayer;
    		}
    	}
    	
    	return null;
    }
	
	/**
	 * Sends the given player a chat message. <p>
	 * 
	 * Args: player, message, color.
	 */
	public static void sendMessageToPlayer(EntityPlayer par1EntityPlayer, String par2, EnumChatFormatting par3){
		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(par2, new Object[0]);
    	
		if(par3 != null){
    		chatcomponenttranslation.getChatStyle().setColor(par3);
    	}
    	
		par1EntityPlayer.addChatComponentMessage(chatcomponenttranslation);
	}
	
	/**
	 * Sends the given {@link ICommandSender} a chat message. <p>
	 * 
	 * Args: sender, message, color.
	 */
	public static void sendMessage(ICommandSender par1ICommandSender, String par2, EnumChatFormatting par3){
        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(par2, new Object[0]);

        if(par3 != null){
    		chatcomponenttranslation.getChatStyle().setColor(par3);
    	}
    	
        par1ICommandSender.addChatMessage(chatcomponenttranslation);
	}
	
}

public static class BlockUtils{
	
	/**
	 * Used by the Cage Trap to create the cage. <p>
	 * 
	 * Args: world, x, y, z, block.
	 */
	public static void setBlockInBox(World par1World, int par2, int par3, int par4, Block par5){
		par1World.setBlock(par2 + 1, par3 + 1, par4, par5);
		par1World.setBlock(par2 + 1, par3 + 2, par4, par5);
		par1World.setBlock(par2 + 1, par3 + 3, par4, par5);
		par1World.setBlock(par2 + 1, par3 + 1, par4 + 1, par5);
		par1World.setBlock(par2 + 1, par3 + 2, par4 + 1, par5);
		par1World.setBlock(par2 + 1, par3 + 3, par4 + 1, par5);
		par1World.setBlock(par2 - 1, par3 + 1, par4, par5);
		par1World.setBlock(par2 - 1, par3 + 2, par4, par5);
		par1World.setBlock(par2 - 1, par3 + 3, par4, par5);
		par1World.setBlock(par2 - 1, par3 + 1, par4 + 1, par5);
		par1World.setBlock(par2 - 1, par3 + 2, par4 + 1, par5);
		par1World.setBlock(par2 - 1, par3 + 3, par4 + 1, par5);
		par1World.setBlock(par2, par3 + 1, par4 + 1, par5);
		par1World.setBlock(par2, par3 + 2, par4 + 1, par5);
		par1World.setBlock(par2, par3 + 3, par4 + 1, par5);

		par1World.setBlock(par2 + 1, par3 + 1, par4, par5);
		par1World.setBlock(par2 + 1, par3 + 2, par4, par5);
		par1World.setBlock(par2 + 1, par3 + 3, par4, par5);

		par1World.setBlock(par2, par3 + 1, par4 - 1, par5);
		par1World.setBlock(par2, par3 + 2, par4 - 1, par5);
		par1World.setBlock(par2, par3 + 3, par4 - 1, par5);
		par1World.setBlock(par2 + 1, par3 + 1, par4 - 1, par5);
		par1World.setBlock(par2 + 1, par3 + 2, par4 - 1, par5);
		par1World.setBlock(par2 + 1, par3 + 3, par4 - 1, par5);
		par1World.setBlock(par2 - 1, par3 + 1, par4 - 1, par5);
		par1World.setBlock(par2 - 1, par3 + 2, par4 - 1, par5);
		par1World.setBlock(par2 - 1, par3 + 3, par4 - 1, par5);
	}
	
	/**
	 * Updates a block and notify's neighboring blocks of a change. <p>
	 * 
	 * Args: worldObj, x, y, z, blockID, tickRate, shouldUpdate.
	 */
	public static void updateAndNotify(World par1World, int par2, int par3, int par4, Block par5, int par6, boolean par7){
		if(par7){
			par1World.scheduleBlockUpdate(par2, par3, par4, par5, par6);
		}
		
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par5, par1World.getBlockMetadata(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2 + 1, par3, par4, par1World.getBlock(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2 - 1, par3, par4, par1World.getBlock(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2, par3, par4 + 1, par1World.getBlock(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2, par3, par4 - 1, par1World.getBlock(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2, par3 + 1, par4, par1World.getBlock(par2, par3, par4));
		par1World.notifyBlockOfNeighborChange(par2, par3 - 1, par4, par1World.getBlock(par2, par3, par4));
	}
	
	/**
	 * Gets the Item version of the given Block. <p>
	 * 
	 * Args: block
	 */
	public static Item getItemFromBlock(Block par1){
		return Item.getItemFromBlock(par1);
	}
	
	/**
	 * Breaks the block at the given coordinates. <p>
	 * 
	 * Args: world, x, y, z, shouldDropItem.
	 */
	public static void destroyBlock(World par1World, int par2, int par3, int par4, boolean par5){
		par1World.func_147480_a(par2, par3, par4, par5);
	}
	
	/**
	 * Checks if the block at the given coordinates is a beacon, and currently producing that light beam? <p>
	 * 
	 * Args: World, x, y, z.
	 */
	public static boolean isActiveBeacon(World par1World, int beaconX, int beaconY, int beaconZ){
		if(par1World.getBlock(beaconX, beaconY, beaconZ) == Blocks.beacon){
			float f = ((TileEntityBeacon) par1World.getTileEntity(beaconX, beaconY, beaconZ)).func_146002_i();
			
			return f > 0.0F ? true : false;
		}else{
			return false;
		}
	}
	
	/**
	 * Checks if the block at the given coordinates is touching the specified block on any side. <p>
	 * 
	 * Args: world, x, y, z, blockToCheckFor, checkYAxis.
	 */
	public static boolean blockSurroundedBy(World world, int x, int y, int z, Block blockToCheckFor, boolean checkYAxis) {
		if(!checkYAxis && (world.getBlock(x + 1, y, z) == blockToCheckFor || world.getBlock(x - 1, y, z) == blockToCheckFor || world.getBlock(x, y, z + 1) == blockToCheckFor || world.getBlock(x, y, z - 1) == blockToCheckFor)){
			return true;
		}else if(checkYAxis && (world.getBlock(x + 1, y, z) == blockToCheckFor || world.getBlock(x - 1, y, z) == blockToCheckFor || world.getBlock(x, y, z + 1) == blockToCheckFor || world.getBlock(x, y, z - 1) == blockToCheckFor || world.getBlock(x, y + 1, z) == blockToCheckFor || world.getBlock(x, y - 1, z) == blockToCheckFor)){
			return true;
		}else{
			return false;
		}
	}
	
}

public static class ModuleUtils{
	
	/**
	 * Insert a module into a customizable TileEntity. <p>
	 * 
	 * Args: world, x, y, z, moduleType.
	 */
	public static void insertModule(World par1World, int par2, int par3, int par4, EnumCustomModules module){
		((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4)).insertModule(module);
	}
	
	/**
	 * Used by the Laser Block to insert modules in all directions.
	 * 
	 * Args: world, x, y, z, direction, blockToCheckFor, range, module, updateAdjecentBlocks
	 */
	public static void checkForBlockAndInsertModule(World par1World, int par2, int par3, int par4, String dir, Block blockToCheckFor, int range, ItemStack module, boolean updateAdjecentBlocks){
		for(int i = 1; i <= range; i++){
			if(dir.equalsIgnoreCase("x+")){
				if(par1World.getBlock(par2 + i, par3, par4) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2 + i, par3, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("x-")){
				if(par1World.getBlock(par2 - i, par3, par4) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2 - i, par3, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("y+")){
				if(par1World.getBlock(par2, par3 + i, par4) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2, par3 + i, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("y-")){
				if(par1World.getBlock(par2, par3 - i, par4) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2, par3 - i, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("z+")){
				if(par1World.getBlock(par2, par3, par4 + i) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2, par3, par4 + i, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("z-")){
				if(par1World.getBlock(par2, par3, par4 - i) == blockToCheckFor && !((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).hasModule(CustomizableSCTE.getTypeFromModule(module))){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).insertModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndInsertModule(par1World, par2, par3, par4 - i, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}
		}
	}
	
	public static void checkInAllDirsAndInsertModule(World par1World, int par2, int par3, int par4, Block blockToCheckFor, int range, ItemStack module, boolean updateAdjecentBlocks){
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "x+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "x-", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "y+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "y-", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "z+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndInsertModule(par1World, par2, par3, par4, "z-", blockToCheckFor, range, module, updateAdjecentBlocks);
	}
	
	/**
	 * Used by the Laser Block to remove modules in all directions.
	 * 
	 * Args: world, x, y, z, direction, blockToCheckFor, range, module, updateAdjecentBlocks
	 */
	public static void checkForBlockAndRemoveModule(World par1World, int par2, int par3, int par4, String dir, Block blockToCheckFor, int range, EnumCustomModules module, boolean updateAdjecentBlocks){
		for(int i = 1; i <= range; i++){
			if(dir.equalsIgnoreCase("x+")){
				if(par1World.getBlock(par2 + i, par3, par4) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2 + i, par3, par4)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2 + i, par3, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("x-")){
				if(par1World.getBlock(par2 - i, par3, par4) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2 - i, par3, par4)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2 - i, par3, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("y+")){
				if(par1World.getBlock(par2, par3 + i, par4) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3 + i, par4)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2, par3 + i, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("y-")){
				if(par1World.getBlock(par2, par3 - i, par4) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3 - i, par4)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2, par3 - i, par4, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("z+")){
				if(par1World.getBlock(par2, par3, par4 + i) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 + i)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2, par3, par4 + i, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}else if(dir.equalsIgnoreCase("z-")){
				if(par1World.getBlock(par2, par3, par4 - i) == blockToCheckFor && ((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).hasModule(module)){
					((CustomizableSCTE) par1World.getTileEntity(par2, par3, par4 - i)).removeModule(module);
					if(updateAdjecentBlocks){
						checkInAllDirsAndRemoveModule(par1World, par2, par3, par4 - i, blockToCheckFor, range, module, updateAdjecentBlocks);
					}
				}
			}
		}
	}
	
	public static void checkInAllDirsAndRemoveModule(World par1World, int par2, int par3, int par4, Block blockToCheckFor, int range, EnumCustomModules module, boolean updateAdjecentBlocks){
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "x+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "x-", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "y+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "y-", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "z+", blockToCheckFor, range, module, updateAdjecentBlocks);
		checkForBlockAndRemoveModule(par1World, par2, par3, par4, "z-", blockToCheckFor, range, module, updateAdjecentBlocks);
	}
	
	/**
	 * Get the {@link ItemModule} instance of the given module type. <p>
	 * 
	 * Args: moduleType.
	 */
	public static ItemModule getItemFromModule(EnumCustomModules module) { //TODO Add any new modules to this list!
		if(module == EnumCustomModules.REDSTONE){
			return mod_SecurityCraft.redstoneModule;
		}else if(module == EnumCustomModules.WHITELIST){
			return mod_SecurityCraft.whitelistModule;
		}else if(module == EnumCustomModules.BLACKLIST){
			return mod_SecurityCraft.blacklistModule;
		}else if(module == EnumCustomModules.HARMING){
			return mod_SecurityCraft.harmingModule;
		}else if(module == EnumCustomModules.SMART){
			return mod_SecurityCraft.smartModule;
		}else{
			return null;
		}
	}
	
	
	/**
	 * Gets the players added to customizable modules (such as the Whitelist Module.) <p>
	 * 
	 * Args: world, x, y, z, moduleType.
	 */
	public static List<String> getPlayersFromModule(World par1World, int par2, int par3, int par4, EnumCustomModules module) {
		List<String> list = new ArrayList<String>();
		
		CustomizableSCTE te = (CustomizableSCTE) par1World.getTileEntity(par2, par3, par4);
		
		if(te.hasModule(module)){
			ItemStack item = te.getModule(module);
						
			for(int i = 1; i <= 10; i++){
				if(item.stackTagCompound != null && item.stackTagCompound.getString("Player" + i) != null && !item.stackTagCompound.getString("Player" + i).isEmpty()){
					list.add(item.stackTagCompound.getString("Player" + i).toLowerCase());
				}
			}
		}
		
		return list;
	}
	
	/**
	 * A large block of code that checks if the TileEntity at the specified coordinates has the given module inserted, and what should happen if it does. <p>
	 * 
	 * Args: world, x, y, z, player, moduleType.
	 */
	public static boolean checkForModule(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, EnumCustomModules module){
		TileEntity te = par1World.getTileEntity(par2, par3, par4);
		
		if(te == null || !(te instanceof CustomizableSCTE)){ return false; }
		
		if(te instanceof TileEntityKeypad){
			if(module == EnumCustomModules.WHITELIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.WHITELIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.WHITELIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(par5EntityPlayer, "You have been whitelisted on this keypad.", EnumChatFormatting.GREEN);
				new ScheduleUpdate(par1World, 3, par2, par3, par4);
				return true;
			}
			
			if(module == EnumCustomModules.BLACKLIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.BLACKLIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.BLACKLIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(par5EntityPlayer, "You have been blacklisted on this keypad.", EnumChatFormatting.RED);
				return true;
			}
		}else if(te instanceof TileEntityKeycardReader){
			if(module == EnumCustomModules.WHITELIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.WHITELIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.WHITELIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(par5EntityPlayer, "You have been whitelisted on this reader.", EnumChatFormatting.GREEN);
				((TileEntityKeycardReader) te).setIsProvidingPower(true);
				new ScheduleKeycardUpdate(3, par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par1World.getBlock(par2, par3, par4));
				return true;
			}
			
			if(module == EnumCustomModules.BLACKLIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.BLACKLIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.BLACKLIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				PlayerUtils.sendMessageToPlayer(par5EntityPlayer, "You have been blacklisted on this reader.", EnumChatFormatting.RED);
				return true;
			}
		}else if(te instanceof TileEntityRetinalScanner){
			if(module == EnumCustomModules.WHITELIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.WHITELIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.WHITELIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				return true;
			}
		}else if(te instanceof TileEntityInventoryScanner){
			if(module == EnumCustomModules.WHITELIST && ((CustomizableSCTE) te).hasModule(EnumCustomModules.WHITELIST) && getPlayersFromModule(par1World, par2, par3, par4, EnumCustomModules.WHITELIST).contains(par5EntityPlayer.getCommandSenderName().toLowerCase())){
				return true;
			}
		}
		
		return false;
	}
	
}

public static class EntityUtils{
	
	/**
	 * Does the given entity have the specified potion effect? <p>
	 * 
	 * Args: entity, potionEffect.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean doesMobHavePotionEffect(EntityLivingBase mob, Potion potion){
		Iterator iterator = mob.getActivePotionEffects().iterator();

		while(iterator.hasNext()){
			PotionEffect effect = (PotionEffect) iterator.next();
			String eName = effect.getEffectName();
			
			if(eName.matches(potion.getName())){
				return true;
			}else{
				continue;
			}
		}
		
		return false;
	}
	
}

public static class ClientUtils{
	
	/**
	 * Closes any GUI that is currently open. <p>
	 * 
	 * Only works on the CLIENT side. 
	 */
	@SideOnly(Side.CLIENT)
	public static void closePlayerScreen(){
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		Minecraft.getMinecraft().setIngameFocus();
	}
	
	/**
	 * Sets the "zoom" of the client's view.
	 * 
	 * Only works on the CLIENT side. 
	 */
	@SideOnly(Side.CLIENT)
	public static void setCameraZoom(double zoom){
		if(zoom == 0){
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 1.0D, 46);
			return;
		}
		
		double tempZoom = ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 46);
		ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, tempZoom + zoom, 46);
	}
	
	/**
	 * Gets the "zoom" of the client's view.
	 * 
	 * Only works on the CLIENT side. 
	 */
	@SideOnly(Side.CLIENT)
	public static double getCameraZoom(){
		return ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, 46);
	}
	
	/**
	 * Takes a screenshot, and sends the player a notification. <p>
	 * 
	 * Only works on the CLIENT side. 
	 */
	@SideOnly(Side.CLIENT)
	public static void takeScreenshot() {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
        	Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(Minecraft.getMinecraft().mcDataDir, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer()));	
        }
	}
	
	/**
	 * Returns the current Minecraft in-game time, in a 12-hour AM/PM format.
	 * 
	 * Only works on the CLIENT side. 
	 */
	@SideOnly(Side.CLIENT)
	public static String getFormattedMinecraftTime(){
		Long time = Long.valueOf(Minecraft.getMinecraft().theWorld.provider.getWorldTime());
		
		int hours24 = (int) ((float) time.longValue() / 1000L + 6L) % 24;
		int hours = hours24 % 12;
		int minutes = (int) ((float) time.longValue() / 16.666666F % 60.0F);
		
		return String.format("%02d:%02d %s", new Object[]{Integer.valueOf(hours < 1 ? 12 : hours), Integer.valueOf(minutes), hours24 < 12 ? "AM" : "PM"});
	}
	
}

	/**
	 * Removes the last character in the given String. <p>
	 */
	public static String removeLastChar(String par1){
		if(par1 == null || par1.isEmpty()){ return ""; }
		
		return par1.substring(0, par1.length() - 1);
	}
	
	/**
	 * Returns the given X, Y, and Z coordinates in a nice String, useful for chat messages. <p>
	 * 
	 * Args: x, y, z.
	 */
	public static String getFormattedCoordinates(int par1, int par2, int par3){
		return " X:" + par1 + " Y:" + par2 + " Z:" + par3;
	}
	
	/**
	 * Returns the opposite value of the given boolean. <p>
	 */
	public static boolean toggleBoolean(boolean par1) {
		return !par1;
	}
	
	//----------------------//
	//    Random methods    //
	//----------------------//
	
	public static void setISinTEAppropriately(World par1World, int par2, int par3, int par4, ItemStack[] contents, String type) {
		if(par1World.getBlockMetadata(par2, par3, par4) == 4 && par1World.getBlock(par2 - 2, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlock(par2 - 1, par3, par4) == mod_SecurityCraft.inventoryScannerField && par1World.getBlockMetadata(par2 - 2, par3, par4) == 5){
			((TileEntityInventoryScanner) par1World.getTileEntity(par2 - 2, par3, par4)).setContents(contents);
			((TileEntityInventoryScanner) par1World.getTileEntity(par2 - 2, par3, par4)).setType(type);
		}else if(par1World.getBlockMetadata(par2, par3, par4) == 5 && par1World.getBlock(par2 + 2, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlock(par2 + 1, par3, par4) == mod_SecurityCraft.inventoryScannerField && par1World.getBlockMetadata(par2 + 2, par3, par4) == 4){
			((TileEntityInventoryScanner) par1World.getTileEntity(par2 + 2, par3, par4)).setContents(contents);
			((TileEntityInventoryScanner) par1World.getTileEntity(par2 + 2, par3, par4)).setType(type);
		}else if(par1World.getBlockMetadata(par2, par3, par4) == 2 && par1World.getBlock(par2, par3, par4 - 2) == mod_SecurityCraft.inventoryScanner && par1World.getBlock(par2, par3, par4 - 1) == mod_SecurityCraft.inventoryScannerField && par1World.getBlockMetadata(par2, par3, par4 - 2) == 3){
			((TileEntityInventoryScanner) par1World.getTileEntity(par2, par3, par4 - 2)).setContents(contents);
			((TileEntityInventoryScanner) par1World.getTileEntity(par2, par3, par4 - 2)).setType(type);
		}else if(par1World.getBlockMetadata(par2, par3, par4) == 3 && par1World.getBlock(par2, par3, par4 + 2) == mod_SecurityCraft.inventoryScanner && par1World.getBlock(par2, par3, par4 + 1) == mod_SecurityCraft.inventoryScannerField && par1World.getBlockMetadata(par2, par3, par4 + 2) == 2){
			((TileEntityInventoryScanner) par1World.getTileEntity(par2, par3, par4 + 2)).setContents(contents);
			((TileEntityInventoryScanner) par1World.getTileEntity(par2, par3, par4 + 2)).setType(type);
		}
	}
	
	public static boolean hasInventoryScannerFacingBlock(World par1World, int par2, int par3, int par4) {
		if(par1World.getBlock(par2 + 1, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2 + 1, par3, par4) == 4 && par1World.getBlock(par2 - 1, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2 - 1, par3, par4) == 5){
			return true;
		}
		else if(par1World.getBlock(par2 - 1, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2 - 1, par3, par4) == 5 && par1World.getBlock(par2 + 1, par3, par4) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2 + 1, par3, par4) == 4){
			return true;
		}
		else if(par1World.getBlock(par2, par3, par4 + 1) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2, par3, par4 + 1) == 2 && par1World.getBlock(par2, par3, par4 - 1) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2, par3, par4 - 1) == 3){
			return true;
		}
		else if(par1World.getBlock(par2, par3, par4 - 1) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2, par3, par4 - 1) == 3 && par1World.getBlock(par2, par3, par4 + 1) == mod_SecurityCraft.inventoryScanner && par1World.getBlockMetadata(par2, par3, par4 + 1) == 2){
			return true;
		}else{
			return false;
		}
	}
	
	//private static void bookCode(){
	//	ItemStack book = new ItemStack(Items.written_book);
	//	
	//	NBTTagCompound tag = new NBTTagCompound();
	//	NBTTagList bookPages = new NBTTagList();
	//	bookPages.appendTag(new NBTTagString("SecurityCraft " + mod_SecurityCraft.getVersion() + " info book."));
	//	bookPages.appendTag(new NBTTagString("Keypad: \n \nThe keypad is used by placing the keypad, right-clicking it, and setting a numerical passcode. Once the keycode is set, right-clicking the keypad will allow you to enter the code. If it's correct, the keypad will emit redstone power for three seconds."));
	//	bookPages.appendTag(new NBTTagString("Laser block: The laser block is used by putting two of them within five blocks of each other. When the blocks are placed correctly, a laser should form between them. Whenever a player walks through the laser, both the laser blocks will emit a 15-block redstone signal."));
	//	
	//	book.setTagInfo("pages", bookPages);
	//	book.setTagInfo("author", new NBTTagString("Geforce"));
	//	book.setTagInfo("title", new NBTTagString("SecurityCraft"));
	//	
	//	player.inventory.addItemStackToInventory(book);
	//}

}
