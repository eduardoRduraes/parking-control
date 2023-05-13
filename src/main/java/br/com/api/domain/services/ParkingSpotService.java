package br.com.api.domain.services;

import br.com.api.adapter.dtos.ParkingSpotDTO;
import br.com.api.domain.models.ParkingSpotModel;
import br.com.api.domain.repositories.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository repository;

    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public ParkingSpotModel create(ParkingSpotDTO parkingDTO){
        var parkingentity = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingDTO, parkingentity);
        parkingentity.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return this.repository.save(parkingentity);
    }

    @Transactional
    public ParkingSpotModel update(UUID id, ParkingSpotDTO parking){
        Optional<ParkingSpotModel> enitty = this.repository.findById(id);
        if(enitty.isPresent()){
            BeanUtils.copyProperties(parking,enitty.get());
            return this.repository.save(enitty.get());
        }
        return null;
    }

    public Page<ParkingSpotModel> getAll(Pageable pageable){
        return this.repository.findAll(pageable);
    }

    public ParkingSpotModel findById(UUID id){
        Optional<ParkingSpotModel> enitty = this.repository.findById(id);
        return enitty.orElse(null);
    }

    @Transactional
    public void delete(UUID id){
        Optional<ParkingSpotModel> entity = this.repository.findById(id);
        entity.ifPresent(this.repository::delete);
    }

    public boolean existsByLicencePlateCar(String licensePlacaCar){
        return this.repository.existsByLicensePlateCar(licensePlacaCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber){
        return this.repository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    public boolean existsByApartmentAndBlock(String apartment, String block){
        return this.repository.existsByApartmentAndBlock(apartment,block);
    }
}
