package io.ehdev.easyinvoice.interfaces

import de.svenjacobs.loremipsum.LoremIpsum

class ContactInfoWrapper {
    def contactName
    def companyName
    def street1
    def street2
    def city
    def zip
    def country

    def phone
    def email

    def taxId
    def id

    static ContactInfoWrapper createSampleData(){
        def lorem = LoremIpsum.newInstance()
        def contactInfo = ContactInfoWrapper.newInstance()

        contactInfo.with {
            contactName = lorem.getWords(2)
            companyName = lorem.getWords(5)
            street1 = lorem.getWords(2)
            city = lorem.getWords(1)
            zip = "1234"
            country = lorem.getWords(2)
            phone = "123-456-7890"
            email = "john@doe.com"
            taxId = "ASDF12345321"
            id = UUID.randomUUID().toString().replace("-", "")
        }

        return contactInfo
    }



    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        ContactInfoWrapper that = (ContactInfoWrapper) o

        if (city != that.city) return false
        if (companyName != that.companyName) return false
        if (contactName != that.contactName) return false
        if (country != that.country) return false
        if (email != that.email) return false
        if (phone != that.phone) return false
        if (street1 != that.street1) return false
        if (street2 != that.street2) return false
        if (taxId != that.taxId) return false
        if (zip != that.zip) return false

        return true
    }

    int hashCode() {
        int result
        result = (contactName != null ? contactName.hashCode() : 0)
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0)
        result = 31 * result + (street1 != null ? street1.hashCode() : 0)
        result = 31 * result + (street2 != null ? street2.hashCode() : 0)
        result = 31 * result + (city != null ? city.hashCode() : 0)
        result = 31 * result + (zip != null ? zip.hashCode() : 0)
        result = 31 * result + (country != null ? country.hashCode() : 0)
        result = 31 * result + (phone != null ? phone.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (taxId != null ? taxId.hashCode() : 0)
        return result
    }

    @Override
    public java.lang.String toString() {
        return "ContactInfoWrapper{" +
                "contactName=" + contactName +
                ", companyName=" + companyName +
                ", street1=" + street1 +
                ", street2=" + street2 +
                ", city=" + city +
                ", zip=" + zip +
                ", country=" + country +
                ", phone=" + phone +
                ", email=" + email +
                ", taxId=" + taxId +
                '}';
    }
}
