/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Ethan Hall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/

package publicshame

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

@TestFor(ApiGroupController)
@Mock([Team, SinEntry])
class ApiGroupControllerTests {

    ApiGroupController controller
    def jsonBuilder

    @Before
    void setUp() {
        controller = new ApiGroupController()
        jsonBuilder = new JsonBuilder()
    }

    @Test
    void testAddingGroupController() {
        createGroupReturnId(controller)
    }

    @Test
    void testGettingTwoGroupControllers() {
        def idsCreated = [createGroupReturnId(controller), createGroupReturnId(controller)]
        controller.getAllGroups()
        def jsonObject = new JsonSlurper().parseText(controller.response.contentAsString)

        assert (jsonObject as ArrayList).size() == 2
        for(entry in jsonObject){
            assert entry.key in idsCreated
        }
    }

    public static String createGroupReturnId(ApiGroupController controller) {
        controller.request.contentType = 'type/json'
        controller.request.content = "{\"name\":\"${System.currentTimeMillis()}\"}" as byte[]
        controller.postGroup()

        def jsonObj = new JsonSlurper().parseText((String)controller.response.contentAsString)
        controller.response.reset()

        assert jsonObj.key != null
        return jsonObj.key
    }
}

