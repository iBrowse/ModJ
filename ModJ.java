package com.juraka.modj;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.juraka.modj.input.InputHandler;
import com.juraka.modj.input.KeyBindings;
import com.juraka.modj.network.PacketHandler;
import com.juraka.modj.player.ModPlayer;


@Mod(modid = ModJ.MODID, name = ModJ.MODNAME, version = ModJ.VERSION, dependencies = "required-after:Forge@[12.18.1.2014]", useMetadata = true)
public class ModJ {
	public static final String MODID = "modj";
	public static final String MODNAME = "ModJ";
	public static final String VERSION = "0.0.1";

    @SidedProxy
    public static CommonProxy proxy;
    
    @Mod.Instance
    public static ModJ instance;
    
    public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
    
    public static class CommonProxy {
    	
    	public void preInit(FMLPreInitializationEvent e)
    	{
    		PacketHandler.registerMessages("modj");
    		
    		ModBlocks.init();
    		ModItems.init();
    		
    		//TODO modPlayer = new ModPlayer(EntityPlayer)
    	}
    	
    	public void init(FMLInitializationEvent e)
    	{
    		
    	}
    	
    	public void postInit(FMLPostInitializationEvent e) {
    		
    	}
    	
    }
    /**
     * Initialization specifically for the client
     * @author DMT
     * 
     */
    public static class ClientProxy extends CommonProxy
    {
    	
    	@Override
    	public void preInit(FMLPreInitializationEvent e)
    	{
    		super.preInit(e);
    		
    		ModBlocks.initModels();
    		ModItems.initModels();
    	}
    	
    	@Override
    	public void init(FMLInitializationEvent e)
    	{
    		super.init(e);
    		
    		//initializes input handler
    		MinecraftForge.EVENT_BUS.register(new InputHandler());
    		KeyBindings.init();
    		
    		ModBlocks.initItemModels();
    		//TODO ModPlayer.init();
    	}
    	
    }
    
    /**
     * Initialization specifically for the server
     * @author DMT
     *
     */
    public static class ServerProxy extends CommonProxy
    {

    }

}
