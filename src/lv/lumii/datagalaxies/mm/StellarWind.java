// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class StellarWind
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
				System.err.println("Unable to delete the object "+rObject+" of type StellarWind since the RAAPI wrapper does not take care of this reference.");
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
	StellarWind(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.STELLARWIND), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.STELLARWIND);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public StellarWind(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
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
	public static Iterable<? extends StellarWind> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends StellarWind> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<StellarWind> retVal = new ArrayList<StellarWind>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.STELLARWIND);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			StellarWind o = (StellarWind)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (StellarWind)factory.findOrCreateRAAPIReferenceWrapper(StellarWind.class, r, true);
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
		for (StellarWind o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static StellarWind firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static StellarWind firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.STELLARWIND);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			StellarWind  retVal = (StellarWind)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (StellarWind)factory.findOrCreateRAAPIReferenceWrapper(StellarWind.class, r, true);
			return retVal;
		}
	} 
 
	public String getDescription()
	{
		return factory.raapi.getAttributeValue(rObject, factory.STELLARWIND_DESCRIPTION);
	}
	public boolean setDescription(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.STELLARWIND_DESCRIPTION);
		return factory.raapi.setAttributeValue(rObject, factory.STELLARWIND_DESCRIPTION, value.toString());
	}
	public List<Star> getSource()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Star>(factory, rObject, factory.STELLARWIND_SOURCE); 
	}
	public boolean setSource(Star value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STELLARWIND_SOURCE);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STELLARWIND_SOURCE))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STELLARWIND_SOURCE))
				ok = false;
		return ok;
	}
	public List<Star> getTarget()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Star>(factory, rObject, factory.STELLARWIND_TARGET); 
	}
	public boolean setTarget(Star value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STELLARWIND_TARGET);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STELLARWIND_TARGET))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STELLARWIND_TARGET))
				ok = false;
		return ok;
	}
	public List<CrossFilter> getCrossFilter()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<CrossFilter>(factory, rObject, factory.STELLARWIND_CROSSFILTER); 
	}
	public boolean setCrossFilter(CrossFilter value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STELLARWIND_CROSSFILTER);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STELLARWIND_CROSSFILTER))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STELLARWIND_CROSSFILTER))
				ok = false;
		return ok;
	}
	public List<ConfigureStellarWind> getConfigureStellarWind()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<ConfigureStellarWind>(factory, rObject, factory.STELLARWIND_CONFIGURESTELLARWIND); 
	}
	public boolean setConfigureStellarWind(ConfigureStellarWind value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STELLARWIND_CONFIGURESTELLARWIND);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STELLARWIND_CONFIGURESTELLARWIND))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STELLARWIND_CONFIGURESTELLARWIND))
				ok = false;
		return ok;
	}
	public List<EmitStellarWind> getEmitStellarWind()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<EmitStellarWind>(factory, rObject, factory.STELLARWIND_EMITSTELLARWIND); 
	}
	public boolean setEmitStellarWind(EmitStellarWind value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.STELLARWIND_EMITSTELLARWIND);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.STELLARWIND_EMITSTELLARWIND))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.STELLARWIND_EMITSTELLARWIND))
				ok = false;
		return ok;
	}
}
