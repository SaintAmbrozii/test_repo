package com.example.passangers.service;

import com.example.passangers.domain.Passengers;
import com.example.passangers.repository.PassangersRepo;
import com.example.passangers.util.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassangersService {

    private final PassangersRepo passangersRepo;

    public List<Passengers> findAll() {
        return passangersRepo.findAll();
    }


    @Cacheable(value = "passengers")
    public Page<Passengers> findByFilters(String name, int page, int size, List<String> sortList, String sortOrder,
                                          boolean age, boolean isMan, boolean survived, boolean isNotSiblings) {

        Pageable pageRequest = createPageRequestUsing(page, size);
        Sort sort = Sort.by(createSortOrder(sortList,sortOrder));

        List<Passengers> passangersList = passangersRepo.findAllByName(name,sort);
        if (survived) {
            passangersList.stream()
                    .filter(passangers -> passangers.getSurvived().equals(true))
                    .collect(Collectors.toList());
        }
        if (age) {
            passangersList.stream()
                    .filter(passangers -> passangers.getAge()>16)
                    .collect(Collectors.toList());
        }
        if (isMan) {
            passangersList.stream()
                    .filter(passangers -> passangers.getSex().contains("male"))
                    .collect(Collectors.toList());
        }
        if (isNotSiblings) {
            passangersList.stream()
                    .filter(passangers -> passangers.getChildren_aboard().equals(0))
                    .collect(Collectors.toList());
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), passangersList.size());
        List<Passengers> pageContent = passangersList.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, pageContent.size());

    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    public void save(MultipartFile file) {
        try {
            List<Passengers> tutorials = CsvHelper.CsvToData(file.getInputStream());
            passangersRepo.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
