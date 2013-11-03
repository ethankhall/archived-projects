package io.ehdev.easyinvoice.contact;

import org.testng.annotations.Test;

public class AddressTest {


    @Test(expectedExceptions = Address.AddressCouldNotBeCreated.class)
    public void testThatBuilderValidatedProperly() throws Exception {
        new Address.Builder()
                .build();
    }

    @Test(expectedExceptions = Address.AddressCouldNotBeCreated.class)
    public void testThatBuilderValidatedProperly_withCity() throws Exception {
        new Address.Builder()
                .setCity("123")
                .build();
    }

    @Test(expectedExceptions = Address.AddressCouldNotBeCreated.class)
    public void testThatBuilderValidatedProperly_withCityAndLine1() throws Exception {
        new Address.Builder()
                .setCity("123")
                .setLine1("13")
                .build();
    }

    @Test
    public void testThatBuilderValidatedProperly_withRequired() throws Exception {
        new Address.Builder()
                .setCity("123")
                .setLine1("13")
                .setCountyCode("US")
                .build();
    }
}
