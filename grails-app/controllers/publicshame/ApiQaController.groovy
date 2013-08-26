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
        def newTeam = new Team(name: 'QA', lookup: 'qa')
        newTeam.save(flush: true)

        render "{ \"status\": \"ok\" }"
    }
}
