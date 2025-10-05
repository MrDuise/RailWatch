// LocationServiceTest.java
package com.railway.hazard_reporting_system.service;

import com.railway.hazard_reporting_system.entity.core.RailSegment;
import com.railway.hazard_reporting_system.entity.core.RailroadCompany;
import com.railway.hazard_reporting_system.exceptions.RailSegmentNotFoundException;
import com.railway.hazard_reporting_system.exceptions.RepositoryException;
import com.railway.hazard_reporting_system.repository.RailSegmentRepository;
import com.railway.hazard_reporting_system.util.GeospatialCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.LineString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private RailSegmentRepository railSegmentRepository;

    @Mock
    private GeospatialCalculator geoCalculator;

    @InjectMocks
    private LocationService locationService;

    private RailSegment mockSegment;
    private RailroadCompany mockCompany;
    private LineString mockGeometry;

    @BeforeEach
    void setUp() {
        // Set up mock objects
        mockCompany = new RailroadCompany("BNSF Railway", "BNSF");
        mockCompany.setId(1L);

        mockSegment = new RailSegment();
        mockSegment.setId(1L);
        mockSegment.setName("Test Segment");
        mockSegment.setSegmentCode("TEST-001");
        mockSegment.setRailroadCompany(mockCompany);

        // Mock geometry (you'll need to create this properly in real tests)
        mockGeometry = mock(LineString.class);
        mockSegment.setGeometry(mockGeometry);
    }

    // ========================================================================
    // findNearestRailSegment Tests
    // ========================================================================

    @Test
    void findNearestRailSegment_HappyPath_SegmentFound() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));

        // Act
        Optional<RailSegment> result = locationService.findNearestRailSegment(lat, lng);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockSegment, result.get());
        assertEquals("TEST-001", result.get().getSegmentCode());
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void findNearestRailSegment_HappyPath_NoSegmentFound() {
        // Arrange
        Double lat = 0.0;
        Double lng = 0.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.empty());

        // Act
        Optional<RailSegment> result = locationService.findNearestRailSegment(lat, lng);

        // Assert
        assertTrue(result.isEmpty());
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void findNearestRailSegment_ErrorPath_DataAccessException() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenThrow(new DataAccessException("Database connection failed") {});

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class, () -> {
            locationService.findNearestRailSegment(lat, lng);
        });

        assertTrue(exception.getMessage().contains("Failed to query nearest rail segment"));
        assertTrue(exception.getMessage().contains("lat=47.8107"));
        assertTrue(exception.getMessage().contains("lng=-122.3774"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void findNearestRailSegment_ErrorPath_NullCoordinates() {
        // Arrange
        Double lat = null;
        Double lng = null;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenThrow(new DataAccessException("Null coordinates") {});

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class, () -> {
            locationService.findNearestRailSegment(lat, lng);
        });

        assertTrue(exception.getMessage().contains("Failed to query nearest rail segment"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    // ========================================================================
    // determineOwningRailroad Tests
    // ========================================================================

    @Test
    void determineOwningRailroad_HappyPath_CompanyFound() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));

        // Act
        String result = locationService.determineOwningRailroad(lat, lng);

        // Assert
        assertEquals("BNSF", result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void determineOwningRailroad_HappyPath_DifferentCompany() {
        // Arrange
        Double lat = 41.8781;
        Double lng = -87.6298;
        RailroadCompany upCompany = new RailroadCompany("Union Pacific", "UP");
        mockSegment.setRailroadCompany(upCompany);
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));

        // Act
        String result = locationService.determineOwningRailroad(lat, lng);

        // Assert
        assertEquals("UP", result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void determineOwningRailroad_ErrorPath_NoSegmentFound() {
        // Arrange
        Double lat = 0.0;
        Double lng = 0.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.empty());

        // Act & Assert
        RailSegmentNotFoundException exception = assertThrows(RailSegmentNotFoundException.class, () -> {
            locationService.determineOwningRailroad(lat, lng);
        });

        assertTrue(exception.getMessage().contains("No rail segment found near"));
        assertTrue(exception.getMessage().contains("lat=0.0"));
        assertTrue(exception.getMessage().contains("lng=0.0"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    @Test
    void determineOwningRailroad_ErrorPath_CompanyIsNull() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        mockSegment.setRailroadCompany(null);
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));

        // Act & Assert
        RailSegmentNotFoundException exception = assertThrows(RailSegmentNotFoundException.class, () -> {
            locationService.determineOwningRailroad(lat, lng);
        });

        assertTrue(exception.getMessage().contains("railroad company missing"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
    }

    // ========================================================================
    // isLocationNearRail Tests
    // ========================================================================

    @Test
    void isLocationNearRail_HappyPath_WithinThreshold() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        Double threshold = 500.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenReturn(300.0);

        // Act
        Boolean result = locationService.isLocationNearRail(lat, lng, threshold);

        // Assert
        assertTrue(result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }

    @Test
    void isLocationNearRail_HappyPath_OutsideThreshold() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        Double threshold = 100.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenReturn(500.0);

        // Act
        Boolean result = locationService.isLocationNearRail(lat, lng, threshold);

        // Assert
        assertFalse(result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }

    @Test
    void isLocationNearRail_ErrorPath_NoSegmentFound() {
        // Arrange
        Double lat = 0.0;
        Double lng = 0.0;
        Double threshold = 500.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.empty());

        // Act & Assert
        RailSegmentNotFoundException exception = assertThrows(RailSegmentNotFoundException.class, () -> {
            locationService.isLocationNearRail(lat, lng, threshold);
        });

        assertTrue(exception.getMessage().contains("No rail segment found near"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, never()).calculateDistance(any(), any(), any());
    }

    @Test
    void isLocationNearRail_ErrorPath_CalculationException() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        Double threshold = 500.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenThrow(new RuntimeException("Geometry calculation failed"));

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class, () -> {
            locationService.isLocationNearRail(lat, lng, threshold);
        });

        assertTrue(exception.getMessage().contains("Failed to calculate distance"));
        assertTrue(exception.getMessage().contains("segment id=1"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }

    // ========================================================================
    // calculateDistanceToRail Tests
    // ========================================================================

    @Test
    void calculateDistanceToRail_HappyPath_CloseDistance() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenReturn(150.5);

        // Act
        Double result = locationService.calculateDistanceToRail(lat, lng);

        // Assert
        assertEquals(150.5, result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }

    @Test
    void calculateDistanceToRail_HappyPath_FarDistance() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenReturn(1500.0);

        // Act
        Double result = locationService.calculateDistanceToRail(lat, lng);

        // Assert
        assertEquals(1500.0, result);
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }

    @Test
    void calculateDistanceToRail_ErrorPath_NoSegmentFound() {
        // Arrange
        Double lat = 0.0;
        Double lng = 0.0;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.empty());

        // Act & Assert
        RailSegmentNotFoundException exception = assertThrows(RailSegmentNotFoundException.class, () -> {
            locationService.calculateDistanceToRail(lat, lng);
        });

        assertTrue(exception.getMessage().contains("No rail segment found near"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, never()).calculateDistance(any(), any(), any());
    }

    @Test
    void calculateDistanceToRail_ErrorPath_CalculationFails() {
        // Arrange
        Double lat = 47.8107;
        Double lng = -122.3774;
        when(railSegmentRepository.findNearestSegment(lat, lng, 1609.34))
                .thenReturn(Optional.of(mockSegment));
        when(geoCalculator.calculateDistance(lat, lng, mockGeometry))
                .thenThrow(new IllegalArgumentException("Invalid geometry"));

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class, () -> {
            locationService.calculateDistanceToRail(lat, lng);
        });

        assertTrue(exception.getMessage().contains("Failed to calculate distance"));
        assertTrue(exception.getMessage().contains("lat=47.8107"));
        assertTrue(exception.getMessage().contains("lng=-122.3774"));
        verify(railSegmentRepository, times(1)).findNearestSegment(lat, lng, 1609.34);
        verify(geoCalculator, times(1)).calculateDistance(lat, lng, mockGeometry);
    }
}