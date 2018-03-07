package cn.damei.service.sale.dict;
import cn.damei.entity.sale.dict.DameiDictionary;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.repository.sale.dict.DameiDictionaryDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DameiDictionaryService extends CrudService<DameiDictionaryDao,DameiDictionary> {

    /**
     * 添加或修改数据字典
     * @param dameiDictionary
     * @return
     */
    public Object addOrUpdate(DameiDictionary dameiDictionary) {
        if (dameiDictionary.getId() == null) {
            if(dameiDictionary.getParentCode()==null){
                dameiDictionary.setParentCode(0);
            }
            //给默认值
            dameiDictionary.setStatus(0);
            this.entityDao.insert(dameiDictionary);
            return StatusDto.buildDataSuccessStatusDto("添加成功");
        }

        this.entityDao.update(dameiDictionary);
        return StatusDto.buildDataSuccessStatusDto("修改成功");
    }

    /**
     * 获取父类代码
     * @return
     */
    public List<DameiDictionary> getNode(Long type){
        return this.entityDao.getNode(type);
    }

    /**
     * 通过名称获取字典
     * @param name
     * @return
     */
   public DameiDictionary getByName(String name){
       return this.entityDao.getByName(name);
   }

    /**
     * 通过类型获取字典内容
     * @return
     */
    public List<DameiDictionary> getByType(Integer parentCode, Integer type){
        return this.entityDao.getByType(parentCode,type);
    }

    public List<DameiDictionary> findByCompanyLiableDep(Integer liableDep){
        return this.entityDao.findByCompanyLiableDep(liableDep);
    }
}