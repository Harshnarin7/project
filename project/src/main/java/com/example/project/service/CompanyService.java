package com.example.project.service;

import com.example.project.EditDistance;
import com.example.project.entity.CompanyEntity;
import com.example.project.entity.ResultEntity;
import com.example.project.repository.CompanyRepository;
import com.example.project.repository.ResultRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    private ResultRepository resultRepository;

    public CompanyService(CompanyRepository companyRepository, ResultRepository resultRepository) {
        this.companyRepository = companyRepository;
        this.resultRepository = resultRepository;
    }


    @Transactional
    public List<CompanyEntity> saveCompanies(List<CompanyEntity> companyEntities) {
        return companyRepository.saveAll(companyEntities);
    }

    public List<CompanyEntity> getCompanies() {
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        if (companyEntities.isEmpty())
            return null;
        return companyEntities;
    }

    public CompanyEntity getCompany(Long id) {
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (!companyEntity.isPresent())
            return null;
        return companyEntity.get();
    }

    @Transactional
    public CompanyEntity updateCompany(Long id, CompanyEntity companyEntity) {
        Optional<CompanyEntity> companyEntity1 = companyRepository.findById(id);
        if (!companyEntity1.isPresent())
            return null;
        CompanyEntity mcompanyEntity = companyEntity1.get();
        mcompanyEntity.setName(companyEntity.getName());
        return mcompanyEntity;
    }

    @Transactional
    public List<ResultEntity> implementAlgorithm(Long threshold) {
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        List<String> companyList = new ArrayList<>();
        Set<ResultEntity> mresultList = new HashSet<>();
        companyEntities.forEach(companyEntity -> {
            companyList.add(companyEntity.getName());
        });
        String string1, string2, string3;
        ResultEntity resultEntity;
        for (int i = 0; i < companyList.size(); i++) {
            for (int j = 0; j < companyEntities.size(); j++) {
                string1 = companyList.get(i);
                string2 = companyList.get(j);
                if (string1.length() > string2.length()) {
                    string3 = string1;
                    string1 = string2;
                    string2 = string3;
                }
                int len = EditDistance.editDistDP(string1, string2, string1.length(), string2.length());

                if (len <= threshold) {
                    resultEntity = new ResultEntity();
                    resultEntity.setName(string2);
                    mresultList.add(resultEntity);
                } else {
                    resultEntity = new ResultEntity();
                    resultEntity.setName(string1);
                    mresultList.add(resultEntity);
                    resultEntity = new ResultEntity();
                    resultEntity.setName(string2);
                    mresultList.add(resultEntity);
                }
            }
        }
        return resultRepository.saveAll(mresultList);
    }


    @Transactional
    public Boolean deleteCompany(Long id) {
        if (!companyRepository.existsById(id))
            return false;
        companyRepository.deleteById(id);
        return true;
    }

}
