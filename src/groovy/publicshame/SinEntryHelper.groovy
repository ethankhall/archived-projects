package publicshame

import publicshame.SinEntry
import publicshame.Team

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
        SinEntry.findAllByTeam(teamUsed,  [max: MAX_STEP, offset: startPosition]).each {
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
