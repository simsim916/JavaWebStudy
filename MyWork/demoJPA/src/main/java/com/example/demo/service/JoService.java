package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Jo;

public interface JoService {


    public List<Jo> selectJoList();

    //selectJoDetail
    public Jo selectJoDetail(String jno);

    //insert & update
    public Jo save(Jo entity);

    //**delete
    public void delete(String jno);
}