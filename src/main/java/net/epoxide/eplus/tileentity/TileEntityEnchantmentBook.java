package net.epoxide.eplus.tileentity;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnchantmentBook extends TileEntity {
    
    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    public float pageFlipRandom;
    public float pageFlipTurn;
    public float foldAmount;
    public float prevFoldAmount;
    public float rotation;
    public float prevRotation;
    public float bookRotation;
    
    public float yOffset;
    public int color;
    
    private static Random random = new Random();
    
    public TileEntityEnchantmentBook() {
        
        yOffset = 0.0f;
        color = 0;
    }
    
    @Override
    public void readFromNBT (NBTTagCompound nbt) {
        
        super.readFromNBT(nbt);
        this.yOffset = nbt.getFloat("bookOffset");
        this.color = nbt.getInteger("bookColor");
    }
    
    @Override
    public void writeToNBT (NBTTagCompound nbt) {
        
        super.writeToNBT(nbt);
        nbt.setFloat("bookOffset", this.yOffset);
        nbt.setInteger("bookColor", this.color);
    }
    
    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity pkt) {
        
        this.readFromNBT(pkt.func_148857_g());
        this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
    
    @Override
    public Packet getDescriptionPacket () {
        
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }
    
    @Override
    public void updateEntity () {
        
        if (worldObj.isRemote)
            updateBook();
    }
    
    private void updateBook () {
        
        this.prevFoldAmount = this.foldAmount;
        this.prevRotation = this.rotation;
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double) ((float) this.xCoord + 0.5F), (double) ((float) this.yCoord + 0.5F), (double) ((float) this.zCoord + 0.5F), 3.0D);
        
        if (entityplayer != null) {
            double d0 = entityplayer.posX - (double) ((float) this.xCoord + 0.5F);
            double d1 = entityplayer.posZ - (double) ((float) this.zCoord + 0.5F);
            this.bookRotation = (float) Math.atan2(d1, d0);
            this.foldAmount += 0.1F;
            
            if (this.foldAmount < 0.5F || random.nextInt(40) == 0) {
                float f1 = this.pageFlipRandom;
                
                do {
                    this.pageFlipRandom += (float) (random.nextInt(4) - random.nextInt(4));
                }
                while (f1 == this.pageFlipRandom);
            }
        }
        else {
            this.bookRotation += 0.02F;
            this.foldAmount -= 0.1F;
        }
        
        while (this.rotation >= (float) Math.PI) {
            this.rotation -= ((float) Math.PI * 2F);
        }
        
        while (this.rotation < -(float) Math.PI) {
            this.rotation += ((float) Math.PI * 2F);
        }
        
        while (this.bookRotation >= (float) Math.PI) {
            this.bookRotation -= ((float) Math.PI * 2F);
        }
        
        while (this.bookRotation < -(float) Math.PI) {
            this.bookRotation += ((float) Math.PI * 2F);
        }
        
        float f2;
        
        f2 = this.bookRotation - this.rotation;
        while (f2 >= (float) Math.PI) {
            f2 -= ((float) Math.PI * 2F);
        }
        
        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }
        
        this.rotation += f2 * 0.4F;
        
        if (this.foldAmount < 0.0F) {
            this.foldAmount = 0.0F;
        }
        
        if (this.foldAmount > 1.0F) {
            this.foldAmount = 1.0F;
        }
        
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float f = (this.pageFlipRandom - this.pageFlip) * 0.4F;
        float f3 = 0.2F;
        
        if (f < -f3) {
            f = -f3;
        }
        
        if (f > f3) {
            f = f3;
        }
        
        this.pageFlipTurn += (f - this.pageFlipTurn) * 0.9F;
        this.pageFlip += this.pageFlipTurn;
    }
    
    public void increaseHeight () {
        
        this.yOffset += 0.05f;
        
        if (this.yOffset > 0.5f)
            this.yOffset = 0.5f;
    }
    
    public void decreaseHeight () {
        
        this.yOffset -= 0.05f;
        
        if (this.yOffset < -0.5f)
            this.yOffset = -0.5f;
    }
}