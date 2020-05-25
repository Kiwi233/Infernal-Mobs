package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Wither extends MobModifier
{
    private static int potionDuration;

    public MM_Wither(EntityLivingBase mob)
    {
        this.modName = "Wither";
    }
    
    public MM_Wither(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Wither";
        this.nextMod = prevMod;
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null
        && (source.getEntity() instanceof EntityLivingBase)
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(source.getEntity())
        && !(source instanceof EntityDamageSourceIndirect)
        && !source.isProjectile())
        {
            ((EntityLivingBase)source.getEntity()).addPotionEffect(new PotionEffect(Potion.wither.id, potionDuration, 0));
        }
        
        return super.onHurt(mob, source, damage);
    }
    
    @Override
    public float onAttack(EntityLivingBase entity, DamageSource source, float damage)
    {
        if (entity != null
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(entity))
        {
            entity.addPotionEffect(new PotionEffect(Potion.wither.id, potionDuration, 0));
        }
        
        return super.onAttack(entity, source, damage);
    }

    public static void loadConfig(Configuration config)
    {
        potionDuration = config.get(MM_Wither.class.getSimpleName(), "witherDurationTicks", 120L, "Time attacker is withered").getInt(120);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofDarkSkulls", "Doomskull" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "withering" };
    
}
