package com.buddyrental.Controllers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.buddyrental.DTO.VehicleDTO;
import com.buddyrental.Services.VehicleService.VehicleService;
import com.buddyrental.enums.Fueltype;
import com.buddyrental.enums.TransmissionType;
import com.buddyrental.enums.VehicleType;
import java.util.UUID;
import org.springframework.data.domain.Page;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController{


    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService=vehicleService;
    
    }
@PostMapping("/addVehicle")
    public VehicleDTO addVehicle(@RequestBody VehicleDTO vehicleDTO){
      return vehicleService.addVehicle(vehicleDTO);
    }
    @DeleteMapping("/{vehicleId}")
    public void deleteVehicle(@PathVariable UUID vehicleId){
        vehicleService.deleteVehicle(vehicleId);
    }
   @GetMapping("/city/{city}/available/{isAvailable}")
   public Page<VehicleDTO>getVehicleByCityAndAvailability(@PathVariable String city,@PathVariable boolean isAvailable,@RequestParam int page,@RequestParam int size){
    return vehicleService.getVehicleByCityAndAvailability(city,isAvailable,page,size);
   }
   @GetMapping("/type/{type}/available/{isAvailable}/minPrice/{minPrice}/maxPrice/{maxPrice}/transmissionType/{transmissionType}/fueltype/{fueltype}")
   public Page<VehicleDTO>getVehicleByFilter(@PathVariable VehicleType type,@PathVariable boolean isAvailable,@PathVariable int minPrice,@PathVariable int maxPrice,@PathVariable TransmissionType transmissionType,@PathVariable Fueltype fueltype,@RequestParam int page,@RequestParam int size){
return vehicleService.getVehicleByFilters(type,isAvailable,minPrice,maxPrice,transmissionType,fueltype,page,size);
   }
   @GetMapping("/fueltype/{fuelType}")
public Page<VehicleDTO>getVehicleByFuelType(@PathVariable Fueltype fuelType,@RequestParam int page,@RequestParam int size){
    return vehicleService.getVehicleByFuelType(fuelType,page,size);
}
@GetMapping("/{vehicleId}")
public Optional<VehicleDTO>getVehicleById(@PathVariable UUID vehicleId){
    return vehicleService.getVehicleById(vehicleId);
}
@GetMapping("/transmissionType/{transmissionType}")
public Page<VehicleDTO>getVehicleByTransmissionType(@PathVariable TransmissionType transmissionType,@RequestParam int page,@RequestParam int size){
    return vehicleService.getVehicleByTransmissionType(transmissionType,page,size);
}
@GetMapping("/type/{vehicleType}")
public Page<VehicleDTO>getVehicleByType(@PathVariable VehicleType vehicleType,@RequestParam int page,@RequestParam int size){
    return vehicleService.getVehicleByType(vehicleType,page,size);
}
@GetMapping("/ownerId/{ownerId}")
public List<VehicleDTO>getVehiclesByOwnerId(@PathVariable UUID ownerId){
    return vehicleService.getVehiclesByOwnerId(ownerId);
}
@PutMapping("/update/{vehicleId}")
public VehicleDTO updateVehicle(@PathVariable UUID vehicleId,@RequestBody VehicleDTO vehicleDTO){
    return vehicleService.updateVehicle(vehicleId, vehicleDTO);
}
}