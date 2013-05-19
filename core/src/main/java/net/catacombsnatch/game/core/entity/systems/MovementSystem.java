package net.catacombsnatch.game.core.entity.systems;

import net.catacombsnatch.game.core.entity.components.Position;
import net.catacombsnatch.game.core.entity.components.Rotation;
import net.catacombsnatch.game.core.entity.components.Velocity;
import net.catacombsnatch.game.core.world.Direction;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	
	@Mapper protected ComponentMapper<Position> posMapper;
	@Mapper protected ComponentMapper<Rotation> rotMapper;
	@Mapper protected ComponentMapper<Velocity> velMapper;
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Rotation.class, Velocity.class));
	}

	@Override
	protected void process(Entity e) {
		Position p = posMapper.get(e);
		Rotation r = rotMapper.get(e);
		Velocity v = velMapper.get(e);
		
		v.normalize();
		
		p.addX(v.getVelocityX());
		p.addY(v.getVelocityY());
		
		v.reset(); 
		
		r.setDirection(Direction.getDirectionFor(p.vec));
	}
}