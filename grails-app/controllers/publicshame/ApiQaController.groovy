package publicshame

class ApiQaController {

    def seed() {
        def team = Team.findWhere(name: 'QA')
        if (null != team) {
                team.entries.each {
                it.delete()
            }
            team.delete(flush: true)
        }
        def qaTeam = new Team(name: 'QA', lookup: 'qa')
        qaTeam.save(flush: true)
        for (int i in 1..37) {
            def sin = new SinEntry(sinner: Integer.toString(i), sin: "sin number ${i}", team: qaTeam)
            sin.save(flush: true)
        }

        render "{ \"status\": \"ok\" }"
    }
}
