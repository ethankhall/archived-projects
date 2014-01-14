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
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

@TestFor(ApiSinEntryController)
@Mock([SinEntry, Team])
class ApiSinEntryControllerTests {

    ApiSinEntryController sinController

    @Before
    void setUp() {
        sinController = new ApiSinEntryController()
    }

    @Test
    void testAddSin() {
        def group = createGroup("123", "1")

        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject != null
        assert jsonObject.count == 1
    }

    @Test
    void testGetCount() {
        def group = createGroup("1234", "12")
        createSin("Ethan", "", group)
        createSin("Ethan1", "", group)

        sinController.request.contentType = 'type/json'
        sinController.params.teamId = group.lookup
        sinController.getAllEntries()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject.size == 2
    }

    @Test
    void testPasswordProtectedBadPassword() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\", \"passphrase\" : \"somethingwrong\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        assert sinController.response.getStatus() == 403
    }

    @Test
    void testPasswordProtectedGoodPassword() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\", \"passphrase\" : \"someone\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        assert sinController.response.getStatus() == 200
    }

    @Test
    void testThatNameIsPassedBackProperly() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.params.teamId = group.lookup
        sinController.getAllEntries()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject.name == group.name
    }

    @Test
    void testThatInvalidTeamFailsWith404() {
        sinController.request.contentType = 'type/json'
        sinController.params.teamId = '1'
        sinController.getAllEntries()
        assert sinController.response.getStatus() ==  404
    }

    @Test
    void testThatWillReturn400WhenNoSinnerIsGiven() {

        def group = createGroup("123", "1")

        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"foobar\" : \"Ethan\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject != null
        assert jsonObject.error == "Invalid input"
        assert sinController.response.getStatus() == 400
    }

    private void createSin(name, sinName, group){
        def sin = new SinEntry()
        sin.sinner = name
        sin.sin = sinName
        sin.team = group
        sin.save(failOnError: true)
    }

    private Team createGroup(name, lookup, password = "") {
        def group = new Team()
        group.name = name
        group.lookup = lookup
        group.passphrase = password
        group.save(failOnError: true)
        return group
    }
}
