package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Fiery extends MobModifier
{
    private static int fireDuration;

    public MM_Fiery(EntityLivingBase mob)
    {
        this.modName = "Fiery";
    }
    
    public MM_Fiery(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Fiery";
        this.nextMod = prevMod;
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null
        && (source.getEntity() instanceof EntityLivingBase)
        && !(source instanceof EntityDamageSourceIndirect))
        {
            ((EntityLivingBase)source.getEntity()).setFire(fireDuration);
        }
        
        mob.extinguish();
        return super.onHurt(mob, source, damage);
    }
    
    @Override
    public float onAttack(EntityLivingBase entity, DamageSource source, float damage)
    {
        if (entity != null)
        {
            entity.setFire(fireDuration);
        }
        
        return super.onAttack(entity, source, damage);
    }

    public static void loadConfig(Configuration config)
    {
        fireDuration = config.get(MM_Fiery.class.getSimpleName(), "fieryDurationSecs", 3L, "Time attacker is set on fire").getInt(3);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofConflagration", "thePhoenix", "ofCrispyness" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "burning", "toasting" };
    
}
