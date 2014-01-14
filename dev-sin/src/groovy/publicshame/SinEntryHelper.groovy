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

class SinEntryHelper {

    public static final int MAX_STEP = 15

    /**
     * This method will take a team and create a list of all sins
     *
     * @param teamUsed
     * @return list of all sins assigned no this team
     *
     */
    public static List<SinEntry> generateSinnerList(Team teamUsed, int startPosition) {
        def sinnerList = []
        SinEntry.findAllByTeam(teamUsed,  [max: MAX_STEP, order: 'desc', sort:'id', offset: startPosition]).each {
            sinnerList << createEntryMap(it)
        }

        return sinnerList
    }

    static def createEntryMap(SinEntry sinUsed) {
        def resultMap = [
                sinner: sinUsed.sinner,
                sin: sinUsed.sin,
                id: sinUsed.id,
        ]

        if (sinUsed.misc)
            resultMap <<  ["misc", sinUsed.misc]
        resultMap
    }
}
