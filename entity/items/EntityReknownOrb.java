package com.juraka.modj.entity.items;

import com.juraka.modj.ModJ;
import com.juraka.modj.events.PlayerPickupReknownEvent;
import com.juraka.modj.player.ModPlayer;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityReknownOrb extends Entity {
	
	/** A constantly increasing value that RenderReknownOrb uses to control the color shifting (Green / yellow) */
    public int rekColor; //TODO deprecate
    /** The age of the Reknown orb in ticks. */
    public int rekOrbAge;
    public int delayBeforeCanPickup;
    /** The health of this Reknown orb. */
    private int rekOrbHealth = 5;
    /** This is how much Reknown this orb has. */
    public int reknownValue;
    /** The closest EntityPlayer to this orb. */
    private EntityPlayer closestPlayer;
    /** Threshold color for tracking players */
    private int rekTargetColor;
	
	public EntityReknownOrb(World worldIn, double x, double y, double z, int rekValue)
    {
        super(worldIn);
        this.setSize(2.0F, 2.0F);
        this.setPosition(x, y, z);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motionY = (double)((float)(Math.random() * 0.5D) * 2.0F);
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.glowing = true;
        this.reknownValue = rekValue;
        this.setCustomNameTag("Reknown Orb");
    }
	
	protected boolean canTriggerWalking()
	{
		return false;
	}


	public EntityReknownOrb(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }
	
	@Override
	protected void entityInit() 
	{
		//setUnlocalizedName(ModJ.MODID + ".reknown_orb");
	}
	
	@Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.func_189652_ae()) //isOnSolidGround()?
        {
            this.motionY -= 0.029999999329447746D;
        }

        if (this.worldObj.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA)
        {
            this.motionY = 5.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
        
        double d0 = 8.0D;

        if (this.rekTargetColor < this.rekColor - 20 + this.getEntityId() % 100)
        {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > 64.0D)
            {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, 8.0D);
            }

            this.rekTargetColor = this.rekColor;
        }

        if (this.closestPlayer != null && this.closestPlayer.isSpectator())
        {
            this.closestPlayer = null;
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
        {
            f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.8999999761581421D;
        }
        
        ++this.rekOrbAge;

        if (this.rekOrbAge >= 60000)
        {
            this.setDead();
        }
    }

	

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.rekOrbHealth = compound.getShort("Health");
        this.rekOrbAge = compound.getShort("Age");
        this.reknownValue = compound.getShort("Value");
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("Health", (short)this.rekOrbHealth);
        compound.setShort("Age", (short)this.rekOrbAge);
        compound.setShort("Value", (short)this.reknownValue);
		
	}
	
	public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        if (!this.worldObj.isRemote)
        {
            if (this.delayBeforeCanPickup == 0)
            {
                if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new PlayerPickupReknownEvent(entityIn, this))) return;
                this.worldObj.playSound((EntityPlayer)null, entityIn.posX, entityIn.posY, entityIn.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_TOUCH, SoundCategory.PLAYERS, 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
                entityIn.onItemPickup(this, 1);

                if (this.reknownValue > 0)
                {
                	ModPlayer player = new ModPlayer(entityIn);
                	NBTTagCompound nbt = new NBTTagCompound();
                    player.addReknown(reknownValue);
                }

                this.setDead();
            }
        }
    }

    private int durabilityToXp(int durability)
    {
        return durability / 2;
    }

    private int xpToDurability(int xp)
    {
        return xp * 2;
    }

    /**
     * Returns the XP value of this XP orb.
     */
    public int getReknownValue()
    {
        return this.reknownValue;
    }

    /**
     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderReknownOrb to determine
     * what texture to use.
     */
    @SideOnly(Side.CLIENT)
    public int getTextureByXP()
    {
        return this.reknownValue >= 2477 ? 10 : (this.reknownValue >= 1237 ? 9 : (this.reknownValue >= 617 ? 8 : (this.reknownValue >= 307 ? 7 : (this.reknownValue >= 149 ? 6 : (this.reknownValue >= 73 ? 5 : (this.reknownValue >= 37 ? 4 : (this.reknownValue >= 17 ? 3 : (this.reknownValue >= 7 ? 2 : (this.reknownValue >= 3 ? 1 : 0)))))))));
    }

    /**
     * Get a fragment of the maximum experience points value for the supplied value of experience points value.
     */
    public static int getXPSplit(int reknownValue)
    {
        return reknownValue >= 2477 ? 2477 : (reknownValue >= 1237 ? 1237 : (reknownValue >= 617 ? 617 : (reknownValue >= 307 ? 307 : (reknownValue >= 149 ? 149 : (reknownValue >= 73 ? 73 : (reknownValue >= 37 ? 37 : (reknownValue >= 17 ? 17 : (reknownValue >= 7 ? 7 : (reknownValue >= 3 ? 3 : 1)))))))));
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem()
    {
        return false;
    }

}
