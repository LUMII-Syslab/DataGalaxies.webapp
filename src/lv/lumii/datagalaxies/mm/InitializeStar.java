// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class InitializeStar
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
				System.err.println("Unable to delete the object "+rObject+" of type InitializeStar since the RAAPI wrapper does not take care of this reference.");
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
	InitializeStar(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.INITIALIZESTAR), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.INITIALIZESTAR);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public InitializeStar(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
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
	public static Iterable<? extends InitializeStar> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends InitializeStar> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<InitializeStar> retVal = new ArrayList<InitializeStar>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.INITIALIZESTAR);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			InitializeStar o = (InitializeStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (InitializeStar)factory.findOrCreateRAAPIReferenceWrapper(InitializeStar.class, r, true);
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
		for (InitializeStar o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static InitializeStar firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static InitializeStar firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.INITIALIZESTAR);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			InitializeStar  retVal = (InitializeStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (InitializeStar)factory.findOrCreateRAAPIReferenceWrapper(InitializeStar.class, r, true);
			return retVal;
		}
	} 
 
	public List<Star> getStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Star>(factory, rObject, factory.INITIALIZESTAR_STAR); 
	}
	public boolean setStar(Star value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.INITIALIZESTAR_STAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.INITIALIZESTAR_STAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.INITIALIZESTAR_STAR))
				ok = false;
		return ok;
	}
}
