package tr.com.srdc.cda2fhir;

import java.util.List;

import ca.uhn.fhir.model.dstu2.resource.*;
import ca.uhn.fhir.model.dstu2.resource.Device;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Organization;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.resource.Patient.Communication;
import ca.uhn.fhir.model.dstu2.resource.Procedure;
import ca.uhn.fhir.model.dstu2.resource.Procedure.Performer;

import org.openhealthtools.mdht.uml.cda.*;
import org.openhealthtools.mdht.uml.cda.consol.AllergyProblemAct;
import org.openhealthtools.mdht.uml.cda.consol.Encounter;
import org.openhealthtools.mdht.uml.cda.consol.MedicationActivity;
import org.openhealthtools.mdht.uml.cda.consol.MedicationInformation;
import org.openhealthtools.mdht.uml.cda.consol.ProblemConcernAct;
import org.openhealthtools.mdht.uml.cda.consol.ResultObservation;
import org.openhealthtools.mdht.uml.cda.consol.VitalSignObservation;
/**
 * Created by mustafa on 7/28/2016.
 */
public interface ResourceTransformer {
	
	// necip start
	Patient PatientRole2Patient(PatientRole patRole);

	ca.uhn.fhir.model.dstu2.resource.Patient.Contact Guardian2Contact( org.openhealthtools.mdht.uml.cda.Guardian guardian );
	
	ca.uhn.fhir.model.dstu2.resource.Procedure Procedure2Procedure(org.openhealthtools.mdht.uml.cda.Procedure cdaProcedure);

	Performer Performer22Performer( Performer2 cdaPerformer );

	Practitioner AssignedEntity2Practitioner( AssignedEntity assignedEntity );

	ca.uhn.fhir.model.dstu2.resource.Organization Organization2Organization ( org.openhealthtools.mdht.uml.cda.Organization cdaOrganization );

	Communication LanguageCommunication2Communication( LanguageCommunication LC );

	// necip end
	
	// ismail start
	Medication Medication2Medication(MedicationInformation manPro);
	
	List<Condition> ProblemConcernAct2Condition(ProblemConcernAct probAct);

	MedicationActivity MedicationActivity2MedicationSatement(
			MedicationActivity subAd);
	
	MedicationDispense MedicationDispense2MedicationDispense(org.openhealthtools.mdht.uml.cda.consol.MedicationDispense sup);
	// ismail end
	
	//tahsin start
    Observation ResultObservation2Observation(ResultObservation resObs);

    Observation VitalSignObservation2Observation(VitalSignObservation vsObs);
    
    
    /*This is not a fully independent mapping method.*/
    Practitioner Performer2Practitioner(Performer2 performer);
    /*It will be called by functions which contain Practitioner as a subresource*/
    
    AllergyIntolerance AllergyProblemAct2AllergyIntolerance(AllergyProblemAct allergyProblemAct);

    Immunization SubstanceAdministration2Immunization(SubstanceAdministration subAd);
    
    ca.uhn.fhir.model.dstu2.resource.Device Device2Device(org.openhealthtools.mdht.uml.cda.Device device);
    //tahsin end

	Composition.Section section2Section(Section cdaSec);
}