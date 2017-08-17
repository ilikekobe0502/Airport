package com.point_consulting.testindoormap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SelDestActivity extends Activity {

    private View m_selected[] = new View[2];

    final public void onDone(View view)
    {
        final Intent intent = new Intent();
        final String [] sel = new String[m_selected.length];
        int i = 0;
        for (View v: m_selected)
        {
            if (v != null)
            {
                Button b = (Button)v;
                sel[i] = b.getText().toString();
            }
            ++i;
        }
        intent.putExtra(MyAppUtils.s_selDestResult, sel);
        setResult(RESULT_OK, intent);
        finish();
    }

    final private void select(int tableIndex, View v)
    {
        final View s = m_selected[tableIndex];
        if (s == v) {
            return;
        }
        if (s != null) {
            s.getBackground().setColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY);
        }
        m_selected[tableIndex] = v;
        if (v != null) {
            v.getBackground().setColorFilter(0xff7fff7f, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_dest);

        final String[] featureNames = getIntent().getStringArrayExtra(MyAppUtils.s_extra_featuresSet);

        final LinearLayout[] tables = {(LinearLayout)findViewById(R.id.table_1), (LinearLayout)findViewById(R.id.table_2)};
        int tableIndex = 0;
        for (LinearLayout tl: tables) {
            for (String featureName : featureNames) {
                Button b = new Button(this);
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                b.setLayoutParams(lp);
                b.setText(featureName);
                final int ti = tableIndex;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select(ti, v);
                    }
                });
                tl.addView(b);
            }
            ++tableIndex;
        }
    }
}
