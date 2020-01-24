package galactictypes.Spreadsheet_to_CSV;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.webappos.server.API;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import lv.lumii.tda.raapi.RAAPI;

public class Spreadsheet_to_CSV {
	
	
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
		
		System.out.println("Spreadsheet_to_CSV emission at server called");
		
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
			
			
			long rClass = src.getRAAPI().findClass("Spreadsheet_to_CSV");
			long rAttr = src.getRAAPI().findAttribute(rClass, "sheetName");
			String sheetName = src.getRAAPI().getAttributeValue(stW.getRAAPIReference(), rAttr);
			src.getRAAPI().freeReference(rClass);
			src.getRAAPI().freeReference(rAttr);
			int sheetIndex = -1;
			try {
				sheetIndex = Integer.parseInt(sheetName)-1; // sheetName may contain index starting from 1
			}
			catch(Throwable t) {
				if ((sheetName == null) || (sheetName.trim().isEmpty()))
					sheetIndex = 0;
			}
			
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
		
			CSVPrinter printer = null;
			try {
				// initializing output writer...
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(projectDir+File.separator+targetFileName), "UTF-8"));
				printer = new CSVPrinter(bw, CSVFormat.EXCEL);
				
				Sheet sheet = null;
				// initialize a sheet from source xlsx file...
				if (sourceFileName.toLowerCase().endsWith(".xlsx") || sourceFileName.toLowerCase().endsWith(".xlsm")) {
					XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(new File(projectDir+File.separator+sourceFileName)));
					
					if (sheetIndex >= 0)
						sheet = wBook.getSheetAt(0);
					else
						sheet = wBook.getSheet(sheetName);
				}
				// initialize a sheet from source xls file...
				if (sourceFileName.toLowerCase().endsWith(".xls")) {
					
					FileInputStream myInput = new FileInputStream(projectDir+File.separator+sourceFileName);
			        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
			        HSSFWorkbook wBook = new HSSFWorkbook(myFileSystem);
					
					if (sheetIndex >= 0)
						sheet = wBook.getSheetAt(0);
					else
						sheet = wBook.getSheet(sheetName);
				}
				
				if (sheet == null)
					throw new RuntimeException("Could not open spreadsheet from "+sourceFileName);
					
				ArrayList<String> list = new ArrayList<String>();
				for (org.apache.poi.ss.usermodel.Row row : sheet) {
					for (org.apache.poi.ss.usermodel.Cell cell : row) {
						String value = cell.toString();
						
						String dateFormat = cell.getCellStyle().getDataFormatString();
						if ((dateFormat!=null) && !dateFormat.equals("@") && !dateFormat.equals("General")) {
							System.out.println("!!!!dateFormat for "+value+" is "+dateFormat);
							// this is date...
							try {
								Date d = cell.getDateCellValue();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								value = sdf.format(d);
							}
							catch(Throwable t) {
								// if something goes wrong, just keeping the cell.toString() in the value
							}
							
						}
						
					/*	switch (cell.getCellType()) {
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                        	value = new Boolean(cell.getBooleanCellValue()).toString();

                            break;
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                        	value = new Double(cell.getNumericCellValue()).toString();
							if (value.endsWith(".0"))
								value = value.substring(0, value.length()-2);
                            break;
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                        	value = cell.getStringCellValue();
                            break;
                            
                        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
                        	list.add("");
                            break;
                        default:
							try {
								Date d = cell.getDateCellValue();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								value = sdf.format(d); 
							}
							catch(Throwable t) {
								value = cell.toString();
							}
						} // switch*/
						
						if (value == null)
							value = "";
						if (value.endsWith(".0"))
							value = value.substring(0, value.length()-2);
						
						list.add(value);						
                    }
					printer.printRecord(list);
					list.clear();
				}

			}
			catch (Throwable t) {
				System.err.println("Error while transforming by Spreadsheet_to_CSV - "+t.toString());
				stW.setStateMessage("Error while transforming by Spreadsheet_to_CSV - "+t.toString());
				stW.setState("RUN_ERROR");
	 			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
	 			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
	 			rc.setModifiedComponent(stW);
	 			rc.submit();
	 			return true;				
			}
			finally {
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
