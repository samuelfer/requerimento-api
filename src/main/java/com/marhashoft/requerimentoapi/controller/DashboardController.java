package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.DashboardCount;
import com.marhashoft.requerimentoapi.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/counts")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardCount> counts() {
        return new ResponseEntity(dashboardService.counts(), HttpStatus.OK);
    }
}
