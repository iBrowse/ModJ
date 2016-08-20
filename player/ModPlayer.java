package com.juraka.modj.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ModPlayer 
{
	private EntityPlayer player;
	private int reknown; //we use this to hire NPCs or found/support structures and towns?
	private int power; //we use this to level up our NPCs and structures?

	public ModPlayer(EntityPlayer playerIn)
	{
		this.player = playerIn;
	}
	
	public void init() 
	{
		player = Minecraft.getMinecraft().thePlayer;
	}
	
	public ModPlayer(EntityPlayer playerIn, int reknown, int power)
	{
		this.player = playerIn;
		this.reknown = reknown;
		this.power = power;
	}
	
	public void setReknown(int amount)
	{
		this.reknown = amount;
	}
	
	public void addReknown(int amount)
	{
		int oldR = this.reknown;
		int newR = oldR + amount;
		this.reknown = newR;
		
		//this.setReknown(this.reknown + amount);
	}
	
	public void setPower(int amount)
	{
		this.power = amount;
	}
	
	public void addPower(int amount)
	{
		this.setPower(this.power + amount);
	}

	
	
}
