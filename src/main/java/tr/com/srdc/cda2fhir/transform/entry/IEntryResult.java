package tr.com.srdc.cda2fhir.transform.entry;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.instance.model.api.IBaseResource;

import tr.com.srdc.cda2fhir.transform.util.IResult;

public interface IEntryResult extends IResult {
	Bundle getBundle();

	Bundle getFullBundle();

	void copyTo(Bundle bundle);

	boolean hasResult();

	IBaseResource findResourceResult(Class<? extends Resource> clazz);

}
