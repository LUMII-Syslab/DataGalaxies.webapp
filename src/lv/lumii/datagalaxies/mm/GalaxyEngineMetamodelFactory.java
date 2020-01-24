// automatically generated
package lv.lumii.datagalaxies.mm;
import lv.lumii.tda.raapi.RAAPI;
import java.util.*;

public class GalaxyEngineMetamodelFactory
{
	// for compatibility with ECore
	public static GalaxyEngineMetamodelFactory eINSTANCE = new GalaxyEngineMetamodelFactory();

	HashMap<Long, RAAPIReferenceWrapper> wrappers =
		new HashMap<Long, RAAPIReferenceWrapper>();

	public RAAPIReferenceWrapper findOrCreateRAAPIReferenceWrapper(Class<? extends RAAPIReferenceWrapper> cls, long rObject, boolean takeReference)
	// if takeReference==true, takes care about freeing rObject
	{
		RAAPIReferenceWrapper w = wrappers.get(rObject);
		if (w != null) {
			if (takeReference)
				raapi.freeReference(rObject);
			return w;
		}

		Class<? extends RAAPIReferenceWrapper> cls1 = findClosestType(rObject);
				
		try {
			java.lang.reflect.Constructor<? extends RAAPIReferenceWrapper> c = cls1.getConstructor(GalaxyEngineMetamodelFactory.class, Long.TYPE, Boolean.TYPE);
			return (RAAPIReferenceWrapper)c.newInstance(this, rObject, takeReference);
		} catch (Throwable t1) {
			try {
				java.lang.reflect.Constructor<? extends RAAPIReferenceWrapper> c = cls.getConstructor(GalaxyEngineMetamodelFactory.class, Long.TYPE, Boolean.TYPE);
				return (RAAPIReferenceWrapper)c.newInstance(this, rObject, takeReference);				
			} catch (Throwable t) {
				return null;
			}
		}

	}

	public Class<? extends RAAPIReferenceWrapper> findClosestType(long rObject)
	{
		Class<? extends RAAPIReferenceWrapper> retVal = null;
		long rCurClass = 0;

		if (raapi.isKindOf(rObject, LAUNCHTRANSFORMATIONCOMMAND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(LAUNCHTRANSFORMATIONCOMMAND,rCurClass))) {
				retVal = LaunchTransformationCommand.class;
				rCurClass = LAUNCHTRANSFORMATIONCOMMAND;
			}
		}
		if (raapi.isKindOf(rObject, COMMAND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(COMMAND,rCurClass))) {
				retVal = Command.class;
				rCurClass = COMMAND;
			}
		}
		if (raapi.isKindOf(rObject, GALAXYENGINE)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(GALAXYENGINE,rCurClass))) {
				retVal = GalaxyEngine.class;
				rCurClass = GALAXYENGINE;
			}
		}
		if (raapi.isKindOf(rObject, DATAGALAXY)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(DATAGALAXY,rCurClass))) {
				retVal = DataGalaxy.class;
				rCurClass = DATAGALAXY;
			}
		}
		if (raapi.isKindOf(rObject, GALAXYCOMPONENT)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(GALAXYCOMPONENT,rCurClass))) {
				retVal = GalaxyComponent.class;
				rCurClass = GALAXYCOMPONENT;
			}
		}
		if (raapi.isKindOf(rObject, RUNCONFIGURATION)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(RUNCONFIGURATION,rCurClass))) {
				retVal = RunConfiguration.class;
				rCurClass = RUNCONFIGURATION;
			}
		}
		if (raapi.isKindOf(rObject, STAR)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(STAR,rCurClass))) {
				retVal = Star.class;
				rCurClass = STAR;
			}
		}
		if (raapi.isKindOf(rObject, PLANET)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(PLANET,rCurClass))) {
				retVal = Planet.class;
				rCurClass = PLANET;
			}
		}
		if (raapi.isKindOf(rObject, STELLARWIND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(STELLARWIND,rCurClass))) {
				retVal = StellarWind.class;
				rCurClass = STELLARWIND;
			}
		}
		if (raapi.isKindOf(rObject, CROSSFILTER)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CROSSFILTER,rCurClass))) {
				retVal = CrossFilter.class;
				rCurClass = CROSSFILTER;
			}
		}
		if (raapi.isKindOf(rObject, STARDATA)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(STARDATA,rCurClass))) {
				retVal = StarData.class;
				rCurClass = STARDATA;
			}
		}
		if (raapi.isKindOf(rObject, CONFIGUREPLANET)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CONFIGUREPLANET,rCurClass))) {
				retVal = ConfigurePlanet.class;
				rCurClass = CONFIGUREPLANET;
			}
		}
		if (raapi.isKindOf(rObject, VISUALIZEPLANET)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(VISUALIZEPLANET,rCurClass))) {
				retVal = VisualizePlanet.class;
				rCurClass = VISUALIZEPLANET;
			}
		}
		if (raapi.isKindOf(rObject, CONFIGURESTAR)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CONFIGURESTAR,rCurClass))) {
				retVal = ConfigureStar.class;
				rCurClass = CONFIGURESTAR;
			}
		}
		if (raapi.isKindOf(rObject, INITIALIZESTAR)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(INITIALIZESTAR,rCurClass))) {
				retVal = InitializeStar.class;
				rCurClass = INITIALIZESTAR;
			}
		}
		if (raapi.isKindOf(rObject, CONFIGURECROSSFILTER)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CONFIGURECROSSFILTER,rCurClass))) {
				retVal = ConfigureCrossFilter.class;
				rCurClass = CONFIGURECROSSFILTER;
			}
		}
		if (raapi.isKindOf(rObject, CONFIGURESTELLARWIND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CONFIGURESTELLARWIND,rCurClass))) {
				retVal = ConfigureStellarWind.class;
				rCurClass = CONFIGURESTELLARWIND;
			}
		}
		if (raapi.isKindOf(rObject, EMITSTELLARWIND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(EMITSTELLARWIND,rCurClass))) {
				retVal = EmitStellarWind.class;
				rCurClass = EMITSTELLARWIND;
			}
		}
		if (raapi.isKindOf(rObject, EDITGALAXYCOMMAND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(EDITGALAXYCOMMAND,rCurClass))) {
				retVal = EditGalaxyCommand.class;
				rCurClass = EDITGALAXYCOMMAND;
			}
		}
		if (raapi.isKindOf(rObject, FRAME)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(FRAME,rCurClass))) {
				retVal = Frame.class;
				rCurClass = FRAME;
			}
		}
		if (raapi.isKindOf(rObject, REFRESHGALAXYCOMMAND)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(REFRESHGALAXYCOMMAND,rCurClass))) {
				retVal = RefreshGalaxyCommand.class;
				rCurClass = REFRESHGALAXYCOMMAND;
			}
		}
		if (raapi.isKindOf(rObject, FINALIZESTAR)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(FINALIZESTAR,rCurClass))) {
				retVal = FinalizeStar.class;
				rCurClass = FINALIZESTAR;
			}
		}
		if (raapi.isKindOf(rObject, CLEANUPSTAR)) {
			if ((rCurClass == 0) || (raapi.isDerivedClass(CLEANUPSTAR,rCurClass))) {
				retVal = CleanupStar.class;
				rCurClass = CLEANUPSTAR;
			}
		}

		return retVal; 
	}

	public RAAPIReferenceWrapper findOrCreateRAAPIReferenceWrapper(long rObject, boolean takeReference)
		// if takeReference==true, takes care about freeing rObject
	{
		RAAPIReferenceWrapper w = wrappers.get(rObject);
		if (w != null) {
			if (takeReference)
				raapi.freeReference(rObject);
			return w;
		}
		long it = raapi.getIteratorForDirectObjectClasses(rObject);
		if (it == 0)
			return null;		
		long rClass = raapi.resolveIteratorFirst(it);
		raapi.freeIterator(it);
		if (rClass == 0)
			return null;
		if (rClass == LAUNCHTRANSFORMATIONCOMMAND)
			w = new LaunchTransformationCommand(this, rObject, takeReference);
		if (rClass == COMMAND)
			w = new Command(this, rObject, takeReference);
		if (rClass == GALAXYENGINE)
			w = new GalaxyEngine(this, rObject, takeReference);
		if (rClass == DATAGALAXY)
			w = new DataGalaxy(this, rObject, takeReference);
		if (rClass == GALAXYCOMPONENT)
			w = new GalaxyComponent(this, rObject, takeReference);
		if (rClass == RUNCONFIGURATION)
			w = new RunConfiguration(this, rObject, takeReference);
		if (rClass == STAR)
			w = new Star(this, rObject, takeReference);
		if (rClass == PLANET)
			w = new Planet(this, rObject, takeReference);
		if (rClass == STELLARWIND)
			w = new StellarWind(this, rObject, takeReference);
		if (rClass == CROSSFILTER)
			w = new CrossFilter(this, rObject, takeReference);
		if (rClass == STARDATA)
			w = new StarData(this, rObject, takeReference);
		if (rClass == CONFIGUREPLANET)
			w = new ConfigurePlanet(this, rObject, takeReference);
		if (rClass == VISUALIZEPLANET)
			w = new VisualizePlanet(this, rObject, takeReference);
		if (rClass == CONFIGURESTAR)
			w = new ConfigureStar(this, rObject, takeReference);
		if (rClass == INITIALIZESTAR)
			w = new InitializeStar(this, rObject, takeReference);
		if (rClass == CONFIGURECROSSFILTER)
			w = new ConfigureCrossFilter(this, rObject, takeReference);
		if (rClass == CONFIGURESTELLARWIND)
			w = new ConfigureStellarWind(this, rObject, takeReference);
		if (rClass == EMITSTELLARWIND)
			w = new EmitStellarWind(this, rObject, takeReference);
		if (rClass == EDITGALAXYCOMMAND)
			w = new EditGalaxyCommand(this, rObject, takeReference);
		if (rClass == FRAME)
			w = new Frame(this, rObject, takeReference);
		if (rClass == REFRESHGALAXYCOMMAND)
			w = new RefreshGalaxyCommand(this, rObject, takeReference);
		if (rClass == FINALIZESTAR)
			w = new FinalizeStar(this, rObject, takeReference);
		if (rClass == CLEANUPSTAR)
			w = new CleanupStar(this, rObject, takeReference);
		if (w==null) {
		}
		if ((w != null) && takeReference)
			wrappers.put(rObject, w);
		return w;
	}

	public boolean deleteModel()
	{
		boolean ok = true;
		if (!LaunchTransformationCommand.deleteAllObjects(this))
			ok = false;
		if (!Command.deleteAllObjects(this))
			ok = false;
		if (!GalaxyEngine.deleteAllObjects(this))
			ok = false;
		if (!DataGalaxy.deleteAllObjects(this))
			ok = false;
		if (!GalaxyComponent.deleteAllObjects(this))
			ok = false;
		if (!RunConfiguration.deleteAllObjects(this))
			ok = false;
		if (!Star.deleteAllObjects(this))
			ok = false;
		if (!Planet.deleteAllObjects(this))
			ok = false;
		if (!StellarWind.deleteAllObjects(this))
			ok = false;
		if (!CrossFilter.deleteAllObjects(this))
			ok = false;
		if (!StarData.deleteAllObjects(this))
			ok = false;
		if (!ConfigurePlanet.deleteAllObjects(this))
			ok = false;
		if (!VisualizePlanet.deleteAllObjects(this))
			ok = false;
		if (!ConfigureStar.deleteAllObjects(this))
			ok = false;
		if (!InitializeStar.deleteAllObjects(this))
			ok = false;
		if (!ConfigureCrossFilter.deleteAllObjects(this))
			ok = false;
		if (!ConfigureStellarWind.deleteAllObjects(this))
			ok = false;
		if (!EmitStellarWind.deleteAllObjects(this))
			ok = false;
		if (!EditGalaxyCommand.deleteAllObjects(this))
			ok = false;
		if (!Frame.deleteAllObjects(this))
			ok = false;
		if (!RefreshGalaxyCommand.deleteAllObjects(this))
			ok = false;
		if (!FinalizeStar.deleteAllObjects(this))
			ok = false;
		if (!CleanupStar.deleteAllObjects(this))
			ok = false;
		return ok; 
	}

	// RAAPI references:
	RAAPI raapi = null;
	public long LAUNCHTRANSFORMATIONCOMMAND = 0;
	  public long LAUNCHTRANSFORMATIONCOMMAND_URI = 0;
	public long COMMAND = 0;
	public long GALAXYENGINE = 0;
	  public long GALAXYENGINE_ONRUNEVENT = 0;
	  public long GALAXYENGINE_USEGALACTICICONS = 0;
	  public long GALAXYENGINE_DATAGALAXY = 0;
	public long DATAGALAXY = 0;
	  public long DATAGALAXY_LAYOUTINFO = 0;
	  public long DATAGALAXY_GALAXYCOMPONENT = 0;
	  public long DATAGALAXY_GALAXYENGINE = 0;
	  public long DATAGALAXY_FRAME = 0;
	  public long DATAGALAXY_EDITGALAXYCOMMAND = 0;
	  public long DATAGALAXY_RUNCONFIGURATION = 0;
	  public long DATAGALAXY_ACTIVERUNCONFIGURATION = 0;
	  public long DATAGALAXY_REFRESHGALAXYCOMMAND = 0;
	public long GALAXYCOMPONENT = 0;
	  public long GALAXYCOMPONENT_ID = 0;
	  public long GALAXYCOMPONENT_NAME = 0;
	  public long GALAXYCOMPONENT_STATE = 0;
	  public long GALAXYCOMPONENT_STATEMESSAGE = 0;
	  public long GALAXYCOMPONENT_LOCATION = 0;
	  public long GALAXYCOMPONENT_DATAGALAXY = 0;
	  public long GALAXYCOMPONENT_RC1 = 0;
	  public long GALAXYCOMPONENT_RC2 = 0;
	  public long GALAXYCOMPONENT_RC3 = 0;
	  public long GALAXYCOMPONENT_REFRESHGALAXYCOMMAND = 0;
	  public long GALAXYCOMPONENT_FRAME = 0;
	public long RUNCONFIGURATION = 0;
	  public long RUNCONFIGURATION_NAME = 0;
	  public long RUNCONFIGURATION_DESCRIPTION = 0;
	  public long RUNCONFIGURATION_VISIBLECOMPONENT = 0;
	  public long RUNCONFIGURATION_MUSTCONFIGURECOMPONENT = 0;
	  public long RUNCONFIGURATION_DEACTIVATEDCOMPONENT = 0;
	  public long RUNCONFIGURATION_DATAGALAXY = 0;
	  public long RUNCONFIGURATION_DG1 = 0;
	public long STAR = 0;
	  public long STAR_EMPTYONINIT = 0;
	  public long STAR_STARDATATYPE = 0;
	  public long STAR_PLANET = 0;
	  public long STAR_CONSUMER = 0;
	  public long STAR_PRODUCER = 0;
	  public long STAR_STARDATA = 0;
	  public long STAR_CONFIGURESTAR = 0;
	  public long STAR_INITIALIZESTAR = 0;
	  public long STAR_FINALIZESTAR = 0;
	  public long STAR_CLEANUPSTAR = 0;
	public long PLANET = 0;
	  public long PLANET_STAR = 0;
	  public long PLANET_CROSSFILTER = 0;
	  public long PLANET_CONFIGUREPLANET = 0;
	  public long PLANET_VISUALIZEPLANET = 0;
	public long STELLARWIND = 0;
	  public long STELLARWIND_DESCRIPTION = 0;
	  public long STELLARWIND_SOURCE = 0;
	  public long STELLARWIND_TARGET = 0;
	  public long STELLARWIND_CROSSFILTER = 0;
	  public long STELLARWIND_CONFIGURESTELLARWIND = 0;
	  public long STELLARWIND_EMITSTELLARWIND = 0;
	public long CROSSFILTER = 0;
	  public long CROSSFILTER_PLANET = 0;
	  public long CROSSFILTER_STELLARWIND = 0;
	  public long CROSSFILTER_CONFIGURECROSSFILTER = 0;
	public long STARDATA = 0;
	  public long STARDATA_STAR = 0;
	public long CONFIGUREPLANET = 0;
	  public long CONFIGUREPLANET_FRAMELOCATION = 0;
	  public long CONFIGUREPLANET_PLANET = 0;
	public long VISUALIZEPLANET = 0;
	  public long VISUALIZEPLANET_FRAMELOCATION = 0;
	  public long VISUALIZEPLANET_PLANET = 0;
	public long CONFIGURESTAR = 0;
	  public long CONFIGURESTAR_FRAMELOCATION = 0;
	  public long CONFIGURESTAR_STAR = 0;
	public long INITIALIZESTAR = 0;
	  public long INITIALIZESTAR_STAR = 0;
	public long CONFIGURECROSSFILTER = 0;
	  public long CONFIGURECROSSFILTER_FRAMELOCATION = 0;
	  public long CONFIGURECROSSFILTER_CROSSFILTER = 0;
	public long CONFIGURESTELLARWIND = 0;
	  public long CONFIGURESTELLARWIND_FRAMELOCATION = 0;
	  public long CONFIGURESTELLARWIND_STELLARWIND = 0;
	public long EMITSTELLARWIND = 0;
	  public long EMITSTELLARWIND_STELLARWIND = 0;
	public long EDITGALAXYCOMMAND = 0;
	  public long EDITGALAXYCOMMAND_DATAGALAXY = 0;
	public long FRAME = 0;
	  public long FRAME_DATAGALAXY = 0;
	  public long FRAME_GALAXYCOMPONENT = 0;
	public long REFRESHGALAXYCOMMAND = 0;
	  public long REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT = 0;
	  public long REFRESHGALAXYCOMMAND_DATAGALAXY = 0;
	public long FINALIZESTAR = 0;
	  public long FINALIZESTAR_STAR = 0;
	public long CLEANUPSTAR = 0;
	  public long CLEANUPSTAR_STAR = 0;

	public class ElementReferenceException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public ElementReferenceException(String msg)
		{
			super(msg);
		}
	}

	public void unsetRAAPI()
	{
		try {
			setRAAPI(null, null, false);
		}
		catch (Throwable t)
		{
		}
	}

	public RAAPI getRAAPI()
	{
		return raapi;
	}

	public void setRAAPI(RAAPI _raapi, String prefix, boolean insertMetamodel) throws ElementReferenceException // set RAAPI to null to free references
	{
		if (raapi != null) {
			// freeing object-level references...
			for (Long r : wrappers.keySet())
				raapi.freeReference(r);
			wrappers.clear();
			// freeing class-level references...
			if (LAUNCHTRANSFORMATIONCOMMAND != 0) {
				raapi.freeReference(LAUNCHTRANSFORMATIONCOMMAND);
				LAUNCHTRANSFORMATIONCOMMAND = 0;
			}
	  		if (LAUNCHTRANSFORMATIONCOMMAND_URI != 0) {
				raapi.freeReference(LAUNCHTRANSFORMATIONCOMMAND_URI);
				LAUNCHTRANSFORMATIONCOMMAND_URI = 0;
			}
			if (COMMAND != 0) {
				raapi.freeReference(COMMAND);
				COMMAND = 0;
			}
			if (GALAXYENGINE != 0) {
				raapi.freeReference(GALAXYENGINE);
				GALAXYENGINE = 0;
			}
	  		if (GALAXYENGINE_ONRUNEVENT != 0) {
				raapi.freeReference(GALAXYENGINE_ONRUNEVENT);
				GALAXYENGINE_ONRUNEVENT = 0;
			}
	  		if (GALAXYENGINE_USEGALACTICICONS != 0) {
				raapi.freeReference(GALAXYENGINE_USEGALACTICICONS);
				GALAXYENGINE_USEGALACTICICONS = 0;
			}
	  		if (GALAXYENGINE_DATAGALAXY != 0) {
				raapi.freeReference(GALAXYENGINE_DATAGALAXY);
				GALAXYENGINE_DATAGALAXY = 0;
			}
			if (DATAGALAXY != 0) {
				raapi.freeReference(DATAGALAXY);
				DATAGALAXY = 0;
			}
	  		if (DATAGALAXY_LAYOUTINFO != 0) {
				raapi.freeReference(DATAGALAXY_LAYOUTINFO);
				DATAGALAXY_LAYOUTINFO = 0;
			}
	  		if (DATAGALAXY_GALAXYCOMPONENT != 0) {
				raapi.freeReference(DATAGALAXY_GALAXYCOMPONENT);
				DATAGALAXY_GALAXYCOMPONENT = 0;
			}
	  		if (DATAGALAXY_GALAXYENGINE != 0) {
				raapi.freeReference(DATAGALAXY_GALAXYENGINE);
				DATAGALAXY_GALAXYENGINE = 0;
			}
	  		if (DATAGALAXY_FRAME != 0) {
				raapi.freeReference(DATAGALAXY_FRAME);
				DATAGALAXY_FRAME = 0;
			}
	  		if (DATAGALAXY_EDITGALAXYCOMMAND != 0) {
				raapi.freeReference(DATAGALAXY_EDITGALAXYCOMMAND);
				DATAGALAXY_EDITGALAXYCOMMAND = 0;
			}
	  		if (DATAGALAXY_RUNCONFIGURATION != 0) {
				raapi.freeReference(DATAGALAXY_RUNCONFIGURATION);
				DATAGALAXY_RUNCONFIGURATION = 0;
			}
	  		if (DATAGALAXY_ACTIVERUNCONFIGURATION != 0) {
				raapi.freeReference(DATAGALAXY_ACTIVERUNCONFIGURATION);
				DATAGALAXY_ACTIVERUNCONFIGURATION = 0;
			}
	  		if (DATAGALAXY_REFRESHGALAXYCOMMAND != 0) {
				raapi.freeReference(DATAGALAXY_REFRESHGALAXYCOMMAND);
				DATAGALAXY_REFRESHGALAXYCOMMAND = 0;
			}
			if (GALAXYCOMPONENT != 0) {
				raapi.freeReference(GALAXYCOMPONENT);
				GALAXYCOMPONENT = 0;
			}
	  		if (GALAXYCOMPONENT_ID != 0) {
				raapi.freeReference(GALAXYCOMPONENT_ID);
				GALAXYCOMPONENT_ID = 0;
			}
	  		if (GALAXYCOMPONENT_NAME != 0) {
				raapi.freeReference(GALAXYCOMPONENT_NAME);
				GALAXYCOMPONENT_NAME = 0;
			}
	  		if (GALAXYCOMPONENT_STATE != 0) {
				raapi.freeReference(GALAXYCOMPONENT_STATE);
				GALAXYCOMPONENT_STATE = 0;
			}
	  		if (GALAXYCOMPONENT_STATEMESSAGE != 0) {
				raapi.freeReference(GALAXYCOMPONENT_STATEMESSAGE);
				GALAXYCOMPONENT_STATEMESSAGE = 0;
			}
	  		if (GALAXYCOMPONENT_LOCATION != 0) {
				raapi.freeReference(GALAXYCOMPONENT_LOCATION);
				GALAXYCOMPONENT_LOCATION = 0;
			}
	  		if (GALAXYCOMPONENT_DATAGALAXY != 0) {
				raapi.freeReference(GALAXYCOMPONENT_DATAGALAXY);
				GALAXYCOMPONENT_DATAGALAXY = 0;
			}
	  		if (GALAXYCOMPONENT_RC1 != 0) {
				raapi.freeReference(GALAXYCOMPONENT_RC1);
				GALAXYCOMPONENT_RC1 = 0;
			}
	  		if (GALAXYCOMPONENT_RC2 != 0) {
				raapi.freeReference(GALAXYCOMPONENT_RC2);
				GALAXYCOMPONENT_RC2 = 0;
			}
	  		if (GALAXYCOMPONENT_RC3 != 0) {
				raapi.freeReference(GALAXYCOMPONENT_RC3);
				GALAXYCOMPONENT_RC3 = 0;
			}
	  		if (GALAXYCOMPONENT_REFRESHGALAXYCOMMAND != 0) {
				raapi.freeReference(GALAXYCOMPONENT_REFRESHGALAXYCOMMAND);
				GALAXYCOMPONENT_REFRESHGALAXYCOMMAND = 0;
			}
	  		if (GALAXYCOMPONENT_FRAME != 0) {
				raapi.freeReference(GALAXYCOMPONENT_FRAME);
				GALAXYCOMPONENT_FRAME = 0;
			}
			if (RUNCONFIGURATION != 0) {
				raapi.freeReference(RUNCONFIGURATION);
				RUNCONFIGURATION = 0;
			}
	  		if (RUNCONFIGURATION_NAME != 0) {
				raapi.freeReference(RUNCONFIGURATION_NAME);
				RUNCONFIGURATION_NAME = 0;
			}
	  		if (RUNCONFIGURATION_DESCRIPTION != 0) {
				raapi.freeReference(RUNCONFIGURATION_DESCRIPTION);
				RUNCONFIGURATION_DESCRIPTION = 0;
			}
	  		if (RUNCONFIGURATION_VISIBLECOMPONENT != 0) {
				raapi.freeReference(RUNCONFIGURATION_VISIBLECOMPONENT);
				RUNCONFIGURATION_VISIBLECOMPONENT = 0;
			}
	  		if (RUNCONFIGURATION_MUSTCONFIGURECOMPONENT != 0) {
				raapi.freeReference(RUNCONFIGURATION_MUSTCONFIGURECOMPONENT);
				RUNCONFIGURATION_MUSTCONFIGURECOMPONENT = 0;
			}
	  		if (RUNCONFIGURATION_DEACTIVATEDCOMPONENT != 0) {
				raapi.freeReference(RUNCONFIGURATION_DEACTIVATEDCOMPONENT);
				RUNCONFIGURATION_DEACTIVATEDCOMPONENT = 0;
			}
	  		if (RUNCONFIGURATION_DATAGALAXY != 0) {
				raapi.freeReference(RUNCONFIGURATION_DATAGALAXY);
				RUNCONFIGURATION_DATAGALAXY = 0;
			}
	  		if (RUNCONFIGURATION_DG1 != 0) {
				raapi.freeReference(RUNCONFIGURATION_DG1);
				RUNCONFIGURATION_DG1 = 0;
			}
			if (STAR != 0) {
				raapi.freeReference(STAR);
				STAR = 0;
			}
	  		if (STAR_EMPTYONINIT != 0) {
				raapi.freeReference(STAR_EMPTYONINIT);
				STAR_EMPTYONINIT = 0;
			}
	  		if (STAR_STARDATATYPE != 0) {
				raapi.freeReference(STAR_STARDATATYPE);
				STAR_STARDATATYPE = 0;
			}
	  		if (STAR_PLANET != 0) {
				raapi.freeReference(STAR_PLANET);
				STAR_PLANET = 0;
			}
	  		if (STAR_CONSUMER != 0) {
				raapi.freeReference(STAR_CONSUMER);
				STAR_CONSUMER = 0;
			}
	  		if (STAR_PRODUCER != 0) {
				raapi.freeReference(STAR_PRODUCER);
				STAR_PRODUCER = 0;
			}
	  		if (STAR_STARDATA != 0) {
				raapi.freeReference(STAR_STARDATA);
				STAR_STARDATA = 0;
			}
	  		if (STAR_CONFIGURESTAR != 0) {
				raapi.freeReference(STAR_CONFIGURESTAR);
				STAR_CONFIGURESTAR = 0;
			}
	  		if (STAR_INITIALIZESTAR != 0) {
				raapi.freeReference(STAR_INITIALIZESTAR);
				STAR_INITIALIZESTAR = 0;
			}
	  		if (STAR_FINALIZESTAR != 0) {
				raapi.freeReference(STAR_FINALIZESTAR);
				STAR_FINALIZESTAR = 0;
			}
	  		if (STAR_CLEANUPSTAR != 0) {
				raapi.freeReference(STAR_CLEANUPSTAR);
				STAR_CLEANUPSTAR = 0;
			}
			if (PLANET != 0) {
				raapi.freeReference(PLANET);
				PLANET = 0;
			}
	  		if (PLANET_STAR != 0) {
				raapi.freeReference(PLANET_STAR);
				PLANET_STAR = 0;
			}
	  		if (PLANET_CROSSFILTER != 0) {
				raapi.freeReference(PLANET_CROSSFILTER);
				PLANET_CROSSFILTER = 0;
			}
	  		if (PLANET_CONFIGUREPLANET != 0) {
				raapi.freeReference(PLANET_CONFIGUREPLANET);
				PLANET_CONFIGUREPLANET = 0;
			}
	  		if (PLANET_VISUALIZEPLANET != 0) {
				raapi.freeReference(PLANET_VISUALIZEPLANET);
				PLANET_VISUALIZEPLANET = 0;
			}
			if (STELLARWIND != 0) {
				raapi.freeReference(STELLARWIND);
				STELLARWIND = 0;
			}
	  		if (STELLARWIND_DESCRIPTION != 0) {
				raapi.freeReference(STELLARWIND_DESCRIPTION);
				STELLARWIND_DESCRIPTION = 0;
			}
	  		if (STELLARWIND_SOURCE != 0) {
				raapi.freeReference(STELLARWIND_SOURCE);
				STELLARWIND_SOURCE = 0;
			}
	  		if (STELLARWIND_TARGET != 0) {
				raapi.freeReference(STELLARWIND_TARGET);
				STELLARWIND_TARGET = 0;
			}
	  		if (STELLARWIND_CROSSFILTER != 0) {
				raapi.freeReference(STELLARWIND_CROSSFILTER);
				STELLARWIND_CROSSFILTER = 0;
			}
	  		if (STELLARWIND_CONFIGURESTELLARWIND != 0) {
				raapi.freeReference(STELLARWIND_CONFIGURESTELLARWIND);
				STELLARWIND_CONFIGURESTELLARWIND = 0;
			}
	  		if (STELLARWIND_EMITSTELLARWIND != 0) {
				raapi.freeReference(STELLARWIND_EMITSTELLARWIND);
				STELLARWIND_EMITSTELLARWIND = 0;
			}
			if (CROSSFILTER != 0) {
				raapi.freeReference(CROSSFILTER);
				CROSSFILTER = 0;
			}
	  		if (CROSSFILTER_PLANET != 0) {
				raapi.freeReference(CROSSFILTER_PLANET);
				CROSSFILTER_PLANET = 0;
			}
	  		if (CROSSFILTER_STELLARWIND != 0) {
				raapi.freeReference(CROSSFILTER_STELLARWIND);
				CROSSFILTER_STELLARWIND = 0;
			}
	  		if (CROSSFILTER_CONFIGURECROSSFILTER != 0) {
				raapi.freeReference(CROSSFILTER_CONFIGURECROSSFILTER);
				CROSSFILTER_CONFIGURECROSSFILTER = 0;
			}
			if (STARDATA != 0) {
				raapi.freeReference(STARDATA);
				STARDATA = 0;
			}
	  		if (STARDATA_STAR != 0) {
				raapi.freeReference(STARDATA_STAR);
				STARDATA_STAR = 0;
			}
			if (CONFIGUREPLANET != 0) {
				raapi.freeReference(CONFIGUREPLANET);
				CONFIGUREPLANET = 0;
			}
	  		if (CONFIGUREPLANET_FRAMELOCATION != 0) {
				raapi.freeReference(CONFIGUREPLANET_FRAMELOCATION);
				CONFIGUREPLANET_FRAMELOCATION = 0;
			}
	  		if (CONFIGUREPLANET_PLANET != 0) {
				raapi.freeReference(CONFIGUREPLANET_PLANET);
				CONFIGUREPLANET_PLANET = 0;
			}
			if (VISUALIZEPLANET != 0) {
				raapi.freeReference(VISUALIZEPLANET);
				VISUALIZEPLANET = 0;
			}
	  		if (VISUALIZEPLANET_FRAMELOCATION != 0) {
				raapi.freeReference(VISUALIZEPLANET_FRAMELOCATION);
				VISUALIZEPLANET_FRAMELOCATION = 0;
			}
	  		if (VISUALIZEPLANET_PLANET != 0) {
				raapi.freeReference(VISUALIZEPLANET_PLANET);
				VISUALIZEPLANET_PLANET = 0;
			}
			if (CONFIGURESTAR != 0) {
				raapi.freeReference(CONFIGURESTAR);
				CONFIGURESTAR = 0;
			}
	  		if (CONFIGURESTAR_FRAMELOCATION != 0) {
				raapi.freeReference(CONFIGURESTAR_FRAMELOCATION);
				CONFIGURESTAR_FRAMELOCATION = 0;
			}
	  		if (CONFIGURESTAR_STAR != 0) {
				raapi.freeReference(CONFIGURESTAR_STAR);
				CONFIGURESTAR_STAR = 0;
			}
			if (INITIALIZESTAR != 0) {
				raapi.freeReference(INITIALIZESTAR);
				INITIALIZESTAR = 0;
			}
	  		if (INITIALIZESTAR_STAR != 0) {
				raapi.freeReference(INITIALIZESTAR_STAR);
				INITIALIZESTAR_STAR = 0;
			}
			if (CONFIGURECROSSFILTER != 0) {
				raapi.freeReference(CONFIGURECROSSFILTER);
				CONFIGURECROSSFILTER = 0;
			}
	  		if (CONFIGURECROSSFILTER_FRAMELOCATION != 0) {
				raapi.freeReference(CONFIGURECROSSFILTER_FRAMELOCATION);
				CONFIGURECROSSFILTER_FRAMELOCATION = 0;
			}
	  		if (CONFIGURECROSSFILTER_CROSSFILTER != 0) {
				raapi.freeReference(CONFIGURECROSSFILTER_CROSSFILTER);
				CONFIGURECROSSFILTER_CROSSFILTER = 0;
			}
			if (CONFIGURESTELLARWIND != 0) {
				raapi.freeReference(CONFIGURESTELLARWIND);
				CONFIGURESTELLARWIND = 0;
			}
	  		if (CONFIGURESTELLARWIND_FRAMELOCATION != 0) {
				raapi.freeReference(CONFIGURESTELLARWIND_FRAMELOCATION);
				CONFIGURESTELLARWIND_FRAMELOCATION = 0;
			}
	  		if (CONFIGURESTELLARWIND_STELLARWIND != 0) {
				raapi.freeReference(CONFIGURESTELLARWIND_STELLARWIND);
				CONFIGURESTELLARWIND_STELLARWIND = 0;
			}
			if (EMITSTELLARWIND != 0) {
				raapi.freeReference(EMITSTELLARWIND);
				EMITSTELLARWIND = 0;
			}
	  		if (EMITSTELLARWIND_STELLARWIND != 0) {
				raapi.freeReference(EMITSTELLARWIND_STELLARWIND);
				EMITSTELLARWIND_STELLARWIND = 0;
			}
			if (EDITGALAXYCOMMAND != 0) {
				raapi.freeReference(EDITGALAXYCOMMAND);
				EDITGALAXYCOMMAND = 0;
			}
	  		if (EDITGALAXYCOMMAND_DATAGALAXY != 0) {
				raapi.freeReference(EDITGALAXYCOMMAND_DATAGALAXY);
				EDITGALAXYCOMMAND_DATAGALAXY = 0;
			}
			if (FRAME != 0) {
				raapi.freeReference(FRAME);
				FRAME = 0;
			}
	  		if (FRAME_DATAGALAXY != 0) {
				raapi.freeReference(FRAME_DATAGALAXY);
				FRAME_DATAGALAXY = 0;
			}
	  		if (FRAME_GALAXYCOMPONENT != 0) {
				raapi.freeReference(FRAME_GALAXYCOMPONENT);
				FRAME_GALAXYCOMPONENT = 0;
			}
			if (REFRESHGALAXYCOMMAND != 0) {
				raapi.freeReference(REFRESHGALAXYCOMMAND);
				REFRESHGALAXYCOMMAND = 0;
			}
	  		if (REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT != 0) {
				raapi.freeReference(REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT);
				REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT = 0;
			}
	  		if (REFRESHGALAXYCOMMAND_DATAGALAXY != 0) {
				raapi.freeReference(REFRESHGALAXYCOMMAND_DATAGALAXY);
				REFRESHGALAXYCOMMAND_DATAGALAXY = 0;
			}
			if (FINALIZESTAR != 0) {
				raapi.freeReference(FINALIZESTAR);
				FINALIZESTAR = 0;
			}
	  		if (FINALIZESTAR_STAR != 0) {
				raapi.freeReference(FINALIZESTAR_STAR);
				FINALIZESTAR_STAR = 0;
			}
			if (CLEANUPSTAR != 0) {
				raapi.freeReference(CLEANUPSTAR);
				CLEANUPSTAR = 0;
			}
	  		if (CLEANUPSTAR_STAR != 0) {
				raapi.freeReference(CLEANUPSTAR_STAR);
				CLEANUPSTAR_STAR = 0;
			}
		}

		raapi = _raapi;

		if (raapi != null) {
			// initializing class references...
			LAUNCHTRANSFORMATIONCOMMAND = raapi.findClass("LaunchTransformationCommand");
			if ((LAUNCHTRANSFORMATIONCOMMAND == 0) && (prefix != null))
				LAUNCHTRANSFORMATIONCOMMAND = raapi.findClass(prefix+"LaunchTransformationCommand");
			if ((LAUNCHTRANSFORMATIONCOMMAND == 0) && insertMetamodel)
				LAUNCHTRANSFORMATIONCOMMAND = raapi.createClass(prefix+"LaunchTransformationCommand");
			if (LAUNCHTRANSFORMATIONCOMMAND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class LaunchTransformationCommand.");
			}
			COMMAND = raapi.findClass("Command");
			if ((COMMAND == 0) && (prefix != null))
				COMMAND = raapi.findClass(prefix+"Command");
			if ((COMMAND == 0) && insertMetamodel)
				COMMAND = raapi.createClass(prefix+"Command");
			if (COMMAND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class Command.");
			}
			GALAXYENGINE = raapi.findClass("GalaxyEngine");
			if ((GALAXYENGINE == 0) && (prefix != null))
				GALAXYENGINE = raapi.findClass(prefix+"GalaxyEngine");
			if ((GALAXYENGINE == 0) && insertMetamodel)
				GALAXYENGINE = raapi.createClass(prefix+"GalaxyEngine");
			if (GALAXYENGINE == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class GalaxyEngine.");
			}
			DATAGALAXY = raapi.findClass("DataGalaxy");
			if ((DATAGALAXY == 0) && (prefix != null))
				DATAGALAXY = raapi.findClass(prefix+"DataGalaxy");
			if ((DATAGALAXY == 0) && insertMetamodel)
				DATAGALAXY = raapi.createClass(prefix+"DataGalaxy");
			if (DATAGALAXY == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class DataGalaxy.");
			}
			GALAXYCOMPONENT = raapi.findClass("GalaxyComponent");
			if ((GALAXYCOMPONENT == 0) && (prefix != null))
				GALAXYCOMPONENT = raapi.findClass(prefix+"GalaxyComponent");
			if ((GALAXYCOMPONENT == 0) && insertMetamodel)
				GALAXYCOMPONENT = raapi.createClass(prefix+"GalaxyComponent");
			if (GALAXYCOMPONENT == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class GalaxyComponent.");
			}
			RUNCONFIGURATION = raapi.findClass("RunConfiguration");
			if ((RUNCONFIGURATION == 0) && (prefix != null))
				RUNCONFIGURATION = raapi.findClass(prefix+"RunConfiguration");
			if ((RUNCONFIGURATION == 0) && insertMetamodel)
				RUNCONFIGURATION = raapi.createClass(prefix+"RunConfiguration");
			if (RUNCONFIGURATION == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class RunConfiguration.");
			}
			STAR = raapi.findClass("Star");
			if ((STAR == 0) && (prefix != null))
				STAR = raapi.findClass(prefix+"Star");
			if ((STAR == 0) && insertMetamodel)
				STAR = raapi.createClass(prefix+"Star");
			if (STAR == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class Star.");
			}
			PLANET = raapi.findClass("Planet");
			if ((PLANET == 0) && (prefix != null))
				PLANET = raapi.findClass(prefix+"Planet");
			if ((PLANET == 0) && insertMetamodel)
				PLANET = raapi.createClass(prefix+"Planet");
			if (PLANET == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class Planet.");
			}
			STELLARWIND = raapi.findClass("StellarWind");
			if ((STELLARWIND == 0) && (prefix != null))
				STELLARWIND = raapi.findClass(prefix+"StellarWind");
			if ((STELLARWIND == 0) && insertMetamodel)
				STELLARWIND = raapi.createClass(prefix+"StellarWind");
			if (STELLARWIND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class StellarWind.");
			}
			CROSSFILTER = raapi.findClass("CrossFilter");
			if ((CROSSFILTER == 0) && (prefix != null))
				CROSSFILTER = raapi.findClass(prefix+"CrossFilter");
			if ((CROSSFILTER == 0) && insertMetamodel)
				CROSSFILTER = raapi.createClass(prefix+"CrossFilter");
			if (CROSSFILTER == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class CrossFilter.");
			}
			STARDATA = raapi.findClass("StarData");
			if ((STARDATA == 0) && (prefix != null))
				STARDATA = raapi.findClass(prefix+"StarData");
			if ((STARDATA == 0) && insertMetamodel)
				STARDATA = raapi.createClass(prefix+"StarData");
			if (STARDATA == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class StarData.");
			}
			CONFIGUREPLANET = raapi.findClass("ConfigurePlanet");
			if ((CONFIGUREPLANET == 0) && (prefix != null))
				CONFIGUREPLANET = raapi.findClass(prefix+"ConfigurePlanet");
			if ((CONFIGUREPLANET == 0) && insertMetamodel)
				CONFIGUREPLANET = raapi.createClass(prefix+"ConfigurePlanet");
			if (CONFIGUREPLANET == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class ConfigurePlanet.");
			}
			VISUALIZEPLANET = raapi.findClass("VisualizePlanet");
			if ((VISUALIZEPLANET == 0) && (prefix != null))
				VISUALIZEPLANET = raapi.findClass(prefix+"VisualizePlanet");
			if ((VISUALIZEPLANET == 0) && insertMetamodel)
				VISUALIZEPLANET = raapi.createClass(prefix+"VisualizePlanet");
			if (VISUALIZEPLANET == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class VisualizePlanet.");
			}
			CONFIGURESTAR = raapi.findClass("ConfigureStar");
			if ((CONFIGURESTAR == 0) && (prefix != null))
				CONFIGURESTAR = raapi.findClass(prefix+"ConfigureStar");
			if ((CONFIGURESTAR == 0) && insertMetamodel)
				CONFIGURESTAR = raapi.createClass(prefix+"ConfigureStar");
			if (CONFIGURESTAR == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class ConfigureStar.");
			}
			INITIALIZESTAR = raapi.findClass("InitializeStar");
			if ((INITIALIZESTAR == 0) && (prefix != null))
				INITIALIZESTAR = raapi.findClass(prefix+"InitializeStar");
			if ((INITIALIZESTAR == 0) && insertMetamodel)
				INITIALIZESTAR = raapi.createClass(prefix+"InitializeStar");
			if (INITIALIZESTAR == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class InitializeStar.");
			}
			CONFIGURECROSSFILTER = raapi.findClass("ConfigureCrossFilter");
			if ((CONFIGURECROSSFILTER == 0) && (prefix != null))
				CONFIGURECROSSFILTER = raapi.findClass(prefix+"ConfigureCrossFilter");
			if ((CONFIGURECROSSFILTER == 0) && insertMetamodel)
				CONFIGURECROSSFILTER = raapi.createClass(prefix+"ConfigureCrossFilter");
			if (CONFIGURECROSSFILTER == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class ConfigureCrossFilter.");
			}
			CONFIGURESTELLARWIND = raapi.findClass("ConfigureStellarWind");
			if ((CONFIGURESTELLARWIND == 0) && (prefix != null))
				CONFIGURESTELLARWIND = raapi.findClass(prefix+"ConfigureStellarWind");
			if ((CONFIGURESTELLARWIND == 0) && insertMetamodel)
				CONFIGURESTELLARWIND = raapi.createClass(prefix+"ConfigureStellarWind");
			if (CONFIGURESTELLARWIND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class ConfigureStellarWind.");
			}
			EMITSTELLARWIND = raapi.findClass("EmitStellarWind");
			if ((EMITSTELLARWIND == 0) && (prefix != null))
				EMITSTELLARWIND = raapi.findClass(prefix+"EmitStellarWind");
			if ((EMITSTELLARWIND == 0) && insertMetamodel)
				EMITSTELLARWIND = raapi.createClass(prefix+"EmitStellarWind");
			if (EMITSTELLARWIND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class EmitStellarWind.");
			}
			EDITGALAXYCOMMAND = raapi.findClass("EditGalaxyCommand");
			if ((EDITGALAXYCOMMAND == 0) && (prefix != null))
				EDITGALAXYCOMMAND = raapi.findClass(prefix+"EditGalaxyCommand");
			if ((EDITGALAXYCOMMAND == 0) && insertMetamodel)
				EDITGALAXYCOMMAND = raapi.createClass(prefix+"EditGalaxyCommand");
			if (EDITGALAXYCOMMAND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class EditGalaxyCommand.");
			}
			FRAME = raapi.findClass("Frame");
			if ((FRAME == 0) && (prefix != null))
				FRAME = raapi.findClass(prefix+"Frame");
			if ((FRAME == 0) && insertMetamodel)
				FRAME = raapi.createClass(prefix+"Frame");
			if (FRAME == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class Frame.");
			}
			REFRESHGALAXYCOMMAND = raapi.findClass("RefreshGalaxyCommand");
			if ((REFRESHGALAXYCOMMAND == 0) && (prefix != null))
				REFRESHGALAXYCOMMAND = raapi.findClass(prefix+"RefreshGalaxyCommand");
			if ((REFRESHGALAXYCOMMAND == 0) && insertMetamodel)
				REFRESHGALAXYCOMMAND = raapi.createClass(prefix+"RefreshGalaxyCommand");
			if (REFRESHGALAXYCOMMAND == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class RefreshGalaxyCommand.");
			}
			FINALIZESTAR = raapi.findClass("FinalizeStar");
			if ((FINALIZESTAR == 0) && (prefix != null))
				FINALIZESTAR = raapi.findClass(prefix+"FinalizeStar");
			if ((FINALIZESTAR == 0) && insertMetamodel)
				FINALIZESTAR = raapi.createClass(prefix+"FinalizeStar");
			if (FINALIZESTAR == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class FinalizeStar.");
			}
			CLEANUPSTAR = raapi.findClass("CleanupStar");
			if ((CLEANUPSTAR == 0) && (prefix != null))
				CLEANUPSTAR = raapi.findClass(prefix+"CleanupStar");
			if ((CLEANUPSTAR == 0) && insertMetamodel)
				CLEANUPSTAR = raapi.createClass(prefix+"CleanupStar");
			if (CLEANUPSTAR == 0) {				
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the class CleanupStar.");
			}

			// creating generalizations, if they do not exist...
			if (insertMetamodel) {
				if (!raapi.isDirectSubClass(LAUNCHTRANSFORMATIONCOMMAND, COMMAND))
					if (!raapi.createGeneralization(LAUNCHTRANSFORMATIONCOMMAND, COMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes LaunchTransformationCommand and Command.");
					}
				if (!raapi.isDirectSubClass(STAR, GALAXYCOMPONENT))
					if (!raapi.createGeneralization(STAR, GALAXYCOMPONENT)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes Star and GalaxyComponent.");
					}
				if (!raapi.isDirectSubClass(PLANET, GALAXYCOMPONENT))
					if (!raapi.createGeneralization(PLANET, GALAXYCOMPONENT)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes Planet and GalaxyComponent.");
					}
				if (!raapi.isDirectSubClass(STELLARWIND, GALAXYCOMPONENT))
					if (!raapi.createGeneralization(STELLARWIND, GALAXYCOMPONENT)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes StellarWind and GalaxyComponent.");
					}
				if (!raapi.isDirectSubClass(CROSSFILTER, GALAXYCOMPONENT))
					if (!raapi.createGeneralization(CROSSFILTER, GALAXYCOMPONENT)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes CrossFilter and GalaxyComponent.");
					}
				if (!raapi.isDirectSubClass(CONFIGUREPLANET, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(CONFIGUREPLANET, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes ConfigurePlanet and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(VISUALIZEPLANET, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(VISUALIZEPLANET, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes VisualizePlanet and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(CONFIGURESTAR, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(CONFIGURESTAR, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes ConfigureStar and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(INITIALIZESTAR, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(INITIALIZESTAR, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes InitializeStar and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(CONFIGURECROSSFILTER, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(CONFIGURECROSSFILTER, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes ConfigureCrossFilter and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(CONFIGURESTELLARWIND, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(CONFIGURESTELLARWIND, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes ConfigureStellarWind and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(EMITSTELLARWIND, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(EMITSTELLARWIND, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes EmitStellarWind and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(EDITGALAXYCOMMAND, COMMAND))
					if (!raapi.createGeneralization(EDITGALAXYCOMMAND, COMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes EditGalaxyCommand and Command.");
					}
				if (!raapi.isDirectSubClass(REFRESHGALAXYCOMMAND, COMMAND))
					if (!raapi.createGeneralization(REFRESHGALAXYCOMMAND, COMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes RefreshGalaxyCommand and Command.");
					}
				if (!raapi.isDirectSubClass(FINALIZESTAR, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(FINALIZESTAR, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes FinalizeStar and LaunchTransformationCommand.");
					}
				if (!raapi.isDirectSubClass(CLEANUPSTAR, LAUNCHTRANSFORMATIONCOMMAND))
					if (!raapi.createGeneralization(CLEANUPSTAR, LAUNCHTRANSFORMATIONCOMMAND)) {
						setRAAPI(null, null, false); // freeing references initialized so far...
						throw new ElementReferenceException("Error creating a generalization between classes CleanupStar and LaunchTransformationCommand.");
					}
			}

			// initializing references for attributes and associations...
			LAUNCHTRANSFORMATIONCOMMAND_URI = raapi.findAttribute(LAUNCHTRANSFORMATIONCOMMAND, "uri");
			if ((LAUNCHTRANSFORMATIONCOMMAND_URI == 0) && insertMetamodel)
				LAUNCHTRANSFORMATIONCOMMAND_URI = raapi.createAttribute(LAUNCHTRANSFORMATIONCOMMAND, "uri", raapi.findPrimitiveDataType("String"));
			if (LAUNCHTRANSFORMATIONCOMMAND_URI == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute uri of the class LaunchTransformationCommand.");
			}
			GALAXYENGINE_ONRUNEVENT = raapi.findAttribute(GALAXYENGINE, "onRunEvent");
			if ((GALAXYENGINE_ONRUNEVENT == 0) && insertMetamodel)
				GALAXYENGINE_ONRUNEVENT = raapi.createAttribute(GALAXYENGINE, "onRunEvent", raapi.findPrimitiveDataType("String"));
			if (GALAXYENGINE_ONRUNEVENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute onRunEvent of the class GalaxyEngine.");
			}
			GALAXYENGINE_USEGALACTICICONS = raapi.findAttribute(GALAXYENGINE, "useGalacticIcons");
			if ((GALAXYENGINE_USEGALACTICICONS == 0) && insertMetamodel)
				GALAXYENGINE_USEGALACTICICONS = raapi.createAttribute(GALAXYENGINE, "useGalacticIcons", raapi.findPrimitiveDataType("Boolean"));
			if (GALAXYENGINE_USEGALACTICICONS == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute useGalacticIcons of the class GalaxyEngine.");
			}
			GALAXYENGINE_DATAGALAXY = raapi.findAssociationEnd(GALAXYENGINE, "dataGalaxy");
			if ((GALAXYENGINE_DATAGALAXY == 0) && insertMetamodel) {
				GALAXYENGINE_DATAGALAXY = raapi.createAssociation(GALAXYENGINE, DATAGALAXY, "galaxyEngine", "dataGalaxy", false);
			}
			if (GALAXYENGINE_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class GalaxyEngine.");
			}
			DATAGALAXY_LAYOUTINFO = raapi.findAttribute(DATAGALAXY, "layoutInfo");
			if ((DATAGALAXY_LAYOUTINFO == 0) && insertMetamodel)
				DATAGALAXY_LAYOUTINFO = raapi.createAttribute(DATAGALAXY, "layoutInfo", raapi.findPrimitiveDataType("String"));
			if (DATAGALAXY_LAYOUTINFO == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute layoutInfo of the class DataGalaxy.");
			}
			DATAGALAXY_GALAXYCOMPONENT = raapi.findAssociationEnd(DATAGALAXY, "galaxyComponent");
			if ((DATAGALAXY_GALAXYCOMPONENT == 0) && insertMetamodel) {
				DATAGALAXY_GALAXYCOMPONENT = raapi.createAssociation(DATAGALAXY, GALAXYCOMPONENT, "dataGalaxy", "galaxyComponent", false);
			}
			if (DATAGALAXY_GALAXYCOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end galaxyComponent of the class DataGalaxy.");
			}
			DATAGALAXY_GALAXYENGINE = raapi.findAssociationEnd(DATAGALAXY, "galaxyEngine");
			if ((DATAGALAXY_GALAXYENGINE == 0) && insertMetamodel) {
				DATAGALAXY_GALAXYENGINE = raapi.createAssociation(DATAGALAXY, GALAXYENGINE, "dataGalaxy", "galaxyEngine", false);
			}
			if (DATAGALAXY_GALAXYENGINE == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end galaxyEngine of the class DataGalaxy.");
			}
			DATAGALAXY_FRAME = raapi.findAssociationEnd(DATAGALAXY, "frame");
			if ((DATAGALAXY_FRAME == 0) && insertMetamodel) {
				DATAGALAXY_FRAME = raapi.createAssociation(DATAGALAXY, FRAME, "dataGalaxy", "frame", false);
			}
			if (DATAGALAXY_FRAME == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end frame of the class DataGalaxy.");
			}
			DATAGALAXY_EDITGALAXYCOMMAND = raapi.findAssociationEnd(DATAGALAXY, "editGalaxyCommand");
			if ((DATAGALAXY_EDITGALAXYCOMMAND == 0) && insertMetamodel) {
				DATAGALAXY_EDITGALAXYCOMMAND = raapi.createAssociation(DATAGALAXY, EDITGALAXYCOMMAND, "dataGalaxy", "editGalaxyCommand", false);
			}
			if (DATAGALAXY_EDITGALAXYCOMMAND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end editGalaxyCommand of the class DataGalaxy.");
			}
			DATAGALAXY_RUNCONFIGURATION = raapi.findAssociationEnd(DATAGALAXY, "runConfiguration");
			if ((DATAGALAXY_RUNCONFIGURATION == 0) && insertMetamodel) {
				DATAGALAXY_RUNCONFIGURATION = raapi.createAssociation(DATAGALAXY, RUNCONFIGURATION, "dataGalaxy", "runConfiguration", false);
			}
			if (DATAGALAXY_RUNCONFIGURATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end runConfiguration of the class DataGalaxy.");
			}
			DATAGALAXY_ACTIVERUNCONFIGURATION = raapi.findAssociationEnd(DATAGALAXY, "activeRunConfiguration");
			if ((DATAGALAXY_ACTIVERUNCONFIGURATION == 0) && insertMetamodel) {
				DATAGALAXY_ACTIVERUNCONFIGURATION = raapi.createAssociation(DATAGALAXY, RUNCONFIGURATION, "dg1", "activeRunConfiguration", false);
			}
			if (DATAGALAXY_ACTIVERUNCONFIGURATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end activeRunConfiguration of the class DataGalaxy.");
			}
			DATAGALAXY_REFRESHGALAXYCOMMAND = raapi.findAssociationEnd(DATAGALAXY, "refreshGalaxyCommand");
			if ((DATAGALAXY_REFRESHGALAXYCOMMAND == 0) && insertMetamodel) {
				DATAGALAXY_REFRESHGALAXYCOMMAND = raapi.createAssociation(DATAGALAXY, REFRESHGALAXYCOMMAND, "dataGalaxy", "refreshGalaxyCommand", false);
			}
			if (DATAGALAXY_REFRESHGALAXYCOMMAND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end refreshGalaxyCommand of the class DataGalaxy.");
			}
			GALAXYCOMPONENT_ID = raapi.findAttribute(GALAXYCOMPONENT, "id");
			if ((GALAXYCOMPONENT_ID == 0) && insertMetamodel)
				GALAXYCOMPONENT_ID = raapi.createAttribute(GALAXYCOMPONENT, "id", raapi.findPrimitiveDataType("String"));
			if (GALAXYCOMPONENT_ID == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute id of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_NAME = raapi.findAttribute(GALAXYCOMPONENT, "name");
			if ((GALAXYCOMPONENT_NAME == 0) && insertMetamodel)
				GALAXYCOMPONENT_NAME = raapi.createAttribute(GALAXYCOMPONENT, "name", raapi.findPrimitiveDataType("String"));
			if (GALAXYCOMPONENT_NAME == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute name of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_STATE = raapi.findAttribute(GALAXYCOMPONENT, "state");
			if ((GALAXYCOMPONENT_STATE == 0) && insertMetamodel)
				GALAXYCOMPONENT_STATE = raapi.createAttribute(GALAXYCOMPONENT, "state", raapi.findPrimitiveDataType("String"));
			if (GALAXYCOMPONENT_STATE == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute state of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_STATEMESSAGE = raapi.findAttribute(GALAXYCOMPONENT, "stateMessage");
			if ((GALAXYCOMPONENT_STATEMESSAGE == 0) && insertMetamodel)
				GALAXYCOMPONENT_STATEMESSAGE = raapi.createAttribute(GALAXYCOMPONENT, "stateMessage", raapi.findPrimitiveDataType("String"));
			if (GALAXYCOMPONENT_STATEMESSAGE == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute stateMessage of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_LOCATION = raapi.findAttribute(GALAXYCOMPONENT, "location");
			if ((GALAXYCOMPONENT_LOCATION == 0) && insertMetamodel)
				GALAXYCOMPONENT_LOCATION = raapi.createAttribute(GALAXYCOMPONENT, "location", raapi.findPrimitiveDataType("String"));
			if (GALAXYCOMPONENT_LOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute location of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_DATAGALAXY = raapi.findAssociationEnd(GALAXYCOMPONENT, "dataGalaxy");
			if ((GALAXYCOMPONENT_DATAGALAXY == 0) && insertMetamodel) {
				GALAXYCOMPONENT_DATAGALAXY = raapi.createAssociation(GALAXYCOMPONENT, DATAGALAXY, "galaxyComponent", "dataGalaxy", false);
			}
			if (GALAXYCOMPONENT_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_RC1 = raapi.findAssociationEnd(GALAXYCOMPONENT, "rc1");
			if ((GALAXYCOMPONENT_RC1 == 0) && insertMetamodel) {
				GALAXYCOMPONENT_RC1 = raapi.createAssociation(GALAXYCOMPONENT, RUNCONFIGURATION, "visibleComponent", "rc1", false);
			}
			if (GALAXYCOMPONENT_RC1 == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end rc1 of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_RC2 = raapi.findAssociationEnd(GALAXYCOMPONENT, "rc2");
			if ((GALAXYCOMPONENT_RC2 == 0) && insertMetamodel) {
				GALAXYCOMPONENT_RC2 = raapi.createAssociation(GALAXYCOMPONENT, RUNCONFIGURATION, "mustConfigureComponent", "rc2", false);
			}
			if (GALAXYCOMPONENT_RC2 == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end rc2 of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_RC3 = raapi.findAssociationEnd(GALAXYCOMPONENT, "rc3");
			if ((GALAXYCOMPONENT_RC3 == 0) && insertMetamodel) {
				GALAXYCOMPONENT_RC3 = raapi.createAssociation(GALAXYCOMPONENT, RUNCONFIGURATION, "deactivatedComponent", "rc3", false);
			}
			if (GALAXYCOMPONENT_RC3 == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end rc3 of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_REFRESHGALAXYCOMMAND = raapi.findAssociationEnd(GALAXYCOMPONENT, "refreshGalaxyCommand");
			if ((GALAXYCOMPONENT_REFRESHGALAXYCOMMAND == 0) && insertMetamodel) {
				GALAXYCOMPONENT_REFRESHGALAXYCOMMAND = raapi.createAssociation(GALAXYCOMPONENT, REFRESHGALAXYCOMMAND, "modifiedComponent", "refreshGalaxyCommand", false);
			}
			if (GALAXYCOMPONENT_REFRESHGALAXYCOMMAND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end refreshGalaxyCommand of the class GalaxyComponent.");
			}
			GALAXYCOMPONENT_FRAME = raapi.findAssociationEnd(GALAXYCOMPONENT, "frame");
			if ((GALAXYCOMPONENT_FRAME == 0) && insertMetamodel) {
				GALAXYCOMPONENT_FRAME = raapi.createAssociation(GALAXYCOMPONENT, FRAME, "galaxyComponent", "frame", false);
			}
			if (GALAXYCOMPONENT_FRAME == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end frame of the class GalaxyComponent.");
			}
			RUNCONFIGURATION_NAME = raapi.findAttribute(RUNCONFIGURATION, "name");
			if ((RUNCONFIGURATION_NAME == 0) && insertMetamodel)
				RUNCONFIGURATION_NAME = raapi.createAttribute(RUNCONFIGURATION, "name", raapi.findPrimitiveDataType("String"));
			if (RUNCONFIGURATION_NAME == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute name of the class RunConfiguration.");
			}
			RUNCONFIGURATION_DESCRIPTION = raapi.findAttribute(RUNCONFIGURATION, "description");
			if ((RUNCONFIGURATION_DESCRIPTION == 0) && insertMetamodel)
				RUNCONFIGURATION_DESCRIPTION = raapi.createAttribute(RUNCONFIGURATION, "description", raapi.findPrimitiveDataType("String"));
			if (RUNCONFIGURATION_DESCRIPTION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute description of the class RunConfiguration.");
			}
			RUNCONFIGURATION_VISIBLECOMPONENT = raapi.findAssociationEnd(RUNCONFIGURATION, "visibleComponent");
			if ((RUNCONFIGURATION_VISIBLECOMPONENT == 0) && insertMetamodel) {
				RUNCONFIGURATION_VISIBLECOMPONENT = raapi.createAssociation(RUNCONFIGURATION, GALAXYCOMPONENT, "rc1", "visibleComponent", false);
			}
			if (RUNCONFIGURATION_VISIBLECOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end visibleComponent of the class RunConfiguration.");
			}
			RUNCONFIGURATION_MUSTCONFIGURECOMPONENT = raapi.findAssociationEnd(RUNCONFIGURATION, "mustConfigureComponent");
			if ((RUNCONFIGURATION_MUSTCONFIGURECOMPONENT == 0) && insertMetamodel) {
				RUNCONFIGURATION_MUSTCONFIGURECOMPONENT = raapi.createAssociation(RUNCONFIGURATION, GALAXYCOMPONENT, "rc2", "mustConfigureComponent", false);
			}
			if (RUNCONFIGURATION_MUSTCONFIGURECOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end mustConfigureComponent of the class RunConfiguration.");
			}
			RUNCONFIGURATION_DEACTIVATEDCOMPONENT = raapi.findAssociationEnd(RUNCONFIGURATION, "deactivatedComponent");
			if ((RUNCONFIGURATION_DEACTIVATEDCOMPONENT == 0) && insertMetamodel) {
				RUNCONFIGURATION_DEACTIVATEDCOMPONENT = raapi.createAssociation(RUNCONFIGURATION, GALAXYCOMPONENT, "rc3", "deactivatedComponent", false);
			}
			if (RUNCONFIGURATION_DEACTIVATEDCOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end deactivatedComponent of the class RunConfiguration.");
			}
			RUNCONFIGURATION_DATAGALAXY = raapi.findAssociationEnd(RUNCONFIGURATION, "dataGalaxy");
			if ((RUNCONFIGURATION_DATAGALAXY == 0) && insertMetamodel) {
				RUNCONFIGURATION_DATAGALAXY = raapi.createAssociation(RUNCONFIGURATION, DATAGALAXY, "runConfiguration", "dataGalaxy", false);
			}
			if (RUNCONFIGURATION_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class RunConfiguration.");
			}
			RUNCONFIGURATION_DG1 = raapi.findAssociationEnd(RUNCONFIGURATION, "dg1");
			if ((RUNCONFIGURATION_DG1 == 0) && insertMetamodel) {
				RUNCONFIGURATION_DG1 = raapi.createAssociation(RUNCONFIGURATION, DATAGALAXY, "activeRunConfiguration", "dg1", false);
			}
			if (RUNCONFIGURATION_DG1 == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dg1 of the class RunConfiguration.");
			}
			STAR_EMPTYONINIT = raapi.findAttribute(STAR, "emptyOnInit");
			if ((STAR_EMPTYONINIT == 0) && insertMetamodel)
				STAR_EMPTYONINIT = raapi.createAttribute(STAR, "emptyOnInit", raapi.findPrimitiveDataType("Boolean"));
			if (STAR_EMPTYONINIT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute emptyOnInit of the class Star.");
			}
			STAR_STARDATATYPE = raapi.findAttribute(STAR, "starDataType");
			if ((STAR_STARDATATYPE == 0) && insertMetamodel)
				STAR_STARDATATYPE = raapi.createAttribute(STAR, "starDataType", raapi.findPrimitiveDataType("String"));
			if (STAR_STARDATATYPE == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute starDataType of the class Star.");
			}
			STAR_PLANET = raapi.findAssociationEnd(STAR, "planet");
			if ((STAR_PLANET == 0) && insertMetamodel) {
				STAR_PLANET = raapi.createAssociation(STAR, PLANET, "star", "planet", false);
			}
			if (STAR_PLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end planet of the class Star.");
			}
			STAR_CONSUMER = raapi.findAssociationEnd(STAR, "consumer");
			if ((STAR_CONSUMER == 0) && insertMetamodel) {
				STAR_CONSUMER = raapi.createAssociation(STAR, STELLARWIND, "source", "consumer", false);
			}
			if (STAR_CONSUMER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end consumer of the class Star.");
			}
			STAR_PRODUCER = raapi.findAssociationEnd(STAR, "producer");
			if ((STAR_PRODUCER == 0) && insertMetamodel) {
				STAR_PRODUCER = raapi.createAssociation(STAR, STELLARWIND, "target", "producer", false);
			}
			if (STAR_PRODUCER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end producer of the class Star.");
			}
			STAR_STARDATA = raapi.findAssociationEnd(STAR, "starData");
			if ((STAR_STARDATA == 0) && insertMetamodel) {
				STAR_STARDATA = raapi.createAssociation(STAR, STARDATA, "star", "starData", false);
			}
			if (STAR_STARDATA == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end starData of the class Star.");
			}
			STAR_CONFIGURESTAR = raapi.findAssociationEnd(STAR, "configureStar");
			if ((STAR_CONFIGURESTAR == 0) && insertMetamodel) {
				STAR_CONFIGURESTAR = raapi.createAssociation(STAR, CONFIGURESTAR, "star", "configureStar", false);
			}
			if (STAR_CONFIGURESTAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end configureStar of the class Star.");
			}
			STAR_INITIALIZESTAR = raapi.findAssociationEnd(STAR, "initializeStar");
			if ((STAR_INITIALIZESTAR == 0) && insertMetamodel) {
				STAR_INITIALIZESTAR = raapi.createAssociation(STAR, INITIALIZESTAR, "star", "initializeStar", false);
			}
			if (STAR_INITIALIZESTAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end initializeStar of the class Star.");
			}
			STAR_FINALIZESTAR = raapi.findAssociationEnd(STAR, "finalizeStar");
			if ((STAR_FINALIZESTAR == 0) && insertMetamodel) {
				STAR_FINALIZESTAR = raapi.createAssociation(STAR, FINALIZESTAR, "star", "finalizeStar", false);
			}
			if (STAR_FINALIZESTAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end finalizeStar of the class Star.");
			}
			STAR_CLEANUPSTAR = raapi.findAssociationEnd(STAR, "cleanupStar");
			if ((STAR_CLEANUPSTAR == 0) && insertMetamodel) {
				STAR_CLEANUPSTAR = raapi.createAssociation(STAR, CLEANUPSTAR, "star", "cleanupStar", false);
			}
			if (STAR_CLEANUPSTAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end cleanupStar of the class Star.");
			}
			PLANET_STAR = raapi.findAssociationEnd(PLANET, "star");
			if ((PLANET_STAR == 0) && insertMetamodel) {
				PLANET_STAR = raapi.createAssociation(PLANET, STAR, "planet", "star", false);
			}
			if (PLANET_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class Planet.");
			}
			PLANET_CROSSFILTER = raapi.findAssociationEnd(PLANET, "crossFilter");
			if ((PLANET_CROSSFILTER == 0) && insertMetamodel) {
				PLANET_CROSSFILTER = raapi.createAssociation(PLANET, CROSSFILTER, "planet", "crossFilter", false);
			}
			if (PLANET_CROSSFILTER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end crossFilter of the class Planet.");
			}
			PLANET_CONFIGUREPLANET = raapi.findAssociationEnd(PLANET, "configurePlanet");
			if ((PLANET_CONFIGUREPLANET == 0) && insertMetamodel) {
				PLANET_CONFIGUREPLANET = raapi.createAssociation(PLANET, CONFIGUREPLANET, "planet", "configurePlanet", false);
			}
			if (PLANET_CONFIGUREPLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end configurePlanet of the class Planet.");
			}
			PLANET_VISUALIZEPLANET = raapi.findAssociationEnd(PLANET, "visualizePlanet");
			if ((PLANET_VISUALIZEPLANET == 0) && insertMetamodel) {
				PLANET_VISUALIZEPLANET = raapi.createAssociation(PLANET, VISUALIZEPLANET, "planet", "visualizePlanet", false);
			}
			if (PLANET_VISUALIZEPLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end visualizePlanet of the class Planet.");
			}
			STELLARWIND_DESCRIPTION = raapi.findAttribute(STELLARWIND, "description");
			if ((STELLARWIND_DESCRIPTION == 0) && insertMetamodel)
				STELLARWIND_DESCRIPTION = raapi.createAttribute(STELLARWIND, "description", raapi.findPrimitiveDataType("String"));
			if (STELLARWIND_DESCRIPTION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute description of the class StellarWind.");
			}
			STELLARWIND_SOURCE = raapi.findAssociationEnd(STELLARWIND, "source");
			if ((STELLARWIND_SOURCE == 0) && insertMetamodel) {
				STELLARWIND_SOURCE = raapi.createAssociation(STELLARWIND, STAR, "consumer", "source", false);
			}
			if (STELLARWIND_SOURCE == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end source of the class StellarWind.");
			}
			STELLARWIND_TARGET = raapi.findAssociationEnd(STELLARWIND, "target");
			if ((STELLARWIND_TARGET == 0) && insertMetamodel) {
				STELLARWIND_TARGET = raapi.createAssociation(STELLARWIND, STAR, "producer", "target", false);
			}
			if (STELLARWIND_TARGET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end target of the class StellarWind.");
			}
			STELLARWIND_CROSSFILTER = raapi.findAssociationEnd(STELLARWIND, "crossFilter");
			if ((STELLARWIND_CROSSFILTER == 0) && insertMetamodel) {
				STELLARWIND_CROSSFILTER = raapi.createAssociation(STELLARWIND, CROSSFILTER, "stellarWind", "crossFilter", false);
			}
			if (STELLARWIND_CROSSFILTER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end crossFilter of the class StellarWind.");
			}
			STELLARWIND_CONFIGURESTELLARWIND = raapi.findAssociationEnd(STELLARWIND, "configureStellarWind");
			if ((STELLARWIND_CONFIGURESTELLARWIND == 0) && insertMetamodel) {
				STELLARWIND_CONFIGURESTELLARWIND = raapi.createAssociation(STELLARWIND, CONFIGURESTELLARWIND, "stellarWind", "configureStellarWind", false);
			}
			if (STELLARWIND_CONFIGURESTELLARWIND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end configureStellarWind of the class StellarWind.");
			}
			STELLARWIND_EMITSTELLARWIND = raapi.findAssociationEnd(STELLARWIND, "emitStellarWind");
			if ((STELLARWIND_EMITSTELLARWIND == 0) && insertMetamodel) {
				STELLARWIND_EMITSTELLARWIND = raapi.createAssociation(STELLARWIND, EMITSTELLARWIND, "stellarWind", "emitStellarWind", false);
			}
			if (STELLARWIND_EMITSTELLARWIND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end emitStellarWind of the class StellarWind.");
			}
			CROSSFILTER_PLANET = raapi.findAssociationEnd(CROSSFILTER, "planet");
			if ((CROSSFILTER_PLANET == 0) && insertMetamodel) {
				CROSSFILTER_PLANET = raapi.createAssociation(CROSSFILTER, PLANET, "crossFilter", "planet", false);
			}
			if (CROSSFILTER_PLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end planet of the class CrossFilter.");
			}
			CROSSFILTER_STELLARWIND = raapi.findAssociationEnd(CROSSFILTER, "stellarWind");
			if ((CROSSFILTER_STELLARWIND == 0) && insertMetamodel) {
				CROSSFILTER_STELLARWIND = raapi.createAssociation(CROSSFILTER, STELLARWIND, "crossFilter", "stellarWind", false);
			}
			if (CROSSFILTER_STELLARWIND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end stellarWind of the class CrossFilter.");
			}
			CROSSFILTER_CONFIGURECROSSFILTER = raapi.findAssociationEnd(CROSSFILTER, "configureCrossFilter");
			if ((CROSSFILTER_CONFIGURECROSSFILTER == 0) && insertMetamodel) {
				CROSSFILTER_CONFIGURECROSSFILTER = raapi.createAssociation(CROSSFILTER, CONFIGURECROSSFILTER, "crossFilter", "configureCrossFilter", false);
			}
			if (CROSSFILTER_CONFIGURECROSSFILTER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end configureCrossFilter of the class CrossFilter.");
			}
			STARDATA_STAR = raapi.findAssociationEnd(STARDATA, "star");
			if ((STARDATA_STAR == 0) && insertMetamodel) {
				STARDATA_STAR = raapi.createAssociation(STARDATA, STAR, "starData", "star", false);
			}
			if (STARDATA_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class StarData.");
			}
			CONFIGUREPLANET_FRAMELOCATION = raapi.findAttribute(CONFIGUREPLANET, "frameLocation");
			if ((CONFIGUREPLANET_FRAMELOCATION == 0) && insertMetamodel)
				CONFIGUREPLANET_FRAMELOCATION = raapi.createAttribute(CONFIGUREPLANET, "frameLocation", raapi.findPrimitiveDataType("String"));
			if (CONFIGUREPLANET_FRAMELOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute frameLocation of the class ConfigurePlanet.");
			}
			CONFIGUREPLANET_PLANET = raapi.findAssociationEnd(CONFIGUREPLANET, "planet");
			if ((CONFIGUREPLANET_PLANET == 0) && insertMetamodel) {
				CONFIGUREPLANET_PLANET = raapi.createAssociation(CONFIGUREPLANET, PLANET, "configurePlanet", "planet", false);
			}
			if (CONFIGUREPLANET_PLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end planet of the class ConfigurePlanet.");
			}
			VISUALIZEPLANET_FRAMELOCATION = raapi.findAttribute(VISUALIZEPLANET, "frameLocation");
			if ((VISUALIZEPLANET_FRAMELOCATION == 0) && insertMetamodel)
				VISUALIZEPLANET_FRAMELOCATION = raapi.createAttribute(VISUALIZEPLANET, "frameLocation", raapi.findPrimitiveDataType("String"));
			if (VISUALIZEPLANET_FRAMELOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute frameLocation of the class VisualizePlanet.");
			}
			VISUALIZEPLANET_PLANET = raapi.findAssociationEnd(VISUALIZEPLANET, "planet");
			if ((VISUALIZEPLANET_PLANET == 0) && insertMetamodel) {
				VISUALIZEPLANET_PLANET = raapi.createAssociation(VISUALIZEPLANET, PLANET, "visualizePlanet", "planet", false);
			}
			if (VISUALIZEPLANET_PLANET == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end planet of the class VisualizePlanet.");
			}
			CONFIGURESTAR_FRAMELOCATION = raapi.findAttribute(CONFIGURESTAR, "frameLocation");
			if ((CONFIGURESTAR_FRAMELOCATION == 0) && insertMetamodel)
				CONFIGURESTAR_FRAMELOCATION = raapi.createAttribute(CONFIGURESTAR, "frameLocation", raapi.findPrimitiveDataType("String"));
			if (CONFIGURESTAR_FRAMELOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute frameLocation of the class ConfigureStar.");
			}
			CONFIGURESTAR_STAR = raapi.findAssociationEnd(CONFIGURESTAR, "star");
			if ((CONFIGURESTAR_STAR == 0) && insertMetamodel) {
				CONFIGURESTAR_STAR = raapi.createAssociation(CONFIGURESTAR, STAR, "configureStar", "star", false);
			}
			if (CONFIGURESTAR_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class ConfigureStar.");
			}
			INITIALIZESTAR_STAR = raapi.findAssociationEnd(INITIALIZESTAR, "star");
			if ((INITIALIZESTAR_STAR == 0) && insertMetamodel) {
				INITIALIZESTAR_STAR = raapi.createAssociation(INITIALIZESTAR, STAR, "initializeStar", "star", false);
			}
			if (INITIALIZESTAR_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class InitializeStar.");
			}
			CONFIGURECROSSFILTER_FRAMELOCATION = raapi.findAttribute(CONFIGURECROSSFILTER, "frameLocation");
			if ((CONFIGURECROSSFILTER_FRAMELOCATION == 0) && insertMetamodel)
				CONFIGURECROSSFILTER_FRAMELOCATION = raapi.createAttribute(CONFIGURECROSSFILTER, "frameLocation", raapi.findPrimitiveDataType("String"));
			if (CONFIGURECROSSFILTER_FRAMELOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute frameLocation of the class ConfigureCrossFilter.");
			}
			CONFIGURECROSSFILTER_CROSSFILTER = raapi.findAssociationEnd(CONFIGURECROSSFILTER, "crossFilter");
			if ((CONFIGURECROSSFILTER_CROSSFILTER == 0) && insertMetamodel) {
				CONFIGURECROSSFILTER_CROSSFILTER = raapi.createAssociation(CONFIGURECROSSFILTER, CROSSFILTER, "configureCrossFilter", "crossFilter", false);
			}
			if (CONFIGURECROSSFILTER_CROSSFILTER == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end crossFilter of the class ConfigureCrossFilter.");
			}
			CONFIGURESTELLARWIND_FRAMELOCATION = raapi.findAttribute(CONFIGURESTELLARWIND, "frameLocation");
			if ((CONFIGURESTELLARWIND_FRAMELOCATION == 0) && insertMetamodel)
				CONFIGURESTELLARWIND_FRAMELOCATION = raapi.createAttribute(CONFIGURESTELLARWIND, "frameLocation", raapi.findPrimitiveDataType("String"));
			if (CONFIGURESTELLARWIND_FRAMELOCATION == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the attibute frameLocation of the class ConfigureStellarWind.");
			}
			CONFIGURESTELLARWIND_STELLARWIND = raapi.findAssociationEnd(CONFIGURESTELLARWIND, "stellarWind");
			if ((CONFIGURESTELLARWIND_STELLARWIND == 0) && insertMetamodel) {
				CONFIGURESTELLARWIND_STELLARWIND = raapi.createAssociation(CONFIGURESTELLARWIND, STELLARWIND, "configureStellarWind", "stellarWind", false);
			}
			if (CONFIGURESTELLARWIND_STELLARWIND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end stellarWind of the class ConfigureStellarWind.");
			}
			EMITSTELLARWIND_STELLARWIND = raapi.findAssociationEnd(EMITSTELLARWIND, "stellarWind");
			if ((EMITSTELLARWIND_STELLARWIND == 0) && insertMetamodel) {
				EMITSTELLARWIND_STELLARWIND = raapi.createAssociation(EMITSTELLARWIND, STELLARWIND, "emitStellarWind", "stellarWind", false);
			}
			if (EMITSTELLARWIND_STELLARWIND == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end stellarWind of the class EmitStellarWind.");
			}
			EDITGALAXYCOMMAND_DATAGALAXY = raapi.findAssociationEnd(EDITGALAXYCOMMAND, "dataGalaxy");
			if ((EDITGALAXYCOMMAND_DATAGALAXY == 0) && insertMetamodel) {
				EDITGALAXYCOMMAND_DATAGALAXY = raapi.createAssociation(EDITGALAXYCOMMAND, DATAGALAXY, "editGalaxyCommand", "dataGalaxy", false);
			}
			if (EDITGALAXYCOMMAND_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class EditGalaxyCommand.");
			}
			FRAME_DATAGALAXY = raapi.findAssociationEnd(FRAME, "dataGalaxy");
			if ((FRAME_DATAGALAXY == 0) && insertMetamodel) {
				FRAME_DATAGALAXY = raapi.createAssociation(FRAME, DATAGALAXY, "frame", "dataGalaxy", false);
			}
			if (FRAME_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class Frame.");
			}
			FRAME_GALAXYCOMPONENT = raapi.findAssociationEnd(FRAME, "galaxyComponent");
			if ((FRAME_GALAXYCOMPONENT == 0) && insertMetamodel) {
				FRAME_GALAXYCOMPONENT = raapi.createAssociation(FRAME, GALAXYCOMPONENT, "frame", "galaxyComponent", false);
			}
			if (FRAME_GALAXYCOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end galaxyComponent of the class Frame.");
			}
			REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT = raapi.findAssociationEnd(REFRESHGALAXYCOMMAND, "modifiedComponent");
			if ((REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT == 0) && insertMetamodel) {
				REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT = raapi.createAssociation(REFRESHGALAXYCOMMAND, GALAXYCOMPONENT, "refreshGalaxyCommand", "modifiedComponent", false);
			}
			if (REFRESHGALAXYCOMMAND_MODIFIEDCOMPONENT == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end modifiedComponent of the class RefreshGalaxyCommand.");
			}
			REFRESHGALAXYCOMMAND_DATAGALAXY = raapi.findAssociationEnd(REFRESHGALAXYCOMMAND, "dataGalaxy");
			if ((REFRESHGALAXYCOMMAND_DATAGALAXY == 0) && insertMetamodel) {
				REFRESHGALAXYCOMMAND_DATAGALAXY = raapi.createAssociation(REFRESHGALAXYCOMMAND, DATAGALAXY, "refreshGalaxyCommand", "dataGalaxy", false);
			}
			if (REFRESHGALAXYCOMMAND_DATAGALAXY == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end dataGalaxy of the class RefreshGalaxyCommand.");
			}
			FINALIZESTAR_STAR = raapi.findAssociationEnd(FINALIZESTAR, "star");
			if ((FINALIZESTAR_STAR == 0) && insertMetamodel) {
				FINALIZESTAR_STAR = raapi.createAssociation(FINALIZESTAR, STAR, "finalizeStar", "star", false);
			}
			if (FINALIZESTAR_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class FinalizeStar.");
			}
			CLEANUPSTAR_STAR = raapi.findAssociationEnd(CLEANUPSTAR, "star");
			if ((CLEANUPSTAR_STAR == 0) && insertMetamodel) {
				CLEANUPSTAR_STAR = raapi.createAssociation(CLEANUPSTAR, STAR, "cleanupStar", "star", false);
			}
			if (CLEANUPSTAR_STAR == 0) {
				setRAAPI(null, null, false); // freeing references initialized so far...
				throw new ElementReferenceException("Error obtaining a reference for the association end star of the class CleanupStar.");
			}
		}
	}

	public LaunchTransformationCommand createLaunchTransformationCommand()
	{
		LaunchTransformationCommand retVal = new LaunchTransformationCommand(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public Command createCommand()
	{
		Command retVal = new Command(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public GalaxyEngine createGalaxyEngine()
	{
		GalaxyEngine retVal = new GalaxyEngine(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public DataGalaxy createDataGalaxy()
	{
		DataGalaxy retVal = new DataGalaxy(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public GalaxyComponent createGalaxyComponent()
	{
		GalaxyComponent retVal = new GalaxyComponent(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public RunConfiguration createRunConfiguration()
	{
		RunConfiguration retVal = new RunConfiguration(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public Star createStar()
	{
		Star retVal = new Star(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public Planet createPlanet()
	{
		Planet retVal = new Planet(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public StellarWind createStellarWind()
	{
		StellarWind retVal = new StellarWind(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public CrossFilter createCrossFilter()
	{
		CrossFilter retVal = new CrossFilter(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public StarData createStarData()
	{
		StarData retVal = new StarData(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public ConfigurePlanet createConfigurePlanet()
	{
		ConfigurePlanet retVal = new ConfigurePlanet(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public VisualizePlanet createVisualizePlanet()
	{
		VisualizePlanet retVal = new VisualizePlanet(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public ConfigureStar createConfigureStar()
	{
		ConfigureStar retVal = new ConfigureStar(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public InitializeStar createInitializeStar()
	{
		InitializeStar retVal = new InitializeStar(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public ConfigureCrossFilter createConfigureCrossFilter()
	{
		ConfigureCrossFilter retVal = new ConfigureCrossFilter(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public ConfigureStellarWind createConfigureStellarWind()
	{
		ConfigureStellarWind retVal = new ConfigureStellarWind(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public EmitStellarWind createEmitStellarWind()
	{
		EmitStellarWind retVal = new EmitStellarWind(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public EditGalaxyCommand createEditGalaxyCommand()
	{
		EditGalaxyCommand retVal = new EditGalaxyCommand(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public Frame createFrame()
	{
		Frame retVal = new Frame(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public RefreshGalaxyCommand createRefreshGalaxyCommand()
	{
		RefreshGalaxyCommand retVal = new RefreshGalaxyCommand(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public FinalizeStar createFinalizeStar()
	{
		FinalizeStar retVal = new FinalizeStar(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
	public CleanupStar createCleanupStar()
	{
		CleanupStar retVal = new CleanupStar(this);
		wrappers.put(retVal.getRAAPIReference(), retVal);
		return retVal; 
	}
  
}
