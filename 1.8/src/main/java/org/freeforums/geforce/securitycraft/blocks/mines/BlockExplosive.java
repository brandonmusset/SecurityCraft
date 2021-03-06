package org.freeforums.geforce.securitycraft.blocks.mines;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import org.freeforums.geforce.securitycraft.blocks.BlockOwnable;
import org.freeforums.geforce.securitycraft.interfaces.IExplosive;

public abstract class BlockExplosive extends BlockOwnable implements IExplosive {

	public BlockExplosive(Material par1) {
		super(par1);
	}
	
	public abstract void explode(World world, BlockPos pos);
	
	public boolean isDefusable(){
		return true;
	}

}
