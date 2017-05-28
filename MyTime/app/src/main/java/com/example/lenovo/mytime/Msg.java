package com.example.lenovo.mytime;



public class Msg {
    public static final int TYPE_RECEIVED =0 ;
    public static final int TYPE_SENT=1;
    private String content;
    private int type;
    public Msg(String content, int type){
        this.content=content;
        this.type=type;
    }
 //表示消息内容
    public String getContent(){
        return content;
    }
//表示消息类型，收到或者发出消息
    public int getType(){
        return type;
    }


}
