package org.app.zoo.dashboard;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController{

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping("/monthEntries")
    public Map<String, Integer> getEntriesPerMonthByYear(
        @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year) {
        return dashboardService.getEntriesPerMonthByYear(year);
    }

    @GetMapping("/topSpecies")
    public Map<String, Integer> getTopSpecies() {
        return dashboardService.getTopSpecies();
    }

    @GetMapping("/activeContracts")
    public Map<String, Integer> getActiveContractsStatistics() {
        return dashboardService.getActiveContractsByProviderType();
    }

    @GetMapping("/monthActivities")
    public Map<String, Integer>getActivitiesPerMonthByYear(
        @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year) {
        return dashboardService.getActivitiesPerMonthByYear(year);
    }
}