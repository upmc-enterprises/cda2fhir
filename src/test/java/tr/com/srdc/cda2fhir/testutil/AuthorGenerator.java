package tr.com.srdc.cda2fhir.testutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.PractitionerRole;
import org.junit.Assert;
import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.impl.PersonImpl;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;

public class AuthorGenerator {
	private static final String DEFAULT_CODE_CODE = "363LA2100X";
	private static final String DEFAULT_CODE_PRINTNAME = "Nurse Practitioner - Acute Care";
	private static final String DEFAULT_GIVEN_NAME = "JOE";
	private static final String DEFAULT_FAMILY_NAME = "DOE";
	private static final String DEFAULT_ID_ROOT = "2.5.6.77";
	private static final String DEFAULT_ID_EXTENSION = "1234545";

	private List<Pair<String, String>> ids = new ArrayList<Pair<String, String>>();

	private String familyName;
	private List<String> givenNames = new ArrayList<String>();

	private String codeCode;
	private String codePrintName;

	private OrganizationGenerator organizationGenerator;

	public Author generate(CDAFactories factories) {
		Author author = factories.base.createAuthor();

		AssignedAuthor assignedAuthor = factories.base.createAssignedAuthor();

		for (Pair<String, String> id : ids) {
			String left = id.getLeft();
			String right = id.getRight();
			if (right == null) {
				II ii = factories.datatype.createII(left);
				assignedAuthor.getIds().add(ii);
			} else {
				II ii = factories.datatype.createII(left, right);
				assignedAuthor.getIds().add(ii);
			}
		}

		if (familyName != null || !givenNames.isEmpty()) {
			PN pn = factories.datatype.createPN();

			if (familyName != null) {
				pn.addFamily(familyName);
			}
			givenNames.stream().forEach(r -> pn.addGiven(r));

			PersonImpl person = (PersonImpl) factories.base.createPerson();
			person.getNames().add(pn);

			assignedAuthor.setAssignedPerson(person);
		}

		if (codeCode != null) {
			CE ce = factories.datatype.createCE(codeCode, "2.16.840.1.11388 3.6.101",
					"Healthcare Provider Taxonomy (HIPAA)", codePrintName);
			assignedAuthor.setCode(ce);
		}

		if (organizationGenerator != null) {
			Organization organization = organizationGenerator.generate(factories);
			assignedAuthor.setRepresentedOrganization(organization);
		}

		author.setAssignedAuthor(assignedAuthor);

		return author;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void addGivenName(String givenName) {
		givenNames.add(givenName);
	}

	public void setCode(String code, String printName) {
		codeCode = code;
		codePrintName = printName;
	}

	public void setCode() {
		setCode(DEFAULT_CODE_CODE, DEFAULT_CODE_PRINTNAME);
	}

	public void addId(String root) {
		ids.add(Pair.of(root, null));
	}

	public void addId(String root, String extension) {
		ids.add(Pair.of(root, extension));
	}

	public static AuthorGenerator getDefaultInstance() {
		AuthorGenerator aeg = new AuthorGenerator();

		aeg.setFamilyName(DEFAULT_FAMILY_NAME);
		aeg.addGivenName(DEFAULT_GIVEN_NAME);
		aeg.addId(DEFAULT_ID_ROOT, DEFAULT_ID_EXTENSION);
		aeg.setCode(DEFAULT_CODE_CODE, DEFAULT_CODE_PRINTNAME);

		aeg.organizationGenerator = OrganizationGenerator.getDefaultInstance();

		return aeg;
	}

	public static AuthorGenerator getFullInstance() {
		AuthorGenerator aeg = new AuthorGenerator();

		aeg.setFamilyName(DEFAULT_FAMILY_NAME);
		aeg.addGivenName(DEFAULT_GIVEN_NAME);
		aeg.addId(DEFAULT_ID_ROOT, DEFAULT_ID_EXTENSION);
		aeg.setCode(DEFAULT_CODE_CODE, DEFAULT_CODE_PRINTNAME);

		aeg.organizationGenerator = OrganizationGenerator.getFullInstance();

		return aeg;
	}

	public void verify(Practitioner practitioner) {
		HumanName humanName = practitioner.getName().get(0);
		Assert.assertEquals("Expect the correct family name", familyName, humanName.getFamily());
		Assert.assertEquals("Expect the correct given name", givenNames.get(0), humanName.getGiven().get(0).getValue());

		if (!ids.isEmpty()) {
			Identifier identifier = practitioner.getIdentifier().get(0);
			Pair<String, String> id = ids.get(0);
			Assert.assertEquals("Expect the correct id system", "urn:oid:" + id.getLeft(), identifier.getSystem());
			Assert.assertEquals("Expect the correct id value", id.getRight(), identifier.getValue());
		}
	}

	public void verify(PractitionerRole role) {
		Coding code = role.getCode().get(0).getCoding().get(0);
		Assert.assertEquals("Expect the role code", codeCode, code.getCode());
		Assert.assertEquals("Expect the role print name", codePrintName, code.getDisplay());
	}

	public void verify(org.hl7.fhir.dstu3.model.Organization organization) {
		if (organizationGenerator == null) {
			Assert.assertNull("Author organization", organization);
			return;
		}
		organizationGenerator.verify(organization);
	}
}