package org.freeforums.geforce.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.freeforums.geforce.securitycraft.tileentity.TileEntityKeycardReader;

public class PacketSetKeycardLevel implements IMessage{
	
	private int x, y, z, level;
	private boolean exactCard;
	
	public PacketSetKeycardLevel(){
		
	}
	
	public PacketSetKeycardLevel(int x, int y, int z, int level, boolean exactCard){
		this.x = x;
		this.y = y;
		this.z = z;
		this.level = level;
		this.exactCard  = exactCard;
	}

	public void toBytes(ByteBuf par1ByteBuf) {
		par1ByteBuf.writeInt(x);
		par1ByteBuf.writeInt(y);
		par1ByteBuf.writeInt(z);
		par1ByteBuf.writeInt(level);
		par1ByteBuf.writeBoolean(exactCard);
	}

	public void fromBytes(ByteBuf par1ByteBuf) {
		this.x = par1ByteBuf.readInt();
		this.y = par1ByteBuf.readInt();
		this.z = par1ByteBuf.readInt();
		this.level = par1ByteBuf.readInt();
		this.exactCard = par1ByteBuf.readBoolean();
	}
	
public static class Handler extends PacketHelper implements IMessageHandler<PacketSetKeycardLevel, IMessage> {

	public IMessage onMessage(PacketSetKeycardLevel packet, MessageContext context) {
		int x = packet.x;
		int y = packet.y;
		int z = packet.z;
		int level = packet.level;
		boolean exactCard = packet.exactCard;
		EntityPlayer par1EntityPlayer = context.getServerHandler().playerEntity;

		((TileEntityKeycardReader) getWorld(par1EntityPlayer).getTileEntity(new BlockPos(x, y, z))).setPassLV(level);
		((TileEntityKeycardReader) getWorld(par1EntityPlayer).getTileEntity(new BlockPos(x, y, z))).setRequiresExactKeycard(exactCard);
		
		return null;
	}
}

	
}
