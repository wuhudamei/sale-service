package com.damei.repository.upload;


import com.damei.service.upload.SequenceService;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceDao {

    public Integer getCurVal(SequenceService.SequenceTable seqTab);

    public void next(SequenceService.SequenceTable seqTab);

    void insert(String seqTab);
}
