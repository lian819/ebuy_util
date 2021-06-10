package com.ebuy.util.service.question;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2018/4/17
 */
public class SpdAnswer {

//    insert `question_answer`(`question_id`,`answer_id`,`answer_content`,`answer_score`,`show_seq`,`del_flag`,`create_time`,`update_time`) values('QW00403','QW00403A4','我可请不出80天的长假',3,4,0,'0','0');
    private String question_id = "";
    private String answer_id = "";
    private String answer_content = "";
    private int answer_score = 0;
    private int show_seq = 0;
    private int del_flag = 0;
    private long create_time = 0L;
    private long update_time = 0L;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public int getAnswer_score() {
        return answer_score;
    }

    public void setAnswer_score(int answer_score) {
        this.answer_score = answer_score;
    }

    public int getShow_seq() {
        return show_seq;
    }

    public void setShow_seq(int show_seq) {
        this.show_seq = show_seq;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
}
