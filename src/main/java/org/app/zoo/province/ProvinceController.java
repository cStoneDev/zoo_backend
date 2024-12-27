package org.app.zoo.province;

import org.app.zoo.province.dto.in.ProvinceInputDTO;
import org.app.zoo.province.dto.out.ProvinceOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/province")
@Schema(description = "Province controller class to handle HTTP requests")
public class ProvinceController {
        private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<ProvinceOutputDTO> createProvince(@RequestBody ProvinceInputDTO province) {
        return new ResponseEntity<>(provinceService.createProvince(province), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ProvinceOutputDTO> getAllProvince(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        return provinceService.getAllProvince(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable int id) {
        provinceService.deleteProvince(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceOutputDTO> findProvince(@PathVariable int id) {
        ProvinceOutputDTO province = provinceService.findById(id);
        return ResponseEntity.ok(province);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceOutputDTO> updateProvince(@PathVariable int id, @RequestBody ProvinceInputDTO updatedProvince) {
        ProvinceOutputDTO province = provinceService.updateProvince(id, updatedProvince);
        return ResponseEntity.ok(province);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProvinceOutputDTO>> searchProvince(@RequestBody ProvinceSearchCriteria provinceSearchCriteria) {
        
        return new ResponseEntity<>(provinceService.searchProvince(provinceSearchCriteria) , HttpStatus.OK);
        
    }
}
