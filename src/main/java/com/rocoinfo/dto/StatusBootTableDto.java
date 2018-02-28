package com.rocoinfo.dto;

import com.rocoinfo.Constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

/**
 * 根据boottable列表格式
 * @author zzc
 * @param <T>
 * @description 2016-12-12
 */
public class StatusBootTableDto<T> {

    /**
     * 状态码
     */
    protected String code;

    /**
     * 信息
     */
    protected String message;

    /**
     * 携带数据
     */
    protected Map<String,Object> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String,Object> getData() {
        return data;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
    }
    
    /**
     * 添加数据
     * @param key
     * @param object
     * @return
     */
    public Map<String,Object> addNewData(String key, Object object){
        if(data == null){
        	data = new HashMap<String, Object>();
        }
        data.put(key,object);
        return data;
    }

	public static StatusBootTableDto<String> buildSuccessStatusDto() {
        return buildSuccessStatusDto("Success");
    }

    public static <E> StatusBootTableDto<E> buildDataSuccessStatusDto() {
        return buildDataSuccessStatusDto("Success");
    }


    public static <E> StatusBootTableDto<E> buildDataSuccessStatusDto(String message) {
        StatusBootTableDto<E> dto = new StatusBootTableDto<E>();
        dto.code = Constants.RESP_STATUS_CODE_SUCCESS;
        dto.message = message;
        return dto;
    }


    public static StatusBootTableDto<String> buildSuccessStatusDto(String message) {
        StatusBootTableDto<String> dto = new StatusBootTableDto<String>();
        dto.code = Constants.RESP_STATUS_CODE_SUCCESS;
        dto.message = message;
        return dto;
    }

    /**
     * 设置返回信息,返回code
     * @param message 返回信息
     * @param code    返回code
     * @return
     */
    public static StatusBootTableDto<String> buildSuccessStatusDtoWithCode(String message, String code) {
        StatusBootTableDto<String> dto = new StatusBootTableDto<String>();
        dto.code = code;
        dto.message = message;
        return dto;
    }

    public static StatusBootTableDto<String> buildFailureStatusDto() {
        return buildFailureStatusDto("Failure");
    }

    public static StatusBootTableDto<String> buildFailureStatusDto(String message) {
        StatusBootTableDto<String> dto = new StatusBootTableDto<String>();
        dto.code = Constants.RESP_STATUS_CODE_FAIL;
        dto.message = message;
        return dto;
    }


    public static <E> StatusBootTableDto<E> buildDataFailureStatusDto() {
        return buildDataFailureStatusDto("Failure");
    }


    public static <E> StatusBootTableDto<E> buildDataFailureStatusDto(String message) {
        StatusBootTableDto<E> dto = new StatusBootTableDto<E>();
        dto.code = Constants.RESP_STATUS_CODE_FAIL;
        dto.message = message;
        return dto;
    }

    public boolean isSuccess() {
        return StringUtils.equals(this.code, Constants.RESP_STATUS_CODE_SUCCESS);
    }

    public static StatusBootTableDto<String> buildStatusDtoWithCode(String code, String message) {
        StatusBootTableDto<String> dto = new StatusBootTableDto<>();
        dto.code = code;
        dto.message = message;
        return dto;
    }

    public static <E> StatusBootTableDto<E> buildDataSuccessStatusDto(String message, Page<E> data,Long totle) {
        StatusBootTableDto<E> dto = buildDataSuccessStatusDto(message);
        dto.addNewData("rows", data.getContent());
        dto.addNewData("total", data.getTotalElements());
        return dto;
    }

    /**
     * 直接传入分页类
     * @param data 
     * @return
     */
    public static <E> StatusBootTableDto<E> buildDataSuccessStatusDto(Page<E> data) {
        StatusBootTableDto<E> dto = buildDataSuccessStatusDto();
        dto.addNewData("rows", data.getContent());
        dto.addNewData("total", data.getTotalElements());
        return dto;
    }
    
    /**
     * 传入数据类及分页信息的总行数
     * @param data
     * @return
     */
    public static <E> StatusBootTableDto<E> buildDataSuccessStatusDto(E data,Long total) {
        StatusBootTableDto<E> dto = buildDataSuccessStatusDto();
        dto.addNewData("rows", data);
        dto.addNewData("total", total);
        return dto;
    }
}