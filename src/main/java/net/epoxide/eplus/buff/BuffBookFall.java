package net.epoxide.eplus.buff;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.darkhax.bookshelf.buff.Buff;
import net.darkhax.bookshelf.buff.BuffHelper;

import net.epoxide.eplus.handler.ContentHandler;
import net.epoxide.eplus.lib.util.RenderUtil;

public class BuffBookFall extends Buff {

    private static ResourceLocation texture = new ResourceLocation("eplus:textures/entity/enchantingplus_book.png");
    private static ResourceLocation icon = new ResourceLocation("eplus:textures/buffs/enchanted_guardian.png");

    public BuffBookFall() {

        super("eplus.book", false, new Color(128, 0, 128).getRGB(), icon);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerRender (RenderLivingEvent.Specials.Pre event) {

        if (shouldUseEffect(event.entity)) {

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glTranslated(event.x, event.y, event.z);
            GL11.glTranslatef(0f, -0.5f, 0f);
            GL11.glRotatef(26f, 0f, -1f, 0f);
            GL11.glRotatef(event.entity.rotationYaw, 0, -1f, 0);
            GL11.glScalef(4f, 4, 4f);
            RenderUtil.renderBook(texture, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 0f);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate (LivingUpdateEvent event) {

        if (shouldUseEffect(event.entityLiving)) {

            event.entityLiving.motionY *= 0.45D;
            event.entityLiving.fallDistance = 0;
        }
    }

    public boolean shouldUseEffect (EntityLivingBase entity) {

        if (BuffHelper.hasBuff(entity, ContentHandler.buffFloatingBook) && !entity.onGround && !entity.isInWater() && !entity.isInsideOfMaterial(Material.water)) {
            return (entity.motionY < 0.0f);
        }

        return false;
    }
}