package lv.lumii.datagalaxies;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import lv.lumii.tda.raapi.RAAPI;

public class TdaJson {

	public static String getTdaJsonString(RAAPI raapi, long rObject,
			boolean attributesOnly) {
		return getTdaJsonString(raapi, rObject, attributesOnly, true, "");
	}

	public static String getTdaJsonString(RAAPI raapi, long rObject,
			boolean attributesOnly, boolean backwardLinks) {
		return getTdaJsonString(raapi, rObject, attributesOnly, backwardLinks, "");
	}

	private static String getTdaJsonString(RAAPI raapi, long rObject,
			boolean attributesOnly, boolean backwardLinks, String indent) {
		if (backwardLinks)
			return getTdaJsonString(raapi, rObject, attributesOnly, 0, indent);
		else
			return getTdaJsonString(raapi, rObject, attributesOnly, rObject, indent);
	}
	
	private static String getTdaJsonString(RAAPI raapi, long rObject,
			boolean attributesOnly, long rObjectToExclude, String indent) {
		if ((raapi == null) || (rObject == 0))
			return null;
		
		StringBuffer retVal = new StringBuffer(indent);
		
		retVal.append("{\n"+indent+"\"reference\" : "+rObject);
		
		boolean classPrinted = false;;

		long itCls = raapi.getIteratorForDirectObjectClasses(rObject);
		if (itCls == 0)
			return null;
		long rCls = raapi.resolveIteratorFirst(itCls);
		while (rCls != 0) {
			long itA;
			long rA;
			
			if (!classPrinted) {
				String s = raapi.getClassName(rCls);
				if (s!=null) {
					retVal.append(",\n"+indent+"\"className\" : "+org.codehaus.jettison.json.JSONObject.quote(s));
				}
				classPrinted = true;
			}

			// attribute values...
			itA = raapi.getIteratorForAllAttributes(rCls);
			rA = raapi.resolveIteratorFirst(itA);
			while (rA != 0) {
				String val = raapi.getAttributeValue(rObject, rA);
				if (val != null) {
					retVal.append(",\n"+indent+"\"" + raapi.getAttributeName(rA) + "\" : "
						+ org.codehaus.jettison.json.JSONObject.quote(val));
				}
				raapi.freeReference(rA);
				rA = raapi.resolveIteratorNext(itA);
			}
			raapi.freeIterator(itA);

			// links corresponding to all outgoing associations (including bi-directional), if attributesOnly==false;
			// otherwise, just compositions...
			itA = raapi.getIteratorForAllOutgoingAssociationEnds(rCls);
			rA = raapi.resolveIteratorFirst(itA);
			while (rA != 0) {
				
				boolean isCompos = raapi.isComposition(rA); 
				long itLinked;
				long rLinked;
				
				if (isCompos || !attributesOnly) {
	
					itLinked = raapi.getIteratorForLinkedObjects(rObject, rA);
					rLinked = raapi.resolveIteratorFirst(itLinked);
					
					boolean hasLinkedObjects = rLinked != 0;
					if (rLinked == rObjectToExclude) {
						rLinked = raapi.resolveIteratorNext(itLinked);
						hasLinkedObjects = (rLinked != 0);						
					}
					
					
					if (hasLinkedObjects) {
						retVal.append(",\n"+indent+"\""+raapi.getRoleName(rA)+"\" : [\n"+indent+"  "); 
					}
					
					while (rLinked != 0) {
						
						if (rLinked==rObjectToExclude) {
							// skipping that object
							rLinked = raapi.resolveIteratorNext(itLinked);
							continue;
						}
						
						String linkedJSON = getTdaJsonString(raapi, rLinked, true, rObjectToExclude, indent+"  ");
						if (linkedJSON != null)
							retVal.append(linkedJSON);
	
						rLinked = raapi.resolveIteratorNext(itLinked);
						
						if (rLinked!=0)
							retVal.append(",\n"+indent+"  ");
					}
					raapi.freeIterator(itLinked);
					if (hasLinkedObjects) {
						retVal.append("\n]"); 
					}
				}
				
				raapi.freeReference(rA);

				rA = raapi.resolveIteratorNext(itA);
			}
			raapi.freeIterator(itA);

			// ignoring links of remaining ingoing unidirectional
			// associations...

			raapi.freeReference(rCls);

			rCls = raapi.resolveIteratorNext(itCls);

		}
		raapi.freeIterator(itCls);

			retVal.append("\n}");
		return retVal.toString();
	}

	private static boolean getTdaJson(RAAPI raapi, long rObject,
			boolean attributesOnly, JSONObject obj) {
		if ((raapi == null) || (rObject == 0))
			return false;

		try {
			obj.put("reference", rObject);
		
			long itCls = raapi.getIteratorForDirectObjectClasses(rObject);
			if (itCls == 0)
				return false;
			long rCls = raapi.resolveIteratorFirst(itCls);
			while (rCls != 0) {
				long itA;
				long rA;
				
				String s = raapi.getClassName(rCls);
				if (s!=null) {
					obj.put("className", s);
				}
	
				// attribute values...
				itA = raapi.getIteratorForAllAttributes(rCls);
				rA = raapi.resolveIteratorFirst(itA);
				while (rA != 0) {
					String val = raapi.getAttributeValue(rObject, rA);
					if (val != null) {
						obj.put(raapi.getAttributeName(rA), val);
					}
					raapi.freeReference(rA);
					rA = raapi.resolveIteratorNext(itA);
				}
				raapi.freeIterator(itA);
	
				// links corresponding to all outgoing associations (including bi-directional), if attributesOnly==false;
				// otherwise, just compositions...
				itA = raapi.getIteratorForAllOutgoingAssociationEnds(rCls);
				rA = raapi.resolveIteratorFirst(itA);
				while (rA != 0) {
					
					boolean isCompos = raapi.isComposition(rA); 
					long itLinked;
					long rLinked;
					
					if (isCompos || !attributesOnly) {
		
						itLinked = raapi.getIteratorForLinkedObjects(rObject, rA);
						rLinked = raapi.resolveIteratorFirst(itLinked);
						
						boolean hasLinkedObjects = rLinked != 0;
						
						JSONArray arr = new JSONArray();
						
						while (rLinked != 0) {
							
							JSONObject linkedObj = new JSONObject();
							
							if (!getTdaJson(raapi, rLinked, true, linkedObj))
								return false;
							
							arr.put(linkedObj);
		
							rLinked = raapi.resolveIteratorNext(itLinked);
							
						}
						raapi.freeIterator(itLinked);
						if (hasLinkedObjects) {
							obj.put(raapi.getRoleName(rA), arr);
						}
					}
					
					raapi.freeReference(rA);
	
					rA = raapi.resolveIteratorNext(itA);
				}
				raapi.freeIterator(itA);
	
				// ignoring links of remaining ingoing unidirectional
				// associations...
	
				raapi.freeReference(rCls);
	
				rCls = raapi.resolveIteratorNext(itCls);
	
			}
			raapi.freeIterator(itCls);
	
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	public static String loadTdaJsonString(RAAPI raapi, String rootJsonObjectString, String navigation) {
		JSONObject obj;
		try {
			obj = new JSONObject(rootJsonObjectString);
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		LinkedList<String> list = new LinkedList<String>();
		StringTokenizer t = new StringTokenizer(navigation, ".");
		while (t.hasMoreTokens()) {
			String tkn = t.nextToken();
			if (tkn.isEmpty())
				continue;
			list.add(tkn);
		}
		
		loadTdaJsonRecursively(raapi, obj, list);
		return obj.toString();
	}
	
	
	public static void loadTdaJsonObject(RAAPI raapi, JSONObject obj, String navigation) {
		
		LinkedList<String> list = new LinkedList<String>();
		StringTokenizer t = new StringTokenizer(navigation, ".");
		while (t.hasMoreTokens()) {
			String tkn = t.nextToken();
			if (tkn.isEmpty())
				continue;
			list.add(tkn);
		}
		
		loadTdaJsonRecursively(raapi, obj, list);
	}
	
	private static void loadTdaJsonRecursively(RAAPI raapi, JSONObject rootObject, List<String> navigation) {
		if (navigation.isEmpty())
			return;
		
		if (rootObject instanceof java.util.Collection) {
			for (JSONObject o : (java.util.Collection<JSONObject>)rootObject)
				loadTdaJsonRecursively(raapi, o, navigation);
			return;
		}
		
//		ObjectMapper m = new ObjectMapper();
		//JsonNode root = m.readTree(startObjectJSON);
		
		long rObject = 0;
		try {
			rObject = rootObject.getLong("reference");
		} catch (JSONException e1) {
		}
		
		if (rObject != 0) {
			getTdaJson(raapi, rObject, true, rootObject);
		}
		
		String navigationStep = navigation.get(0);
		boolean recurseStep = (navigationStep.length()>1) && (navigationStep.endsWith("*"));
		if (recurseStep)
			navigationStep = navigationStep.substring(0, navigationStep.length()-1);
		
		boolean allMask = "*".equals(navigationStep); 
		
		if (navigationStep.startsWith("_")) {
//			if (navigationStep.startsWith("_DirectObjectClasses")) {
				
//			}
			
			if (navigationStep.equals("_classes")) {
				long itClasses = raapi.getIteratorForClasses();
				if (itClasses != 0) {
					
					//LinkedList<JSONObject> list = new LinkedList<JSONObject>();
					JSONObject classes = new JSONObject();
					
					long rCls = raapi.resolveIteratorFirst(itClasses);
					
					while (rCls != 0) {
	
						JSONObject clsObj = new JSONObject();
						try {
							clsObj.put("reference", rCls);
							clsObj.put("name", raapi.getClassName(rCls));
						} catch (JSONException e) {
							System.err.println(e.getMessage());
						}
						
						// going further...						
						String navigationFirst = navigation.remove(0);
						loadTdaJsonRecursively(raapi, classes, navigation);
						navigation.add(0, navigationFirst);
						
						//list.add(clsObj);
						try {
							classes.put(raapi.getClassName(rCls), clsObj);
						} catch (JSONException e) {
							System.err.println(e.getMessage());
						}
						
						rCls = raapi.resolveIteratorNext(itClasses);						
					}
					raapi.freeIterator(itClasses);
					
					try {
						//rootObject.put("_classes", list);
						rootObject.put("_classes", classes);
					} catch (JSONException e) {
						System.err.println(e.getMessage());
					}
				}
			}
			
			if (navigationStep.equals("_allClassObjects")) {
				long itObjects = raapi.getIteratorForAllClassObjects(rObject);
				if (itObjects != 0) {
					
					LinkedList<JSONObject> list = new LinkedList<JSONObject>();
					
					long rObj = raapi.resolveIteratorFirst(itObjects);
					
					while (rObj != 0) {
	
						JSONObject objObj = new JSONObject();
						try {
							objObj.put("reference", rObj);
						} catch (JSONException e) {
							System.err.println(e.getMessage());
						}
												
						// going further...						
						String navigationFirst = navigation.remove(0);
						loadTdaJsonRecursively(raapi, objObj, navigation);
						navigation.add(0, navigationFirst);
						
						list.add(objObj);
						
						rObj = raapi.resolveIteratorNext(itObjects);						
					}
					raapi.freeIterator(itObjects);
					
					try {
						rootObject.put("_allClassObjects", list);
					} catch (JSONException e) {
						System.err.println(e.getMessage());
					}
				}				
			}
		}
		else {						
			// searching for attributes and links...
			long itCls = raapi.getIteratorForDirectObjectClasses(rObject);
			if (itCls != 0) {
				long rCls = raapi.resolveIteratorFirst(itCls);
				while (rCls != 0) {
					long itA;
					long rA;
	
					// attribute values...
					if (allMask) {
						itA = raapi.getIteratorForAllAttributes(rCls);
						rA = raapi.resolveIteratorFirst(itA);
						while (rA != 0) {									
							try {
								String val = raapi.getAttributeValue(rObject, rA);
								if (val == null)
									rootObject.remove(raapi.getAttributeName(rA));
								else
									rootObject.put(raapi.getAttributeName(rA), val);
							} catch (JSONException e) {
								System.err.println(e.getMessage());
							}
							raapi.freeReference(rA);
							rA = raapi.resolveIteratorNext(itA);
						}
						raapi.freeIterator(itA);
					}
					else {
						rA = raapi.findAttribute(rCls, navigationStep);
						if (rA != 0) {									
							try {
								String val = raapi.getAttributeValue(rObject, rA);
								if (val == null)
									rootObject.remove(raapi.getAttributeName(rA));
								else
									rootObject.put(raapi.getAttributeName(rA), val);
							} catch (JSONException e) {
								System.err.println(e.getMessage());
							}
							raapi.freeReference(rA);
						}					
					}
	
					// TODO: compositions
					
					if (allMask) {
		
						// links corresponding to all outgoing associations (including
						// bi-directional)...
						itA = raapi.getIteratorForAllOutgoingAssociationEnds(rCls);
						rA = raapi.resolveIteratorFirst(itA);
						while (rA != 0) {
							long itLinked;
							long rLinked;
		
							rootObject.remove(raapi.getRoleName(rA));
							
							itLinked = raapi.getIteratorForLinkedObjects(rObject, rA);
							rLinked = raapi.resolveIteratorFirst(itLinked);
							
							boolean hasLinkedObjects = rLinked != 0;
							
							LinkedList<JSONObject> list = new LinkedList<JSONObject>();
							
							while (rLinked != 0) {
		
								JSONObject linkedObj = new JSONObject();
								try {
									linkedObj.put("reference", rLinked);
								} catch (JSONException e) {
									System.err.println(e.getMessage());
								}
														
								
								// recursing step
								if (recurseStep) {
									loadTdaJsonRecursively(raapi, linkedObj, navigation);
								}
								
								// going further...						
								String navigationFirst = navigation.remove(0);
								loadTdaJsonRecursively(raapi, linkedObj, navigation);
								navigation.add(0, navigationFirst);
								
								list.add(linkedObj);
								
								rLinked = raapi.resolveIteratorNext(itLinked);						
							}
							raapi.freeIterator(itLinked);
							
							if (hasLinkedObjects) {
								try {
									rootObject.put(raapi.getRoleName(rA), list);
								} catch (JSONException e) {
									System.err.println(e.getMessage());
								}
							}
		
							raapi.freeReference(rA);
		
							rA = raapi.resolveIteratorNext(itA);
						}
						raapi.freeIterator(itA);
	
					}
					else {
						rA = raapi.findAssociationEnd(rCls, navigationStep);
						if (rA != 0) {
							long itLinked;
							long rLinked;
		
							rootObject.remove(raapi.getRoleName(rA));
							
							itLinked = raapi.getIteratorForLinkedObjects(rObject, rA);
							rLinked = raapi.resolveIteratorFirst(itLinked);
							
							boolean hasLinkedObjects = rLinked != 0;
							
							LinkedList<JSONObject> list = new LinkedList<JSONObject>();
							
							while (rLinked != 0) {
		
								JSONObject linkedObj = new JSONObject();
								try {
									linkedObj.put("reference", rLinked);
								} catch (JSONException e) {
									System.err.println(e.getMessage());
								}
														
								
								// recursing step
								if (recurseStep) {
									loadTdaJsonRecursively(raapi, linkedObj, navigation);
								}
								
								// going further...						
								String navigationFirst = navigation.remove(0);
								loadTdaJsonRecursively(raapi, linkedObj, navigation);
								navigation.add(0, navigationFirst);
								
								list.add(linkedObj);
								
								rLinked = raapi.resolveIteratorNext(itLinked);						
							}
							raapi.freeIterator(itLinked);
							
							if (hasLinkedObjects) {
								try {
									rootObject.put(raapi.getRoleName(rA), list);
								} catch (JSONException e) {
									System.err.println(e.getMessage());
								}
							}
		
							raapi.freeReference(rA);	
						}
						
					}
					
					// ignoring links of remaining ingoing unidirectional associations...
	
					raapi.freeReference(rCls);
	
					rCls = raapi.resolveIteratorNext(itCls);
	
				}
				raapi.freeIterator(itCls);
			}
			else {
				// using json attribute...
				if (allMask) {
					Iterator<String> it = rootObject.keys();
					
					String navigationFirst = navigation.remove(0);
					while (it.hasNext()) {
						Object o = null;
						try {
							o = rootObject.get(it.next());
						} catch (JSONException e) {
						}
						if (o instanceof JSONObject) {
							loadTdaJsonRecursively(raapi, (JSONObject)o, navigation);							
						}
					}
					navigation.add(0, navigationFirst);
				}
				else {
					JSONObject obj = null;
					try {
						obj = (JSONObject) rootObject.get(navigationStep);
					}
					catch (Throwable t) {					
					}
					
					if (obj!=null) {				
						// going further...						
						String navigationFirst = navigation.remove(0);
						loadTdaJsonRecursively(raapi, obj, navigation);
						navigation.add(0, navigationFirst);
					}
				}
				
			}
				
		}
		

	}
	
	public static boolean storeTdaJsonString(RAAPI raapi, String objectJson) {
		JSONObject obj;
		try {
			obj = new JSONObject(objectJson);
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return false;
		}
		storeJsonRecursively(raapi, obj, new HashSet<Long>());
		return true;
	}
	
	public static String getJsonStringValue(String objectJson, String key)
	{
		JSONObject obj;
		try {
			obj = new JSONObject(objectJson);
			return obj.getString(key);
		} catch (JSONException e) {
			return null;
		}		
	}

	public static String setJsonStringValue(String objectJson, String key, String value)
	{
		JSONObject obj;
		try {
			obj = new JSONObject(objectJson);
			obj.put(key, value);
			return obj.toString();
		} catch (JSONException e) {
			return null;
		}		
	}
	
	private static void storeJsonRecursively(RAAPI raapi, JSONObject rootObject, Set<Long> storedReferences) {
		
		long rObject = 0;
		try {
			rObject = rootObject.getLong("reference");
		} catch (JSONException e1) {
		}
		if (rObject == 0)
			return;
		
		if (storedReferences.contains(rObject))
			return;
		
		storedReferences.add(rObject);

		long itCls = raapi.getIteratorForDirectObjectClasses(rObject);
		if (itCls != 0) {
			long rCls = raapi.resolveIteratorFirst(itCls);
			while (rCls != 0) {
		
				Iterator<String> it = rootObject.keys();
				while (it.hasNext()) {
					String key = it.next();
			
					// searching for an attribute with the given name...					
					long rA = raapi.findAttribute(rCls, key);
					if (rA != 0) {						
						if (rootObject.isNull(key))
							raapi.deleteAttributeValue(rObject, rA);
						else {
							try {
								raapi.setAttributeValue(rObject, rA, rootObject.getString(key));
							} catch (JSONException e) {
							}
						}
						raapi.freeReference(rA);
					}
					
					// searching for an association with the given name...
					rA = raapi.findAssociationEnd(rCls, key);
					if (rA != 0) {
						// deleting old links...
						for (;;) {
							long itt = raapi.getIteratorForLinkedObjects(rObject, rA);
							if (itt==0)
								break;
							long r = raapi.resolveIteratorFirst(itt);
							raapi.freeIterator(itt);
							if (r==0)
								break;
							if (!raapi.deleteLink(rObject, r, rA))
								break; // error on delete...
						}
						
						// creating new links...
						
						if (!rootObject.isNull(key)) {
							
							JSONArray arr;
							try {
								arr = rootObject.getJSONArray(key);
								for (int i=0; i<arr.length(); i++) {
									/*JSONObject o=null;
									try {
										o = (JSONObject)arr.get(i);
									}
									catch(Throwable t) {
										continue;
									}*/
									
									JSONObject o = (JSONObject)arr.get(i);
									
									long r = 0;
									try {
										r = o.getLong("reference");
									}
									catch (Throwable t) {}
									if (r == 0) {
										r = raapi.createObject(raapi.getTargetClass(rA));
										if (r!=0)
											o.put("reference", r);
									}
									
									if (r!=0) {
										raapi.createLink(rObject, r, rA);
										
										storeJsonRecursively(raapi, o, storedReferences);
									}
								}
							} catch (JSONException e) {
								System.err.println(e.getMessage());
							}
						}
						raapi.freeReference(rA);
					}
				}
				raapi.freeReference(rCls);

				rCls = raapi.resolveIteratorNext(itCls);

			}
			raapi.freeIterator(itCls);
		}
		
	}

}
