package com.juraka.modj;

import com.juraka.modj.items.ItemReknownWand;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems 
{
	public static ItemReknownWand reknownWand;
	
	public static void init() 
	{
		reknownWand = new ItemReknownWand();
	}
		
	@SideOnly(Side.CLIENT)
	public static void initModels() 
	{
		reknownWand.initModel();
	}
}
