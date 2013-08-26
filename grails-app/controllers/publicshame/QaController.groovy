package publicshame

class QaController {

    def seed() {
        SinEntry.findAllWhere([ team: 'qa' ]).deleteAll(flush: true)
        Team.findWhere(team: 'qa').delete(flush: true)
        def newTeam = new Team(name: 'QA', lookup: 'qa')
        newTeam.save(flush: true)
    }
}
