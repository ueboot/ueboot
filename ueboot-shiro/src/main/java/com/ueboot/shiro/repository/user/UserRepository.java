package com.ueboot.shiro.repository.user;

import com.ueboot.core.repository.BaseRepository;
import com.ueboot.shiro.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 这个类里面使用spring data jpa 方式实现数据库的CRUD
 * Created on 2018-08-14 10:47:54
 *
 * @author yangkui
 * @author update by felix
 * @since 3.0.0 by ueboot-generator
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserBaseRepository {

    /**
     * 根据用户名查找用户
     *
     * @param userName 用户名
     * @return 用户，不存在则返回空对象
     */
    User findByUserName(String userName);
}
