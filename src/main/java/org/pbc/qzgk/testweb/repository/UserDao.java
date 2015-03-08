package org.pbc.qzgk.testweb.repository;

import org.pbc.qzgk.testweb.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
