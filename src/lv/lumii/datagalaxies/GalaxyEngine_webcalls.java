package lv.lumii.datagalaxies;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webappos.auth.UsersManager;
import org.webappos.project.ProjectCache;
import org.webappos.server.API;
import org.webappos.server.ConfigStatic;
import org.webappos.webcaller.IWebCaller;
import org.webappos.webcaller.IWebCaller.WebCallSeed;
import org.webappos.webcaller.WebCaller;
import org.webappos.webmem.IWebMemory;
import org.webappos.webmem.WebMemoryContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import lv.lumii.datagalaxies.mm.StellarWind;
import lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory.ElementReferenceException;
import lv.lumii.datagalaxies.mm.LaunchTransformationCommand;
import lv.lumii.tda.raapi.RAAPI;

public class GalaxyEngine_webcalls {
	
	private static Logger logger =  LoggerFactory.getLogger(GalaxyEngine_webcalls.class);
	
	public static boolean redirectToLaunchTransformationCommand(IWebMemory webmem, long rCmd) {
		// modified for webAppOS: using web call instead of cmd.submit()
		WebMemoryContext ctx = webmem.getContext();
		IWebCaller.WebCallSeed seed = new IWebCaller.WebCallSeed();
		
		seed.actionName = "LaunchTransformationCommand";
		
		seed.callingConventions = WebCaller.CallingConventions.WEBMEMCALL;
		seed.webmemArgument = rCmd;
		

		if (ctx!=null) {
			seed.login = ctx.login;
			seed.project_id = ctx.project_id;
		}
  		
  		API.webCaller.enqueue(seed);
  		return true;
	}
	// Functions for working with galactic types
	
	public static String isFinalGalacticType(RAAPI raapi, String typeName)
	{
		String retVal = " { \"result\" : "+ (GalacticTypes.isFinalGalacticType(typeName)) +" }";
		return retVal;
	}
	
	
	private static String getJsonOfSubTypes(String email, String typeName, Set<String> allowedTypes) {
		StringBuffer retVal = new StringBuffer("{");
		
		Set<String> childTypes = GalacticTypes.getAvailableChildTypes(email, typeName);
		boolean first = true;
		
		for (String childTypeName : childTypes) {
			if ((allowedTypes!=null) && !allowedTypes.contains(childTypeName))
				continue; // skipping this type, since it is not allowed
			if (!first)
				retVal.append(',');
			retVal.append("'"+childTypeName+"' : "+getJsonOfSubTypes(email, childTypeName, allowedTypes));
			first = false;
		}
		
		return retVal.append('}').toString();
	}
	
	
	public static String getAllPossibleGalacticTypes(RAAPI raapi, String s, String login)
	{		
		StringBuffer retVal = new StringBuffer("{");
		retVal.append("'StarData' : " + getJsonOfSubTypes(login, "StarData", null));
		retVal.append(",");
		retVal.append("'StellarWind' : " + getJsonOfSubTypes(login, "StellarWind", null));
		retVal.append(",");
		retVal.append("'Planet' : " + getJsonOfSubTypes(login, "Planet", null));
		retVal.append(",");
		retVal.append("'CrossFilter' : " + getJsonOfSubTypes(login, "CrossFilter", null));
		retVal.append("}");
				
		return retVal.toString().replace("'", "\"");
	}
	
	private static void addTypeAncestors(String email, String typeName, Set<String> list)
	{
		for (String s : GalacticTypes.getAvailableParentTypes(email, typeName)) {
			if (!list.contains(s)) {
				list.add(s);
				addTypeAncestors(email, s, list);
			}
		}
	}
	
	private static void addTypeDescendants(String email, String typeName, Set<String> list)
	{
		for (String s : GalacticTypes.getAvailableChildTypes(email, typeName)) {
			if (!list.contains(s)) {
				list.add(s);
				addTypeDescendants(email, s, list);
			}
		}
	}

	
	
	public static String getPossibleStarDataTypes(RAAPI raapi, String s, String login)
	{		
		StringBuffer retVal = new StringBuffer("{");
		retVal.append("'StarData' : " + getJsonOfSubTypes(login, "StarData", null));
		retVal.append("}");
		
		
		return retVal.toString().replace("'", "\"");
	}
	

	
	public static String getPossibleStellarWinds(RAAPI raapi, String json, String login)
	{
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			factory.setRAAPI(raapi, "", false);
		} catch (ElementReferenceException e) {
			return "{\"error\":\"Web memory metamodel error.\"}";
		}
		
		try {
			JSONArray arr = new JSONArray(json);
			String sourceStarDataTypes[] = new String[arr.length()];
			for (int i=0; i<arr.length(); i++)  {
				// get star type from star id...
				String id = arr.getString(i);
				if (id == null)
					return null;
				lv.lumii.datagalaxies.mm.Star star = null;
				for (lv.lumii.datagalaxies.mm.Star _star : lv.lumii.datagalaxies.mm.Star.allObjects(factory)) {
					if (id.equals(_star.getId())) {
						star = _star;
						break;
					}
				}
				
				if (star==null)
					return null;
				
				sourceStarDataTypes[i] = star.getStarDataType();
				if (sourceStarDataTypes[i]==null)
					sourceStarDataTypes[i]="StarData";
			}
			
			Set<String> possible = new HashSet<String>();

			for (String stW : GalacticTypes.getAvailableFinalStellarWinds(login)) {
				if (GalacticTypes.stellarWindPossible(login, stW, sourceStarDataTypes)) {
					possible.add(stW);
					addTypeAncestors(login, stW, possible);
				}
			}
			
			return "{\"StellarWind\" : " + getJsonOfSubTypes(login, "StellarWind", possible).replace("'", "\"") + "}";
			
		} catch (JSONException e) {
			return null;
		}		
	}

	
	public static String getPossiblePlanets(RAAPI raapi, String starId, String login)
	{
		if (starId == null)
			return null;
		
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			factory.setRAAPI(raapi, "", false);
		} catch (ElementReferenceException e) {
			return null;
		}
		
		try {
			lv.lumii.datagalaxies.mm.Star star = null;
			for (lv.lumii.datagalaxies.mm.Star _star : lv.lumii.datagalaxies.mm.Star.allObjects(factory)) {
				if (starId.equals(_star.getId())) {
					star = _star;
					break;
				}
			}
			
			if (star==null)
				return null;
			
			String starDataType = star.getStarDataType();
			if (starDataType==null)
				starDataType="StarData";
			
			Set<String> possible = new HashSet<String>();

			for (String planet : GalacticTypes.getAvailableFinalPlanets(login)) {
				if (GalacticTypes.planetCanBeAttached(login, planet, starDataType)) {
					possible.add(planet);
					addTypeAncestors(login, planet, possible);
				}
			}
			
			
			return "{\"Planet\" : " + getJsonOfSubTypes(login, "Planet", possible).replace("'", "\"") + "}";
			
		} catch (Throwable e) {
			return null;
		}		
	}
	
	public static String getPossibleCrossFilters(RAAPI raapi, String planetOrWindId, String login)
	{
		if (planetOrWindId == null)
			return null;
		
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			factory.setRAAPI(raapi, "", false);
		} catch (ElementReferenceException e) {
			return null;
		}
		
		try {
			lv.lumii.datagalaxies.mm.GalaxyComponent gc = null;
			for (lv.lumii.datagalaxies.mm.GalaxyComponent _gc : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(factory)) {
				if (planetOrWindId.equals(_gc.getId())) {
					gc = _gc;
					break;
				}
			}
			
			if (gc==null)
				return null;
			
			Set<String> possible = new HashSet<String>();

			long it = raapi.getIteratorForDirectObjectClasses(gc.getRAAPIReference());
			long rCls = raapi.resolveIteratorFirst(it);
			String className = raapi.getClassName(rCls);
			raapi.freeReference(rCls);
			raapi.freeIterator(it);
			
			if (className == null)
				return null;
			
			GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);
			if ((cfg == null) || (cfg.knownCrossFilterTypes==null))
				return null;
			
			for (String crossFilter : cfg.knownCrossFilterTypes) {
				if (GalacticTypes.isTypeAvailable(login, crossFilter)) {
					possible.add(crossFilter);
					addTypeAncestors(login, crossFilter, possible);
					addTypeDescendants(login, crossFilter, possible);
				}
			}
			
			StringBuffer possible_new_val = new StringBuffer(getJsonOfSubTypes(login, "CrossFilter", possible).replace("'", "\""));

			
			StringBuffer possible_existing_val = new StringBuffer("{");
			
			for (String crossFilter : cfg.knownCrossFilterTypes) {
				if (GalacticTypes.isTypeAvailable(login, crossFilter)) {
					rCls = raapi.findClass(crossFilter);
					it = raapi.getIteratorForAllClassObjects(rCls);
					long r = raapi.resolveIteratorFirst(it);
					
					boolean typeExists = (r!=0);
					if (typeExists) {
						if (possible_existing_val.length()>1)
							possible_existing_val.append(",");
						possible_existing_val.append("\""+crossFilter+"\" : {");
					}					
					
					
					while (r!=0) {
						
						lv.lumii.datagalaxies.mm.GalaxyComponent c =
								(lv.lumii.datagalaxies.mm.GalaxyComponent)factory.findOrCreateRAAPIReferenceWrapper(
										lv.lumii.datagalaxies.mm.GalaxyComponent.class, r, false);
						if (c!=null)
							possible_existing_val.append("\""+c.getId()+":"+c.getName()+"\" : {}");
								
						r = raapi.resolveIteratorNext(it);
						
						if ((c!=null) && (r!=0))
							possible_existing_val.append(",");
					}
					
					if (typeExists) {
						possible_existing_val.append("}");
					}
					
					
					raapi.freeReference(rCls);
					raapi.freeIterator(it);
				}
			}
			
			possible_existing_val.append("}");
			
			return "{ \"possible_new\" : "+possible_new_val.toString()+", \"possible_existing\" : "+possible_existing_val.toString()+" } ";
		} catch (Throwable e) {
			return null;
		}		
	}
	
	public static String putGalacticTypeIntoMetamodel(RAAPI raapi, String json, String login)
	{
		boolean ok = GalacticTypes.putGalacticTypeIntoMetamodel(login, raapi, json, false);
		if (ok)
			return "{}";
		else
			return null;
	}
	
	
	// Serializing Edit/Refresh Galaxy Commands
	
	private static boolean checkState(String state)
	{
		if ((state==null) || ("NEW".equals(state)))
			return false;
		return true;
	}
	
	private static String getStateIcon(lv.lumii.datagalaxies.mm.GalaxyComponent c, boolean useGalacticIcons)
	{
		String className = null;
		String state = c.getState();
		
		
		String galacticIcons;
		if (useGalacticIcons)
			galacticIcons = "_GALACTIC";
		else
			galacticIcons = "_CLASSICAL";
		
		long r = 0;		
		if (c instanceof lv.lumii.datagalaxies.mm.Star) {
			try {
				r = ((lv.lumii.datagalaxies.mm.Star) c).getStarData().get(0).getRAAPIReference();
			}
			catch(Throwable t) {}
		}
		else
			r = c.getRAAPIReference();
		
		if (r!=0) {
			long it = c.getRAAPI().getIteratorForDirectObjectClasses(r);
			long rClass = c.getRAAPI().resolveIteratorFirst(it);
			className = c.getRAAPI().getClassName(rClass);
			c.getRAAPI().freeIterator(it);
			c.getRAAPI().freeReference(rClass);
		}
		
		if (className != null) {
			String fileName = "galactictypes/"+className+"/"+state+galacticIcons+".png";
			if (new File(GalacticTypes.WEB_ROOT_DIR+File.separator+fileName).exists())
				return fileName;
		}
		
		if (c instanceof lv.lumii.datagalaxies.mm.Star)
			className = "StarData";
		else
		if (c instanceof lv.lumii.datagalaxies.mm.StellarWind)
			className = "StellarWind";
		else
		if (c instanceof lv.lumii.datagalaxies.mm.CrossFilter)
			className = "CrossFilter";
		else
			className = "Planet";
		
		String fileName = "galactictypes/"+className+"/"+state+galacticIcons+".png";
		if (new File(GalacticTypes.WEB_ROOT_DIR+File.separator+fileName).exists())
			return fileName;
		else
			return "";
	}

	private static long ensureDGFrame(RAAPI raapi, long rCmd) {
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory1 = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			factory1.setRAAPI(raapi, "", false);
		} catch (Throwable e) {
			return 0;
		}
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory factory2 = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		try {
			factory2.setRAAPI(raapi, "", false);
		} catch (Throwable e) {
			return 0;
		}
		
		lv.lumii.datagalaxies.mm.EditGalaxyCommand editCmd = lv.lumii.datagalaxies.mm.EditGalaxyCommand.firstObject(factory1);
		lv.lumii.datagalaxies.mm.RefreshGalaxyCommand refreshCmd = lv.lumii.datagalaxies.mm.RefreshGalaxyCommand.firstObject(factory1);
		
		lv.lumii.datagalaxies.mm.DataGalaxy dg = null;
		
		lv.lumii.datagalaxies.mm.Frame frame = null;
		try {
			dg = editCmd.getDataGalaxy().get(0);
			frame = dg.getFrame().get(0);
		}
		catch(Throwable t) {			
		}
		if (frame==null) {
			try {
				dg = refreshCmd.getDataGalaxy().get(0);
				frame = dg.getFrame().get(0);
			}
			catch(Throwable t) {			
			}			
		}
		
		if (dg==null)
			return 0; // no data galaxy
		
		lv.lumii.datagalaxies.eemm.Frame f = null;
		if (frame!=null)
			f = (lv.lumii.datagalaxies.eemm.Frame)factory2.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false);
		
		if (f==null)
			f = factory2.createFrame();
		
		String contentURI = f.getContentURI();
		if (contentURI==null || contentURI.isEmpty()) {
			if (f.getCaption()==null) { 
				f.setCaption("DataGalaxy");
				f.setIsClosable(false);
			}
			f.setContentURI("html:GalaxyEngine.html?frameReference="+f.getRAAPIReference());
			raapi.createLink(f.getRAAPIReference(), dg.getRAAPIReference(), factory1.FRAME_DATAGALAXY);
			f.setLocation("center");
			f.setOnCloseFrameRequestedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onCloseFrameRequestedEvent");		
			
			lv.lumii.datagalaxies.eemm.AttachFrameCommand attachCmd = factory2.createAttachFrameCommand();
			attachCmd.setFrame(f);
			attachCmd.submit();
		}
		
		return f.getRAAPIReference();
		
	}
	
	private static JSONObject serializeGalaxy(RAAPI raapi) {
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		try {
			factory.setRAAPI(raapi, "", false);
		} catch (ElementReferenceException e) {
			return null;
		}
		
		
		lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(factory);
		if (dg == null)
			return null;
		// getting useGalacticIcons
		lv.lumii.datagalaxies.mm.GalaxyEngine ge = lv.lumii.datagalaxies.mm.GalaxyEngine.firstObject(factory);
		boolean useGalacticIcons = (ge!=null) && (ge.getUseGalacticIcons()!=null) && (ge.getUseGalacticIcons());
		
		JSONObject layoutInfo = null; // legacy coos support		
		try {
			layoutInfo = new JSONObject( dg.getLayoutInfo() );
			layoutInfo = layoutInfo.getJSONObject("xy");
		}
		catch(Throwable t) {				
		}
		dg.setLayoutInfo(null);
		
		int SCALE_FACTOR = 5;
		
		
		JSONObject retVal = new JSONObject();
		try {
			JSONArray nodes = new JSONArray();		
			JSONArray links = new JSONArray();		
			retVal.put("nodes", nodes);
			retVal.put("edges", links);
			
			JSONObject tooltips = new JSONObject();
			retVal.put("tooltips", tooltips);
			
			
			for (lv.lumii.datagalaxies.mm.Star star : lv.lumii.datagalaxies.mm.Star.allObjects(factory)) {
				JSONObject o = new JSONObject();
				o.put("id", star.getId());
				String state = star.getState();
				if (!checkState(state)) {
					GalaxyHelper.deleteGalaxyComponent(star);
					continue; // ignoring this star
				}			
				String label = star.getName();
				if (label==null)
					label = "";
				o.put("label", label);
				String image = getStateIcon(star, useGalacticIcons);
				o.put("shape", "image");
				o.put("image", image);
				
				String location = star.getLocation();
				if (location==null) {					
					try { JSONObject xy = layoutInfo.getJSONObject(star.getId()); location = xy.getDouble("x")+","+xy.getDouble("y"); star.setLocation(location); } catch (Throwable t) {}
				}
				if ((location != null) && (!location.isEmpty())) {
					int i = location.indexOf(',');
					if (i>=0) {
						o.put("x", Double.parseDouble(location.substring(0,i)));
						o.put("y", Double.parseDouble(location.substring(i+1)));
						JSONObject fixed = new JSONObject();
						fixed.put("x", true); fixed.put("y", true);
						o.put("fixed", fixed);
					}
				}
				o.put("size", 10*SCALE_FACTOR);
				nodes.put(o);
				//retVal.append("{'id':'"+star.getId()+"'"+location+", 'loaded':'true', 'style':{'fillColor':null, 'lineColor':null, 'image':'"+image+"','label': '"+label+"','radius': 10}}");
			}
			
			for (lv.lumii.datagalaxies.mm.Planet planet : lv.lumii.datagalaxies.mm.Planet.allObjects(factory)) {
				JSONObject o = new JSONObject();
				o.put("id", planet.getId());
				String state = planet.getState();
				if (!checkState(state)) {
					GalaxyHelper.deleteGalaxyComponent(planet);
					continue; // ignoring this planet
				}
				String label = planet.getName();
				if (label==null)
					label = "";
				o.put("label", label);
				String image = getStateIcon(planet, useGalacticIcons);
				o.put("shape", "image");
				o.put("image", image);
				
				String location = planet.getLocation();
				if (location==null) {					
					try { JSONObject xy = layoutInfo.getJSONObject(planet.getId()); location = xy.getDouble("x")+","+xy.getDouble("y"); planet.setLocation(location); } catch (Throwable t) {}
				}
				if ((location != null) && (!location.isEmpty())) {
					int i = location.indexOf(',');
					if (i>=0) {
						o.put("x", Double.parseDouble(location.substring(0,i)));
						o.put("y", Double.parseDouble(location.substring(i+1)));
						JSONObject fixed = new JSONObject();
						fixed.put("x", true); fixed.put("y", true);
						o.put("fixed", fixed);
					}
				}
				o.put("size", 8*SCALE_FACTOR);
				//retVal.append("{'id':'"+planet.getId()+"'"+location+", 'loaded':'true', 'style':{'fillColor':null, 'lineColor':null, 'image':'"+image+"','label': '"+label+"','radius': 8}}");
				nodes.put(o);
			}
			for (lv.lumii.datagalaxies.mm.CrossFilter crossFilter : lv.lumii.datagalaxies.mm.CrossFilter.allObjects(factory)) {
				JSONObject o = new JSONObject();
				o.put("id", crossFilter.getId());
				String state = crossFilter.getState();
				if (!checkState(state)) {
					GalaxyHelper.deleteGalaxyComponent(crossFilter);
					continue; // ignoring this cross filter
				}
				String label = crossFilter.getName();
				if (label==null)
					label = "";
				o.put("label", label);
				String image = getStateIcon(crossFilter, useGalacticIcons);
				o.put("shape", "image");
				o.put("image", image);
				
				String location = crossFilter.getLocation();
				if (location==null) {					
					try { JSONObject xy = layoutInfo.getJSONObject(crossFilter.getId()); location = xy.getDouble("x")+","+xy.getDouble("y"); crossFilter.setLocation(location); } catch (Throwable t) {}
				}
				if ((location != null) && (!location.isEmpty())) {
					int i = location.indexOf(',');
					if (i>=0) {
						o.put("x", Double.parseDouble(location.substring(0,i)));
						o.put("y", Double.parseDouble(location.substring(i+1)));
						JSONObject fixed = new JSONObject();
						fixed.put("x", true); fixed.put("y", true);
						o.put("fixed", fixed);
					}
				}
				o.put("size", 6*SCALE_FACTOR);
				nodes.put(o);
				//retVal.append("{'id':'"+crossFilter.getId()+"'"+location+", 'loaded':'true', 'style':{'fillColor':null, 'lineColor':null, 'image':'"+image+"','label': '"+label+"','radius': 6}}");
			}
			for (lv.lumii.datagalaxies.mm.StellarWind stellarWind : lv.lumii.datagalaxies.mm.StellarWind.allObjects(factory)) {			
				JSONObject o = new JSONObject();
				o.put("id", stellarWind.getId());
				String state = stellarWind.getState();
				if (!checkState(state)) {
					GalaxyHelper.deleteGalaxyComponent(stellarWind);
					continue; // ignoring this stellar wind
				}
				String label = stellarWind.getName();
				if (label==null)
					label = "";
				o.put("label", label);
				String image = getStateIcon(stellarWind, useGalacticIcons);
				o.put("shape", "image");
				o.put("image", image);
				
				String location = stellarWind.getLocation();
				if (location==null) {					
					try { JSONObject xy = layoutInfo.getJSONObject(stellarWind.getId()); location = xy.getDouble("x")+","+xy.getDouble("y"); stellarWind.setLocation(location); } catch (Throwable t) {}
				}
				if ((location != null) && (!location.isEmpty())) {
					int i = location.indexOf(',');
					if (i>=0) {
						o.put("x", Double.parseDouble(location.substring(0,i)));
						o.put("y", Double.parseDouble(location.substring(i+1)));
						JSONObject fixed = new JSONObject();
						fixed.put("x", true); fixed.put("y", true);
						o.put("fixed", fixed);
					}
				}
				o.put("size", 9*SCALE_FACTOR);
				nodes.put(o);
	
	//			retVal.append("{'id':'"+stellarWind.getId()+"'"+location+", 'loaded':'true', 'style':{'fillColor':null, 'lineColor':null, 'image':'"+image+"','label': '"+label+"','radius': 9}}");
			}
			
			for (lv.lumii.datagalaxies.mm.StellarWind stellarWind : lv.lumii.datagalaxies.mm.StellarWind.allObjects(factory)) {			
				
				String appendDashedStyle = "";
				if (raapi.isKindOf(stellarWind.getRAAPIReference(), raapi.findClass("Manual"))) {				
					appendDashedStyle = ", 'dashed': 'true'";
				}
				
				String fillColor = "#ffc61e";
				if (!useGalacticIcons)
					fillColor = "#406C8B";
				if ("RUN_OK".equalsIgnoreCase(stellarWind.getState()))
					fillColor = "#00ff00";
				if ("RUN_ERROR".equalsIgnoreCase(stellarWind.getState()))
					fillColor = "#ff0000";
				for (lv.lumii.datagalaxies.mm.Star p : stellarWind.getSource()) {
					JSONObject l = new JSONObject();
					l.put("id", "Into"+stellarWind.getId()+"From"+p.getId());
					JSONObject color = new JSONObject();
					color.put("color", fillColor);				
					l.put("color", color);
					l.put("arrows", "to");
					l.put("from", p.getId());
					l.put("to",stellarWind.getId());
					links.put(l);
					//retVal.append("{'id':'Into"+stellarWind.getId()+"From"+p.getId()+"', 'style': {'direction': 'R', 'fromDecoration':null, 'toDecoration': null, 'fillColor':'"+fillColor+"'"+appendDashedStyle+"}, 'from': '"+p.getId()+"', 'to': '"+stellarWind.getId()+"', 'share': 0}");
				}
				for (lv.lumii.datagalaxies.mm.Star p : stellarWind.getTarget()) {
					JSONObject l = new JSONObject();
					l.put("id", "From"+stellarWind.getId()+"To"+p.getId());
					JSONObject color = new JSONObject();
					color.put("color", fillColor);				
					l.put("color", color);
					l.put("arrows", "to");
					l.put("from", stellarWind.getId());
					l.put("to", p.getId());
					
					links.put(l);
					//retVal.append("{'id':'From"+stellarWind.getId()+"To"+p.getId()+"', 'style': {'direction': 'R', 'fromDecoration':null, 'toDecoration': 'arrow', 'fillColor':'"+fillColor+"'"+appendDashedStyle+"}, 'from': '"+stellarWind.getId()+"', 'to': '"+p.getId()+"', 'share': 0}");
				}
			}
			for (lv.lumii.datagalaxies.mm.Planet planet : lv.lumii.datagalaxies.mm.Planet.allObjects(factory)) {			
				String fillColor = "#ffc61e"; 
				if (!useGalacticIcons)
					fillColor = "#406C8B";
				if ("RUN_OK".equalsIgnoreCase(planet.getState()))
					fillColor = "#00ff00";
				if ("RUN_ERROR".equalsIgnoreCase(planet.getState()))
					fillColor = "#ff0000";
				
				for (lv.lumii.datagalaxies.mm.Star star : planet.getStar()) {
					JSONObject l = new JSONObject();
					l.put("id", "Into"+planet.getId()+"From"+star.getId());
					JSONObject color = new JSONObject();
					color.put("color", fillColor);				
					l.put("color", color);
					l.put("from", star.getId());
					l.put("to", planet.getId());
					
					l.put("dashes", true);
					links.put(l);
					//retVal.append("{'id':'Into"+planet.getId()+"From"+star.getId()+"', 'style': {'fromDecoration':null, 'toDecoration': null, 'fillColor':'"+fillColor+"'}, 'from': '"+star.getId()+"', 'to': '"+planet.getId()+"', 'share': 0}");
				}
			}
			for (lv.lumii.datagalaxies.mm.CrossFilter crossFilter : lv.lumii.datagalaxies.mm.CrossFilter.allObjects(factory)) {						
				String fillColor = "#ffc61e"; 
				if (!useGalacticIcons)
					fillColor = "#406C8B";
				
				for (lv.lumii.datagalaxies.mm.StellarWind stellarWind : crossFilter.getStellarWind()) {
					JSONObject l = new JSONObject();
					l.put("id", "From"+crossFilter.getId()+"To"+stellarWind.getId());
					JSONObject color = new JSONObject();
					color.put("color", fillColor);				
					l.put("color", color);
					l.put("from", crossFilter.getId());
					l.put("to", stellarWind.getId());
					
					l.put("dashes", true);
					links.put(l);
					//retVal.append("{'id':'From"+crossFilter.getId()+"To"+stellarWind.getId()+"', 'style': {'lineDash':2, 'fromDecoration':null, 'toDecoration': null, 'fillColor':'"+fillColor+"'}, 'from': '"+crossFilter.getId()+"', 'to': '"+stellarWind.getId()+"', 'share': 0}");				
				}
				for (lv.lumii.datagalaxies.mm.Planet planet : crossFilter.getPlanet()) {
					JSONObject l = new JSONObject();
					l.put("id", "From"+crossFilter.getId()+"To"+planet.getId());
					JSONObject color = new JSONObject();
					color.put("color", fillColor);				
					l.put("color", color);
					l.put("from", crossFilter.getId());
					l.put("to", planet.getId());
					
					l.put("dashes", true);
					links.put(l);
					//retVal.append("{'id':'From"+crossFilter.getId()+"To"+planet.getId()+"', 'style': {'lineDash':2, 'fromDecoration':null, 'toDecoration': null, 'fillColor':'"+fillColor+"'}, 'from': '"+crossFilter.getId()+"', 'to': '"+planet.getId()+"', 'share': 0}");				
				}			
			}
			
			
			// Tooltips...
			for (lv.lumii.datagalaxies.mm.GalaxyComponent c : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(factory)) {
				String msg = c.getStateMessage();
				if (msg!=null) {
					int i = msg.indexOf('\'',0);
					while (i>0) {
						msg = msg.substring(0,i)+"\\"+msg.substring(i);
						i = msg.indexOf('\'',i+2);
					}
					i = msg.indexOf('\"',0);
					while (i>0) {
						msg = msg.substring(0,i)+"\\"+msg.substring(i);
						i = msg.indexOf('\"',i+2);
					}
					tooltips.put(c.getId(), msg);
				}
			}
		
		} catch (JSONException e) {
			e.printStackTrace(); // TODO:
		}
		return retVal;
		
	}
	public static boolean prepareAndContinueEditGalaxyCommand(IWebMemory raapi, long rCmd)
	{
		JSONObject retVal = serializeGalaxy(raapi);
		if (retVal==null)
			return false;
		
		long rFrame = ensureDGFrame(raapi, rCmd);
		if (rFrame == 0)
			return false;
		
		try {
			retVal.put("frameReference", rFrame);
		} catch (JSONException e) {
		}

		raapi.deleteObject(rCmd);
		
		WebMemoryContext ctx = raapi.getContext();
		IWebCaller.WebCallSeed seed = new IWebCaller.WebCallSeed();
					
		seed.actionName = "continueEditGalaxyCommand";
		
		//System.out.println(retVal);
		seed.callingConventions = WebCaller.CallingConventions.JSONCALL;
		seed.jsonArgument = retVal.toString();
		
		if (ctx!=null) {
			seed.login = ctx.login;
			seed.project_id = ctx.project_id;
		}

  		API.webCaller.enqueue(seed);

/*		lv.lumii.datagalaxies.eemm.PostMessageToFrameCommand postCmd = factory2.createPostMessageToFrameCommand();
		postCmd.setFrame((lv.lumii.datagalaxies.eemm.Frame)factory2.findOrCreateRAAPIReferenceWrapper(rFrame, true));
		postCmd.setMessageURI("javascript:executeEditGalaxyCommand("+s+")");
		postCmd.submit();*/
		

		return true;

	}

	public static boolean prepareAndContinueRefreshGalaxyCommand(IWebMemory raapi, long r)
	{		
			lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
			try {
				factory.setRAAPI(raapi, "", false);
			} catch (ElementReferenceException e) {
				return false;
			}
				
			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand cmd = (lv.lumii.datagalaxies.mm.RefreshGalaxyCommand)factory.findOrCreateRAAPIReferenceWrapper(r, true);
						
			JSONObject retVal = serializeGalaxy(raapi);
			
			long rFrame = ensureDGFrame(raapi, r);
			if (rFrame == 0) {
				cmd.delete();
				return false;
			}
			
			try {
				retVal.put("frameReference", rFrame);

				JSONArray modifiedComponent = new JSONArray();
				retVal.put("modifiedComponent", modifiedComponent);
							
				for (lv.lumii.datagalaxies.mm.GalaxyComponent c : cmd.getModifiedComponent()) {
					JSONObject obj = new JSONObject();
					obj.put("id", c.getId());
					obj.put("reference", c.getRAAPIReference());
					modifiedComponent.put(obj);
				}
			} catch (JSONException e1) {
			}
			
			cmd.delete();
			
			// continue...
			lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory factory2 = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
			try {
				factory2.setRAAPI(raapi, "", false);
			} catch (Throwable e) {
				return false;
			}
			
			
			WebMemoryContext ctx = raapi.getContext();
			IWebCaller.WebCallSeed seed = new IWebCaller.WebCallSeed();
						
			seed.actionName = "continueRefreshGalaxyCommand";
			
			System.out.println(retVal);
			seed.callingConventions = WebCaller.CallingConventions.JSONCALL;
			seed.jsonArgument = retVal.toString();
			
			if (ctx!=null) {
				seed.login = ctx.login;
				seed.project_id = ctx.project_id;
			}
	
	  		API.webCaller.enqueue(seed);

			return true;
	}
	
	// Editing a galaxy...
	
	public static String storeGalaxyCoordinates(RAAPI raapi, String s)
	{
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory factory = null;
		try {
	
			factory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
			try {
				factory.setRAAPI(raapi, "", false);
			} catch (ElementReferenceException e) {
				return null;
			}
			
			lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(factory);
			if (dg!=null)
				dg.setLayoutInfo(s);
			
			return "{}";
			
		} catch (Throwable e) {
			return null;
		}			
		finally {
			if (factory != null)
				factory.unsetRAAPI();
		}
	}
	
		
	public static String configureGalaxyComponent(IWebMemory webmem, String json)
	{
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(webmem, "", true);
				geFactory.setRAAPI(webmem, "", true);
			} catch (Throwable e) {
				return null;
			}
			

			JSONObject cmdJsonObj = null;
			String id = null;
			String what = null;
			String frameLocation = null;
			JSONObject componentJsonObj = null;
			try {
				cmdJsonObj = new JSONObject(json);
				frameLocation = cmdJsonObj.getString("frameLocation");
				try {
					componentJsonObj = cmdJsonObj.getJSONArray("star").getJSONObject(0);
				}
				catch (Throwable t) {
					try {
						componentJsonObj = cmdJsonObj.getJSONObject("star");
					}
					catch(Throwable tt) {}
				}
				if (componentJsonObj!=null) {
					id = componentJsonObj.getString("id");
					what = "Star";
				}
				else {
					try {
						componentJsonObj = cmdJsonObj.getJSONArray("stellarWind").getJSONObject(0);
					}
					catch (Throwable t) {
						try {
							componentJsonObj = cmdJsonObj.getJSONObject("stellarWind");
						}
						catch(Throwable tt) {}
					}
					if (componentJsonObj!=null) {
						id = componentJsonObj.getString("id");
						what = "StellarWind";
					}
					else {
						try {
							componentJsonObj = cmdJsonObj.getJSONArray("crossFilter").getJSONObject(0);
						}
						catch (Throwable t) {
							try {
								componentJsonObj = cmdJsonObj.getJSONObject("crossFilter");
							}
							catch(Throwable tt) {}
						}
						if (componentJsonObj!=null) {
							id = componentJsonObj.getString("id");
							what = "CrossFilter";
						}
						else {
							try {
								componentJsonObj = cmdJsonObj.getJSONArray("planet").getJSONObject(0);
							}
							catch (Throwable t) {
								try {
									componentJsonObj = cmdJsonObj.getJSONObject("planet");
								}
								catch(Throwable tt) {}								
							}
							if (componentJsonObj!=null) {
								id = componentJsonObj.getString("id");
								what = "Planet";
							}
						}						
					}
				}
					
			} catch (JSONException e) {
				return null;
			}
			
			if (id==null)
				return null;
			
			// check if that galaxy component exists...
			lv.lumii.datagalaxies.mm.GalaxyComponent componentObj = null;
			if ("Star".equals(what))
				for (lv.lumii.datagalaxies.mm.Star c : lv.lumii.datagalaxies.mm.Star.allObjects(geFactory)) {
					if (id.equals(c.getId())) {
						componentObj = c;
						break;
					}
				}
			else
			if ("StellarWind".equals(what))
				for (lv.lumii.datagalaxies.mm.StellarWind c : lv.lumii.datagalaxies.mm.StellarWind.allObjects(geFactory)) {
					if (id.equals(c.getId())) {
						componentObj = c;
						break;
					}
				}
			else
			if ("Planet".equals(what))
				for (lv.lumii.datagalaxies.mm.Planet c : lv.lumii.datagalaxies.mm.Planet.allObjects(geFactory)) {
					if (id.equals(c.getId())) {
						componentObj = c;
						break;
					}
				}
			else
			if ("CrossFilter".equals(what))
				for (lv.lumii.datagalaxies.mm.CrossFilter c : lv.lumii.datagalaxies.mm.CrossFilter.allObjects(geFactory)) {
					if (id.equals(c.getId())) {
						componentObj = c;
						break;
					}
				}
			
			if (componentObj == null) { // not found
				logger.error("configureGalaxyComponent error: component "+id+" not found; the client side must create it before");
				return null;
			}
			
			
			// Storing new attribute values in the corresponding galaxy component... 			
			TdaJson.storeTdaJsonString(webmem, componentJsonObj.toString());
			
			
			// Searching for a transformation to call...
			long it = webmem.getIteratorForDirectObjectClasses(componentObj.getRAAPIReference());			
			long rCls = webmem.resolveIteratorFirst(it);
			webmem.freeIterator(it);
			String className = webmem.getClassName(rCls);
			webmem.freeReference(rCls);
			if (className == null)
				return null;
			
			boolean multiTypedStar = false;
			
			if ("Star".equals(className)) {
				// getting star data type name...
				className = ((lv.lumii.datagalaxies.mm.Star)componentObj).getStarDataType();
								
				if (className == null) {
					multiTypedStar = true;
				}
				else {						
					
					if (((lv.lumii.datagalaxies.mm.Star)componentObj).getStarData().size() == 0) {
						if (className.contains(",") || !isFinalGalacticType(webmem, className).contains("true")) {
							multiTypedStar = true;
						}
					}
					else {
						long _r = ((lv.lumii.datagalaxies.mm.Star)componentObj).getStarData().get(0).getRAAPIReference();
						long _it = webmem.getIteratorForDirectObjectClasses(_r);
						long _rCls = webmem.resolveIteratorFirst(_it);
						webmem.freeIterator(_it);
						className = webmem.getClassName(_rCls);
						webmem.freeReference(_rCls);
					}
				}
			}

			if (componentObj instanceof lv.lumii.datagalaxies.mm.StellarWind) {
				if (!((lv.lumii.datagalaxies.mm.StellarWind) componentObj).getTarget().isEmpty()) {
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);
					if (cfg != null)
						((lv.lumii.datagalaxies.mm.StellarWind) componentObj).getTarget().get(0).setStarDataType(cfg.allowedTargetTypes);
				}
			}
			
			String URI = null;
			
			if (multiTypedStar) {
				URI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureViaHTML"; // this function will take care of multi-typed stars
			}
			else {
				GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);
				if (cfg == null)
					return null;
			
				if (lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).getActiveRunConfiguration().isEmpty()) {
					URI = cfg.designerConfigurationURI;
				}
				else
					URI = cfg.endUserConfigurationURI;
			}
			
			// issue CloseFrameRequestedEvent...
			if (!componentObj.getFrame().isEmpty()) {
				
				lv.lumii.datagalaxies.eemm.CloseFrameRequestedEvent ev = eeFactory.createCloseFrameRequestedEvent();
				ev.setForce(true);
				lv.lumii.datagalaxies.mm.Frame frame_ = componentObj.getFrame().get(0);
				lv.lumii.datagalaxies.eemm.Frame frame =  (lv.lumii.datagalaxies.eemm.Frame)eeFactory.findOrCreateRAAPIReferenceWrapper(frame_.getRAAPIReference(), false); 
				ev.setFrame(frame);
				ev.submit();
				
				
			}
			GalaxyHelper.closeComponentFrame(eeFactory, componentObj);

			if (URI==null) {
				
				if ("NEW".equals( componentObj.getState()) ) {
					GalaxyHelper.deleteGalaxyComponent(componentObj);
				}
				
					
					
				return null;
			}
			
			
			long rCmdCls = webmem.findClass("Configure"+what);
			long rCmdObj = webmem.createObject(rCmdCls);
			webmem.freeReference(rCmdCls);
			

			lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (LaunchTransformationCommand) geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, rCmdObj, true);
			cmd.setUri(URI);
			
			
			
			lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd_ = 
					(lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(rCmdObj, false);
			if ("Star".equals(what)) {
				((lv.lumii.datagalaxies.mm.ConfigureStar)cmd_).setFrameLocation(frameLocation);
				((lv.lumii.datagalaxies.mm.ConfigureStar)cmd_).setStar((lv.lumii.datagalaxies.mm.Star)componentObj);
			}
			if ("Planet".equals(what)) {
				((lv.lumii.datagalaxies.mm.ConfigurePlanet)cmd_).setFrameLocation(frameLocation);
				((lv.lumii.datagalaxies.mm.ConfigurePlanet)cmd_).setPlanet((lv.lumii.datagalaxies.mm.Planet)componentObj);
			}
			if ("StellarWind".equals(what)) {
				((lv.lumii.datagalaxies.mm.ConfigureStellarWind)cmd_).setFrameLocation(frameLocation);
				((lv.lumii.datagalaxies.mm.ConfigureStellarWind)cmd_).setStellarWind((lv.lumii.datagalaxies.mm.StellarWind)componentObj);
			}
			if ("CrossFilter".equals(what)) {
				((lv.lumii.datagalaxies.mm.ConfigureCrossFilter)cmd_).setFrameLocation(frameLocation);
				((lv.lumii.datagalaxies.mm.ConfigureCrossFilter)cmd_).setCrossFilter((lv.lumii.datagalaxies.mm.CrossFilter)componentObj);
			}
			

			cmd.submit();
			
			
			
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}

		return "{}";
	}

	
	// cleanup BEFORE delete!!!
	public static String deleteGalaxyComponents(RAAPI raapi, String json)
	{		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return null;
			}
			
			JSONArray arr = null;
			try {
				arr = new JSONArray(json);
				
				for (int i=0; i<arr.length(); i++) {
					String id = arr.getString(i);
				
			
					lv.lumii.datagalaxies.mm.GalaxyComponent componentObj = null;
					for (lv.lumii.datagalaxies.mm.GalaxyComponent c : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
						if (id.equals(c.getId())) {
							componentObj = c;
							break;
						}
					}
					
					if (componentObj == null)
						return null;
					else {
						GalaxyHelper.deleteGalaxyComponent(componentObj);
					}
				
				}
				
				GalaxyHelper.refreshGalaxy(geFactory, null);
			
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
			
			
			return json;
		}
		finally {
			
			
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}

	}
	
	private static String displayStyleToGridStackDiv(String style, String classes)
	{
		int j = style.indexOf(";");
		int l = style.lastIndexOf(";");
		int i = style.indexOf(":");//substring(0, j).indexOf(":");
		int k = style.lastIndexOf(":");//substring(j+1).indexOf(":");
		// width:123;height:456;
		//      i   j      k   l		
		
		int w=2, h=2;
		try {
			w = Integer.parseInt(style.substring(i+1,j).trim());
			w = w/100;
			if (w<1)
				w=1;
			if (w>12)
				w=12;
		}
		catch(Throwable t) {}
		try {
			h = Integer.parseInt(style.substring(k+1,l).trim());
			h = h/100;
			if (h<1)
				h=1;
			if (h>12)
				h=12;
		}
		catch(Throwable t) {}
				
		if (classes!=null)
			return "<div class=\"grid-stack-item "+classes+"\" data-gs-width=\""+w+"\" data-gs-height=\""+h+"\">";		
		else
			return "<div class=\"grid-stack-item\" data-gs-width=\""+w+"\" data-gs-height=\""+h+"\">";		
	}
	
	public static String deployGalaxyForEndUsers(String project_id, String id/*arg*/, String login, String appFullName) {
		IWebMemory webmem = API.dataMemory.getWebMemory(project_id);
		System.out.println("DEPLOY "+project_id+" arg="+id+" login="+login+", "+webmem);
		if (webmem==null)
			return null;
		
		long r;
		try {
			r = Long.parseLong(id);
			if (r==0)
				return null;
		}
		catch (Throwable t) {
			return null;
		}
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(webmem, "", true);
				geFactory.setRAAPI(webmem, "", true);
			} catch (Throwable e) {
				return null;
			}
			
			
			lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory);
			lv.lumii.datagalaxies.mm.GalaxyEngine ge = lv.lumii.datagalaxies.mm.GalaxyEngine.firstObject(geFactory);
						
			lv.lumii.datagalaxies.mm.RunConfiguration rc =
					(lv.lumii.datagalaxies.mm.RunConfiguration)geFactory.findOrCreateRAAPIReferenceWrapper(r, true);

			
			// checking for components that must be configured...
			// (initial stars with no configuration, or non-stars with no configuration)
			for (lv.lumii.datagalaxies.mm.GalaxyComponent c : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
				if (c instanceof lv.lumii.datagalaxies.mm.Star) {
					lv.lumii.datagalaxies.mm.Star s = (lv.lumii.datagalaxies.mm.Star)c; 
					if (s.getProducer().isEmpty()) {
						if ("CONFIGURATION_ERROR".equals(c.getState()) || "CONFIGURATION_UNKNOWN".equals(c.getState())) {
							if (!rc.getMustConfigureComponent().contains(s)) {
								rc.getMustConfigureComponent().add(s);
							}
							if (!rc.getVisibleComponent().contains(s)) {
								rc.getMustConfigureComponent().add(s);
							}
						}						
					}					
				}
				else
				if ("CONFIGURATION_ERROR".equals(c.getState()) || "CONFIGURATION_UNKNOWN".equals(c.getState())) {
					if (!rc.getMustConfigureComponent().contains(c)) {
						rc.getMustConfigureComponent().add(c);
					}
					if (!rc.getVisibleComponent().contains(c)) {
						rc.getMustConfigureComponent().add(c);
					}
				}
			}
			
			File f = new File(API.dataMemory.getProjectFolder(project_id)+File.separator+"GalaxyEngineForEndUsers.html");
			
			PrintWriter w = null;
			try {
				w = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(f)), "UTF-8"));
				w.println("<html>");
				w.println("<head>");
				w.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				//local scripts:
				



				w.println("<script src=\"end-user-js-css/jquery.min.js\" type=\"text/javascript\"></script>"); 
				w.println("<script src=\"end-user-js-css/jquery-ui.min.js\" type=\"text/javascript\"></script>");
				w.println("<link href=\"end-user-js-css/jquery-ui.min.css\" rel=\"stylesheet\"></link>");

				w.println("<link href=\"end-user-js-css/bootstrap.min.css\" rel=\"stylesheet\"></link>");
				w.println("<script src=\"end-user-js-css/bootstrap.min.js\" type=\"text/javascript\"></script>");
				
				w.println("<script src=\"end-user-js-css/gridstack.js\" type=\"text/javascript\"></script>");
				w.println("<link href=\"end-user-js-css/gridstack.css\" rel=\"stylesheet\"/>");
				
				w.println("<script type=\"text/javascript\" src=\"/dojo/dojo.js\" data-dojo-config=\"async:0\" type=\"text/javascript\"></script>");
				w.println("<script type=\"text/javascript\" src=\"/webappos.js\" type=\"text/javascript\"></script>");
				w.println("<style>");
				w.println(".geditable:hover { background-color: rgba(217, 245, 255,0.5); }");
				w.println(".geditable:focus { background-color: rgba(217, 245, 255,0.5); }");
				w.println("</style>");
						
				w.println("</head>");
				w.println("<body>");
				w.println("<h1 class=\"geditable\" style=\"font-size:18.0pt;padding-left:10;font-family:'Segoe UI Light','sans-serif'\">"+rc.getName()+"</h1>");
				String desc = rc.getDescription();
				if ((desc!=null) && (!desc.isEmpty()))
				  w.println("<i><p class=\"geditable\" style=\"padding-left:10;\" id=\"description\">"+desc+"</p></i>");
				
				w.println("<div id=\"errorDiv\" style=\"padding-left:10;font-family: 'Consolas', 'monospace'; color:#c5000b\"></div>");
				
				w.println("<div id=\"gridstack1\" class=\"grid-stack configurationDiv\" style=\"margin-bottom:5;margin-top:5;background-color:#F0F8FF;\">");
				
				for (lv.lumii.datagalaxies.mm.GalaxyComponent c : rc.getVisibleComponent()) {
					long it = c.getRAAPI().getIteratorForDirectObjectClasses(c.getRAAPIReference());
					long rCls = c.getRAAPI().resolveIteratorFirst(it);
					c.getRAAPI().freeIterator(it);
					String className = c.getRAAPI().getClassName(rCls);
					c.getRAAPI().freeReference(rCls);
					
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);					
					String cfgStyle = "width:400px;height:300px;"; 
					if (cfg!=null) {
						cfgStyle = cfg.endUserConfigurationStyle;
					}
					
					String classes;					
					if (rc.getMustConfigureComponent().contains(c))
						classes = "configurationDiv";
					else
						continue;
					
					w.println(displayStyleToGridStackDiv(cfgStyle, classes));
					w.println("<div class=\"grid-stack-item-content\" style=\"padding-top:20px;\">"); // background-color:red
					w.println("<div style=\"margin-top:-20px; height:20px; color:white; background-color:blue;\"><span class=\"geditable\">"+c.getName()+"</span>");
					w.println("</div>");
					w.println("<div id=\"configurationFor"+c.getId()+"\" class=\""+classes+"\" name=\""+c.getName()+"\" style=\"min-height:100%;width:100%;background-color:#e3ebf3\">");
					
					if (c.getName()!=null)
						w.println("Configuration for "+c.getName());
					else
						w.println("Configuration for "+c.getId());
					w.println("</div>");
					w.println("</div>");
					w.println("</div>");
				}
				w.println("</div>"); // for gridstack1
				
				w.println("<div id=\"gridstack2\" class=\"grid-stack advancedConfigurationDiv\" style=\"margin-bottom:5;margin-top:5;background-color:#F0F0FF;\">");
				
				for (lv.lumii.datagalaxies.mm.GalaxyComponent c : rc.getVisibleComponent()) {
					long it = c.getRAAPI().getIteratorForDirectObjectClasses(c.getRAAPIReference());
					long rCls = c.getRAAPI().resolveIteratorFirst(it);
					c.getRAAPI().freeIterator(it);
					String className = c.getRAAPI().getClassName(rCls);
					c.getRAAPI().freeReference(rCls);
					
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);					
					String cfgStyle = "width:400px;height:300px;"; 
					if (cfg!=null) {
						cfgStyle = cfg.endUserConfigurationStyle;
					}
					
					String classes;					
					if (rc.getMustConfigureComponent().contains(c))
						continue; 
					else
						classes = "advancedConfigurationDiv";
					
					w.println(displayStyleToGridStackDiv(cfgStyle, classes));
					w.println("<div class=\"grid-stack-item-content\" style=\"padding-top:20px;\">"); // background-color:red
					w.println("<div style=\"margin-top:-20px; height:20px; color:white; background-color:blue;\"><span class=\"geditable\">"+c.getName()+"</span>");
					w.println("</div>");
					w.println("<div id=\"configurationFor"+c.getId()+"\" class=\""+classes+"\" name=\""+c.getName()+"\" style=\"min-height:100%;width:100%;background-color:#e3ebf3\">");
					
					if (c.getName()!=null)
						w.println("Configuration for "+c.getName());
					else
						w.println("Configuration for "+c.getId());
					w.println("</div>");
					w.println("</div>");
					w.println("</div>");
				}
				w.println("</div>"); // for gridstack2

				w.println("<div id=\"gridstack3\" class=\"grid-stack visualizationDiv\" style=\"margin-bottom:5;margin-top:5;background-color:#F0E8FF;\">"); // overflow: auto;
				
				for (lv.lumii.datagalaxies.mm.GalaxyComponent c : rc.getVisibleComponent()) {
					long it = c.getRAAPI().getIteratorForDirectObjectClasses(c.getRAAPIReference());
					long rCls = c.getRAAPI().resolveIteratorFirst(it);
					c.getRAAPI().freeIterator(it);
					String className = c.getRAAPI().getClassName(rCls);
					c.getRAAPI().freeReference(rCls);
					
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(className);					
					String vizStyle = "width:400px;height:300px;"; 
					if (cfg!=null) {
						vizStyle = cfg.endUserVisualizationStyle;
					}
					
					if (c instanceof lv.lumii.datagalaxies.mm.Planet) {
						// adding visualization div
						w.println(displayStyleToGridStackDiv(vizStyle, "visualizationDiv"));
						w.println("<div class=\"grid-stack-item-content\" style=\"padding-top:20px;\">"); //background-color:red
						w.println("<div style=\"margin-top:-20px; height:20px; color:white; background-color:blue;\"><span class=\"geditable\">"+c.getName()+"</span>");
						w.println("</div>");
						w.println("<div id=\"visualizationFor"+c.getId()+"\" class=\"visualizationDiv\" name=\""+c.getName()+"\" style=\"min-height:100%;width:100%;background-color:#e3ebf3\">");
						
						if (c.getName()!=null)
							w.println("Visualization for "+c.getName());
						else
							w.println("Visualization for "+c.getId());
						w.println("</div>");
						w.println("</div>");						
						w.println("</div>");
					}
				}								
				w.println("</div>"); // for gridstack3
				
				w.println("<script>");
/*				w.println("function setGridStackSize() {");
				w.println("     var yT=null, yB=null;");
				w.println("     $( \"#gridstack1\" ).children().each( function( index, element ){");
				w.println("       if (element.style.display==\"none\")");
				w.println("          return;");
				w.println("       if ((yT==null) || (yT>parseInt(element.dataset.gsY)))");
				w.println("         yT=parseInt(element.dataset.gsY);");
				w.println("       if ((yB==null) || (yB<parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight)))");
				w.println("         yB=parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight);");
				w.println("       console.log(element,yT,yB);");
				w.println("     });");
				w.println("     if ((yT!=null) && (yB!=null)) {");
				w.println("         $('#gridstack1').css(\"min-height\", (yB-yT)*80+(yB-yT-1)*10);");
				w.println("         gridstack1.style[\"min-height\"]=(yB-yT)*80+(yB-yT-1)*10;");
				w.println("     }");
				w.println("     yT=null, yB=null;");
				
				w.println("     $( \"#gridstack2\" ).children().each( function( index, element ){");
				w.println("       if (element.style.display==\"none\")");
				w.println("          return;");
				w.println("       if ((yT==null) || (yT>parseInt(element.dataset.gsY)))");
				w.println("         yT=parseInt(element.dataset.gsY);");
				w.println("       if ((yB==null) || (yB<parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight)))");
				w.println("         yB=parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight);");
				w.println("       console.log(element,yT,yB);");
				w.println("     });");
				w.println("     if ((yT!=null) && (yB!=null)) {");
				w.println("         $('#gridstack2').css(\"min-height\", (yB-yT)*80+(yB-yT-1)*10);");
				w.println("         gridstack2.style[\"min-height\"]=(yB-yT)*80+(yB-yT-1)*10;");
				w.println("     }");
				
				w.println("     yT=null, yB=null;");
				w.println("     $( \"#gridstack3\" ).children().each( function( index, element ){");
				w.println("       if (element.style.display==\"none\")");
				w.println("          return;");
				w.println("       if ((yT==null) || (yT>parseInt(element.dataset.gsY)))");
				w.println("         yT=parseInt(element.dataset.gsY);");
				w.println("       if ((yB==null) || (yB<parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight)))");
				w.println("         yB=parseInt(element.dataset.gsY)+parseInt(element.dataset.gsHeight);");
				w.println("       console.log(element,yT,yB);");
				w.println("     });");
				w.println("     if ((yT!=null) && (yB!=null)) {");
				w.println("         $('#gridstack3').css(\"min-height\", (yB-yT)*80+(yB-yT-1)*10);");
				w.println("         gridstack3.style[\"min-height\"]=(yB-yT)*80+(yB-yT-1)*10;");
				w.println("     }");				
				w.println("}");*/

				w.println("$(document).ready(function() {");
				w.println("  var options = {");
				w.println("    cell_height: 80,");
				w.println("    vertical_margin: 10,");
				w.println("    resizable: {");
				w.println("      handles: 'e, se, s, sw, w'");
				w.println("    },");
				w.println("    handle: '.grid-stack-item-content',");
				w.println("    draggable: {handle: '.grid-stack-item-content', scroll: true, appendTo: 'body'},");
				w.println("    resizable: {autoHide: true, handles: 'e, se, s, sw, w'}");
				w.println("  };");
				w.println("  $('#gridstack1').gridstack(options);");
				w.println("  $('#gridstack2').gridstack(options);");
				w.println("  $('#gridstack3').gridstack(options);");
				
//				w.println("  setTimeout(setGridStackSize, 500);");
				w.println("  var fStart = function (event, ui) {");
				w.println("	    var grid = this;");
				w.println("	    var element = event.target;");
				w.println("     $(\".grid-stack-item\").css(\"z-index\", \"\");");
				w.println("     element.style[\"z-index\"]=10000;");
				w.println("     $(\".grid-stack-item-content\").css(\"pointer-events\", \"none\");");
				w.println("  };");
				w.println("  var fStop = function (event, ui) {");
				w.println("     var grid = this;");
				w.println("     var element = event.target;");
				w.println("     $(\".grid-stack-item-content\").css(\"pointer-events\", \"auto\");");
				w.println("     setTimeout(function() {");
//				w.println("       setGridStackSize();");
				w.println("       $(\".grid-stack-item\").css(\"z-index\", \"\");");
				w.println("       element.style[\"z-index\"]=10000;");
				w.println("     },100);");
				w.println("  };");
				w.println("  $('.grid-stack').on('dragstart', fStart);");
				w.println("  $('.grid-stack').on('dragstop', fStop);");
				w.println("  $('.grid-stack').on('resizestart', fStart);");
				w.println("  $('.grid-stack').on('resizestop', fStop);");
				w.println();

				w.println("});");
				w.println("</script>");

				
				w.println("<script id=\"scripts\" class=\"not-forever\">");
				w.println("$(document).ready(function() {");
				w.println("  $('.geditable').attr(\"contenteditable\", \"true\");");
				w.println("  $('.geditable').attr(\"onclick\", \"$(this).focus();\");");
				w.println("});");

				w.println("function saveFile()");
				w.println("{");
				w.println("  $('#gridstack1').attr('class', 'grid-stack configurationDiv');");
				w.println("  $('#gridstack2').attr('class', 'grid-stack advancedConfigurationDiv');");
				w.println("  $('#gridstack3').attr('class', 'grid-stack visualizationConfigurationDiv');");
				w.println("  $('#gridstack3').hide();");
				w.println("  $('.grid-stack-item').removeClass('ui-draggable').removeClass('ui-resizable').removeClass('ui-resizable-autohide');");
				w.println("  $('.grid-stack-item-content').removeClass('ui-draggable-handle');");
				w.println("  $('.grid-stack').css('height', '');");
				w.println("  $('.grid-stack').css('min-height', '');");
				w.println("  $('.grid-stack-item-content').css('pointer-events', '');");				
				w.println("  $('.grid-stack-placeholder').remove();");				
				w.println("  $('.ui-resizable-handle').remove();");
				
				
				w.println("  $(\".not-for-save\").remove();");
				w.println("  $(\"iframe\").remove();");
				w.println("  var s = \"<html>\\n\"+document.head.outerHTML+document.body.outerHTML+\"\\n</html>\";");
				w.println("  setTimeout( function() {");
				w.println("    webappos.webcall(\"webappos.uploadFileToCurrentProject\", {");
				w.println("      fileName: \"GalaxyEngineForEndUsers.html\",");
				w.println("      content: s");
				w.println("    }).then(()=>window.top.location.reload());");
				w.println("  }, 100); // wait for jQuery to finish refreshing the document...");
				w.println("}");

				w.println("function onSaveClick()");
				w.println("{");
				w.println("  saveFile();");
				w.println("}");

				w.println("function onSaveForeverClick()");
				w.println("{");
				w.println("  $(\".grid-stack-item\").attr(\"data-gs-no-resize\", true);");
				w.println("  $(\".grid-stack-item\").attr(\"data-gs-no-move\", true);");
				w.println("  $(\".not-forever\").remove();");
				w.println("  $(\".grid-stack-placeholder\").remove();");
				w.println("  $(\".geditable\").removeAttr(\"contenteditable\");");
				w.println("  $(\".geditable\").removeClass();");
				w.println("  saveFile();");
				w.println("}");
				w.println("</script>");

				w.println("<div class=\"not-forever\" style=\"margin:10;\">");
				w.println("  <input type=\"button\" value=\"Save layout\" onclick=\"onSaveClick();\"/>");
				w.println("  <input type=\"button\" value=\"Save layout forever\" onclick=\"onSaveForeverClick();\"/>");
				w.println("</div>");
				
				
				w.println("</body>");
				w.println("</html>");
								
			}
			catch (Throwable t) {
				return null;
			}
			finally {
				if (w!=null)
					w.close();
			}
			
			
			dg.setActiveRunConfiguration(rc);
			
			Boolean savedGalacticIcons = ge.getUseGalacticIcons();
			ge.setUseGalacticIcons(true);
			
			lv.lumii.tda.kernel.TDAKernel tdaKernel = (lv.lumii.tda.kernel.TDAKernel)webmem; // !!!! TODO save webmem w/o TDAKernel
			
			boolean ok = true;
			String errorMessage = null;
			
			if (!tdaKernel.startSave()) {
				errorMessage = "GalaxyEngine deploy: Could not start the save process.";
				System.err.println(errorMessage);
				ok = false;
			}
			if (ok && !tdaKernel.finishSave()) {
				errorMessage = "GalaxyEngine deploy: Could not finish the save process.";
				System.err.println(errorMessage);
				ok = false;
			}
			
			String templateName = rc.getName();
			if ((templateName==null) || (templateName.isEmpty()))
				templateName = "MyDataGalaxyForEndUser";
			
			File fTemplate;
			if (UsersManager.userInGroup(login, "admin"))
				// Global tool template
				fTemplate = new File(DataGalaxiesApp.APP_DIR+File.separator+"templates"+File.separator+templateName+".datagalaxy"); 
			else
				// Local user template
				fTemplate = new File(ConfigStatic.HOME_DIR+File.separator+login+File.separator+"templates"+File.separator+templateName+".datagalaxy");
				// !!!!! ^^^^ TODO via HomeFS
			
			fTemplate.getParentFile().mkdirs();
			
			
			if (ok) {
				//ok = lv.lumii.tda.util.ZipFolder.zip(f.getParentFile().toPath(), fTemplate);
				ok = ProjectCache.zip(f.getParentFile().toPath(), fTemplate);
				if (!ok) {
					errorMessage = "GalaxyEngine deploy: Could not create (zip) a template for end users.";
				}
			}
			
			dg.setActiveRunConfiguration(null);
			ge.setUseGalacticIcons(savedGalacticIcons);
			
			if (!tdaKernel.startSave()) {
				logger.error("GalaxyEngine deploy: Could not start the save process (after deploy).");
				ok = false;
			}
			else
			if (!tdaKernel.finishSave()) {
				logger.error("GalaxyEngine deploy: Could not finish the save process (after deploy).");
				ok = false;
			}
			
			f.delete();
			
			if (errorMessage==null)
				return "{\"result\":"+ok+"}";
			else
				return "{\"result\":"+ok+", \"errorMessage\":\""+errorMessage+"\"}";
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}
	}	
		
	public static String inEndUserMode(RAAPI raapi, String json) {		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return null;
			}
			
			lv.lumii.datagalaxies.mm.DataGalaxy dg = lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory);
						
			if (dg.getActiveRunConfiguration().isEmpty()) {
				return "{\"result\":false}";
			}
			else
				return "{\"result\":true,\n\"runConfiguration\":"+TdaJson.getTdaJsonString(raapi, dg.getActiveRunConfiguration().get(0).getRAAPIReference(), true, false)+"}";
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}
	}	
	
	// Runs

	private static void reverseTopologicalSortViaReverseDFS(
			lv.lumii.datagalaxies.mm.GalaxyComponent c, // current component
			Set<lv.lumii.datagalaxies.mm.GalaxyComponent> reached,
			List<String> list)
	{
		reached.add(c);
		
		// adjust the "RUN_ERROR" or "RUNNING" state
		if ("RUN_ERROR".equals(c.getState()) || "RUNNING".equals(c.getState())) {
			if (c instanceof lv.lumii.datagalaxies.mm.Star) {
				if (((lv.lumii.datagalaxies.mm.Star) c).getStarData().isEmpty()) {
					c.setState("CONFIGURATION_UNKNOWN");
				}
				else
					c.setState("CONFIGURATION_OK");
			}
			c.setState("CONFIGURATION_OK");
			c.setStateMessage(null);
		}
								
		if ("RUN_OK".equals(c.getState())) {
			// already run; do not add "earlier" components
			return;
		}
		
		// for all "earlier" and not-reached components call DFS...
		// adding "earlier" components and then add the current component c to the list
		if (c instanceof lv.lumii.datagalaxies.mm.Planet) {
			GalaxyHelper.closeComponentFrame(c);
			
			List<lv.lumii.datagalaxies.mm.Star> stars = ((lv.lumii.datagalaxies.mm.Planet) c).getStar();
			for (lv.lumii.datagalaxies.mm.Star s : stars) {
				if (!reached.contains(s)) {
					reverseTopologicalSortViaReverseDFS(s, reached, list);
				}
			}
			
			list.add("{\"id\":\""+c.getId()+"\", \"action\":\"VisualizePlanet\"}");
			return;
		}
		else
		if (c instanceof lv.lumii.datagalaxies.mm.Star) {
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.Star) c).getProducer()) {
				if (!reached.contains(w))
					reverseTopologicalSortViaReverseDFS(w, reached, list);
			}
			
			for (lv.lumii.datagalaxies.mm.Planet p : ((lv.lumii.datagalaxies.mm.Star) c).getPlanet()) {
				GalaxyHelper.closeComponentFrame(p);			
			}
			
			
			if (((lv.lumii.datagalaxies.mm.Star) c).getProducer().isEmpty()) { // initialStar
				list.add("{\"id\":\""+c.getId()+"\", \"action\":\"InitializeStar\"}");
				list.add("{\"id\":\""+c.getId()+"\", \"action\":\"FinalizeStar\"}");				
			}
			else
				// non-initial stars are initialized and finalized when the corresponding stellar wind, which creates the star,
				// is being emitted
				return;
		}
		else
		if (c instanceof lv.lumii.datagalaxies.mm.StellarWind) {
			for (lv.lumii.datagalaxies.mm.Star src : ((lv.lumii.datagalaxies.mm.StellarWind) c).getSource()) {
				if (!reached.contains(src))
					reverseTopologicalSortViaReverseDFS(src, reached, list);
			}
			
			for (lv.lumii.datagalaxies.mm.Star tgt : ((lv.lumii.datagalaxies.mm.StellarWind) c).getTarget()) {
				list.add("{\"id\":\""+tgt.getId()+"\", \"action\":\"InitializeStar\"}");
			}						 			
			list.add("{\"id\":\""+c.getId()+"\", \"action\":\"EmitStellarWind\"}");
			for (lv.lumii.datagalaxies.mm.Star tgt : ((lv.lumii.datagalaxies.mm.StellarWind) c).getTarget()) {
				list.add("{\"id\":\""+tgt.getId()+"\", \"action\":\"FinalizeStar\"}");
			}						 
			return;
		}
		
	}
	

	/**
	 * Returns the actions that have to be performed to be able to run
	 * the given galaxy component (emit a stellar wind or visualize a planet).
	 * Actions are returned in the JSON array, where actions have to be passed to
	 * the runGalaxyComponentAsync in the same order (this will be topological order).
	 * 
	 * @param raapi
	 * @param id the id of a stellar wind or planet, which needs to be run (emitted, visualized),
	 * @return a json array of objects { "action" : "some-action", "id" : "some-id" }, where
	 *   each such object can be passed to the runGalaxyComponentAsync
	 *   function 
	 */
	public static String getRunsForGalaxyComponent(RAAPI raapi, String id)
	{
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return null;
			}
			
			if (id==null)
				return null;
			
			String[] ids = id.split(",");
			Set<lv.lumii.datagalaxies.mm.GalaxyComponent> reached = new HashSet<lv.lumii.datagalaxies.mm.GalaxyComponent>();
			List<String> list = new LinkedList<String>();
			
			for (String id1 : ids) {				
			
				// check if that galaxy component exists...
				lv.lumii.datagalaxies.mm.GalaxyComponent c = null;
				for (lv.lumii.datagalaxies.mm.GalaxyComponent cc : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
					if (id1.equals(cc.getId())) {
						c = cc;
						break;
					}
				}
				if (c == null) { // not found
					continue;//return null;
				}
				
				if (c instanceof lv.lumii.datagalaxies.mm.CrossFilter)
					continue;//return "[]"; // nothing to run for a cross-filter
	
				if (!reached.contains(c))
					reverseTopologicalSortViaReverseDFS(c, reached, list);
			}
						
			StringBuffer retVal = new StringBuffer("[");
			for (String s : list) {
				if (retVal.length()==1)
					retVal.append(s);
				else {
					retVal.append(",");
					retVal.append(s);
				}
			}
			retVal.append("]");
			
			return retVal.toString();
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}

	}
	
	private static void forwardCleanup(
			lv.lumii.datagalaxies.mm.GalaxyComponent componentObj,
			List<lv.lumii.datagalaxies.mm.GalaxyComponent> visited,
			List<String> runs,
			boolean includeThisComponent)
	{				
		if (visited.contains(componentObj))
			return;
		
		visited.add(componentObj);
		
		if (componentObj instanceof lv.lumii.datagalaxies.mm.Star) {
						
			if ("RUN_OK".equals( componentObj.getState() ) || "RUN_ERROR".equals( componentObj.getState() )) {
				// cleanup star...
				componentObj.setState("CONFIGURATION_OK");
				componentObj.setStateMessage(null);
			}
			
			if (includeThisComponent) {
				runs.add("{\"id\":\""+componentObj.getId()+"\", \"action\":\"CleanupStar\"}");
			}				
			
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.Star) componentObj).getProducer()) {				
				if ("RUN_OK".equals( w.getState() ) || "RUN_ERROR".equals( w.getState() )) {
					w.setState("CONFIGURATION_OK");
				}
			}								
								
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.Star) componentObj).getConsumer()) {				
				if ("RUN_OK".equals( w.getState() ) || "RUN_ERROR".equals( w.getState() )) {
					w.setState("CONFIGURATION_OK");
				}
				forwardCleanup(w, visited, runs, true);
			}								
			
			for (lv.lumii.datagalaxies.mm.GalaxyComponent p : ((lv.lumii.datagalaxies.mm.Star) componentObj).getPlanet()) {
				if ("RUN_OK".equals( p.getState() ) || "RUN_ERROR".equals( p.getState() )) {
					p.setState("CONFIGURATION_OK");
					GalaxyHelper.closeComponentFrame(p);
				}
				forwardCleanup(p, visited, runs, true);
			}
		}
		
		if (componentObj instanceof lv.lumii.datagalaxies.mm.StellarWind) {
			if ("RUN_OK".equals( componentObj.getState() ) || "RUN_ERROR".equals( componentObj.getState() )) {
				componentObj.setState("CONFIGURATION_OK");
			}
			for (lv.lumii.datagalaxies.mm.Star s : ((lv.lumii.datagalaxies.mm.StellarWind) componentObj).getTarget()) {
				forwardCleanup(s, visited, runs, true);
			}
			
		}
		
		if (componentObj instanceof lv.lumii.datagalaxies.mm.Planet) {
			if ("RUN_OK".equals( componentObj.getState() ) || "RUN_ERROR".equals( componentObj.getState() )) {
				componentObj.setState("CONFIGURATION_OK");
				
				GalaxyHelper.closeComponentFrame(componentObj);
			}			
			// there are no forward links from planets
		}
		
		if (componentObj instanceof lv.lumii.datagalaxies.mm.CrossFilter) {
			for (lv.lumii.datagalaxies.mm.Planet p : ((lv.lumii.datagalaxies.mm.CrossFilter) componentObj).getPlanet()) {
				forwardCleanup(p, visited, runs, true);
			}
			
			for (lv.lumii.datagalaxies.mm.StellarWind w : ((lv.lumii.datagalaxies.mm.CrossFilter) componentObj).getStellarWind()) {
				forwardCleanup(w, visited, runs, true);
			}
			
		}		
	}

	/**
	 * Returns the actions that have to be performed to cleanup the current data galaxy.
	 * Non-initial star data-s having emptyOnInit=true are emptied, all planet visualizations are closed,
	 * if stars and stellar winds are marked as "RUN_OK", they become marked as "CONFIGURATION_OK". 
	 * 
	 * @param raapi
	 * @param json a stringified JSON array of galaxy component id-s to cleanup; if empty, the cleanup is being performed globally
	 *        if for some object a prefix ">" is added (e.g., ">1234"), then this object is excluded from cleanup, but forward
	 *        galaxy elements are still included
	 * @return a json array of objects { "action" : "some-action", "id" : "some-id" }, where
	 *   each such object can be passed to the runGalaxyComponentAsync
	 *   function
	 */
	public static String getRunsForCleanup(RAAPI raapi, String json)
	{
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return null;
			}
			
			
			List<String> list = new LinkedList<String>();
			
			try {
				JSONArray arr = new JSONArray(json);
				if (arr.length() == 0)
					throw new RuntimeException("Do globally!");
				
				List<lv.lumii.datagalaxies.mm.GalaxyComponent> visited = new LinkedList<lv.lumii.datagalaxies.mm.GalaxyComponent>();
				for (int i=0; i<arr.length(); i++) {
					try {
						String id = arr.getString(i);
						boolean forward = false;
						if ((id!=null)&&(id.startsWith(">"))) {
							forward = true;
							id = id.substring(1);
						}
						lv.lumii.datagalaxies.mm.GalaxyComponent c = null;
						for (lv.lumii.datagalaxies.mm.GalaxyComponent cc : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
							if (id.equals(cc.getId())) {
								c = cc;
								break;
							}
						}
						if (c != null)
							forwardCleanup(c, visited, list, !forward);
					}
					catch (Throwable t) {
						// skip this array element...
					}
				}
				
			} catch (JSONException e) {
				// global cleanup...
				// closing all open planets...
				for (lv.lumii.datagalaxies.mm.Planet p : lv.lumii.datagalaxies.mm.Planet.allObjects(geFactory)) {
					if ("RUN_OK".equals( p.getState() ) || "RUN_ERROR".equals( p.getState() )) {
						p.setState("CONFIGURATION_OK");
						p.setStateMessage(null);
						GalaxyHelper.closeComponentFrame(p);
					}			
				}
				
				// marking all RUN_OK and RUN_ERROR stellar winds as CONFIGURATION_OK (since run could be performed after CONFIGURATION_OK)
				for (lv.lumii.datagalaxies.mm.StellarWind w : lv.lumii.datagalaxies.mm.StellarWind.allObjects(geFactory)) {
					if ("RUN_OK".equals(w.getState()) || "RUN_ERROR".equals(w.getState())) {
						w.setState("CONFIGURATION_OK");
						w.setStateMessage(null);
					}
				}
				
				// marking all RUN_OK and RUN_ERROR stars as CONFIGURATION_OK (since run could be performed after CONFIGURATION_OK)
				// and adding CleanupStar actions for non-initial stars having emptyOnInit==true			
				for (lv.lumii.datagalaxies.mm.Star s : lv.lumii.datagalaxies.mm.Star.allObjects(geFactory)) {
					if ("RUN_OK".equals(s.getState()) || "RUN_ERROR".equals(s.getState())) {
						s.setState("CONFIGURATION_OK");
						s.setStateMessage(null);
						
						if (!s.getProducer().isEmpty()) {
							// non-initial star
							if ((s.getEmptyOnInit()==null) || s.getEmptyOnInit()) { // default: cleanup
								list.add("{\"id\":\""+s.getId()+"\", \"action\":\"CleanupStar\"}");
							}
						}
						else { // initial star
							if ((s.getEmptyOnInit()!=null) && s.getEmptyOnInit()) { // default: not cleanup
								list.add("{\"id\":\""+s.getId()+"\", \"action\":\"CleanupStar\"}");
							}
							
						}
					}
				}
			}
			

			
			StringBuffer retVal = new StringBuffer("[");
			for (String s : list) {
				if (retVal.length()==1)
					retVal.append(s);
				else {
					retVal.append(",");
					retVal.append(s);
				}
			}
			retVal.append("]");
			
			return retVal.toString();
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}
	}
	
	/**
	 * Launches (asynchronously) the given "run" action for the galaxy component with the given id.
	 * Takes into a consideration the currently active run configuration (if any).
	 * @param raapi
	 * @param the json consists of two attributes:
	 *        "action" - values can be "InitializeStar", "FinalizeStar", "EmitStellarWind", or "VisualizePlanet"
	 *        "id" - the id of the component, for which the given action has to be performed
	 *        "frameLocation" - for VisualizePlanet 
	 * @return "{}" on success, or {error:msg} on error
	 */
	//public static String runGalaxyComponentAsync(RAAPI raapi, String json)
	
	public static String runGalaxyComponentAsync(String project_id, String json, String login, String appFullName)	
	{		
		IWebMemory raapi = API.dataMemory.getWebMemory(project_id);
		if (raapi==null)
			return "{\"error\":\"Web memory is not present.\"}";

		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return "{\"error\":\"Web memory metamodel error.\"}";
			}
			

			JSONObject jsonObj = null;
			String id = null;
			String action = null;
			String frameLocation = null;
			try {
				jsonObj = new JSONObject(json);
				id = jsonObj.getString("id");
				action = jsonObj.getString("action");
			} catch (JSONException e) {
				return "{\"error\":\"Wrong json passed to runGalaxyComponentAsync.\"}";
			}
			
			try {
				frameLocation = jsonObj.getString("frameLocation");
			} catch (JSONException e) {
			}
			
			if (id==null)
				return "{\"error\":\"Wrong json passed to runGalaxyComponentAsync.\"}";
			
			// check if that galaxy component exists...
			lv.lumii.datagalaxies.mm.GalaxyComponent c = null;
			for (lv.lumii.datagalaxies.mm.GalaxyComponent cc : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
				if (id.equals(cc.getId())) {
					c = cc;
					break;
				}
			}
			if (c == null) { // not found
				return "{\"error\":\"Component not found.\"}";
			}
				
			if (c instanceof lv.lumii.datagalaxies.mm.CrossFilter)		
				return "{}";
			
			if ("RUN_OK".equals(c.getState()) /*&& !"FinalizeStar".equals(action)*/) {
				GalaxyHelper.refreshGalaxy(geFactory, c);
				return "{}"; // already run
			}
			
			if ("CONFIGURATION_UNKNOWN".equals(c.getState())) {
				c.setStateMessage("The star has not been initialized/finalized since the star data type is not specified (yet).");
				GalaxyHelper.refreshGalaxy(geFactory, c);
				return "{}"; // assume that this is OK; we set no error to the state
			}
			
			if (!"CONFIGURATION_OK".equals(c.getState()) && !"RUNNING".equals(c.getState())) {
				System.out.println(c.getState()+" "+c.getId());
					
				GalaxyHelper.refreshGalaxy(geFactory, c);
				if ("CONFIGURATION_ERROR".equals(c.getState()))
					return "{\"error\":\"Configuration error.\"}";
				else
					return "{\"error\":\"Run or configuration error.\"}";
			}
			
			
			if (c instanceof lv.lumii.datagalaxies.mm.Star) {
				if ("InitializeStar".equals(action)) {
					
					if (((lv.lumii.datagalaxies.mm.Star) c).getStarData().isEmpty()) {
						c.setState("CONFIGURATION_UNKNOWN"); // setting (back) to CONFIGURATION_UNKNOWN
						//c.setStateMessage(null);
						GalaxyHelper.refreshGalaxy(geFactory, c); // we need to call refresh to be able to execute the next run action at the client side
						return "{}"; // when no star data is attached, we assume that the initialization is empty - the corresponding
						             // stellar wind that leads to this star must also initialize it
					}

					// skip, if the producing stellar wind is not in the RUN_OK state
					if (   (!((lv.lumii.datagalaxies.mm.Star) c).getProducer().isEmpty())
						&& (!"RUN_OK".equals(((lv.lumii.datagalaxies.mm.Star) c).getProducer().get(0).getState()))  ) {
						
						if (!"CONFIGURATION_ERROR".equals(c.getState()) && !"CONFIGURATION_UNKNOWN".equals(c.getState()))
							c.setState("CONFIGURATION_OK");							
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
					
					

					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(GalaxyHelper.getGalacticTypeName(c));
					if (cfg == null) {
						c.setState("RUN_ERROR");
						c.setStateMessage("Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c));
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{\"error\":\"Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
					}
					
					
					
					if (cfg.initializationURI!=null) {
						
						
						lv.lumii.datagalaxies.mm.InitializeStar cmd_ = geFactory.createInitializeStar(); 
						cmd_.setStar((lv.lumii.datagalaxies.mm.Star) c);
						
						lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, cmd_.getRAAPIReference(), false);
						cmd.setUri(cfg.initializationURI);
						
						/// !!!!
						boolean ok = cmd.submit();
						cmd_.delete();	
						
						if (ok) {
							c.setState("RUNNING");
							c.setStateMessage(null);
							// the transformation must refresh the state by its own
							return "{}";
						}
						else {
							c.setState("RUN_ERROR");
							c.setStateMessage("Could not launch star initialization transformation "+cfg.initializationURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c));
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "{\"error\":\"Could not launch star initialization transformation "+cfg.initializationURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
						}
					}
					else {
						// refresh the state as "RUNNING"
						c.setState("RUNNING");
						c.setStateMessage(null);
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
				}
				else
				if ("FinalizeStar".equals(action)) {										

					if (((lv.lumii.datagalaxies.mm.Star) c).getStarData().isEmpty()) {
						c.setStateMessage("The star has not been initialized/finalized since the star data type is not specified (yet).");
						c.setState("CONFIGURATION_UNKNOWN");
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}"; // when no star data is attached, we assume that the initialization is empty - the corresponding
						             // stellar wind that leads to this star must also initialize it
					}
					
					if (!"RUNNING".equals(c.getState())) {
						// skip FinalizeStar, since this star has not been initialized
						if (!"CONFIGURATION_ERROR".equals(c.getState()) && !"CONFIGURATION_UNKNOWN".equals(c.getState()))
							c.setState("CONFIGURATION_OK");							
						
						c.setStateMessage(null);
						GalaxyHelper.refreshGalaxy(geFactory, c); // go to the next action
						return "{}";
					}

					
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(GalaxyHelper.getGalacticTypeName(c));
					if (cfg == null) {
						c.setState("RUN_ERROR");
						c.setStateMessage("Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c));
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{\"error\":\"Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
					}
					
					
					if (cfg.finalizationURI!=null) {
						lv.lumii.datagalaxies.mm.FinalizeStar cmd_ = geFactory.createFinalizeStar(); 
						cmd_.setStar((lv.lumii.datagalaxies.mm.Star) c);
						
						lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, cmd_.getRAAPIReference(), false);
						cmd.setUri(cfg.finalizationURI);
						
						///!!!!
						boolean ok = cmd.submit();
						cmd_.delete();	
						
						if (ok) {
							// the transformation must refresh the state by its own
							c.setState("RUNNING");
							c.setStateMessage(null);
							return "{}";
						}
						else {
							c.setState("RUN_ERROR");
							c.setStateMessage("Could not launch star finalization transformation "+cfg.finalizationURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c));
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "\"error\":\"Could not launch star finalization transformation "+cfg.finalizationURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
						}
					}
					else {
						c.setState("RUN_OK");
						c.setStateMessage(null);
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
				}
				else
				if ("CleanupStar".equals(action)) {
						
					if (((lv.lumii.datagalaxies.mm.Star) c).getStarData().isEmpty()) {						
						c.setState("CONFIGURATION_UNKNOWN"); // setting (back) to CONFIGURATION_UNKNOWN
						GalaxyHelper.refreshGalaxy(geFactory, c); // we need to call refresh to be able to execute the next run action at the client side
						return "{}"; // when no star data is attached, we assume that the cleanup action is to do nothing
					}

					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(GalaxyHelper.getGalacticTypeName(c));
					if (cfg == null) {
						c.setState("RUN_ERROR");
						c.setStateMessage("Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c));
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{\"error\":\"Could not find the type configuration for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
					}
										
					
					if (cfg.cleanupURI!=null) {
						
						
						lv.lumii.datagalaxies.mm.CleanupStar cmd_ = geFactory.createCleanupStar(); 
						cmd_.setStar((lv.lumii.datagalaxies.mm.Star) c);
						
						lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, cmd_.getRAAPIReference(), false);
						cmd.setUri(cfg.cleanupURI);
						
						c.setState("CONFIGURATION_OK");
						c.setStateMessage(null);
						
						///!!!!
						boolean ok = cmd.submit(); // this transformation may change the state, e.g., to CONFIGURATION_ERROR
						cmd_.delete();	
						
						if (ok) {
							// the transformation must refresh the state by its own
							return "{}";
						}
						else {
							c.setState("RUN_ERROR");
							c.setStateMessage("Could not launch cleanup star transformation "+cfg.cleanupURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c));
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "{\"error\":\"Could not launch cleanup star transformation "+cfg.cleanupURI+" for the star data type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
						}
					}
					else {
						// refresh the state as "CONFIGURATION_OK"
						c.setState("CONFIGURATION_OK");
						c.setStateMessage(null);
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
					
				}
				else {
					c.setState("RUN_ERROR");
					c.setStateMessage("Invalid star run action - "+action);
					GalaxyHelper.refreshGalaxy(geFactory, c);
					return "{\"error\":\"Invalid star run action - "+action+"\"}";
				}
				
			} // Star
			
			if (c instanceof lv.lumii.datagalaxies.mm.StellarWind) {
				if ("EmitStellarWind".equals(action)) {
					
					// skip, if the corresponding source stars are not in the RUN_OK state
					for (lv.lumii.datagalaxies.mm.Star s : ((lv.lumii.datagalaxies.mm.StellarWind) c).getSource()) {
						if (!"RUN_OK".equals(s.getState())) {
							if (!"CONFIGURATION_ERROR".equals(c.getState()) && !"CONFIGURATION_UNKNOWN".equals(c.getState()))
								c.setState("CONFIGURATION_OK");							
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "{}";							
						}
					}
					
					// emitting stellar wind...
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(GalaxyHelper.getGalacticTypeName(c));
					
					if (cfg.emissionURI!=null) {
						
						c.setState("RUNNING");
						c.setStateMessage(null);
						// keep the state as "RUNNING"
						
						lv.lumii.datagalaxies.mm.EmitStellarWind cmd_ = geFactory.createEmitStellarWind(); 
						cmd_.setStellarWind((StellarWind) c);
						
						lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, cmd_.getRAAPIReference(), false);
						cmd.setUri(cfg.emissionURI);
						
						///!!!!
						boolean ok = cmd.submit();
						cmd_.delete();
						
						if (ok) {
							// the transformation must refresh the state by its own;

							smartRefreshGalaxy(raapi, project_id, c.getRAAPIReference());
							
							return "{}"; 
						}
						else {
							c.setState("RUN_ERROR");
							c.setStateMessage("Could not launch stellar wind emission transformation "+cfg.emissionURI+" for the stellar wind type "+GalaxyHelper.getGalacticTypeName(c));
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "{\"error\":\"Could not launch stellar wind emission transformation "+cfg.emissionURI+" for the stellar wind type "+GalaxyHelper.getGalacticTypeName(c)+"\"}";
						}
					}
					else {
						c.setState("RUN_ERROR"); // null stellar winds are not allowed
						c.setStateMessage("Stellar wind emission transformation has not been specified in `"+GalaxyHelper.getGalacticTypeName(c)+".properties'");
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
					
				}
				else {
					c.setState("RUN_ERROR");
					c.setStateMessage("Invalid stellar wind run action - "+action);
					GalaxyHelper.refreshGalaxy(geFactory, c);
					return "{\"error\":\"Invalid stellar wind run action - "+action+"\"}";
				}
			} // StellarWind
			
			if (c instanceof lv.lumii.datagalaxies.mm.Planet) {
				if ("VisualizePlanet".equals(action)) {

					GalaxyHelper.closeComponentFrame(eeFactory, c);
					
					// skip, if the corresponding star is not in the RUN_OK state
					if ( (!((lv.lumii.datagalaxies.mm.Planet) c).getStar().isEmpty())
					  && (!"RUN_OK".equals(((lv.lumii.datagalaxies.mm.Planet) c).getStar().get(0).getState())) ) {
						if (!"CONFIGURATION_ERROR".equals(c.getState()) && !"CONFIGURATION_UNKNOWN".equals(c.getState()))
							c.setState("CONFIGURATION_OK");
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{}";
					}
					
					GalacticTypes.GalacticTypeConfiguration cfg = GalacticTypes.getTypeConfiguration(GalaxyHelper.getGalacticTypeName(c));
					
					String URI;
					
					if (lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory).getActiveRunConfiguration().isEmpty())
						URI = cfg.designerVisualizationURI;
					else
						URI = cfg.endUserVisualizationURI;
					
					if (URI!=null) {
						lv.lumii.datagalaxies.mm.VisualizePlanet cmd_ = geFactory.createVisualizePlanet(); 
						cmd_.setPlanet((lv.lumii.datagalaxies.mm.Planet) c);
						cmd_.setFrameLocation(frameLocation);
						
						lv.lumii.datagalaxies.mm.LaunchTransformationCommand cmd = (lv.lumii.datagalaxies.mm.LaunchTransformationCommand)geFactory.findOrCreateRAAPIReferenceWrapper(lv.lumii.datagalaxies.mm.LaunchTransformationCommand.class, cmd_.getRAAPIReference(), false);
						cmd.setUri(URI);
						
						boolean ok = cmd.submit();
						cmd_.delete();
						
						if (ok) {
							c.setState("RUNNING");
							c.setStateMessage(null);
							// the transformation must refresh the state by its own
							return "{}";
						}
						else {
							c.setState("RUN_ERROR");
							GalaxyHelper.refreshGalaxy(geFactory, c);
							return "{\"error\":\"Run error\"}";
						}
					}
					else {
						
						if ("NEW".equals( c.getState()) ) {
							GalaxyHelper.deleteGalaxyComponent(c);
							GalaxyHelper.refreshGalaxy(geFactory, null);
							return "{}";
						}
						
						c.setState("RUN_ERROR");
						c.setStateMessage("No visualization for the given planet was found.");
						GalaxyHelper.refreshGalaxy(geFactory, c);
						return "{\"error\":\"No visualization for the given planet was found.\"}";
					}
					
				}
				else {
					c.setState("RUN_ERROR");
					c.setStateMessage("Invalid planet run action - "+action);
					GalaxyHelper.refreshGalaxy(geFactory, c);
					return "{\"error\":\"Invalid planet run action - "+action+"\"}";
				}
			} // Planet
			
			c.setState("RUN_ERROR");
			c.setStateMessage("Attempt to run an invalid galactic type.");
			GalaxyHelper.refreshGalaxy(geFactory, c);
			return "{\"error\":\"Attempt to run an invalid galactic type.\"}";
		}
		finally {
			eeFactory.unsetRAAPI();
			geFactory.unsetRAAPI();
		}

	}
	
	
	public static void smartRefreshGalaxy(IWebMemory raapi, String project_id, long rObject) {
		boolean idle = API.webCaller.getQueueSize(project_id)<=1; // the current webcall has to be counted as 1
		if (idle) {
			lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
			
			lv.lumii.datagalaxies.mm.GalaxyComponent c = null;
			try {
				geFactory.setRAAPI(raapi, "", true);
				
				for (lv.lumii.datagalaxies.mm.GalaxyComponent cc : lv.lumii.datagalaxies.mm.GalaxyComponent.allObjects(geFactory)) {
					if (rObject == cc.getRAAPIReference()) {
						c = cc;
						break;
					}
				}

			} catch (Throwable e) {
				return; // hmmm... that's bad! we won't refresh the galaxy...
			}
			if (c==null)
				return;
			
			System.out.println("state is "+c.getState());
			if ("RUNNING".equals(c.getState())) {
				c.setState("RUN_ERROR");
				c.setStateMessage("Unhandled run error.");
				GalaxyHelper.refreshGalaxy(geFactory, c);
			}
			
		}
		else {
			WebCallSeed seed2 = new WebCallSeed();
			seed2.actionName = "SmartRefreshGalaxy";
			seed2.project_id = project_id;
			seed2.webmemArgument = rObject;
			seed2.callingConventions = IWebCaller.CallingConventions.WEBMEMCALL;
			API.webCaller.enqueue(seed2);
		}
	}
	
	
}
