package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Vengeance extends MobModifier
{
    private static float reflectMultiplier;
    private static float maxReflectDamage;

    public MM_Vengeance(EntityLivingBase mob)
    {
        this.modName = "Vengeance";
    }

    public MM_Vengeance(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Vengeance";
        this.nextMod = prevMod;
    }

    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null && source.getEntity() != mob && !InfernalMobsCore.instance().isInfiniteLoop(mob, source.getEntity()))
        {
            if (maxReflectDamage <= 0.0f)
            {
                source.getEntity().attackEntityFrom(DamageSource.causeMobDamage(mob),
                        InfernalMobsCore.instance().getLimitedDamage(Math.max(damage * reflectMultiplier, 1)));
            }
            else
            {
                source.getEntity().attackEntityFrom(DamageSource.causeMobDamage(mob),
                        Math.min(maxReflectDamage, InfernalMobsCore.instance().getLimitedDamage(Math.max(damage * reflectMultiplier, 1))));
            }
        }

        return super.onHurt(mob, source, damage);
    }

    public static void loadConfig(Configuration config)
    {
        reflectMultiplier = (float) config.get(MM_Vengeance.class.getSimpleName(), "vengeanceMultiplier", 0.5D, "Multiplies damage received, result is subtracted from attacking entity's health").getDouble(0.5D);
        maxReflectDamage= (float) config.get(MM_Vengeance.class.getSimpleName(), "vengeanceMaxDamage", 0.0D, "Maximum amount of damage that is reflected (0, or less than zero for unlimited vengeance damage)").getDouble(0.0D);
    }

    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }

    private static String[] suffix = { "ofRetribution", "theThorned", "ofStrikingBack" };

    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }

    private static String[] prefix = { "thorned", "thorny", "spiky" };

}
