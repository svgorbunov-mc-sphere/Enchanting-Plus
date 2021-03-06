package net.epoxide.eplus.common.network;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import net.darkhax.bookshelf.common.network.AbstractMessage;

import io.netty.buffer.ByteBuf;
import net.epoxide.eplus.inventory.ContainerEnchantTable;

public class PacketEnchant extends AbstractMessage {
    
    protected int totalCost;
    protected HashMap<Integer, Integer> enchants = new HashMap<Integer, Integer>();
    
    public PacketEnchant() {
    
    }
    
    public PacketEnchant(HashMap<Integer, Integer> enchants, int totalCost) {
        
        this.enchants = enchants;
        this.totalCost = totalCost;
    }
    
    @Override
    public void handleClientMessage (AbstractMessage message, EntityPlayer player) {
    
    }
    
    @Override
    public void handleServerMessage (AbstractMessage message, EntityPlayer player) {
        
        if (message instanceof PacketEnchant) {
            
            PacketEnchant packet = (PacketEnchant) message;
            if (player.openContainer instanceof ContainerEnchantTable) {
                
                ((ContainerEnchantTable) player.openContainer).updateItemStack(player, packet.enchants, packet.totalCost);
                player.openContainer.detectAndSendChanges();
            }
        }
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        final HashMap<Integer, Integer> enchants = new HashMap<Integer, Integer>();
        
        this.totalCost = buf.readInt();
        
        int size = buf.readInt();
        
        for (int i = 0; i < size; i++) {
            enchants.put(buf.readInt(), buf.readInt());
        }
        
        this.enchants = enchants;
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(totalCost);
        buf.writeInt(enchants.size());
        
        for (Integer enchantmentId : enchants.keySet()) {
            buf.writeInt(enchantmentId);
            buf.writeInt(enchants.get(enchantmentId));
        }
    }
}
