package me.botsko.prism.actions;

import me.botsko.prism.utils.TypeUtils;

import org.bukkit.block.Block;

public class BlockAction implements Action {
	
	/**
	 * 
	 */
	protected String action_time;
	
	/**
	 * 
	 */
	protected String action_type;
	
	/**
	 * 
	 */
	protected String world_name;
	
	/**
	 * 
	 */
	protected String player_name;
	
	/**
	 * 
	 */
	protected double x;
	
	/**
	 * 
	 */
	protected double y;
	
	/**
	 * 
	 */
	protected double z;
	
	/**
	 * 
	 */
	protected Block block;
	
	/**
	 * 
	 */
	protected String data;
	
	/**
	 * 
	 */
	protected int block_id;
	
	/**
	 * 
	 */
	protected byte block_subid;
	
	
	/**
	 * 
	 * @param action_time
	 * @param action_type
	 * @param world_name
	 * @param player_name
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
	public BlockAction( String action_time, String action_type, String world_name, String player_name, double x, double y, double z, Block block ){
		this(action_time, action_type, world_name, player_name, x, y, z, block, null);
	}
	
	
	/**
	 * 
	 * @param action_time
	 * @param action_type
	 * @param world_name
	 * @param player_name
	 * @param x
	 * @param y
	 * @param z
	 * @param data
	 */
	public BlockAction( String action_time, String action_type, String world_name, String player_name, double x, double y, double z, String data ){
		this(action_time, action_type, world_name, player_name, x, y, z, null, data);
	}


	/**
	 * 
	 * @param action_time
	 * @param action_type
	 * @param world_name
	 * @param player_name
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 * @param data
	 */
	public BlockAction( String action_time, String action_type, String world_name, String player_name, double x, double y, double z, Block block, String data ){
		
		this.action_time = action_time;
		this.action_type = action_type;
		this.world_name = world_name;
		this.player_name = player_name;
		this.data = data;
		this.block = block;
		this.x = x;
		this.y = y;
		this.z = z;
		
		// We either received a block or a data string. We need both, so make whichever we need.
		setDataFromBlock();
		setBlockIdsFromData();
		
	}
	
	
	/**
	 * 
	 */
	protected void setDataFromBlock(){
		if(data == null){
			data = block.getTypeId() + ":" + block.getData();
		}
	}
	
	
	/**
	 * 
	 */
	protected void setBlockIdsFromData(){
		if(block == null){
			String[] blockArr = data.split(":");
			if (!TypeUtils.isNumeric(blockArr[0])) return;
			
			block_id = Integer.parseInt(blockArr[0]);
			if (blockArr.length > 1){
				block_subid = (byte) Integer.parseInt(blockArr[1]);
			}
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getAction_time(){
		return action_time;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getAction_type(){
		return action_type;
	}
	
	
	/**
	 * @return the world_name
	 */
	public String getWorld_name() {
		return world_name;
	}

	
	/**
	 * @return the player_name
	 */
	public String getPlayer_name() {
		return player_name;
	}

	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	
	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	
	
	/**
	 * 
	 */
	public String getData(){
		return data;
	}
	
	
	/**
	 * 
	 */
	public int getBlock_id(){
		return block_id;
	}
	
	
	/**
	 * 
	 */
	public byte getBlock_subid(){
		return block_subid;
	}
}