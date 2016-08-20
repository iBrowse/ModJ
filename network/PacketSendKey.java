package com.juraka.modj.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendKey implements IMessage {
	private BlockPos blockPos;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(blockPos.getX());
		buf.writeInt(blockPos.getY());
		buf.writeInt(blockPos.getZ());
	}
	
	public PacketSendKey() {
		//find blockPos of block we are looking at
		RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
		blockPos = result.getBlockPos();
	}
	
	public static class Handler implements IMessageHandler<PacketSendKey, IMessage> {
		@Override
		public IMessage onMessage(PacketSendKey message, MessageContext context) {
			//ensures handler code is run on main Minecraft thread?? hope it works
			FMLCommonHandler.instance().getWorldThread(context.netHandler).addScheduledTask(handle(message, context));
			return null;
		}
		
		private Runnable handle(PacketSendKey message, MessageContext context) {
			//run on server side. Safe to do server-side calculations here
			EntityPlayerMP playerEntity = context.getServerHandler().playerEntity;
			World world = playerEntity.worldObj;
			Block block = world.getBlockState(message.blockPos).getBlock();
			playerEntity.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Hit block: " + block.getRegistryName()));
			return null;
		}
	}
}
