package com.bibliomania.BiblioMania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliomania.BiblioMania.dto.UserStatisticsDTO;
import com.bibliomania.BiblioMania.service.StatisticsService;

@RestController
@RequestMapping("/api/stats")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/user/{userId}")
    public UserStatisticsDTO getStats(@PathVariable Long userId) {
        return statisticsService.getUserStatistics(userId);
    }
}
