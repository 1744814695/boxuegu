package boxuegu.com.boxuegu.bean;

public class ExercisesDetailBean {
    public int id;
    public String subject;
    public String a,b,c,d;
    public int answer;//正确答案：1：表示正确答案是A
    public int selected;//用户选择的答案:0：表示用户还没有为该选择题作答；1:表示用户选择的A
}