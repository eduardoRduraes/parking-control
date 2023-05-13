package br.com.api.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="tb-parking-spot")
@Data
@NoArgsConstructor
public class ParkingSpotModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name="parking_spot_number", unique = true, length = 10)
    private String parkingSpotNumber;

    @Column(nullable = false, name="license_plate", unique = true, length = 7)
    private String licensePlateCar;

    @Column(nullable = false, name="brand_car",length = 70)
    private String brandCar;

    @Column(nullable = false,name="model_car", length = 70)
    private String modelCar;

    @Column(nullable = false, name="color_car", length = 70)
    private String colorCar;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, name = "responsible_name", length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;

    @CreationTimestamp()
    private Timestamp createdAt;

    @UpdateTimestamp()
    private Timestamp updatedAt;
}
