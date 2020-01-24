package galactictypes.StarData.CSV;

import lv.lumii.tda.raapi.RAAPI;

public class CSV {
	
	public static boolean designerConfiguration(RAAPI raapi, long r)
	{
		// r isTypeOf ConfigureStar
		
		System.out.println("CSV designerConfiguration at server called");
		
		lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory eeFactory = new lv.lumii.datagalaxies.eemm.EnvironmentEngineMetamodelFactory();
		lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory geFactory = new lv.lumii.datagalaxies.mm.GalaxyEngineMetamodelFactory();
		
		try {
			try {
				eeFactory.setRAAPI(raapi, "", true);
				geFactory.setRAAPI(raapi, "", true);
			} catch (Throwable e) {
				return false;
			}
			
			
			lv.lumii.datagalaxies.mm.ConfigureStar cmd = (lv.lumii.datagalaxies.mm.ConfigureStar)geFactory.findOrCreateRAAPIReferenceWrapper(r, false);
		
 			lv.lumii.datagalaxies.eemm.Frame frame = eeFactory.createFrame();
			//frame.setLocation("popup[300x400]");//cmd.getLocation());
			frame.setLocation(cmd.getFrameLocation());
			frame.setCaption(cmd.getStar().get(0).getId()+" configuration");
			
			cmd.getStar().get(0).setFrame((lv.lumii.datagalaxies.mm.Frame)geFactory.findOrCreateRAAPIReferenceWrapper(frame.getRAAPIReference(), false));
			
			frame.setOnCloseFrameRequestedEvent("staticjava:lv.lumii.datagalaxies.DataGalaxiesApp#onCloseFrameRequestedEvent");
			
			frame.setContentURI("innerhtml:Hello, this is CSV star data configuration.<p>Select your log-files: <input id=\"logfiles\" type=\"file\" multiple><br>");
			//frame.setContentURI("html:http://www.lumii.lv");
			lv.lumii.datagalaxies.eemm.AttachFrameCommand afc = eeFactory.createAttachFrameCommand();
			afc.setFrame(frame);
			afc.submit();
			
			return true;
		
		}
		finally {
			geFactory.unsetRAAPI();
		}
	}

}
