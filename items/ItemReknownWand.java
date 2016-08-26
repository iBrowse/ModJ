package com.juraka.modj.items;

import com.juraka.modj.ModJ;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemReknownWand extends Item
{
	public ItemReknownWand() 
	{
		setUnlocalizedName(ModJ.MODID + ".reknown_wand");
		
		setRegistryName(new ResourceLocation("modj:reknown_wand"));
		
		GameRegistry.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
