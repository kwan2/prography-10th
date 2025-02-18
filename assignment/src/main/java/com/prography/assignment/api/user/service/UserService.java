package com.prography.assignment.api.user.service;

import com.prography.assignment.api.user.dto.UserDto;
import com.prography.assignment.api.common.dto.request.InitRequest;
import com.prography.assignment.api.user.dto.response.UserTotalResponse;
import com.prography.assignment.api.common.manager.FakerExternalManager;
import com.prography.assignment.api.room.manager.RoomManager;
import com.prography.assignment.api.user.manager.UserManager;
import com.prography.assignment.api.user.repository.UserRepository;
import com.prography.assignment.api.user.domain.User;
import com.prography.assignment.api.userRoom.manager.UserRoomManager;
import com.prography.assignment.global.dto.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final FakerExternalManager fakerExternalManager;
    private final UserManager userManager;
    private final RoomManager roomManager;
    private final UserRoomManager userRoomManager;

    @Transactional
    public ApiResponse<Void> init(InitRequest request) {

        // 0. 기존 정보들 모두 삭제
        userRoomManager.deleteAll();
        roomManager.deleteAll();
        userManager.deleteAll();

        // 1. Faker API 유저 정보 조회
        List<User> userInfoList = fakerExternalManager.getFakerInitInfo(request.seed(), request.quantity());

        userInfoList = userInfoList.stream().sorted(Comparator.comparing(User::getFakerId)).toList();

        // 2. 정보 삽입
        userManager.bulkInsertUsers(userInfoList);

        return ApiResponse.success();
    }

    public ApiResponse<UserTotalResponse> getUserTotal(Integer size, Integer page) {

        // 1. 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        // 2. 페이지 정보 가져오기
        Page<User> pageInfo = userManager.findUserPageInfo(pageable);

        if (pageInfo.isEmpty()) {
            return ApiResponse.success(UserTotalResponse.of(0, 0, List.of()));
        }

        // 3. 페이지에 따른 객체 가져오기
        List<User> userVoList = userManager.findAllPagination(pageable);

        // 4. 리턴 객체 생성
        List<UserDto> userInfoList = userVoList.stream()
                .map(UserDto::from)
                .toList();

        UserTotalResponse response = UserTotalResponse.of((int) pageInfo.getTotalElements(), pageInfo.getTotalPages(), userInfoList);

        return ApiResponse.success(response);

    }

}
