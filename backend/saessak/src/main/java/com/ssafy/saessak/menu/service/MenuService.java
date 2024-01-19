package com.ssafy.saessak.menu.service;

import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.menu.dto.*;
import com.ssafy.saessak.menu.repository.FoodRepository;
import com.ssafy.saessak.menu.repository.MenuRepository;
import com.ssafy.saessak.user.domain.Daycare;
import com.ssafy.saessak.user.repository.DaycareRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    public void insert(List<MenuRequestDto> menuRequestDtoList) {
        for(MenuRequestDto requestDto : menuRequestDtoList) {
            Daycare daycare = daycareRepository.findById(requestDto.getDaycareId()).get();
            Optional<Menu> result = menuRepository.findByDaycareAndMenuDateAndMenuType(daycare, requestDto.getMenuDate(), requestDto.getMenuType());
            if(result.isPresent()) { // 식단이 존재하는 경우
                Menu menu = result.get();

                Food food = Food.builder()
                        .menu(menu)
                        .foodName(requestDto.getFoodName())
                        .allergyNum(requestDto.getAllergyNum())
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
                        .allergyNum(requestDto.getAllergyNum())
                        .build();
                foodRepository.save(food);
            }
        }
    }

    public List<MenuResponseDto> list(MenuWeekRequestDto requestDto) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if(requestDto.getWeek() != 0) {
            startDate = getStartDateOfWeek(requestDto.getYear(), requestDto.getMonth(), requestDto.getWeek());
            endDate = startDate.plusDays(6); // 일주일 더하기
        }

        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        List<Menu> menuList = menuRepository.findByMenuDateBetween(startDate, endDate);
        for(Menu menu : menuList) {
            List<Food> foodList = foodRepository.findByMenu(menu);
            List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();
            for(Food food : foodList) {
                FoodResponseDto foodResponseDto = FoodResponseDto.builder()
                        .foodId(food.getFoodId())
                        .foodName(food.getFoodName())
                        .allergyNum(food.getAllergyNum())
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

    public void insertPhoto(MenuPhotoRequestDto requestDto) {
        Menu menu = menuRepository.findById(requestDto.getMenuId()).get();
        // AWS 사진 upload 구현해야함
    }
}
