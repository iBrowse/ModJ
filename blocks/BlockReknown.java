package com.juraka.modj.blocks;

import javax.annotation.Nullable;

import com.juraka.modj.ModJ;
import com.juraka.modj.entity.items.EntityReknownOrb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockReknown extends Block 
{
	private int amount;
	
	public boolean isReknownBlock = true;
	
	public BlockReknown() 
	{
		super(Material.ROCK);
		
		setUnlocalizedName(ModJ.MODID + ".reknown_block");
		
		setRegistryName("modj:reknown_block");
		
		GameRegistry.register(this);
		
		GameRegistry.register(new ItemBlock(this), this.getRegistryName());	
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	public int getAmount() 
	{
		return amount;
	}
	
	public void setAmount(int amount) 
	{
		this.amount = amount;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			this.dispenseReknown(worldIn, pos, 1);
		}
		
		return true;
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		
	}
	
	public void dispenseReknown(World worldIn, BlockPos pos, int amount)
	{
		if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops"))
		{
			if (amount > 0)
			{
				worldIn.spawnEntityInWorld(new EntityReknownOrb(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.5D, (double)pos.getZ() + 0.5D, amount));
				//playSound
			}
		}
	}
}
