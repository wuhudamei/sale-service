package com.damei.entity.sale.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.damei.entity.IdEntity;

import java.util.Date;

public class ReportNissin extends IdEntity {
    private static final long serialVersionUID = 2432100011762181636L;
    private String orgName;

    private Long sort;

    private Long orgId;

    private Long dayAssign;

    private Long dayRefusedAgain;

    private Long dayCreate;

    private Long dayReceive;

    private Double score1;

    private Long monthMaturity;

    private Long monthNoProcessing;

    private Double monthProcessingRate;

    private Double score2;

    private Long monthSize;

    private Long monthSolve;

    private Long monthNoSolve;

    private Double monthSolveRate;

    private Double score3;

    private Long visitSatisfied;

    private Long visitCommonly;

    private Long visitUnsatisfied;
    private Double score4;

    private Long visitInvalid;

    private Long visitFail;

    private Long visitCompleted;

    private Long visitUnsatisfiedComplain;
    private Long daySize;

    private Double monthUnsatisfiedComplainRate;

    private Double score5;

    private Double score;

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