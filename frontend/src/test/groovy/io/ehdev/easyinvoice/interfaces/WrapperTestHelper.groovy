package io.ehdev.easyinvoice.interfaces

import com.fasterxml.jackson.databind.ObjectMapper

class WrapperTestHelper {

    public static def createJsonConverter(def contactInfo, Class clazz) {
        ObjectMapper om = ObjectMapper.newInstance()
        StringWriter sw = StringWriter.newInstance()
        om.writeValue(sw, contactInfo)
        sw.close()
        om.readValue(sw.toString(), clazz)
    }
}
