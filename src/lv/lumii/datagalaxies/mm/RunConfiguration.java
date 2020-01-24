// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class RunConfiguration
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
				System.err.println("Unable to delete the object "+rObject+" of type RunConfiguration since the RAAPI wrapper does not take care of this reference.");
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
	RunConfiguration(GalaxyEngineMetamodelFactory _factory)
	{
		factory = _factory;
		rObject = factory.raapi.createObject(factory.RUNCONFIGURATION);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
	}

	public RunConfiguration(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
	{
		factory = _factory;
		rObject = _rObject;
		takeReference = _takeReference;
		if (takeReference)
			factory.wrappers.put(rObject, this);
	}

	// iterator for instances...
	public static Iterable<? extends RunConfiguration> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends RunConfiguration> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<RunConfiguration> retVal = new ArrayList<RunConfiguration>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.RUNCONFIGURATION);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			RunConfiguration o = (RunConfiguration)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (RunConfiguration)factory.findOrCreateRAAPIReferenceWrapper(RunConfiguration.class, r, true);
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
		for (RunConfiguration o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static RunConfiguration firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static RunConfiguration firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.RUNCONFIGURATION);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			RunConfiguration  retVal = (RunConfiguration)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (RunConfiguration)factory.findOrCreateRAAPIReferenceWrapper(RunConfiguration.class, r, true);
			return retVal;
		}
	} 
 
	public String getName()
	{
		return factory.raapi.getAttributeValue(rObject, factory.RUNCONFIGURATION_NAME);
	}
	public boolean setName(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.RUNCONFIGURATION_NAME);
		return factory.raapi.setAttributeValue(rObject, factory.RUNCONFIGURATION_NAME, value.toString());
	}
	public String getDescription()
	{
		return factory.raapi.getAttributeValue(rObject, factory.RUNCONFIGURATION_DESCRIPTION);
	}
	public boolean setDescription(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.RUNCONFIGURATION_DESCRIPTION);
		return factory.raapi.setAttributeValue(rObject, factory.RUNCONFIGURATION_DESCRIPTION, value.toString());
	}
	public List<GalaxyComponent> getVisibleComponent()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<GalaxyComponent>(factory, rObject, factory.RUNCONFIGURATION_VISIBLECOMPONENT); 
	}
	public boolean setVisibleComponent(GalaxyComponent value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_VISIBLECOMPONENT);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.RUNCONFIGURATION_VISIBLECOMPONENT))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.RUNCONFIGURATION_VISIBLECOMPONENT))
				ok = false;
		return ok;
	}
	public List<GalaxyComponent> getMustConfigureComponent()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<GalaxyComponent>(factory, rObject, factory.RUNCONFIGURATION_MUSTCONFIGURECOMPONENT); 
	}
	public boolean setMustConfigureComponent(GalaxyComponent value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_MUSTCONFIGURECOMPONENT);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.RUNCONFIGURATION_MUSTCONFIGURECOMPONENT))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.RUNCONFIGURATION_MUSTCONFIGURECOMPONENT))
				ok = false;
		return ok;
	}
	public List<GalaxyComponent> getDeactivatedComponent()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<GalaxyComponent>(factory, rObject, factory.RUNCONFIGURATION_DEACTIVATEDCOMPONENT); 
	}
	public boolean setDeactivatedComponent(GalaxyComponent value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_DEACTIVATEDCOMPONENT);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.RUNCONFIGURATION_DEACTIVATEDCOMPONENT))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.RUNCONFIGURATION_DEACTIVATEDCOMPONENT))
				ok = false;
		return ok;
	}
	public DataGalaxy getDataGalaxy()
	{
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_DATAGALAXY);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r != 0) {
			DataGalaxy retVal = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(DataGalaxy.class, r, true);
			return retVal;
		}
		else
			return null;
	}
	public boolean setDataGalaxy(DataGalaxy value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_DATAGALAXY);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.RUNCONFIGURATION_DATAGALAXY))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.RUNCONFIGURATION_DATAGALAXY))
				ok = false;
		return ok;
	}
	public List<DataGalaxy> getDg1()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<DataGalaxy>(factory, rObject, factory.RUNCONFIGURATION_DG1); 
	}
	public boolean setDg1(DataGalaxy value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.RUNCONFIGURATION_DG1);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.RUNCONFIGURATION_DG1))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.RUNCONFIGURATION_DG1))
				ok = false;
		return ok;
	}
}
