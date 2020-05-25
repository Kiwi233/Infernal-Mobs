package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Poisonous extends MobModifier
{
    private static int potionDuration;

    public MM_Poisonous(EntityLivingBase mob)
    {
        this.modName = "Poisonous";
    }
    
    public MM_Poisonous(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Poisonous";
        this.nextMod = prevMod;
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null
        && (source.getEntity() instanceof EntityLivingBase)
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(source.getEntity()))
        {
            EntityLivingBase ent = (EntityLivingBase)source.getEntity();
            if (!ent.isPotionActive(Potion.poison)
            && !(source instanceof EntityDamageSourceIndirect)
            && !source.isProjectile())
            {
                ent.addPotionEffect(new PotionEffect(Potion.poison.id, potionDuration, 0));
            }
        }
        
        return super.onHurt(mob, source, damage);
    }
    
    @Override
    public float onAttack(EntityLivingBase entity, DamageSource source, float damage)
    {
        if (entity != null
        && InfernalMobsCore.instance().getIsEntityAllowedTarget(entity)
        && !entity.isPotionActive(Potion.poison))
        {
            entity.addPotionEffect(new PotionEffect(Potion.poison.id, potionDuration, 0));
        }
        
        return super.onAttack(entity, source, damage);
    }

    public static void loadConfig(Configuration config)
    {
        potionDuration = config.get(MM_Poisonous.class.getSimpleName(), "poisonDurationTicks", 120L, "Time attacker is poisoned").getInt(120);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofVenom", "thedeadlyChalice" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "poisonous", "stinging", "despoiling" };
    
}
