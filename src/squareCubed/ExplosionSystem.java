package squareCubed;

import java.io.IOException;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ExplosionSystem {
	
	private ConfigurableEmitter smallExplosionEmitter;
	private ParticleSystem effectSystem;
	
	public ExplosionSystem() {
		try{
			effectSystem = ParticleIO.loadConfiguredSystem("res/global/explosion.xml");
			effectSystem.getEmitter(0).setEnabled(false); // disable the initial emitter
			effectSystem.setRemoveCompletedEmitters(true); // remove emitters once they finish
			//Create a new emitter based on the explosionSystem - set disabled
			smallExplosionEmitter = (ConfigurableEmitter)effectSystem.getEmitter(0);
			smallExplosionEmitter.setEnabled(false);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Explosion Emmitter failed to create.");
		}
	}
	
	public void addExplosion(float x, float y){
		ConfigurableEmitter emitter = smallExplosionEmitter.duplicate(); // copy initial emitter
		emitter.setEnabled(true); // enable
		emitter.setPosition(x, y);
		effectSystem.addEmitter(emitter); // add to particle system for rendering and updating
	}
	
	public ParticleSystem getEffectSystem(){
		return effectSystem;
	}

}
