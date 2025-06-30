package com.baumstaemme.backend.game.report;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepo reportRepo;

    public ReportService(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }

    public Report save(Report report) {
        return reportRepo.save(report);
    }
}
