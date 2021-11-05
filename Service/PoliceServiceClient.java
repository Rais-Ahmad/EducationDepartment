package com.example.EducationDepartment.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "e-police-system")
public interface PoliceServiceClient {

    @GetMapping("/criminal/check-criminal-record")
    public Boolean checkCriminalRecord(@RequestHeader String cnic);
}
