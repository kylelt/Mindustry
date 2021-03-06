package io.anuke.mindustry.entities.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

public class TeslaOrb extends Entity{
	private Array<Vector2> points = new Array<>();
	private ObjectSet<Enemy> hit = new ObjectSet<>();
	private int damage = 0;
	private float range = 0;
	private float lifetime = 30f;
	private float life = 0f;
	
	public TeslaOrb(float x, float y, float range, int damage){
		set(x, y);
		this.damage = damage;
		this.range = range;
	}
	
	void shock(){
		float stopchance = 0.1f;
		float curx = x, cury = y;
		float shake = 3f;
		
		outer:
		while(true){
			if(Mathf.chance(stopchance)){
				break;
			}
			Array<SolidEntity> enemies = Entities.getNearby(curx, cury, range);
			
			for(SolidEntity entity : enemies){
				if(entity instanceof Enemy && entity.distanceTo(curx, cury) < range 
						&& !hit.contains((Enemy)entity)){
					hit.add((Enemy)entity);
					points.add(new Vector2(entity.x + Mathf.range(shake), entity.y + Mathf.range(shake)));
					damageEnemy((Enemy)entity);
					curx = entity.x;
					cury = entity.y;
					continue outer;
				}
			}
			
			break;
		}
		
		if(points.size == 0){
			remove();
		}
	}
	
	void damageEnemy(Enemy enemy){
		//TODO
		enemy.damage(damage);
		Effects.effect("laserhit", enemy.x + Mathf.range(2f), enemy.y + Mathf.range(2f));
	}
	
	@Override
	public void update(){
		life += Timers.delta();
		
		if(life >= lifetime){
			remove();
		}
	}
	
	@Override
	public void drawOver(){
		if(points.size == 0) return;
		
		float range = 1f;
		
		Vector2 previous = Tmp.v1.set(x, y);
		
		for(Vector2 enemy : points){
			
			
			float x1 = previous.x + Mathf.range(range), 
					y1 = previous.y + Mathf.range(range),
					x2 = enemy.x + Mathf.range(range),
					y2 = enemy.y + Mathf.range(range);
			
			Draw.color(Color.WHITE);
			Draw.alpha(1f-life/lifetime);
			
			Draw.thick(3f - life/lifetime*2f);
			Draw.line(x1, y1, x2, y2);
			
			float rad = 7f - life/lifetime*5f;
			
			Draw.rect("circle", x2, y2, rad, rad);
			
			if(previous.epsilonEquals(x, y, 0.001f)){
				Draw.rect("circle", x, y, rad, rad);
			}
			
			//Draw.color(Color.WHITE);
			
			//Draw.thick(2f - life/lifetime*2f);
			//Draw.line(x1, y1, x2, y2);
			
			Draw.reset();
			
			previous = enemy;
		}
	}
	
	@Override
	public void added(){
		Timers.run(1f, ()->{
			shock();
		});
		
	}
}
