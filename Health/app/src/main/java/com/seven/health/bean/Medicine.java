package com.seven.health.bean;

public class Medicine {
    private String drug_id;
    private String drug_name;
    private String indication;
    private String drug_usage;
    private String reaction;
    private String special_crowd;
    private String attention;

    public Medicine(){

    }

    public Medicine(String drug_id, String drug_name, String indication, String drug_usage, String reaction, String special_crowd, String attention) {
        this.drug_id = drug_id;
        this.drug_name = drug_name;
        this.indication = indication;
        this.drug_usage = drug_usage;
        this.reaction = reaction;
        this.special_crowd = special_crowd;
        this.attention = attention;
    }

    public String getDrug_name(){
        return drug_name;
    }

    public String showDrug_name(){
        return "【药品名称】\n  "+drug_name;
    }

    public void setDrug_name(String drug_name){
        this.drug_name = drug_name;
    }

    public String getDrug_id(){
        return drug_id;
    }


    public void setDrug_id(String drug_id){
        this.drug_id = drug_id;
    }

    public  String getIndication(){
        return indication;
    }

    public void setIndication(String indication){
        this.indication = indication;
    }

    public String showIndication(){
        return "【适应症】\n  "+indication;
    }

    public String getDrug_usage(){
        return this.drug_usage;
    }

    public void setDrug_usage(String drug_usage){
        this.drug_usage = drug_usage;
    }

    public String showDrug_usage(){
        return "【用法用量】\n  "+drug_usage;
    }

    public String getReaction(){
        return this.reaction;
    }

    public void setReaction(String reaction){
        this.reaction = reaction;
    }

    public String showReaction(){
        return "【不良反应】\n  "+reaction;
    }

    public String getSpecial_crowd(){
        return this.special_crowd;
    }

    public  void setSpecial_crowd(String special_crowd){
        this.special_crowd = special_crowd;
    }

    public String showSpecial_crowd(){
        return "【特殊人群】\n  "+special_crowd;
    }

    public String getAttention(){
        return this.attention;
    }

    public void setAttention(String attention){
        this.attention = attention;
    }

    public String showAttention(){
        return "【注意事项】\n  "+attention;
    }

    @Override
    public String toString() {
        return "【药品名称】\n " +drug_name+
                "\n\n【适应症】\n "+indication+
                "\n\n【用法用量】\n "+drug_usage+
                "\n\n【不良反应】\n "+reaction+
                "\n\n【特殊人群】\n "+special_crowd+
                "\n\n【注意事项】\n "+attention;
    }
}
