package com.rocoinfo.entity.sale.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你智装 日清报表实体</dd>
 * <dd>@date：2017/10/12  11:33</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
public class ReportNissin extends IdEntity {
    private static final long serialVersionUID = 2432100011762181636L;
    /**
     * 组织名字
     */
    private String orgName;

    /**
     * 综合排序
     */
    private Long sort;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 当天客管待分配数量
     */
    private Long dayAssign;

    /**
     * 当天的申诉无效的数量
     */
    private Long dayRefusedAgain;

    /**
     * 当天未派单的数量
     */
    private Long dayCreate;

    /**
     * 当天已接收的数量
     */
    private Long dayReceive;

    /**
     * 得分1
     */
    private Double score1;

    /**
     * 当月已到期的数量
     */
    private Long monthMaturity;

    /**
     * 当月已到期未解决的数量
     */
    private Long monthNoProcessing;

    /**
     * 当月已到期解决率
     */
    private Double monthProcessingRate;

    /**
     * 得分2
     */
    private Double score2;

    /**
     * 当月工单数量
     */
    private Long monthSize;

    /**
     * 当月解决的数量
     */
    private Long monthSolve;

    /**
     * 当月未解决的数量
     */
    private Long monthNoSolve;

    /**
     * 当月总解决率
     */
    private Double monthSolveRate;

    /**
     * 得分3
     */
    private Double score3;

    /**
     * 回访满意数量
     */
    private Long visitSatisfied;

    /**
     * 回访一般满意数量
     */
    private Long visitCommonly;

    /**
     * 回访不满意数量
     */
    private Long visitUnsatisfied;

    /**
     * 得分4
     */
    private Double score4;

    /**
     * 回访暂无评价数量
     */
    private Long visitInvalid;

    /**
     * 拒绝回访数量
     */
    private Long visitFail;

    /**
     * 未回访数量
     */
    private Long visitCompleted;

    /**
     * 回访未执行数量
     */
    private Long visitUnsatisfiedComplain;
    /**
     * 当天工单的数量
     */
    private Long daySize;

    /**
     * 未执行率
     */
    private Double monthUnsatisfiedComplainRate;

    /**
     * 得分5
     */
    private Double score5;

    /**
     * 综合得分
     */
    private Double score;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createDate;


    public Long getDaySize() {
        return daySize;
    }

    public void setDaySize(Long daySize) {
        this.daySize = daySize;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDayAssign() {
        return dayAssign;
    }

    public void setDayAssign(Long dayAssign) {
        this.dayAssign = dayAssign;
    }

    public Long getDayRefusedAgain() {
        return dayRefusedAgain;
    }

    public void setDayRefusedAgain(Long dayRefusedAgain) {
        this.dayRefusedAgain = dayRefusedAgain;
    }

    public Long getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(Long dayCreate) {
        this.dayCreate = dayCreate;
    }

    public Long getDayReceive() {
        return dayReceive;
    }

    public void setDayReceive(Long dayReceive) {
        this.dayReceive = dayReceive;
    }

    public Double getScore1() {
        return score1;
    }

    public void setScore1(Double score1) {
        this.score1 = score1;
    }

    public Long getMonthMaturity() {
        return monthMaturity;
    }

    public void setMonthMaturity(Long monthMaturity) {
        this.monthMaturity = monthMaturity;
    }

    public Long getMonthNoProcessing() {
        return monthNoProcessing;
    }

    public void setMonthNoProcessing(Long monthNoProcessing) {
        this.monthNoProcessing = monthNoProcessing;
    }

    public Double getMonthProcessingRate() {
        return monthProcessingRate;
    }

    public void setMonthProcessingRate(Double monthProcessingRate) {
        this.monthProcessingRate = monthProcessingRate;
    }

    public Double getScore2() {
        return score2;
    }

    public void setScore2(Double score2) {
        this.score2 = score2;
    }

    public Long getMonthSize() {
        return monthSize;
    }

    public void setMonthSize(Long monthSize) {
        this.monthSize = monthSize;
    }

    public Long getMonthSolve() {
        return monthSolve;
    }

    public void setMonthSolve(Long monthSolve) {
        this.monthSolve = monthSolve;
    }

    public Long getMonthNoSolve() {
        return monthNoSolve;
    }

    public void setMonthNoSolve(Long monthNoSolve) {
        this.monthNoSolve = monthNoSolve;
    }

    public Double getMonthSolveRate() {
        return monthSolveRate;
    }

    public void setMonthSolveRate(Double monthSolveRate) {
        this.monthSolveRate = monthSolveRate;
    }

    public Double getScore3() {
        return score3;
    }

    public void setScore3(Double score3) {
        this.score3 = score3;
    }

    public Long getVisitSatisfied() {
        return visitSatisfied;
    }

    public void setVisitSatisfied(Long visitSatisfied) {
        this.visitSatisfied = visitSatisfied;
    }

    public Long getVisitCommonly() {
        return visitCommonly;
    }

    public void setVisitCommonly(Long visitCommonly) {
        this.visitCommonly = visitCommonly;
    }

    public Long getVisitUnsatisfied() {
        return visitUnsatisfied;
    }

    public void setVisitUnsatisfied(Long visitUnsatisfied) {
        this.visitUnsatisfied = visitUnsatisfied;
    }

    public Double getScore4() {
        return score4;
    }

    public void setScore4(Double score4) {
        this.score4 = score4;
    }

    public Long getVisitInvalid() {
        return visitInvalid;
    }

    public void setVisitInvalid(Long visitInvalid) {
        this.visitInvalid = visitInvalid;
    }

    public Long getVisitFail() {
        return visitFail;
    }

    public void setVisitFail(Long visitFail) {
        this.visitFail = visitFail;
    }

    public Long getVisitCompleted() {
        return visitCompleted;
    }

    public void setVisitCompleted(Long visitCompleted) {
        this.visitCompleted = visitCompleted;
    }

    public Long getVisitUnsatisfiedComplain() {
        return visitUnsatisfiedComplain;
    }

    public void setVisitUnsatisfiedComplain(Long visitUnsatisfiedComplain) {
        this.visitUnsatisfiedComplain = visitUnsatisfiedComplain;
    }

    public Double getMonthUnsatisfiedComplainRate() {
        return monthUnsatisfiedComplainRate;
    }

    public void setMonthUnsatisfiedComplainRate(Double monthUnsatisfiedComplainRate) {
        this.monthUnsatisfiedComplainRate = monthUnsatisfiedComplainRate;
    }

    public Double getScore5() {
        return score5;
    }

    public void setScore5(Double score5) {
        this.score5 = score5;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}