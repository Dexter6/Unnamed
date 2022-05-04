package Cat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;


class CatPacketHandler extends ChannelDuplexHandler {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private EntityPlayerSP player = mc.player;
	private WorldClient world = mc.world;
	
    public CatPacketHandler() {
    	try { mc.getConnection().getNetworkManager().channel().pipeline().addBefore("packet_handler", "PacketHandler", this); } catch (Exception e) {}
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (onPacket(packet, "IN")) super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (onPacket(packet, "OUT")) super.write(ctx, packet, promise);
    }
    
    private boolean onPacket(Object packet, String side) {
        if(!CatFrame.isStopped) {
        	try {
            	if (world != null && !CatFrame.isTempStopped) {
            		
            		if(CatFrame.noBackState && CatFrame.GSDisAcitive) {
            			this.player.rotationPitch = this.player.prevRotationPitch;
                		this.player.rotationYaw = this.player.prevRotationYaw;
            		}
            		if(side.equals("IN")) {
            			if(CatFrame.AntiKnockBackState) {
            				if(packet instanceof SPacketExplosion) {
                				return false;
            	    		}
            	    		if (packet instanceof SPacketEntityVelocity) {
            	    			return false;
            	    		}
            			}
            		}else {
            			
                		if(CatFrame.noFallState && this.player.fallDistance > 2.5F && packet instanceof CPacketPlayer) {
                			CatFrame.setPrivateValue(CPacketPlayer.class, (CPacketPlayer) packet, true, "onGround", "field_149474_g");
                		}
                		if(CatFrame.FreecamState && packet instanceof CPacketPlayer) {
                			return false;
                		}
            		}
                }
    		} catch (Exception e) {}
        }
        return true;
    }
}
