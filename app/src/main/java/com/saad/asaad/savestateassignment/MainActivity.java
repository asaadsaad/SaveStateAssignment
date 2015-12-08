package com.saad.asaad.savestateassignment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

public class MainActivity extends Activity
{
    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);

        FrameLayout outerLayout = new FrameLayout(this);
        outerLayout.addView(gv);

        setContentView(outerLayout);
    }
    @Override
    protected void onPause()
    {
        SharedPreferences prefs = getSharedPreferences("MyBallGameData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String tempScoreSuccess=Integer.toString(gv.getScoreSuccess());
        String tempScoreFail=Integer.toString( gv.getScoreFail() );
        editor.putString("ScoreSuccess", tempScoreSuccess);
        editor.putString("ScoreFail", tempScoreFail);
        editor.commit();

        super.onPause();
        gv.killThread();
    }
    @Override
    protected void onResume()
    {
        SharedPreferences prefs = getSharedPreferences("MyBallGameData",MODE_PRIVATE);
        String tempScoreSuccess = prefs.getString("ScoreSuccess", "0");
        String tempScoreFail = prefs.getString("ScoreFail", "0");

        gv.setScoreSuccess(Integer.valueOf(tempScoreSuccess));
        gv.setScoreFail(Integer.valueOf(tempScoreFail));
        super.onResume();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gv.onDestroy();
    }
}