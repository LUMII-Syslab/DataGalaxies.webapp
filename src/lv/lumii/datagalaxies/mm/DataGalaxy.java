// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class DataGalaxy
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
				System.err.println("Unable to delete the object "+rObject+" of type DataGalaxy since the RAAPI wrapper does not take care of this reference.");
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
	DataGalaxy(GalaxyEngineMetamodelFactory _factory)
	{
		factory = _factory;
		rObject = factory.raapi.createObject(factory.DATAGALAXY);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
	}

	public DataGalaxy(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
	{
		factory = _factory;
		rObject = _rObject;
		takeReference = _takeReference;
		if (takeReference)
			factory.wrappers.put(rObject, this);
	}

	// iterator for instances...
	public static Iterable<? extends DataGalaxy> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends DataGalaxy> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<DataGalaxy> retVal = new ArrayList<DataGalaxy>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.DATAGALAXY);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			DataGalaxy o = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(DataGalaxy.class, r, true);
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
		for (DataGalaxy o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static DataGalaxy firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static DataGalaxy firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.DATAGALAXY);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			DataGalaxy  retVal = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (DataGalaxy)factory.findOrCreateRAAPIReferenceWrapper(DataGalaxy.class, r, true);
			return retVal;
		}
	} 
 
	public String getLayoutInfo()
	{
		return factory.raapi.getAttributeValue(rObject, factory.DATAGALAXY_LAYOUTINFO);
	}
	public boolean setLayoutInfo(String value)
	{
		if (value == null)
			return factory.raapi.deleteAttributeValue(rObject, factory.DATAGALAXY_LAYOUTINFO);
		return factory.raapi.setAttributeValue(rObject, factory.DATAGALAXY_LAYOUTINFO, value.toString());
	}
	public List<GalaxyComponent> getGalaxyComponent()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<GalaxyComponent>(factory, rObject, factory.DATAGALAXY_GALAXYCOMPONENT); 
	}
	public boolean setGalaxyComponent(GalaxyComponent value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_GALAXYCOMPONENT);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_GALAXYCOMPONENT))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_GALAXYCOMPONENT))
				ok = false;
		return ok;
	}
	public GalaxyEngine getGalaxyEngine()
	{
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_GALAXYENGINE);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r != 0) {
			GalaxyEngine retVal = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (GalaxyEngine)factory.findOrCreateRAAPIReferenceWrapper(GalaxyEngine.class, r, true);
			return retVal;
		}
		else
			return null;
	}
	public boolean setGalaxyEngine(GalaxyEngine value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_GALAXYENGINE);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_GALAXYENGINE))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_GALAXYENGINE))
				ok = false;
		return ok;
	}
	public List<Frame> getFrame()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<Frame>(factory, rObject, factory.DATAGALAXY_FRAME); 
	}
	public boolean setFrame(Frame value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_FRAME);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_FRAME))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_FRAME))
				ok = false;
		return ok;
	}
	public List<EditGalaxyCommand> getEditGalaxyCommand()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<EditGalaxyCommand>(factory, rObject, factory.DATAGALAXY_EDITGALAXYCOMMAND); 
	}
	public boolean setEditGalaxyCommand(EditGalaxyCommand value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_EDITGALAXYCOMMAND);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_EDITGALAXYCOMMAND))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_EDITGALAXYCOMMAND))
				ok = false;
		return ok;
	}
	public List<RunConfiguration> getRunConfiguration()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RunConfiguration>(factory, rObject, factory.DATAGALAXY_RUNCONFIGURATION); 
	}
	public boolean setRunConfiguration(RunConfiguration value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_RUNCONFIGURATION);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_RUNCONFIGURATION))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_RUNCONFIGURATION))
				ok = false;
		return ok;
	}
	public List<RunConfiguration> getActiveRunConfiguration()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RunConfiguration>(factory, rObject, factory.DATAGALAXY_ACTIVERUNCONFIGURATION); 
	}
	public boolean setActiveRunConfiguration(RunConfiguration value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_ACTIVERUNCONFIGURATION);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_ACTIVERUNCONFIGURATION))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_ACTIVERUNCONFIGURATION))
				ok = false;
		return ok;
	}
	public List<RefreshGalaxyCommand> getRefreshGalaxyCommand()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<RefreshGalaxyCommand>(factory, rObject, factory.DATAGALAXY_REFRESHGALAXYCOMMAND); 
	}
	public boolean setRefreshGalaxyCommand(RefreshGalaxyCommand value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.DATAGALAXY_REFRESHGALAXYCOMMAND);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.DATAGALAXY_REFRESHGALAXYCOMMAND))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.DATAGALAXY_REFRESHGALAXYCOMMAND))
				ok = false;
		return ok;
	}
}
