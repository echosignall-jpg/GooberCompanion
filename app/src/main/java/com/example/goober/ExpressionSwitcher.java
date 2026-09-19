package com.example.goober;

import android.widget.ImageView;

public final class ExpressionSwitcher {
    private ExpressionSwitcher(){}
    public static void show(ImageView view,String text){ String s=text==null?"":text.toLowerCase(); int id=R.drawable.expr_calm; if(s.contains("happy")||s.contains("joy")||s.contains("great"))id=R.drawable.expr_joy; else if(s.contains("sad")||s.contains("sorry"))id=R.drawable.expr_shy; else if(s.contains("confus")||s.contains("what"))id=R.drawable.expr_confused; else if(s.contains("jealous")||s.contains("angry")||s.contains("fight"))id=R.drawable.expr_salute; else if(s.contains("hi")||s.contains("hello"))id=R.drawable.expr_smile; view.setImageResource(id); }
}
