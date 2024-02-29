package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Jo;
import com.example.demo.repository.JoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoServiceImpl implements JoService{

    //@Autowired
    private final JoRepository repository;
    
    //**selectJoList
    public List<Jo> selectJoList(){
        return repository.findAll();
    }
    
    //**selectJoDetail
    public Jo selectJoDetail(String jno) {
        Optional<Jo> result = repository.findById(jno); 
        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
    
    //**insert & update
    public Jo save(Jo entity) {
        return repository.save(entity);
    }
    
    
//    **delete
    public void delete(String jno) {
        repository.deleteById(jno);
    }
}