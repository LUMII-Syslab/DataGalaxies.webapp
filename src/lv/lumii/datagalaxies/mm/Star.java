// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class Star
	extends GalaxyComponent
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
				System.err.println("Unable to delete the object "+rObject+" of type Star since the RAAPI wrapper does not take care of this reference.");
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
	Star(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.STAR), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.STAR);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public Star(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
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
	public static Iterable<? extends Star> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends Star> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<Star> retVal = new ArrayList<Star>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.STAR);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			Star o = (Star)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (Star)factory.findOrCreateRAAPIReferenceWrapper(Star.class, r, true);
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
		for (Star o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static Star firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Star firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.STAR);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			Star  retVal = (Star)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (Star)factory.findOrCreateRAAPIReferenceWrapper(Star.class, r, true);
			return retVal;
		}
	} 
 
	public Boolean getEmptyOnInit()
	{
		try { 
			String value = factory.raapi.getAttributeValue(rObject, factory.STAR_EMPTYONINIT);
			if (value == null)
				return null;
			return Boolean.parseBoolean(value);
		}
		catch (Throwable t)
		{
			return null;
		} 
	}
	public boolean setEmptyOnInit(Boolean value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.STAR_EMPTYONINIT);
		return factory.raapi.setAttributeValue(rObject, factory.STAR_EMPTYONINIT, value.toString());
	}
	public String getStarDataType()
	{
		return factory.raapi.getAttributeValue(rObject, factory.STAR_STARDATATYPE);
	}
	public boolean setStarDataType(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.STAR_STARDATATYPE);
		return factory.raapi.setAttributeValue(rObject, factory.STAR_STARDATATYPE, value.toString());
	}
	public List<Planet> getPlanet()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Planet>(factory, rObject, factory.STAR_PLANET); 
	}
	public boolean setPlanet(Planet value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_PLANET);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_PLANET))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_PLANET))
				ok = false;
		return ok;
	}
	public List<StellarWind> getConsumer()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<StellarWind>(factory, rObject, factory.STAR_CONSUMER); 
	}
	public boolean setConsumer(StellarWind value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_CONSUMER);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_CONSUMER))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_CONSUMER))
				ok = false;
		return ok;
	}
	public List<StellarWind> getProducer()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<StellarWind>(factory, rObject, factory.STAR_PRODUCER); 
	}
	public boolean setProducer(StellarWind value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_PRODUCER);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_PRODUCER))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_PRODUCER))
				ok = false;
		return ok;
	}
	public List<StarData> getStarData()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<StarData>(factory, rObject, factory.STAR_STARDATA); 
	}
	public boolean setStarData(StarData value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_STARDATA);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_STARDATA))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_STARDATA))
				ok = false;
		return ok;
	}
	public List<ConfigureStar> getConfigureStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<ConfigureStar>(factory, rObject, factory.STAR_CONFIGURESTAR); 
	}
	public boolean setConfigureStar(ConfigureStar value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_CONFIGURESTAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_CONFIGURESTAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_CONFIGURESTAR))
				ok = false;
		return ok;
	}
	public List<InitializeStar> getInitializeStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<InitializeStar>(factory, rObject, factory.STAR_INITIALIZESTAR); 
	}
	public boolean setInitializeStar(InitializeStar value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_INITIALIZESTAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_INITIALIZESTAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_INITIALIZESTAR))
				ok = false;
		return ok;
	}
	public List<FinalizeStar> getFinalizeStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<FinalizeStar>(factory, rObject, factory.STAR_FINALIZESTAR); 
	}
	public boolean setFinalizeStar(FinalizeStar value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_FINALIZESTAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_FINALIZESTAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_FINALIZESTAR))
				ok = false;
		return ok;
	}
	public List<CleanupStar> getCleanupStar()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<CleanupStar>(factory, rObject, factory.STAR_CLEANUPSTAR); 
	}
	public boolean setCleanupStar(CleanupStar value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STAR_CLEANUPSTAR);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STAR_CLEANUPSTAR))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STAR_CLEANUPSTAR))
				ok = false;
		return ok;
	}
}
