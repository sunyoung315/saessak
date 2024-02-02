package com.ssafy.saessak.menu.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.menu.dto.*;
import com.ssafy.saessak.menu.repository.FoodRepository;
import com.ssafy.saessak.menu.repository.MenuRepository;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.s3.S3Upload;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Daycare;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.DaycareRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final DaycareRepository daycareRepository;
    private final S3Upload s3Uploader;
    private final AuthenticationService authenticationService;
    private final KidRepository kidRepository;

    @Transactional
    public void insert(List<MenuRequestDto> menuRequestDtoList) {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        for(MenuRequestDto requestDto : menuRequestDtoList) {
            Daycare daycare = daycareRepository.findById(classroom.getDaycare().getDaycareId())
                    .orElseThrow(() -> new UserException(ExceptionCode.DAYCARE_NOT_FOUND));
            Optional<Menu> result = menuRepository.findByDaycareAndMenuDateAndMenuType(daycare, requestDto.getMenuDate(), requestDto.getMenuType());
            if(result.isPresent()) { // 식단이 존재하는 경우
                Menu menu = result.get();
                Food food = Food.builder()
                        .menu(menu)
                        .foodName(requestDto.getFoodName())
                        .foodAllergy(requestDto.getFoodAllergy())
                        .build();
                foodRepository.save(food);
            } else {
                Menu menu = Menu.builder()
                        .daycare(daycare)
                        .menuDate(requestDto.getMenuDate())
                        .menuType(requestDto.getMenuType())
                        .build();
                Menu savedMenu = menuRepository.save(menu);

                Food food = Food.builder()
                        .menu(savedMenu)
                        .foodName(requestDto.getFoodName())
                        .foodAllergy(requestDto.getFoodAllergy())
                        .build();
                foodRepository.save(food);
            }
        }
    }

    public List<MenuResponseDto> listofTeacher(MenuWeekRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        return list(classroom.getDaycare().getDaycareId(), requestDto);
    }

    public List<MenuResponseDto> listofParent(Long kidId, MenuWeekRequestDto requestDto) {
        Kid kid = kidRepository.findById(kidId).get();
        Classroom classroom = kid.getClassroom();
        return list(classroom.getDaycare().getDaycareId(), requestDto);
    }

    public List<MenuResponseDto> list(Long daycareId, MenuWeekRequestDto requestDto) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if(requestDto.getWeek() != 0) {
            startDate = getStartDateOfWeek(requestDto.getYear(), requestDto.getMonth(), requestDto.getWeek());
            endDate = startDate.plusDays(6); // 일주일 더하기
        }

        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        List<Menu> menuList = menuRepository.findByMenuDateBetween(startDate, endDate);
        System.out.println(startDate+" "+endDate+" "+menuList.size());
        for(Menu menu : menuList) {
            List<Food> foodList = foodRepository.findByMenu(menu);
            List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();
            for(Food food : foodList) {
                FoodResponseDto foodResponseDto = FoodResponseDto.builder()
                        .foodId(food.getFoodId())
                        .foodName(food.getFoodName())
                        .foodAllergy(food.getFoodAllergy())
                        .build();
                foodResponseDtoList.add(foodResponseDto);
            }
            MenuResponseDto menuResponseDto = MenuResponseDto.builder()
                    .menuId(menu.getMenuId())
                    .menuDate(menu.getMenuDate())
                    .menuType(menu.getMenuType())
                    .menuPath(menu.getMenuPath())
                    .build();

            menuResponseDtoList.add(menuResponseDto);
        }

        return menuResponseDtoList;
    }

    // 해당 주차의 시작일을 계산
    private LocalDate getStartDateOfWeek(int year, int month, int week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.get(weekFields.dayOfWeek());
        int daysToAdd = (week-1)*7 - firstDayOfWeek + 1;

        return firstDayOfMonth.plusDays(daysToAdd);
    }

    @Transactional
    public void insertPhoto(Long menuId, MultipartFile menuFile) throws IOException {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.MENU_NOT_FOUND));
        // AWS S3 사진 upload
        String filePath = s3Uploader.upload(menuFile, "menu");
        menu.uploadPhoto(filePath);
    }
}
