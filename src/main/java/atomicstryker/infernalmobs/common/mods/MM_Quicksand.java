package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Quicksand extends MobModifier
{
    public MM_Quicksand(EntityLivingBase mob)
    {
        this.modName = "Quicksand";
    }
    
    public MM_Quicksand(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Quicksand";
        this.nextMod = prevMod;
    }
    
    int ticker = 0;
    
    @Override
    public boolean onUpdate(EntityLivingBase mob)
    {
        if (getMobTarget() != null
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(getMobTarget())
        && mob.canEntityBeSeen(getMobTarget())
        && ++ticker >= 80)
        {
            ticker = 0;
            getMobTarget().addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 0));
        }
        
        return super.onUpdate(mob);
    }

    public static void loadConfig(Configuration config)
    {
        // NOOP by default
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofYouCantRun", "theSlowingB" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "slowing", "Quicksand" };
    
}
