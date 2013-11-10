package io.ehdev.easyinvoice.services
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.accessor.LineItemAccessor
import io.ehdev.easyinvoice.interfaces.LineItemWrapper
import io.ehdev.easyinvoice.lineitem.FlatLineItem
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import io.ehdev.easyinvoice.lineitem.LineItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@Component
@RequestMapping("lineitem")
@Slf4j
class LineItemService {

    @Autowired
    LineItemAccessor lineItemAccessor

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody LineItem getLineItem(@RequestParam String id){
        def lineItem = lineItemAccessor.get(id)
        if(null == lineItem) {
            throw new NotFoundException()
        } else {
            return lineItem
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Map createLineItem(@RequestBody LineItemWrapper lineItem){
        log.info(JsonOutput.toJson(lineItem))
        //lineItemAccessor.save(lineItem)

        return [status: "created", id: lineItem.id]
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public def updatePost(@PathVariable String id, @RequestBody LineItemWrapper wrapper){
        log.info("Updating post $id to ${JsonOutput.toJson(wrapper)}")
        [ updated: "Updated lineItem"]
    }

    @RequestMapping(value = "{id}",  method = RequestMethod.DELETE)
    @ResponseBody
    public def deletePost(@PathVariable String id) {
        log.info("Deleting $id")
        [ status: "Deleted"]
    }

    @RequestMapping(method = RequestMethod.GET, value = "template/{type}")
    public @ResponseBody LineItem[] getTemplate(@PathVariable String type){
        if(type.equalsIgnoreCase("hourly"))
            return [new HourlyLineItem(), new HourlyLineItem()]
        if(type.equalsIgnoreCase("flat"))
            return [new FlatLineItem(), new FlatLineItem()]

        return [ new HourlyLineItem(), new FlatLineItem() ]
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void sendNotFound(){

    }

    class NotFoundException extends RuntimeException {
        ;
    }
}
