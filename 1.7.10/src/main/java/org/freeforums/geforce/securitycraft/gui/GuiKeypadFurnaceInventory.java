package org.freeforums.geforce.securitycraft.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;
import org.freeforums.geforce.securitycraft.network.packets.PacketSetBlock;
import org.freeforums.geforce.securitycraft.network.packets.PacketSetBlockMetadata;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiKeypadFurnaceInventory extends GuiContainer{
	
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
    private TileEntityFurnace tileFurnace;

    public GuiKeypadFurnaceInventory(InventoryPlayer p_i1091_1_, TileEntityFurnace p_i1091_2_){
        super(new ContainerFurnace(p_i1091_1_, p_i1091_2_));
        this.tileFurnace = p_i1091_2_;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format("Protected Furnace", new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString("Protected Furnace", 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        if (this.tileFurnace.isBurning())
        {
            int i1 = this.tileFurnace.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.tileFurnace.getCookProgressScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
    }
    
    public void onGuiClosed(){
    	super.onGuiClosed();
		mod_SecurityCraft.network.sendToServer(new PacketSetBlockMetadata(this.tileFurnace.xCoord, this.tileFurnace.yCoord, this.tileFurnace.zCoord, this.mc.theWorld.getBlockMetadata(this.tileFurnace.xCoord, this.tileFurnace.yCoord, this.tileFurnace.zCoord) - 5, false, 1, "", ""));
    }
    
}