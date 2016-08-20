package com.juraka.modj;

import com.juraka.modj.blocks.*;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks 
{
	public static BlockReknown reknownBlock;
	
	public static void init() 
	{
		reknownBlock = new BlockReknown();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() 
	{
		reknownBlock.initModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initItemModels() 
	{
		//bakedModelBlock for example
	}
}
