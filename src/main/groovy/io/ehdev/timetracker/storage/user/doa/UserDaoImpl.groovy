package io.ehdev.timetracker.storage.user.doa

import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.core.user.User
import io.ehdev.timetracker.core.user.UserImpl
import io.ehdev.timetracker.storage.BaseDao
import org.hibernate.Criteria
import org.hibernate.criterion.Example
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Slf4j
class UserDaoImpl extends BaseDao<UserImpl> implements UserDao {

    @Transactional
    public Optional<User> getUserByUUID(String UUID){
        log.debug("Finding user for uuid: {}", UUID)
        return queryForByExample([uuid: UUID])
    }

    @Override
    Optional<UserImpl> getUserFromToken(String token) {
        log.debug("Finding user for token: {}", token)
        return queryForByExample([authToken: token])
    }

    @Override
    Optional<UserImpl> getUserFromToken(OpenIDAuthenticationToken token) {
        return getUserFromToken(token.getIdentityUrl())
    }

    public Optional<UserImpl> queryForByExample(Map userParams) {
        def results = query() { Criteria criteria ->
            criteria.add(Example.create(new UserImpl(userParams)))
        }

        if (results.size() == 1) {
            return Optional.fromNullable(results[0])
        }  else {
            return Optional.absent();
        }
    }

    @Override
    Class<User> getBaseType() {
        return UserImpl.class;
    }
}
