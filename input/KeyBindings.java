package com.juraka.modj.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
	
	public static KeyBinding tHelpKey;
	
	public static void init() {
		tHelpKey = new KeyBinding("key.tHelp", Keyboard.KEY_T, "key.categories.modtemplate");
		ClientRegistry.registerKeyBinding(tHelpKey);
	}
}
