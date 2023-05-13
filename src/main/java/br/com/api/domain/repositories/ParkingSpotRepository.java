package br.com.api.domain.repositories;

import br.com.api.domain.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

        boolean existsByLicensePlateCar(String licensePlateCar);
        boolean existsByParkingSpotNumber(String parkingSpotNumber);
        boolean existsByApartmentAndBlock(String apartment, String block);

}
