package me.victor.drag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author Victor
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_drag).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                View.DragShadowBuilder builder = new View.DragShadowBuilder(v);
                ClipData data = ClipData.newPlainText("Label", "Hello World!");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, builder, v, 0);
                } else {
                    v.startDrag(data, builder, v, 0);
                }
                return true;
            }
        });
        findViewById(R.id.fl_drag).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("aaa", "开始拖拽");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("aaa", "结束拖拽");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("aaa", "拖拽的view进入监听的view时");
                        v.setBackgroundColor(Color.GRAY);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("aaa", "拖拽的view离开监听的view时");
                        v.setBackgroundResource(R.color.colorPrimary);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        float x = event.getX();
                        float y = event.getY();
                        Log.i("aaa", "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i("aaa", "释放拖拽的view");
                        if (event.getLocalState() != null) {
                            View localState = (View) event.getLocalState();
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ((ViewGroup) localState.getParent()).removeView(localState);
                            ((FrameLayout) v).addView(localState, layoutParams);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
