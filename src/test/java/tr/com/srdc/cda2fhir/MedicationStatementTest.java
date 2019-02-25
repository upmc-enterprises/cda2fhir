package tr.com.srdc.cda2fhir;

/*
 * #%L
 * CDA to FHIR Transformer Library
 * %%
 * Copyright (C) 2019 Amida Technology Solutions, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.List;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.consol.impl.MedicationActivityImpl;
import org.openhealthtools.mdht.uml.cda.consol.impl.ConsolFactoryImpl;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.impl.CDAFactoryImpl;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.impl.DatatypesFactoryImpl;
import org.hl7.fhir.dstu3.model.Base;
import tr.com.srdc.cda2fhir.transform.ResourceTransformerImpl;

public class MedicationStatementTest {

    private static final ResourceTransformerImpl rt = new ResourceTransformerImpl();

	private static DatatypesFactory cdaTypeFactory;
	private static CDAFactoryImpl cdaFactory;
	private static ConsolFactoryImpl consolFactory;

    @BeforeClass
	public static void init() {
		CDAUtil.loadPackages();	
		cdaTypeFactory = DatatypesFactoryImpl.init();		
		cdaFactory = (CDAFactoryImpl) CDAFactoryImpl.init();
		consolFactory = (ConsolFactoryImpl) ConsolFactoryImpl.init();
    }
 

    @Test
    public void testMedicationStatus() throws Exception {
      
    	// Make a medication activity.
    	MedicationActivityImpl medAct = (MedicationActivityImpl) consolFactory.createMedicationActivity();
    	
        // Transform from CDA to FHIR.
        org.hl7.fhir.dstu3.model.Bundle fhirBundle = rt.tMedicationActivity2MedicationStatement(medAct);
       
        org.hl7.fhir.dstu3.model.Resource fhirResource = fhirBundle.getEntry().get(0).getResource();
        List<Base> takenCodes = fhirResource.getNamedProperty("taken").getValues();
        
        // Make assertions.
        Assert.assertEquals("Taken code defaults to UNK","unk", takenCodes.get(0).primitiveValue());
        

    }


}