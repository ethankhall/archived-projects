package publicshame

class SinEntry {

    String sinner
    String sin = ""
    String misc = ""

    static constraints = {
        sinner(blank: false)
        group(blank: false)
    }

    static belongsTo = [ group: Group ]
}
