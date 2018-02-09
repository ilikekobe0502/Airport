package com.whatsmedia.ttia.utility;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.whatsmedia.ttia.R;

/**
 * 會因應寬度，動態改變字體大小的TextView
 *
 */
public class FontFitTextView extends android.support.v7.widget.AppCompatTextView{
	/**
	 * 最小字體尺寸
	 */
	private float minTextSize;
	/**
	 * 字體每次縮小的值
	 */
	private float decreaseValue;
	/**
	 * 原始字體大小
	 */
	private float originalSize;
	/**
	 * 是否調整尺寸flag
	 */
	private boolean needResize;
	private boolean needSpecilHightResize;
	private Context context;
	private boolean resize = true;
	private Paint paint;

	public FontFitTextView(Context context) {
		this(context, null);
	}

	public FontFitTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FontFitTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontFitTextView);

		minTextSize = a.getDimensionPixelSize(R.styleable.FontFitTextView_minTextSize,
				(int) (context.getResources().getDimension(R.dimen.dp_pixel_1) * 10));
		decreaseValue = a.getDimensionPixelSize(R.styleable.FontFitTextView_decreaseValue,
				(int) (context.getResources().getDimension(R.dimen.dp_pixel_1) * 2));
		needResize = a.getBoolean(R.styleable.FontFitTextView_needResize, true);
		needSpecilHightResize = a.getBoolean(R.styleable.FontFitTextView_needSpecielHeightResize,false);
		originalSize = this.getTextSize();
		paint = new Paint();

		a.recycle();
	}

	private void reSize(int width, String text){
		float targetWidth = width - this.getPaddingLeft() - this.getPaddingRight();
		if (targetWidth <= 0 || text == null || text.length() == 0)
			return;
		if(!needResize)
			return;
		paint.set(getPaint());
		float size = originalSize;
		while (size > minTextSize) {
			paint.setTextSize(size);
			if (paint.measureText(text) <= targetWidth)
				break;
			size -= decreaseValue;
		}
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	}
	/**使用時，須在layout設定   app:needSpecielHeightResize="true"**/
	private void reSizeForSpecialHeight(int height, String text){
		if(!(text.contains("j")||text.contains("p")||text.contains("q")))
			return ;
		if(!needSpecilHightResize)
			return;
		float targetHeight = height - this.getPaddingBottom()- this.getPaddingTop()  ;
		if (targetHeight <= 0 || text == null || text.length() == 0)
			return;

		paint.set(getPaint());
		float size = originalSize;
		while (size > minTextSize) {
			paint.setTextSize(size);
			float measureHeight = (paint.getFontMetrics().descent  - paint.getFontMetrics().ascent  + paint.getFontMetrics().leading);
			if (  measureHeight <= targetHeight )
				break;
			size -= decreaseValue;
		}
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	}

	@Override
	protected void onTextChanged(final CharSequence text, final int start, final int before, final int after){
		resize = true;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(resize){
			reSize(this.getWidth(), this.getText().toString());
			reSizeForSpecialHeight(this.getHeight(),this.getText().toString());
			resize = false;
		}
		super.onDraw(canvas);
	}


	@Override
	protected void onSizeChanged(int width, int height, int oldwidth, int oldheigh){
		if (width != oldwidth){
			resize = true;
		}
	}



	/**
	 * 設定最小字體值單位為sp
	 * 設定單位px請使用setMinTextSize(TypedValue.COMPLEX_UNIT_PX, float size)
	 * @param size
	 */
	public void setMinTextSize(float size){
		setMinTextSize(TypedValue.COMPLEX_UNIT_SP, size);
	}
	/**
	 * 設定字體大小與單位
	 * @param unit TypedValue.COMPLEX_UNIT_PX
	 * @param size
	 */
	public void setMinTextSize(int unit, float size){
		switch(unit){
			case TypedValue.COMPLEX_UNIT_SP:
				size = size * context.getResources().getDimension(R.dimen.dp_pixel_1);
				break;
		}
		minTextSize = size;
	}
	/**
	 * 設定每次縮小字體的遞減值單位為px
	 * @param value
	 */
	public void setDecreaseValue(float value){
		decreaseValue = value;
	}

	/**
	 * 設定是否需要自動調整尺寸
	 * @param need
	 */
	public void setNeedResize(boolean need){
		needResize = need;
	}


}
