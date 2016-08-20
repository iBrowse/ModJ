package com.juraka.modj.input;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import com.juraka.modj.network.PacketHandler;
import com.juraka.modj.network.PacketSendKey;

public class InputHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent e) {
		if (KeyBindings.tHelpKey.isPressed()) {
			//someone pressed the help button
			PacketHandler.INSTANCE.sendToServer(new PacketSendKey());
		}
	}
}
