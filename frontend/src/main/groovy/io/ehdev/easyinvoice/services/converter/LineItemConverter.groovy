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

@Component
class LineItemConverter {

    @Autowired
    LineItemAccessor lineItemAccessor

    LineItemWrapper getWrapperFromId(String id){
        def lineItem = lineItemAccessor.get(id)
        if(null != lineItem){
            convertLineItemToWrapper(lineItem)
        } else {
            throw new NotFoundException([ response: [ status: "LineItem not found" ] ]);
        }
    }

    LineItem getLineItemFromId(String id){
        def lineItem = lineItemAccessor.get(id)
        if(null == lineItem) {
            throw new NotFoundException([ response: [ status: "LineItem not found" ] ]);
        } else {
            return lineItem
        }
    }

    void deleteLineItem(String id){
        lineItemAccessor.delete id
    }

    void saveLineItem(LineItemWrapper lineItemWrapper){
        def lineItem = convertWrapperToLineItem(lineItemWrapper)
        lineItemAccessor.save lineItem
    }

    static LineItem convertWrapperToLineItem(FlatLineItemWrapper flatLineItemWrapper) {
        def parameters = [
                amount: flatLineItemWrapper.amount, description: flatLineItemWrapper.description,
                category: flatLineItemWrapper.category, id: flatLineItemWrapper.id, taxEnabled: flatLineItemWrapper.taxable
        ]
        FlatLineItem.newInstance(parameters)
    }

    static LineItem convertWrapperToLineItem(HourlyLineItemWrapper hourlyLineItemWrapper) {
        def parameters = [
                hourlyRate: hourlyLineItemWrapper.hourlyRate, hours: hourlyLineItemWrapper.hours, description: hourlyLineItemWrapper.description,
                category: hourlyLineItemWrapper.category, id: hourlyLineItemWrapper.id, taxEnabled: hourlyLineItemWrapper.taxable
        ]
        HourlyLineItem.newInstance(parameters)
    }

    static FlatLineItemWrapper convertLineItemToWrapper(FlatLineItem flatLineItem) {
        def fieldData = [ amount: flatLineItem.amount, description: flatLineItem.description,
                category: flatLineItem.category, id: flatLineItem.id, taxable: flatLineItem.taxEnabled]
        return FlatLineItemWrapper.newInstance(fieldData)
    }

    static HourlyLineItemWrapper convertLineItemToWrapper(HourlyLineItem lineItem){
        def fieldData = [hourlyRate : lineItem.hourlyRate, hours : lineItem.hours, amount: lineItem.calculateAmount() ,
                description: lineItem.description, category: lineItem.category, id: lineItem.id, taxable: lineItem.taxEnabled]
        return HourlyLineItemWrapper.newInstance(fieldData)
    }
}
