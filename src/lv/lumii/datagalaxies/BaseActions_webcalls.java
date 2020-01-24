package lv.lumii.datagalaxies;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import lv.lumii.tda.kernel.TDACopier;
import lv.lumii.tda.raapi.RAAPI;


public class BaseActions_webcalls {

	private static boolean DEBUG = true;

	/**
	 * Searches for objects of the given class (see criteriaStr and its attribute isKindOf) having the given attribute values.
	 * @param raapi a pointer to RAAPI passed to each string-to-string transformation
	 * @param criteriaStr a TDA JSON string with attribute values to match; a special attribute name isKindOf specifies the class name to lookup
	 * @return a stringified JSON list of found objects in TDA JSON syntax
	 */
	public static String findObjects(RAAPI raapi, String criteriaStr)
	{
		if (raapi==null)
			return null;
		
		JSONObject criteria;
		try {
			criteria = new JSONObject(criteriaStr);
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		String className;
		try {
			className = criteria.getString("isKindOf");			
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		criteria.remove("isKindOf");
		
		StringBuffer retVal = new StringBuffer("[");
		
		long rCls = raapi.findClass(className);
		long it = raapi.getIteratorForAllClassObjects(rCls);
		long r = raapi.resolveIteratorFirst(it);
		while (r!=0) {
			
			// checking whether the object conforms to the given attribute values:
			boolean conforms = true;
			
			Iterator<String> itk = criteria.keys();
			while (conforms && itk.hasNext()) {
				String key = itk.next();
				
				long rAttr = raapi.findAttribute(rCls, key);
				if (rAttr != 0) {
					String val1 = null;
					try {
						val1 = criteria.getString(key);
					} catch (JSONException e) {
					}
					String val2 = raapi.getAttributeValue(r, rAttr);
					if (val1==null) {
						if (val2!=null)
							conforms = false;
					}
					else
						if (!val1.equals(val2))
							conforms = false;
					raapi.freeReference(rAttr);
				}
				else
					conforms = false;
			}
			
			if (conforms) {
				String s = TdaJson.getTdaJsonString(raapi, r, true);
				if (retVal.length()>1)
					retVal.append(",");
				retVal.append(s);
			}
			else {
				raapi.freeReference(r);
			}
			
			r = raapi.resolveIteratorNext(it);
		}
		raapi.freeIterator(it);
		
		retVal.append("]");
		
		return retVal.toString();
	}

	/**
	 * Loads additional attributes and links for the given objects represented as JSON
	 * (some attributes and links can already be there; the "reference" attribute is mandatory for each objects). 
	 * @param navigationAndObjects the JSON describing a navigation expression (attribute "navigationExpression" or several
	 *        expressions in attribute "navigationExpressions") and
	 *        a list of starting objects (attribute "objects"); 
	 * 		  the navigation expression is in the form .x1.x2.x3 (up to xN), where
	 *        xI is a role name, attribute name, or * for all roles and attributes.
	 *        Special values for xI can be used to traverse the metamodel, e.g., _objectClasses, _directSubClasses, _directSuperClasses...;
	 *        the "*" symbol after a special value denotes recursive search, e.g., _directSubClasses*
	 * @return the original list of starting objects (stringified)  complemented by the attributes and links corresponding to the
	 *         navigation expression.
	 */
	public static String loadObjects(RAAPI raapi, String navigationAndObjects)
	{
		if (raapi == null)
			return null;
		
		try {
		try {
			JSONObject json;
			json = new JSONObject(navigationAndObjects);		
			StringBuffer retVal = new StringBuffer("");
			
			String navigationExpr = null;
			JSONArray navigationExprArr = null;
			try {
				navigationExpr = json.getString("navigationExpression");
			}
			catch(Throwable t) {
				navigationExprArr = json.getJSONArray("navigationExpressions");
			}
			
			if (navigationExpr != null) {
				String objStr = null;
				JSONArray arr = null;
				try {
					objStr = json.getString("object");
				}
				catch(Throwable t) {
					arr = json.getJSONArray("objects");
				}
					
				if (objStr != null) {
					return TdaJson.loadTdaJsonString(raapi, objStr, navigationExpr);
				}
				else {
					retVal.append("[");
					for (int i=0; i<arr.length(); i++) {	
						JSONObject obj = arr.getJSONObject(i);
						TdaJson.loadTdaJsonObject(raapi, obj, navigationExpr);
						if (retVal.length()>1)
							retVal.append(",");
						retVal.append(obj.toString());
					}
					retVal.append("]");
					return retVal.toString();
				}
			}
			else {
				assert navigationExprArr != null;
				
				String objStr = null;
				JSONArray arr = null;
				try {
					objStr = json.getString("object");
				}
				catch(Throwable t) {
					arr = json.getJSONArray("objects");
				}
					
				if (objStr != null) {
					JSONObject obj;
					try {
						obj = new JSONObject(objStr);
					} catch (JSONException e) {
						System.err.println(e.getMessage());
						return null;
					}
					
					for (int j=0; j<navigationExprArr.length(); j++) {
						navigationExpr = navigationExprArr.getString(j);
						TdaJson.loadTdaJsonObject(raapi, obj, navigationExpr);
					}
					
					return obj.toString();
				}
				else {
					retVal.append("[");
					for (int i=0; i<arr.length(); i++) {
						JSONObject obj;
						try {
							obj = arr.getJSONObject(i);
						} catch (JSONException e) {
							System.err.println(e.getMessage());
							return null;
						}
						
						for (int j=0; j<navigationExprArr.length(); j++) {
							navigationExpr = navigationExprArr.getString(j);
							TdaJson.loadTdaJsonObject(raapi, obj, navigationExpr);
						}
						
						if (i>0)
							retVal.append(",");
						retVal.append(obj.toString());
					}
					retVal.append("]");
					return retVal.toString();
				}
				
								
				
			}
		
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		}
		finally {
		
		}
		
	}
	
	/**
	 * Stores the given object with its attributes and links (represented in the JSON format) in the repository.
	 * If some attribute or role name value is null, that attribute or corresponding links are deleted from the repository.
	 * To delete objects, use the deleteObjects function (see below). 
	 * @param objectsStr a stringified JSON representing a list of objects in TDA JSON syntax to store
	 * @return the original list of objects on success, or null on error.
	 */
	public static String storeObjects(RAAPI raapi, String objectsStr)
	{
		if (raapi == null)
			return null;
		
		
		try {
		try {
			JSONArray arr = new JSONArray(objectsStr);
			
			boolean ok = true;
			
			for (int i=0; i<arr.length(); i++) {					
				if (!TdaJson.storeTdaJsonString(raapi, arr.getJSONObject(i).toString()))
					ok = false;
			}
			
			if (ok)
				return objectsStr;
			else
				return null;
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		}
		finally {
			
		}
		
	}
	
	/**
	 * Removes the objects stringified in TDA JSON syntax. Only root objects (and their linked objects via compositions) are deleted.
	 * @param objectsStr a stringified JSON representing a list of objects in TDA JSON syntax to delete
	 * @return the original list of objects on success, or null on error.
	 */
	public static String deleteObjects(RAAPI raapi, String objectsStr)
	{
		if (raapi == null)
			return null;


		try {
		try {
			JSONArray arr = new JSONArray(objectsStr);
			
			boolean ok = true;
			
			for (int i=0; i<arr.length(); i++) {
				Long r = null;
				try {
					r = arr.getJSONObject(i).getLong("reference");
				}
				catch(Throwable t) {					
				}
				if (r!=null) {
					if (!raapi.deleteObject(r))
						ok = false;
				}
				else
					ok = false;
			}
			
			if (ok)
				return objectsStr;
			else
				return null;		
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		}
		finally {
		
		}
		
	}

	/**
	 * Copies (replicates) the objects specified as stringified objects in TDA JSON syntax. Compositions are also copied recursively.
	 * @param objectsStr a stringified JSON representing a list of objects in TDA JSON syntax to copy
	 * @return the list similar to the original list, but representing the copied objects; returns null on error.
	 */
	public static String replicateObjects(RAAPI raapi, String objectsStr)
	{
		if (raapi == null)
			return null;
		
		if (DEBUG) System.out.println("replicateObject("+objectsStr+") called.");

		
		ArrayList<Long> copied = new ArrayList<Long>();
		
		String retVal = "[";

		try {
		try {
			JSONArray arr = new JSONArray(objectsStr);
			
			boolean ok = true;
			
			for (int i=0; i<arr.length(); i++) {
				Long r = null;
				try {
					r = arr.getJSONObject(i).getLong("reference");
				}
				catch(Throwable t) {					
				}
				if (r!=null) {
					long rCopy = TDACopier.copyObject(raapi, r);
					if (rCopy == 0)
						ok = false;
					else {
						copied.add(rCopy);
						String s = TdaJson.getTdaJsonString(raapi, rCopy, false);
						if (s == null)
							ok = false;
						else {
							if (retVal.length()>1)
								retVal += ","+s;
							else
								retVal += s;
						}
					}
				}
				else
					ok = false;
				if (!ok)
					break;
			}
			
			if (ok)
				return retVal+"]";
			else {
				return null;
			}
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		}
		finally {
		
		}
		
	}
	
	public static String insertMMD(RAAPI raapi, String mmd)
	{
		if ((raapi==null) || (mmd==null))
			return null;
		
		boolean retVal;
		InputStream is = new ByteArrayInputStream(mmd.getBytes(StandardCharsets.UTF_8));
		retVal = lv.lumii.tda.kernel.mmdparser.MetamodelInserter.insertMMD(is, raapi, null);					

		return "{\"result\":"+retVal+"}";		
	}
	

}
