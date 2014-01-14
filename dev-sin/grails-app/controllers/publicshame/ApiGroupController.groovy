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
import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils

class ApiGroupController {

    def getAllGroups(){
        def mapHash = []
        Team.findAll().each {
            mapHash.add([
                    key: it.lookup,
                    name: it.name,
                    count: SinEntry.countByTeam(it)
            ])
        }

        render mapHash as JSON
    }

    def postGroup() {
        def group = new Team(request.JSON)
        group.lookup = createRandomString()

        if(group.save()) {
            def resp = [
                    key: group.lookup,
                    name: group.name
            ]
            render resp as JSON
        } else {
            render group.errors
        }
    }

    def createRandomString() {
        def randomString = RandomStringUtils.random(5, true, true)
        while ( 0 != Team.findAllWhere(lookup: randomString).size() ) {
            randomString = RandomStringUtils.random(5, true, true)
        }

        return randomString

    }
}
