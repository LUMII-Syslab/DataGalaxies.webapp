package lv.lumii.datagalaxies;

public class GalaxyHelper {
	
	//private static Logger logger =  LoggerFactory.getLogger(GalaxyHelper.class);
	
	public static String getGalacticTypeName(lv.lumii.datagalaxies.mm.GalaxyComponent componentObj)
	// for stars, returns the type name of the corresponding star data;
	// for other galactic types, returns the corresponding type name;
	// on error, returns null
	{
		long rrr = componentObj.getRAAPIReference(); 
		if (componentObj instanceof lv.lumii.datagalaxies.mm.Star)
			if (!((lv.lumii.datagalaxies.mm.Star) componentObj).getStarData().isEmpty())
				rrr = ((lv.lumii.datagalaxies.mm.Star) componentObj).getStarData().get(0).getRAAPIReference();
			else
				return null;
			
		long it = componentObj.getRAAPI().getIteratorForDirectObjectClasses(rrr);
		long r = componentObj.getRAAPI().resolveIteratorFirst(it);				
		String retVal = componentObj.getRAAPI().getClassName(r);
		componentObj.getRAAPI().freeReference(r);
		componentObj.getRAAPI().freeIterator(it);
		return retVal;
	}
	
	public static void closeComponentFrame(lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory, lv.lumii.datagalaxies.mm.GalaxyComponent componentObj)
	{
		if (!componentObj.getFrame().isEmpty()) {
						
			
			// detaching previous frame...
			lv.lumii.datagalaxies.mm.Frame frame_ = componentObj.getFrame().get(0);
			
			
			lv.lumii.datagalaxies.eemm.Frame frame =  (lv.lumii.datagalaxies.eemm.Frame)eeFactory.findOrCreateRAAPIReferenceWrapper(frame_.getRAAPIReference(), false); 
			
		    componentObj.setFrame(null); // detaching the frame from the galaxy component...
		    		    
		    lv.lumii.datagalaxies.eemm.DetachFrameCommand dfc = eeFactory.createDetachFrameCommand();
			dfc.setFrame(frame);
			dfc.setPermanently(true);
			dfc.submit();
			
		}
		
	}
	
	public static void closeComponentFrame(lv.lumii.datagalaxies.mm.GalaxyComponent componentObj)
	{
		if (!componentObj.getFrame().isEmpty()) {
						
			
			lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
			
			try {
				eeFactory.setRAAPI(componentObj.getRAAPI(), "", true);
			}
			catch (Throwable t)
			{
				return;
			}
			
			closeComponentFrame(eeFactory, componentObj);
			
			eeFactory.unsetRAAPI();			
		}
		
	}

	public static boolean deleteGalaxyComponent(lv.lumii.datagalaxies.mm.GalaxyComponent componentObj)
	{
		closeComponentFrame(componentObj);
		
		if (componentObj instanceof lv.lumii.datagalaxies.mm.StellarWind) {			
			if ("NEW".equals( componentObj.getState() )) {
				if (!((lv.lumii.datagalaxies.mm.StellarWind) componentObj).getTarget().isEmpty())
					((lv.lumii.datagalaxies.mm.StellarWind) componentObj).getTarget().get(0).delete();
			}
		}
		if (componentObj instanceof lv.lumii.datagalaxies.mm.Star) {
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.Star) componentObj).getConsumer()) {
				deleteGalaxyComponent(w);
			}
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.Star) componentObj).getProducer()) {
				deleteGalaxyComponent(w);
			}
			for (lv.lumii.datagalaxies.mm.Planet p : ((lv.lumii.datagalaxies.mm.Star) componentObj).getPlanet()) {
				deleteGalaxyComponent(p);
			}
			for (lv.lumii.datagalaxies.mm.StarData d : ((lv.lumii.datagalaxies.mm.Star) componentObj).getStarData()) {
				d.delete();
			}
		}
		return componentObj.delete();							
	}
	
	public static void refreshGalaxy(lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory, lv.lumii.datagalaxies.mm.GalaxyComponent modifiedComponent) {
		lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rgc = geFactory.createRefreshGalaxyCommand();
		rgc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
		if (modifiedComponent != null)
			rgc.setModifiedComponent(modifiedComponent);
		rgc.submit();	
	}
	
}
