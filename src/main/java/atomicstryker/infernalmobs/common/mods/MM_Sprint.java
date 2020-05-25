package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Sprint extends MobModifier
{
    private long nextAbilityUse = 0L;
    private static long coolDown;
    private boolean sprinting;

    public MM_Sprint(EntityLivingBase mob)
    {
        this.modName = "Sprint";
    }
    
    public MM_Sprint(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Sprint";
        this.nextMod = prevMod;
    }

    @Override
    public boolean onUpdate(EntityLivingBase mob)
    {
        if (getMobTarget() != null)
        {
            long time = System.currentTimeMillis();
            if (time > nextAbilityUse)
            {
                nextAbilityUse = time+coolDown;
                sprinting = !sprinting;
            }
            
            if (sprinting)
            {
                doSprint(mob);
            }
        }
        
        return super.onUpdate(mob);
    }
    
    private double modMotionX;
    private double modMotionZ;
    
    private void doSprint(EntityLivingBase mob)
    {
        float rotationMovement = (float)((Math.atan2(mob.motionX, mob.motionZ) * 180D) / 3.1415D);
        float rotationLook = mob.rotationYaw;
        
        // god fucking dammit notch
        if(rotationLook > 360F)
        {
            rotationLook -= (rotationLook % 360F) * 360F;
        }
        else if(rotationLook < 0F)
        {
            rotationLook += ((rotationLook * -1) % 360F) * 360F;
        }
        
        // god fucking dammit, NOTCH
        if (Math.abs(rotationMovement+rotationLook) > 10F)
        {
            rotationLook -= 360F;
        }
        
        double entspeed = GetAbsSpeed(mob);
        
        // unfuck velocity lock
        if (Math.abs(rotationMovement+rotationLook) > 10F)
        {
            modMotionX = mob.motionX;
            modMotionZ = mob.motionZ;
        }
        
        if (entspeed < 0.3D)
        {
            if (GetAbsModSpeed() > 0.6D || !(mob.onGround))
            {
                modMotionX /= 1.55;
                modMotionZ /= 1.55;
            }
        
            modMotionX *= 1.5;
            mob.motionX = modMotionX;
            modMotionZ *= 1.5;
            mob.motionZ = modMotionZ;
        }
    }
    
    private double GetAbsSpeed(EntityLivingBase ent)
    {
        return Math.sqrt(ent.motionX*ent.motionX + ent.motionZ*ent.motionZ);
    }
    
    private double GetAbsModSpeed()
    {
        return Math.sqrt(modMotionX*modMotionX + modMotionZ*modMotionZ);
    }
    
    public static void loadConfig(Configuration config)
    {
        coolDown = config.get(MM_Sprint.class.getSimpleName(), "coolDownMillis", 5000L, "Time between ability uses").getInt(5000);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofBolting", "theSwiftOne", "ofbeinginyourFace" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "sprinting", "swift", "charging" };
    
}
