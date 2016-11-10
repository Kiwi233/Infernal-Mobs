package atomicstryker.minions.common.network;

import atomicstryker.minions.client.MinionsClient;
import atomicstryker.minions.common.MinionsCore;
import atomicstryker.minions.common.network.NetworkHelper.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RequestXPSettingPacket implements IPacket
{
    
    public RequestXPSettingPacket() {}
    
    private int setting;
    
    public RequestXPSettingPacket(int a)
    {
        setting = a;
    }

    @Override
    public void writeBytes(ChannelHandlerContext ctx, ByteBuf bytes)
    {
        bytes.writeInt(setting);
    }

    @Override
    public void readBytes(ChannelHandlerContext ctx, ByteBuf bytes)
    {
        setting = bytes.readInt();
        FMLClientHandler.instance().getClient().addScheduledTask(new ScheduledCode());
    }
    
    class ScheduledCode implements Runnable
    {

        @Override
        public void run()
        {
            if (MinionsCore.instance.evilDeedXPCost != setting)
            {
                MinionsCore.instance.evilDeedXPCost = setting;
                MinionsClient.onChangedXPSetting();
            }
        }
    }

}
