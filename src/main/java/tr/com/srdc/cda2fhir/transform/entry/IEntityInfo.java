package tr.com.srdc.cda2fhir.transform.entry;

import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;

public interface IEntityInfo {
	Practitioner getPractitioner();

	PractitionerRole getPractitionerRole();

	Organization getOrganization();

	boolean isOrgNew();

	void setOrgIsNew(boolean orgIsNew);

	Device getDevice();
}
