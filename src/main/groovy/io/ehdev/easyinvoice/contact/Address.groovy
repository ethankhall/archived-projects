package io.ehdev.easyinvoice.contact

class Address {
    String line1
    String line2
    String city;
    String state
    String postalCode
    String postalCodeExtension
    String countryCode;

    private Address() {}

    static class Builder {
        private Address addressInProgress;

        public Builder() {
            addressInProgress = new Address()
        }

        Builder setLine1(def input) {
            addressInProgress.line1 = input
            return this
        }

        Builder setLine2(def input) {
            addressInProgress.line2 = input
            return this
        }

        Builder setCity(def input) {
            addressInProgress.city = input
            return this
        }

        Builder setState(def input) {
            addressInProgress.state = input
            return this
        }

        Builder setPostalCode(def input) {
            addressInProgress.postalCode = input
            return this
        }

        Builder setPostalCodeExt(def input) {
            addressInProgress.postalCodeExtension = input
            return this
        }

        Builder setCountyCode(def input) {
            addressInProgress.countryCode = input
            return this
        }

        def build() {
            if (!addressInProgress.line1 || !addressInProgress.city || !addressInProgress.countryCode)
                throw new AddressCouldNotBeCreated(addressInProgress)

            return addressInProgress;
        }


    }

    public static class AddressCouldNotBeCreated extends Exception {
        public AddressCouldNotBeCreated(Address address) {
            super(getMessage(address))
        }

        private static String getMessage(Address address) {
            String result = (address.line1?.empty) ? "Line 1 needs to be filled in; " : ""
            result += (address.city?.empty) ? "City needs to be filled in; " : ""
            result += (address.countryCode?.empty) ? "Country Code needs to be filled in" : ""
            result
        }

    }
}
