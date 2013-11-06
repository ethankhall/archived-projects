package io.ehdev.easyinvoice.config.rest.invoice

class CreateResponse {
    String status

    CreateResponse(String status){
        this.status = status
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }
}
