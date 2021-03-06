package io.anuke.mindustry;

import static io.anuke.mindustry.Vars.respawnduration;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.graphics.Hue;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;

public class EffectCreator{
	static Color lightRed = Hue.mix(Color.WHITE, Color.FIREBRICK, 0.1f);
	static Color lightOrange = Color.valueOf("f68021");
	
	public static void create(){
		
		Effects.create("generatorexplosion", 28, e -> {
			Angles.randLenVectors(e.id, 16, 10f + e.ifract()*8f, (x, y)->{
				float size = e.fract()*12f + 1f;
				Draw.color(Color.WHITE, lightOrange, e.ifract());
				Draw.rect("circle", e.x + x, e.y + y, size, size);
				Draw.reset();
			});
		});
		
		Effects.create("shockwave", 10f, e -> {
			Draw.color(Color.WHITE, Color.LIGHT_GRAY, e.ifract());
			Draw.thick(e.fract()*2f + 0.2f);
			Draw.circle(e.x, e.y, e.ifract()*28f);
			Draw.reset();
		});
		
		Effects.create("shellsmoke", 21, e -> {
			Angles.randLenVectors(e.id, 8, 1f + e.ifract()*16f, (x, y)->{
				float size = 2f+e.fract()*5f;
				Draw.color(Color.LIGHT_GRAY, Color.DARK_GRAY, e.ifract());
				Draw.rect("circle", e.x + x, e.y + y, size, size);
				Draw.reset();
			});
		});
		
		Effects.create("blastsmoke", 26, e -> {
			Angles.randLenVectors(e.id, 12, 1f + e.ifract()*23f, (x, y)->{
				float size = 2f+e.fract()*6f;
				Draw.color(Color.LIGHT_GRAY, Color.DARK_GRAY, e.ifract());
				Draw.rect("circle", e.x + x, e.y + y, size, size);
				Draw.reset();
			});
		});
		
		Effects.create("lava", 18, e -> {
			Angles.randLenVectors(e.id, 3, 1f + e.ifract()*10f, (x, y)->{
				float size = e.sfract()*4f;
				Draw.color(Color.ORANGE, Color.GRAY, e.ifract());
				Draw.rect("circle", e.x + x, e.y + y, size, size);
				Draw.reset();
			});
		});
		
		Effects.create("lavabubble", 45f, e -> {
			Draw.color(Color.ORANGE);
			float scl = 0.35f;
			Draw.thick(1f - Mathf.clamp(e.ifract() - (1f-scl)) * (1f/scl));
			Draw.circle(e.x, e.y, e.ifract()*4f);
			Draw.reset();
		});
		
		Effects.create("oilbubble", 64f, e -> {
			Draw.color(Color.DARK_GRAY);
			float scl = 0.25f;
			Draw.thick(1f - Mathf.clamp(e.ifract() - (1f-scl)) * (1f/scl));
			Draw.circle(e.x, e.y, e.ifract()*3f);
			Draw.reset();
		});
		
		Effects.create("shellexplosion", 15, e -> {
			Draw.thickness(1.3f - e.ifract());
			Draw.color(Hue.mix(Color.WHITE, Color.ORANGE, e.ifract()));
			Draw.circle(e.x, e.y, 1f + e.ifract() * 7f);
			Draw.reset();
		});
		
		Effects.create("blastexplosion", 16, e -> {
			Draw.thickness(1.2f - e.ifract());
			Draw.color(Hue.mix(Color.WHITE, Color.SCARLET, e.ifract()));
			Draw.circle(e.x, e.y, 1.5f + e.ifract() * 9f);
			Draw.reset();
		});
		
		Effects.create("place", 16, e -> {
			Draw.thickness(3f - e.ifract() * 2f);
			Draw.square(e.x, e.y, Vars.tilesize / 2f + e.ifract() * 3f);
			Draw.reset();
		});
		
		Effects.create("purify", 10, e -> {
			Draw.color(Hue.mix(Color.ROYAL, Color.GRAY, e.ifract()));
			Draw.thickness(2f);
			Draw.spikes(e.x, e.y, e.ifract() * 4f, 2, 6);
			Draw.reset();
		});
		
		Effects.create("purifyoil", 10, e -> {
			Draw.color(Hue.mix(Color.BLACK, Color.GRAY, e.ifract()));
			Draw.thickness(2f);
			Draw.spikes(e.x, e.y, e.ifract() * 4f, 2, 6);
			Draw.reset();
		});
		
		Effects.create("generate", 11, e -> {
			Draw.color(Hue.mix(Color.ORANGE, Color.YELLOW, e.ifract()));
			Draw.thickness(1f);
			Draw.spikes(e.x, e.y, e.ifract() * 5f, 2, 8);
			Draw.reset();
		});

		Effects.create("spark", 10, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.GRAY, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 5f, 2, 8);
			Draw.reset();
		});
		
		Effects.create("sparkbig", 11, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(lightRed, Color.GRAY, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 5f, 2.3f, 8);
			Draw.reset();
		});
		
		Effects.create("smelt", 10, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.YELLOW, Color.RED, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 5f, 2, 8);
			Draw.reset();
		});

		Effects.create("break", 12, e -> {
			Draw.thickness(2f);
			Draw.color(Color.WHITE, Color.GRAY, e.ifract());
			Draw.spikes(e.x, e.y, e.ifract() * 5f, 2, 5);
			Draw.reset();
		});

		Effects.create("hit", 10, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.ORANGE, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 3f, 2, 8);
			Draw.reset();
		});
		
		Effects.create("laserhit", 10, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.SKY, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 2f, 2, 6);
			Draw.reset();
		});
		
		Effects.create("shoot", 8, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.GOLD, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 2f, 2, 5);
			Draw.reset();
		});
		
		Effects.create("shoot2", 8, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.SKY, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 2f, 1, 5);
			Draw.reset();
		});
		
		Effects.create("shoot3", 8, e -> {
			Draw.thickness(1f);
			Draw.color(Hue.mix(Color.WHITE, Color.GOLD, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 2f, 1, 5);
			Draw.reset();
		});
		
		Effects.create("railshoot", 8, e -> {
			Draw.thickness(2f  - e.ifract()*2f);
			Draw.color(Hue.mix(Color.WHITE, Color.LIGHT_GRAY, e.ifract()));
			Draw.spikes(e.x, e.y, 1f + e.ifract() * 4f, 1, 5);
			Draw.reset();
		});
		
		Effects.create("mortarshoot", 9, e -> {
			Draw.thickness(1.3f - e.ifract());
			Draw.color(Hue.mix(Color.WHITE, Color.ORANGE, e.ifract()));
			Draw.spikes(e.x, e.y, e.ifract() * 4f, 2, 6);
			Draw.circle(e.x, e.y, e.ifract() * 5f + 1f);
			Draw.reset();
		});

		Effects.create("explosion", 15, e -> {
			Draw.thickness(2f);
			Draw.color(Hue.mix(Color.ORANGE, Color.GRAY, e.ifract()));
			Draw.spikes(e.x, e.y, 2f + e.ifract() * 3f, 4, 6);
			Draw.circle(e.x, e.y, 3f + e.ifract() * 3f);
			Draw.reset();
		});
		
		Effects.create("coreexplosion", 13, e -> {
			Draw.thickness(3f-e.ifract()*2f);
			Draw.color(Hue.mix(Color.ORANGE, Color.WHITE, e.ifract()));
			Draw.spikes(e.x, e.y, 5f + e.ifract() * 40f, 6, 6);
			Draw.circle(e.x, e.y, 4f + e.ifract() * 40f);
			Draw.reset();
		});
		
		Effects.create("smoke", 100, e -> {
			Draw.color(Color.GRAY, new Color(0.3f, 0.3f, 0.3f, 1f), e.ifract());
			float size = 7f-e.ifract()*7f;
			Draw.rect("circle", e.x, e.y, size, size);
			Draw.reset();
		});
		
		Effects.create("railsmoke", 30, e -> {
			Draw.color(Color.LIGHT_GRAY, Color.WHITE, e.ifract());
			float size = e.fract()*4f;
			Draw.rect("circle", e.x, e.y, size, size);
			Draw.reset();
		});
		
		Effects.create("spawn", 23, e -> {
			Draw.thickness(2f);
			Draw.color(Hue.mix(Color.DARK_GRAY, Color.SCARLET, e.ifract()));
			Draw.circle(e.x, e.y, 7f - e.ifract() * 6f);
			Draw.reset();
		});

		Effects.create("ind", 100, e -> {
			Draw.thickness(3f);
			Draw.color("royal");
			Draw.circle(e.x, e.y, 3);
			Draw.reset();
		});
		
		Effects.create("respawn", respawnduration, e -> {
			Draw.tcolor(Color.SCARLET);
			Draw.tscl(0.25f);
			Draw.text("Respawning in " + (int)((e.lifetime-e.time)/60), e.x, e.y);
			Draw.tscl(0.5f);
			Draw.reset();
		});
	}
}
