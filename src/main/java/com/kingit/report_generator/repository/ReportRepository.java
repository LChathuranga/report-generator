package com.kingit.report_generator.repository;

import com.kingit.report_generator.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, String> {}
