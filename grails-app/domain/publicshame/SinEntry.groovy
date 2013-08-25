package publicshame

class SinEntry {

    String sinner
    String sin = ""
    String misc = ""

    static constraints = {
        sinner(blank: false, nullable: false)
        group(blank: false)
    }

    static belongsTo = [ group: Team ]
}
