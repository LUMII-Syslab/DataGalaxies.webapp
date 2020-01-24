// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class GalaxyComponent
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
				System.err.println("Unable to delete the object "+rObject+" of type GalaxyComponent since the RAAPI wrapper does not take care of this reference.");
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
	GalaxyComponent(GalaxyEngineMetamodelFactory _factory)
	{
		factory = _factory;
		rObject = factory.raapi.createObject(factory.GALAXYCOMPONENT);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
	}

	public GalaxyComponent(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
	{
		factory = _factory;
		rObject = _rObject;
		takeReference = _takeReference;
		if (takeReference)
			factory.wrappers.put(rObject, this);
	}

	// iterator for instances...
	public static Iterable<? extends GalaxyComponent> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends GalaxyComponent> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<GalaxyComponent> retVal = new ArrayList<GalaxyComponent>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.GALAXYCOMPONENT);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			GalaxyComponent o = (GalaxyComponent)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (GalaxyComponent)factory.findOrCreateRAAPIReferenceWrapper(GalaxyComponent.class, r, true);
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
		for (GalaxyComponent o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static GalaxyComponent firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static GalaxyComponent firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.GALAXYCOMPONENT);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			GalaxyComponent  retVal = (GalaxyComponent)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (GalaxyComponent)factory.findOrCreateRAAPIReferenceWrapper(GalaxyComponent.class, r, true);
			return retVal;
		}
	} 
 
	public String getId()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYCOMPONENT_ID);
	}
	public boolean setId(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYCOMPONENT_ID);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYCOMPONENT_ID, value.toString());
	}
	public String getName()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYCOMPONENT_NAME);
	}
	public boolean setName(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYCOMPONENT_NAME);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYCOMPONENT_NAME, value.toString());
	}
	public String getState()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYCOMPONENT_STATE);
	}
	public boolean setState(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYCOMPONENT_STATE);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYCOMPONENT_STATE, value.toString());
	}
	public String getStateMessage()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYCOMPONENT_STATEMESSAGE);
	}
	public boolean setStateMessage(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYCOMPONENT_STATEMESSAGE);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYCOMPONENT_STATEMESSAGE, value.toString());
	}
	public String getLocation()
	{
		return factory.raapi.getAttributeValue(rObject, factory.GALAXYCOMPONENT_LOCATION);
	}
	public boolean setLocation(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.GALAXYCOMPONENT_LOCATION);
		return factory.raapi.setAttributeValue(rObject, factory.GALAXYCOMPONENT_LOCATION, value.toString());
	}
	public DataGalaxy getDataGalaxy()
	{
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_DATAGALAXY);
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
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_DATAGALAXY);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_DATAGALAXY))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_DATAGALAXY))
				ok = false;
		return ok;
	}
	public List<RunConfiguration> getRc1()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RunConfiguration>(factory, rObject, factory.GALAXYCOMPONENT_RC1); 
	}
	public boolean setRc1(RunConfiguration value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_RC1);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_RC1))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_RC1))
				ok = false;
		return ok;
	}
	public List<RunConfiguration> getRc2()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RunConfiguration>(factory, rObject, factory.GALAXYCOMPONENT_RC2); 
	}
	public boolean setRc2(RunConfiguration value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_RC2);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_RC2))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_RC2))
				ok = false;
		return ok;
	}
	public List<RunConfiguration> getRc3()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RunConfiguration>(factory, rObject, factory.GALAXYCOMPONENT_RC3); 
	}
	public boolean setRc3(RunConfiguration value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_RC3);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_RC3))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_RC3))
				ok = false;
		return ok;
	}
	public List<RefreshGalaxyCommand> getRefreshGalaxyCommand()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RefreshGalaxyCommand>(factory, rObject, factory.GALAXYCOMPONENT_REFRESHGALAXYCOMMAND); 
	}
	public boolean setRefreshGalaxyCommand(RefreshGalaxyCommand value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_REFRESHGALAXYCOMMAND);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_REFRESHGALAXYCOMMAND))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_REFRESHGALAXYCOMMAND))
				ok = false;
		return ok;
	}
	public List<Frame> getFrame()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Frame>(factory, rObject, factory.GALAXYCOMPONENT_FRAME); 
	}
	public boolean setFrame(Frame value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.GALAXYCOMPONENT_FRAME);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.GALAXYCOMPONENT_FRAME))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.GALAXYCOMPONENT_FRAME))
				ok = false;
		return ok;
	}
}
