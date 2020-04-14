package com.example.project.service;

import com.example.project.entity.ResultEntity;
import com.example.project.repository.ResultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {


    private ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Transactional
    public List<ResultEntity> saveCompanies(List<ResultEntity> resultEntities) {
        return resultRepository.saveAll(resultEntities);
    }

    public List<ResultEntity> getCompanies() {
        List<ResultEntity> resultEntities = resultRepository.findAll();
        if (resultEntities.isEmpty())
            return null;
        return resultEntities;
    }

    public ResultEntity getCompany(Long id) {
        Optional<ResultEntity> resultEntity = resultRepository.findById(id);
        if (!resultEntity.isPresent())
            return null;
        return resultEntity.get();
    }

    @Transactional
    public ResultEntity updateCompany(Long id, ResultEntity resultEntity) {
        Optional<ResultEntity> resultEntity1 = resultRepository.findById(id);
        if (!resultEntity1.isPresent())
            return null;
        ResultEntity mresultEntity = resultEntity1.get();
        mresultEntity.setName(resultEntity.getName());
        return mresultEntity;
    }

    @Transactional
    public Boolean deleteCompany(Long id) {
        if (!resultRepository.existsById(id))
            return false;
        resultRepository.deleteById(id);
        return true;
    }
}
