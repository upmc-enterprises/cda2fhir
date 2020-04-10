package pipeline_wrapper;

import java.io.FileInputStream;

import org.hl7.fhir.r4.model.Bundle;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.consol.ContinuityOfCareDocument;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.srdc.cda2fhir.transform.CCDTransformerImpl;
import tr.com.srdc.cda2fhir.util.FHIRUtil;
import tr.com.srdc.cda2fhir.util.IdGeneratorEnum;

public class Wrapper {

	public static void main(String[] args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(Wrapper.class);
		if (args.length > 1) {
			CDAUtil.loadPackages();
	
			FileInputStream fis = new FileInputStream("src/test/resources/" + args[0]);
			ContinuityOfCareDocument cda = (ContinuityOfCareDocument) CDAUtil.loadAs(fis,
					ConsolPackage.eINSTANCE.getContinuityOfCareDocument());
			
			CCDTransformerImpl ccdTransformer = new CCDTransformerImpl(IdGeneratorEnum.COUNTER);;
			Bundle bundle = ccdTransformer.transformDocument(cda);
			FHIRUtil.printJSON(bundle, "src/test/resources/output/" + args[1]);
		} else {
			logger.error("Wrapper run without arguments");

		}

	}

}
