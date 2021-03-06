package net.epoxide.eplus.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import net.epoxide.eplus.client.gui.GuiModEnchantmentTable;
import net.epoxide.eplus.client.gui.GuiVanillaEnchantmentTable;
import net.epoxide.eplus.inventory.ContainerEnchantTable;
import net.epoxide.eplus.tileentity.TileEntityEnchantTable;

public final class GuiHandler implements IGuiHandler {
    
    @Override
    public Object getServerGuiElement (int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        
        TileEntity tileEntity;
        switch (id) {
            case 0:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityEnchantTable)) {
                    return null;
                }
                return new ContainerEnchantTable(entityPlayer.inventory, world, x, y, z, (TileEntityEnchantTable) tileEntity);
            case 1:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityEnchantTable)) {
                    return null;
                }
                return new ContainerEnchantment(entityPlayer.inventory, world, x, y, z) {
                    @Override
                    public boolean canInteractWith (EntityPlayer entityPlayer) {
                        
                        return !entityPlayer.isDead;
                    }
                };
            default:
                return null;
        }
    }
    
    @Override
    public Object getClientGuiElement (int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        
        TileEntity tileEntity;
        switch (id) {
            case 0:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityEnchantTable))
                    return null;
                    
                return new GuiModEnchantmentTable(entityPlayer.inventory, world, x, y, z, (TileEntityEnchantTable) tileEntity);
            case 1:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityEnchantTable))
                    return null;
                    
                return new GuiVanillaEnchantmentTable(entityPlayer.inventory, world, x, y, z, "");
            default:
                return null;
        }
    }
}