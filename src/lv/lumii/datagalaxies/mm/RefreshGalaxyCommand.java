// automatically generated
package lv.lumii.datagalaxies.mm; 

import java.util.*;
import lv.lumii.tda.raapi.RAAPI;

public class RefreshGalaxyCommand
	extends Command
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
				System.err.println("Unable to delete the object "+rObject+" of type RefreshGalaxyCommand since the RAAPI wrapper does not take care of this reference.");
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
	RefreshGalaxyCommand(GalaxyEngineMetamodelFactory _factory)
	{
		super(_factory, _factory.raapi.createObject(_factory.REFRESHGALAXYCOMMAND), true);		
		factory = _factory;
		rObject = super.rObject;
		takeReference = true;
		factory.wrappers.put(rObject, this);
		/*
		factory = _factory;
		rObject = factory.raapi.createObject(factory.REFRESHGALAXYCOMMAND);			
		takeReference = true;
		factory.wrappers.put(rObject, this);
		*/
	}

	public RefreshGalaxyCommand(GalaxyEngineMetamodelFactory _factory, long _rObject, boolean _takeReference)
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
	public static Iterable<? extends RefreshGalaxyCommand> allObjects()
	{
		return allObjects(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static Iterable<? extends RefreshGalaxyCommand> allObjects(GalaxyEngineMetamodelFactory factory)
	{
		ArrayList<RefreshGalaxyCommand> retVal = new ArrayList<RefreshGalaxyCommand>();
		long it = factory.raapi.getIteratorForAllClassObjects(factory.REFRESHGALAXYCOMMAND);
		if (it == 0)
			return retVal;
		long r = factory.raapi.resolveIteratorFirst(it);
		while (r != 0) {
 			RefreshGalaxyCommand o = (RefreshGalaxyCommand)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (o == null)
				o = (RefreshGalaxyCommand)factory.findOrCreateRAAPIReferenceWrapper(RefreshGalaxyCommand.class, r, true);
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
		for (RefreshGalaxyCommand o : allObjects(factory))
			o.delete();
		return firstObject(factory) == null;
	}

	public static RefreshGalaxyCommand firstObject()
	{
		return firstObject(GalaxyEngineMetamodelFactory.eINSTANCE);
	} 

	public static RefreshGalaxyCommand firstObject(GalaxyEngineMetamodelFactory factory)
	{
		long it = factory.raapi.getIteratorForAllClassObjects(factory.REFRESHGALAXYCOMMAND);
		if (it == 0)
			return null;
		long r = factory.raapi.resolveIteratorFirst(it);
		factory.raapi.freeIterator(it);
		if (r == 0)
			return null;
		else {
			RefreshGalaxyCommand  retVal = (RefreshGalaxyCommand)factory.findOrCreateRAAPIReferenceWrapper(r, true);
			if (retVal == null)
				retVal = (RefreshGalaxyCommand)factory.findOrCreateRAAPIReferenceWrapper(RefreshGalaxyCommand.class, r, true);
			return retVal;
		}
	} 
 
	public List<GalaxyComponent> getModifiedComponent()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<GalaxyComponent>(factory, rObject, factory.REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT); 
	}
	public boolean setModifiedComponent(GalaxyComponent value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT))
				ok = false;
		return ok;
	}
	public List<DataGalaxy> getDataGalaxy()
	{
		return new GalaxyEngineMetamodel_RAAPILinkedObjectsList<DataGalaxy>(factory, rObject, factory.REFRESHGALAXYCOMMAND_DATAGALAXY); 
	}
	public boolean setDataGalaxy(DataGalaxy value)
	{
		boolean ok = true;
		long it = factory.raapi.getIteratorForLinkedObjects(rObject, factory.REFRESHGALAXYCOMMAND_DATAGALAXY);
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
				if (!factory.raapi.deleteLink(rObject, rLinked, factory.REFRESHGALAXYCOMMAND_DATAGALAXY))
					ok = false;
		}
		if (value != null)
			if (!factory.raapi.createLink(rObject, value.rObject, factory.REFRESHGALAXYCOMMAND_DATAGALAXY))
				ok = false;
		return ok;
	}
}
