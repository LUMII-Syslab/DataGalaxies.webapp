package galactictypes.CSV_to_JSON;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lv.lumii.tda.raapi.RAAPI;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webappos.server.API;

public class CSV_to_JSON {

	private static Logger logger = LoggerFactory.getLogger(CSV_to_JSON.class);

	public static boolean emission(RAAPI raapi, String project_id, long r) {
		// r isTypeOf EmitStellarWind

		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();

		try {
			geFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			logger.error("Metamodel error while preparing the CSV to JSON stellar wind: " + e.getMessage());
			return false;
		}

		lv.lumii.datagalaxies.mm.EmitStellarWind cmd = null;
		lv.lumii.datagalaxies.mm.StellarWind stW = null;
		lv.lumii.datagalaxies.mm.StarData src = null;
		lv.lumii.datagalaxies.mm.Star tgtStar = null;
		lv.lumii.datagalaxies.mm.StarData tgt = null;

		try {
			cmd = (lv.lumii.datagalaxies.mm.EmitStellarWind) geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
			stW = cmd.getStellarWind().get(0);
			src = stW.getSource().get(0).getStarData().get(0);
			tgtStar = stW.getTarget().get(0);
			tgt = stW.getTarget().get(0).getStarData().get(0);

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

			if (!new File(projectDir + File.separator + sourceFileName).exists()) {
				stW.setStateMessage("Source file " + sourceFileName + " doest not exist");
				stW.setState("RUN_ERROR");
				lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
				rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				rc.setModifiedComponent(stW);
				rc.submit();
				return true;
			}

			CSVParser parser = null;

//			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				parser = CSVParser.parse(new File(projectDir + File.separator + sourceFileName),
						Charset.forName("UTF-8"), CSVFormat.EXCEL);
				// br = new BufferedReader(new InputStreamReader(new
				// FileInputStream(projectDir+File.separator+sourceFileName), "UTF-8"));
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(projectDir + File.separator + targetFileName), "UTF-8"));

				/*
				 * String line = br.readLine(); if (line==null) throw new
				 * RuntimeException("The source CSV file is empty"); String[] attrs =
				 * line.split(",");
				 */

				List<CSVRecord> records = parser.getRecords();
				Iterator<CSVRecord> rit = records.iterator();

				CSVRecord header = null;
				if (rit.hasNext())
					header = rit.next();
				ArrayList<String> attrs = new ArrayList<String>();
				if (header != null) {
					for (int i = 0; i < header.size(); i++)
						attrs.add(header.get(i));
				}

				JSONArray arr = new JSONArray();

				while (rit.hasNext()) {
					CSVRecord csvRecord = rit.next();

					JSONObject obj = new JSONObject();

					for (int i = 0; i < csvRecord.size(); i++) {
						String attr;
						if (i < attrs.size())
							attr = attrs.get(i);
						else
							attr = "attr" + i;

						String t = csvRecord.get(i).trim();

						obj.put(attr, t);
					}
					arr.put(obj);

				}

				arr.write(bw);
			} catch (Throwable t) {
				stW.setStateMessage("Error while transforming CSV to JSON - " + t.getMessage());
				stW.setState("RUN_ERROR");
				lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
				rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				rc.setModifiedComponent(stW);
				rc.submit();
				return true;
			} finally {
				if (parser != null)
					parser.close();
				if (bw != null)
					bw.close();
			}

			tgtStar.setState("RUN_OK");
			stW.setState("RUN_OK");
			lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
			rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
			rc.setModifiedComponent(stW);
			rc.submit();

			return true;

		} catch (Throwable t) {
			logger.error("Error while preparing transformation from CSV to JSON - " + t.getMessage());
			if (stW != null) {
				stW.setStateMessage("Error while preparing transformation from CSV to JSON - " + t.toString());
				stW.setState("RUN_ERROR");
				lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
				rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				rc.setModifiedComponent(stW);
				rc.submit();
			}
			return false;
		} finally {
			geFactory.unsetRAAPI();
		}

	}

}
