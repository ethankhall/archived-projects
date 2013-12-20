package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.UserNotAuthorizedException
import io.ehdev.timetracker.core.user.User

class ProjectInteractor {

    Project project
    User user

    public void changeName(String newName){
        if(project.getPermissions().canUserWrite(user)){
            project.setName(newName)
        } else {
            throw new UserNotAuthorizedException();
        }
    }


}
