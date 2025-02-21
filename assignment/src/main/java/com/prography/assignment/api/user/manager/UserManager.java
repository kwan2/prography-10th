package com.prography.assignment.api.user.manager;

import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.user.repository.UserRepository;
import com.prography.assignment.global.exception.CommonException;
import com.prography.assignment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    public User findUser(Integer userId) {
        return userRepository.findByUserIsNotDeleted(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.SERVER_ERROR));
    }

    public void deleteAll(){
        userRepository.deleteAllBySoftDelete(LocalDateTime.now());
    }

    public void bulkInsertUsers(List<User> users) {
        List<User> result = userRepository.saveAll(users);

        if (CollectionUtils.isEmpty(result)) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    public Page<User> findUserPageInfo(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findAllPagination(Pageable pageable) {
        return userRepository.findAllOrderByWithPagination(pageable);
    }
}
