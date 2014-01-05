package io.ehdev.timetracker.services
import io.ehdev.timetracker.core.company.CompanyInteractor
import io.ehdev.timetracker.core.user.UserNotAuthorizedToAdminException
import io.ehdev.timetracker.services.external.company.ExternalCompany
import io.ehdev.timetracker.storage.company.CompanyDao
import io.ehdev.timetracker.storage.permission.UserCompanyPermissionsDao
import io.ehdev.timetracker.storage.project.ProjectDao
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

    @Autowired
    UserCompanyPermissionsDao userCompanyPermissionsDao

    @Autowired
    ProjectDao projectDao

    @RequestMapping(method = RequestMethod.POST)
    public def createNewCompany(@RequestBody ExternalCompany externalCompany,
                                OpenIDAuthenticationToken authentication){
        def userLoggedIn = userDao.getUserFromToken(authentication)
        def company = CompanyInteractor.createNewCompany(userLoggedIn, externalCompany.getName())
        companyDao.save(company)
        return new ExternalCompany(company)
    }

    @RequestMapping(method = RequestMethod.GET, value = '/{uid}')
    public def getCompany(@PathVariable('uid') String uid,
                             OpenIDAuthenticationToken authentication){
        def company = companyDao.getByUuid(uid)
        def user = userDao.getUserFromToken(authentication)
        if(company.findPermissionForUser(user)){
            return new ExternalCompany(company)
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public def getAllCompaniesForUser(OpenIDAuthenticationToken authentication){
        def user = userDao.getUserFromToken(authentication)
        def companiesFound = userCompanyPermissionsDao.getCompaniesAvailableToUser(user)

        return companiesFound.collect {
            new ExternalCompany(it)
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = '/{uid}')
    public void updateCompany(@PathVariable('uid') String uid,
                              @RequestBody ExternalCompany externalCompany,
                              OpenIDAuthenticationToken authentication){
        def company = companyDao.getByUuid(uid)
        def user = userDao.getUserFromToken(authentication)
        def companyInteractor = new CompanyInteractor(user, company)

        companyInteractor.setName(externalCompany.getName())
        setupPermissions(externalCompany, companyInteractor)

        companyDao.save(company)
    }

    def setupPermissions(ExternalCompany company, CompanyInteractor interactor) {
        interactor.clearPermissions()
        company.admin.each {
            interactor.addSetUserAsAdmin(userDao.getUserByUUID(it))
        }
        company.write.each {
            interactor.addSetUserAsWrite(userDao.getUserByUUID(it))
        }
        company.read.each {
            interactor.addSetUserAsRead(userDao.getUserByUUID(it))
        }
    }

    @RequestMapping(value ='/{uid}', method = RequestMethod.DELETE)
    public def deleteCompany(@PathVariable String uid,
                                 OpenIDAuthenticationToken authentication){
        def company = companyDao.getByUuid(uid)
        def user = userDao.getUserFromToken(authentication)
        def project = projectDao.findProjectsForCompany(company)
        if(company.findPermissionForUser(user).canUserAdmin()){
            project.each {
                projectDao.delete(it)
            }
            companyDao.delete(company)
        } else {
            throw new UserNotAuthorizedToAdminException(user)
        }
    }
}
