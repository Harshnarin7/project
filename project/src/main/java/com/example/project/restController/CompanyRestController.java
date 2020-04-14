package com.example.project.restcontroller;

import com.example.project.ApiUrls;
import com.example.project.entity.CompanyEntity;
import com.example.project.entity.ResultEntity;
import com.example.project.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.COMPANY)
public class CompanyRestController {

    public CompanyService companyService;

    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping()
    public ResponseEntity<?> saveCompanies(@Valid @RequestBody List<CompanyEntity> companyEntityList){
     return ResponseEntity.ok(companyService.saveCompanies(companyEntityList));
    }

    @GetMapping()
    public ResponseEntity<?> getCompanies(){
        List<CompanyEntity> companyEntities = companyService.getCompanies();
        if(companyEntities==null)
            return ResponseEntity.status(404).body("there are no companies");
        return ResponseEntity.ok(companyEntities);
    }

    @GetMapping(ApiUrls.COMPANY_ID)
    public ResponseEntity<?> getCompany(@PathVariable(name = "companyId") Long id){
        CompanyEntity companyEntity =companyService.getCompany(id);
        if(companyEntity==null)
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.ok(companyEntity);
    }

    @PutMapping(ApiUrls.COMPANY_ID)
    public ResponseEntity<?> updateCompany(@PathVariable(name = "companyId") Long id,@Valid @RequestBody CompanyEntity companyEntity){
        CompanyEntity companyEntity1 = companyService.updateCompany(id,companyEntity);
        if(companyEntity1 == null)
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.ok(companyEntity1);
    }

    @DeleteMapping(ApiUrls.COMPANY_ID)
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "companyId") Long id){
        if(!companyService.deleteCompany(id))
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ApiUrls.AlGORITHM+ApiUrls.THRESHOLD)
    public ResponseEntity<?> implementAlgorithm(@PathVariable(name = "threshold") Long threshold){
       List<ResultEntity> roomEntities= companyService.implementAlgorithm(threshold);
        return ResponseEntity.ok(roomEntities);
    }

}
