package galactictypes.Filterer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.webappos.server.API;

import lv.lumii.tda.raapi.RAAPI;

public class Filterer {
	
	private static boolean DEBUG = !false;
	
	private interface IFilter {
		boolean acceptRow(CSVRecord rowValues);
	}
	
	private static class DateFilterImpl implements IFilter {
		private int columnIndex;
		private String fromDate, toDate;
		
		public DateFilterImpl(int _columnIndex, String _fromDate, String _toDate) {
			columnIndex = _columnIndex;
			fromDate = _fromDate;
			toDate = _toDate;
		}

		@Override
		public boolean acceptRow(CSVRecord rowValues) {
			if ((columnIndex < 0) || (columnIndex>=rowValues.size())) {
				if (DEBUG) {
					System.out.println("columnIndex accept: "+rowValues.size());
					for (int i = 0; i<rowValues.size(); i++) {
						System.out.println("  "+rowValues.get(i)+" ");
					}
				}					
				return true;
			}
			
			if (rowValues.get(columnIndex) == null) {
				if (DEBUG) System.out.println("rowValues[columnIndex]==null");
				return (fromDate==null) && (toDate==null);
			}
			
			if (fromDate!=null) {
				if (fromDate.compareTo(rowValues.get(columnIndex)) > 0)
					return false; // the row does not match fromDate
			}
			
			if (toDate!=null) {
				if (toDate.compareTo(rowValues.get(columnIndex)) < 0)
					return false; // the row does not match toDate
			}
			
			
			return true;
		}
		
	}

	private static class ValuesFilterImpl implements IFilter {
		private int columnIndex;
		private Set<String> values;
		
		public ValuesFilterImpl(int _columnIndex, Set<String> _values) {
			columnIndex = _columnIndex;
			values = _values;
		}

		@Override
		public boolean acceptRow(CSVRecord rowValues) {
			if ((columnIndex < 0) || (columnIndex>=rowValues.size()))
				return true;
			
			if ((values == null) || (values.isEmpty()))
				return true;
			
			if (rowValues.get(columnIndex) == null)
				return false;
			
			return values.contains(rowValues.get(columnIndex));					
		}
		
	}
	
	private static int getColumnIndex(String[] columnNames, String name) {
		if (name == null)
			return -1;
		int retVal = -1;
		try {
			retVal = Integer.parseInt(name)-1; // name may be the number starting from 1
			if ((retVal >= 0) && (retVal < columnNames.length))
				return retVal;
		}
		catch (Throwable t) {			
		}
		
		for (int i=0; i<columnNames.length; i++)
			if (name.equals(columnNames[i]))
				return i;
		
		return -1;
	}
	
	private static List<IFilter> initFilters(String[] columnNames, lv.lumii.datagalaxies.mm.StellarWind stW, Map<String,String> filterToColumnMap) {
		List<IFilter> retVal = new LinkedList<IFilter>();
		
		if (DEBUG) System.out.println("initFilter: "+columnNames.length+" columns");
		
		for (lv.lumii.datagalaxies.mm.CrossFilter cf : stW.getCrossFilter()) {
			long it = cf.getRAAPI().getIteratorForDirectObjectClasses( cf.getRAAPIReference() );
			long rClass = cf.getRAAPI().resolveIteratorFirst(it);
			cf.getRAAPI().freeIterator(it);
			String className = cf.getRAAPI().getClassName(rClass);
			if (DEBUG) System.out.println("filter class "+className);
			
			if ("Date filter".equals(className)) {
								
				int columnIndex = getColumnIndex(columnNames, filterToColumnMap.get(cf.getId()));
				if (DEBUG) System.out.println("columnIndex "+columnIndex+" "+stW.getId()+" "+filterToColumnMap.get(cf.getId()));
				if (columnIndex!=-1) {
				
					long rFromDate = cf.getRAAPI().findAttribute(rClass, "fromDate");
					long rToDate = cf.getRAAPI().findAttribute(rClass, "toDate");
					String fromDate = cf.getRAAPI().getAttributeValue(cf.getRAAPIReference(), rFromDate);
					String toDate = cf.getRAAPI().getAttributeValue(cf.getRAAPIReference(), rToDate);
					cf.getRAAPI().freeReference(rFromDate);
					cf.getRAAPI().freeReference(rToDate);
					
					
					if (DEBUG) System.out.println("DATE FILTER "+columnIndex+" "+fromDate+" "+toDate);
					IFilter f = new DateFilterImpl(columnIndex, fromDate, toDate);
					retVal.add(f);
				}
			}
			else
			if ("Subtree filter".equals(className)) {
				int columnIndex = getColumnIndex(columnNames, filterToColumnMap.get(cf.getId()));
				if (columnIndex!=-1) {
				
					long rValues = cf.getRAAPI().findAttribute(rClass, "selectedValues");
					String values = cf.getRAAPI().getAttributeValue(cf.getRAAPIReference(), rValues);
					cf.getRAAPI().freeReference(rValues);
					
					if ((values!=null)&&(!values.isEmpty())) {					
						Set<String> valuesSet = new HashSet<String>();
						for (String s : values.split(";"))
							valuesSet.add(s.trim());
						
						if (DEBUG) System.out.println("VALUES FILTER "+columnIndex+" "+values);
						IFilter f = new ValuesFilterImpl(columnIndex, valuesSet);
						retVal.add(f);
					}
				}
				
			}
			
			cf.getRAAPI().freeReference(rClass);
		}
		
		return retVal;
		
	}
	
	
	private static String replaceAll(String s, String what, String toWhat) {
		int i = s.indexOf(what);
		while (i>=0) {
			s = s.substring(0, i)+toWhat+s.substring(i+what.length());
			i = s.indexOf(what, i+what.length());
		}
		return s;
	}
	
	public static boolean emission(RAAPI raapi, String project_id, long r)
	{
		// r isTypeOf EmitStellarWind
		
		if (DEBUG) System.out.println("Filterer emission at server called");
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				System.err.println("Error while preparing filterer stellar wind: "+e.getMessage());
				return false;
			}
			
			
			lv.lumii.datagalaxies.mm.EmitStellarWind cmd = (lv.lumii.datagalaxies.mm.EmitStellarWind)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
		
			lv.lumii.datagalaxies.mm.StellarWind stW = cmd.getStellarWind().get(0);
			lv.lumii.datagalaxies.mm.StarData src = stW.getSource().get(0).getStarData().get(0);
			lv.lumii.datagalaxies.mm.Star tgtStar = stW.getTarget().get(0);
			lv.lumii.datagalaxies.mm.StarData tgt = stW.getTarget().get(0).getStarData().get(0);
 			
			long rFile = src.getRAAPI().findClass("File");
			long rFileNameAttr = src.getRAAPI().findAttribute(rFile, "fileName");
			
			String sourceFileName = src.getRAAPI().getAttributeValue(src.getRAAPIReference(), rFileNameAttr);
			String targetFileName = src.getRAAPI().getAttributeValue(tgt.getRAAPIReference(), rFileNameAttr);
			
			src.getRAAPI().freeReference(rFile);
			src.getRAAPI().freeReference(rFileNameAttr);
			
			if (sourceFileName == null) {
				stW.setStateMessage("Source file name not specified");
				stW.setState("RUN_ERROR");
	 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
	 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
	 			rc.setModifiedComponent(stW);
	 			rc.submit();
	 			return true;
			}
			if (targetFileName == null) {
				stW.setStateMessage("Target file name not specified");
				stW.setState("RUN_ERROR");
	 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
	 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
	 			rc.setModifiedComponent(stW);
	 			rc.submit();
	 			return true;
			}
			
			String projectDir = API.dataMemory.getProjectFolder(project_id);
			
			if (!new File(projectDir+File.separator+sourceFileName).exists()) {
				stW.setStateMessage("Source file "+sourceFileName+" doest not exist");
				stW.setState("RUN_ERROR");
	 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
	 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
	 			rc.setModifiedComponent(stW);
	 			rc.submit();
	 			return true;
			}
			
			// scanning filters...
					
			//BufferedReader br = null;
			//BufferedOutputStream bw = null;
		
			CSVParser parser = null;
			CSVPrinter printer = null;
			try {
				//br = new BufferedReader(new InputStreamReader(new FileInputStream(projectDir+File.separator+sourceFileName), "UTF-8"));
				//bw = new BufferedOutputStream(new FileOutputStream(projectDir+File.separator+targetFileName););
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectDir+File.separator+targetFileName), "UTF-8"));

				parser = CSVParser.parse(new File(projectDir+File.separator+sourceFileName), Charset.forName("UTF-8"), CSVFormat.EXCEL);
				printer = new CSVPrinter(bw, CSVFormat.EXCEL);
				
				List<CSVRecord> records = parser.getRecords();				
				Iterator<CSVRecord> rit = records.iterator();
				
				CSVRecord header = null;
				if (rit.hasNext())
						header = rit.next();
				ArrayList<String> attrs = new ArrayList<String>();
				if (header!=null) {
					for (int i=0; i<header.size(); i++)
						attrs.add(header.get(i));
					printer.printRecord(header);
				}
				
		        Map<String,String> filterToColumnMap = new HashMap<String,String>();
		        
				long it = stW.getRAAPI().getIteratorForDirectObjectClasses( stW.getRAAPIReference() );
				long rClass = stW.getRAAPI().resolveIteratorFirst(it);
				stW.getRAAPI().freeIterator(it);
				long rAttr = stW.getRAAPI().findAttribute(rClass, "filtersToColumns");
				String f2c = stW.getRAAPI().getAttributeValue(stW.getRAAPIReference(), rAttr);
				stW.getRAAPI().freeReference(rClass);
				stW.getRAAPI().freeReference(rAttr);
				
				if (f2c != null) {
					for (String filter_column : f2c.split(";")) {
						String[] a = filter_column.split(":");
						if (a.length == 2) {
							filterToColumnMap.put(a[0], a[1]);
						}
					}
				}
		        
				List<IFilter> filterList = initFilters(attrs.toArray(new String[0]), stW, filterToColumnMap);
				
				
				while (rit.hasNext()) {
					CSVRecord csvRecord = rit.next();
		            boolean ok = true;
		            for (IFilter f : filterList)
		            	if (!f.acceptRow(csvRecord)) {
		            		ok = false;
		            		break;
		            	}
		            
		            if (ok) {		            	
		            	if (DEBUG) System.out.println("ACCEPTED LINE "+csvRecord.toString());
		            	printer.printRecord(csvRecord);
//		            	bw.write((line+"\n").getBytes("UTF-8"));
		            }
				}
				
				
				 
/*		        String line = br.readLine();
		        if (line==null)
		        	throw new RuntimeException("The source CSV file is empty");
		        String[] attrs = line.split(",");
	            bw.write((line+"\n").getBytes("UTF-8")); // writing the same column names into the output file*/

						
			}
			catch (Throwable t) {
				System.err.println("Error while transforming by filterer - "+t.toString());
				stW.setStateMessage("Error while transforming by filterer - "+t.toString());
				stW.setState("RUN_ERROR");
	 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
	 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
	 			rc.setModifiedComponent(stW);
	 			rc.submit();
	 			return true;				
			}
			finally {
				if (parser!=null)
					parser.close();
				if (printer!=null)
					printer.close();
			}
 			
			tgtStar.setState("RUN_OK");
 			stW.setState("RUN_OK");
 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
 			rc.setModifiedComponent(stW);
 			rc.submit();
 			
			return true;
		
		}
		catch(Throwable t) {
			System.err.println("Error while preparing filterer - "+t.getMessage());
			return false;
		}
		finally {
			geFactory.unsetRAAPI();
		}
		
	}

}
