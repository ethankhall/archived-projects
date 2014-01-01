package io.ehdev.timetracker.services

import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/company")
class CompanyEndpoint {

    @RequestMapping(method = RequestMethod.POST)
    public def createNewCompany(OpenIDAuthenticationToken authentication){
        return "Congratulations, you would have created a company here"
    }

    @RequestMapping(method = RequestMethod.GET, value = '/{uid}')
    public def updateCompany(@PathVariable('uid') String uid, OpenIDAuthenticationToken authentication){
        return 'Updated Project'
    }

}
