package com.yunju.app.entity;

/**
 * @author: captain
 * Time:  2018/5/7 0007
 * Describe:
 */
public class LoginResponse {

    /**
     * status : 1
     * msg : success
     * data : {"id":"a10aAwwOqGn35nlP/Xg05twV+/y/Q0J2bxdgMNdEtzPFMJEi2EX8sSNNagnUsP4","grade":0,"grade_remark":"普通会员","mobile":"15580993896"}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : a10aAwwOqGn35nlP/Xg05twV+/y/Q0J2bxdgMNdEtzPFMJEi2EX8sSNNagnUsP4
         * grade : 0
         * grade_remark : 普通会员
         * mobile : 15580993896
         */

        private String id;
        private int grade;
        private String grade_remark;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getGrade_remark() {
            return grade_remark;
        }

        public void setGrade_remark(String grade_remark) {
            this.grade_remark = grade_remark;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
