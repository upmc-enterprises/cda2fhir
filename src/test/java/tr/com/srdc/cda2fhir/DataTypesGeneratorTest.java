package tr.com.srdc.cda2fhir;

import java.util.HashMap;
import java.util.Map;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Period;
import org.junit.BeforeClass;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;

import tr.com.srdc.cda2fhir.testutil.CDAFactories;
import tr.com.srdc.cda2fhir.testutil.generator.ADGenerator;
import tr.com.srdc.cda2fhir.testutil.generator.IVL_TSPeriodGenerator;
import tr.com.srdc.cda2fhir.testutil.generator.PNGenerator;
import tr.com.srdc.cda2fhir.testutil.generator.TELGenerator;
import tr.com.srdc.cda2fhir.transform.DataTypesTransformerImpl;
import tr.com.srdc.cda2fhir.transform.IDataTypesTransformer;

public class DataTypesGeneratorTest {
	private static CDAFactories factories;
	private static IDataTypesTransformer dtt;

	private interface Verification {
		Map<String, Object> verify(Map<String, Object> input);
	}

	@BeforeClass
	public static void init() {
		CDAUtil.loadPackages();
		factories = CDAFactories.init();
		dtt = new DataTypesTransformerImpl();
	}

	private static void verify(TELGenerator generator) {
		TEL tel = generator.generate(factories);
		ContactPoint contactPoint = dtt.tTEL2ContactPoint(tel);
		generator.verify(contactPoint);
	}

	private static void verify(ADGenerator generator) {
		AD ad = generator.generate(factories);
		Address address = dtt.AD2Address(ad);
		generator.verify(address);
	}

	private static void verify(PNGenerator generator) {
		PN pn = generator.generate(factories);
		HumanName humanName = dtt.tEN2HumanName(pn);
		generator.verify(humanName);
	}

	private static void verify(IVL_TSPeriodGenerator generator) {
		IVL_TS ivlTs = generator.generate(factories);
		Period period = dtt.tIVL_TS2Period(ivlTs);
		generator.verify(period);
	}

	private static final Map<String, Verification> verifications = new HashMap<>();
	static {
		verifications.put("TEL", input -> {
			TELGenerator generator = new TELGenerator(input);
			verify(generator);
			return generator.toJson();
		});
		verifications.put("AD", input -> {
			ADGenerator generator = new ADGenerator(input);
			verify(generator);
			return generator.toJson();
		});
		verifications.put("PN", input -> {
			PNGenerator generator = new PNGenerator(input);
			verify(generator);
			return generator.toJson();
		});
		verifications.put("IVL_TSPeriod", input -> {
			IVL_TSPeriodGenerator generator = new IVL_TSPeriodGenerator(input);
			verify(generator);
			return generator.toJson();
		});
	}

}
