package com.point_consulting.testindoormap;

import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public class MyStateDrawable extends LayerDrawable {

	private boolean m_pressed = false;
	
    public MyStateDrawable(Drawable[] layers) {
        super(layers);
    }

    private final void myClearColorFilter()
    {
        clearColorFilter();
        invalidateSelf();
    }
    
    private final void mySetColorFilter(int color, PorterDuff.Mode mode)
    {
        setColorFilter(color, mode);
        invalidateSelf();
    }
    
    @Override
    protected boolean onStateChange(int[] states) {
        for (int state : states)
        {
        	if (state == android.R.attr.state_pressed)
            {
            	if (!m_pressed)
            	{
            		m_pressed = true;
            		mySetColorFilter(0x7f000000, Mode.SRC_ATOP);
	                return true;
            	}
            }
            else
            {
            	if (m_pressed)
            	{
            		m_pressed = false;
            		myClearColorFilter();
            	}
            }
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
