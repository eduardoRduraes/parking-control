package br.com.api.adapter.controllers;

import br.com.api.adapter.dtos.ParkingSpotDTO;
import br.com.api.domain.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/park-spot")
public class ParkingSpotController {

    final ParkingSpotService service;
    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ParkingSpotDTO parking){
        if(service.existsByLicencePlateCar(parking.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Confict: License Plate Car is already in user!");
        }
        if(service.existsByParkingSpotNumber(parking.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Confict: Parking Spot is already in user!");
        }
        if(service.existsByApartmentAndBlock(parking.getApartment(), parking.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Confict: Apartment and Block is already in user!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(parking));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable @Valid UUID id, @RequestBody @Valid ParkingSpotDTO parking){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.update(id, parking));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") @Valid UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findById(id));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll(pageable));
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Valid UUID id){
        this.service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Parking delete success!");
    }
}
