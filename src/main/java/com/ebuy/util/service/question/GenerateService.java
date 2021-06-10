package com.ebuy.util.service.question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2018/4/17
 */
public class GenerateService {
//
//    private static final String FEMALE_PREFIX = "QF";
//
//
//    public static void main(String[] args) {
//        String path = "/Users/xin/Downloads/美餐猜猜我是哪里人题库样式0403.xlsx";
//        FileOutputStream fs = null;
//        try {
//            List<SpdAnswer> list = readFile(path);
//
//            System.out.println(list.size());
//
//            File file = new File("/Users/xin/Desktop/qf.txt");
//            if(file.exists()) {
//            	file.delete();
//            }
//            fs = new FileOutputStream(file);
//    			PrintStream p = new PrintStream(fs);
//
//    			for (int i=0; i<list.size(); i++) {
//    				StringBuffer buffer = new StringBuffer();
//    				buffer.append("insert `question_answer`(`question_id`,`answer_id`,`answer_content`,`answer_score`,`show_seq`,`del_flag`,`create_time`,`update_time`) values(");
//    				buffer.append("`").append(list.get(i).getQuestion_id()).append("`,");
//    				buffer.append("`").append(list.get(i).getAnswer_id()).append("`,");
//    				buffer.append("`").append(list.get(i).getAnswer_content()).append("`,");
//    				buffer.append(list.get(i).getAnswer_score()).append(",");
//    				buffer.append(list.get(i).getShow_seq()).append(",");
//    				buffer.append(list.get(i).getDel_flag()).append(",");
////    				buffer.append("`").append(list.get(i).getCreate_time()).append("`,");
//    				buffer.append("`0`,");
//    				buffer.append("`").append(0).append("`);");
//
//    				p.println(buffer.toString());
//    				if ((i+1)%4 == 0) {
//    					p.println();
//    				}
//    			}
//
//
//    			p.flush();
//    			p.close();
//        } catch (Exception e) {
//        		e.printStackTrace();
//        }
//
//    }
//
//    private static List<SpdAnswer> readFile(String path) throws IOException {
//
//        FileInputStream inputStream = new FileInputStream(new File(path));
//        List<List<Object>> list = ExcelUtil.readRowsAutoDetectEndCol(inputStream, true, 1, 1, 10, 14);
//        List<SpdAnswer> spdAnswerList = new ArrayList<SpdAnswer>();
//        int indexBegin = 5;
//        int modNum = 1;
//        long currentTime = System.currentTimeMillis();
//        	for (int i=0; i<list.size(); i++) {
//        		List<Object> record = list.get(i);
//        		int temp = indexBegin + i/3;
//        		String formatStr = getFormatString(temp);
//        		String questionId = FEMALE_PREFIX + formatStr + "0" + (modNum%4);
//        		System.out.println("i["+i+"]indexBegin["+ indexBegin + "]temp["+temp + "]formatStr["+formatStr+"]questionId["+questionId+"]");
//            SpdAnswer spdAnswer = new SpdAnswer();
//            spdAnswer.setQuestion_id(questionId);
//            spdAnswer.setAnswer_id(questionId + "A1");
//            spdAnswer.setAnswer_content((String) record.get(1));
//            String score1 = (String) record.get(2);
//            spdAnswer.setAnswer_score(getFormatInt(score1));
//            spdAnswer.setShow_seq(1);
//            spdAnswer.setDel_flag(0);
//            spdAnswer.setCreate_time(currentTime);
//            spdAnswerList.add(spdAnswer);
//
//            spdAnswer = new SpdAnswer();
//            spdAnswer.setQuestion_id(questionId);
//            spdAnswer.setAnswer_id(questionId + "A2");
//            spdAnswer.setAnswer_content((String) record.get(3));
//            String score2 = (String) record.get(4);
//            spdAnswer.setAnswer_score(getFormatInt(score2));
//            spdAnswer.setShow_seq(2);
//            spdAnswer.setDel_flag(0);
//            spdAnswer.setCreate_time(currentTime);
//            spdAnswerList.add(spdAnswer);
//
//            spdAnswer = new SpdAnswer();
//            spdAnswer.setQuestion_id(questionId);
//            spdAnswer.setAnswer_id(questionId + "A3");
//            spdAnswer.setAnswer_content((String) record.get(5));
//            String score3 = (String) record.get(6);
//            spdAnswer.setAnswer_score(getFormatInt(score3));
//            spdAnswer.setShow_seq(3);
//            spdAnswer.setDel_flag(0);
//            spdAnswer.setCreate_time(currentTime);
//            spdAnswerList.add(spdAnswer);
//
//            spdAnswer = new SpdAnswer();
//            spdAnswer.setQuestion_id(questionId);
//            spdAnswer.setAnswer_id(questionId + "A4");
//            spdAnswer.setAnswer_content((String) record.get(7));
//            String score4 = (String) record.get(8);
//            spdAnswer.setAnswer_score(getFormatInt(score4));
//            spdAnswer.setShow_seq(4);
//            spdAnswer.setDel_flag(0);
//            spdAnswer.setCreate_time(currentTime);
//            spdAnswerList.add(spdAnswer);
//
////            indexBegin ++;
//            if(modNum == 3) {
//            		modNum = 1;
//            } else {
//            		modNum ++;
//            }
//
//
//        }
//
//        inputStream.close();
//
//        return spdAnswerList;
//    }
//
//    private static String getFormatString(int number) {
//        if (number < 10) {
//            return "00" + number;
//        } else if (number < 100) {
//            return "0" + number;
//        }
//
//        return number + "";
//    }
//
//    private static int getFormatInt(String s) {
//    	    s = s.replace(".0", "");
//
//	    	return Integer.parseInt(s);
//    }
}
