package publicshame

class SinEntry {

    String sinner
    String sin = ""
    String misc = ""
    String pathToFile = ""
    Date createdDate = new Date()

    static constraints = {
        sinner(blank: false, nullable: false)
        team(blank: false)
    }

    static belongsTo = [ team: Team ]
}
