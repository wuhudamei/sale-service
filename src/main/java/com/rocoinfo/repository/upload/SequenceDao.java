package com.rocoinfo.repository.upload;


import com.rocoinfo.service.upload.SequenceService.SequenceTable;
import org.springframework.stereotype.Repository;

/**
 * @author zhangmin
 */
@Repository
public interface SequenceDao {

    /**
     * 返回 sequence的当前值
     */
    public Integer getCurVal(SequenceTable seqTab);

    /**
     * 增加sequence值
     */
    public void next(SequenceTable seqTab);

    void insert(String seqTab);
}
