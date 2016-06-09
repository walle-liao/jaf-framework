package com.jaf.framework.poi.excel.imports.dto;

import com.jaf.framework.poi.excel.ColumnHead;

/**
 * 试题导入模板
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2015年10月3日
 * @since 1.0
 */
public class QuestionImportDTO {
	
	@ColumnHead(title = "章节编号")
	private String chapterNo;
	
	@ColumnHead(title = "知识点编号")
	private String knowledgeNo;
	
	@ColumnHead(title = "题型")
	private String questionType;
	
	@ColumnHead(title = "题目")
	private String questionContent;
	
	@ColumnHead(title = "选项A")
	private String answerItemA;
	
	@ColumnHead(title = "选项B")
	private String answerItemB;
	
	@ColumnHead(title = "选项C")
	private String answerItemC;
	
	@ColumnHead(title = "选项D")
	private String answerItemD;
	
	@ColumnHead(title = "正确答案")
	private String rightAnswer;
	
	@ColumnHead(title = "难度系数")
	private String difficultyLevel;
	
	@ColumnHead(title = "解析")
	private String questionAnalysis;
	
	public String getChapterNo() {
		return chapterNo;
	}
	
	public void setChapterNo(String chapterNo) {
		this.chapterNo = chapterNo;
	}
	
	public String getKnowledgeNo() {
		return knowledgeNo;
	}
	
	public void setKnowledgeNo(String knowledgeNo) {
		this.knowledgeNo = knowledgeNo;
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	public String getQuestionContent() {
		return questionContent;
	}
	
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	
	public String getAnswerItemA() {
		return answerItemA;
	}
	
	public void setAnswerItemA(String answerItemA) {
		this.answerItemA = answerItemA;
	}
	
	public String getAnswerItemB() {
		return answerItemB;
	}
	
	public void setAnswerItemB(String answerItemB) {
		this.answerItemB = answerItemB;
	}
	
	public String getAnswerItemC() {
		return answerItemC;
	}
	
	public void setAnswerItemC(String answerItemC) {
		this.answerItemC = answerItemC;
	}
	
	public String getAnswerItemD() {
		return answerItemD;
	}
	
	public void setAnswerItemD(String answerItemD) {
		this.answerItemD = answerItemD;
	}
	
	public String getRightAnswer() {
		return rightAnswer;
	}
	
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	
	public String getQuestionAnalysis() {
		return questionAnalysis;
	}
	
	public void setQuestionAnalysis(String questionAnalysis) {
		this.questionAnalysis = questionAnalysis;
	}
	
}
