package com.example.passangers.controller;

import com.example.passangers.domain.Passengers;
import com.example.passangers.service.PassangersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/passangers")
@RequiredArgsConstructor
public class PassengersContoller {

    private final PassangersService passangersService;




    @GetMapping
    public Page<Passengers> getPassangersAndSorted(@RequestParam(defaultValue = "",required = false) String nameFilter,
                                                   @RequestParam(defaultValue = "0",required = false) int page,
                                                   @RequestParam(defaultValue = "50",required = false) int size,
                                                   @RequestParam(defaultValue = "",required = false) List<String> sortList,
                                                   @RequestParam(defaultValue = "DESC",required = false) Sort.Direction sortOrder,
                                                   @RequestParam(defaultValue = "false",required = false,name = "age") boolean age,
                                                   @RequestParam(defaultValue = "false",required = false, name = "isMan") boolean isMan,
                                                   @RequestParam(defaultValue = "false",required = false,name = "survived") boolean survived,
                                                   @RequestParam(defaultValue = "false",required = false,name = "isNotSiblings") boolean isNotSiblings)
    {
        return  passangersService.findByFilters(nameFilter,page,size,sortList,sortOrder.toString(),age,isMan,survived, isNotSiblings);

    }




    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

            try {
                passangersService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
    }




}
