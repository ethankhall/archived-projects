package io.ehdev.timetracker.storage.user

import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.core.user.UserNotFoundException
import io.ehdev.timetracker.storage.BaseDao
import io.ehdev.timetracker.storage.UUIDSearcher
import org.hibernate.Criteria
import org.hibernate.criterion.Example
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
@Slf4j
class UserDaoImpl extends BaseDao<UserImpl> implements UserDao {

    @Delegate
    UUIDSearcher<UserImpl> searcher = new UUIDSearcher<UserImpl>(this)

    @Transactional
    public UserImpl getUserByUUID(String UUID){
        log.debug("Finding user for uuid: {}", UUID)
        return queryForByExample([uuid: UUID])[0]
    }

    @Override
    UserImpl getUserFromToken(String token) {
        log.debug("Finding user for token: {}", token)
        def optionalUser = queryForByExample([authToken: token])
        return optionalUser[0]
    }

    @Override
    UserImpl getUserFromToken(OpenIDAuthenticationToken token) {
        return getUserFromToken(token.getIdentityUrl())
    }

    public List<UserImpl> queryForByExample(Map userParams) {
        def results = query() { Criteria criteria ->
            criteria.add(Example.create(new UserImpl(userParams)))
        }

        if (results.size() != 0) {
            return results
        }  else {
            throw new UserNotFoundException('map', userParams.toString())
        }
    }

    @Override
    Class<User> getBaseType() {
        return UserImpl.class;
    }
}
