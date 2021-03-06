package org.freeforums.geforce.securitycraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.freeforums.geforce.securitycraft.interfaces.IHelpInfo;
import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityKeypadChest;

public class BlockKeypadChest extends BlockChest implements IHelpInfo {

	public BlockKeypadChest(int par1){
		super(par1);
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote){
            return true;
        }else{
            IInventory iinventory = this.func_149951_m(par1World, par2, par3, par4);

            if (iinventory != null){ 
            	if(par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() == mod_SecurityCraft.Codebreaker){
            		par5EntityPlayer.displayGUIChest(iinventory);
            		return true;
            	}
            	
            	if(par1World.getTileEntity(par2, par3, par4) != null && par1World.getTileEntity(par2, par3, par4) instanceof TileEntityKeypadChest){
            		if(((TileEntityKeypadChest) par1World.getTileEntity(par2, par3, par4)).getKeypadCode() != null && !((TileEntityKeypadChest) par1World.getTileEntity(par2, par3, par4)).getKeypadCode().isEmpty()){
            			par5EntityPlayer.openGui(mod_SecurityCraft.instance, 13, par1World, par2, par3, par4);
            		}else{
            			par5EntityPlayer.openGui(mod_SecurityCraft.instance, 12, par1World, par2, par3, par4);

            		}
            	}
            	
            }

            return true;
        }
    }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        
        ((TileEntityKeypadChest) par1World.getTileEntity(par2, par3, par4)).setOwner(((EntityPlayer) par5EntityLivingBase).getGameProfile().getId().toString(), par5EntityLivingBase.getCommandSenderName());
        
        if(par1World.getTileEntity(par2 + 1, par3, par4) != null && par1World.getTileEntity(par2 + 1, par3, par4) instanceof TileEntityKeypadChest){
        	((TileEntityKeypadChest)(par1World.getTileEntity(par2, par3, par4))).setKeypadCode(((TileEntityKeypadChest) par1World.getTileEntity(par2 + 1, par3, par4)).getKeypadCode());
		}else if(par1World.getTileEntity(par2 - 1, par3, par4) != null && par1World.getTileEntity(par2 - 1, par3, par4) instanceof TileEntityKeypadChest){
			((TileEntityKeypadChest)(par1World.getTileEntity(par2, par3, par4))).setKeypadCode(((TileEntityKeypadChest) par1World.getTileEntity(par2 - 1, par3, par4)).getKeypadCode());
		}else if(par1World.getTileEntity(par2, par3, par4 + 1) != null && par1World.getTileEntity(par2, par3, par4 + 1) instanceof TileEntityKeypadChest){
			((TileEntityKeypadChest)(par1World.getTileEntity(par2, par3, par4))).setKeypadCode(((TileEntityKeypadChest) par1World.getTileEntity(par2, par3, par4 + 1)).getKeypadCode());
		}else if(par1World.getTileEntity(par2, par3, par4 - 1) != null && par1World.getTileEntity(par2, par3, par4 - 1) instanceof TileEntityKeypadChest){
			((TileEntityKeypadChest)(par1World.getTileEntity(par2, par3, par4))).setKeypadCode(((TileEntityKeypadChest) par1World.getTileEntity(par2, par3, par4 - 1)).getKeypadCode());
		}
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5Block)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5Block);
        TileEntityKeypadChest tileentitychest = (TileEntityKeypadChest)par1World.getTileEntity(par2, par3, par4);

        if (tileentitychest != null)
        {
            tileentitychest.updateContainingBlockInfo();
        }
      
    }
	
	/**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World, int par2)
    {
        return new TileEntityKeypadChest();
    }
  
	public String[] getRecipe() {
		return new String[]{"The password-protected chest requires: 7 iron ingot, 1 keypad, 1 chest", "XYX", "XZX", "XXX", "X = iron ingot, Y = keypad, Z = chest"};
	}

}
