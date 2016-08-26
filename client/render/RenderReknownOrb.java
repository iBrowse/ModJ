package com.juraka.modj.client.render;

import com.juraka.modj.entity.items.EntityReknownOrb;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderReknownOrb extends Render<EntityReknownOrb>
{
	private static final ResourceLocation REKNOWN_ORB_TEXTURE = new ResourceLocation("modj:textures/entities/reknown_orb.png");
	
	public RenderReknownOrb(RenderManager renderManagerIn)
	{
		super(renderManagerIn);
		this.shadowSize = 0.25F;
		this.shadowOpaque = 0.6F;
	}
	
	public void doRender(EntityReknownOrb entity, double x, double y, double z, float entityYar, float partialTicks)
	{
		//special stuff if ReknownOrb should change color like XPOrb
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityReknownOrb entity)
	{
		return REKNOWN_ORB_TEXTURE;
	}

}
