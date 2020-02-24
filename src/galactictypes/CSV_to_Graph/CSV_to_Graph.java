package galactictypes.CSV_to_Graph;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lv.lumii.tda.raapi.RAAPI;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webappos.server.API;

public class CSV_to_Graph {

	private static Logger logger = LoggerFactory.getLogger(CSV_to_Graph.class);

	public static boolean emission(RAAPI raapi, String project_id, long r) {
		// r isTypeOf EmitStellarWind

		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();

		try {
			geFactory.setRAAPI(raapi, "", true);
		} catch (Throwable e) {
			logger.error("Metamodel error while preparing the CSV to Graph(JSON) stellar wind: " + e.getMessage());
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

			long rCTG = src.getRAAPI().findClass("CSV_to_Graph");
			long rSCN = src.getRAAPI().findAttribute(rCTG, "sourceColumnName");
			long rTCN = src.getRAAPI().findAttribute(rCTG, "targetColumnName");

			String sourceColumnName = src.getRAAPI().getAttributeValue(stW.getRAAPIReference(), rSCN);
			String targetColumnName = src.getRAAPI().getAttributeValue(stW.getRAAPIReference(), rTCN);

			src.getRAAPI().freeReference(rCTG);
			src.getRAAPI().freeReference(rSCN);
			src.getRAAPI().freeReference(rTCN);

			if (sourceColumnName == null) {
				stW.setStateMessage("Source column name not specified");
				stW.setState("RUN_ERROR");
				lv.lumii.datagalaxies.mm.RefreshGalaxyCommand rc = geFactory.createRefreshGalaxyCommand();
				rc.setDataGalaxy(lv.lumii.datagalaxies.mm.DataGalaxy.firstObject(geFactory));
				rc.setModifiedComponent(stW);
				rc.submit();
				return true;
			}
			if (targetColumnName == null) {
				stW.setStateMessage("Target column name not specified");
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

			BufferedWriter bw = null;
			try {
				parser = CSVParser.parse(new File(projectDir + File.separator + sourceFileName),
						Charset.forName("UTF-8"), CSVFormat.EXCEL);
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(projectDir + File.separator + targetFileName), "UTF-8"));

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

				JSONObject resultGraph = new JSONObject();

				Set<String> nodeIds = new HashSet<String>();

				JSONArray nodesArr = new JSONArray();
				JSONArray edgesArr = new JSONArray();

				while (rit.hasNext()) {
					CSVRecord csvRecord = rit.next();

					JSONObject obj = new JSONObject();

					boolean hasLabel = false;
					for (int i = 0; i < csvRecord.size(); i++) {
						String attr;
						if (i < attrs.size())
							attr = attrs.get(i);
						else
							attr = "attr" + i;

						String t = csvRecord.get(i).trim();

						if (attr.equals(sourceColumnName)) {
							obj.put("source", t);
							if (!nodeIds.contains(t)) {
								nodeIds.add(t);
								JSONObject node = new JSONObject();
								node.put("id", t);
								node.put("label", t);
								nodesArr.put(node);
							}
						} else if (attr.equals(targetColumnName)) {
							obj.put("target", t);
							if (!nodeIds.contains(t)) {
								nodeIds.add(t);
								JSONObject node = new JSONObject();
								node.put("id", t);
								node.put("label", t);
								nodesArr.put(node);
							}
						} else
							obj.put(attr, t);

						if (attr.equals("label"))
							hasLabel = true;
					}
					if (!hasLabel)
						obj.put("label", obj.getString("source") + " to " + obj.getString("target"));
					edgesArr.put(obj);
				}

				resultGraph.put("nodes", nodesArr);
				resultGraph.put("edges", edgesArr);
				resultGraph.write(bw);

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
			logger.error("Error while preparing transformation from CSV to Graph(JSON) - " + t.getMessage());
			if (stW != null) {
				stW.setStateMessage("Error while preparing transformation from CSV to Graph(JSON) - " + t.toString());
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
