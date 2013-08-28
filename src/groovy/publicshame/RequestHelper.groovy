package publicshame

class RequestHelper {

    static def getBaseURL(request) {
        def responseUrl = "http://" + request.getServerName()
        if(80 != request.getServerPort()){
            responseUrl += ":${request.getServerPort()}"
        }
        responseUrl += "${request.forwardURI}"
        return responseUrl
    }
}
