package pipeline_wrapper;

import java.io.FileInputStream;

import org.hl7.fhir.r4.model.Bundle;
import org.openhealthtools.mdht.uml.cda.consol.ConsolPackage;
import org.openhealthtools.mdht.uml.cda.consol.ContinuityOfCareDocument;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;

import tr.com.srdc.cda2fhir.transform.CCDTransformerImpl;
import tr.com.srdc.cda2fhir.util.FHIRUtil;
import tr.com.srdc.cda2fhir.util.IdGeneratorEnum;

public class wrapper {

	public static void main(String[] args) throws Exception {
		CDAUtil.loadPackages();

		FileInputStream fis = new FileInputStream("src/test/resources/C-CDA_R2-1_CCD.xml");
		ContinuityOfCareDocument cda = (ContinuityOfCareDocument) CDAUtil.loadAs(fis,
				ConsolPackage.eINSTANCE.getContinuityOfCareDocument());
		
		CCDTransformerImpl ccdTransformer = new CCDTransformerImpl(IdGeneratorEnum.COUNTER);;
		Bundle bundle = ccdTransformer.transformDocument(cda);
		FHIRUtil.printJSON(bundle, "src/test/resources/output/C-CDA_R2-1_CCD_OUTPUT.json");

	}

}
