package io.ehdev.timetracker.services

import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.services.external.company.ExternalCompany
import io.ehdev.timetracker.storage.company.CompanyDao
import io.ehdev.timetracker.storage.user.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/company")
class CompanyEndpoint {

    @Autowired
    UserDao userDao

    @Autowired
    CompanyDao companyDao

    @RequestMapping(method = RequestMethod.POST)
    public def createNewCompany(@RequestBody ExternalCompany externalCompany,
                                OpenIDAuthenticationToken authentication){
        def userLoggedIn = userDao.getUserFromToken(authentication).get()
        def company = CompanyInteractor.createNewCompany(userLoggedIn, externalCompany.getName())
        return new ExternalCompany(company)
    }

    @RequestMapping(method = RequestMethod.GET, value = '/{uid}')
    public def getCompany(@PathVariable('uid') String uid,
                             OpenIDAuthenticationToken authentication){
        return new Object()
    }

}
