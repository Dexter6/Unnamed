package Cat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.binary.Base64;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Timer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IGenericEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.crypto.Cipher;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

@SideOnly(Side.CLIENT)
@Mod(modid = "unnamed")
public class CatFrame extends JFrame implements Runnable {
	@Mod.EventHandler
	public void CatLoader() {
		 new CatFrame();
	}
	
	private Minecraft mc = Minecraft.getMinecraft();
	private EntityPlayerSP player() {
		return mc.player;
	}
	private PlayerControllerMP playerController() {
		return mc.playerController;
	}
	private WorldClient world() {
		return mc.world;
	}
	
	protected static volatile boolean isStopped;
	
	protected static volatile boolean isTempStopped;
	protected static long tempStopTime;
	
	private boolean isLoaded;
	
    private JSlider sleepTime;
    
    private JLabel sleeptimeLabel;

	
	private JTextArea infoMap;
	
	private JCheckBox ReverseDistance;
	
    private JCheckBox playerNametag;
    private JCheckBox mobNametag;
    private Map<EntityLivingBase, double[]> entityPositions = new HashMap();
    private boolean isRegistered;
	
	private JTextField outputMessage;

	
	
	private JCheckBox up;
	
	private JCheckBox down;
	
	private JCheckBox noFall;
	protected static boolean noFallState;
	
	private JCheckBox Sprint;
	
	private JCheckBox AntiPush;
	private boolean AntiPushState;
	
	private JCheckBox AntiKnockBack;
	protected static boolean AntiKnockBackState;
	
	private JCheckBox Freecam;
	protected static boolean FreecamState;
	private EntityOtherPlayerMP freecamEnt = null;
	private double x,y,z = 0;
	private GameType TYPE = GameType.SURVIVAL;
	
	
	
	private JCheckBox NoFov;
	
	private JCheckBox fastPlace;
	
	
	
	private JCheckBox noBack;
	protected static boolean noBackState;
	
	
	
	private JCheckBox GSDMode;
	
	private BetterJButton GSDModeKey;
	
	private JSlider GSDRange;
	private JLabel GSDRangeLabel;
	
	private JSlider GSDFOV;
	private JLabel GSDFovLabel;
	
	private JSlider GSDAimFov;
	private JLabel GSDAimFovLabel;
	
	private JSlider GSDPredict;
	private JLabel GSDPredictLabel;
	
	private JCheckBox GSDAimPlayers;
	
	private JCheckBox GSDAimMobs;
	
	private JCheckBox GSDDeathOff;
	
	private JCheckBox GSDThroughWall;
	
	private JCheckBox GSDPreferHealth;
	protected static boolean GSDisAcitive;
	private EntityLivingBase entity = null;
    private EntityLivingBase sortEntity = null;
    private Map<EntityPlayer, List<Vec3i>> playerPositions = new HashMap<>();
    private int playerpath = 16;
    
    
    private JCheckBox KillAura;
    
    private BetterJButton KAKey;
    
    private JSlider KARange;
    private JLabel KARangeLabel;
    
    private JSlider KADelay;
    private JLabel KADelayLabel;
    
    private JSlider KACount;
    private JLabel KACountLabel;
    
    private JCheckBox KAAttackPlayer;
    
    private JCheckBox KAAttackMob;
    
    private JCheckBox KADeathOff;
    private ArrayList<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
    private long attackLastMs;

    
    private JCheckBox DynamicFly;
    
    private BetterJButton DynamicFlyKey;
    
    private JSlider flySpeed;
    private JLabel flySpeedLabel;
    
    private JSlider UDDistance;
    private JLabel UDDistanceLabel;
    
    private JCheckBox ChestStealer;
    
    private JSlider takeDelay;
    private JLabel takeDelayLabel;
    private long chestLastMs;
    
    private JCheckBox SpeedForce;
    
    private BetterJButton SpeedForceKey;
    
    private JSlider SPSpeed;
    private JLabel speedLabel;
    private boolean SpeedForceState;
    

    
	private int count = 0;
	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		while(true) {
			try {
				thread.sleep(100);
				if(isLoaded && Minecraft.getMinecraft() != null) {
					break;
				}
			} catch (Exception e) {}
		}
		while(true) {
			if(isTempStopped) {
				if(System.currentTimeMillis() - tempStopTime > 6000) {
					try { setVisible(true); } catch (Exception e) {}
					isTempStopped = false;
					continue;
				}
				try { setVisible(false); } catch (Exception e) {}
				try { thread.sleep(1000); } catch (Exception e) {}
				continue;
			}

			if(isStopped = !isVisible()) {
				break;
			}
			long time = sleepTime.getValue();
			sleeptimeLabel.setText("休眠间隔(越低执行速度越快)[0是智能]: " + time);
			try {
				if(count++ > 100) {
					count = 0;
					new CatPacketHandler();
				}
				if(world() != null && player() != null) {
					try {
						long count = 1000 / (mc.getDebugFPS() + 1);
						if(count > 7) {
							count = time;
						}
						thread.sleep(count);
					} catch (Exception e) {
						thread.sleep(7);
					}
					onTick();
				}else {
					thread.sleep(100);
				}
			} catch (Exception e) {}
		}
		thread.stop();
	}
	
	private boolean[] keyStates = new boolean[256];
	
	private boolean checkAndSaveKeyState(int key)
    {
        return mc.currentScreen != null ? false : (Keyboard.isKeyDown(key) != this.keyStates[key] ? (this.keyStates[key] = !this.keyStates[key]) : false);
    }
	
	private void onTick() {
		
		if(checkAndSaveKeyState(GSDModeKey.getKey())) {
			GSDMode.setSelected(!GSDMode.isSelected());
		}else if(checkAndSaveKeyState(KAKey.getKey())) {
			KillAura.setSelected(!KillAura.isSelected());
		}else if(checkAndSaveKeyState(DynamicFlyKey.getKey())) {
			DynamicFly.setSelected(!DynamicFly.isSelected());
		}else if(checkAndSaveKeyState(SpeedForceKey.getKey())) {
			SpeedForce.setSelected(!SpeedForce.isSelected());
		}
		
		
		infoMap.setText(getPlayersMapInfo(ReverseDistance.isSelected()));
		
		
		if(up.isSelected()) {
			up.setSelected(false);
			boolean Success = false;
			boolean isBlock = false;
	    	for(int downblock = 1; downblock < 10; downblock++) {
	    		Block blockUp = getBlock(player().posX, player().posY + downblock, player().posZ);
	    		if(isBlock) {
	    			
	                if (blockUp instanceof BlockAir && getBlock(player().posX, player().posY + downblock + 1, player().posZ) instanceof BlockAir)
	                {
	                	player().setPosition(player().posX, player().posY + downblock, player().posZ);
	                	Success = true;
	                	break;
	                }
	    		}else {
	    			if(!(blockUp instanceof BlockAir)) {
	    				isBlock = true;
	    			}
	    		}
	    	}
	    	if(!Success) {
	    		message("该方块上面没有上升空间！");
	    	}
		}
		
		
		if(down.isSelected()) {
			down.setSelected(false);
			boolean Success = false;
			for(int downblock = 1; downblock < 10; downblock++) {
				Block blockDown = getBlock(player().posX, player().posY + downblock, player().posZ);
	    		
	            if (blockDown instanceof BlockAir && getBlock(player().posX, player().posY + downblock + 1, player().posZ) instanceof BlockAir)
	            {
	            	player().setPosition(player().posX, player().posY - downblock - 1, player().posZ);
	            	Success = true;
                	break;
	            }
	    	}
	    	if(!Success) {
	    		message("该方块底下没有下降空间！");
	    	}
		}
		
		
		if(Sprint.isSelected() && mc.gameSettings.keyBindForward.isKeyDown()) {
			try {
				player().setSprinting(true);
			} catch (Exception e) {}
		}
		
		
		if(AntiPush.isSelected()) {
			player().entityCollisionReduction = 1;
		} else if(!AntiPush.isSelected() && AntiPushState){
			player().entityCollisionReduction = 0;
		}
		
		
		if(Freecam.isSelected() && !FreecamState) {
			TYPE = playerController().getCurrentGameType();
	    	
	    	playerController().setGameType(GameType.CREATIVE);
	    	x = player().posX;
	    	y = player().posY;
	    	z = player().posZ;
			freecamEnt = new EntityOtherPlayerMP(world(), player().getGameProfile());
	        freecamEnt.setPosition(player().posX, player().posY, player().posZ);
	        freecamEnt.inventory = player().inventory;
	        freecamEnt.rotationPitch = player().rotationPitch;
	        freecamEnt.rotationYaw = player().rotationYaw;
	        freecamEnt.rotationYawHead = player().rotationYawHead;
	        world().spawnEntity(freecamEnt);
		}else if(Freecam.isSelected()) {
			player().capabilities.isFlying = false;

	    	player().jumpMovementFactor = 1F;
			player().motionX = 0.0;
			player().motionY = 0.0;
			player().motionZ = 0.0;
			player().jumpMovementFactor *= 2F;
	    	if(mc.gameSettings.keyBindJump.isKeyDown()) {
	    		player().motionY = 1D;
	    	}
	    	if(mc.gameSettings.keyBindSneak.isKeyDown()) {
	    		player().motionY = -1D;
	    	}
		}else if(!Freecam.isSelected() && FreecamState) {
			player().setPosition(x, y, z);
			
	    	if (freecamEnt != null) {
	            world().removeEntity(freecamEnt);
	            freecamEnt = null;
	        }
	    	
	    	playerController().setGameType(TYPE);
		}
		
		
		if(NoFov.isSelected()) {
			try {
				player().hurtTime = 0;
			} catch (Exception e) {}
		}
		
		
		if(fastPlace.isSelected() && mc.gameSettings.keyBindUseItem.isKeyDown()) {
			try {
                Method e = findMethod(Minecraft.class, mc, new String[] {"func_147121_ag", "rightClickMouse"});
                e.invoke(mc, new Object[0]);
            }
            catch (Exception e){}
		}
		
		
		if(noBack.isSelected()) {
			player().rotationPitch = player().prevRotationPitch;
    		player().rotationYaw = player().prevRotationYaw;
		}
		
		
		double aimHeight = ((double)GSDAimFov.getValue() / 100);

		GSDRangeLabel.setText("瞄准距离: " + GSDRange.getValue());
		GSDFovLabel.setText("瞄准角度: " + GSDFOV.getValue());
		GSDAimFovLabel.setText("自瞄位置: " + aimHeight);
		GSDPredictLabel.setText("位置预判: " + GSDPredict.getValue());
		GSDisAcitive = true;
		if(GSDMode.isSelected()) {
			if(GSDDeathOff.isSelected() && player().getHealth() <= 0) {
				GSDMode.setSelected(false);
			}else {
				entity = null;
	        	sortEntity = null;
	        	if(!Keyboard.isKeyDown(Keyboard.KEY_LMENU) && !player().inventory.getCurrentItem().isEmpty()) {
	        		ArrayList<EntityLivingBase> playerList = new ArrayList<>();
	        		ArrayList<EntityLivingBase> mobList = new ArrayList<>();
	        		for(Entity e : world().loadedEntityList) {
	        			if(e instanceof EntityLivingBase && e != player()) {
	        				EntityLivingBase base = (EntityLivingBase) e;
	                		
	                		
	                		boolean attackCanBeSeen = e instanceof EntityPlayer ? (((base.getEntityBoundingBox().maxY - base.getEntityBoundingBox().minY) < 1.6) ? true : player().canEntityBeSeen(base)) : true;
	                		
	                		
	                		boolean throughWallAtk = GSDThroughWall.isSelected() ? true : attackCanBeSeen;
	                		float playerFOV = Math.abs((float)((Math.atan2(base.posZ - player().posZ, base.posX - player().posX) * 180.0 / 3.141592653589793) - 90) - player().rotationYaw) % 360.0F;
	                        if (player().getDistance(base) < GSDRange.getValue() && 
	                        	base.getHealth() > 0 && 
	                        	!base.isInvisible() && throughWallAtk &&
	                        	((playerFOV > 180.0F ? (360.0F - playerFOV) : playerFOV) <= GSDFOV.getValue())) 
	                        {
	                        	if(e instanceof EntityPlayer) {
	                        		if(GSDAimPlayers.isSelected()) {
	                        			playerList.add(base);
	                        		}
	                        	}else {
	                        		if(GSDAimMobs.isSelected()) {
	                        			mobList.add(base);
	                        		}
	                        	}
	                        }
	        			}
	                }
	        		
	        		
	        		if(GSDAimPlayers.isSelected() && !playerList.isEmpty()) {
	        			playerList.sort(GSDPreferHealth.isSelected() ? Comparator.comparingDouble(player -> player.getHealth()) : Comparator.comparingDouble(player -> player().getDistance(player)));
	        			sortEntity = playerList.get(0);
	        		}else if(GSDAimMobs.isSelected() && !mobList.isEmpty()) {
	        			mobList.sort(GSDPreferHealth.isSelected() ? Comparator.comparingDouble(mob -> mob.getHealth()) : Comparator.comparingDouble(mob -> player().getDistance(mob)));
	        			sortEntity = mobList.get(0);
	        		}
	        		
	        		
	        		if(GSDAimPlayers.isSelected() && !GSDAimMobs.isSelected() && sortEntity != null && sortEntity instanceof EntityPlayer) {
	        			try {
	                		for (EntityPlayer player : playerPositions.keySet()) {
	                            if (!playerPositions.isEmpty() && !world().playerEntities.contains(player)) {
	                                playerPositions.remove(player);
	                            }
	                        }
	                		EntityPlayer player = (EntityPlayer) sortEntity;
	                    	playerPositions.putIfAbsent(player, new ArrayList<Vec3i>());
	                        List<Vec3i> previousPositions = playerPositions.get(player);
	                        previousPositions.add(new Vec3i(player.posX, player.posY, player.posZ));
	                        if (previousPositions.size() > playerpath) {
	                            int i = 0;
	                            for (Vec3i position : new ArrayList<Vec3i>(previousPositions)) {
	                                if (i < previousPositions.size() - playerpath) {
	                                    previousPositions.remove(previousPositions.get(i));
	                                }
	                                ++i;
	                            }
	                        }
	                        
	                        if (this.playerPositions.containsKey(player)) {
	                            List<Vec3i> previousPositions1 = this.playerPositions.get(player);
	                            if (previousPositions1.size() > 0) {
	                                Vec3i origin = previousPositions1.get(0);
	                                List<Vec3i> deltas = new ArrayList<Vec3i>();
	                                Vec3i previous = origin;
	                                for (Vec3i position : previousPositions1) {
	                                    deltas.add(new Vec3i(position.getX() - previous.getX(), position.getY() - previous.getY(), position.getZ() - previous.getZ()));
	                                    previous = position;
	                                }
	                                double x = 0.0;
	                                double y = 0.0;
	                                double z = 0.0;
	                                for (Vec3i delta : deltas) {
	                                    x += delta.getX();
	                                    y += delta.getY();
	                                    z += delta.getZ();
	                                }
	                                x /= deltas.size();
	                                y /= deltas.size();
	                                z /= deltas.size();
	                                EntityPlayer simulated = new EntityOtherPlayerMP(world(), player.getGameProfile());
	                                simulated.noClip = false;
	                                simulated.setPosition(player.posX, player.posY, player.posZ);
	                                for (int i = 0; i < GSDPredict.getValue(); ++i) {
	                                    simulated.move(MoverType.PLAYER, x, y, z);
	                                }
	                                simulated.setEntityBoundingBox(new AxisAlignedBB(simulated.getEntityBoundingBox().minX, player.getEntityBoundingBox().minY, simulated.getEntityBoundingBox().minZ, simulated.getEntityBoundingBox().maxX, player.getEntityBoundingBox().maxY, simulated.getEntityBoundingBox().maxZ));
	                                entity = simulated;
	                                GSDisAcitive = false;
	            	            	faceEntity(entity, aimHeight);
	                            }
	                        }else {
	                            entity = player;
	                            GSDisAcitive = false;
	        	            	faceEntity(entity, aimHeight);
	                        }
	    				} catch (Exception e) {}
	        		}else if(GSDAimMobs.isSelected() && sortEntity != null) {
	        			entity = sortEntity;
		            	faceEntity(entity, aimHeight);
	        		}
	        	}
	        	if(entity != null) {
	        		GSDisAcitive = false;
	            	faceEntity(entity, aimHeight);
	        	}
			}
		}
		
		
		double KARangeTemp = ((double)KARange.getValue() / 10);
		double KADelayTemp = ((double)KADelay.getValue() / 10);

		KARangeLabel.setText("攻击距离: " + KARangeTemp);
		KADelayLabel.setText("攻击延迟(0是智能): " + KADelayTemp);
		KACountLabel.setText("攻击数量: " + KACount.getValue());
		if(KillAura.isSelected()) {
			if(KADeathOff.isSelected() && player().getHealth() <= 0) {
				KillAura.setSelected(false);
			}else {
				
				boolean timeResult = false;
				if (System.currentTimeMillis() - attackLastMs >= KADelayTemp * 1000) {
					attackLastMs = System.currentTimeMillis();
		        	timeResult = true;
		        }
				boolean attackDeleyIsOK = KADelayTemp == 0 ? player().getCooledAttackStrength(0.5F) == 1F : timeResult;

		    	entities.clear();
		    	if(!player().isDead) {
		    		
		    		for(Entity e : world().loadedEntityList) {
		                
		                if(e instanceof EntityLivingBase && e != player()){
		                	EntityLivingBase base = (EntityLivingBase) e;
		            		
		            		
		                	if(base.getHealth() > 0 && 
		                	   player().getDistance(base) < KARangeTemp && 
		                	   player().canEntityBeSeen(base) && !base.isInvisible())
		                	{
		                		if(e instanceof EntityPlayer) {
			                		if(KAAttackPlayer.isSelected()) {
			                			entities.add(base);
			                		}
		                		}else {
		                			if(KAAttackMob.isSelected()) {
			                			entities.add(base);
			                		}
		                		}
		                	}
		                }
		    		}
		    		if(!entities.isEmpty()) {
		    			entities.sort(Comparator.comparingDouble(entity -> player().getDistance(entity)));
		    			for(int i = 0; i < KACount.getValue(); i++) {
		    				
	                    	if(attackDeleyIsOK) {
			    				EntityLivingBase entity = entities.get(i);
	                            playerController().attackEntity(player(), entity);
	                            player().swingArm(EnumHand.MAIN_HAND);
	                    	}
		    			}
		    		}
		    	}
			}
		}
		
		
		double flySpeedTemp = ((double)flySpeed.getValue() / 100);
		double UDDistanceTemp = ((double)UDDistance.getValue() / 10);
		flySpeedLabel.setText("飞行速度: " + flySpeedTemp);
		UDDistanceLabel.setText("升降距离: " + UDDistanceTemp);
		if(DynamicFly.isSelected()) {
			player().capabilities.isFlying = false;
	    	
	    	
	    	if(!player().onGround) {
	    		player().jumpMovementFactor = 1f;
	    		player().motionX = 0.0;
	    		player().motionY = 0.0;
	    		player().motionZ = 0.0;
	    		player().jumpMovementFactor *= flySpeedTemp;
	    	}
	    	
	    	player().motionY -= 0.03126;
	    	
	    	
	        if(mc.gameSettings.keyBindSneak.isKeyDown()) {
	        	player().motionY -= UDDistanceTemp;
	        }
	        
	        
	        if(mc.gameSettings.keyBindJump.isKeyDown()) {
	        	player().motionY += UDDistanceTemp;
	        }
		}
		
		
		double takeDelayTemp = ((double)takeDelay.getValue() / 10);
		takeDelayLabel.setText("拿取延迟: " + takeDelayTemp);
		if(ChestStealer.isSelected()) {
			
	        if (!mc.inGameHasFocus && mc.currentScreen instanceof GuiChest)
	        {
            	Container container = player().openContainer;
            	boolean isContainerEmpty = true;
                for (int i = 0; i < (container.inventorySlots.size() == 90 ? 54 : 27); ++i)
                {
                    if (container.getSlot(i).getHasStack())
                    {
                    	isContainerEmpty = false;
                    }
                }
	            if (!isContainerEmpty)
	            {
	            	int slotId = -1;
	            	int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
	                for (int i = 0; i < slotAmount; ++i)
	                {
	                    if (container.getInventory().get(i) != null && !container.getInventory().get(i).isEmpty())
	                    {
	                    	slotId = i;
	                    	break;
	                    }
	                }

					if (System.currentTimeMillis() - chestLastMs >= takeDelayTemp * 1000) {
						chestLastMs = System.currentTimeMillis();
						playerController().windowClick(player().openContainer.windowId, slotId, 1, ClickType.QUICK_MOVE, player());
			        }
	            }
	            else
	            {
	                player().closeScreen();
	            }
	        }
		}
		
		
		float SPSpeedTemp = ((float)SPSpeed.getValue() / 10);
		speedLabel.setText("速度: " + SPSpeedTemp);
		if(SpeedForce.isSelected()) {
			setSpeedForce(50 / SPSpeedTemp);
		}else if(!SpeedForce.isSelected() && SpeedForceState) {
			setSpeedForce(50);
		}
		

		
		noFallState = noFall.isSelected();
		AntiPushState = AntiPush.isSelected();
		AntiKnockBackState = AntiKnockBack.isSelected();
		FreecamState = Freecam.isSelected();
		noBackState = noBack.isSelected();
		SpeedForceState = SpeedForce.isSelected();

		
		
		if((playerNametag.isSelected() || mobNametag.isSelected()) && !isRegistered) {
			isRegistered = true;
	        for (Method method : getClass().getDeclaredMethods()) {
	        	for (Class<?> cls : TypeToken.of(getClass()).getTypes().rawTypes()) {
                    try {
                        if (cls.getDeclaredMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(SubscribeEvent.class)) {
                            Class<?> eventType = method.getParameterTypes()[0];
                            Constructor<?> ctr = eventType.getConstructor();
                            ctr.setAccessible(true);
                            ASMEventHandler asm = new ASMEventHandler(this, method, Loader.instance().getMinecraftModContainer(), IGenericEvent.class.isAssignableFrom(eventType));
                            ((Event)ctr.newInstance()).getListenerList().register((int)getPrivateValue(EventBus.class, MinecraftForge.EVENT_BUS, "busID"), asm.getPriority(), asm);
                            break;
                        }
                    } catch (Throwable e) {}
                }
	        }
		}
	}

	private CatFrame() {
		try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch (Exception e) {}

		

		setBounds(100, 100, 750, 471);
		JPanel protocolPane = new JPanel();
		protocolPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(protocolPane);
		protocolPane.setLayout(null);
		
		JCheckBox readingCheckBox = new JCheckBox("我已详细阅读并同意该协议");
		readingCheckBox.setFont(new Font("微软雅黑", Font.BOLD, 20));
		readingCheckBox.setBounds(212, 297, 315, 37);
		protocolPane.add(readingCheckBox);

		try { setVisible(true); } catch (Exception e) {}
		
		boolean prepareToRead = true;
		while(prepareToRead) {
			JLabel protocol = new JLabel("使用协议");
			protocol.setHorizontalAlignment(SwingConstants.CENTER);
			protocol.setFont(new Font("微软雅黑", Font.BOLD, 25));
			protocol.setBounds(10, 10, 714, 49);
			protocolPane.add(protocol);
			
			JLabel protocol1 = new JLabel("本产品为基于Minecraft Forge所制作的一个增加个人游玩游戏体验的模组");
			protocol1.setHorizontalAlignment(SwingConstants.CENTER);
			protocol1.setFont(new Font("微软雅黑", Font.BOLD, 20));
			protocol1.setBounds(10, 69, 714, 42);
			protocolPane.add(protocol1);
			
			JLabel protocol3 = new JLabel("请勿用于非法用途！否则后果使用者自负！");
			protocol3.setHorizontalAlignment(SwingConstants.CENTER);
			protocol3.setFont(new Font("微软雅黑", Font.BOLD, 20));
			protocol3.setBounds(10, 173, 714, 42);
			protocolPane.add(protocol3);
			
			JLabel protocol4 = new JLabel("在法律允许范围内，产品制作方保留对该产品的解释权！");
			protocol4.setHorizontalAlignment(SwingConstants.CENTER);
			protocol4.setFont(new Font("微软雅黑", Font.BOLD, 20));
			protocol4.setBounds(10, 225, 714, 42);
			protocolPane.add(protocol4);
			
			JLabel protocol2 = new JLabel("完全免费且已开源，仅供学习交流，下载后请24小时内删除");
			protocol2.setHorizontalAlignment(SwingConstants.CENTER);
			protocol2.setFont(new Font("微软雅黑", Font.BOLD, 20));
			protocol2.setBounds(10, 121, 714, 42);
			protocolPane.add(protocol2);

			prepareToRead = !readingCheckBox.isSelected();
			if(!prepareToRead) {
				try { protocolPane.setVisible(false); } catch (Exception e) {}
			}
		}

		

		setBounds(100, 100, 750, 471);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		tabbedPane.setBounds(0, 0, 734, 411);
		contentPane.add(tabbedPane);

		JPanel infoMapPanel = new JPanel();
		tabbedPane.addTab("信息地图", null, infoMapPanel, null);
		infoMapPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 729, 340);
		infoMapPanel.add(scrollPane);

		infoMap = new JTextArea();
		scrollPane.setViewportView(infoMap);
		infoMap.setWrapStyleWord(true);
		infoMap.setLineWrap(true);
		infoMap.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		infoMap.setEditable(false);
		
		ReverseDistance = new JCheckBox("距离显示反转");
		ReverseDistance.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		ReverseDistance.setBounds(10, 346, 113, 27);
		infoMapPanel.add(ReverseDistance);
		
		playerNametag = new JCheckBox("显示玩家名字标签");
		playerNametag.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		playerNametag.setBounds(586, 348, 137, 27);
		infoMapPanel.add(playerNametag);
		
		mobNametag = new JCheckBox("显示生物名字标签");
		mobNametag.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mobNametag.setBounds(447, 348, 137, 27);
		infoMapPanel.add(mobNametag);
		
		JPanel noParaModulesPanel = new JPanel();
		tabbedPane.addTab("无参数功能", null, noParaModulesPanel, null);
		noParaModulesPanel.setLayout(null);
		
		noBack = new JCheckBox("无后座力");
		noBack.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		noBack.setBounds(218, 43, 89, 27);
		noParaModulesPanel.add(noBack);
		
		Sprint = new JCheckBox("强制疾跑");
		Sprint.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		Sprint.setBounds(6, 130, 89, 27);
		noParaModulesPanel.add(Sprint);
		
		fastPlace = new JCheckBox("快速放置");
		fastPlace.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		fastPlace.setBounds(111, 72, 89, 27);
		noParaModulesPanel.add(fastPlace);
		
		noFall = new JCheckBox("无摔落");
		noFall.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		noFall.setBounds(6, 101, 89, 27);
		noParaModulesPanel.add(noFall);
		
		AntiPush = new JCheckBox("防推挤");
		AntiPush.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		AntiPush.setBounds(6, 159, 89, 27);
		noParaModulesPanel.add(AntiPush);
		
		AntiKnockBack = new JCheckBox("无击退");
		AntiKnockBack.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		AntiKnockBack.setBounds(6, 188, 89, 27);
		noParaModulesPanel.add(AntiKnockBack);
		
		up = new JCheckBox("向上穿墙");
		up.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		up.setBounds(6, 43, 89, 27);
		noParaModulesPanel.add(up);
		
		down = new JCheckBox("向下穿墙");
		down.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		down.setBounds(6, 72, 89, 27);
		noParaModulesPanel.add(down);
		
		NoFov = new JCheckBox("窗口不抖动");
		NoFov.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		NoFov.setBounds(111, 43, 105, 27);
		noParaModulesPanel.add(NoFov);
		
		JLabel MOVEMENT = new JLabel("移动类：");
		MOVEMENT.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		MOVEMENT.setBounds(6, 10, 61, 27);
		noParaModulesPanel.add(MOVEMENT);
		
		JLabel PLAYER = new JLabel("玩家类：");
		PLAYER.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		PLAYER.setBounds(111, 10, 61, 27);
		noParaModulesPanel.add(PLAYER);
		
		JLabel FIGHT = new JLabel("战斗类：");
		FIGHT.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		FIGHT.setBounds(220, 10, 61, 27);
		noParaModulesPanel.add(FIGHT);
		
		JLabel outPutMessageLabel = new JLabel("输出信息: ");
		outPutMessageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		outPutMessageLabel.setBounds(6, 342, 72, 27);
		noParaModulesPanel.add(outPutMessageLabel);
		
		outputMessage = new JTextField();
		outputMessage.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		outputMessage.setBounds(88, 342, 631, 27);
		noParaModulesPanel.add(outputMessage);
		outputMessage.setColumns(10);
		
		Freecam = new JCheckBox("灵魂出窍");
		Freecam.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		Freecam.setBounds(6, 217, 89, 27);
		noParaModulesPanel.add(Freecam);
		
		JPanel paraModulesPanel = new JPanel();
		tabbedPane.addTab("有参数功能", null, paraModulesPanel, null);
		paraModulesPanel.setLayout(null);
		
		GSDMode = new JCheckBox("自瞄");
		GSDMode.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDMode.setBounds(6, 6, 200, 27);
		paraModulesPanel.add(GSDMode);
		
		GSDModeKey = new BetterJButton();
		GSDModeKey.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDModeKey.addKeyListener(new KeyBindEvent(GSDModeKey));
		GSDModeKey.setBounds(6, 334, 200, 23);
		paraModulesPanel.add(GSDModeKey);
		
		GSDRange = new JSlider(1, 100, 1);
		GSDRange.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		GSDRange.setValue(100);
		GSDRange.setMinorTickSpacing(1);
		GSDRange.setMinimum(0);
		GSDRange.setBounds(6, 67, 200, 14);
		paraModulesPanel.add(GSDRange);
		
		GSDFOV = new JSlider(0, 90, 30);
		GSDFOV.setMinorTickSpacing(1);
		GSDFOV.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		GSDFOV.setBounds(6, 119, 200, 14);
		paraModulesPanel.add(GSDFOV);
		
		GSDRangeLabel = new JLabel("瞄准距离: ");
		GSDRangeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDRangeLabel.setBounds(6, 39, 200, 18);
		paraModulesPanel.add(GSDRangeLabel);
		
		GSDFovLabel = new JLabel("瞄准角度: ");
		GSDFovLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDFovLabel.setBounds(6, 91, 200, 18);
		paraModulesPanel.add(GSDFovLabel);
		
		GSDAimFovLabel = new JLabel("自瞄位置: ");
		GSDAimFovLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDAimFovLabel.setBounds(6, 143, 200, 18);
		paraModulesPanel.add(GSDAimFovLabel);
		
		GSDAimFov = new JSlider(0, 150, 5);
		GSDAimFov.setMinorTickSpacing(5);
		GSDAimFov.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		GSDAimFov.setBounds(6, 171, 200, 14);
		paraModulesPanel.add(GSDAimFov);
		
		GSDPredictLabel = new JLabel("位置预判: ");
		GSDPredictLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDPredictLabel.setBounds(6, 195, 200, 18);
		paraModulesPanel.add(GSDPredictLabel);
		
		GSDPredict = new JSlider(1, 50, 12);
		GSDPredict.setMinorTickSpacing(1);
		GSDPredict.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		GSDPredict.setBounds(6, 223, 200, 14);
		paraModulesPanel.add(GSDPredict);
		
		GSDAimPlayers = new JCheckBox("瞄准玩家");
		GSDAimPlayers.setSelected(true);
		GSDAimPlayers.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDAimPlayers.setBounds(6, 243, 95, 27);
		paraModulesPanel.add(GSDAimPlayers);
		
		GSDAimMobs = new JCheckBox("瞄准生物");
		GSDAimMobs.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDAimMobs.setBounds(111, 243, 95, 27);
		paraModulesPanel.add(GSDAimMobs);
		
		GSDDeathOff = new JCheckBox("死亡关闭本功能");
		GSDDeathOff.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDDeathOff.setBounds(6, 272, 131, 27);
		paraModulesPanel.add(GSDDeathOff);
		
		GSDThroughWall = new JCheckBox("隔墙瞄准");
		GSDThroughWall.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDThroughWall.setBounds(6, 301, 87, 27);
		paraModulesPanel.add(GSDThroughWall);
		
		GSDPreferHealth = new JCheckBox("优先低血量");
		GSDPreferHealth.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GSDPreferHealth.setBounds(111, 301, 95, 27);
		paraModulesPanel.add(GSDPreferHealth);
		
		KARange = new JSlider(29, 62, 55);
		KARange.setMinorTickSpacing(1);
		KARange.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		KARange.setBounds(216, 67, 200, 14);
		paraModulesPanel.add(KARange);
		
		KillAura = new JCheckBox("杀戮光环");
		KillAura.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KillAura.setBounds(216, 6, 200, 27);
		paraModulesPanel.add(KillAura);
		
		KAKey = new BetterJButton();
		KAKey.addKeyListener(new KeyBindEvent(KAKey));
		KAKey.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KAKey.setBounds(216, 256, 200, 23);
		paraModulesPanel.add(KAKey);
		
		KARangeLabel = new JLabel("攻击距离: ");
		KARangeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KARangeLabel.setBounds(216, 39, 200, 18);
		paraModulesPanel.add(KARangeLabel);
		
		KADelayLabel = new JLabel("攻击延迟(0是智能): ");
		KADelayLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KADelayLabel.setBounds(216, 91, 200, 18);
		paraModulesPanel.add(KADelayLabel);
		
		KADelay = new JSlider(0, 20, 0);
		KADelay.setMinorTickSpacing(1);
		KADelay.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		KADelay.setBounds(216, 119, 200, 14);
		paraModulesPanel.add(KADelay);
		
		KACountLabel = new JLabel("攻击数量: ");
		KACountLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KACountLabel.setBounds(216, 143, 200, 18);
		paraModulesPanel.add(KACountLabel);
		
		KACount = new JSlider(1, 20, 1);
		KACount.setMinorTickSpacing(1);
		KACount.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		KACount.setBounds(216, 171, 200, 14);
		paraModulesPanel.add(KACount);
		
		KAAttackPlayer = new JCheckBox("攻击玩家");
		KAAttackPlayer.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KAAttackPlayer.setBounds(216, 191, 95, 27);
		paraModulesPanel.add(KAAttackPlayer);
		
		KAAttackMob = new JCheckBox("攻击生物");
		KAAttackMob.setSelected(true);
		KAAttackMob.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KAAttackMob.setBounds(321, 191, 95, 27);
		paraModulesPanel.add(KAAttackMob);
		
		KADeathOff = new JCheckBox("死亡关闭本功能");
		KADeathOff.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		KADeathOff.setBounds(216, 223, 131, 27);
		paraModulesPanel.add(KADeathOff);
		
		flySpeed = new JSlider(50, 500, 225);
		flySpeed.setMinorTickSpacing(5);
		flySpeed.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		flySpeed.setBounds(426, 67, 200, 14);
		paraModulesPanel.add(flySpeed);
		
		DynamicFly = new JCheckBox("物理飞行");
		DynamicFly.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		DynamicFly.setBounds(426, 6, 156, 27);
		paraModulesPanel.add(DynamicFly);
		
		DynamicFlyKey = new BetterJButton();
		DynamicFlyKey.addKeyListener(new KeyBindEvent(DynamicFlyKey));
		DynamicFlyKey.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		DynamicFlyKey.setBounds(588, 8, 131, 23);
		paraModulesPanel.add(DynamicFlyKey);
		
		flySpeedLabel = new JLabel("飞行速度: ");
		flySpeedLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		flySpeedLabel.setBounds(426, 39, 200, 18);
		paraModulesPanel.add(flySpeedLabel);
		
		UDDistanceLabel = new JLabel("升降距离: ");
		UDDistanceLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		UDDistanceLabel.setBounds(426, 91, 200, 18);
		paraModulesPanel.add(UDDistanceLabel);
		
		UDDistance = new JSlider(5, 90, 20);
		UDDistance.setMinorTickSpacing(5);
		UDDistance.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		UDDistance.setBounds(426, 119, 200, 14);
		paraModulesPanel.add(UDDistance);
		
		ChestStealer = new JCheckBox("箱子小偷");
		ChestStealer.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		ChestStealer.setBounds(426, 143, 156, 27);
		paraModulesPanel.add(ChestStealer);
		
		takeDelayLabel = new JLabel("拿取延迟: ");
		takeDelayLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		takeDelayLabel.setBounds(426, 171, 200, 18);
		paraModulesPanel.add(takeDelayLabel);
		
		takeDelay = new JSlider(0, 30, 3);
		takeDelay.setMinorTickSpacing(1);
		takeDelay.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		takeDelay.setBounds(426, 195, 200, 14);
		paraModulesPanel.add(takeDelay);
		
		SpeedForce = new JCheckBox("变速");
		SpeedForce.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		SpeedForce.setBounds(426, 223, 156, 27);
		paraModulesPanel.add(SpeedForce);
		
		SpeedForceKey = new BetterJButton();
		SpeedForceKey.addKeyListener(new KeyBindEvent(SpeedForceKey));
		SpeedForceKey.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		SpeedForceKey.setBounds(588, 225, 131, 23);
		paraModulesPanel.add(SpeedForceKey);
		
		speedLabel = new JLabel("速度: ");
		speedLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		speedLabel.setBounds(426, 249, 200, 18);
		paraModulesPanel.add(speedLabel);
		
		SPSpeed = new JSlider(1, 200, 13);
		SPSpeed.setMinorTickSpacing(1);
		SPSpeed.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		SPSpeed.setBounds(426, 272, 200, 14);
		paraModulesPanel.add(SPSpeed);
		
		sleepTime = new JSlider(0, 20, 7);
		sleepTime.setMinorTickSpacing(1);
		sleepTime.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		sleepTime.setBounds(288, 414, 446, 14);
		contentPane.add(sleepTime);
		
		sleeptimeLabel = new JLabel("休眠间隔(越低执行速度越快)[0是智能]: ");
		sleeptimeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		sleeptimeLabel.setBounds(10, 413, 268, 15);
		contentPane.add(sleeptimeLabel);

		
		isLoaded = true;

		
		new Thread(this).start();
	}
	
	private String getPlayersMapInfo(boolean ReverseDistance) {
		if(world() != null) {
			StringBuilder builder = new StringBuilder();
			ArrayList<EntityPlayer> playerList = new ArrayList<>(world().playerEntities);
			if(!playerList.isEmpty()) {
				playerList.sort(Comparator.comparingDouble(player -> (ReverseDistance ? -player().getDistance(player) : player().getDistance(player))));
			}
			for(int i = 0; i < playerList.size(); i++) {
				EntityPlayer player = playerList.get(i);
				if(player != player() && player.getHealth() > 0) {
					double posX = (player.posX - player().posX) * 1.2F;
			        double posZ = (player.posZ - player().posZ) * 1.2F;
			        double cos = Math.cos(player().rotationYaw * (Math.PI * 2 / 360));
			        double sin = Math.sin(player().rotationYaw * (Math.PI * 2 / 360));
			        double angle = Math.atan2(-(posZ * cos - posX * sin), -(posX * cos + posZ * sin)) * 180 / Math.PI;
					String direction = "未识别";
					if(angle >= -105 && angle <= -75) {
						direction = "正前方";
					}else if(angle > -75 && angle < -15) {
						direction = "右前方";
					}else if(angle >= -15 && angle <= 15) {
						direction = "正右方";
					}else if(angle > 15 && angle < 75) {
						direction = "右后方";
					}else if(angle >= 75 && angle <= 105) {
						direction = "正后方";
					}else if(angle > 105 && angle < 165) {
						direction = "左后方";
					}else if((angle >= 165 && angle <= 180) || (angle <= -165 && angle >= -180)) {
						direction = "正左方";
					}else if(angle > -165 && angle < -105) {
						direction = "左前方";
					}
					int posY = (int)(player.posY - player().posY);
					String PON = "";
					if(posY > 0) {
						PON = "+";
					}
					builder.append("距离: " + (int)player().getDistance(player) + " 方向: " + direction + " 玩家: " + player.getName() + " 血量: " + player.getHealth() + " 相对高度: " + PON + posY + " 蹲下: " + changeToChinese(player.isSneaking()) + " 被方块挡住: " + changeToChinese(player().canEntityBeSeen(player)) + " 隐身: " + changeToChinese(player.isInvisible()) + " 是否受伤: " + changeToChinese(player.hurtTime > 0) + "\r\n");
				}
			}
			return builder.toString();
		}
		return "请先进入世界！";
	}
	
	private String changeToChinese(boolean b) {
		return (b ? "是" : "否");
	}
	
	private void message(String message) {
		outputMessage.setText(message);
	}
	
	private Block getBlock(double x, double y, double z)
	{
		return world().getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	@SubscribeEvent
	public void onWorldRenderWithParameter(RenderWorldLastEvent event) {
		if(isStopped || isTempStopped) {
			return;
		}
		boolean player = playerNametag.isSelected();
		boolean mob = mobNametag.isSelected();
		if(player || mob) {
			entityPositions.clear();
	        float pTicks = event.getPartialTicks();
	        for (Entity e : world().loadedEntityList) {
	        	if(e instanceof EntityLivingBase && e != player()) {
	        		EntityLivingBase base = (EntityLivingBase) e;
	        		if(e instanceof EntityPlayer) {
	        			if(!player) {
	        				continue;
	        			}
	        		}else {
	        			if(!mob) {
	        				continue;
	        			}
	        		}
	        		if(base.getHealth() > 0) {
	        			pre3D();
		            	FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
		                IntBuffer viewport = BufferUtils.createIntBuffer(16);
		                FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
		                FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		                GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
		                GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		                GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		                if (GLU.gluProject((float)(e.lastTickPosX + (e.posX - e.lastTickPosX) * pTicks - mc.getRenderManager().viewerPosX), (float)(e.lastTickPosY + (e.posY - e.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY + e.height + 0.2D), (float)(e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pTicks - mc.getRenderManager().viewerPosZ), modelView, projection, viewport, screenCoords))
		                {
			            	double convertTo2D = screenCoords.get(2);
			                if (convertTo2D >= 0.0D && convertTo2D < 1.0D) {
			                	entityPositions.put(base, new double[] {screenCoords.get(0), Display.getHeight() - screenCoords.get(1)});
			                }
		                }
		            	post3D();
	        		}
	        	}
	        }
		}
	}

	@SubscribeEvent
	public void onGameOverlay(Text event) {
		if(isStopped || isTempStopped) {
			return;
		}
		boolean player = playerNametag.isSelected();
		boolean mob = mobNametag.isSelected();
		if((player || mob) && entityPositions.keySet().size() > 0) {
			int scaleFactor = new ScaledResolution(mc).getScaleFactor();

	        for (EntityLivingBase ent : entityPositions.keySet()) {
	        	pre3D();
	        	
	            double[] renderPositions = entityPositions.get(ent);

	            
	            GL11.glTranslatef((float)(renderPositions[0] / scaleFactor), (float)(renderPositions[1] / scaleFactor), 0.0F);
	            
	            
	            float scale = 1.2F;
	            if(mc.currentScreen == null && GameSettings.isKeyDown(mc.gameSettings.keyBindSprint)) {
	            	scale *= 2;
	            }
	            GL11.glScalef(scale, scale, scale);
	            
	            
	            GL11.glTranslatef(0.0F, -2.5F, 0.0F);
	            
	            
	            String info = "§h" + ent.getName() + " §cHP: §a" + (int)ent.getHealth() + " §e距离: §b" + (int)player().getDistance(ent);
	            
	            int strWidth = mc.fontRenderer.getStringWidth(info);
	            
	            double x = -strWidth / 2 + 3;
	            double y = -17.0D;
	            double x2 = strWidth / 2 + 7;
	            double y2 = -7;
	            int col1 = 0;
	            int col2 = 2013265920;
	            float f = (float)(col1 >> 24 & 255) / 255.0F;
	            float f1 = (float)(col1 >> 16 & 255) / 255.0F;
	            float f2 = (float)(col1 >> 8 & 255) / 255.0F;
	            float f3 = (float)(col1 & 255) / 255.0F;
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            GL11.glEnable(GL11.GL_LINE_SMOOTH);
	            GL11.glDisable(GL11.GL_BLEND);
	            GL11.glPushMatrix();
	            GL11.glColor4f(f1, f2, f3, f);
	            GL11.glLineWidth(1.0F);
	            GL11.glBegin(GL11.GL_LINES);
	            GL11.glVertex2d(x, y);
	            GL11.glVertex2d(x, y2);
	            GL11.glVertex2d(x2, y2);
	            GL11.glVertex2d(x2, y);
	            GL11.glVertex2d(x, y);
	            GL11.glVertex2d(x2, y);
	            GL11.glVertex2d(x, y2);
	            GL11.glVertex2d(x2, y2);
	            GL11.glEnd();
	            GL11.glPopMatrix();
	            float f20 = (float)(col2 >> 24 & 255) / 255.0F;
	            float f21 = (float)(col2 >> 16 & 255) / 255.0F;
	            float f22 = (float)(col2 >> 8 & 255) / 255.0F;
	            float f23 = (float)(col2 & 255) / 255.0F;
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            GL11.glEnable(GL11.GL_LINE_SMOOTH);
	            GL11.glShadeModel(GL11.GL_SMOOTH);
	            GL11.glPushMatrix();
	            GL11.glBegin(GL11.GL_QUADS);
	            GL11.glColor4f(f21, f22, f23, f20);
	            GL11.glVertex2d(x2, y);
	            GL11.glVertex2d(x, y);
	            GL11.glVertex2d(x, y2);
	            GL11.glVertex2d(x2, y2);
	            GL11.glEnd();
	            GL11.glPopMatrix();
	            GL11.glEnable(GL11.GL_TEXTURE_2D);
	            GL11.glDisable(GL11.GL_BLEND);
	            GL11.glDisable(GL11.GL_LINE_SMOOTH);
	            GL11.glShadeModel(GL11.GL_FLAT);
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glEnable(GL11.GL_TEXTURE_2D);
	            GL11.glDisable(GL11.GL_LINE_SMOOTH);
	            
	            mc.fontRenderer.drawStringWithShadow(info, (int)(-strWidth / 2) + 5, -16, 0);
	            
	        	post3D();
	        }
		}
	}
	
	private void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

	private void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }
	
	
    private void faceEntity(EntityLivingBase e, double aimHeight)
    {
    	double x = e.posX - player().posX;
        double y = e.posY - player().posY;
        double z = e.posZ - player().posZ;
        double hight = (e.getEntityBoundingBox().maxY - e.getEntityBoundingBox().minY) > 1.6 || !(e instanceof EntityPlayer) ? aimHeight : 1.5;
        double d1 = player().posY + player().getEyeHeight() - e.posY - e.getEyeHeight() + hight;
        double d3 = Math.sqrt(x * x + z * z);
        
        float f1 = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
        double posX = x * 1.2F;
        double posZ = z * 1.2F;
        double cos = Math.cos(player().rotationYaw * (Math.PI * 2 / 360));
        double sin = Math.sin(player().rotationYaw * (Math.PI * 2 / 360));
        double angle = Math.atan2(-(posZ * cos - posX * sin), -(posX * cos + posZ * sin)) * 180 / Math.PI;
        float playerFOV = Math.abs((float)((Math.atan2(e.posZ - player().posZ, e.posX - player().posX) * 180.0 / Math.PI) - 90) - player().rotationYaw) % 360.0F;
        
        float offsetFov = (playerFOV > 180.0F ? (360.0F - playerFOV) : playerFOV);
        if(angle > -90) {
        	player().rotationYaw += offsetFov;
        }else {
        	player().rotationYaw -= offsetFov;
        }
        player().rotationPitch = f1;
    }
    
    
    private void setSpeedForce(float speed) {
    	try {
			Field fTimer = findField(Minecraft.class, "field_71428_T", "timer");
			fTimer.setAccessible(true);
			Field fTickLength = findField(Timer.class, "field_194149_e", "tickLength");
			fTickLength.setAccessible(true);
			fTickLength.setFloat(fTimer.get(mc), speed);
		} catch (Exception e) {}
    }
	
	
	
	
	
	
	
	
	protected static Field findField(Class<?> clazz, String... fieldNames)
    {
        for (String fieldName : fieldNames)
        {
            try
            {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            }catch (Exception e) {}
        }
		return null;
    }

	protected static <T, E> T getPrivateValue(Class <? super E > classToAccess, E instance, String... fieldNames)
    {
        try
        {
            return (T) findField(classToAccess, fieldNames).get(instance);
        }catch (Exception e) {}
		return null;
    }

	protected static <T, E> void setPrivateValue(Class <? super T > classToAccess, T instance, E value, String... fieldNames)
    {
        try
        {
            findField(classToAccess, fieldNames).set(instance, value);
        }catch (Exception e) {}
    }

	protected static <E> Method findMethod(Class<? super E> clazz, E instance, String[] methodNames, Class<?>... methodTypes)
    {
        for (String methodName : methodNames)
        {
            try
            {
                Method m = clazz.getDeclaredMethod(methodName, methodTypes);
                m.setAccessible(true);
                return m;
            }catch (Exception e) {}
        }
		return null;
    }
}
