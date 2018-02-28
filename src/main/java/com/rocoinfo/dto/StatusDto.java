package com.rocoinfo.dto;

import org.apache.commons.lang3.StringUtils;

import com.rocoinfo.Constants;

public class StatusDto<T> {

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
    protected T data;
    
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

	public static StatusDto<String> buildSuccessStatusDto() {
        return buildSuccessStatusDto("Success");
    }

    public static <E> StatusDto<E> buildDataSuccessStatusDto() {
        return buildDataSuccessStatusDto("Success");
    }


    public static <E> StatusDto<E> buildDataSuccessStatusDto(String message) {
        StatusDto<E> dto = new StatusDto<E>();
        dto.code = Constants.RESP_STATUS_CODE_SUCCESS;
        dto.message = message;
        return dto;
    }


    public static StatusDto<String> buildSuccessStatusDto(String message) {
        StatusDto<String> dto = new StatusDto<String>();
        dto.code = Constants.RESP_STATUS_CODE_SUCCESS;
        dto.message = message;
        return dto;
    }

    /**
     * 鑷畾涔夋垚鍔熺殑code
     *
     * @param message 淇℃伅
     * @param code    code
     * @return
     */
    public static StatusDto<String> buildSuccessStatusDtoWithCode(String message, String code) {
        StatusDto<String> dto = new StatusDto<String>();
        dto.code = code;
        dto.message = message;
        return dto;
    }

    public static StatusDto<String> buildFailureStatusDto() {
        return buildFailureStatusDto("Failure");
    }

    public static StatusDto<String> buildFailureStatusDto(String message) {
        StatusDto<String> dto = new StatusDto<String>();
        dto.code = Constants.RESP_STATUS_CODE_FAIL;
        dto.message = message;
        return dto;
    }


    public static <E> StatusDto<E> buildDataFailureStatusDto() {
        return buildDataFailureStatusDto("Failure");
    }


    public static <E> StatusDto<E> buildDataFailureStatusDto(String message) {
        StatusDto<E> dto = new StatusDto<E>();
        dto.code = Constants.RESP_STATUS_CODE_FAIL;
        dto.message = message;
        return dto;
    }

    public boolean isSuccess() {
        return StringUtils.equals(this.code, Constants.RESP_STATUS_CODE_SUCCESS);
    }

    public static StatusDto<String> buildStatusDtoWithCode(String code, String message) {
        StatusDto<String> dto = new StatusDto<>();
        dto.code = code;
        dto.message = message;
        return dto;
    }

    public static <E> StatusDto<E> buildDataSuccessStatusDto(String message, E data) {
        StatusDto<E> dto = buildDataSuccessStatusDto(message);
        dto.data = data;
        return dto;
    }

    public static <E> StatusDto<E> buildDataSuccessStatusDto(E data) {
        StatusDto<E> dto = buildDataSuccessStatusDto();
        dto.data = data;
        return dto;
    }
}