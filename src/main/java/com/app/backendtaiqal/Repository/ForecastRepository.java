package com.app.backendtaiqal.Repository;

import com.app.backendtaiqal.Models.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<Forecast, Long> {}
