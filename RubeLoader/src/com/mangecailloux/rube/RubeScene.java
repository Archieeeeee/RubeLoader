package com.mangecailloux.rube;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * A simple encapsulation of a {@link World}. Plus the data needed to run the simulation.
 * @author clement.vayer
 *
 */
public class RubeScene 
{
   public class CustomProperties {

      Map<String, Integer> m_customPropertyMap_int;
      Map<String, Double> m_customPropertyMap_float;
      Map<String, String> m_customPropertyMap_string;
      Map<String, Vector2> m_customPropertyMap_Vector2;
      Map<String, Boolean> m_customPropertyMap_bool;
      
      public CustomProperties() {
         m_customPropertyMap_int = new HashMap<String, Integer>();
         m_customPropertyMap_float = new HashMap<String, Double>();
         m_customPropertyMap_string = new HashMap<String, String>();
         m_customPropertyMap_Vector2 = new HashMap<String, Vector2>();
         m_customPropertyMap_bool = new HashMap<String, Boolean>();
      }
   }
   
	/** Box2D {@link World} */
	public World world;
	
	private Array<Body> mBodies;
	private Array<Fixture> mFixtures;
	private Array<Joint> mJoints;
	
	public Map<Object,CustomProperties> mCustomPropertiesMap;
	
	/** Simulation steps wanted per second */
	public int   stepsPerSecond;
	/** Iteration steps done in the simulation to calculates positions */
	public int   positionIterations;
	/** Iteration steps done in the simulation to calculates velocities */
	public int   velocityIterations;
	
	public RubeScene()
	{
		stepsPerSecond 		= RubeDefaults.World.stepsPerSecond;
		positionIterations 	= RubeDefaults.World.positionIterations;
		velocityIterations 	= RubeDefaults.World.velocityIterations;
		mCustomPropertiesMap = new HashMap<Object, CustomProperties>();
	}
	
   public CustomProperties getCustomPropertiesForItem(Object item, boolean createIfNotExisting)
   {

      if (mCustomPropertiesMap.containsKey(item))
         return mCustomPropertiesMap.get(item);

      if (!createIfNotExisting)
         return null;

      CustomProperties props = new CustomProperties();
      mCustomPropertiesMap.put(item, props);

      return props;
   }

   public void setCustom(Body item, String propertyName, String val)
   {
      getCustomPropertiesForItem(item, true).m_customPropertyMap_string.put(propertyName, val);
   }
   
   public String getCustom(Object item, String propertyName, String defaultVal)
   {
      CustomProperties props = getCustomPropertiesForItem(item, false);
      if (null == props)
         return defaultVal;
      if (props.m_customPropertyMap_string.containsKey(propertyName))
         return props.m_customPropertyMap_string.get(propertyName);
      return defaultVal;
	}
	
	/**
	 * Convenience method to update the Box2D simulation with the parameters read from the scene.
	 */
	public void step()
	{
		if(world != null)
		{
			float dt = 1.0f/stepsPerSecond;
			world.step(dt, velocityIterations, positionIterations);
		}
	}

   public void setBodies(Array<Body> mBodies)
   {
      this.mBodies = mBodies;
   }

   public Array<Body> getBodies()
   {
      return mBodies;
   }

   public void setFixtures(Array<Fixture> mFixtures)
   {
      this.mFixtures = mFixtures;
   }

   public Array<Fixture> getFixtures()
   {
      return mFixtures;
   }

   public void setJoints(Array<Joint> mJoints)
   {
      this.mJoints = mJoints;
   }

   public Array<Joint> getJoints()
   {
      return mJoints;
   }
}
