package io.anuke.mindustry.world.blocks.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Timers;

public class LiquidBlock extends Block implements LiquidAcceptor{
	protected float liquidCapacity = 10f;
	protected float flowfactor = 4.9f;
	
	public LiquidBlock(String name) {
		super(name);
		rotate = true;
		update = true;
	}
	
	@Override
	public String description(){
		return "Transports liquids.";
	}
	
	@Override
	public void draw(Tile tile){
		LiquidEntity entity = tile.entity();
		
		Draw.rect(name() + "bottom", tile.worldx(), tile.worldy(), tile.rotation * 90);
		
		if(entity.liquid != null && entity.liquidAmount > 0.01f){
			Draw.color(entity.liquid.color);
			Draw.alpha(entity.liquidAmount / liquidCapacity);
			Draw.rect("conduitliquid", tile.worldx(), tile.worldy(), tile.rotation * 90);
			Draw.color();
		}
		
		Draw.rect(name() + "top", tile.worldx(), tile.worldy(), tile.rotation * 90);
		
	}
	
	@Override
	public TileEntity getEntity(){
		return new LiquidEntity();
	}
	
	@Override
	public void update(Tile tile){
		LiquidEntity entity = tile.entity();
		
		if(entity.liquidAmount > 0.01f && Timers.get(entity, "flow", 3)){
			tryMoveLiquid(tile, tile.getNearby()[tile.rotation]);
		}
		
	}
	
	public void tryDumpLiquid(Tile tile){
		LiquidEntity entity = tile.entity();
		
		if(entity.liquidAmount > 0.01f){
			tryMoveLiquid(tile, tile.getNearby()[tile.dump]);
			tile.dump ++;
			tile.dump %= 4;
		}
	}
	
	public void tryMoveLiquid(Tile tile, Tile next){
		LiquidEntity entity = tile.entity();
		
		Liquid liquid = entity.liquid;
		
		if(next != null && next.block() instanceof LiquidAcceptor && entity.liquidAmount > 0.01f){
			LiquidAcceptor other = (LiquidAcceptor)next.block();
			
			float flow = Math.min(other.getLiquidCapacity(next) - other.getLiquid(next) - 0.001f, Math.min(entity.liquidAmount/flowfactor, entity.liquidAmount));
			
			if(flow <= 0f || entity.liquidAmount < flow) return;
			
			if(other.acceptLiquid(next, tile, liquid, flow)){
				other.handleLiquid(next, tile, liquid, flow);
				entity.liquidAmount -= flow;
			}
		}
	}
	
	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidEntity entity = tile.entity();
		
		return entity.liquidAmount + amount < liquidCapacity && (entity.liquid == liquid || entity.liquidAmount <= 0.01f);
	}
	
	@Override
	public void handleLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidEntity entity = tile.entity();
		entity.liquid = liquid;
		entity.liquidAmount += amount;
	}
	
	@Override
	public float getLiquid(Tile tile){
		LiquidEntity entity = tile.entity();
		return entity.liquidAmount;
	}

	@Override
	public float getLiquidCapacity(Tile tile){
		return liquidCapacity;
	}
	
	public static class LiquidEntity extends TileEntity{
		public Liquid liquid;
		public float liquidAmount;
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(liquid == null ? -1 : liquid.ordinal());
			stream.writeByte((byte)(liquidAmount));
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			byte ordinal = stream.readByte();
			liquid = ordinal == -1 ? null : Liquid.values()[ordinal];
			liquidAmount = stream.readByte();
		}
	}
}
