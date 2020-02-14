package lv.lumii.datagalaxies;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webappos.server.API;
import org.webappos.webmem.IWebMemory;

import lv.lumii.datagalaxies.GalacticTypes;
import lv.lumii.datagalaxies.GalaxyHelper;
import lv.lumii.tda.raapi.RAAPI;

public class DataGalaxiesApp
{
	private static Logger logger =  LoggerFactory.getLogger(DataGalaxiesApp.class);
	
	public static String APP_DIR = new File(DataGalaxiesApp.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		
	public static boolean mainTransformation(IWebMemory webmem, long rCommand)
	{
		logger.trace("in DataGalaxiesApp main webcall");
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = webmem.elevate(lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory.class, true);
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngine ee = lv.lumii.datagalaxies.eemm.EnvironmentEngine.firstObject(eeFactory);
		if (ee==null)
			ee = eeFactory.createEnvironmentEngine();
		ee.setOnProjectOpenedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onProjectOpened");
			
		return true;
	}
	
	public static boolean onProjectOpened(IWebMemory webmem, long rCommand)
	{
		logger.trace("in DataGalaxiesApp.onProjectOpened");
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = webmem.elevate(lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory.class, true);
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = webmem.elevate(lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory.class, true);
		
		
		
		// Updating unfinished states of galaxy components...
		for (lv.lumii.datagalaxies.mm.GalaxyComponent c : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
			if ("RUNNING".equals(c.getState())) {
				c.setState("RUN_ERROR");
			}
		}
		
		lv.lumii.datagalaxies.mm.GalaxyEngine ge = lv.lumii.datagalaxies.mm.GalaxyEngine.firstObject(geFactory);
		if (ge==null)
			ge = geFactory.createGalaxyEngine();

		lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory);
		if (dg==null)
			dg = geFactory.createDataGalaxy();
		
		
		lv.lumii.datagalaxies.mm.Command.deleteAllObjects(geFactory);
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngine ee = lv.lumii.datagalaxies.eemm.EnvironmentEngine.firstObject(eeFactory);
		
		if (dg.getActiveRunConfiguration().size()==0) {
			// designer's mode; allow to switch the view...
		
			boolean found=false;
			for (lv.lumii.datagalaxies.eemm.Option o : lv.lumii.datagalaxies.eemm.Option.allObjects(eeFactory)) {
				if ("switch_galactic_view"==o.getId()) {
					found = true;
					break;
				}
			}
			
			if (!found && ee!=null) {
				lv.lumii.datagalaxies.eemm.Option opt = eeFactory.createOption();
				opt.setCaption("Switch view");
				opt.setId("switch_galactic_view");
				opt.setLocation("TOOLBAR");
				opt.setImage("DG_GALACTIC_TO_CLASSICAL.png");
				opt.setIsEnabled(true);
				opt.setOnOptionSelectedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onOptionSelectedEvent");
				ee.getOption().add(opt);			
			}
		}

		// visualizing planets as not "run"
		for (lv.lumii.datagalaxies.mm.Planet p : lv.lumii.datagalaxies.mm.Planet.allObjects(geFactory)) {
			if ("RUN_OK".equals(p.getState())) {
				p.setState("CONFIGURATION_OK");
			}
		}

		// removing run errors
		for (lv.lumii.datagalaxies.mm.GalaxyComponent c : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
			if ("RUN_ERROR".equals(c.getState())) {
				c.setState("CONFIGURATION_OK");
				c.setStateMessage(null);
			}
		}
		
		lv.lumii.datagalaxies.mm.EditGalaxyCommand egcmd = geFactory.createEditGalaxyCommand();
		
		lv.lumii.datagalaxies.mm.Frame.deleteAllObjects(geFactory);
					
		lv.lumii.datagalaxies.mm.Frame frame_ = geFactory.createFrame();					
		lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).setFrame(frame_);
		
		egcmd.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
		lv.lumii.datagalaxies.eemm.Frame frame = (lv.lumii.datagalaxies.eemm.Frame)eeFactory.findOrCreateRAAPIReferenceWrapper(frame_.getRAAPIReference(), false);
		frame.setIsClosable(false);
		
		frame.setCaption("Data Galaxy");
		egcmd.submit();
		
		if (ee!=null && ee.getOption().size()>0) {
			lv.lumii.datagalaxies.eemm.RefreshOptionsCommand rocmd = eeFactory.createRefreshOptionsCommand();
			rocmd.setEnvironmentEngine(ee);
			rocmd.submit();				
		}
		

		return true;
	}
	
	public static boolean onOptionSelectedEvent(RAAPI raapi, long r)
	{
		logger.trace("in DataGalaxiesApp.onOptionSelectedEvent");
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			eeFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			return false;
		}
		
		try {
			geFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			eeFactory.unsetRAAPI();
			return false;
		}
		
		lv.lumii.datagalaxies.eemm.OptionSelectedEvent ev = (lv.lumii.datagalaxies.eemm.OptionSelectedEvent)eeFactory.findOrCreateRAAPIReferenceWrapper(r, false);
		if ((ev.getOption()!=null) && ("switch_galactic_view".equals(ev.getOption().getId()))) {
			logger.trace("in DataGalaxiesApp.onOptionSelectedEvent: switching galactic view");
			lv.lumii.datagalaxies.mm.GalaxyEngine ge = lv.lumii.datagalaxies.mm.GalaxyEngine.firstObject(geFactory);
			if ((ge.getUseGalacticIcons() == null) || (!ge.getUseGalacticIcons())) {
				ge.setUseGalacticIcons(true);
			}
			else {
				ge.setUseGalacticIcons(false);
			}
			
			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rgc = geFactory.createRefreshGalaxyCommand();
			lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory);
			rgc.setDataGalaxy(dg);
			rgc.submit();
		}
		
		eeFactory.unsetRAAPI();
		geFactory.unsetRAAPI();
		
		return true;
	}

	
	public static boolean onCloseFrameRequestedEvent(RAAPI raapi, long r)
	{
		logger.trace("in DataGalaxiesApp.onCloseFrameRequestedEvent");
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			eeFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			return false;
		}
		
		try {
			geFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			eeFactory.unsetRAAPI();
			return false;
		}
		
		lv.lumii.datagalaxies.eemm.CloseFrameRequestedEvent ev = (lv.lumii.datagalaxies.eemm.CloseFrameRequestedEvent)eeFactory.findOrCreateRAAPIReferenceWrapper(r, false); 
		lv.lumii.datagalaxies.eemm.Frame frame = ev.getFrame();
		
		
		lv.lumii.datagalaxies.mm.Frame frame_ = (lv.lumii.datagalaxies.mm.Frame)geFactory.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false);
		
		if (!frame_.getGalaxyComponent().isEmpty()) {
			lv.lumii.datagalaxies.mm.GalaxyComponent c = frame_.getGalaxyComponent().get(0);
			c.getFrame().clear();
			//c.setFrame(null); // detaching the frame from the galaxy component...
			if ("NEW".equals(c.getState())) {
				GalaxyHelper.deleteGalaxyComponent(c);
			}
			else
			if ((c instanceof lv.lumii.datagalaxies.mm.Planet) && "RUN_OK".equals(c.getState())) {
				c.setState("CONFIGURATION_OK");
				
				lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rgc = geFactory.createRefreshGalaxyCommand();
				// rgc.setModifiedComponent(c); - otherwise the planet will be "run"
				rgc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				rgc.submit();
			}
		}
		
		lv.lumii.datagalaxies.eemm.DetachFrameCommand dfc = eeFactory.createDetachFrameCommand();
		dfc.setFrame(frame);
		dfc.setPermanently(true);
		dfc.submit();
		
		eeFactory.unsetRAAPI();
		geFactory.unsetRAAPI();
		
		return true;
	}

	public static boolean configureViaHTML(IWebMemory raapi, long r, String login)
	// buttons "OK" and "Cancel" can be appended or not depending on the mode (designer's mode or end user's mode)
	{
		try {
			lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
			geFactory.setRAAPI(raapi, "", true);
			boolean designersMode = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).getActiveRunConfiguration().isEmpty();			
			geFactory.unsetRAAPI();
			
			return configureViaHTML(raapi, r, designersMode, designersMode, login); // show buttons iff we are in the designer's mode
		} catch (Throwable e) {
			return false;
		}
		
	}

	public static boolean configureViaHTMLDesignerFile(IWebMemory raapi, long r, String login)
	{
		try {			
			return configureViaHTML(raapi, r, false, true, login);
		} catch (Throwable e) {
			return false;
		}
		
	}

	public static boolean configureViaHTMLEndUserFile(IWebMemory raapi, long r, String login)
	{
		try {			
			return configureViaHTML(raapi, r, true, false, login);
		} catch (Throwable e) {
			return false;
		}
		
	}
	
	private static boolean configureViaHTML(IWebMemory raapi, long r, boolean designerMode, boolean designerFile, String login)
	{
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
			
			lv.lumii.datagalaxies.mm.GalaxyComponent component = null;
			
			String className = null;
			
			boolean configureMulti = false; // whether we will need to call configureMultiTypedStar.html
			boolean chooseMulti = false;  // whether we will need to call chooseTypeForMultiTypedStar.html
			
			String frameLocation = null;
			if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureStar) {
				
				// getting star data type name...
				className = (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0)).getStarDataType();
				
				if (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0).getStarData().isEmpty()) {
					// no star data;
					// checking, whether we need to configure multi-typed star...
					if (className == null)
						configureMulti = true;
					else {
						if (className.contains(",") || !GalaxyEngine_webcalls.isFinalGalacticType(raapi, className).contains("true")) {
							chooseMulti = true;
						}
					}
				}
				
				component = (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0));
				frameLocation = ((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getFrameLocation();
								
				if (!configureMulti && !chooseMulti)
				{
					GalacticTypes.putGalacticTypeIntoMetamodel(login, raapi, className, false);
					raapi.flush();
					
					// creating StarData instance, if there is no such instance...
					if (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0).getStarData().isEmpty()) {					
						long rStarDataCls = raapi.findClass(className);
						long rStarDataObj = raapi.createObject(rStarDataCls);					
						raapi.freeReference(rStarDataCls);
						
						lv.lumii.datagalaxies.mm.StarData sd = (lv.lumii.datagalaxies.mm.StarData)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.StarData.class, rStarDataObj, true);
						boolean ok = (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar()).get(0).getStarData().add(sd);
					}
					
					// getting star data class name:
					if (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0).getStarData().isEmpty()) {
						className = null;
					}
					else {
						long _r = ((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0).getStarData().get(0).getRAAPIReference();
						long _it = raapi.getIteratorForDirectObjectClasses(_r);
						long _rCls = raapi.resolveIteratorFirst(_it);
						raapi.freeIterator(_it);
						className = raapi.getClassName(_rCls);
						raapi.freeReference(_rCls);
					}
				}
			}
			else {
				long rObj = 0;
				if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigurePlanet) {
					component = ((lv.lumii.datagalaxies.mm.ConfigurePlanet) cmd).getPlanet().get(0);
					rObj = component.getRAAPIReference();
					frameLocation = ((lv.lumii.datagalaxies.mm.ConfigurePlanet) cmd).getFrameLocation();
				}
				else
				if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureStellarWind) {
					component = ((lv.lumii.datagalaxies.mm.ConfigureStellarWind) cmd).getStellarWind().get(0);
					rObj = component.getRAAPIReference();
					frameLocation = ((lv.lumii.datagalaxies.mm.ConfigureStellarWind) cmd).getFrameLocation();
				}
				else
				if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureCrossFilter) {
					component = ((lv.lumii.datagalaxies.mm.ConfigureCrossFilter) cmd).getCrossFilter().get(0);
					rObj = component.getRAAPIReference();
					frameLocation = ((lv.lumii.datagalaxies.mm.ConfigureCrossFilter) cmd).getFrameLocation();
				}
				if (rObj == 0)
					return false;
				long it = raapi.getIteratorForDirectObjectClasses(rObj);
				long rCls = raapi.resolveIteratorFirst(it);
				raapi.freeIterator(it);
				className = raapi.getClassName(rCls);
				raapi.freeReference(rCls);
			}
			
			String fileName = null;
			String title = null;
			if (configureMulti) {
				fileName = "configureMultiTypedStar.html";
				title = "Configuring allowed star data types";
			}
			else
			if (chooseMulti) {
				// className contains multiple types, we need to choose:
				fileName = "chooseTypeForMultiTypedStar.html";
				title = "Choosing a final star data type";
			}
			else {
				if (className == null)
					return false;
				
				if (designerFile)
					fileName = "designerConfiguration";
				else
					fileName = "endUserConfiguration";
				
				String dimensions = null;
				
				File f = new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className+File.separator+fileName+".html"); 
				
				if (!f.exists()) {
					for (String s : new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className).list()) {
						if (s.startsWith(fileName+".")) {
							fileName = s;
							dimensions = s.substring(s.indexOf('.')+1, s.lastIndexOf('.'));
							if (dimensions.length()>0) {
								dimensions = "["+dimensions+"]";
							}
							break;
						}
					}
				}
				else
					fileName = fileName+".html";
				
				fileName = className+"/"+fileName;
	 			if (dimensions != null)
	 				if (frameLocation.startsWith("popup") || frameLocation.startsWith("modalpopup") || frameLocation.startsWith("framediv"))
	 					if (frameLocation.indexOf('[')<0)
	 						frameLocation += dimensions;
	 			
	 			title = className+" configuration";
			}
			
			fileName = fileName.replace(" ", "%20");
				
 			lv.lumii.datagalaxies.eemm.Frame frame = eeFactory.createFrame();
			frame.setLocation(frameLocation);
			frame.setCaption(title);
			
			component.setFrame((lv.lumii.datagalaxies.mm.Frame)geFactory.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false));
			
			frame.setOnCloseFrameRequestedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onCloseFrameRequestedEvent");
			
			if (designerMode) {
				frame.setContentURI("html:galactictypes/configureViaHTML.html?mode=designer&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());				
			}
			else
				frame.setContentURI("html:galactictypes/configureViaHTML.html?mode=endUser&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());				
			lv.lumii.datagalaxies.eemm.AttachFrameCommand afc = eeFactory.createAttachFrameCommand();
			afc.setFrame(frame);
			afc.submit();
			
			return true;
		
		}
		finally {
			geFactory.unsetRAAPI();
		}	
		}
		catch(Throwable t) {
			System.err.println(t);
			return false;
		}
	}

	public static boolean configureNothing(RAAPI raapi, long r)
	{		
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
		try {
			try {
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			System.out.println("configureNothing");
			lv.lumii.datagalaxies.mm.GalaxyComponent component = null;
			
			lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
			if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureStar) {
				component = (((lv.lumii.datagalaxies.mm.ConfigureStar) cmd).getStar().get(0));
			}
			else 
			if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigurePlanet) {
				component = ((lv.lumii.datagalaxies.mm.ConfigurePlanet) cmd).getPlanet().get(0);
			}
			else
			if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureStellarWind) {
				component = ((lv.lumii.datagalaxies.mm.ConfigureStellarWind) cmd).getStellarWind().get(0);
			}
			else
			if (cmd instanceof lv.lumii.datagalaxies.mm.ConfigureCrossFilter) {
				component = ((lv.lumii.datagalaxies.mm.ConfigureCrossFilter) cmd).getCrossFilter().get(0);
			}
			
			if (component!=null)
				component.setState("CONFIGURATION_OK");
			
			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rcmd = geFactory.createRefreshGalaxyCommand();
			rcmd.setModifiedComponent(component);
			rcmd.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				
			rcmd.submit();
			
			return true;
		
		}
		finally {
			geFactory.unsetRAAPI();
		}	
		}
		catch(Throwable t) {
			return false;
		}
	}

	public static boolean emitViaHTML(RAAPI raapi, long r)
	{
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			lv.lumii.datagalaxies.mm.EmitStellarWind cmd = (lv.lumii.datagalaxies.mm.EmitStellarWind)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
			
			lv.lumii.datagalaxies.mm.StellarWind component = cmd.getStellarWind().get(0);
			
			String frameLocation = "modalpopup";
			long it = raapi.getIteratorForDirectObjectClasses(cmd.getStellarWind().get(0).getRAAPIReference());
			long rCls = raapi.resolveIteratorFirst(it);
			raapi.freeIterator(it);
			String className = raapi.getClassName(rCls);
			raapi.freeReference(rCls);
			
			if (className == null)
				return false;
			
			
			
			String fileName = "emission";
			
			String dimensions = null;
			
			File f = new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className+File.separator+fileName+".html"); 
			
			if (!f.exists()) {
				for (String s : new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className).list()) {
					if (s.startsWith(fileName+".")) {
						fileName = s;
						dimensions = s.substring(s.indexOf('.')+1, s.lastIndexOf('.'));
						if (dimensions.length()>0) {
							dimensions = "["+dimensions+"]";
						}
						break;
					}
				}
			}
			else
				fileName = fileName+".html";
			
			fileName = className+"/"+fileName;
			
			fileName = fileName.replace(" ", "%20");
				
 			lv.lumii.datagalaxies.eemm.Frame frame = eeFactory.createFrame();
			if ((dimensions != null) && (frameLocation.indexOf('[')<0))
			//-- only modalpopup for stellar winds: if (frameLocation.startsWith("popup") || frameLocation.startsWith("modalpopup") || frameLocation.startsWith("framediv")) 				
				frameLocation += dimensions;
 					
			frame.setLocation(frameLocation);
 			frame.setIsClosable(true);
			frame.setCaption(className+" emission");
			
			frame.setOnCloseFrameRequestedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onCloseFrameRequestedEvent");
			
			component.setFrame((lv.lumii.datagalaxies.mm.Frame)geFactory.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false));
			
			boolean designerMode = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).getActiveRunConfiguration().isEmpty();
			
			if (designerMode)
				frame.setContentURI("html:galactictypes/emitViaHTML.html?mode=designer&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());
			else
				frame.setContentURI("html:galactictypes/emitViaHTML.html?mode=endUser&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());
			
			
			lv.lumii.datagalaxies.eemm.AttachFrameCommand afc = eeFactory.createAttachFrameCommand();
			afc.setFrame(frame);
			afc.submit();
			
			return true;
		
		}
		finally {
			geFactory.unsetRAAPI();
		}				
	}

	public static boolean visualizeViaHTML(RAAPI raapi, long r)
	{
		try {
			lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
			geFactory.setRAAPI(raapi, "", true);
			boolean designersMode = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).getActiveRunConfiguration().isEmpty();			
			geFactory.unsetRAAPI();
			
			return visualizeViaHTML(raapi, r, designersMode, designersMode); // show buttons iff we are in the designer's mode
		} catch (Throwable e) {
			return false;
		}
		
	}

	public static boolean visualizeViaHTMLDesignerFile(RAAPI raapi, long r)
	{
		try {
			return visualizeViaHTML(raapi, r, false, true);
		} catch (Throwable e) {
			return false;
		}
		
	}

	public static boolean visualizeViaHTMLEndUserFile(RAAPI raapi, long r)
	{
		try {
			return visualizeViaHTML(raapi, r, true, false);
		} catch (Throwable e) {
			return false;
		}
		
	}
	
	public static boolean visualizeViaHTML(RAAPI raapi, long r, boolean designerMode, boolean designerFile)
	{
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			lv.lumii.datagalaxies.mm.VisualizePlanet cmd = (lv.lumii.datagalaxies.mm.VisualizePlanet)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
			
			lv.lumii.datagalaxies.mm.Planet component = cmd.getPlanet().get(0);
			
			String frameLocation = cmd.getFrameLocation();
			long it = raapi.getIteratorForDirectObjectClasses(cmd.getPlanet().get(0).getRAAPIReference());
			long rCls = raapi.resolveIteratorFirst(it);
			raapi.freeIterator(it);
			String className = raapi.getClassName(rCls);
			raapi.freeReference(rCls);
			
			if (className == null)
				return false;
			
			
			
			String fileName;
			if (designerFile)
				fileName = "designerVisualization";
			else
				fileName = "endUserVisualization";
			
			String dimensions = null;
			
			File f = new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className+File.separator+fileName+".html"); 
			
			if (!f.exists()) {
				for (String s : new File(GalacticTypes.WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+className).list()) {
					if (s.startsWith(fileName+".")) {
						fileName = s;
						dimensions = s.substring(s.indexOf('.')+1, s.lastIndexOf('.'));
						if (dimensions.length()>0) {
							dimensions = "["+dimensions+"]";
						}
						break;
					}
				}
			}
			else
				fileName = fileName+".html";
			
			fileName = className+"/"+fileName;
			
			fileName = fileName.replace(" ", "%20");
				
 			lv.lumii.datagalaxies.eemm.Frame frame = eeFactory.createFrame();
 			if (frameLocation!=null) {
	 			if (dimensions != null)
	 				if (frameLocation.startsWith("popup") || frameLocation.startsWith("modalpopup") || frameLocation.startsWith("framediv")) {
	 					if (frameLocation.indexOf('[')<0)
	 						frameLocation += dimensions;
	 				}

 			}
 			else
 				frameLocation = "center";
 					
			frame.setLocation(frameLocation);
 			frame.setIsClosable(true);
			frame.setCaption(className+" visualization");
			
			frame.setOnCloseFrameRequestedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onCloseFrameRequestedEvent");
			
			component.setFrame((lv.lumii.datagalaxies.mm.Frame)geFactory.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false));
			
			
			if (designerMode) {
				frame.setContentURI("html:galactictypes/visualizeViaHTML.html?mode=designer&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());				
			}
			else
				frame.setContentURI("html:galactictypes/visualizeViaHTML.html?mode=endUser&fileName="+fileName+"&id="+component.getId()+"&frameReference="+frame.getRAAPIReference());				
			
			
			lv.lumii.datagalaxies.eemm.AttachFrameCommand afc = eeFactory.createAttachFrameCommand();
			afc.setFrame(frame);
			afc.submit();
			
			return true;
		
		}
		finally {
			geFactory.unsetRAAPI();
		}				
	}
	
	public static boolean cleanupStarFile(RAAPI raapi, String project_id, long r)
	{		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			lv.lumii.datagalaxies.mm.CleanupStar cmd = (lv.lumii.datagalaxies.mm.CleanupStar)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);

			
			lv.lumii.datagalaxies.mm.Star star = cmd.getStar().get(0);
			
			if (star.getStarData().isEmpty())
				return true;
			lv.lumii.datagalaxies.mm.StarData data = star.getStarData().get(0);
			
			long it = raapi.getIteratorForDirectObjectClasses(data.getRAAPIReference());
			long rCls = raapi.resolveIteratorFirst(it);
			raapi.freeIterator(it);
//			String className = raapi.getClassName(rCls);
			if (rCls == 0)
				return false;
						
			System.out.println("deleting file in cleanup");
			long rCls2 = raapi.findClass("File");
			if (rCls2 == 0) {
				raapi.freeReference(rCls);
				return false;
			}
			
			boolean retVal = false;
			if (raapi.isDerivedClass(rCls, rCls2)) {
				long rAttr = raapi.findAttribute(rCls2, "fileName");
				if (rAttr != 0) {
					String fileName = raapi.getAttributeValue(data.getRAAPIReference(), rAttr);
					
					if (fileName != null) {
						String dir = API.dataMemory.getProjectFolder(project_id);
						if (dir!=null) {
							File f = new File(dir + File.separator + fileName); 
							f.delete();
							
							retVal = !f.exists();
							if (retVal && star.getProducer().isEmpty()) {
								// clearing file name from the initial star...
								raapi.setAttributeValue(data.getRAAPIReference(), rAttr, "");
								star.setState("CONFIGURATION_ERROR");
							}
						}
					}
					
					raapi.freeReference(rAttr);
				}
			}
			raapi.freeReference(rCls);			
			raapi.freeReference(rCls2);
			
			
			GalaxyHelper.refreshGalaxy(geFactory, null);
			
			return retVal;
		}
		finally {
			geFactory.unsetRAAPI();
		}				
	}
	
	
}
