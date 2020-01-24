// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class GalaxyEngine
  	implements RAAPIReferenceWrapper
{
	protected GalaxyEngineMetamodelFactory factory;
	protected long rObject = 0;
	protected boolean takeReference;

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
				System.err.println("Unable to delete the object "+rObject+" of type GalaxyEngine since the RAAPI wrapper does not take care of this reference.");
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
	GalaxyEngine(GalaxyEngineMetamodelFactory _factory)
	{
		factory = _factory;
		rObject = factory.raapi.createObject(factory.GALAXYENGINE);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
	}

	public GalaxyEngine(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
	{
		factory = _factory;
		rObject = _rObject;
		takeReference = _takeReference;
		if (takeReference)
			factory.wrappers.put(rObject, this);
	}

	// iterator for instances...
	public static Iterable<? extends GalaxyEngine> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends GalaxyEngine> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<GalaxyEngine> retVal = new ArrayList<GalaxyEngine>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.GALAXYENGINE);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			GalaxyEngine o = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(GalaxyEngine.class, r, true);
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
		for (GalaxyEngine o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static GalaxyEngine firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static GalaxyEngine firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.GALAXYENGINE);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			GalaxyEngine  retVal = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(GalaxyEngine.class, r, true);
			return retVal;
		}
	} 
 
	public String getOnRunEvent()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYENGINE_ONRUNEVENT);
	}
	public boolean setOnRunEvent(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYENGINE_ONRUNEVENT);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYENGINE_ONRUNEVENT, value.toString());
	}
	public Boolean getUseGalacticIcons()
	{
		try { 
			String value = factory.raapi.getAttributeValue(rObject, factory.GALAXYENGINE_USEGALACTICICONS);
			if (value == null)
				return null;
			return Boolean.parseBoolean(value);
		}
		catch (Throwable t)
		{
			return null;
		} 
	}
	public boolean setUseGalacticIcons(Boolean value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYENGINE_USEGALACTICICONS);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYENGINE_USEGALACTICICONS, value.toString());
	}
	public List<DataGalaxy> getDataGalaxy()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<DataGalaxy>(factory, rObject, factory.GALAXYENGINE_DATAGALAXY); 
	}
	public boolean setDataGalaxy(DataGalaxy value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYENGINE_DATAGALAXY);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYENGINE_DATAGALAXY))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYENGINE_DATAGALAXY))
				ok = false;
		return ok;
	}
}
