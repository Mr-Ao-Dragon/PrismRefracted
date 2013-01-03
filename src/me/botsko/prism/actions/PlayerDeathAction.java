package me.botsko.prism.actions;

import java.text.SimpleDateFormat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerDeathAction extends GenericAction {

	
	/**
	 * 
	 */
	protected Player player;
	
	/**
	 * 
	 */
	protected String cause;
	
	/**
	 * 
	 */
	protected String attacker;


	/**
	 * 
	 * @param action_type
	 * @param block
	 * @param player
	 */
	public PlayerDeathAction( ActionType action_type, Player player, String cause, String attacker ){
		if(action_type != null){
			this.type = action_type;
		}
		if(player != null){
			this.player = player;
			this.world_name = player.getWorld().getName();
			this.x = player.getLocation().getX();
			this.y = player.getLocation().getY();
			this.z = player.getLocation().getZ();
			this.player_name = player.getName();
		}
		this.cause = cause;
		this.attacker = attacker;
		if(action_time == null){
			java.util.Date date= new java.util.Date();
			action_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime());
		}
		// Save entity data from current entity
		setDataFromDeathInfo();
		getDeathInfoFromData();
	}
	
	
	/**
	 * 
	 */
	public void setData( String data ){
		this.data = data;
		getDeathInfoFromData();
	}
	
	
	/**
	 * 
	 */
	protected void setDataFromDeathInfo(){
		if(data == null && cause != null){
			data = cause+":"+attacker;
		}
	}
	
	
	/**
	 * 
	 */
	public EntityType getDeathInfoFromData(){
		if(cause == null && data != null){
			String[] dataArr = data.split(":");
			System.out.print("SETTING DEATH MSG: " + dataArr[0]);
			cause = dataArr[0];
			if (dataArr.length > 1){
				attacker = dataArr[1];
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getNiceName(){
		String name = "";
		if(attacker != null && !attacker.isEmpty()){
			name += attacker;
		}
		if(cause != null && !cause.isEmpty()){
			name += "(" + cause + ")";
		}
		return name;
	}
}