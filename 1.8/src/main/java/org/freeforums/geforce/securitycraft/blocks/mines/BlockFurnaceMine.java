package org.freeforums.geforce.securitycraft.blocks.mines;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.freeforums.geforce.securitycraft.blocks.BlockOwnable;
import org.freeforums.geforce.securitycraft.interfaces.IExplosive;
import org.freeforums.geforce.securitycraft.interfaces.IHelpInfo;
import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;

public class BlockFurnaceMine extends BlockOwnable implements IExplosive, IHelpInfo {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockFurnaceMine(Material par1Material) {
		super(par1Material);
	}

	/**
	 * Called upon the block being destroyed by an explosion
	 */
	public void onBlockDestroyedByExplosion(World par1World, BlockPos pos, Explosion par5Explosion)
	{
		if (!par1World.isRemote)
		{
			this.explode(par1World, pos);
		}
	}

	public void onBlockDestroyedByPlayer(World par1World, BlockPos pos, IBlockState state){
		if (!par1World.isRemote)
		{
			this.explode(par1World, pos);
		}
	}	

	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumFacing side, float par7, float par8, float par9){
		if(par1World.isRemote){
			return true;
		}else{
			if(par5EntityPlayer.getCurrentEquippedItem() == null || par5EntityPlayer.getCurrentEquippedItem().getItem() != mod_SecurityCraft.remoteAccessMine){
				this.explode(par1World, pos);
				return true;
			}else{
				return false;	   		
			}
		}
	}	
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	public void activateMine(World world, BlockPos pos) {}

	public void defuseMine(World world, BlockPos pos) {}
	
	public void explode(World par1World, BlockPos pos) {
		par1World.destroyBlock(pos, false);

		if(mod_SecurityCraft.configHandler.smallerMineExplosion){
			par1World.createExplosion((Entity)null, pos.getX(), pos.getY(), pos.getZ(), 2.5F, true);
		}else{
			par1World.createExplosion((Entity)null, pos.getX(), pos.getY(), pos.getZ(), 5.0F, true);
		}

	}

	/**
	 * Return whether this block can drop from an explosion.
	 */
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

	public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta].getAxis() == EnumFacing.Axis.Y ? EnumFacing.NORTH : EnumFacing.values()[meta]);
    }

    public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[] {FACING});
    }
	
	public boolean isActive(World world, BlockPos pos) {
		return true;
	}
	
	public boolean isDefusable() {
		return false;
	}

	public String getHelpInfo() {
		return "The furnace mine will explode when a player right-clicks on it.";
	}

	public String[] getRecipe() {
		return new String[]{"The furnace mine requires: 1 furnace, 1 mine. This is a shapeless recipe."};
	}

}
