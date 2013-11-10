package io.ehdev.easyinvoice.services.converter

import io.ehdev.easyinvoice.accessor.ContactInfoAccessor
import io.ehdev.easyinvoice.accessor.InvoiceAccessor
import io.ehdev.easyinvoice.accessor.LineItemAccessor
import io.ehdev.easyinvoice.interfaces.FlatLineItemWrapper
import io.ehdev.easyinvoice.interfaces.HourlyLineItemWrapper
import io.ehdev.easyinvoice.interfaces.LineItemWrapper
import io.ehdev.easyinvoice.lineitem.FlatLineItem
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BackendConverter {

    @Autowired
    LineItemAccessor lineItemAccessor

    @Autowired
    InvoiceAccessor invoiceAccessor

    @Autowired
    ContactInfoAccessor contactInfoConverter

    LineItemWrapper getLineItemFromId(String id){
        def lineItem = lineItemAccessor.get(id)
        if(lineItem instanceof HourlyLineItem){
            return convertToHourlyLineItemWrapper(lineItem)
        } else if(lineItem instanceof FlatLineItem ){
            return convertToFlatLineItemWrapper(lineItem)
        } else {
            return null;
        }
    }

    FlatLineItemWrapper convertToFlatLineItemWrapper(FlatLineItem flatLineItem) {
        def fieldData = [ amount: flatLineItem.amount, description: flatLineItem.description,
                category: flatLineItem.category, id: flatLineItem.id, taxable: flatLineItem.taxEnabled]
        return FlatLineItemWrapper.newInstance(fieldData)
    }

    HourlyLineItemWrapper convertToHourlyLineItemWrapper(HourlyLineItem lineItem){
        def fieldData = [rate : lineItem.hourlyRate, time : lineItem.hours, amount: lineItem.calculateAmount() ,
            description: lineItem.description, category: lineItem.category, id: lineItem.id, taxable: lineItem.taxEnabled]
        return HourlyLineItemWrapper.newInstance(fieldData)
    }
}
