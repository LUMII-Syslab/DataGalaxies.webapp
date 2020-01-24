package lv.lumii.datagalaxies;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import lv.lumii.tda.raapi.IRepository;
import lv.lumii.tda.raapi.RAAPI;
import lv.lumii.tda.util.DOS;


public class GalacticTypes {
	
	private static final boolean DEBUG = false;
	
	public final static String WEB_ROOT_DIR = DataGalaxiesApp.APP_DIR + File.separator + "web-root";
	public final static String WEB_ROOT_GALACTIC_TYPES_DIR = DataGalaxiesApp.APP_DIR + File.separator + "web-root" + File.separator + "galactictypes";
	public final static String GALACTIC_TYPES_SRC_DIR = DataGalaxiesApp.APP_DIR + File.separator + "src"+ File.separator+"galactictypes";
	public final static String GALACTIC_TYPES_BIN_DIR = DataGalaxiesApp.APP_DIR + File.separator + "bin"+ File.separator+"galactictypes";
	
	/**
	 * A dummy repository, where the hierarchy of galactic types is stored.
	 */
	private static IRepository typesRepo = null;
	
	public static class GalacticTypeConfiguration {
		// for all galactic types...
//		Set<String> parentTypes = null;
//		Set<String> childrenTypes = null;
		boolean isHidden = false;
		String metamodelFileName = null;
		
		String designerConfigurationURI = null;
		String endUserConfigurationURI = null;
		String executeCommand = null;
		Process executingProcess = null;
		
		// for star data types...
		public String initializationURI = null;
		public String finalizationURI = null;
		public String cleanupURI = null;
		// for stellar winds...
		public String emissionURI = null;
		ArrayList<ArrayList<String>> allowedSourceTypeSets = null;
		String allowedTargetTypes = null;
		// for planets...
		String designerVisualizationURI = null;
		String endUserVisualizationURI = null;
		ArrayList<String> allowedStarDataTypes = null;
		// for planets and stellar winds
		ArrayList<String> knownCrossFilterTypes = null;
		
		// for generating CSS style...
		String endUserConfigurationStyle = "width:400px;height:300px;";
		String endUserVisualizationStyle = "width:400px;height:300px;";
	}
	
	private static HashMap<String, GalacticTypeConfiguration> galacticTypeNameToConfiguration =
			new HashMap<String, GalacticTypeConfiguration>();
//	private static ArrayList<String> installedGalacticTypeNames =
//			new ArrayList<String>();
/*	private static ArrayList<String> installedFinalStellarWinds =
			new ArrayList<String>();
	private static ArrayList<String> installedFinalPlanets =
			new ArrayList<String>();
	
	public static Set<String> getInstalledGalacticTypeNames()
	{
		return new HashSet<String>(installedGalacticTypeNames);
		//return installedGalacticTypeNames;
	}
	
	public static Set<String> getInstalledFinalStellarWinds()
	{
		return new HashSet<String>(installedFinalStellarWinds);
		//return installedStellarWinds;
	}

	public static Set<String> getInstalledFinalPlanets()
	{
		return new HashSet<String>(installedFinalPlanets);
		//return installedStellarWinds;
	}*/

	private static Set<String> getInstalledParentTypes(String type) // returns all types, including "commercial" and hidden
	{
		long r = typesRepo.findClass(type);
		Set<String> retVal = new HashSet<String>();
		long it = typesRepo.getIteratorForDirectSuperClasses(r);
		typesRepo.freeReference(r);
		r = typesRepo.resolveIteratorFirst(it);
		while (r!=0) {
			String s = typesRepo.getClassName(r);
			if (s!=null)
				retVal.add(s);
			typesRepo.freeReference(r);
			r = typesRepo.resolveIteratorNext(it);
		}
		typesRepo.freeIterator(it);

		return retVal;
	}

	/**
	 * Returns the list of parent types for the given galactic type. The types returned do not include
	 * hidden and "non-purchased" types.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) galactic types
	 * @param type
	 * @return the list of parent types
	 */
	public static Set<String> getAvailableParentTypes(String email, String type)
	{
		Set<String> retVal = getInstalledParentTypes(type);
		for (String s : retVal) {
			GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(s);
			if ((cfg == null) || (cfg.isHidden))
				retVal.remove(s);
		}
		return retVal;
	}
	
	private static Set<String> getInstalledChildTypes(String type) // returns all types, including "commercial" and hidden
	{
		long r = typesRepo.findClass(type);
		Set<String> retVal = new HashSet<String>();
		long it = typesRepo.getIteratorForDirectSubClasses(r);
		typesRepo.freeReference(r);
		r = typesRepo.resolveIteratorFirst(it);
		while (r!=0) {
			String s = typesRepo.getClassName(r);
			if (s!=null)
				retVal.add(s);
			typesRepo.freeReference(r);
			r = typesRepo.resolveIteratorNext(it);
		}
		typesRepo.freeIterator(it);

		return retVal;
	}

	/**
	 * Returns the list of child types for the given galactic type. The types returned do not include
	 * hidden and "non-purchased" types.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) galactic types
	 * @param type
	 * @return the list of child types
	 */
	public static Set<String> getAvailableChildTypes(String email, String type)
	{
		Set<String> retVal = getInstalledChildTypes(type);
		for (String s : retVal) {
			GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(s);
			if ((cfg == null) || (cfg.isHidden))
				retVal.remove(s);
		}
		return retVal;
	}
	
	private static void getFinalSubclasses(long r, Set<String> types)
	{
		long it = typesRepo.getIteratorForDirectSubClasses(r);
		long rSub = typesRepo.resolveIteratorFirst(it);
		if (rSub == 0) {
			types.add(typesRepo.getClassName(r));
		}
		else {
			while (rSub!=0) {
				getFinalSubclasses(rSub, types);
				typesRepo.freeReference(rSub);
				rSub = typesRepo.resolveIteratorNext(it);
			}
		}
		typesRepo.freeIterator(it);
	}
	
	/**
	 * Returns the list of available final stellar wind types (types without subclasses). The types returned do not include
	 * hidden and "non-purchased" types.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) stellar winds
	 * @return the list of child types
	 */
	public static Set<String> getAvailableFinalStellarWinds(String email)
	{
		Set<String> retVal = new HashSet<String>();
		long r = typesRepo.findClass("StellarWind");
		if (r!=0) {
			getFinalSubclasses(r, retVal);
			typesRepo.freeReference(r);
		}
		return retVal;
	}
	
	/**
	 * Returns the list of available final planet types (types without subclasses). The types returned do not include
	 * hidden and "non-purchased" types.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) planets
	 * @return the list of child types
	 */
	public static Set<String> getAvailableFinalPlanets(String email)
	{
		Set<String> retVal = new HashSet<String>();
		long r = typesRepo.findClass("Planet");
		if (r!=0) {
			getFinalSubclasses(r, retVal);
			typesRepo.freeReference(r);
		}
		return retVal;		
	}
	
	/**
	 * Returns whether the given type is available for the given user.
	 * The type is not available if it is not present, it is hidden, or it is not "purchased".
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) planets
	 * @return whether the given type is available
	 */
	public static boolean isTypeAvailable(String email, String type)
	{
		long r = typesRepo.findClass(type);
		typesRepo.freeReference(r);
		return (r!=0);
	}
	
	public static GalacticTypeConfiguration getTypeConfiguration(String type)
	{
		GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(type);
		if (cfg == null)
			return null;
		
		if (cfg.executeCommand!=null) {
			
			if (cfg.executingProcess!=null) {
				// process has been launched earlier...
				if (!cfg.executingProcess.isAlive()) {
					cfg.executingProcess.destroy();
					cfg.executingProcess = null;
				}
			}
			
			if (cfg.executingProcess == null) {
				// command specified, but the process is not running; we will try to run it...
				// command does not contain any values to substitute any more (they must have been replaced when reading configuration)
				try {
					cfg.executingProcess = Runtime.getRuntime().exec(cfg.executeCommand);
					if (DEBUG) {
						System.out.println("Command launched for galactic type "+type+":");
						System.out.println("  "+cfg.executeCommand);
					}
				} catch (IOException e) {
					System.err.println("Error launching command for galactic type "+type+":");
					System.err.println("  "+cfg.executeCommand);
					cfg.executeCommand = null;
				}
			}
		}
		
		return cfg;
	}
	
	private static boolean fileExistsEx(String dir, String prefix, String postfix)
	{
		try {
			for (File f : new File(dir).listFiles()) {
				String s = f.getName();
				if (s.startsWith(prefix) && s.endsWith(postfix))
					return true;
			}
		}
		catch (Throwable t) {
			return false;
		}
		return false;
	}	
	
	private static String getEndUserStyle(String dir, String prefix, String postfix, String defaultValue)
	{
		File f = new File(dir+File.separator+prefix+postfix);
		
		if (f.exists())
			return defaultValue;
	
		for (String s : new File(dir).list()) {
			if (s.startsWith(prefix+".")) {
				String dimensions = s.substring(s.indexOf('.')+1, s.lastIndexOf('.'));
				if (dimensions.length()>0) {
					int i = dimensions.indexOf("*");
					if (i<0)
						i = dimensions.indexOf("x");
					if (i<0)
						return defaultValue;
					
					String w = dimensions.substring(0, i);
					String h = dimensions.substring(i+1);
					return "width:"+w+";height:"+h+";"; 
				}
				break;
			}
		}
		
		return defaultValue;
	}
	
	private static void scanGalacticTypes()
	{
		File[] dirs = new File(GALACTIC_TYPES_SRC_DIR).listFiles();
		if (dirs == null) {
			System.err.println("No galactic type found in "+GALACTIC_TYPES_SRC_DIR);
			return;
		}
		
		for (File d : dirs) {
			if (d.isDirectory())
				scanGalacticType(d.getName());
		}
		
		
		// TODO: final planets and stellar winds
/*			if ("Planet".equals(rootTypeName))
				installedFinalPlanets.add(dir.getName());
			
			if ("StellarWind".equals(rootTypeName))
				installedFinalStellarWinds.add(dir.getName());*/			
		
	}
	
	private static String replaceAll(String s, String what, String toWhat) {
		for (;;) {
			int i = s.indexOf(what);
			if (i<0)
				return s;
			s = s.substring(0, i) + toWhat + s.substring(i+what.length());
		}
	}
	
	private static String enquote(String s)
	{
		if (s.contains(" ") && !s.startsWith("\"") && !s.endsWith("\""))
			return "\""+s+"\"";
		else
			return s;
	}
	
	private static void scanGalacticType(String typeName)
	{
		if (galacticTypeNameToConfiguration.containsKey(typeName)) {
			// this galactic type is already scanned...
			return;
		}
		
		// processing this dir...
		// dir.getName() is the name of the current galactic type
		
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(GALACTIC_TYPES_SRC_DIR + File.separator + typeName + File.separator
					+ typeName+".properties"));			
		} catch (Throwable t) {
		}

		boolean javaClassExists = new File(GALACTIC_TYPES_BIN_DIR + File.separator + typeName+File.separator+typeName+".class").exists();
		if (DEBUG) System.out.println("clazz "+GALACTIC_TYPES_BIN_DIR + File.separator + typeName+File.separator+typeName+".class found "+javaClassExists);
		String javaClassName = typeName.replace(" ", "");
		
		// creating galactic type configuration...
		GalacticTypeConfiguration galacticType = new GalacticTypeConfiguration();		
		galacticTypeNameToConfiguration.put(typeName, galacticType);

		// setting common attributes...
		galacticType.isHidden = (p.getProperty("isHidden")!=null) && p.getProperty("isHidden").equalsIgnoreCase("true");
		
		galacticType.metamodelFileName = GALACTIC_TYPES_SRC_DIR + File.separator + typeName+File.separator+typeName+".ecore";
		if (!new File(galacticType.metamodelFileName).exists()) {
			galacticType.metamodelFileName = GALACTIC_TYPES_SRC_DIR + File.separator + typeName+File.separator+typeName+".mmd";
			if (!new File(galacticType.metamodelFileName).exists()) {
				galacticType.metamodelFileName = null;
			}
		}
		
		
		// adding metamodel (if exists)...
		if (galacticType.metamodelFileName != null) {
			try {
				lv.lumii.tda.kernel.mmdparser.MetamodelInserter.insertMetamodel(new File(galacticType.metamodelFileName).toURI().toURL(), typesRepo);
			} catch (MalformedURLException e) {
			}					
		}				
		
		if (DEBUG) {
			System.out.println("Parent types for "+typeName+" are: ");
			for (String s : getInstalledParentTypes(typeName))
				System.out.println(" "+s);
			System.out.println();
		}
		
		// ensuring that all ascendants are also scanned, since we will need to determine
		// whether the current type is a descendant of StarData, Planet, CrossFilter, or StellarWind
		for (String s : getInstalledParentTypes(typeName)) {
			scanGalacticType(s);
		}
		
		
		galacticType.designerConfigurationURI = p.getProperty("designerConfigurationURI");		
		if ((galacticType.designerConfigurationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerConfiguration", ".html")) 
			galacticType.designerConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureViaHTML";
		if ((galacticType.designerConfigurationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserConfiguration", ".html"))
			galacticType.designerConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureViaHTMLEndUserFile";		
		if ((galacticType.designerConfigurationURI==null) && (javaClassExists))
			galacticType.designerConfigurationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#designerConfiguration";
		
		galacticType.endUserConfigurationURI = p.getProperty("endUserConfigurationURI");
		if ((galacticType.endUserConfigurationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserConfiguration", ".html")) {
			galacticType.endUserConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureViaHTML";
			galacticType.endUserConfigurationStyle = GalacticTypes.getEndUserStyle(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserConfiguration", ".html", galacticType.endUserConfigurationStyle);
		}
		if ((galacticType.endUserConfigurationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerConfiguration", ".html")) {
			galacticType.endUserConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureViaHTMLDesignerFile";		
			galacticType.endUserConfigurationStyle = GalacticTypes.getEndUserStyle(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerConfiguration", ".html", galacticType.endUserConfigurationStyle);
		}
		if ((galacticType.endUserConfigurationURI==null) && (javaClassExists))
			galacticType.endUserConfigurationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#endUserConfiguration";
		
		
		if (galacticType.designerConfigurationURI==null) 
			galacticType.designerConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureNothing";
		if (galacticType.endUserConfigurationURI==null)
			galacticType.endUserConfigurationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#configureNothing";
		
		if (DEBUG) {
			System.out.println("CONF FOR "+typeName+" are ");
			System.out.println("  1: "+galacticType.designerConfigurationURI);
			System.out.println("  2: "+galacticType.endUserConfigurationURI);
		}
		
		
		// setting specific attributes...
		long r = typesRepo.findClass(typeName);
		if (r==0) {
			System.err.println("Error in scanning galactic type "+typeName+": the corresponding class is not added to the type repository.");
			return;
		}
		
		galacticType.executeCommand = p.getProperty("executeCommand");
		if (galacticType.executeCommand!=null) {
			if ('\\'!=File.separatorChar)
				galacticType.executeCommand = galacticType.executeCommand.replace('\\', File.separatorChar);
			//if ('/'!=File.separatorChar)
			//galacticType.executeCommand = galacticType.executeCommand.replace('/', File.separatorChar);
			if (';'!=File.pathSeparatorChar)
				galacticType.executeCommand = galacticType.executeCommand.replace(';', File.pathSeparatorChar);
			//if (':'!=File.pathSeparatorChar)
			//	galacticType.executeCommand = galacticType.executeCommand.replace(':', File.pathSeparatorChar);
			galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$TYPE_DIR", enquote(GALACTIC_TYPES_SRC_DIR + File.separator + typeName));
			galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$TYPE_WEB_DIR", enquote(WEB_ROOT_GALACTIC_TYPES_DIR + File.separator + typeName));
			
			String javaExe = System.getProperty("java.home")+File.separator+"bin"+File.separator +"java";
			if (System.getProperty("os.name").contains("Windows"))
				javaExe+=".exe";
			
			try {
				javaExe = DOS.getMSDOSName(javaExe);
			} catch (IOException | InterruptedException e) {
			}

			galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$JAVA", enquote(javaExe));
			
			if (System.getProperty("os.name").contains("Windows")) {
				galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$SHELL", enquote(System.getenv("COMSPEC"))+" /c");
				galacticType.executeCommand = 
						replaceAll(galacticType.executeCommand, "$TERMINAL", enquote(DataGalaxiesApp.APP_DIR+File.separator+"ConEmu"+File.separator+"ConEmu.exe")+" /cmd");
						//replaceAll(galacticType.executeCommand, "$TERMINAL", enquote(System.getenv("COMSPEC"))+" /c start/w");
						//replaceAll(galacticType.executeCommand, "$TERMINAL", enquote(System.getenv("SystemRoot")+"\\system32\\WindowsPowerShell\\v1.0\\powershell.exe")+" -command");				
			}
			else {
				galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$SHELL", enquote(System.getenv("SHELL")));				
				galacticType.executeCommand = 
						replaceAll(galacticType.executeCommand, "$TERMINAL", enquote(System.getenv("SHELL")));				
			}
			
			galacticType.executeCommand = 
					replaceAll(galacticType.executeCommand, "$WEB_ROOT",
							enquote(DataGalaxiesApp.APP_DIR+File.separator + "web-root"));
			
			if (DEBUG)
				System.out.println("executeCommand for type "+typeName+" is "+galacticType.executeCommand);
		}
		
		long rRoot = typesRepo.findClass("StarData");
		if (typesRepo.isDerivedClass(r, rRoot)) {
			galacticType.initializationURI = p.getProperty("initializationURI");
			if ((galacticType.initializationURI==null) && (javaClassExists))
				galacticType.initializationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#initialization";
			
			galacticType.finalizationURI = p.getProperty("finalizationURI");
			if ((galacticType.finalizationURI==null) && (javaClassExists))
				galacticType.finalizationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#finalization";
			
			galacticType.cleanupURI = p.getProperty("cleanupURI");
			if ((galacticType.cleanupURI==null) && (javaClassExists))
				galacticType.cleanupURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#cleanup";
		}
		typesRepo.freeReference(rRoot);
		
		rRoot = typesRepo.findClass("Planet");
		
		if (typesRepo.isDerivedClass(r, rRoot)) {			
			galacticType.designerVisualizationURI = p.getProperty("designerVisualizationURI");
			if ((galacticType.designerVisualizationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerVisualization", ".html"))
				galacticType.designerVisualizationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#visualizeViaHTML";		
			if ((galacticType.designerVisualizationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserVisualization", ".html"))
				galacticType.designerVisualizationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#visualizeViaHTMLEndUserFile";		
			if ((galacticType.designerVisualizationURI==null) && (javaClassExists))
				galacticType.designerVisualizationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#designerVisualization";
			galacticType.endUserVisualizationURI = p.getProperty("endUserVisualizationURI");
			if ((galacticType.endUserVisualizationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserVisualization", ".html")) {
				galacticType.endUserVisualizationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#visualizeViaHTML";		
				galacticType.endUserVisualizationStyle = GalacticTypes.getEndUserStyle(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "endUserVisualization", ".html", galacticType.endUserVisualizationStyle);
			}
			if ((galacticType.endUserVisualizationURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerVisualization", ".html")) {
				galacticType.endUserVisualizationURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#visualizeViaHTMLDesignerFile";
				galacticType.endUserVisualizationStyle = GalacticTypes.getEndUserStyle(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "designerVisualization", ".html", galacticType.endUserVisualizationStyle);
			}
			if ((galacticType.endUserVisualizationURI==null) && (javaClassExists))
				galacticType.endUserVisualizationURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#endUserVisualization";
						
			//!!!
			String srcTypes = p.getProperty("allowedStarDataTypes");
			galacticType.allowedStarDataTypes = new ArrayList<String>();
			
			if (srcTypes != null) {
				StringTokenizer tknz = new StringTokenizer(srcTypes, ",");
				while (tknz.hasMoreTokens()) {
					String name = tknz.nextToken().trim();				
					galacticType.allowedStarDataTypes.add(name);
				}
			}
			
			if (galacticType.allowedStarDataTypes.isEmpty()) {
				galacticType.allowedStarDataTypes.add("StarData");
			}
			
			galacticType.knownCrossFilterTypes = new ArrayList<String>();
			String knownFilters = p.getProperty("knownCrossFilterTypes");
			if (knownFilters != null) {
				StringTokenizer tknz = new StringTokenizer(knownFilters, ",");
				while (tknz.hasMoreTokens()) {
					String name = tknz.nextToken().trim();				
					galacticType.knownCrossFilterTypes.add(name);
				}
			}
			
		}
		typesRepo.freeReference(rRoot);
		
		rRoot = typesRepo.findClass("StellarWind");
		
		if (typesRepo.isDerivedClass(r, rRoot)) {
			galacticType.emissionURI = p.getProperty("emissionURI");
			if ((galacticType.emissionURI==null) && fileExistsEx(WEB_ROOT_GALACTIC_TYPES_DIR+File.separator+typeName, "emission", ".html"))
				galacticType.emissionURI = "staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#emitViaHTML";		
			if ((galacticType.emissionURI==null) && (javaClassExists))
				galacticType.emissionURI = "staticjava:galactictypes."+javaClassName+"."+javaClassName+"#emission";
			
			String sets = p.getProperty("allowedSourceTypeSets");
			if (sets != null) {
				StringTokenizer tknz = new StringTokenizer(sets, "}");
				while (tknz.hasMoreTokens()) {
					ArrayList<String> currSet = new ArrayList<String>();
					
					String set = tknz.nextToken().trim();
					while (set.startsWith(",") || set.startsWith("{"))
						set = set.substring(1).trim();
					while (set.endsWith(","))
						set = set.substring(0, set.length()-1).trim();
					
					StringTokenizer tknz2 = new StringTokenizer(set, ",");
					while (tknz2.hasMoreTokens()) {
						String name = tknz2.nextToken().trim();
						Integer minCardinality = null;
						Integer maxCardinality = null;
						if (name.endsWith("]")) {
							int i = name.lastIndexOf('[');
							if (i>=0) {
								String cardinality = name.substring(i+1, name.length()-1);
								name = name.substring(0, i);
								if (cardinality.contains("..")) {
									try {
										minCardinality = Integer.parseInt(cardinality.substring(0, cardinality.indexOf("..")));
										maxCardinality = Integer.parseInt(cardinality.substring(cardinality.indexOf("..")+2));
									}
									catch (Throwable thrw) {
										
									}									
								}
								else {
									try {
										Integer j = Integer.parseInt(cardinality);
										minCardinality = j;
										maxCardinality = j;
									}
									catch (Throwable thrw) {
										
									}
								}
							}
							else
								name = name.substring(0, name.length()-1).trim();
						}
						
						currSet.add(name);
						/*if (minCardinality!=null)
							refObj.setMinCardinality(minCardinality);
						if (maxCardinality!=null)
							refObj.setMaxCardinality(maxCardinality);*/
						
					}
					
					if (!currSet.isEmpty()) {
						if (galacticType.allowedSourceTypeSets == null)
							galacticType.allowedSourceTypeSets = new ArrayList<ArrayList<String>>();
						
						galacticType.allowedSourceTypeSets.add(currSet);
					}
				}
			}
			if (galacticType.allowedSourceTypeSets == null) {
				galacticType.allowedSourceTypeSets = new ArrayList<ArrayList<String>>();
				ArrayList<String> a = new ArrayList<String>();
				a.add("StarData");
				galacticType.allowedSourceTypeSets.add(a);
			}
			
			galacticType.allowedTargetTypes = p.getProperty("allowedTargetTypes");
			if (galacticType.allowedTargetTypes == null)
				galacticType.allowedTargetTypes = "StarData";

			galacticType.knownCrossFilterTypes = new ArrayList<String>();
			String knownFilters = p.getProperty("knownCrossFilterTypes");
			if (knownFilters != null) {
				StringTokenizer tknz = new StringTokenizer(knownFilters, ",");
				while (tknz.hasMoreTokens()) {
					String name = tknz.nextToken().trim();				
					galacticType.knownCrossFilterTypes.add(name);
				}
			}
			
		}
		typesRepo.freeReference(rRoot);
		
		rRoot = typesRepo.findClass("CrossFilter");
		
		if (typesRepo.isDerivedClass(r, rRoot)) {
		}
		typesRepo.freeReference(rRoot);
		
		typesRepo.freeReference(r);
		
		
	}
	
	

	/**
	 * Inserts the given galactic type and its ancestors into the repository. Optionally, includes also all
	 * available descendants of the given types.  
	 * 
	 * @param login the user login to be used (in the future) to determine available (purchased) galactic types
	 * @param raapi the target repository
	 * @param typeName the name of the galactic type to insert
	 * @param addDescendants whether the descendants have to be inserted, too 
	 * @return whether the operation succeeded
	 */
	public static boolean putGalacticTypeIntoMetamodel(String login, RAAPI raapi, String typeName, boolean addDescendants)
	{
		boolean putAgain = false;
		long r = raapi.findClass(typeName);
		if (r!=0) {
			//raapi.freeReference(r);
			//return true;
			putAgain = true;
		}
		
		boolean ok = true;
		
		r = raapi.findClass(typeName);
		if (r==0) {
			r = raapi.createClass(typeName);
		}
		raapi.freeReference(r);
		
		if (r==0)
			ok = false;
		
		// first, adding ancestors...
		for (String parentTypeName : getInstalledParentTypes(typeName)) {
			if (!putGalacticTypeIntoMetamodel(login, raapi, parentTypeName, false))
				ok = false;
			long rParent = raapi.findClass(parentTypeName);
			if ((rParent!=0) && (!raapi.isDerivedClass(r, rParent))) {
				if (!raapi.createGeneralization(r, rParent))
					ok = false;
			}
			raapi.freeReference(rParent);
		}
		
		// now, adding the type itself...
		
		GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(typeName);
		if (cfg == null)
			return false;
		
		if (cfg.metamodelFileName != null) {
			try {
				lv.lumii.tda.kernel.mmdparser.MetamodelInserter.insertMetamodel(new File(cfg.metamodelFileName).toURI().toURL(), raapi);
			} catch (MalformedURLException e) {
			}					
		}

				
		
		// adding descendants...
		if ((r!=0) && addDescendants) {
			for (String childTypeName : getInstalledChildTypes(typeName)) {
				if (!putGalacticTypeIntoMetamodel(login, raapi, childTypeName, true))
					ok = false;
				long rChild = raapi.findClass(childTypeName);
				if ((rChild!=0) && (!raapi.isDerivedClass(rChild, r))) {
					if (!raapi.createGeneralization(rChild, r))
						ok = false;
				}
				raapi.freeReference(rChild);
			}
			
		}
		
		//return ok;
		return ok || putAgain;
	}
	
	
	/**
	 * Use the maximum matching algorithm to find out whether the given stellar wind is possible
	 * from the given set of source stars...
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) galactic types
	 * @param stellarWindTypeName the stellar wind type name
	 * @param sourceStarDataTypes a list of data types of selected stars, from which a stellar wind of the given type is going to be emitted 
	 * @return whether a stellar wind of the given type is possible from the given set of star data types
	 */
	public static boolean stellarWindPossible(String email, String stellarWindTypeName, String sourceStarDataTypes[])
	{
		GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(stellarWindTypeName);
		if ((cfg == null) || (cfg.allowedSourceTypeSets==null))
			return false;
		
		
		for (ArrayList<String> types : cfg.allowedSourceTypeSets) {
			
			if (types.size() != sourceStarDataTypes.length)
				continue;
			
			if (DEBUG) System.out.println("Maximum matching for "+stellarWindTypeName);
			
			int n = types.size();
			// constructing graph for the maximum matching problem...
			LinkedList<Integer> graph[] = new LinkedList[n];
			for (int k=0; k<n; k++)
				graph[k] = new LinkedList<Integer>();
			
			for (int i=0; i<types.size(); i++)
				for (int j=0; j<sourceStarDataTypes.length; j++) {									
					String allowedType = types.get(i);
					String possibleActualTypes = sourceStarDataTypes[j];
					boolean ok = true;
					
					StringTokenizer tkn = new StringTokenizer(possibleActualTypes, ",");
					while (tkn.hasMoreTokens()) {
						String actualType = tkn.nextToken();
						
						long r1 = typesRepo.findClass(allowedType);
						long r2 = typesRepo.findClass(actualType);
						boolean suitable = (r1==r2) || typesRepo.isDerivedClass(r2, r1);
						typesRepo.freeReference(r1);
						typesRepo.freeReference(r2);
						
						if (!suitable) {
							ok = false;
							break;
						}
					}
					
					if (ok) {
						graph[i].add(j);
						if (DEBUG) System.out.println("  "+possibleActualTypes+" -> "+allowedType);
					}
				}
			// launching maximum matching...
			
			if (maxMatching(graph, n)==n) {
				if (DEBUG) System.out.println("  matching OK");
				return true;
			}
			else {
				if (DEBUG) System.out.println("  matching failed");				
			}
		}
		
		return false;
	}

	/**
	 * Returns the list of installed galactic types that are available for the given user.
	 * Hidden types and "commercial" types that have not been "purchased" are excluded.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) galactic types
	 * @return the list of names of galactic types available to the given user
	 */
	/*public static Set<String> getAvailableGalacticTypes(String email)
	{
		Set<String> retVal = new HashSet<String>( galacticTypeNameToConfiguration.keySet() );
		for (String s : retVal) {
			GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(s);
			if ((cfg == null) || (cfg.isHidden))
				retVal.remove(s);
		}
		return retVal;
	}*/
	
	/**
	 * Checks whether the given type is a final type (i.e., does not have subclasses).
	 * @param typeName
	 * @return whether the given type is a final type (i.e., does not have subclasses).
	 */
	public static boolean isFinalGalacticType(String typeName)
	{
		long r = typesRepo.findClass(typeName);
		if (r==0)
			return false;
		long it = typesRepo.getIteratorForDirectSubClasses(r);
		long rr = typesRepo.resolveIteratorFirst(it);
		typesRepo.freeIterator(it);
		typesRepo.freeReference(rr);
		typesRepo.freeReference(r);

		return (rr==0);
	}
	
	/**
	 * Checks whether a planet of the given type can be attached to a star with the given star data type.
	 * @param email the user e-mail to be used (in the future) to determine available (purchased) galactic types
	 * @param planetTypeName planet type name
	 * @param starDataType the actual star data type attached to the given star
	 * @return whether a planet can be attached
	 */
	public static boolean planetCanBeAttached(String email, String planetTypeName, String starDataType)
	{
		GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(planetTypeName);
		if ((cfg == null) || (cfg.allowedStarDataTypes==null))
			return false;
				
		boolean ok = true;
		
		StringTokenizer tkn = new StringTokenizer(starDataType, ",");
		while (tkn.hasMoreTokens()) {
			String actualType = tkn.nextToken();
			
			boolean local_ok = false;
			
			for (String allowedType : cfg.allowedStarDataTypes) {
				
				long r1 = typesRepo.findClass(allowedType);
				long r2 = typesRepo.findClass(actualType);
				boolean suitable = (r1==r2) || typesRepo.isDerivedClass(r2, r1);
				typesRepo.freeReference(r1);
				typesRepo.freeReference(r2);
				
				if (suitable) {
					local_ok = true;
					break;
				}
			}
			
			if (!local_ok) {
				if (DEBUG) System.out.println("CHECKING PLANET "+planetTypeName+": "+actualType+" not passed");
				ok = false;
				break;
			}
		}
		
		return ok;
	}
	
/*	private static boolean isGalacticSubType(String subType, String superType) {
		GalacticTypeConfiguration cfg = galacticTypeNameToConfiguration.get(subType);
		if ((cfg == null) || (cfg.parentTypes.isEmpty()))
			return false;
		
		if (cfg.parentTypes.contains(superType))
			return true;
		else {
			for (String s : cfg.parentTypes)
				if (isGalacticSubType(s, superType))
					return true;
			return false;
		}
	}*/
	
	private static int maxMatching(List<Integer>[] graph, int n2) 
	{
		int n1 = graph.length;
		int[] matching = new int[n2];
		Arrays.fill(matching, -1);
		int matches = 0;
		for (int u = 0; u < n1; u++) {
			if (findPath(graph, u, matching, new boolean[n1]))
				++matches;
		}
		return matches;
	}

	private static boolean findPath(List<Integer>[] graph, int u1, int[] matching, boolean[] vis) {
		vis[u1] = true;
	    for (int v : graph[u1]) {
	    	int u2 = matching[v];
	      	if (u2 == -1 || !vis[u2] && findPath(graph, u2, matching, vis)) {
	    	  	matching[v] = u1;
	        	return true;
	      	}
	    }
	    return false;
	}
	
	
	private static boolean initialized = false;
	
	public static void initialize()
	{
		if (initialized)
			return;

		//typesRepo = new lv.lumii.tda.adapters.repository.ecore.RepositoryAdapter();//lv.lumii.tda.kernel.ManagerForAdapters.createRepositoryAdapter("ecore", null);
		typesRepo = new lv.lumii.tda.adapters.repository.ar.RepositoryAdapter();
		typesRepo.open(DataGalaxiesApp.APP_DIR+File.separator+"typesrepo");
		
		try {
		scanGalacticTypes();
		}
		catch(Throwable t) {
			t.printStackTrace(System.err);
		}
		
		initialized = true;
	}

	public static void reinitialize()
	{
		if (!initialized) {
			initialize();
			return;
		}

		initialized = false;
		if (typesRepo != null) {
			typesRepo.close();
			typesRepo = new lv.lumii.tda.adapters.repository.ar.RepositoryAdapter();
			typesRepo.open(DataGalaxiesApp.APP_DIR+File.separator+"typesrepo");
		}
		
		galacticTypeNameToConfiguration.clear();
		
		try {
			scanGalacticTypes();
		}
		catch(Throwable t) {
			t.printStackTrace(System.err);
		}
		
		initialized = true;
	}
	
	static {
		initialize();
		
		//TODO: add hook 
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            	synchronized (GalacticTypes.class) {
            		
            		for (GalacticTypeConfiguration cfg : galacticTypeNameToConfiguration.values()) {
            			if (cfg.executingProcess!=null) {
            				if (DEBUG) System.out.println("SIGINT recursively");
            				cfg.executingProcess.destroy();
                			//ProcessInterrupter.SIGINTRecursively(cfg.executingProcess);
            			}
            		}
            	
            	}
            }
        });
		 
	}


}
