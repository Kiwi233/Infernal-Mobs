package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Weakness extends MobModifier
{
    private static int potionDuration;

    public MM_Weakness(EntityLivingBase mob)
    {
        this.modName = "Weakness";
    }
    
    public MM_Weakness(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Weakness";
        this.nextMod = prevMod;
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null
        && (source.getEntity() instanceof EntityLivingBase)
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(source.getEntity()))
        {
            ((EntityLivingBase)source.getEntity()).addPotionEffect(new PotionEffect(Potion.weakness.id, potionDuration, 0));
        }
        
        return super.onHurt(mob, source, damage);
    }
    
    @Override
    public float onAttack(EntityLivingBase entity, DamageSource source, float damage)
    {
        if (entity != null
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(entity))
        {
            entity.addPotionEffect(new PotionEffect(Potion.weakness.id, potionDuration, 0));
        }
        
        return super.onAttack(entity, source, damage);
    }

    public static void loadConfig(Configuration config)
    {
        potionDuration = config.get(MM_Weakness.class.getSimpleName(), "weaknessDurationTicks", 120L, "Time attacker is weakened").getInt(120);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofApathy", "theDeceiver" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "apathetic", "deceiving" };
}
