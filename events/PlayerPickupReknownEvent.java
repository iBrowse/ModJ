package com.juraka.modj.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import com.juraka.modj.entity.items.EntityReknownOrb;

/**
 * This event is called when a player collides with a EntityReknownOrb on the ground.
 * The event can be canceled, and no further processing will be done.  
 */
@Cancelable
public class PlayerPickupReknownEvent extends net.minecraftforge.event.entity.player.PlayerEvent
{
    private final EntityReknownOrb orb;

    public PlayerPickupReknownEvent(EntityPlayer player, EntityReknownOrb orb)
    {
        super(player);
        this.orb = orb;
    }

    public EntityReknownOrb getOrb()
    {
        return orb;
    }
}