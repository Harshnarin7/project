package com.example.project.restcontroller;

import com.example.project.ApiUrls;
import com.example.project.entity.ResultEntity;
import com.example.project.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.RESULT)
public class ResultRestController {

    private ResultService resultService;

    public ResultRestController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping()
    public ResponseEntity<?> saveCompanies(@Valid @RequestBody List<ResultEntity> resultEnities){
        return ResponseEntity.ok(resultService.saveCompanies(resultEnities));
    }

    @GetMapping()
    public ResponseEntity<?> getCompanies(){
        List<ResultEntity> companyEntities = resultService.getCompanies();
        if(companyEntities==null)
            return ResponseEntity.status(404).body("there are no companies");
        return ResponseEntity.ok(companyEntities);
    }

    @GetMapping(ApiUrls.RESULT_ID)
    public ResponseEntity<?> getCompany(@PathVariable(name = "companyId") Long id){
        ResultEntity resultEntity =resultService.getCompany(id);
        if(resultEntity==null)
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.ok(resultEntity);
    }

    @PutMapping(ApiUrls.RESULT_ID)
    public ResponseEntity<?> updateCompany(@PathVariable(name = "companyId") Long id,@Valid @RequestBody ResultEntity resultEntity){
        ResultEntity resultEntity1 = resultService.updateCompany(id,resultEntity);
        if(resultEntity1 == null)
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.ok(resultEntity1);
    }

    @DeleteMapping(ApiUrls.RESULT_ID)
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "companyId") Long id){
        if(!resultService.deleteCompany(id))
            return ResponseEntity.status(404).body("company with Id: "+ id + " not found.");
        return ResponseEntity.noContent().build();
    }
}
