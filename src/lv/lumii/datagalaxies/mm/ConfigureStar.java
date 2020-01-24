// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class ConfigureStar
	extends LaunchTransformationCommand
  	implements RAAPIReferenceWrapper
{
	/* these references are defined only in the top-most superclass:
	protected GalaxyEngineMetamodelFactory factory;
	protected long rObject = 0;
	protected boolean takeReference;
	*/

	public RAAPI getRAAPI()
	{
		return factory.raapi;
	}
	public long getRAAPIReference()
	{
		return rObject;
	}

	public boolean delete()
	{
		if (rObject != 0) {
			if (!takeReference) {
				System.err.println("Unable to delete the object "+rObject+" of type ConfigureStar since the RAAPI wrapper does not take care of this reference.");
				return false;
			}
			factory.wrappers.remove(rObject);
			boolean retVal = factory.raapi.deleteObject(rObject);
			if (retVal) {
				rObject = 0;
			}
			else
				factory.wrappers.put(rObject, this); // putting back
			return retVal;
		}
		else
			return false;
	}

	public void finalize()
	{
		if (rObject != 0) {
			if (takeReference) {
				factory.wrappers.remove(rObject);
				factory.raapi.freeReference(rObject);
			}
			rObject = 0;
		}
	}


	// package-visibility:
	ConfigureStar(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.CONFIGURESTAR), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.CONFIGURESTAR);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public ConfigureStar(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
	{
		super(_factory, _rObject, _takeReference);
		/*
		factory = _factory;
		rObject = _rObject;
		takeReference = _takeReference;
		if (takeReference)
			factory.wrappers.put(rObject, this);
		*/
	}

	// iterator for instances...
	public static Iterable<? extends ConfigureStar> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends ConfigureStar> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<ConfigureStar> retVal = new ArrayList<ConfigureStar>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.CONFIGURESTAR);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			ConfigureStar o = (ConfigureStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (ConfigureStar)factory.findOrCreateRAAPIReferenceWrapper(ConfigureStar.class, r, true);
			if (o != null)
				retVal.add(o);
			r = factory.raapi.resolveIteratorNext(it);
		}
		factory.raapi.freeIterator(it);
		return retVal;
	}

	public static boolean deleteAllObjects()
	{
		return deleteAllObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	}

	public static boolean deleteAllObjects(GalaxyEngineMetamodelFactory factory)
	{
		for (ConfigureStar o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static ConfigureStar firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static ConfigureStar firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.CONFIGURESTAR);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			ConfigureStar  retVal = (ConfigureStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (ConfigureStar)factory.findOrCreateRAAPIReferenceWrapper(ConfigureStar.class, r, true);
			return retVal;
		}
	} 
 
	public String getFrameLocation()
	{
		return factory.raapi.getAttributeValue(rObject, factory.CONFIGURESTAR_FRAMELOCATION);
	}
	public boolean setFrameLocation(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.CONFIGURESTAR_FRAMELOCATION);
		return factory.raapi.setAttributeValue(rObject, factory.CONFIGURESTAR_FRAMELOCATION, value.toString());
	}
	public List<Star> getStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Star>(factory, rObject, factory.CONFIGURESTAR_STAR); 
	}
	public boolean setStar(Star value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.CONFIGURESTAR_STAR);
		// deleting previous links...
		if (it != 0) {
			ArrayList<Long> list = new ArrayList<Long>();
			long r = factory.raapi.resolveIteratorFirst(it);
			while (r != 0) {
				list.add(r);
				r = factory.raapi.resolveIteratorNext(it);
			}
			factory.raapi.freeIterator(it);
			for (Long rLinked : list)
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.CONFIGURESTAR_STAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.CONFIGURESTAR_STAR))
				ok = false;
		return ok;
	}
}
