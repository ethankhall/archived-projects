package io.ehdev.easyinvoice.services.converter

import io.ehdev.easyinvoice.accessor.LineItemAccessor
import io.ehdev.easyinvoice.interfaces.FlatLineItemWrapper
import io.ehdev.easyinvoice.interfaces.HourlyLineItemWrapper
import io.ehdev.easyinvoice.interfaces.LineItemWrapper
import io.ehdev.easyinvoice.lineitem.FlatLineItem
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import io.ehdev.easyinvoice.lineitem.LineItem
import io.ehdev.easyinvoice.services.excpetions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.security.InvalidParameterException

@Component
class LineItemConverter {

    @Autowired
    LineItemAccessor lineItemAccessor

    LineItemWrapper getLineItemFromId(String id){
        def lineItem = lineItemAccessor.get(id)
        if(lineItem instanceof HourlyLineItem){
            return convertToHourlyLineItemWrapper(lineItem)
        } else if(lineItem instanceof FlatLineItem ){
            return convertToFlatLineItemWrapper(lineItem)
        } else {
            throw new NotFoundException([ response: [ status: "LineItem not found" ] ]);
        }
    }

    void deleteLineItem(String id){
        lineItemAccessor[id] = null
    }

    void saveLineItem(LineItemWrapper lineItemWrapper){
        def lineItem = convertWrapperToLineItem(lineItemWrapper)
        lineItemAccessor[lineItem.id] = lineItem
    }

    LineItem convertWrapperToLineItem(LineItemWrapper lineItemWrapper) {
        if(lineItemWrapper instanceof HourlyLineItemWrapper){
            convertHourlyLineItemWrapperToLineItem(lineItemWrapper)
        } else if(lineItemWrapper instanceof FlatLineItemWrapper) {
            convertFlatLineItemWrapperToLineItem(lineItemWrapper)
        } else {
            throw new InvalidParameterException()
        }
    }

    LineItem convertHourlyLineItemWrapperToLineItem(HourlyLineItemWrapper hourlyLineItemWrapper) {
        def parameters = [
                hourlyRate: hourlyLineItemWrapper.hourlyRate, hours: hourlyLineItemWrapper.hours, description: hourlyLineItemWrapper.description,
                category: hourlyLineItemWrapper.category, id: hourlyLineItemWrapper.id, taxEnabled: hourlyLineItemWrapper.taxable
        ]
        HourlyLineItem.newInstance(parameters)
    }

    FlatLineItemWrapper convertToFlatLineItemWrapper(FlatLineItem flatLineItem) {
        def fieldData = [ amount: flatLineItem.amount, description: flatLineItem.description,
                category: flatLineItem.category, id: flatLineItem.id, taxable: flatLineItem.taxEnabled]
        return FlatLineItemWrapper.newInstance(fieldData)
    }

    HourlyLineItemWrapper convertToHourlyLineItemWrapper(HourlyLineItem lineItem){
        def fieldData = [hourlyRate : lineItem.hourlyRate, hours : lineItem.hours, amount: lineItem.calculateAmount() ,
                description: lineItem.description, category: lineItem.category, id: lineItem.id, taxable: lineItem.taxEnabled]
        return HourlyLineItemWrapper.newInstance(fieldData)
    }
}
