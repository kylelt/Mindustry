package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.World;
import io.anuke.mindustry.world.blocks.types.PowerAcceptor;
import io.anuke.mindustry.world.blocks.types.PowerBlock;
import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Geometry;
import io.anuke.ucore.util.Mathf;

public class Generator extends PowerBlock{
	public static final int powerTime = 2;

	public int laserRange = 6;
	public int laserDirections = 4;
	public int powerRange = 4;
	public float powerSpeed = 0.03f;
	public boolean explosive = true;
	public boolean drawRadius = false;

	public Generator(String name) {
		super(name);
		explosionEffect = "generatorexplosion";
	}

	@Override
	public void onDestroyed(Tile tile){
		if(explosive){
			float x = tile.worldx(), y = tile.worldy();

			Effects.effect("shellsmoke", x, y);
			Effects.effect("blastsmoke", x, y);

			Timers.run(Mathf.random(8f + Mathf.random(6f)), () -> {
				Effects.shake(6f, 8f, x, y);
				Effects.effect(explosionEffect, x, y);
				Effects.effect("shockwave", x, y);

				Timers.run(12f + Mathf.random(20f), () -> {
					tile.damageNearby(3, 40, 0f);
				});

				Effects.sound(explosionSound, x, y);
			});

		}else{
			super.onDestroyed(tile);
		}
	}

	@Override
	public void drawPixelOverlay(Tile tile){
		super.drawPixelOverlay(tile);
		
		if(drawRadius){
			Draw.color("yellow");
			Draw.dashcircle(tile.worldx(), tile.worldy(), powerRange * Vars.tilesize);
			Draw.reset();
		}
	}
	
	@Override
	public void drawOver(Tile tile){
		PowerEntity entity = tile.entity();
		if(entity.power > powerSpeed){
			for(int i = 0; i < laserDirections; i++){
				drawLaserTo(tile, (tile.rotation + i) - laserDirections/2);
			}
		}
	}
	
	@Override
	public boolean acceptsPower(Tile tile){
		return false;
	}

	protected void distributeLaserPower(Tile tile){
		PowerEntity entity = tile.entity();

		for(int i = 0; i < laserDirections; i++){
			Tile target = laserTarget(tile, (tile.rotation + i) - laserDirections/2);
			
			if(target == null) continue;
			
			PowerAcceptor p = (PowerAcceptor) target.block();
			if(p.acceptsPower(target) && entity.power >= powerSpeed){
				float accepted = p.addPower(target, powerSpeed);
				entity.power -= (accepted);
			}

		}

	}

	protected void drawLaserTo(Tile tile, int rotation){

		Tile target = laserTarget(tile, rotation);

		if(target != null){
			Angles.translation(rotation * 90, 6f);

			Draw.color(Color.GRAY, Color.WHITE, 0.902f + Mathf.sin(Timers.time(), 1.7f, 0.08f));
			Draw.alpha(1f);

			float r = 0f;

			Draw.laser("laser", "laserend", tile.worldx() + Angles.x() + Mathf.range(r), tile.worldy() + Angles.y() + Mathf.range(r), target.worldx() - Angles.x() + Mathf.range(r), target.worldy() - Angles.y() + Mathf.range(r), 0.7f + Mathf.sin(Timers.time(), 2f, 0.1f * 0));

			Draw.color();
		}
	}

	protected Tile laserTarget(Tile tile, int rotation){
		if(rotation < 0)
			rotation += 4;
		rotation %= 4;
		GridPoint2 point = Geometry.getD4Points()[rotation];

		int i = 0;

		for(i = 1; i < laserRange; i++){
			Tile other = World.tile(tile.x + i * point.x, tile.y + i * point.y);

			if(other != null && other.block() instanceof PowerAcceptor){
				return other;
			}
		}
		return null;
	}
	
	//TODO better distribution
	protected void distributePower(Tile tile){
		if(!Timers.get(tile, "generate", powerTime)){
			return;
		}

		PowerEntity p = tile.entity();

		int acceptors = 0;
		float flow = 0f;

		//TODO have two phases, where it checks nearby blocks first, then distributes it evenly
		for(int i = 0; i < 2; i++){
			for(int x = -powerRange; x <= powerRange; x++){
				for(int y = -powerRange; y <= powerRange; y++){

					if(x == 0 && y == 0){
						continue;
					}

					if(Vector2.dst(x, y, 0, 0) < powerRange){
						Tile dest = World.tile(tile.x + x, tile.y + y);
						if(dest != null && dest.block() instanceof PowerAcceptor && ((PowerAcceptor) dest.block()).acceptsPower(dest)){
							if(i == 1){
								PowerAcceptor block = (PowerAcceptor) dest.block();

								float transmission = Math.min(flow, p.power);

								float amount = block.addPower(dest, transmission);
								p.power -= amount;
							}else{
								acceptors++;
							}
						}
					}
				}
			}

			//TODO better distribution scheme
			if(i == 0 && acceptors > 0){
				flow = Mathf.clamp(p.power / acceptors, 0f, powerSpeed / acceptors);
			}
		}
	}
}
