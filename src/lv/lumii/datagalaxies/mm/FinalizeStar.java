// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class FinalizeStar
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
				System.err.println("Unable to delete the object "+rObject+" of type FinalizeStar since the RAAPI wrapper does not take care of this reference.");
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
	FinalizeStar(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.FINALIZESTAR), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.FINALIZESTAR);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public FinalizeStar(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
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
	public static Iterable<? extends FinalizeStar> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends FinalizeStar> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<FinalizeStar> retVal = new ArrayList<FinalizeStar>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.FINALIZESTAR);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			FinalizeStar o = (FinalizeStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (FinalizeStar)factory.findOrCreateRAAPIReferenceWrapper(FinalizeStar.class, r, true);
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
		for (FinalizeStar o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static FinalizeStar firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static FinalizeStar firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.FINALIZESTAR);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			FinalizeStar  retVal = (FinalizeStar)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (FinalizeStar)factory.findOrCreateRAAPIReferenceWrapper(FinalizeStar.class, r, true);
			return retVal;
		}
	} 
 
	public List<Star> getStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Star>(factory, rObject, factory.FINALIZESTAR_STAR); 
	}
	public boolean setStar(Star value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.FINALIZESTAR_STAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.FINALIZESTAR_STAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.FINALIZESTAR_STAR))
				ok = false;
		return ok;
	}
}
