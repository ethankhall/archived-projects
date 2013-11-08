package io.ehdev.easyinvoice.config
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.accessor.LineItemAccessor
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import io.ehdev.easyinvoice.lineitem.LineItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@Component
@RequestMapping("lineitem")
@Slf4j
class LineItemConfig {

    @Autowired
    LineItemAccessor lineItemAccessor

    @RequestMapping(method = RequestMethod.GET)
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
    public @ResponseBody Map createLineItem(@RequestBody HourlyLineItem lineItem){
        log.info(JsonOutput.toJson(lineItem))
        lineItemAccessor.save(lineItem)

        return [status: "created", id: lineItem.id]
    }

    @RequestMapping(method = RequestMethod.GET, value = "template")
    public @ResponseBody LineItem getTemplate(){
        return new HourlyLineItem();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void sendNotFound(){

    }

    class NotFoundException extends RuntimeException {
        ;
    }
}
