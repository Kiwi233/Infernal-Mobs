package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Bulwark extends MobModifier
{
    private static float damageMultiplier;

    public MM_Bulwark(EntityLivingBase mob)
    {
        this.modName = "Bulwark";
    }
    
    public MM_Bulwark(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Bulwark";
        this.nextMod = prevMod;
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        return super.onHurt(mob, source, Math.max(damage * damageMultiplier, 1));
    }

    public static void loadConfig(Configuration config)
    {
        damageMultiplier = (float) config.get(MM_Bulwark.class.getSimpleName(), "damageMultiplier", 0.5D, "Damage (taken) multiplier, only makes sense for values < 1.0").getDouble(0.5D);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { "ofTurtling", "theDefender", "ofeffingArmor" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { "turtling", "defensive", "armoured" };
    
}
