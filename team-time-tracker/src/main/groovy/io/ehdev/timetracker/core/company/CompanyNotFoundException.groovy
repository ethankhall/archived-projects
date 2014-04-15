package io.ehdev.timetracker.core.company

class CompanyNotFoundException extends RuntimeException{

    CompanyNotFoundException(String id) {
        super("Could not find company with id: " + id)
    }
}
