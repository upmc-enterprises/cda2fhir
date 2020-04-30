package tr.com.srdc.cda2fhir.transform.section;

import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Resource;

import tr.com.srdc.cda2fhir.transform.util.IResult;

public interface ISectionResult extends IResult {
	Bundle getBundle();

	List<? extends Resource> getSectionResources();

	boolean hasResourceMaps();
}
